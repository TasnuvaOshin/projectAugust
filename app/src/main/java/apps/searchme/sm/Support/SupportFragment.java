package apps.searchme.sm.Support;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import apps.searchme.sm.Home.HomeFragment;
import apps.searchme.sm.R;
import apps.searchme.sm.ShowResultUserProfile.ProfileActivity;
import apps.searchme.sm.Util.DeviceClass;

import static android.content.Context.MODE_PRIVATE;


public class SupportFragment extends Fragment {
    private ImageButton imageButton;
    private Button submit;
    private EditText title, des;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        imageButton = view.findViewById(R.id.back_to_home);
        title = view.findViewById(R.id.title);
        des = view.findViewById(R.id.des);
        submit = view.findViewById(R.id.submit);
        progressDialog = new ProgressDialog(getActivity());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title.getText().toString();
                String d = des.getText().toString();

                if (TextUtils.isEmpty(t)) {

                    title.requestFocus();
                    title.setError("Please Enter The Title");
                } else if (TextUtils.isEmpty(d)) {
                    des.requestFocus();
                    des.setError("Please Enter The Description");

                } else {
                    progressDialog.show();
                    InsertDb insertDb = new InsertDb();
                    insertDb.execute();
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new HomeFragment());
            }
        });
        return view;
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }


    public class InsertDb extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;

            try {
                String t = title.getText().toString();
                String d = des.getText().toString();
                SharedPreferences myPrefs = (SharedPreferences) getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                String myemail = myPrefs.getString("MEM1", "");

                url = new URL("http://search-me.xyz/searchme/support/support.php?title=" + t + "&mac_address=" + DeviceClass.getMacAddress() + "&des=" + d + "&email_address=" + myemail);
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
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
            SetFragment(new HomeFragment());
        }
    }

}
