package apps.searchme.sm.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import apps.searchme.sm.MainActivity;
import apps.searchme.sm.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView imageView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.logo);
        relativeLayout = findViewById(R.id.show);

        Glide.with(SplashScreenActivity.this).load(R.drawable.search)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                        .error(R.drawable.search)).into(imageView);

        NextMe();
    }

    private void NextMe() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity( new Intent(SplashScreenActivity.this,MainActivity.class));
                SplashScreenActivity.this.overridePendingTransition(0,0);

            }
        }, 3000);
    }




}
