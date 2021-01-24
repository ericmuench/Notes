package com.emuench.notes.model;

import java.util.ArrayList;
import java.util.Collections;

public class DataStorage
{
    //MemberVariables
    private ArrayList<Note> noteList;


    //INSTANCE
    private static final DataStorage ourInstance = new DataStorage();

    //Constructor
    private DataStorage()
    {
        noteList  = new ArrayList<>();
    }

    //Getter
    public static DataStorage getInstance()
    {
        return ourInstance;
    }

    public ArrayList<Note> getNotes()
    {
        return noteList;
    }

    //Setter
    public void setNoteList(ArrayList<Note> noteList)
    {
        this.noteList = noteList;
    }

    //Methods
    public boolean addNote(Note note)
    {
        boolean b = noteList.add(note);
        sortList();
        return b;
    }

    public Note removeNote(int index)
    {
        return noteList.remove(index);
    }

    public int size()
    {
        return noteList.size();
    }

    public void refreshNote(Note note, String newText)
    {
        Note toBeRefreshed = findNote(note);

        if(toBeRefreshed != null)
        {
            toBeRefreshed.setContent(newText);

            sortList();
        }

    }

    private Note findNote(Note searchedOne)
    {
        for(Note n : noteList)
        {
            if(n.getContent().equals(searchedOne.getContent()))
            {
                return n;
            }
        }

        return null;
    }


    private void sortList()
    {
        Collections.sort(noteList);

        Collections.reverse(noteList);
    }
}
