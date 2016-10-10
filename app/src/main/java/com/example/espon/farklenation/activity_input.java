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
    public final static String[] nameSettings = {
        "WinTotal", "FirstBankInput", "EachBankInput", "OneOneInput",
        "OneFiveInput", "ThreeOnesInput", "ThreeTwosInput", "ThreeThreesInput",
        "ThreeFoursInput", "ThreeFivesInput","ThreeSixsInput", "FourInput", 
        "FiveInput", "SixInput", "PairsInput", "TripletsInput",
        "StraightInput"
    };
    public final static int[] defaultPointVals = { 
        10000, 500, 0, 100, 
        50, 300, 200, 300, 
        400, 500, 600, 1000, 
        2000, 3000, 1500, 2500, 
        1500 
    };

    public final static int NUM_SETTINGS = nameSettings.length;

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
                TextView startTotal = (TextView) findViewById(R.id.editText2);
                TextView currTotal = (TextView) findViewById(R.id.editText);
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                int[] pointSettings = new int[NUM_SETTINGS];
                for (int i = 0; i < NUM_SETTINGS; ++i) {
                    pointSettings[i] = Integer.parseInt(
                        sharedPref.getString(nameSettings[i], Integer.toString(defaultPointVals[i]))
                        );
                }

                int[] dice = {0, 0, 0, 0, 0, 0};
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
