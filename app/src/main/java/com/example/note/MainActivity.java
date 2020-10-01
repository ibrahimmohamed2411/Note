package com.example.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;


    public static final String NOTE_KEY="note_key";

    public static final int ADD_NOTE_REQ_CODE=90;
    public static final int EDIT_NOTE_REQ_CODE=25;

    DatabaseAccess db;
    ArrayList<Note> notes;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.main_toolbar);
        recyclerView=findViewById(R.id.main_rv);



        setSupportActionBar(toolbar);
        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,NewActivity.class);
                startActivityForResult(i,ADD_NOTE_REQ_CODE);

            }
        });

        db=DatabaseAccess.getInstance(this);
        notes=db.getAllNotes();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this, notes, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int noteId) {
                Intent i=new Intent(getBaseContext(),NewActivity.class);
                i.putExtra(NOTE_KEY,noteId);
                startActivityForResult(i,EDIT_NOTE_REQ_CODE);
            }


        });

        recyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView=(SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setQueryHint("Search by tag");
        searchView.setBackgroundColor(getResources().getColor(android.R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ArrayList<Note> notes=db.getNote(query);
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Note> notes=db.getNote(newText);
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.setNotes(db.getAllNotes());
                adapter.notifyDataSetChanged();
                return false;
            }
        });




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_NOTE_REQ_CODE )
        {
            adapter.setNotes(db.getAllNotes());
            adapter.notifyDataSetChanged();
        }

        else if(requestCode==EDIT_NOTE_REQ_CODE)
        {
            adapter.setNotes(db.getAllNotes());
            adapter.notifyDataSetChanged();
        }
    }
}