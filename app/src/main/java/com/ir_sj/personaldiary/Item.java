package com.ir_sj.personaldiary;

import java.io.File;

public class Item implements Comparable<Item>{
    private String name;
    private String data;
    private String date;
    private String path;
    private String image;
    private File file;
    private String bg_color;
    BackgroundColor bgColor;



    public Item(String n,String d, String dt, String p, String img)
    {
        if(img == "@drawable/ic_insert_drive_file_black_24dp")
            name =n;
        else
            name= n.substring(4);
        data = d;
        date = dt;
        path = p;
        image = img;
        bg_color = "@color/normal";
    }

    public Item(String n, String p, String img, File f)
    {
        if(img == "@drawable/ic_insert_drive_file_black_24dp")
            name =n;
        else
            name= n.substring(4);
        path = p;
        image = img;
        file = f;
        bg_color = "@color/normal";
    }
    public String getName()
    {
        return name;
    }
    public String getData()
    {
        return data;
    }
    public String getDate()
    {
        return date;
    }
    public String getPath()
    {
        return path;
    }
    public String getImage() {
        return image;
    }
    public File getFile() { return file;}
    //public int getChecked_state() { return checked_state;}

    public int compareTo(Item o) {
        //if(this.name != null)
            //return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        //else
            //throw new IllegalArgumentException();
        return 1;
    }
    public void setBg_color()
    {
        if(bg_color.equals("normal")) {
            bg_color = "selectedColour";
            bgColor.changeBackgroundColor(R.color.selectedColour);
        }
        else
            bgColor.changeBackgroundColor(R.color.normal);
    }
}