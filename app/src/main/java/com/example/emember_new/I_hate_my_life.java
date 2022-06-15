package com.example.emember_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class I_hate_my_life extends OptionsMenuActivity implements View.OnClickListener {
EditText editText;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn;

    EditText txtMessage;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    boolean isGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ihate_my_life);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }






    public void init(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


        sp = getSharedPreferences("data",0);
        editor = sp.edit();


        sendBtn =(Button)findViewById(R.id.send);

        sendBtn.setOnClickListener( this);

        txtMessage = findViewById(R.id.end_my_suffering);

    }





    @Override
    public void onClick(View v) {
        if (!txtMessage.getText().toString().equals("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permission();//בקשת הרשאה
                if (isGranted) {
                    sendSmsFunction();
                }
            }
            else
                sendSmsFunction();
        }
        else
            Toast.makeText(getApplicationContext(), "massage cannot br null", Toast.LENGTH_LONG).show();

    }
    public void dialog()
    {
        AlertDialog.Builder builder = new AlertDialog
                .Builder(this);
        builder.setMessage("to contact with developer, the app need to access sms");
        builder.setTitle("Sms permission needed");
        builder.setCancelable(true);


        builder.setPositiveButton(
                "Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ActivityCompat.requestPermissions(I_hate_my_life.this, new String[]{Manifest.permission.SEND_SMS}, 200);//מבקש הרשאה
                    }
                });
        builder.setNegativeButton(
                "No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();//סוגר את הדיאלוג
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();//מציד את הדיאלוג
    }


    public void sendSmsFunction(){//פעולה ששולחת sms למפתח
        String msg= txtMessage.getText().toString();//מוציא את ההודעה מהeditText


        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_IMMUTABLE);


        SmsManager sms=SmsManager.getDefault();//מתחבר לsms של המערכת
        sms.sendTextMessage("0504463544", null, msg, pi,null);

        Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
    }

    public void permission() {//פעולה שמטפלת בהרשאה
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                dialog();


            }
            else if (!sp.getBoolean("firstCheckPermission",false)){//אחרת אם זה פעם ראשונה שמבקשים הרשאה
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 100);
                editor.putBoolean("firstCheckPermission",true);
                editor.commit();

            }
            else{//אם זה כבר פעם שלישית מפנים את המשתמש להגדרות של האפליקציה
                Toast.makeText(this, "Please allow sms permission setting", Toast.LENGTH_LONG);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);//הגדרת הintent להגדרות של האפליקציה
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);//הפניה להגדרות של האפליקציה הנוכחית
                this.startActivity(intent);
            }

        }
        else//יש הרשאה
            isGranted = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {//פעולה שמופעלת אחרי בקשת הרשאה
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//אם המשתמש אישר
                isGranted = true;
                System.out.println("PLAYGROUND Permission has been granted");

            } else {//המשתמש לא אישר
                System.out.println("PLAYGROUND Permission has been denied or request cancelled");
                isGranted = false;
            }
        }
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
                System.out.println("PLAYGROUND Permission has been granted");

            } else
                isGranted = false;
        }
    }

}