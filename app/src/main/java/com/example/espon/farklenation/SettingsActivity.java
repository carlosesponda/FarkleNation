package com.example.espon.farklenation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        TextView WinTotalInput = (TextView) findViewById(R.id.WinTotalInput);
        TextView FirstBankInput = (TextView) findViewById(R.id.FirstBankInput);
        TextView EachBankInput = (TextView) findViewById(R.id.EachBankInput);
        TextView OneOneInput = (TextView) findViewById(R.id.OneOneInput);
        TextView OneFiveInput = (TextView) findViewById(R.id.OneFiveInput);
        TextView ThreeOnesInput = (TextView) findViewById(R.id.ThreeOnesInput);
        TextView ThreeTwosInput = (TextView) findViewById(R.id.ThreeTwosInput);
        TextView ThreeThreesInput = (TextView) findViewById(R.id.ThreeThreesInput);
        TextView ThreeFoursInput = (TextView) findViewById(R.id.ThreeFoursInput);
        TextView ThreeFivesInput = (TextView) findViewById(R.id.ThreeFivesInput);
        TextView ThreeSixsInput = (TextView) findViewById(R.id.ThreeSixsInput);
        TextView FourInput = (TextView) findViewById(R.id.FourInput);
        TextView FiveInput = (TextView) findViewById(R.id.FiveInput);
        TextView SixInput = (TextView) findViewById(R.id.SixInput);
        TextView PairsInput = (TextView) findViewById(R.id.PairsInput);
        TextView TripletsInput = (TextView) findViewById(R.id.TripletsInput);
        TextView StraightInput = (TextView) findViewById(R.id.StraightInput);
        WinTotalInput.setText(sharedPref.getString("WinTotal", "10000"));
        FirstBankInput.setText(sharedPref.getString("FirstBankInput", "500"));
        EachBankInput.setText(sharedPref.getString("EachBankInput", "0"));
        OneOneInput.setText(sharedPref.getString("OneOneInput", "100"));
        OneFiveInput.setText(sharedPref.getString("OneFiveInput", "50"));
        ThreeOnesInput.setText(sharedPref.getString("ThreeOnesInput", "300"));
        ThreeTwosInput.setText(sharedPref.getString("ThreeTwosInput", "200"));
        ThreeThreesInput.setText(sharedPref.getString("ThreeThreesInput", "300"));
        ThreeFoursInput.setText(sharedPref.getString("ThreeFoursInput", "400"));
        ThreeFivesInput.setText(sharedPref.getString("ThreeFivesInput", "500"));
        ThreeSixsInput.setText(sharedPref.getString("ThreeSixsInput", "600"));
        FourInput.setText(sharedPref.getString("FourInput", "1000"));
        FiveInput.setText(sharedPref.getString("FiveInput", "2000"));
        SixInput.setText(sharedPref.getString("SixInput", "3000"));
        PairsInput.setText(sharedPref.getString("PairsInput", "1500"));
        TripletsInput.setText(sharedPref.getString("TripletsInput", "2500"));
        StraightInput.setText(sharedPref.getString("StraightInput", "1500"));

        final Button button = (Button) findViewById(R.id.resetSpecialButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                TextView WinTotalInput = (TextView) findViewById(R.id.WinTotalInput);
                TextView FirstBankInput = (TextView) findViewById(R.id.FirstBankInput);
                TextView EachBankInput = (TextView) findViewById(R.id.EachBankInput);
                WinTotalInput.setText("10000");
                FirstBankInput.setText("500");
                EachBankInput.setText("0");
            }
        });
        final Button button2 = (Button) findViewById(R.id.resetSpecialButton2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                TextView OneOneInput = (TextView) findViewById(R.id.OneOneInput);
                TextView OneFiveInput = (TextView) findViewById(R.id.OneFiveInput);
                TextView ThreeOnesInput = (TextView) findViewById(R.id.ThreeOnesInput);
                TextView ThreeTwosInput = (TextView) findViewById(R.id.ThreeTwosInput);
                TextView ThreeThreesInput = (TextView) findViewById(R.id.ThreeThreesInput);
                TextView ThreeFoursInput = (TextView) findViewById(R.id.ThreeFoursInput);
                TextView ThreeFivesInput = (TextView) findViewById(R.id.ThreeFivesInput);
                TextView ThreeSixsInput = (TextView) findViewById(R.id.ThreeSixsInput);
                TextView FourInput = (TextView) findViewById(R.id.FourInput);
                TextView FiveInput = (TextView) findViewById(R.id.FiveInput);
                TextView SixInput = (TextView) findViewById(R.id.SixInput);
                TextView PairsInput = (TextView) findViewById(R.id.PairsInput);
                TextView TripletsInput = (TextView) findViewById(R.id.TripletsInput);
                TextView StraightInput = (TextView) findViewById(R.id.StraightInput);
                OneOneInput.setText("100");
                OneFiveInput.setText("50");
                ThreeOnesInput.setText("300");
                ThreeTwosInput.setText("200");
                ThreeThreesInput.setText("300");
                ThreeFoursInput.setText("400");
                ThreeFivesInput.setText("500");
                ThreeSixsInput.setText("600");
                FourInput.setText("1000");
                FiveInput.setText("2000");
                SixInput.setText("3000");
                PairsInput.setText("1500");
                TripletsInput.setText("2500");
                StraightInput.setText("1500");
            }
        });
    }
    public void onPause() {
        int a = activity_input.defaultPointVals[0];

        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        TextView WinTotalInput = (TextView) findViewById(R.id.WinTotalInput);
        TextView FirstBankInput = (TextView) findViewById(R.id.FirstBankInput);
        TextView EachBankInput = (TextView) findViewById(R.id.EachBankInput);
        TextView OneOneInput = (TextView) findViewById(R.id.OneOneInput);
        TextView OneFiveInput = (TextView) findViewById(R.id.OneFiveInput);
        TextView ThreeOnesInput = (TextView) findViewById(R.id.ThreeOnesInput);
        TextView ThreeTwosInput = (TextView) findViewById(R.id.ThreeTwosInput);
        TextView ThreeThreesInput = (TextView) findViewById(R.id.ThreeThreesInput);
        TextView ThreeFoursInput = (TextView) findViewById(R.id.ThreeFoursInput);
        TextView ThreeFivesInput = (TextView) findViewById(R.id.ThreeFivesInput);
        TextView ThreeSixsInput = (TextView) findViewById(R.id.ThreeSixsInput);
        TextView FourInput = (TextView) findViewById(R.id.FourInput);
        TextView FiveInput = (TextView) findViewById(R.id.FiveInput);
        TextView SixInput = (TextView) findViewById(R.id.SixInput);
        TextView PairsInput = (TextView) findViewById(R.id.PairsInput);
        TextView TripletsInput = (TextView) findViewById(R.id.TripletsInput);
        TextView StraightInput = (TextView) findViewById(R.id.StraightInput);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("WinTotalInput", WinTotalInput.getText().toString());
        editor.putString("FirstBankInput", FirstBankInput.getText().toString());
        editor.putString("EachBankInput", EachBankInput.getText().toString());
        editor.putString("OneOneInput", OneOneInput.getText().toString());
        editor.putString("OneFiveInput", OneFiveInput.getText().toString());
        editor.putString("ThreeOnesInput", ThreeOnesInput.getText().toString());
        editor.putString("ThreeTwosInput", ThreeTwosInput.getText().toString());
        editor.putString("ThreeThreesInput", ThreeThreesInput.getText().toString());
        editor.putString("ThreeFoursInput", ThreeFoursInput.getText().toString());
        editor.putString("ThreeFivesInput", ThreeFivesInput.getText().toString());
        editor.putString("ThreeSixsInput", ThreeSixsInput.getText().toString());
        editor.putString("FourInput", FourInput.getText().toString());
        editor.putString("FiveInput", FiveInput.getText().toString());
        editor.putString("SixInput", SixInput.getText().toString());
        editor.putString("PairsInput", PairsInput.getText().toString());
        editor.putString("TripletsInput", TripletsInput.getText().toString());
        editor.putString("StraightInput", StraightInput.getText().toString());
        String[] nameSettings = {"winTotal","bankTotal","eachbankTotal","OneOneInput","OneFiveInput","ThreeOnesInput","ThreeTwosInput","ThreeThreesInput","ThreeFoursInput","ThreeFivesInput","ThreeSixsInput","FourInput", "FiveInput","SixInput","PairsInput","TripletsInput","StraightInput"};
        String[] pointSettings = new String[nameSettings.length];
        pointSettings[0] = sharedPref.getString("WinTotal", "10000");
        pointSettings[1] = sharedPref.getString("FirstBankInput", "500");
        pointSettings[2] = sharedPref.getString("EachBankInput", "0");
        pointSettings[3] = sharedPref.getString("OneOneInput", "100");
        pointSettings[4] = sharedPref.getString("OneFiveInput", "50");
        pointSettings[5] = sharedPref.getString("ThreeOnesInput", "300");
        pointSettings[6] = sharedPref.getString("ThreeTwosInput", "200");
        pointSettings[7] = sharedPref.getString("ThreeThreesInput", "300");
        pointSettings[8] = sharedPref.getString("ThreeFoursInput", "400");
        pointSettings[9] = sharedPref.getString("ThreeFivesInput", "500");
        pointSettings[10] = sharedPref.getString("ThreeSixsInput", "600");
        pointSettings[11] = sharedPref.getString("FourInput", "1000");
        pointSettings[12] = sharedPref.getString("FiveInput", "2000");
        pointSettings[13] = sharedPref.getString("SixInput", "3000");
        pointSettings[14] = sharedPref.getString("PairsInput", "1500");
        pointSettings[15] = sharedPref.getString("TripletsInput", "2500");
        pointSettings[16] = sharedPref.getString("StraightInput", "1500");
        //Farkle.getTable(nameSettings, pointSettings);
        editor.commit();

    }
}