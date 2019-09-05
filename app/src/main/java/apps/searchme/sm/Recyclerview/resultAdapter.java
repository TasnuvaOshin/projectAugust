package apps.searchme.sm.Recyclerview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.R;
import apps.searchme.sm.ShowResultUserProfile.ProfileActivity;
import apps.searchme.sm.profile_mode;

public class resultAdapter extends RecyclerView.Adapter<resultViewHolder> {


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Context context;
    ArrayList<profile_mode> arrayList;
    Double currentLang,currentLong;

    public resultAdapter(Context context, ArrayList<profile_mode> arrayList,Double currentLang,Double currentLong) {
        this.context = context;
        this.arrayList = arrayList;
        this.currentLang = currentLang;
        this.currentLong = currentLong;
    }

    @NonNull
    @Override
    public resultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_row, parent, false);

        return new resultViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull resultViewHolder holder, int position) {
        final profile_mode curent = arrayList.get(position);
        String status = curent.getStatus();
        if(status.equals("on")){

            holder.imageViewIcon.setImageResource(R.drawable.on);
        }else {

            holder.imageViewIcon.setImageResource(R.drawable.off);
        }

           Double val = CalculationByDistance(currentLang,currentLong,curent.getCurrent_latitude(),curent.getCurrent_longitude());
        //String.valueOf(val)
        //new DecimalFormat("##.##").format(i2)
        holder.userdistance.setText(new DecimalFormat("##.##").format(val) +" Km Away");
        holder.username.setText(curent.getName());
        holder.userPro.setText(curent.getProfession());
        holder.userCoun.setText(curent.getCountry_name());
        Picasso.get().load("http://search-me.xyz/searchme/img/" + curent.getImg_url()).into(holder.imageView);

        holder.imageButton_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + curent.getMobile()));
                    view.getContext().startActivity(callIntent);
                }
            }
        });


        holder.ib_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("mac_address", curent.getMac_address());
                context.startActivity(intent);

            }
        });


    }

    private Double CalculationByDistance(Double currentLang, Double currentLang1, String current_latitude, String current_longitude) {

        float results[] = new float[10];
        Location.distanceBetween(currentLang,
                currentLang1,  Double.parseDouble(current_latitude),  Double.parseDouble(current_longitude), results);
        Double dis = (double) (results[0] / 1000);
        return dis;
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Permission")
                        .setMessage("Please Share/on Your Location")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);

                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }

            return false;
        } else {

            return true;

        }
    }









}
