package apps.searchme.sm.ViewCustomizeSearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.FragmentTransaction;
import apps.searchme.sm.Home.HomeFragment;
import apps.searchme.sm.R;
import apps.searchme.sm.ResultAndRoute.ResultActivity;
import apps.searchme.sm.Util.DeviceClass;

public class defaultRideShareView extends Fragment {
    CheckBox mode_yes, mode_no, journey_yes, journey_no;
    String mode, journey;
    private MaterialButton materialButton;
    private TextInputEditText vechile_name, destination;
    private ImageButton back;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_ride_share_view, container, false);


        mode_yes = view.findViewById(R.id.mode_yes);
        mode_no = view.findViewById(R.id.mode_no);
        materialButton = view.findViewById(R.id.search);
        journey_yes = view.findViewById(R.id.journey_yes);
        journey_no = view.findViewById(R.id.journey_no);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Searching....");


        vechile_name = view.findViewById(R.id.vehicle_name);
        destination = view.findViewById(R.id.destination);
        back = view.findViewById(R.id.back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new viewCustomize());
                fragmentTransaction.addToBackStack("my_fragment").commit();
            }
        });

        mode_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mode_no.setChecked(false);
                    mode = mode_yes.getText().toString();
                }
            }
        });


        mode_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mode_yes.setChecked(false);
                    mode = mode_no.getText().toString();
                }
            }
        });


        journey_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    journey_no.setChecked(false);
                    journey = journey_yes.getText().toString();
                }
            }
        });


        journey_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    journey_yes.setChecked(false);
                    journey = journey_no.getText().toString();
                }
            }
        });


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String car = vechile_name.getText().toString();
                String des = destination.getText().toString();

                //we will send the link to search the result
                progressDialog.show();
                Intent mIntent = new Intent(getActivity(), ResultActivity.class);
                Log.d("a", mode + journey + des + car);
                mIntent.putExtra("data", "http://search-me.xyz/searchme/search/get_search.php?rider_mode=" + mode + "&journey_mode=" + journey + "&vehicle_name=" + car + "&journey_destination=" + des);
                startActivity(mIntent);

            }
        });
        return view;
    }


}
