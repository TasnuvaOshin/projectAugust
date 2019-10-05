package joytechnologiesltd.com.luckyme.Home;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.fragment.app.FragmentTransaction;
import joytechnologiesltd.com.luckyme.R;


public class HomeFragment extends Fragment {
    private int rotation;
    private Button cardView;
    private ObjectAnimator animation;
    private ImageView imageView;
    private RotateAnimation rotate;
    private String title, msg;
    private AlertDialog.Builder alert;

    int rot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cardView = view.findViewById(R.id.play);

        imageView = view.findViewById(R.id.imageView);
        CheckStatus checkStatus = new CheckStatus();
        checkStatus.execute();
        alert = new AlertDialog.Builder(getActivity());
        animation = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360);
        animation.setDuration(500);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
         Log.d("date",date);
        //start
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.INVISIBLE);
                animation.start();
                CountMe();

            }
        });




        return view;
    }

    private void CountMe() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                animation.cancel();
                //value for firebase main value
                imageView.setRotation(rot);
                ShowNotification(title, msg, new HomeFragment());


                //  Toast.makeText(getActivity(), "" + imageView.getRotation(), Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

    private void ShowNotification(String title, String msg, final HomeFragment homeFragment) {

        alert
                .setTitle(title)
                .setMessage(msg)
                .setIcon(R.drawable.coin)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Prompt the user once explanation has been shown
                        SetFragment(homeFragment);
                        cardView.setVisibility(View.VISIBLE);
                    }
                });



        AlertDialog dialog_card = alert.create();
        // dlgAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // WindowManager.LayoutParams WMLP =
        dialog_card.getWindow().setGravity(Gravity.BOTTOM);
        dialog_card.setCanceledOnTouchOutside (false);
        dialog_card.getWindow().setBackgroundDrawableResource(android.R.color.white);
        dialog_card.show();

    }


    public class CheckStatus extends AsyncTask<Void, Void, Void> {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        URL url;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {


            try {

                url = new URL("http://v-tube.xyz/flexie/spin/spin.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                String myFile;
                int i;

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line);

                }

                myFile = stringBuffer.toString();
                Log.d("response", myFile);
                JSONArray jsonArray = new JSONArray(myFile);

                int recharge = Integer.parseInt(jsonArray.getJSONObject(0).getString("r_round"));
                int gift = Integer.parseInt(jsonArray.getJSONObject(0).getString("g_round"));
                int spin = Integer.parseInt(jsonArray.getJSONObject(0).getString("total_spin"));

                if (recharge > 0) {
                    if (spin % 5 == 0) {
                        rot = 130;

                        title = "Congratulations!!!!";
                        msg = "You Have Win 10 taka Mobile Recharge";
                        UpdateValue updateValue = new UpdateValue();
                        updateValue.execute(recharge-1, gift, spin + 1);

                    } else {

                        rot = 100;
                        //try again

                        title = "Sorry!!!!";
                        msg = "Please Try Again";
                        UpdateValue updateValue = new UpdateValue();
                        updateValue.execute(recharge, gift, spin + 1);

                    }


                } else {

                    if (gift > 0) {
                        if (spin % 5 == 0) {
                            rot = 170;

                            title = "Congratulations!!!!";
                            msg = "You Have Win 50 Coins";
                            UpdateValue updateValue = new UpdateValue();
                            updateValue.execute(recharge - 1, gift, spin + 1);

                        } else {
                            rot = 250;
                            //try again
                            title = "Sorry!!!!";
                            msg = "Please Try Again";
                            UpdateValue updateValue = new UpdateValue();
                            updateValue.execute(recharge, gift, spin + 1);

                        }

                    } else {

                        //no gift no recharge available
                        rot = 310;

                        title = "Sorry!!!!";
                        msg = "Please Try Again";
                        UpdateValue updateValue = new UpdateValue();
                        updateValue.execute(recharge, gift, spin + 1);

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }
    }


    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }


    public class UpdateValue extends AsyncTask<Integer, Integer, Integer> {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        URL url;

        @Override
        protected Integer doInBackground(Integer... integers) {


            int reg = integers[0];
            int gifts = integers[1];
            int total = integers[2];


            try {
                url = new URL("http://v-tube.xyz/flexie/spin/update.php?reg=" + reg + "&gift=" + gifts + "&total=" + total);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                String myFile;
                int i;

                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line);

                }


            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;

        }


    }
}
