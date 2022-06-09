package com.example.emember_new;

import android.graphics.Bitmap;
enum Connection {

    RANDOM,
    FAMELY,
    FRIENDS,
    JOB,
    you
}

public class Person {
    long id;
    String fname;
    String lName;
    Bitmap dPic ;
    Connection myConnection;
    String description;

    public Person(String fname, String lname, Bitmap bitmap, Connection myConnection, String description, long id)
    {
        this.id=id;
        if (bitmap!=null)
            dPic = bitmap;
        if (fname!=null)
            this.fname = fname;
        if (lname!=null)
            lName = lname;
        if (myConnection!=null)
            this.myConnection=myConnection;
        if(description !=null)
        {
            this.description = description;
        }

    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getdPic() {
        return dPic;
    }






    public Person(String fname, String lName, Bitmap dPic, Connection myConnection , String description)
    {
        if (dPic!=null)
            this.dPic = dPic;
        if (fname!=null)
            this.fname = fname;
        if (lName!=null)
            this.lName = lName;
        if (myConnection!=null)
            this.myConnection=myConnection;
        if(description !=null)
        {
            this.description = description;
        }


    }
    public Person(String fname, String lName, Bitmap dPic) {
        this.fname = fname;
        this.lName = lName;
        this.dPic = dPic;
    }

    public Person(String fname, String lName) {
        this.fname = fname;
        this.lName = lName;
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

    public Bitmap getDPic() {
        return dPic;
    }

    public void setdPic(Bitmap dPic) {
        this.dPic = dPic;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Connection getMyConnection()
    {
        return myConnection ;
    }

    public void setMyConnection(Connection myConnection)
    {
        this.myConnection = myConnection;
    }

}
