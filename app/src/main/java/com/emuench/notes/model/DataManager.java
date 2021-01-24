package com.emuench.notes.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataManager
{
    //Member Variables
    private Context context;
    private Gson serializer;
    private SharedPreferences sharedPreferences;

    //Constructors
    public DataManager(Context context)
    {
        System.out.println(Note.dateFormat());
        this.serializer  = new GsonBuilder().setDateFormat(Note.dateFormat()).create();
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);

    }

    //Methods
    public void saveData()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("notes",serializer.toJson(DataStorage.getInstance().getNotes()));

        editor.apply();
    }

    public void loadData()
    {
        String listJSON = sharedPreferences.getString("notes",null);

        if(listJSON != null)
        {
            Type type = new TypeToken<ArrayList<Note>>(){}.getType();

            ArrayList<Note> listFromPrefs = serializer.fromJson(listJSON,type);

            DataStorage.getInstance().setNoteList(listFromPrefs);
        }


    }

}
