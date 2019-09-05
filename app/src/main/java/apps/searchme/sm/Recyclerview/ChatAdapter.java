package apps.searchme.sm.Recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.ChatMessage.ChatMainFragment;
import apps.searchme.sm.R;


public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {


    Context context;
    ArrayList<ChatClass> arrayList;

    public ChatAdapter() {
    }

    public ChatAdapter(Context context, ArrayList<ChatClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_title_row, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {


        final ChatClass Current = arrayList.get(position);
        holder.user_one.setText(Current.getName_one());
        holder.user_two.setText(Current.getName_two());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMainFragment chatMainFragment = new ChatMainFragment();
                Bundle i = new Bundle();
                i.putString("room",Current.getRoom_name());
                chatMainFragment.setArguments(i);
                SetFragment(chatMainFragment);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }
}
