package apps.searchme.sm.OnlyTestPurpose;

import androidx.appcompat.app.AppCompatActivity;
import apps.searchme.sm.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.TransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.CurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.TransactionResponseListener;

public class TestActivity extends AppCompatActivity implements TransactionResponseListener {
private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        context = getApplicationContext();





    }

    @Override
    public void transactionSuccess(TransactionInfoModel transactionInfoModel) {
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void transactionFail(String s) {
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void merchantValidationError(String s) {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
    }

    public void doIt(View view) {

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("searchmexyzlive",
                "5D6CD2A12473D52759", 10, CurrencyType.BDT,
                "123456789098765", "food", SdkType.LIVE);

        IntegrateSSLCommerz
                .getInstance(TestActivity.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .buildApiCall(TestActivity.this);

    }
}
