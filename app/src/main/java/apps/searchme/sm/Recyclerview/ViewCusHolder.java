package apps.searchme.sm.Recyclerview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.R;

public class ViewCusHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageButton imageButton;


    public ViewCusHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.title);
        imageButton = itemView.findViewById(R.id.ib_delete);
    }
}
