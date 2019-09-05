package apps.searchme.sm.ResultAndRoute;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.Main2Activity;
import apps.searchme.sm.MainActivity;
import apps.searchme.sm.R;
import apps.searchme.sm.Recyclerview.resultAdapter;
import apps.searchme.sm.Transaction.BackToMainActivity;
import apps.searchme.sm.Transaction.TransactionActivity;
import apps.searchme.sm.Util.DeviceClass;
import apps.searchme.sm.Util.FetchURL;
import apps.searchme.sm.Util.TaskLoadedCallback;
import apps.searchme.sm.profile_mode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 11;
    private TabHost tabHost;
    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private ArrayList arrayList;
    private Marker mCurrLocationMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;
    private Double currentLang, currentLong, distance;
    private ArrayList<profile_mode> transactionList;
    private MarkerOptions markerOptions;
    private Polyline currentLine;
    int count = 0;
    int j;
    private TextView search_result, active, deactive;
    private String phoneno;
    private ImageButton back;
    private int o = 0;
    private int f = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        search_result = findViewById(R.id.search_result);
        transactionList = new ArrayList<profile_mode>();
        back = findViewById(R.id.back_to_home);
        String value = getIntent().getStringExtra("data");
        Log.d("value", value);
        TabHost host = findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Search Results");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Search Results");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Map View");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Map View");
        host.addTab(spec);
        if (checkLocationPermission()) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(this);
            }
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        markerOptions = new MarkerOptions();
        active = findViewById(R.id.search_result_active);
        deactive = findViewById(R.id.search_result_deactive);

        InsertDb insertDb = new InsertDb();
        insertDb.execute(value);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ResultActivity.this, BackToMainActivity.class));
                ResultActivity.this.overridePendingTransition(0, 0);
            }
        });
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(ResultActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ResultActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(ResultActivity.this)
                        .setTitle("Permission")
                        .setMessage("Please Share/on Your Location")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ResultActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                                ResultActivity.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(ResultActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }

            return false;
        } else {

            return true;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (checkLocationPermission()) {
            mMap.setMyLocationEnabled(true);
            //for getting the current Location for my place

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    GetCurrentLocation();
                }
            });
            t1.start();


            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {


                    //we need the api url for getting the route
                    String url = getUrl(currentLang, currentLong, marker.getPosition(), "driving");

                    if (checkPermissions()) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + marker.getSnippet()));
                        startActivity(callIntent);
                    }


