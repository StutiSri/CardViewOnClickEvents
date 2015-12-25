package com.example.stutisrivastava.cardview.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.stutisrivastava.cardview.R;
import com.example.stutisrivastava.cardview.util.Constants;

public class PersonDetailsActivity extends ActionBarActivity {


    private TextView tvName;
    private TextView tvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        initialize();
    }

    private void initialize() {
        Intent data = getIntent();
        tvName = (TextView)findViewById(R.id.tv_name);
        tvAge = (TextView)findViewById(R.id.tv_age);

        String name = "Name : " +data.getStringExtra(Constants.KEY_NAME);
        String age = data.getStringExtra(Constants.KEY_AGE);

        tvName.setText(name);
        tvAge.setText(age);
    }
}
