package com.example.espon.farklenation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by espon on 10/1/2016.
 */
public class activity_rules extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        TextView FirstBank = (TextView) findViewById(R.id.textView40);
        TextView OneOne = (TextView) findViewById(R.id.textView15);
        TextView OneFive = (TextView) findViewById(R.id.textView16);
        TextView ThreeOnes = (TextView) findViewById(R.id.textView17);
        TextView ThreeTwos = (TextView) findViewById(R.id.textView18);
        TextView ThreeThrees = (TextView) findViewById(R.id.textView19);
        TextView ThreeFours = (TextView) findViewById(R.id.textView20);
        TextView ThreeFives = (TextView) findViewById(R.id.textView21);
        TextView ThreeSixs = (TextView) findViewById(R.id.textView22);
        TextView Four = (TextView) findViewById(R.id.textView23);
        TextView Five = (TextView) findViewById(R.id.textView24);
        TextView Six = (TextView) findViewById(R.id.textView25);
        TextView Straight = (TextView) findViewById(R.id.textView26);
        TextView Pairs = (TextView)findViewById(R.id.textView27);
        TextView Triplets = (TextView)findViewById(R.id.textView30);

        FirstBank.setText("To get on the Score Pad for the first time, you must have a running total of " + sharedPref.getString("FirstBankInput", "500") +  " points before you stop rolling.");
        OneOne.setText("Single 1 = " + sharedPref.getString("OneOneInput","100") );
        OneFive.setText("Single 5 = "+ sharedPref.getString("OneFiveInput","100"));
        ThreeOnes.setText("Three 1's = "+ sharedPref.getString("ThreeOnesInput","300"));
        ThreeTwos.setText("Three 2's = "+ sharedPref.getString("ThreeTwosInput","200"));
        ThreeThrees.setText("Three 3's = "+ sharedPref.getString("ThreeThreesInput","300"));
        ThreeFours.setText("Three 4's = "+ sharedPref.getString("ThreeFoursInput","400"));
        ThreeFives.setText("Three 5's = "+sharedPref.getString("ThreeFivesInput","500"));
        ThreeSixs.setText("Three 6's = "+sharedPref.getString("ThreeSixsInput","600"));
        Four.setText("Four of any number = "+sharedPref.getString("FourInput","1000"));
        Five.setText("Five of any number = "+sharedPref.getString("FiveInput","2000"));
        Six.setText("Six of any number = "+sharedPref.getString("SixInput","3000"));
        Straight.setText("1-6 straight = "+sharedPref.getString("StraightInput","1500") );
        Pairs.setText("Three Pairs = "+sharedPref.getString("PairsInput","1500"));
        Triplets.setText("Two Triplets = "+ sharedPref.getString("TripletsInput","2500"));

    }
}
