package com.example.emember_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle bundle= getIntent().getExtras();

    }
    public void back(View view)
    {
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("from", 3);
        startActivity(intent);
    }
}