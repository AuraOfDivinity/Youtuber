package com.industrialmaster.asel.youtuber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LandingActiviity extends AppCompatActivity {

    TextView searchText, feedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_activiity);

        searchText = (TextView)findViewById(R.id.searchtext);
        feedText = (TextView)findViewById(R.id.feedtext);

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(LandingActiviity.this, MainActivity.class);
                startActivity(i);
            }
        });

        feedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(LandingActiviity.this, FeedActivity.class);
                startActivity(i);
            }
        });
    }


}
