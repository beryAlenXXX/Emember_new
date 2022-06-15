package com.example.emember_new;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends OptionsMenuActivity {
    //Button add,RANDOM, FAMLIY,FRIENDS, JOB, YOU;
    Button add,all,random,family,frinds,job,you;
    Dialog dialog;
    TextView showBtn,cancelBtn;

    public final int FOR_RESULT=4555;
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
        startActivityForResult(intent,FOR_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FOR_RESULT)
        {
            createDialog();

//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//            fab.setOnClickListener(view -> {

                //show
                dialog.show();
//            });

            //SHOW BTN CLIKCED
            showBtn.setOnClickListener(v -> Toast.makeText(MainActivity.this,"CLICKED",Toast.LENGTH_LONG).show());

            //CANCEL
            cancelBtn.setOnClickListener(v -> dialog.dismiss());

        }

    }

    private void createDialog()
    {
        dialog=new Dialog(this);

        //SET TITLE
        dialog.setTitle("Player");

        //set content
        dialog.setContentView(R.layout.custom_layout);

        showBtn= (TextView) dialog.findViewById(R.id.showTxt);
        cancelBtn= (TextView) dialog.findViewById(R.id.cancelTxt);
    }


}
