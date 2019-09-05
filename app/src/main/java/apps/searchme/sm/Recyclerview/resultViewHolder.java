package apps.searchme.sm.Recyclerview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.R;

public class resultViewHolder extends RecyclerView.ViewHolder {

    de.hdodenhof.circleimageview.CircleImageView imageView;
    TextView username,userPro,userCoun,userdistance;
    ImageButton imageButton_call,ib_profile;
    ImageView imageViewIcon;


    public resultViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        imageViewIcon = itemView.findViewById(R.id.icon);
        username = itemView.findViewById(R.id.username);
        userPro = itemView.findViewById(R.id.userprofession);
        userCoun = itemView.findViewById(R.id.usercountry);
        imageButton_call = itemView.findViewById(R.id.iv_call);
        ib_profile = itemView.findViewById(R.id.ib_profile);
        userdistance = itemView.findViewById(R.id.userdistance);
    }
}
