package apps.searchme.sm.Recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView user_one,user_two;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        user_one = itemView.findViewById(R.id.user_one);
        user_two = itemView.findViewById(R.id.user_two);
    }
}
