package apps.searchme.searchmeapplication.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import apps.searchme.searchmeapplication.R;

public class HomeFragment extends Fragment {
    private MaterialButton CompleteProfile;
    private apps.searchme.searchmeapplication.CompleteYourProfile.ProfileInitFragment profileInitFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View  view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
       profileInitFragment = new apps.searchme.searchmeapplication.CompleteYourProfile.ProfileInitFragment();
       CompleteProfile = view.findViewById(R.id.CompleteProfile);

       CompleteProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SetFragment(profileInitFragment);
           }
       });
        return view;
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }
}
