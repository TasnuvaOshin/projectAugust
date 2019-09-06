package apps.searchme.sm.Recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.R;


public class ChatMsgViewHolder extends RecyclerView.ViewHolder {

   public TextView msg, user,date;



    public ChatMsgViewHolder(@NonNull View itemView) {
        super(itemView);
        msg = itemView.findViewById(R.id.msg);
        user = itemView.findViewById(R.id.user);
        date = itemView.findViewById(R.id.date);
    }
}
