package apps.searchme.sm.ViewCustomizeSearch;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.Home.HomeFragment;
import apps.searchme.sm.R;
import apps.searchme.sm.Recyclerview.ViewCusAdapter;
import apps.searchme.sm.Recyclerview.viewCus;
import apps.searchme.sm.Util.DeviceClass;

public class viewCustomize extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<viewCus> transactionList;
    private defaultRideShareView defaultRideShareView;
    private CardView cardView;
    private ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_view_customize, container, false);
        cardView = view.findViewById(R.id.ride_card);
        defaultRideShareView = new defaultRideShareView();
        transactionList = new ArrayList<viewCus>();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        InsertDb insertDb = new InsertDb();
        insertDb.execute("http://search-me.xyz/searchme/multisearch/get_multi.php?mac_address=" + DeviceClass.getMacAddress());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(defaultRideShareView);
            }
        });
        back = view.findViewById(R.id.back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                fragmentTransaction.addToBackStack("my_fragment").commit();
            }
        });
        return view;
    }

//get the title list from here


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
                    String title = jsonArray.getJSONObject(x).getString("title");
                    String mac = jsonArray.getJSONObject(x).getString("mac_address");
                    transactionList.add(new viewCus(mac, title));
                }
                return myFile;
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
            Log.d("size", String.valueOf(transactionList.size()));
            ViewCusAdapter mAdapter = new ViewCusAdapter(getActivity(), transactionList);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }
    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }
}
