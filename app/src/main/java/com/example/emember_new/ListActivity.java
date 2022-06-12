package com.example.emember_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ListActivity extends AppCompatActivity {
  LinearLayout showAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle bundle= getIntent().getExtras();
        showAll=findViewById(R.id.SHOW_ALL);


    }
    public void back(View view)
    {
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("from", 3);
        startActivity(intent);
    }
}