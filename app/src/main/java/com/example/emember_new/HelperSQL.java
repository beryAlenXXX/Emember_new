package com.example.emember_new;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public abstract class HelperSQL extends SQLiteOpenHelper {
    public static final String DATABASENAME = "person_db";
    public static final String TABLE_PERSON = "tblperson";


    public static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID = "personId";
    public static final String COLUMN_F_NAME = "fName";
    public static final String COLUMN_L_NAME = "LName";
    public static final String COLUMN_CONNECTION = "connection_my";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PIK = "picture_bitMap";
    private static SQLiteDatabase database;




    public HelperSQL(Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    String[] allColumns = {HelperSQL.COLUMN_ID, HelperSQL.COLUMN_F_NAME, HelperSQL.COLUMN_L_NAME,

            HelperSQL.COLUMN_CONNECTION, HelperSQL.COLUMN_DESCRIPTION, HelperSQL.COLUMN_PIK};

    private static final String CREATE_TABLE_PERSON = "CREATE TABLE IF NOT EXISTS " +
            TABLE_PERSON + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_F_NAME + " VARCHAR," + COLUMN_L_NAME + " VARCHAR," + COLUMN_DESCRIPTION + " VARCHAR,"
            + COLUMN_CONNECTION + " VARCHAR," + COLUMN_PIK+ " BLOB"+");";

    public HelperSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, SQLiteDatabase database) {
        super(context, name, factory, version);
        this.database = database;
    }
    public int conectDisa ()
    {
        return 0;
    };

    public HelperSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE_PERSON);
        Log.i("data1", "Table customer created");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(sqLiteDatabase);
    }
    public void open()
    {
        database=this.getWritableDatabase();
        Log.i("data", "Database connection open");
    }

    public static long createPerson(Person p)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        p.getdPic().compress(Bitmap.CompressFormat.PNG,0,stream);

        ContentValues values=new ContentValues();
        //values.put(HelperSQL.COLUMN_ID, p.getId());
        values.put(HelperSQL.COLUMN_F_NAME, p.getFname());
        values.put(HelperSQL.COLUMN_L_NAME, p.getlName());
        values.put(HelperSQL.COLUMN_CONNECTION, String.valueOf(p.getMyConnection()));
        values.put(HelperSQL.COLUMN_DESCRIPTION, p.getDescription());
        values.put(HelperSQL.COLUMN_PIK, stream.toByteArray());

        long lastId = database.insert(HelperSQL.TABLE_PERSON, null, values);

        p.setId(lastId);
        return lastId;
        //return p;
    }
    public static void createPerson_void (Person p)
    {
        //INSERT INTO table_name (column1, column2, column3, ...)
        //VALUES (value1, value2, value3, ...);
        long lastId = -1;
        String str_sql = "INSERT INTO " + HelperSQL.TABLE_PERSON + "(" + HelperSQL.COLUMN_F_NAME + ","
                + HelperSQL.COLUMN_L_NAME + "," + HelperSQL.COLUMN_DESCRIPTION + "," + HelperSQL.COLUMN_CONNECTION + ","+HelperSQL.COLUMN_PIK+")"
                + " VALUES (" + "'" + p.getFname()+ "'" +  "," + "'" +p.getlName()+"'" +  "," + "'" +p.getDescription() + "'," + "'" + p.getMyConnection() + "'," + "'" +p.getDPic()+"')";

        database.execSQL(str_sql);

        str_sql = "SELECT " + HelperSQL.COLUMN_ID +  " from " + HelperSQL.TABLE_PERSON +"  order by " + HelperSQL.COLUMN_ID + " DESC limit 1\n";

        Cursor c = database.rawQuery(str_sql,null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
            Log.d("data1", ""+ lastId);
        }
        p.setId(lastId);

    }
    public ArrayList<Person> getAllPerson() {

        ArrayList<Person> l = new ArrayList<>();
        Cursor cursor=database.query(HelperSQL.TABLE_PERSON, allColumns, null, null, null, null, null);

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_ID));
                String fname=cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_F_NAME));
                String lname=cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_L_NAME));
                //Toast.makeText(this,cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_CONNECTION)), Toast.LENGTH_SHORT).show();
                String description=cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_DESCRIPTION));
                Connection myConnection = Connection.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_CONNECTION)));
                byte[] bytesImage = cursor.getBlob(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_PIK));
                Bitmap dPic = BitmapFactory.decodeByteArray(bytesImage,0,bytesImage.length);
                //Bitmap dPic = Bitmap. cursor.getBlob(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_PIK)));

                Person c = new Person( fname,  lname,  dPic, myConnection ,  description,id);

                l.add(c);
            }
        }
        return l;
    }
    public Person getPersonById(long rowId)
    {
        Cursor cursor=database.query(HelperSQL.TABLE_PERSON, allColumns, HelperSQL.COLUMN_ID + "=" +rowId, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
           long id = cursor.getLong(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_ID));
            String fname=cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_F_NAME));
            String lname=cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_L_NAME));
            //Toast.makeText(this,cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_CONNECTION)), Toast.LENGTH_SHORT).show();
            String description=cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_DESCRIPTION));
            Connection myConnection = Connection.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_CONNECTION)));
            byte[] bytesImage = cursor.getBlob(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_PIK));
            Bitmap dPic = BitmapFactory.decodeByteArray(bytesImage,0,bytesImage.length);
            //Bitmap dPic = Bitmap. cursor.getBlob(cursor.getColumnIndexOrThrow(HelperSQL.COLUMN_PIK)));
            Person person = new Person( fname,  lname,  dPic, myConnection ,  description,id);
            this.close();
            return person;
        }
        // personId

        return null;

    }
    public long deleteAll()
    {
        return database.delete(HelperSQL.TABLE_PERSON, null, null);

    }
    public long deleteByRow(long rowId)
    {
        return database.delete(HelperSQL.TABLE_PERSON, HelperSQL.COLUMN_ID + "=" + rowId, null);
    }


    public static long updateByRow(Person p)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        p.getdPic().compress(Bitmap.CompressFormat.PNG,0,stream);

        ContentValues values=new ContentValues();
        //values.put(HelperSQL.COLUMN_ID, p.getId());
        values.put(HelperSQL.COLUMN_F_NAME, p.getFname());
        values.put(HelperSQL.COLUMN_L_NAME, p.getlName());
        values.put(HelperSQL.COLUMN_CONNECTION, String.valueOf(p.getMyConnection()));
        values.put(HelperSQL.COLUMN_DESCRIPTION, p.getDescription());
        values.put(HelperSQL.COLUMN_PIK, stream.toByteArray());

        long lastId = database.insert(HelperSQL.TABLE_PERSON, null, values);
        database.update(HelperSQL.TABLE_PERSON, values, HelperSQL.COLUMN_ID + "=" + p.getId(), null);
        return lastId;



    }
    public ArrayList<Person>getAllCustomersByFIlter(String selection,String OrderBy,Connection connection)
    {
        String[] selectionArgs = new String[1];
        String selectionA=new String(String.valueOf(connection));
        selectionArgs[0]=selectionA;
        Cursor cursor=database.query(HelperSQL.TABLE_PERSON, allColumns, COLUMN_CONNECTION+ "=?", selectionArgs , null, null, OrderBy);
        ArrayList<Person>l=convertCurserToList(cursor);
        return  l;
    }
