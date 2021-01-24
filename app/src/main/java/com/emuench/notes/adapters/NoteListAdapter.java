package com.emuench.notes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emuench.notes.R;
import com.emuench.notes.model.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>
{
    //MemberVariables
    private List<Note> noteList;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private int currentPos = 0;

    //Constructors
    public NoteListAdapter(List<Note> noteList)
    {
        this.noteList = noteList;
    }

    //Setter
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.clickListener = listener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener)
    {
        this.longClickListener = longClickListener;
    }

    //Getter
    public int getCurrentPosition()
    {
        return currentPos;
    }

    //Methods
    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notelist_item,viewGroup,false);


        return new NoteListViewHolder(view, clickListener,longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder noteListViewHolder, int i)
    {
        Note note = noteList.get(i);

        noteListViewHolder.contentHead.setText(note.getContentHead());
        noteListViewHolder.date.setText(note.getFormattedDate());
    }

    @Override
    public int getItemCount()
    {
        return noteList.size();
    }

    //ViewHolderClass
    public static class NoteListViewHolder extends RecyclerView.ViewHolder
    {
        public TextView contentHead;
        public TextView date;

        public NoteListViewHolder(@NonNull View itemView, final OnItemClickListener clickListener, final OnItemLongClickListener longListener)
        {
            super(itemView);
            contentHead = itemView.findViewById(R.id.textview_content_headline);
            date = itemView.findViewById(R.id.textview_date);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(clickListener != null)
                    {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION)
                        {
                            clickListener.onItemClicked(position);
                        }


                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {

                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION)
                    {
                        return longListener.onLongClick(position);
                    }
                    else
                    {
                        return false;
                    }
                }
            });


        }
    }


    //internal ItemClickListener Interfaces
    public interface OnItemClickListener
    {
        public void onItemClicked(int position);
    }

    public interface OnItemLongClickListener
    {
        public boolean onLongClick(int position);
    }




}
