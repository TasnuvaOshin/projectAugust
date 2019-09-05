package apps.searchme.searchmeapplication.Auth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import apps.searchme.searchmeapplication.Home.HomeFragment;
import apps.searchme.searchmeapplication.MainActivity;
import apps.searchme.searchmeapplication.R;


public class RegFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private MaterialButton signupButton;
    private HomeFragment homeFragment;
    ArrayList<String> columnValue;
    private TextView textView;
    MaterialButton SignupButton;
    private TextInputEditText et_username, et_useremail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_reg, container, false);

        homeFragment = new HomeFragment();

        textView = view.findViewById(R.id.tv_error);
        columnValue = new ArrayList<String>();
        signupButton = view.findViewById(R.id.sign_up);

        //variable init

        et_username = view.findViewById(R.id.user_name);
        et_useremail = view.findViewById(R.id.email_address);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLocationPermission()) {

                    if(apps.searchme.searchmeapplication.Util.DeviceClass.isValidEmail(et_useremail.getText())) {
                        columnValue.add(0, "mac_address=" + apps.searchme.searchmeapplication.Util.DeviceClass.getMacAddress());
                        hasText(et_username, "Please Enter Your Name");
                        hasText(et_useremail, "Please Enter Your Email Address");
                        checkStatus();
                        InsertDb insertDb = new InsertDb();
                        insertDb.execute();

                    }else{

                        et_useremail.requestFocus();
                        et_useremail.setError("Please Enter a Valid Email Address");

                    }
                }


            }
        });


        return view;
    }

    private String checkStatus() {
        StringBuilder out = new StringBuilder();
        for (Object o : columnValue) {
            out.append("&" + o.toString());
        }
        Log.d("a", out.toString());
        return out.toString();
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }


    public void hasText(TextInputEditText editText, String error_message) {

        @SuppressLint({"NewApi", "LocalSuppress"}) String text = Objects.requireNonNull(editText.getText()).toString().trim();
        editText.setError(null);
//for registration we need to fill up every textinput
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(error_message);
        } else {
            String column_name = getResources().getResourceEntryName(editText.getId());
            String column_value = column_name + "=" + editText.getText().toString();
            columnValue.add(column_value);

        }


    }


    //permission check for the activity
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
                        .setTitle("Permission")
                        .setMessage("Please Share/on Your Location")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);

                            }
                        })
                        .create()
                        .show();


            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

            return false;
        } else {

            return true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {


            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity.class));


                }

            }

        }
    }


    public class InsertDb extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {

                url = new URL("https://tasnuvaoshin.com/searchme/registration.php?test=001" + checkStatus());
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

            JSONObject jsonObject = new JSONObject(myFile);
            String value = jsonObject.getString("value");
            String code = jsonObject.getString("code");
            if(code.equals("201")){

                SetFragment(homeFragment);
            }else {

                return value;
            }

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setVisibility(View.VISIBLE);
            textView.setText(s);
            columnValue.clear();

        }
    }




}
