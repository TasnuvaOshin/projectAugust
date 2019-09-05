package apps.searchme.sm.CustomizeSearchView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.fragment.app.FragmentTransaction;
import apps.searchme.sm.Home.HomeFragment;
import apps.searchme.sm.R;
import apps.searchme.sm.Util.DeviceClass;


public class csv extends Fragment {
    private CheckBox date_of_birth, gender, blood_group, donate_blood, marital_status, marital_offer, mode, co_working_mode, rider_mode, journey_mode, user_name, key_word, father_name, mother_name, name, nationality, religion, profession, telephone, email, fax, mobile, social_network_id, present_address, country_name, school_institute_name, school_institute_short_name, school_exam_name, school_subject, school_class, school_roll_id_no, school_passing_year, college_institute_name, college_institute_short_name, college_exam_name, college_subject, college_roll_id_no, college_passing_year, uni_institute_name, uni_institute_short_name, uni_program_name, uni_subject, uni_id_no, uni_passing_year, more_institute_name, more_institute_short_name, more_program_name, more_department_subject, more_id_no, more_passing_year, sales_product_name, sales_details, professional_organization, posted_office_name, designation, professional_department, specialized_on, year_of_experience, professional_address, business_organization_name, training, services, business_address, partner_subject, area, remarks, looking_for, use_myself_as, vehicle_name, model, registration_no, capacity, rate_per_hr_km_day, journey_destination, yourself;
    private String value;
    private ArrayList<String> columnValue;
    private Button materialButton;
    private HomeFragment homeFragment;
    private ProgressDialog progressDialog;
    private TextInputEditText title;
    private ImageButton back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csv, container, false);
        columnValue = new ArrayList<String>();
        materialButton = view.findViewById(R.id.save);
        homeFragment = new HomeFragment();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Saving......");
        title = view.findViewById(R.id.title);


   /*
        Now for Variable Init for others
         */


        journey_mode = view.findViewById(R.id.journey_mode);
        rider_mode = view.findViewById(R.id.rider_mode);
        co_working_mode = view.findViewById(R.id.co_working_mode);
        mode = view.findViewById(R.id.mode);
        marital_offer = view.findViewById(R.id.marital_offer);
        marital_status = view.findViewById(R.id.marital_status);
        donate_blood = view.findViewById(R.id.donate_blood);
        blood_group = view.findViewById(R.id.blood_group);
        gender = view.findViewById(R.id.gender);
        date_of_birth = view.findViewById(R.id.date_of_birth);
        //  user_name = view.findViewById(R.id.user_name);  //fixed
        key_word = view.findViewById(R.id.key_word);    //fixed


        father_name = view.findViewById(R.id.father_name);
        mother_name = view.findViewById(R.id.mother_name);
        name = view.findViewById(R.id.name);
        nationality = view.findViewById(R.id.nationality);
        religion = view.findViewById(R.id.religion);
        profession = view.findViewById(R.id.profession);
        telephone = view.findViewById(R.id.telephone);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);


        fax = view.findViewById(R.id.fax);
        social_network_id = view.findViewById(R.id.social_network_id);
        present_address = view.findViewById(R.id.present_address);
        country_name = view.findViewById(R.id.country_name);
        school_institute_name = view.findViewById(R.id.school_institute_name);
        school_institute_short_name = view.findViewById(R.id.school_institute_short_name);
        school_exam_name = view.findViewById(R.id.school_exam_name);
        school_subject = view.findViewById(R.id.school_subject);
        school_class = view.findViewById(R.id.school_class);
        school_roll_id_no = view.findViewById(R.id.school_roll_id_no);
        school_passing_year = view.findViewById(R.id.school_passing_year);
        college_institute_name = view.findViewById(R.id.college_institute_name);
        college_institute_short_name = view.findViewById(R.id.college_institute_short_name);
        college_exam_name = view.findViewById(R.id.college_exam_name);
        college_subject = view.findViewById(R.id.college_subject);
        college_roll_id_no = view.findViewById(R.id.college_roll_id_no);
        college_passing_year = view.findViewById(R.id.college_passing_year);
        uni_institute_name = view.findViewById(R.id.uni_institute_name);
        uni_institute_short_name = view.findViewById(R.id.uni_institute_short_name);
        uni_program_name = view.findViewById(R.id.uni_program_name);
        uni_subject = view.findViewById(R.id.uni_subject);
        uni_id_no = view.findViewById(R.id.uni_id_no);
        uni_passing_year = view.findViewById(R.id.uni_passing_year);
        more_institute_name = view.findViewById(R.id.more_institute_name);
        more_institute_short_name = view.findViewById(R.id.more_institute_short_name);
        more_program_name = view.findViewById(R.id.more_program_name);
        more_department_subject = view.findViewById(R.id.more_department_subject);
        more_id_no = view.findViewById(R.id.more_id_no);
        more_passing_year = view.findViewById(R.id.more_passing_year);
        sales_product_name = view.findViewById(R.id.sales_product_name);
        sales_details = view.findViewById(R.id.sales_details);
        professional_organization = view.findViewById(R.id.professional_organization);
        posted_office_name = view.findViewById(R.id.posted_office_name);
        designation = view.findViewById(R.id.designation);
        professional_department = view.findViewById(R.id.professional_department);
        specialized_on = view.findViewById(R.id.specialized_on);
        year_of_experience = view.findViewById(R.id.year_of_experience);
        professional_address = view.findViewById(R.id.professional_address);
        business_organization_name = view.findViewById(R.id.business_organization_name);
        training = view.findViewById(R.id.training);
        services = view.findViewById(R.id.services);
        business_address = view.findViewById(R.id.business_address);
        partner_subject = view.findViewById(R.id.partner_subject);
        area = view.findViewById(R.id.area);
        remarks = view.findViewById(R.id.remarks);
        looking_for = view.findViewById(R.id.looking_for);
        use_myself_as = view.findViewById(R.id.use_myself_as);
        vehicle_name = view.findViewById(R.id.vehicle_name);
        model = view.findViewById(R.id.model);


        registration_no = view.findViewById(R.id.registration_no);
        capacity = view.findViewById(R.id.capacity);
        rate_per_hr_km_day = view.findViewById(R.id.rate_per_hr_km_day);
        journey_destination = view.findViewById(R.id.journey_destination);

        CheckAllClicked();

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                if (!TextUtils.isEmpty(title.getText().toString())) {
                        if(columnValue.size() > 0) {
                            InsertDb insertDb = new InsertDb(); //search query
                            insertDb.execute();
                        }else {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Select Please !", Toast.LENGTH_SHORT).show();

                        }
                } else {

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Please Enter the Title", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back = view.findViewById(R.id.back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                fragmentTransaction.addToBackStack("my_fragment").commit();
            }
        });
        return view;
    }

    //for checking all the click
    private void CheckAllClicked() {

        CheckBoxClicked(key_word);
        CheckBoxClicked(father_name);
        CheckBoxClicked(mother_name);
        CheckBoxClicked(name);
        CheckBoxClicked(nationality);
        CheckBoxClicked(religion);
        CheckBoxClicked(profession);
        CheckBoxClicked(telephone);
        CheckBoxClicked(email);
        CheckBoxClicked(mobile);
        CheckBoxClicked(fax);
        CheckBoxClicked(social_network_id);
        CheckBoxClicked(present_address);
        CheckBoxClicked(country_name);
        CheckBoxClicked(school_institute_name);
        CheckBoxClicked(school_institute_short_name);
        CheckBoxClicked(school_exam_name);
        CheckBoxClicked(school_subject);
        CheckBoxClicked(school_class);
        CheckBoxClicked(school_roll_id_no);
        CheckBoxClicked(school_passing_year);
        CheckBoxClicked(college_institute_name);
        CheckBoxClicked(college_institute_short_name);
        CheckBoxClicked(college_exam_name);
        CheckBoxClicked(college_subject);
        CheckBoxClicked(college_roll_id_no);
        CheckBoxClicked(college_passing_year);
        CheckBoxClicked(uni_institute_name);
        CheckBoxClicked(uni_institute_short_name);
        CheckBoxClicked(uni_program_name);
        CheckBoxClicked(uni_subject);
        CheckBoxClicked(uni_program_name);
        CheckBoxClicked(uni_id_no);
        CheckBoxClicked(uni_passing_year);
        CheckBoxClicked(more_institute_name);
        CheckBoxClicked(more_institute_short_name);
        CheckBoxClicked(more_program_name);
        CheckBoxClicked(more_department_subject);
        CheckBoxClicked(more_id_no);
        CheckBoxClicked(more_passing_year);
        CheckBoxClicked(sales_product_name);
        CheckBoxClicked(sales_details);
        CheckBoxClicked(professional_organization);
        CheckBoxClicked(posted_office_name);
        CheckBoxClicked(designation);
        CheckBoxClicked(professional_department);
        CheckBoxClicked(specialized_on);
        CheckBoxClicked(year_of_experience);
        CheckBoxClicked(professional_address);
        CheckBoxClicked(business_organization_name);
        CheckBoxClicked(training);
        CheckBoxClicked(services);
        CheckBoxClicked(business_address);
        CheckBoxClicked(partner_subject);
        CheckBoxClicked(area);
        CheckBoxClicked(remarks);
        CheckBoxClicked(looking_for);
        CheckBoxClicked(use_myself_as);
        CheckBoxClicked(vehicle_name);
        CheckBoxClicked(model);
        CheckBoxClicked(journey_mode);
        CheckBoxClicked(rider_mode);
        CheckBoxClicked(co_working_mode);
        CheckBoxClicked(mode);
        CheckBoxClicked(marital_offer);
        CheckBoxClicked(marital_status);
        CheckBoxClicked(donate_blood);
        CheckBoxClicked(blood_group);
        CheckBoxClicked(gender);
        CheckBoxClicked(date_of_birth);

        CheckBoxClicked(registration_no);
        CheckBoxClicked(capacity);
        CheckBoxClicked(rate_per_hr_km_day);
        CheckBoxClicked(journey_destination);

    }

    private void CheckBoxClicked(final CheckBox name) {

        name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    String column_name = getResources().getResourceEntryName(name.getId());
                    String value = name.getText().toString();
                    Log.d("value",value);
                    String column_value = column_name + "=" + name.getText().toString();
                    columnValue.add(column_value);
                }
            }
        });
    }


    //after marging all we will get the value
    private String checkStatus() {
        StringBuilder out = new StringBuilder();
        for (Object o : columnValue) {
            out.append("&" + o.toString());
        }
        Log.d("a", out.toString());
        return out.toString();
    }

    ///inserdata
    public class InsertDb extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {

                url = new URL("http://search-me.xyz/searchme/multisearch/multi.php?title="+title.getText().toString()+"&mac_address=" + DeviceClass.getMacAddress() + checkStatus());
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

//                JSONArray jsonArray = new JSONArray(myFile);
//                for (int x = 0; x < jsonArray.length(); x++) {
//                    String na = jsonArray.getJSONObject(x).getString("name");
//                    String pro = jsonArray.getJSONObject(x).getString("profession");
//                    String con = jsonArray.getJSONObject(x).getString("country_name");
//                    String mob = jsonArray.getJSONObject(x).getString("mobile");
//                    String img = jsonArray.getJSONObject(x).getString("img_url");
//                    String lat = jsonArray.getJSONObject(x).getString("current_latitude");
//                    String lon = jsonArray.getJSONObject(x).getString("current_longitude");
//                    profile_modes.add(new profile_mode(na, pro, con, mob, img, lat, lon));
//                }
                return myFile;


            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            textView.setVisibility(View.VISIBLE);
//            textView.setText(s);
//            columnValue.clear();
            progressDialog.dismiss();

            // Log.d("size", String.valueOf(profile_modes.size()));
            Toast.makeText(getActivity(), "Information Saved!", Toast.LENGTH_SHORT).show();


            SetFragment(homeFragment);
        }
    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();

    }

}
