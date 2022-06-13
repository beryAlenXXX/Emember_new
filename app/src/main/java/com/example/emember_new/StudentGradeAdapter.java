package com.example.emember_new;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class StudentGradeAdapter extends ArrayAdapter< Person>
{

    Context context;
    List<Person> objects;

    public StudentGradeAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects)
    {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.list_per, parent, false);

        Button button =(Button) view.findViewById(R.id.list_item_text);
        ImageView iv = (ImageView)view.findViewById(R.id.imageView);
        Person temp = objects.get(position);
        button.setText("id: " + temp.getId()+" name"+temp.getFname()+" "+temp.getlName() );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                long num=temp.getId();
                ListActivity listActivity =new ListActivity();
                listActivity.viewOw(num);
            }});
//        if(temp.getGrade()>=56)
//            iv.setImageResource(R.drawable.green_v);
//        else if(temp.getGrade()<56)
//            iv.setImageResource(R.drawable.red_x);
        iv.setImageBitmap((Bitmap) temp.getdPic());


        return view;
    }

}