////"SELECT * FROM
//    public ArrayList<Person>getAllCustomersByFIlter(String selection,String OrderBy)
//    {
//        Cursor cursor=database.query(HelperSQL.TABLE_PERSON, allColumns, selection, null, null, null, OrderBy);
//        ArrayList<Person>l=convertCurserToList(cursor);
//        return  l;
//    }
//    public ArrayList<Person>getAllCustomersByFIlter_RANDOM()
//    {
//        Cursor cursor=database.query(HelperSQL.TABLE_PERSON, allColumns, "SELECT * FROM TABLE_PERSON COLUMN_CONNECTION = '"+String.valueOf(Connection.RANDOM)+"'" , null, null, null, null);
//        ArrayList<Person>l=convertCurserToList(cursor);
//        return  l;
//    }


    private ArrayList<Person> convertCurserToList(Cursor cursor) {
        ArrayList<Person> l = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(Integer.parseInt(HelperSQL.COLUMN_ID));
                String fname = cursor.getString(Integer.parseInt(HelperSQL.COLUMN_F_NAME));
                String lname = cursor.getString(Integer.parseInt(HelperSQL.COLUMN_L_NAME));
                String description = cursor.getString(Integer.parseInt(HelperSQL.COLUMN_DESCRIPTION));
                Connection myConnection = Connection.valueOf(cursor.getString(Integer.parseInt(HelperSQL.COLUMN_CONNECTION)));
                byte[] bytesImage = cursor.getBlob(Integer.parseInt(HelperSQL.COLUMN_PIK));
                Bitmap dPic = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
                Person person = new Person(fname, lname, dPic, myConnection, description, id);
                l.add(person);

            }

        }
        return l;
    }


    public abstract View getView(int position, View convertView, ViewGroup parent);
}
