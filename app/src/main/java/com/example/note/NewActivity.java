package com.example.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText et_note_name,et_tag,et_note;
    ImageView iv_note;

    private Uri imageUri=null;
    public static final int PICK_IMAGE_REQ_CODE=2;
    public static final int PER_REQ_CODE=6;
    private int noteId=-1;

    DatabaseAccess db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        toolbar=findViewById(R.id.options_toolbar);
        setSupportActionBar(toolbar);
        et_note_name=findViewById(R.id.et_note_name);
        et_tag=findViewById(R.id.et_tag);
        et_note=findViewById(R.id.et_note);
        iv_note=findViewById(R.id.iv_note);
        db=DatabaseAccess.getInstance(this);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PER_REQ_CODE);
        }

        iv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,PICK_IMAGE_REQ_CODE);
            }
        });


        noteId=getIntent().getIntExtra(MainActivity.NOTE_KEY,-1);
        if(noteId==-1) // add new note
        {
            enableFields(true);
            cleanFields();
        }
        else  // view exist note
        {
            enableFields(false);
            Note n=db.getNoteById(noteId);
            if(n!=null)
            {
                fillNoteToField(n);
            }





        }




    }
    public void fillNoteToField(Note note)
    {
        et_note_name.setText(note.getNoteName());
        et_tag.setText(note.getTag());
        et_note.setText(note.getNote());
        if(note.getImage()!=null && !note.getImage().equals("")) {
            Log.d("hema", String.valueOf(Uri.parse(note.getImage())));
//
            iv_note.setEnabled(true);
            iv_note.setImageURI(Uri.parse(note.getImage()));




        }
        else {
            iv_note.setImageResource(R.drawable.icon_add_picture);
//            iv_note.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_picture));
        }


    }
    public void enableFields(boolean bool)
    {
        et_note_name.setEnabled(bool);
        et_tag.setEnabled(bool);
        et_note.setEnabled(bool);
        iv_note.setEnabled(bool);
    }
    public void cleanFields()
    {
        et_note_name.setText("");
        et_tag.setText("");
        et_note.setText("");
        //iv_note.setImageURI(Uri.parse("content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F37/ORIGINAL/NONE/image%2Fjpeg/1804853532"));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        if(noteId==-1) // add new note
        {
            menu.findItem(R.id.edit).setVisible(false);
            menu.findItem(R.id.delete).setVisible(false);
            menu.findItem(R.id.save).setVisible(true);
        }
        else
        {
            menu.findItem(R.id.edit).setVisible(true);
            menu.findItem(R.id.delete).setVisible(true);
            menu.findItem(R.id.save).setVisible(false);

        }

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String note_name , tag, note,image="";

        switch (item.getItemId())
        {

            case R.id.save:
                if(noteId==-1) // add new note
                {
                    note_name=et_note_name.getText().toString();
                    tag=et_tag.getText().toString();
                    note=et_note.getText().toString();
                    if(imageUri!=null)
                        image=imageUri.toString();
                    Note n=new Note(note_name,tag,note,image);

                    boolean res=db.insertNote(n);
                    if(res)
                    {
                        Toast.makeText(getBaseContext(),"Add success",Toast.LENGTH_SHORT).show();
                        setResult(MainActivity.ADD_NOTE_REQ_CODE,null);
                        finish();
                    }



                }
                else  // تعديل
                {
                    note_name=et_note_name.getText().toString();
                    tag=et_tag.getText().toString();
                    note=et_note.getText().toString();
                    if(imageUri!=null)
                        image=imageUri.toString();
                    Note n=new Note(note_name,tag,note,image,noteId);
                    db.update(n);
                    setResult(MainActivity.EDIT_NOTE_REQ_CODE,null);
                    finish();
                }
                return true;
            case R.id.edit:
                enableFields(true);
                toolbar.getMenu().findItem(R.id.save).setVisible(true);
                toolbar.getMenu().findItem(R.id.edit).setVisible(false);
                toolbar.getMenu().findItem(R.id.delete).setVisible(false);
                return true;
            case R.id.delete:
                Note n=new Note(null,null,null,null,noteId);
                boolean res=db.deleteNote(n);
                if(res)
                {
                    Toast.makeText(getBaseContext(),"Note delete succesfully",Toast.LENGTH_SHORT);
                    setResult(MainActivity.EDIT_NOTE_REQ_CODE,null);
                    finish();
                }
                return true;


        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==PICK_IMAGE_REQ_CODE && resultCode==RESULT_OK)
       {
            if(data!=null)
            {
                imageUri=data.getData();
                iv_note.setImageURI(imageUri);
            }
       }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PER_REQ_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                    finish();
        }
    }
}