package apps.searchme.sm.ChatMessage;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.R;
import apps.searchme.sm.Recyclerview.ChatMsgViewHolder;
import apps.searchme.sm.Recyclerview.ChatViewClass;

import static android.content.Context.MODE_PRIVATE;


public class ChatMainFragment extends Fragment {
    private TextInputEditText textInputEditText;
    private ImageButton send;
    private DatabaseReference databaseReference;
    private String name;
    private ImageButton back;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FirebaseRecyclerOptions<ChatViewClass> options;
    private FirebaseRecyclerAdapter<ChatViewClass, ChatMsgViewHolder> firebaseRecyclerAdapter;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_main, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        name = this.getArguments().getString("room");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("message").child(name);




        send = view.findViewById(R.id.send);
        textInputEditText = view.findViewById(R.id.write);
        back = view.findViewById(R.id.back_to_home);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager((AppCompatActivity)getActivity());
        recyclerView.setLayoutManager(layoutManager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new ChatFragment());
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences myPrefs = (SharedPreferences) getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
               // String myemail = myPrefs.getString("MEM1", "");
                String myname = myPrefs.getString("MEM2", "");
                String text = textInputEditText.getText().toString();
                Log.d("email", myname);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", myname);
                hashMap.put("msg", text);
                databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            textInputEditText.getText().clear();
                        } else {

                            Toast.makeText(getActivity(), "Please Write Again....", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        options = new FirebaseRecyclerOptions.Builder<ChatViewClass>().setQuery(databaseReference, ChatViewClass.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatViewClass, ChatMsgViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatMsgViewHolder chatMsgViewHolder, int i, @NonNull ChatViewClass chatViewClass) {
                chatMsgViewHolder.msg.setText(chatViewClass.getMsg());
                chatMsgViewHolder.user.setText(chatViewClass.getName());


            }

            @NonNull
            @Override
            public ChatMsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ChatMsgViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.chat_view_row, parent, false));
            }
        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);


        return view;
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }


    @Override
    public void onStart() {
        firebaseRecyclerAdapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        firebaseRecyclerAdapter.stopListening();
        super.onStop();
    }
}
