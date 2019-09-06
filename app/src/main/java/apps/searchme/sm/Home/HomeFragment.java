package apps.searchme.sm.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import apps.searchme.sm.Auth.LoginFragment;
import apps.searchme.sm.BuildConfig;
import apps.searchme.sm.ChatMessage.ChatFragment;
import apps.searchme.sm.CompleteYourProfile.ProfileInitFragment;
import apps.searchme.sm.CustomizeSearchView.csv;
import apps.searchme.sm.MultiSearch.MultiSearchFragment;
import apps.searchme.sm.Support.SupportFragment;
import apps.searchme.sm.ViewCustomizeSearch.viewCustomize;
import apps.searchme.sm.R;
import apps.searchme.sm.Util.DeviceClass;

import static android.content.Context.MODE_PRIVATE;
/*
Search Me Project Builder
Company : Joy Technologies Ltd
Project Author : Tasnuva Tabassum Oshin,Sr Software Enginner at Joy Technologies Ltd
Team : Joy It Team
http://joy-technologies-ltd.com/
Copyright@2019-tasnuva
Phone : 01401144309
For your Kind Information This Project is Made By Joy Technologies Ltd.
Thanks.
*/
public class HomeFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private CardView CompleteProfile, MultiSearch, CreateCustomize, CustomizeSearchView, msg, support;
    private ProfileInitFragment profileInitFragment;
    private MultiSearchFragment multiSearchFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;
    private Double currentLang, currentLong;
    private viewCustomize viewCustomize;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    private ImageView iv;

    private csv csv;
    private ChatFragment chatFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        support = view.findViewById(R.id.support);
        profileInitFragment = new ProfileInitFragment();
        CompleteProfile = view.findViewById(R.id.CompleteProfile);
        MultiSearch = view.findViewById(R.id.MultiSearch);
        CreateCustomize = view.findViewById(R.id.CreateCustomize);
        CustomizeSearchView = view.findViewById(R.id.CustomizeSearchView);
        multiSearchFragment = new MultiSearchFragment();
        chatFragment = new ChatFragment();
        csv = new csv();
        viewCustomize = new viewCustomize();
        navigationView = view.findViewById(R.id.navigation_View);
        toolbar = view.findViewById(R.id.toolbar);
        drawerLayout = view.findViewById(R.id.drawaer_layout);
        imageButton = view.findViewById(R.id.ib_open);
//        iv = view.findViewById(R.id.IV);
        msg = view.findViewById(R.id.msg);

//        Glide.with(getActivity()).load(R.drawable.maingif)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
//                        .error(R.drawable.maingif)).into(iv);
        SetUpSideDrawer();
        SetupMenu();

        CompleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(profileInitFragment);
            }
        });
        MultiSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(multiSearchFragment);
            }
        });
        CreateCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(csv);
            }
        });
        CustomizeSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(viewCustomize);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new SupportFragment());
            }
        });

        GetCurrentLocation();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
// If the navigation drawer is not open then open it, if its already open then close it.
                if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(Gravity.START);
                else drawerLayout.closeDrawer(Gravity.END);
            }
        });


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(chatFragment);
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.logout:
                        SharedPreferences myPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.putString("online", "no");
                        editor.commit();
                        SetFragment(new LoginFragment());
                        return true;

                    case R.id.share:
                        ShareTheApp();
                        return true;

                    case R.id.support:
                        SetFragment(new SupportFragment());
                        return true;
                }


                return false;
            }
        });

        hideKey();




        SharedPreferences myPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("online", "yes");
        editor.commit();



        return view;
    }

    private void hideKey() {// Check if no view has focus:
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
////Show
//        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

    }

    private void ShareTheApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Search Me");
        String shareMessage = "\nI recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }


    private void GetCurrentLocation() {
        if (checkLocationPermission()) {
            Log.d("debug", "Now We want to show our current location");
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            @SuppressLint("MissingPermission") Task LocationTask = fusedLocationProviderClient.getLastLocation();

            LocationTask.addOnCompleteListener(new OnCompleteListener() {

                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {

                        Log.d("debug", "Now We get our Current Location");

                        location = (Location) task.getResult();

                        currentLang = location.getLatitude();
                        currentLong = location.getLongitude();
                        Log.d("lat", String.valueOf(currentLang));
                        Log.d("long", String.valueOf(currentLong));
                        UpdateLocation updateLocation = new UpdateLocation();
                        updateLocation.execute();


                    }
                }
            });
        }

    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission")
                        .setMessage("PLease Approve this permission")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                        //now do what u what
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        }
    }


    public class UpdateLocation extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;

            try {

                url = new URL("http://search-me.xyz/searchme/update_data.php?test=001&current_latitude=" + currentLang + "&current_longitude=" + currentLong + "&mac_address=" + DeviceClass.getMacAddress());
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


            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;


        }


    }

    private void SetupMenu() {
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//
//
//            }
//        });
    }


    private void SetUpSideDrawer() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }


}
