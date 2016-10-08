package com.example.espon.farklenation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        /*Button Input = (Button) findViewById(R.id.settings_button);
        Input.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent page1 = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(page1);
            }

        });
*/
        Button Input2 = (Button) findViewById(R.id.run_button);
        Input2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent page1 = new Intent(getBaseContext(), activity_input.class);
                startActivity(page1);
            }

        });
        Button Input3 = (Button) findViewById(R.id.tutorial_button);
        Input3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent page1 = new Intent(getBaseContext(), tutorial_activity.class);
                startActivity(page1);
            }

        });
        Button Input4 = (Button) findViewById(R.id.rules_button);
        Input4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent page1 = new Intent(getBaseContext(), activity_rules.class);
                startActivity(page1);
            }

        });
    }
}
