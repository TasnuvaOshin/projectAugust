package apps.searchme.sm.ChatMessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.Home.HomeFragment;
import apps.searchme.sm.R;
import apps.searchme.sm.Recyclerview.ChatAdapter;
import apps.searchme.sm.Recyclerview.ChatClass;
import apps.searchme.sm.ShowResultUserProfile.ProfileActivity;
import apps.searchme.sm.Util.DeviceClass;
import apps.searchme.sm.profile_mode;

import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ChatClass> arrayList;
    private ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

       arrayList = new ArrayList<ChatClass>();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        InsertDb insertDb = new InsertDb();
        insertDb.execute();

back = view.findViewById(R.id.back_to_home);

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SetFragment(new HomeFragment());
    }
});
        return view;
    }

    public class InsertDb extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                SharedPreferences myPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                String myemail = myPrefs.getString("MEM1", "");
                url = new URL("http://search-me.xyz/searchme/chat/get_chat.php?email="+myemail);
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
                    String na = jsonArray.getJSONObject(x).getString("room_name");
                    String pro = jsonArray.getJSONObject(x).getString("user_one");
                    String con = jsonArray.getJSONObject(x).getString("user_two");
                    String mob = jsonArray.getJSONObject(x).getString("name_one");
                    String img = jsonArray.getJSONObject(x).getString("name_two");
                 arrayList.add(new ChatClass(na,pro,con,mob,img));
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

            ChatAdapter chatAdapter = new ChatAdapter(getActivity(),arrayList);
            recyclerView.setAdapter(chatAdapter);


        }
    }


    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }

}
