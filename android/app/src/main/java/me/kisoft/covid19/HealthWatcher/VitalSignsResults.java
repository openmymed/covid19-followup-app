package me.kisoft.covid19.HealthWatcher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.kisoft.covid19.CreateProfileActivity;
import me.kisoft.covid19.R;
import me.kisoft.covid19.RegisterActivity;
import me.kisoft.covid19.models.Patient;
import me.kisoft.covid19.models.Vitals;
import me.kisoft.covid19.services.PatientService;
import me.kisoft.covid19.services.PatientServiceDelegate;

public class VitalSignsResults extends AppCompatActivity {
    Vitals vitals;
    private String user, Date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    java.util.Date today = Calendar.getInstance().getTime();
    int VBP1, VBP2, VRR, VHR, VO2;
    private PatientService service;

    @Override
    protected void onStart() {
        service = new PatientServiceDelegate();
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs_results);

        Date = df.format(today);
        TextView VSRR = this.findViewById(R.id.RRV);
        TextView VSBPS = this.findViewById(R.id.BP2V);
        TextView VSHR = this.findViewById(R.id.HRV);
        TextView VSO2 = this.findViewById(R.id.O2V);
        ImageButton All = this.findViewById(R.id.SendAll);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            VRR = bundle.getInt("breath");
            VHR = bundle.getInt("bpm");
            VBP1 = bundle.getInt("SP");
            VBP2 = bundle.getInt("DP");
            VO2 = bundle.getInt("O2R");
            vitals = new Vitals(VRR,VHR,VBP1,VBP2,VO2);
            postVitalsResults(vitals);
            user = bundle.getString("Usr");
            VSRR.setText(String.valueOf(VRR));
            VSHR.setText(String.valueOf(VHR));
            VSBPS.setText(VBP1 + " / " + VBP2);
            VSO2.setText(String.valueOf(VO2));
        }

        All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
                i.putExtra(Intent.EXTRA_TEXT, user + "'s new measuerment " + "\n" + " at " + Date + " are :" + "\n" + "Heart Rate = " + VHR + "\n" + "Blood Pressure = " + VBP1 + " / " + VBP2 + "\n" + "Respiration Rate = " + VRR + "\n" + "Oxygen Saturation = " + VO2);
                try {
                    VitalSignsResults.this.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(VitalSignsResults.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

 /*   @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(VitalSignsResults.this, HealthWatcherActivity.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }*/
 private void postVitalsResults(final Vitals vitals) {
     new AsyncTask<Void, Void, Boolean>() {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
         }

         @Override
         protected Boolean doInBackground(Void... voids) {
             Boolean result = service.postVitals(vitals);
             if (result) {
                 Log.i("PostVital","PostVital is sucessful");
             } else {
                 Log.i("PostVital","PostVital is sucessful");
             }
             return result;
         }

         @Override
         protected void onPostExecute(Boolean result) {
             if (result) {
                 Log.i("PostVital","PostVital is sucessful");
             } else {
                 Log.i("PostVital","PostVital is sucessful");

                 //TODO Error handiling in post
             /*    tvRegisterWarning.setText(R.string.error_sign_up);
                 tvRegisterWarning.setVisibility(View.VISIBLE);*/
             }
             super.onPostExecute(result);
         }
     }.execute();
 }
}