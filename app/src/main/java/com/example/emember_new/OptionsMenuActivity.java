package com.example.emember_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsMenuActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //shared preferences for music icon
        SharedPreferences sp;

        sp = getSharedPreferences("sound", 0);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();




        if (id == R.id.home)
        {
            Intent intent = new Intent(this, MainActivity.class);
            //go to main screen
            startActivity(intent);
        }

        else if (id == R.id.ADD)
        {
            Intent intent = new Intent(this, AddmenActivity.class);
            //go to login screen
            startActivity(intent);
        }

        else if (id == R.id.contact)
        {
            //go to contactUs screen
            Intent intent = new Intent(this, SmsActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.list)
        {
            Intent intent = new Intent(this, ListActivity.class);
            //
            startActivity(intent);
        }



        return true;

    }

}