package apps.searchme.sm.Transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import apps.searchme.sm.CompleteYourProfile.ProfileInitFragment;
import apps.searchme.sm.Home.HomeFragment;
import apps.searchme.sm.R;

import android.os.Bundle;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        SetFragment(new ProfileInitFragment());
    }
    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }

}
