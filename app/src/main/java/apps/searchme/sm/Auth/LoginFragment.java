package apps.searchme.sm.Auth;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import apps.searchme.sm.Home.HomeFragment;
import apps.searchme.sm.MainActivity;
import apps.searchme.sm.Payment.PaymentFragment;
import apps.searchme.sm.R;
import apps.searchme.sm.ShowResultUserProfile.ProfileActivity;
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

public class LoginFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    MaterialButton materialButton, SignupButton;
    HomeFragment homeFragment;
    RegFragment regFragment;
    private TextView textView;
    private FirebaseAuth firebaseAuth;
    private TextInputEditText et_user_name;
    private ProgressDialog progressDialog;
    private TextView send_email_verification;
    private TextView reset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing ....");
        materialButton = view.findViewById(R.id.login_button);
        SignupButton = view.findViewById(R.id.SignUp);
        send_email_verification = view.findViewById(R.id.send_email_verification);
        homeFragment = new HomeFragment();
        regFragment = new RegFragment();
        textView = view.findViewById(R.id.tv_error);
        firebaseAuth = FirebaseAuth.getInstance();
        reset = view.findViewById(R.id.reset);

        et_user_name = view.findViewById(R.id.user_name);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Reset Account")
                        .setMessage("Want To Send Request For Account Reset?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                                ChangeMac changeMac = new ChangeMac();
                                changeMac.execute();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();


            }
        });

        send_email_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseAuth.getCurrentUser() != null) {
                    firebaseAuth.getCurrentUser().reload();
                    if (!firebaseAuth.getCurrentUser().isEmailVerified()) {
                        firebaseAuth.getCurrentUser().sendEmailVerification();
                        Toast.makeText(getActivity(), "Email Sent!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Your email has been verified! You can login now.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (checkLocationPermission()) {

                    if (TextUtils.isEmpty(et_user_name.getText())) {
                        progressDialog.dismiss();
                        et_user_name.requestFocus();
                        et_user_name.setError("Enter Your Email Address");
                    } else {

                        progressDialog.show();
                        String mac_address = DeviceClass.getMacAddress();
                        String user_name = et_user_name.getText().toString().trim();
                        String url = "http://search-me.xyz/searchme/login.php?email_address=" + user_name + "&mac_address=" + mac_address;
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user != null) {
                            firebaseAuth.getCurrentUser().reload();
                            if (user.isEmailVerified()) {

                                InsertDb insertDb = new InsertDb();
                                insertDb.execute(url);

                            } else {
                                progressDialog.dismiss();
                                firebaseAuth.getCurrentUser().reload();
                                send_email_verification.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            //need to work
                            // Toast.makeText(getActivity(), "Login to firebase", Toast.LENGTH_SHORT).show();
                            InsertDb insertDb = new InsertDb();
                            insertDb.execute(url);
                        }


                    }

                }

            }
        });


        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new PaymentFragment());
            }
        });
        return view;
    }


    //for fragment change
    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }


    //permission work starts from here

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

                String urls = strings[0];
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

                JSONObject jsonObject = new JSONObject(myFile);
                String value = jsonObject.getString("value");
                String code = jsonObject.getString("code");
                if (code.equals("201")) {
                    progressDialog.dismiss();
                    SetFragment(homeFragment);

                } else {

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


        }
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences myPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
        String online = myPrefs.getString("online", "");
        if (online.equals("yes")) {
            SetFragment(new HomeFragment());
        }

    }

    public class ChangeMac extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {

                String email = et_user_name.getText().toString();
                url = new URL("http://search-me.xyz/searchme/request/req_mac.php?req_mac_address=" + DeviceClass.getMacAddress() + "&email_address=" + email + "&name=no");
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

            Toast.makeText(getActivity(), "Request Sent", Toast.LENGTH_LONG).show();
        }
    }


}
