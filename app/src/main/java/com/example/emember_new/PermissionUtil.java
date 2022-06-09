package com.example.emember_new;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class PermissionUtil extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 888;
    private static SharedPreferences sp;
     static Context context;

    static SharedPreferences.Editor editor;
    private  static  final  int REQUEST_CAMERA = 200;
    private  static  final  int REQUEST_STORAGE = 300;
    private  static  final  int REQUEST_CONTACTS = 400;
    private  static  final  int REQUEST_GROUP_PERMISSIOM = 500;



    private  static  final  int CAMERA = 1;
    private  static  final  int STORAGE = 2;
    private  static  final  int CONTACTS = 3;
    private  static  final  int All = 4;

    public PermissionUtil(Context context){
        this.context=context;
        sp = context.getSharedPreferences(context.getString(R.string.permission_preferance),Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public PermissionUtil() {

    }




    public void updatePermissionPreference(String permission){
        if(permission.equals("camera")){
            editor.putBoolean(this.context.getString(R.string.permission_camera),true);
            editor.commit();
        }
        else if(permission.equals("storage")){
            editor.putBoolean(this.context.getString(R.string.permission_storage),true);
            editor.commit();
        }
        else if(permission.equals("contacts")){
            editor.putBoolean(this.context.getString(R.string.permission_contacts),true);
            editor.commit();

        }
    }
    public static boolean checkPermissionPreference(String permission){
        boolean isShown = false;
        if(permission.equals("camera"))
        {
            isShown = sp.getBoolean(context.getString(R.string.permission_camera),false);
        }
        ///if(permission.equals("storage")){
       //     isShown = sp.getBoolean(this.context.getString(R.string.permission_storage),false);
       // }
      //  if(permission.equals("contacts")){
       //     isShown = sp.getBoolean(this.context.getString(R.string.permission_contacts),false);
       // }
        return  isShown;
    }
    public void requestPermission(int permission){
        if(permission == REQUEST_IMAGE_CAPTURE){
            ActivityCompat.requestPermissions((Activity) this.context,
                    new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
        }
        else if(permission == STORAGE){
            ActivityCompat.requestPermissions(PermissionUtil.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE);
        }
        else if(permission == CONTACTS){
            ActivityCompat.requestPermissions(PermissionUtil.this,
                    new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CONTACTS);
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
    public boolean camera(){
        if(this.checkPermission(CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            //did user aske second time
            if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionUtil.this, Manifest.permission.CAMERA)) {
                this.showPermissionExplanation(CAMERA);

            }
            else if (!this.checkPermissionPreference("camera")) {
                this.requestPermission(REQUEST_IMAGE_CAPTURE);
                this.updatePermissionPreference("camera");
            }
            else {//go to setting applications
                Toast.makeText(this, "Please allow camere permission setting", Toast.LENGTH_LONG);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        }
        else
            {
            Toast.makeText(this, "you have camera permission", Toast.LENGTH_LONG);
            AlertDialog d = new AlertDialog.Builder(this)
                    .setTitle("run time permission").setMessage("now you have camera permission")
                    .create();
            d.show();
            return true;
        }
        return false;
    }
    public int checkPermission(int permission){
        int status = PackageManager.PERMISSION_DENIED;
        if(permission == CAMERA){
            status = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA);
        }
        else if(permission == STORAGE){
            status = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        else if(permission == CONTACTS){
            status = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS);
        }
        return status;
    }


    //phase 5
    public void requestAll(){
        ArrayList<String>permissionAsked = new ArrayList<String>();
        ArrayList<String>permissionAvailable= new ArrayList<String>();
        permissionAvailable.add(Manifest.permission.READ_CONTACTS);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionAvailable.add(Manifest.permission.CAMERA);

        for(int i=0;i<permissionAvailable.size();i++){
            if( ContextCompat.checkSelfPermission(this,
                    permissionAvailable.get(i))!=PackageManager.PERMISSION_GRANTED)
            {
                permissionAsked.add(permissionAvailable.get(i));
            }
        }//end of for
        requestGroupPermissions(permissionAsked);


    }
    //phase 6 : Request Group Permissions
    public void requestGroupPermissions(ArrayList<String> permissions){
        if(permissions!=null&& permissions.size()>0) {
            String[] permissionGroups = new String[permissions.size()];
            permissions.toArray(permissionGroups);
            ActivityCompat.requestPermissions(PermissionUtil.this, permissionGroups, REQUEST_GROUP_PERMISSIOM);
        }
        else
            Toast.makeText(this,
                    "You have permission to camera, storage and contacts",Toast.LENGTH_LONG).show();
    }

    //phase 7
    //permission response
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(REQUEST_CAMERA==requestCode){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "You have camera permission",Toast.LENGTH_SHORT).show();

                AlertDialog d = new AlertDialog.Builder(this)
                        .setTitle("run time permission").setMessage("you have camera permission")
                        .create();
                d.show();
            }
            else {
                Toast.makeText(this, "You Do not have camera permission",Toast.LENGTH_SHORT).show();
            }
        }
        else if(REQUEST_CONTACTS==requestCode){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "You have contacts permission",Toast.LENGTH_SHORT).show();
                AlertDialog d = new AlertDialog.Builder(this)
                        .setTitle("run time permission").setMessage("you have contact permission")
                        .create();
                d.show();
            }
            else {
                Toast.makeText(this, "You Do not have contacts permission",Toast.LENGTH_SHORT).show();
            }

        }
        else if(REQUEST_STORAGE==requestCode){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "You have storage permission",Toast.LENGTH_SHORT).show();
                AlertDialog d = new AlertDialog.Builder(this)
                        .setTitle("run time permission").setMessage("you have storage permission")
                        .create();
                d.show();
            }
            else {
                Toast.makeText(this, "You Do not have storage permission",Toast.LENGTH_SHORT).show();
            }

        }
        else if(REQUEST_GROUP_PERMISSIOM == requestCode){
            String solutions = "";
            for(int i=0;i<permissions.length;i++){
                String status = "";
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    status="GRANTED";
                }
                else status = "DENIED";
                solutions += solutions + "\n" + permissions[i] + " :" + status;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Group Permissions Details : ");
            builder.setMessage(solutions);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }





    public void showPermissionExplanation(final int permission){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(permission==CAMERA){
            builder.setMessage("This app need to access to device camera.");
            builder.setTitle("Camera permission needed");
        }
        if(permission==CONTACTS){
            builder.setMessage("This app need to access to your contacts.");
            builder.setTitle("Contacts permission needed");
        }
        if(permission==STORAGE){
            builder.setMessage("This app need to access to your storage.");
            builder.setTitle("Storage permission needed");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(permission == CAMERA){
                    requestPermission(REQUEST_IMAGE_CAPTURE);
                }
                if(permission == STORAGE){
                    requestPermission(STORAGE);
                }
                if(permission == CONTACTS){
                    requestPermission(CONTACTS);
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

