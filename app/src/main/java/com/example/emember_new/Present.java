package com.example.emember_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Present extends AppCompatActivity {
    TextView textFN, textLN, textKn;
    Button back, done;
    ImageView imageView;
    HelperSQL mast=new HelperSQL(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.present);
        initViews();



        Intent intent =getIntent();
        Bundle bundle = getIntent().getExtras();
        long num =bundle.getLong("persen");

        if(num!= 0)
        {
            mast.open();
            Person person=mast.getPersonById(num);
            mast.close();
            show(person) ;
        }
    }

    private void initViews()
    {
        textFN=findViewById(R.id.FName);
        textLN=findViewById(R.id.LName);
        textKn=findViewById(R.id.knowledge);
        back=findViewById(R.id.back);
        done=findViewById(R.id.Done);
        imageView=findViewById(R.id.image);
    }

    private void show(@NonNull Person person)
    {
        textFN.setText(person.getFname());
        textLN.setText(person.getlName());
        textKn.setText(person.getDescription());
        imageView.setImageBitmap(person.getDPic());


    }

    public void edit(View view)
    {

    }
    public void back(View view)
    {
        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("from", 3);
        startActivity(intent);
    }
}