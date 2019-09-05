package apps.searchme.sm.Payment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.TransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.CurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.TransactionResponseListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import apps.searchme.sm.Auth.LoginFragment;
import apps.searchme.sm.Auth.RegFragment;
import apps.searchme.sm.R;
import apps.searchme.sm.Transaction.BackToRegActivity;
import apps.searchme.sm.Transaction.TransactionActivity;


public class PaymentFragment extends Fragment implements TransactionResponseListener {
    private ImageButton back;
    private TabHost tabHost;
    private Button pay;
    private ImageView Ipay;
    private CheckBox checkBox;
    SSLCommerzInitialization sslCommerzInitialization;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        back = view.findViewById(R.id.back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(new LoginFragment());
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing...");
        TabHost host = view.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Terms");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Terms");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("About-us");
        spec.setContent(R.id.tab2);
        spec.setIndicator("About-us");
        host.addTab(spec);
        //Tab 3

        spec = host.newTabSpec("Privacy");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Privacy");
        host.addTab(spec);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //  paymentWork();

            }
        }, 3000);


        pay = view.findViewById(R.id.pay);
        Ipay = view.findViewById(R.id.img_pay);
        checkBox = view.findViewById(R.id.check);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                CheckProcess();
            }
        });

        Ipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                CheckProcess();
            }
        });

        return view;


    }

    private void CheckProcess() {

        if (checkBox.isChecked()) {
            progressDialog.dismiss();
            paymentWork();


        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "You need to Agree With Our Terms & Condition to Proceed", Toast.LENGTH_LONG).show();
        }
    }

    public void paymentWork() {

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("searchmexyzlive",
                "5D6CD2A12473D52759", 10, CurrencyType.BDT,
                "123456789098765", "fund", SdkType.LIVE);

        IntegrateSSLCommerz
                .getInstance(getActivity())
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .buildApiCall(this);
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }

    @Override
    public void transactionSuccess(TransactionInfoModel transactionInfoModel) {
        Toast.makeText(getActivity(), "Successfully Paid! ", Toast.LENGTH_SHORT).show();
      SetFragment(new LoginFragment());

    }

    @Override
    public void transactionFail(String s) {
        Toast.makeText(getActivity(), "Transaction Failed !!!", Toast.LENGTH_SHORT).show();
        // SetFragment(new RegFragment());
        //need to add activity
        SetFragment(new LoginFragment());

    }

    @Override
    public void merchantValidationError(String s) {
        Toast.makeText(getActivity(), "Marchant Validation Failed !!!", Toast.LENGTH_SHORT).show();
        SetFragment(new PaymentFragment());
    }

}
