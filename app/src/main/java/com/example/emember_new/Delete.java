package com.example.emember_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class Delete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        HelperSQL mast= new HelperSQL(this) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }
        };
        Bundle bundle = getIntent().getExtras();
        long num= bundle.getLong("persen");
        mast.open();
        mast.deleteByRow(num);
        mast.close();
        //;;;;;;;;;
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("from",4 );
        startActivity(intent);
    }
}