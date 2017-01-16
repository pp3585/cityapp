package com.samples.pooja.cityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.samples.pooja.cityapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Called when user clicks on News button
    public void openNewsPage(View view) {
        Intent intent = new Intent(this, NewsListActivity.class);
        startActivity(intent);
    }
}
