package com.example.espon.farklenation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class activity_input extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Dice_Values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setSelection(6);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(6);

        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        spinner3.setSelection(6);

        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);
        spinner4.setSelection(6);

        final Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter);
        spinner5.setSelection(6);

        final Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter);
        spinner6.setSelection(6);

        Button runButton = (Button) findViewById(R.id.button);
        runButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] nameSettings = {"WinTotal","FirstBankInput","EachBankInput","OneOneInput","OneFiveInput","ThreeOnesInput","ThreeTwosInput","ThreeThreesInput","ThreeFoursInput","ThreeFivesInput","ThreeSixsInput","FourInput", "FiveInput","SixInput","PairsInput","TripletsInput","StraightInput"};
                String[] pointSettings = new String[nameSettings.length];
                TextView startTotal = (TextView) findViewById(R.id.editText2);
                TextView currTotal = (TextView) findViewById(R.id.editText);
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
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
                int[] dice = {0,0,0,0,0,0};
                if(!spinner1.getSelectedItem().toString().equals("N/A")){
                    dice[Integer.parseInt(spinner1.getSelectedItem().toString())-1]++;
                }
                if(!spinner2.getSelectedItem().toString().equals("N/A")){
                    dice[Integer.parseInt(spinner2.getSelectedItem().toString())-1]++;
                }
                if(!spinner3.getSelectedItem().toString().equals("N/A")){
                    dice[Integer.parseInt(spinner3.getSelectedItem().toString())-1]++;
                }
                if(!spinner4.getSelectedItem().toString().equals("N/A")){
                    dice[Integer.parseInt(spinner4.getSelectedItem().toString())-1]++;
                }
                if(!spinner5.getSelectedItem().toString().equals("N/A")){
                    dice[Integer.parseInt(spinner5.getSelectedItem().toString())-1]++;
                }
                if(!spinner6.getSelectedItem().toString().equals("N/A")){
                    dice[Integer.parseInt(spinner6.getSelectedItem().toString())-1]++;
                }
                TextView outTextView = (TextView) findViewById(R.id.Output);
                if(startTotal.getText().toString().length()==0||currTotal.getText().toString().length()==0){
                    outTextView.setText("Error: Please input both current total for turn and current bank total");
                }
                else{
                    String output = Farkle.getOutput(nameSettings,pointSettings,Integer.parseInt(startTotal.getText().toString()), Integer.parseInt(currTotal.getText().toString()), dice);
                    outTextView.setText(output);
                }


            }
        });
    }
}
