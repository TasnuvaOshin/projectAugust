package apps.searchme.sm.ShowResultUserProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import apps.searchme.sm.Main2Activity;
import apps.searchme.sm.R;
import apps.searchme.sm.Util.DeviceClass;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int SELECT_PICTURES = 11;
    private static final String MY_PREFS_NAME = "email";
    private LinearLayout linearLayout, edu_linearLayout, con_linearLayout, buy_linearLayout, professional_linearLayout, publish_linearLayout, coworking_linearLayout, lookingfor_linearLayout, usemyself_linearLayout, riding_linearLayout, journey_linearLayout, express_linearLayout;
    private ImageButton imageButton, edu_imageButton, con_imageButton, buy_imageButton, professional_imageButton, publish_imageButton, coworking_imageButton, lookingfor_imageButton, usemyself_imageButton, riding_imageButton, journey_imageButton, express_imageButton;
    private int count1st = 0;
    private ImageView imageView;
    DeviceClass deviceClass = new DeviceClass();
    ArrayList<String> columnValue;
    private TextView textView;
    private ProgressDialog progressDialog;

    private FloatingActionButton add;
    private DatabaseReference databaseReference;




    /*
    Initializing all the spinner variable
     */


    private TextInputEditText date_of_birth, gender, blood_group, donate_blood, marital_status, marital_offer, mode, co_working_mode, rider_mode, journey_mode;
    private ImageButton Select_date;
    private Spinner gender_spinner, blood_group_spinner, donate_blood_spinner, marital_status_spinner, marital_offer_spinner, mode_spinner, co_working_mode_spinner, rider_mode_spinner, journey_mode_spinner;

    /*

    init the core variable
     */

    private TextInputEditText user_name, key_word, father_name, mother_name, name, nationality, religion, profession, telephone, email, fax, mobile, social_network_id, present_address, country_name, school_institute_name, school_institute_short_name, school_exam_name, school_subject, school_class, school_roll_id_no, school_passing_year, college_institute_name, college_institute_short_name, college_exam_name, college_subject, college_roll_id_no, college_passing_year, uni_institute_name, uni_institute_short_name, uni_program_name, uni_subject, uni_id_no, uni_passing_year, more_institute_name, more_institute_short_name, more_program_name, more_department_subject, more_id_no, more_passing_year, sales_product_name, sales_details, professional_organization, posted_office_name, designation, professional_department, specialized_on, year_of_experience, professional_address, business_organization_name, training, services, business_address, partner_subject, area, remarks, looking_for, use_myself_as, vehicle_name, model, registration_no, capacity, rate_per_hr_km_day, journey_destination, yourself;


    //button initialization

    private Button submit_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("message");
         /*
        variable Initialization starts from here
         */
        linearLayout = findViewById(R.id.personal_info_expand);
        imageButton = findViewById(R.id.ib_personal_info);
        edu_linearLayout = findViewById(R.id.educational_info_expand);
        edu_imageButton = findViewById(R.id.ib_educational_info);
        con_linearLayout = findViewById(R.id.contact_info_expand);
        con_imageButton = findViewById(R.id.ib_contact_info);
        buy_linearLayout = findViewById(R.id.buy_info_expand);
        buy_imageButton = findViewById(R.id.ib_buy_info);
        professional_linearLayout = findViewById(R.id.professional_info_expand);
        professional_imageButton = findViewById(R.id.ib_professional_info);
        publish_linearLayout = findViewById(R.id.business_info_expand);
        publish_imageButton = findViewById(R.id.ib_business_info);
        coworking_linearLayout = findViewById(R.id.cowork_info_expand);
        coworking_imageButton = findViewById(R.id.ib_cowork_info);
        lookingfor_linearLayout = findViewById(R.id.lookingfor_info_expand);
        lookingfor_imageButton = findViewById(R.id.ib_lookingfor_info);
        usemyself_linearLayout = findViewById(R.id.usemyself_info_expand);
        usemyself_imageButton = findViewById(R.id.ib_usemyself_info);
        riding_linearLayout = findViewById(R.id.riding_info_expand);
        riding_imageButton = findViewById(R.id.ib_riding_info);
        journey_linearLayout = findViewById(R.id.journeypartner_info_expand);
        journey_imageButton = findViewById(R.id.ib_journeypartner_info);
        express_linearLayout = findViewById(R.id.express_info_expand);
        express_imageButton = findViewById(R.id.ib_express_info);

        ExpandTheView(imageButton, linearLayout);
        ExpandTheView(edu_imageButton, edu_linearLayout);
        ExpandTheView(con_imageButton, con_linearLayout);
        ExpandTheView(buy_imageButton, buy_linearLayout);
        ExpandTheView(professional_imageButton, professional_linearLayout);
        ExpandTheView(publish_imageButton, publish_linearLayout);
        ExpandTheView(coworking_imageButton, coworking_linearLayout);
        ExpandTheView(lookingfor_imageButton, lookingfor_linearLayout);
        ExpandTheView(usemyself_imageButton, usemyself_linearLayout);
        ExpandTheView(riding_imageButton, riding_linearLayout);
        ExpandTheView(journey_imageButton, journey_linearLayout);
        ExpandTheView(express_imageButton, express_linearLayout);

        /*
        Now for Variable Init for spinner
         */


        date_of_birth = findViewById(R.id.date_of_birth);
        Select_date = findViewById(R.id.datepicker);

        gender = findViewById(R.id.gender);
        gender_spinner = findViewById(R.id.gender_spinner);

        blood_group = findViewById(R.id.blood_group);
        blood_group_spinner = findViewById(R.id.blood_group_spinner);

        donate_blood = findViewById(R.id.donate_blood);
        donate_blood_spinner = findViewById(R.id.donate_blood_spinner);

        marital_status = findViewById(R.id.marital_status);
        marital_status_spinner = findViewById(R.id.marital_status_spinner);

        marital_offer = findViewById(R.id.marital_offer);
        marital_offer_spinner = findViewById(R.id.marital_offer_spinner);

        mode = findViewById(R.id.mode);
        mode_spinner = findViewById(R.id.mode_spinner);

        co_working_mode = findViewById(R.id.co_working_mode);
        co_working_mode_spinner = findViewById(R.id.co_working_mode_spinner);

        rider_mode = findViewById(R.id.rider_mode);
        rider_mode_spinner = findViewById(R.id.rider_mode_spinner);

        journey_mode = findViewById(R.id.journey_mode);
        journey_mode_spinner = findViewById(R.id.journey_mode_spinner);

        SetSpinner(gender, gender_spinner, R.array.gender);
        SetSpinner(blood_group, blood_group_spinner, R.array.bloodGroup);
        SetSpinner(donate_blood, donate_blood_spinner, R.array.donateBlood);
        SetSpinner(marital_status, marital_status_spinner, R.array.maritalStatus);
        SetSpinner(marital_offer, marital_offer_spinner, R.array.maritalOffer);
        SetSpinner(mode, mode_spinner, R.array.mode);
        SetSpinner(co_working_mode, co_working_mode_spinner, R.array.coworkingmode);
        SetSpinner(rider_mode, rider_mode_spinner, R.array.ridermode);
        SetSpinner(journey_mode, journey_mode_spinner, R.array.journeymode);






        /*
        Now for Variable Init for others
         */


        user_name = findViewById(R.id.user_name);  //fixed
        key_word = findViewById(R.id.key_word);    //fixed
        father_name = findViewById(R.id.father_name);
        mother_name = findViewById(R.id.mother_name);
        name = findViewById(R.id.name);
        nationality = findViewById(R.id.nationality);
        religion = findViewById(R.id.religion);
        profession = findViewById(R.id.profession);
        telephone = findViewById(R.id.telephone);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        fax = findViewById(R.id.fax);
        social_network_id = findViewById(R.id.social_network_id);
        present_address = findViewById(R.id.present_address);
        country_name = findViewById(R.id.country_name);
        school_institute_name = findViewById(R.id.school_institute_name);
        school_institute_short_name = findViewById(R.id.school_institute_short_name);
        school_exam_name = findViewById(R.id.school_exam_name);
        school_subject = findViewById(R.id.school_subject);
        school_class = findViewById(R.id.school_class);
        school_roll_id_no = findViewById(R.id.school_roll_id_no);
        school_passing_year = findViewById(R.id.school_passing_year);
        college_institute_name = findViewById(R.id.college_institute_name);
        college_institute_short_name = findViewById(R.id.college_institute_short_name);
        college_exam_name = findViewById(R.id.college_exam_name);
        college_subject = findViewById(R.id.college_subject);
        college_roll_id_no = findViewById(R.id.college_roll_id_no);
        college_passing_year = findViewById(R.id.college_passing_year);
        uni_institute_name = findViewById(R.id.uni_institute_name);
        uni_institute_short_name = findViewById(R.id.uni_institute_short_name);
        uni_program_name = findViewById(R.id.uni_program_name);
        uni_subject = findViewById(R.id.uni_subject);
        uni_id_no = findViewById(R.id.uni_id_no);
        uni_passing_year = findViewById(R.id.uni_passing_year);
        more_institute_name = findViewById(R.id.more_institute_name);
        more_institute_short_name = findViewById(R.id.more_institute_short_name);
        more_program_name = findViewById(R.id.more_program_name);
        more_department_subject = findViewById(R.id.more_department_subject);
        more_id_no = findViewById(R.id.more_id_no);
        more_passing_year = findViewById(R.id.more_passing_year);
        sales_product_name = findViewById(R.id.sales_product_name);
        sales_details = findViewById(R.id.sales_details);
        professional_organization = findViewById(R.id.professional_organization);
        posted_office_name = findViewById(R.id.posted_office_name);
        designation = findViewById(R.id.designation);
        professional_department = findViewById(R.id.professional_department);
        specialized_on = findViewById(R.id.specialized_on);
        year_of_experience = findViewById(R.id.year_of_experience);
        professional_address = findViewById(R.id.professional_address);
        business_organization_name = findViewById(R.id.business_organization_name);
        training = findViewById(R.id.training);
        services = findViewById(R.id.services);
        business_address = findViewById(R.id.business_address);
        partner_subject = findViewById(R.id.partner_subject);
        area = findViewById(R.id.area);
        remarks = findViewById(R.id.remarks);
        looking_for = findViewById(R.id.looking_for);
        use_myself_as = findViewById(R.id.use_myself_as);
        vehicle_name = findViewById(R.id.vehicle_name);
        model = findViewById(R.id.model);
        registration_no = findViewById(R.id.registration_no);
        capacity = findViewById(R.id.capacity);
        rate_per_hr_km_day = findViewById(R.id.rate_per_hr_km_day);
        journey_destination = findViewById(R.id.journey_destination);
        yourself = findViewById(R.id.yourself);

        textView = findViewById(R.id.tv_error);

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Your Profile is Updating");
        /*
        profile init the edittext and the image
         */
        columnValue = new ArrayList<String>();

        imageView = findViewById(R.id.iv_profile_picture);      //image fixed
        submit_download = findViewById(R.id.submit_download);


        Select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dob = (day + "/" + (month + 1) + "/" + year);
                        date_of_birth.setText(dob);
                    }
                }, day, month, year);

                datePickerDialog.show();


            }
        });


        getProfile getProfile = new getProfile();
        getProfile.execute();

        // Toast.makeText(ProfileActivity.this, ""+mac_address, Toast.LENGTH_SHORT).show();

        add = findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//now insert it into db and check the number
                InsertDb InsertDb = new InsertDb();
                InsertDb.execute();


            }
        });


    }


    ///////////////////chat box work


    public class InsertDb extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;

            try {

                Random random = new Random();
                int roomno = random.nextInt(9900) + 100;
                String emails = email.getText().toString();
                String names = user_name.getText().toString();

                SharedPreferences myPrefs = ProfileActivity.this.getSharedPreferences("myPrefs", MODE_PRIVATE);
                String myemail = myPrefs.getString("MEM1", "");
                String myname = myPrefs.getString("MEM2", "");
                Log.d("a", myemail + " /" + myname);
                url = new URL("http://search-me.xyz/searchme/chat/chat.php?room_name=" + roomno + "&user_one=" + myemail + "&user_two=" + emails + "&name_one=" + myname + "&name_two=" + names);
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
                JSONObject jsonObject = new JSONObject(myFile);
                String value = jsonObject.getString("value");
                String code = jsonObject.getString("code");
                if (code.equals("201")) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("msg", "Welcome To Chat");
                    hashMap.put("name", "Write Your Message");
                    databaseReference.child(String.valueOf(roomno)).push().setValue(hashMap);
                    return value;

                } else {

                    return value;
                }


            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            textView.setVisibility(View.VISIBLE);
            textView.setText(s);
            columnValue.clear();

        }
    }


    /////////////////////
    //this is for setting up the spinner
    private void SetSpinner(final TextInputEditText gender, Spinner gender_spinner, int gender1) {

        @SuppressLint({"NewApi", "LocalSuppress"}) ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(ProfileActivity.this), gender1, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(arrayAdapter);
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    gender.setText((CharSequence) adapterView.getItemAtPosition(i));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /*
    Expandable View Code
     */
    private void ExpandTheView(final ImageButton imageButton, final LinearLayout linearLayout) {

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count1st == 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                    imageButton.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    count1st++;
                } else {

                    linearLayout.setVisibility(View.GONE);
                    imageButton.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    count1st = 0;
                }
            }
        });

    }


    //profile Information and fillup with data :

    @SuppressLint("StaticFieldLeak")
    public class getProfile extends AsyncTask<String, String, JSONObject> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String mainfile;

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                user_name.setText(jsonObject.getString("user_name"));
                key_word.setText(jsonObject.getString("key_word"));
                name.setText(jsonObject.getString("name"));
                father_name.setText(jsonObject.getString("father_name"));
                mother_name.setText(jsonObject.getString("mother_name"));
                date_of_birth.setText(jsonObject.getString("date_of_birth"));
                gender.setText(jsonObject.getString("gender"));
                nationality.setText(jsonObject.getString("nationality"));
                religion.setText(jsonObject.getString("religion"));
                blood_group.setText(jsonObject.getString("blood_group"));
                donate_blood.setText(jsonObject.getString("donate_blood"));
                marital_status.setText(jsonObject.getString("marital_status"));
                marital_offer.setText(jsonObject.getString("marital_offer"));
                profession.setText(jsonObject.getString("profession"));
                mobile.setText(jsonObject.getString("mobile"));
                telephone.setText(jsonObject.getString("telephone"));
                email.setText(jsonObject.getString("email"));
                fax.setText(jsonObject.getString("fax"));
                social_network_id.setText(jsonObject.getString("social_network_id"));
                present_address.setText(jsonObject.getString("present_address"));
                country_name.setText(jsonObject.getString("country_name"));
                school_institute_name.setText(jsonObject.getString("school_institute_name"));
                school_institute_short_name.setText(jsonObject.getString("school_institute_short_name"));
                school_exam_name.setText(jsonObject.getString("school_exam_name"));
                school_subject.setText(jsonObject.getString("school_subject"));
                school_class.setText(jsonObject.getString("school_class"));
                school_roll_id_no.setText(jsonObject.getString("school_roll_id_no"));
                school_passing_year.setText(jsonObject.getString("school_passing_year"));
                college_institute_name.setText(jsonObject.getString("college_institute_name"));
                college_institute_short_name.setText(jsonObject.getString("college_institute_short_name"));
                college_exam_name.setText(jsonObject.getString("college_exam_name"));
                college_subject.setText(jsonObject.getString("college_subject"));
                college_roll_id_no.setText(jsonObject.getString("college_roll_id_no"));
                college_passing_year.setText(jsonObject.getString("college_passing_year"));
                uni_institute_name.setText(jsonObject.getString("uni_institute_name"));
                uni_institute_short_name.setText(jsonObject.getString("uni_institute_short_name"));
                uni_program_name.setText(jsonObject.getString("uni_program_name"));
                uni_subject.setText(jsonObject.getString("uni_subject"));
                uni_id_no.setText(jsonObject.getString("uni_id_no"));
                uni_passing_year.setText(jsonObject.getString("uni_passing_year"));
                more_institute_name.setText(jsonObject.getString("more_institute_name"));
                more_institute_short_name.setText(jsonObject.getString("more_institute_short_name"));
                more_program_name.setText(jsonObject.getString("more_program_name"));
                more_department_subject.setText(jsonObject.getString("more_department_subject"));
                more_id_no.setText(jsonObject.getString("more_id_no"));
                more_passing_year.setText(jsonObject.getString("more_passing_year"));
                mode.setText(jsonObject.getString("mode"));
                sales_product_name.setText(jsonObject.getString("sales_product_name"));
                sales_details.setText(jsonObject.getString("sales_details"));
                professional_organization.setText(jsonObject.getString("professional_organization"));
                posted_office_name.setText(jsonObject.getString("posted_office_name"));
                designation.setText(jsonObject.getString("designation"));
                professional_department.setText(jsonObject.getString("professional_department"));
                specialized_on.setText(jsonObject.getString("specialized_on"));
                year_of_experience.setText(jsonObject.getString("year_of_experience"));
                professional_address.setText(jsonObject.getString("professional_address"));
                business_organization_name.setText(jsonObject.getString("business_organization_name"));
                training.setText(jsonObject.getString("training"));
                services.setText(jsonObject.getString("services"));
                business_address.setText(jsonObject.getString("business_address"));
                co_working_mode.setText(jsonObject.getString("co_working_mode"));
                partner_subject.setText(jsonObject.getString("partner_subject"));
                area.setText(jsonObject.getString("area"));
                remarks.setText(jsonObject.getString("remarks"));
                looking_for.setText(jsonObject.getString("looking_for"));
                use_myself_as.setText(jsonObject.getString("use_myself_as"));
                rider_mode.setText(jsonObject.getString("rider_mode"));
                vehicle_name.setText(jsonObject.getString("vehicle_name"));
                model.setText(jsonObject.getString("model"));
                registration_no.setText(jsonObject.getString("registration_no"));
                capacity.setText(jsonObject.getString("capacity"));
                rate_per_hr_km_day.setText(jsonObject.getString("rate_per_hr_km_day"));
                journey_mode.setText(jsonObject.getString("journey_mode"));
                journey_destination.setText(jsonObject.getString("journey_destination"));
                yourself.setText(jsonObject.getString("yourself"));


                Picasso.get().load("https://tasnuvaoshin.com/searchme/img/" + jsonObject.getString("img_url")).fit().into(imageView);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                //endpoint
                String mac_address = getIntent().getStringExtra("mac_address");

                URL url = new URL("http://search-me.xyz/searchme/profile/get_profile.php?mac_address=" + mac_address);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = " ";
                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line);


                }

                mainfile = stringBuffer.toString();


                JSONArray parent = new JSONArray(mainfile);


                JSONObject child = parent.getJSONObject(0);
                //because we know there can be only one index for each result
//                    String name = child.getString("user_name");
//                    String img = child.getString("user_img");
//                    String description = child.getString("user_description");

                return child;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


    //Spinner Setup


}
