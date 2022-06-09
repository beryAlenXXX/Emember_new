package com.example.emember_new;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Addmen extends AppCompatActivity {


    private static final int MY_CAMERA_REQUEST_CODE = 100;
    Person demyGod;
    String fname;
    String lName;
    //Bitmap dPic;
    Connection myConnection;
    String knowledge;
    EditText editTextFN, editTextLN, editTextDes;
    Button back, done;
    ImageView pik1;
    HelperSQL mast;
    ArrayList<Person> listOfPerson;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmen);
        editTextFN = findViewById(R.id.FName);
        editTextLN = findViewById(R.id.LName);
        editTextDes = findViewById(R.id.kn);
        back = findViewById(R.id.back);
        done = findViewById(R.id.Done);
        pik1 = findViewById(R.id.pik);
        mast = new HelperSQL(this);

        listOfPerson = new ArrayList<Person>();

        RadioGroup rg = (RadioGroup) findViewById(R.id.Radio);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:

                        myConnection = Connection.RANDOM;
                        break;
                    case R.id.radioButton2:

                        myConnection = Connection.FAMELY;
                        break;
                    case R.id.radioButton3:

                        myConnection = Connection.FRIENDS;
                        break;
                    case R.id.radioButton4:

                        myConnection = Connection.JOB;
                        break;
                }
            }

        });


    }

    public void pikFunc(View v) {
        dispatchTakePictureIntent();
    }


    private void dispatchTakePictureIntent() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
            else {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "gjhfjkgjh,gjkvjjhgj", Toast.LENGTH_SHORT).show();

        if (requestCode == REQUEST_IMAGE_CAPTURE)
            if (resultCode == RESULT_OK) {

              //  dPic= (Bitmap) data.getExtras().get("data");
               Bitmap b = (Bitmap) data.getExtras().get("data");
//                Bitmap b =Bitmap.createBitmap(dPic);
//                byte [] b = new byte()[];
                pik1.setImageBitmap(b);
               // pik1.setImageBitmap(getResizedBitmap(b, 640, 800));

            }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();

        return resizedBitmap;
    }


//    public void download(String fname, String lName, Bitmap dPic, Connection
//            myConnection, String knowledge) {
//        if (fname != null) {
//            this.fname = fname;
//        }
//        if (lName != null) {
//            this.lName = lName;
//        }
//        if (dPic != null) {
//            this.dPic = dPic;
//        }
//        if (myConnection != null) {
//            this.myConnection = myConnection;
//        }
//        if (knowledge != null) {
//            this.knowledge = knowledge;
//        }
//    }
//
//    public void done() {
//        demyGod = new Person(fname, lName, dPic, myConnection, knowledge);
//    }

    public void addPerson(View view) {
        fname = editTextFN.getText().toString();
        lName = editTextLN.getText().toString();
        knowledge = editTextDes.getText().toString();


        //Person person = new Person(fname, lName, dPic, myConnection, knowledge);


        Log.i("data", "list size is " + listOfPerson.size());
        createPerson();
        ///Person person1 =mast.createPerson(person);
        //Intent intent = new Intent(this, Present.class);
        //intent.putExtra("persen", (Parcelable) person1);
        //startActivity(intent);
    }

    public void createPerson() {
        mast.open();
        long num = 0;
        pik1.setDrawingCacheEnabled(true);
        Bitmap bitmap = pik1.getDrawingCache();
        Person c1 = new Person(fname, lName, bitmap, myConnection, knowledge);
        num = mast.createPerson(c1);
        /////Toast.makeText(this, (int) num, Toast.LENGTH_SHORT).show();
        listOfPerson.add(c1);
        mast.close();
        Intent intent = new Intent(this, Present.class);
        intent.putExtra("persen", num);
        startActivity(intent);
    }



    public Person getDemyGod() {
        return demyGod;
    }

    public void setDemyGod(Person demyGod) {
        this.demyGod = demyGod;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

//    public Bitmap getdPic() {
//        return dPic;
//    }
//
//    public void setdPic(Bitmap dPic) {
//        this.dPic = dPic;
//    }

    public Connection getMyConnection() {
        return myConnection;
    }

    public void setMyConnection(Connection myConnection) {
        this.myConnection = myConnection;
    }


    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