//        /*
//        Fetchurl is the class that will get the value from the url
//         */
//                    new FetchURL(ResultActivity.this).execute(url, "driving");
//                    // Toast.makeText(SearchRouteActivity.this, "Infowindow clicked", Toast.LENGTH_SHORT).show();
//
//

                }
            });


        }
    }


    private void GetOtherLocation() {

        for (j = 0; j < transactionList.size(); j++) {

            double lat = Double.parseDouble(transactionList.get(j).getCurrent_latitude());

            // Getting longitude of the place
            double lng = Double.parseDouble(transactionList.get(j).getCurrent_longitude());
            Log.d("lat", String.valueOf(lat));
            Log.d("lat", String.valueOf(lng));


            //for counting distance

            float results[] = new float[10];
            Location.distanceBetween(currentLang,
                    currentLong, lat, lng, results);
            Double dis = (double) (results[0] / 1000);
            Double mint = dis * 3.5;
            markerOptions.snippet("Distance=" + new DecimalFormat("##.##").format(dis) + "km" + "& Time Duration = " + new DecimalFormat("##.##").format(mint) + " min");
            // +new DecimalFormat("##.##").format( distance)+ " Km"
            distance = (double) (results[0] / 1000);
            // Getting name
            String name = transactionList.get(j).getName();
            // Getting vicinity
            String country = transactionList.get(j).getCountry_name();
            String profession = transactionList.get(j).getProfession();

            LatLng latLng = new LatLng(lat, lng);
            float zoomLevel = 10.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
            // Setting the position for the marker
            markerOptions.position(new LatLng(Double.parseDouble(transactionList.get(j).getCurrent_latitude()), Double.parseDouble(transactionList.get(j).getCurrent_longitude())));

            markerOptions.title(name + " | " + profession + "| " + country + new DecimalFormat("##.##").format(distance) + " Km Away");
            phoneno = transactionList.get(j).getMobile();
            markerOptions.snippet(phoneno);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

            // Placing a marker on the touched position
            Marker m = mMap.addMarker(markerOptions);
            m.showInfoWindow();
//for showing the current Position ALso
            LatLng latLngs = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.position(latLngs);
            markerOptions2.title("My Position");
            markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mCurrLocationMarker = mMap.addMarker(markerOptions2);
            // UpdateCamera(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM, "Location");
                          /*
                          now we will get the api from which we can the nearby market
                           */


        }
    }

    //this is for getting the current Location
    private void GetCurrentLocation() {

        if (checkLocationPermission()) {
            Log.d("debug", "Now We want to show our current location");
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ResultActivity.this);
            @SuppressLint("MissingPermission") Task LocationTask = fusedLocationProviderClient.getLastLocation();

            LocationTask.addOnCompleteListener(new OnCompleteListener() {

                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {

                        Log.d("debug", "Now We get our Current Location");

                        location = (Location) task.getResult();

                        currentLang = location.getLatitude();
                        currentLong = location.getLongitude();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 6f));
                        //    GetOtherLocation();

                    }
                }
            });
        }
    }

    ///inserdata
    public class InsertDb extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String urls = strings[0];

            try {

                url = new URL(urls);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                String myFile;
                int i;

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line);

                }

                myFile = stringBuffer.toString();
                Log.d("response", myFile);

                JSONArray jsonArray = new JSONArray(myFile);
                for (int x = 0; x < jsonArray.length(); x++) {
                    String na = jsonArray.getJSONObject(x).getString("name");
                    String pro = jsonArray.getJSONObject(x).getString("profession");
                    String con = jsonArray.getJSONObject(x).getString("country_name");
                    String mob = jsonArray.getJSONObject(x).getString("mobile");
                    String img = jsonArray.getJSONObject(x).getString("img_url");
                    String lat = jsonArray.getJSONObject(x).getString("current_latitude");
                    String lon = jsonArray.getJSONObject(x).getString("current_longitude");
                    String mac = jsonArray.getJSONObject(x).getString("mac_address");
                    String status = jsonArray.getJSONObject(x).getString("status");
                    transactionList.add(new profile_mode(na, pro, con, mob, img, lat, lon, mac, status));

                    if (status.equals("on")) {
                        o++;
                    } else {

                        f++;
                    }
                }
                return myFile;


            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;


        }


        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            GetOtherLocation();
            Log.d("size", String.valueOf(transactionList.size()));
            search_result.setText("Results Found  :   " + transactionList.size());
            resultAdapter mAdapter = new resultAdapter(ResultActivity.this, transactionList, currentLang, currentLong);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            active.setText("  " + o);
            deactive.setText("  " + f);
        }
    }

    private String getUrl(Double originLat, Double originLan, LatLng destination, String driving) {


        String start = "origin=" + originLat + "," + originLan;
        String end = "destination=" + destination.latitude + "," + destination.longitude;
        String mode = "mode=" + driving;
        String format = start + "&" + end + "&" + mode;
        String apiUrl = "https://maps.googleapis.com/maps/api/directions/json?" + format + "&key=AIzaSyDMJgOYgIF2qVP7fOmvVeVOxjDkp8gWy74";
        return apiUrl;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentLine != null) {
            currentLine.remove();
        } else {

            currentLine = mMap.addPolyline((PolylineOptions) values[0]);

        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_pin_drop_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(ResultActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) ResultActivity.this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(ResultActivity.this)
                        .setTitle("Permission")
                        .setMessage("Please Share/on Your Location")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) ResultActivity.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);

                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) ResultActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }

            return false;
        } else {

            return true;

        }
    }

    @Override
    public void onBackPressed() {

    }
}
