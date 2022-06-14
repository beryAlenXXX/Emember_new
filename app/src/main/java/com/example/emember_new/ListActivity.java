package com.example.emember_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView showAll;
    private ArrayList<Person> mArrData;
    HelperSQL most;
    StudentGradeAdapter adapter;

    public void viewOw(long num)
    {
        Intent intent=new Intent(this,Present.class);
        intent.putExtra("persen",num);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle bundle = getIntent().getExtras();
        showAll = findViewById(R.id.SHOW_ALL);
        most = new HelperSQL(this) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }
        };
        most.open();
//        mArrData= most.getAllCustomersByFIlter_RANDOM();
        //לאוסיף כבלה של ליסט
        mArrData =most.getAllPerson();
        Connection connection= (Connection) bundle.get("comme");
//        mArrData=most.getAllCustomersByFIlter(null,null,connection);
        most.close();


        adapter =new StudentGradeAdapter(this, R.layout.list_per, mArrData);
        showAll.setAdapter(adapter);
        showAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });
       


    }


    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("from", 3);
        startActivity(intent);
    }
    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<Person> mObjects;
        private MyListAdaper(Context context, int resource, List<Person> objects) {
            super(context, resource);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.dPik = (ImageView) convertView.findViewById(R.id.imageView);
                viewHolder.name = (Button) convertView.findViewById(R.id.list_item_text);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });
            mainViewholder.name.setText(getItem(position));

            return convertView;
        }
    }
    public class ViewHolder {

        ImageView dPik;
        Button name;
    }

}