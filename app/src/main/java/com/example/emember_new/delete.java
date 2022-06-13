package com.example.emember_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class delete extends AppCompatActivity {
    HelperSQL mast= new HelperSQL(this) {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Bundle bundle = getIntent().getExtras();
        long num= bundle.getLong("persen");
        mast.open();
        Person person=mast.getPersonById(num);
        mast.close();
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("from",4 );
        startActivity(intent);
    }
}