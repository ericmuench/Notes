package com.emuench.notes;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.emuench.notes.adapters.NoteListAdapter;
import com.emuench.notes.model.DataManager;
import com.emuench.notes.model.DataStorage;
import com.emuench.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    //Member Variables
    private ActionMode actionMode;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView noteView;
    private NoteListAdapter adapter;

    private static boolean isFirstStart = true;
    private ArrayList<Note> noteList;
    private int tempPosition;
    private boolean actionModeActive;
    private int standarColor;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isFirstStart)
        {
            new DataManager(this).loadData();

            isFirstStart = false;
        }


        noteList = DataStorage.getInstance().getNotes();

        toolbar = findViewById(R.id.noteList_toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        final List<String> languageList = java.util.Arrays.asList(getResources().getStringArray(R.array.availible_languages));


        toolbar.getOverflowIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        buildRecyclerView();




    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.addNote:
                addNote();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }

    //private Methods
    private void buildRecyclerView()
    {
        noteView = findViewById(R.id.noteList);
        noteView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NoteListAdapter(DataStorage.getInstance().getNotes());

        adapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClicked(int position)
            {
                if(!actionModeActive)
                {
                    Intent intent = new Intent(MainActivity.this, NoteEditActivity.class);

                    intent.putExtra("note",noteList.get(position));

                    startActivity(intent);
                }
                else
                {
                    actionMode.finish();
                }

            }
        });

        adapter.setLongClickListener(new NoteListAdapter.OnItemLongClickListener()
        {
            @Override
            public boolean onLongClick(int position)
            {

                if(!actionModeActive)
                {
                    tempPosition = position;
                    actionMode = startSupportActionMode(actionModeCallback);
                }

                return true;
            }
        });

        noteView.setLayoutManager(layoutManager);
        noteView.setAdapter(adapter);


    }

    private void addNote()
    {
        Intent editNote = new Intent(this,NoteEditActivity.class);
        startActivity(editNote);
        adapter.notifyDataSetChanged();

    }

    //Action Mode Callback
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback()
    {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
        {
            Log.d("debug","ACTION MODE GESTARTET");
            actionMode.getMenuInflater().inflate(R.menu.toolbar_actionmode,menu);
            actionMode.setTitle("Selection-Mode");
            View itemView = layoutManager.findViewByPosition(tempPosition);

            RelativeLayout item = itemView.findViewById(R.id.listCard);
            actionModeActive = true;
            standarColor = item.getSolidColor();
            item.setBackgroundColor(getResources().getColor(R.color.item_selected));

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu)
        {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
        {
            switch (menuItem.getItemId())
            {
                case R.id.delete_note:
                    noteList.remove(tempPosition);
                    adapter.notifyItemRemoved(tempPosition);
                    new DataManager(MainActivity.this).saveData();
                    actionMode.finish();
                    return true;

                default:

                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode)
        {
            actionMode = null;
            View itemView = layoutManager.findViewByPosition(tempPosition);
            RelativeLayout item = itemView.findViewById(R.id.listCard);
            actionModeActive = false;
            item.setBackgroundColor(standarColor);
            Log.d("debug","ACTION MODE BEENDET");
        }
    };

    /*
    private void changeLanguage(Locale loc)
    {
        if(!(loc.equals(locale)))
        {
            Resources res = getResources();

            DisplayMetrics displayMetrics = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(loc);
            res.updateConfiguration(conf,displayMetrics);
            Locale.setDefault(loc);

            getBaseContext().getResources().updateConfiguration(new Configuration(),getBaseContext().getResources().getDisplayMetrics());


            //recreate();

            startActivity(new Intent(MainActivity.this,MainActivity.class));

        }


    }

     */
}
