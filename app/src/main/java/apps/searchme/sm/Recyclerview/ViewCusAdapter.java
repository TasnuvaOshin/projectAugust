package apps.searchme.sm.Recyclerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import apps.searchme.sm.MainActivity;
import apps.searchme.sm.MultiSearchResult.MultiViewSearchResult;
import apps.searchme.sm.R;
import apps.searchme.sm.Transaction.ChangeFragment;
import apps.searchme.sm.Transaction.TransactionActivity;
import apps.searchme.sm.Util.DeviceClass;

public class ViewCusAdapter extends RecyclerView.Adapter<ViewCusHolder> {

    Context context;
    ArrayList<viewCus> arrayList;
    MultiViewSearchResult multiViewSearchResult;

    public ViewCusAdapter() {
    }

    public ViewCusAdapter(Context context, ArrayList<viewCus> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewCusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_row, parent, false);

        return new ViewCusHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCusHolder holder, int position) {
        final viewCus curent = arrayList.get(position);
        holder.textView.setText(curent.getTitle());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to Delete This?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                DeleteThis  deleteThis = new DeleteThis();
                                deleteThis.execute(curent.getTitle());
                                SetFragment(new ChangeFragment());

                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SetFragment(curent.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void SetFragment(String title) {

        Bundle bundle=new Bundle();
        bundle.putString("title", title);
        multiViewSearchResult = new MultiViewSearchResult();
        multiViewSearchResult.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, multiViewSearchResult);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }


    public class DeleteThis extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
             String name = strings[0];
            try {

                url = new URL("http://search-me.xyz/searchme/delete/delete.php?mac_address="+DeviceClass.getMacAddress()+"&title="+name);
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

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;


        }


    }
    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }
}
