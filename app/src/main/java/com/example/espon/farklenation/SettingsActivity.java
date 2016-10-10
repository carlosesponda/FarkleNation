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

        WinTotalInput.setText(sharedPref.getString(activity_input.nameSettings[0], Integer.toString(activity_input.defaultPointVals[0])));
        FirstBankInput.setText(sharedPref.getString(activity_input.nameSettings[1], Integer.toString(activity_input.defaultPointVals[1])));
        EachBankInput.setText(sharedPref.getString(activity_input.nameSettings[2], Integer.toString(activity_input.defaultPointVals[2])));
        OneOneInput.setText(sharedPref.getString(activity_input.nameSettings[3], Integer.toString(activity_input.defaultPointVals[3])));
        OneFiveInput.setText(sharedPref.getString(activity_input.nameSettings[4], Integer.toString(activity_input.defaultPointVals[4])));
        ThreeOnesInput.setText(sharedPref.getString(activity_input.nameSettings[5], Integer.toString(activity_input.defaultPointVals[5])));
        ThreeTwosInput.setText(sharedPref.getString(activity_input.nameSettings[6], Integer.toString(activity_input.defaultPointVals[6])));
        ThreeThreesInput.setText(sharedPref.getString(activity_input.nameSettings[7], Integer.toString(activity_input.defaultPointVals[7])));
        ThreeFoursInput.setText(sharedPref.getString(activity_input.nameSettings[8], Integer.toString(activity_input.defaultPointVals[8])));
        ThreeFivesInput.setText(sharedPref.getString(activity_input.nameSettings[9], Integer.toString(activity_input.defaultPointVals[9])));
        ThreeSixsInput.setText(sharedPref.getString(activity_input.nameSettings[10], Integer.toString(activity_input.defaultPointVals[10])));
        FourInput.setText(sharedPref.getString(activity_input.nameSettings[11], Integer.toString(activity_input.defaultPointVals[11])));
        FiveInput.setText(sharedPref.getString(activity_input.nameSettings[12], Integer.toString(activity_input.defaultPointVals[12]));
        SixInput.setText(sharedPref.getString(activity_input.nameSettings[13], Integer.toString(activity_input.defaultPointVals[13])));
        PairsInput.setText(sharedPref.getString(activity_input.nameSettings[14], Integer.toString(activity_input.defaultPointVals[14])));
        TripletsInput.setText(sharedPref.getString(activity_input.nameSettings[15], Integer.toString(activity_input.defaultPointVals[15])));
        StraightInput.setText(sharedPref.getString(activity_input.nameSettings[16], Integer.toString(activity_input.defaultPointVals[16])));

        final Button button = (Button) findViewById(R.id.resetSpecialButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                TextView WinTotalInput = (TextView) findViewById(R.id.WinTotalInput);
                TextView FirstBankInput = (TextView) findViewById(R.id.FirstBankInput);
                TextView EachBankInput = (TextView) findViewById(R.id.EachBankInput);
                WinTotalInput.setText(Integer.toString(activity_input.defaultPointVals[0]));
                FirstBankInput.setText(Integer.toString(activity_input.defaultPointVals[1]));
                EachBankInput.setText(Integer.toString(activity_input.defaultPointVals[2]));
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
                OneOneInput.setText(Integer.toString(activity_input.defaultPointVals[3]));
                OneFiveInput.setText(Integer.toString(activity_input.defaultPointVals[4]));
                ThreeOnesInput.setText(Integer.toString(activity_input.defaultPointVals[5]));
                ThreeTwosInput.setText(Integer.toString(activity_input.defaultPointVals[6]));
                ThreeThreesInput.setText(Integer.toString(activity_input.defaultPointVals[7]));
                ThreeFoursInput.setText(Integer.toString(activity_input.defaultPointVals[8]));
                ThreeFivesInput.setText(Integer.toString(activity_input.defaultPointVals[9]));
                ThreeSixsInput.setText(Integer.toString(activity_input.defaultPointVals[10]));
                FourInput.setText(Integer.toString(activity_input.defaultPointVals[11]));
                FiveInput.setText(Integer.toString(activity_input.defaultPointVals[12]));
                SixInput.setText(Integer.toString(activity_input.defaultPointVals[13]));
                PairsInput.setText(Integer.toString(activity_input.defaultPointVals[14]));
                TripletsInput.setText(Integer.toString(activity_input.defaultPointVals[15]));
                StraightInput.setText(Integer.toString(activity_input.defaultPointVals[16]));
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
        editor.putString(activity_input.nameSettings[0], WinTotalInput.getText().toString());
        editor.putString(activity_input.nameSettings[1], FirstBankInput.getText().toString());
        editor.putString(activity_input.nameSettings[2] EachBankInput.getText().toString());
        editor.putString(activity_input.nameSettings[3], OneOneInput.getText().toString());
        editor.putString(activity_input.nameSettings[4], OneFiveInput.getText().toString());
        editor.putString(activity_input.nameSettings[5], ThreeOnesInput.getText().toString());
        editor.putString(activity_input.nameSettings[6], ThreeTwosInput.getText().toString());
        editor.putString(activity_input.nameSettings[7], ThreeThreesInput.getText().toString());
        editor.putString(activity_input.nameSettings[8], ThreeFoursInput.getText().toString());
        editor.putString(activity_input.nameSettings[9], ThreeFivesInput.getText().toString());
        editor.putString(activity_input.nameSettings[10], ThreeSixsInput.getText().toString());
        editor.putString(activity_input.nameSettings[11], FourInput.getText().toString());
        editor.putString(activity_input.nameSettings[12], FiveInput.getText().toString());
        editor.putString(activity_input.nameSettings[13], SixInput.getText().toString());
        editor.putString(activity_input.nameSettings[14], PairsInput.getText().toString());
        editor.putString(activity_input.nameSettings[15], TripletsInput.getText().toString());
        editor.putString(activity_input.nameSettings[16], StraightInput.getText().toString());

        for (int i = 0; i < num_settings; ++i) {
            pointSettings[i] = Integer.parseInt(
                sharedPref.getString(nameSettings[i], Integer.toString(defaultPointVals[i]))
                );
        }

        //Farkle.getTable(nameSettings, pointSettings);
        editor.commit();

    }
}