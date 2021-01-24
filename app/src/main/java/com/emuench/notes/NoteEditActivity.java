package com.emuench.notes;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.emuench.notes.model.DataManager;
import com.emuench.notes.model.DataStorage;
import com.emuench.notes.model.Note;

import java.util.Locale;

public class NoteEditActivity extends AppCompatActivity
{

    //Member Variables
    private EditText textArea;
    private Note note;
    private boolean isNewNote = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        textArea = findViewById(R.id.textArea);

        note = getIntent().getParcelableExtra("note");

        if(note == null)
        {
            isNewNote = true;
        }
        else
        {
            if(textArea != null)
            {
                textArea.setText(note.getContent());
                textArea.setSelection(textArea.getText().length());
            }
        }


        //actionbar
        Toolbar toolbar = findViewById(R.id.noteEdit_toolbar);
        setSupportActionBar(toolbar);

        //backbutton
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu_edit,menu);

        return true;
    }

    //control back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == 16908332)
        {
            finish();
        }
        else if(id == R.id.confirm_edits)
        {
            if(textArea != null)
            {
                if(isNewNote)
                {
                    DataStorage.getInstance().addNote(new Note(textArea.getText().toString()));
                }
                else
                {

                    DataStorage.getInstance().refreshNote(note,textArea.getText().toString());
                    //Toast.makeText(NoteEditActivity.this,"Refreshed",Toast.LENGTH_SHORT).show();
                }

                new DataManager(this).saveData();

                Toast.makeText(this,"Note saved",Toast.LENGTH_SHORT).show();

                finish();
                //Intent intent = new Intent(this,MainActivity.class);
                //startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    /*
    private void changeLanguage(Locale locale)
    {
        Resources res = getResources();

        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        android.content.res.Configuration configuration = res.getConfiguration();
        configuration.setLocale(locale);

        res.updateConfiguration(configuration,displayMetrics);


        Locale.setDefault(locale);

        recreate();

    }

     */
}
