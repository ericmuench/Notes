package com.emuench.notes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Note implements Parcelable, Comparable<Note>
{
    //Member Variables
    private String content;
    private Date date;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy', 'HH:mm");


    //Constructors
    public Note(String content)
    {
        this.content = content;
        setToCurrentDate();
    }

    public Note()
    {
        content = "";
        setToCurrentDate();
    }

    public Note(Parcel in) throws ParseException
    {
        this.content = in.readString();
        this.date = dateFormat.parse(in.readString());
    }

    //Getters and Setters
    public String getContent()
    {
        return content;
    }

    public static String dateFormat()
    {
        return dateFormat.toPattern();
    }

    public String getContentHead()
    {
        if(content.length() < 25)
        {
            if(content.contains("\n") && content.indexOf("\n") < 25)
            {
                return content.substring(0,content.indexOf("\n")).concat("...");
            }
            else
            {
                return content;
            }
        }
        else
        {
            if(content.contains("\n") && content.indexOf("\n") < 25)
            {
                return content.substring(0,content.indexOf("\n")).concat("...");
            }
            else
            {
                return content.substring(0,25).concat("...");
            }

        }
    }

    public String getFormattedDate()
    {
        return dateFormat.format(date);
    }

    public Date getDate()
    {
        return date;
    }

    public void setContent(String text)
    {
        if(text != null)
        {
            content = text;
            setToCurrentDate();
        }
    }

    //Methods
    public void addContent(String text)
    {
        if(text != null || !(text.isEmpty()))
        {
            content = content+text;
            setToCurrentDate();
        }
    }

    private void setToCurrentDate()
    {
        date = Calendar.getInstance().getTime();
    }


    //Interface Methods
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(content);
        dest.writeString(getFormattedDate());
    }

    @Override
    public int compareTo(Note o)
    {
        return this.getDate().compareTo(o.getDate());
    }

    //CREATOR
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>()
    {
        public Note createFromParcel(Parcel in)
        {
            try
            {
                return new Note(in);
            }
            catch(ParseException ex)
            {
                ex.printStackTrace();
                return null;
            }

        }

        public Note[] newArray(int size)
        {
            return new Note[size];
        }
    };
}
