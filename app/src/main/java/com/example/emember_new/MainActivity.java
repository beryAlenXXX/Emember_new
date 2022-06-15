package com.example.emember_new;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Button add,RANDOM, FAMLIY,FRIENDS, JOB, YOU;
    Button add,all,random,family,frinds,job,you;
    public static final String CHANNEL_ID = "com.emember.CHANNEL_IDN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.add);
        all =findViewById(R.id.All);
        random= findViewById(R.id.RANDOM);
        family=findViewById(R.id.famliy);
        frinds =findViewById(R.id.friends);
        job=findViewById(R.id.job);
        you=findViewById(R.id.button);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "ememberNotification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager =
                    getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPerson(v);


            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList(v);


            }
        });
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openList(v);

            }
        });family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList(v);

            }
        });frinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList(v);

            }
        });job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList(v);

            }
        });you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList(v);


            }
        });




    }


    private void openList(View v)
    {
        Connection connection = null;
        switch (v.getId())
        {
            case R.id.All:


            break;
            case R.id.RANDOM:
                connection=Connection.RANDOM;

                break;

        }
        Intent intent =  new Intent(this,ListActivity.class);
        intent.putExtra("comme",connection);
        startActivity(intent);




    }






    public void addPerson(View view) {

        Intent intent=new Intent(this, Addmen.class);

           startActivity(intent);

    }

    public void kill_me(View view)
    {
        Intent intent =new Intent(this,I_hate_my_life.class);
        startActivity(intent);
    }
}
