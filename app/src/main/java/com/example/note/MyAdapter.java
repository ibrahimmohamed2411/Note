package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Note> notes;
    OnRecyclerViewItemClickListener listener;

    public MyAdapter(Context context, ArrayList<Note> notes,OnRecyclerViewItemClickListener listener)
    {
        this.listener=listener;
        this.context=context;
        this.notes=notes;
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.note_items,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.tv_note_name.setText(notes.get(position).getNoteName());
        holder.tv_note.setText(notes.get(position).getNote());
        holder.tv_tag.setText(notes.get(position).getTag());
        holder.tv_note.setTag(notes.get(position).getId());




    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public void setNotes(ArrayList<Note> notes)
    {
        this.notes=notes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_note_name,tv_note,tv_tag;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_note_name=itemView.findViewById(R.id.tv_note_name);
            tv_note=itemView.findViewById(R.id.tv_note);
            tv_tag=itemView.findViewById(R.id.tv_tag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id=(int) tv_note.getTag();
                    listener.onItemClick(id);
                }
            });









        }



    }
}
