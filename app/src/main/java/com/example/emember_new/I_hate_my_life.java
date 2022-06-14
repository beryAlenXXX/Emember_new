package com.example.emember_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class I_hate_my_life extends AppCompatActivity {
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ihate_my_life);
        editText=findViewById(R.id.end_my_suffering);
    }

    public void send(View view)
    {
        String text =editText.getText().toString();
        String phoneNumber="0504463544";
        Intent intentt = new Intent(Intent.ACTION_VIEW);
        intentt.setData(Uri.parse("sms:"));
        intentt.setType("vnd.android-dir/mms-sms");
        intentt.putExtra(Intent.EXTRA_TEXT, "");
        intentt.putExtra("address",  phoneNumber);
        startActivity(intentt);
    }
}