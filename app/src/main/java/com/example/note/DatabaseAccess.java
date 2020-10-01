package com.example.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

public class DatabaseAccess  {
 private SQLiteDatabase db;
 private static DatabaseAccess instance;
 private MyDatabase myDatabase;


private DatabaseAccess(Context context)
{
  myDatabase=new MyDatabase(context);
}
 public static DatabaseAccess getInstance(Context context)
 {
   if(instance==null)
    {
      instance=new DatabaseAccess(context);
    }
   return instance;
 }
 public boolean insertNote(Note note)
 {
   db=myDatabase.getWritableDatabase();
  ContentValues values=new ContentValues();
  values.put(MyDatabase.COL_NOTE_NAME,note.getNoteName());
  values.put(MyDatabase.COL_TAG,note.getTag());
  values.put(MyDatabase.COL_NOTE,note.getNote());
  values.put(MyDatabase.COL_IMAGE,note.getImage());
  long result=db.insert(MyDatabase.TABLE_NAME,null,values);
  db.close();
  return result!=-1;
 }

 public boolean update(Note note)
 {
   db=myDatabase.getWritableDatabase();
  ContentValues values=new ContentValues();
  values.put(MyDatabase.COL_NOTE_NAME,note.getNoteName());
  values.put(MyDatabase.COL_TAG,note.getTag());
  values.put(MyDatabase.COL_NOTE,note.getNote());
  values.put(MyDatabase.COL_IMAGE,note.getImage());
  int result=db.update(MyDatabase.TABLE_NAME,values,"id=?",new String[]{String.valueOf(note.getId())});
  return result>0;
 }
 public long getNotesCount()
 {
  return DatabaseUtils.queryNumEntries(myDatabase.getReadableDatabase(),myDatabase.TABLE_NAME);
 }
 public boolean deleteNote(Note note)
 {
   db=myDatabase.getWritableDatabase();
  long result=db.delete(myDatabase.TABLE_NAME,"id=?",new String[]{String.valueOf(note.getId())});
  db.close();
  return result>0;
 }
 public ArrayList<Note> getAllNotes()
 {
  ArrayList<Note> notes=new ArrayList<>();
   db=myDatabase.getReadableDatabase();
  Cursor cursor=db.rawQuery("SELECT * FROM "+ MyDatabase.TABLE_NAME,null);
  if(cursor!=null && cursor.moveToFirst())
  {
   do {

    int id=cursor.getInt(cursor.getColumnIndex(MyDatabase.COL_ID));
    String note_name=cursor.getString(cursor.getColumnIndex(MyDatabase.COL_NOTE_NAME));
    String tag=cursor.getString(cursor.getColumnIndex(MyDatabase.COL_TAG));
    String note=cursor.getString(cursor.getColumnIndex(MyDatabase.COL_NOTE));
    String image=cursor.getString(cursor.getColumnIndex(MyDatabase.COL_IMAGE));

    Note n=new Note(note_name,tag,note,image,id);
    notes.add(n);




   }while(cursor.moveToNext());
   cursor.close();
   db.close();
  }
  return notes;
 }

 public ArrayList<Note> getNote(String Tag)  // search
 {
  ArrayList<Note> notes=new ArrayList<>();
   db=myDatabase.getReadableDatabase();
   Cursor cursor=db.rawQuery(" SELECT * FROM "+myDatabase.TABLE_NAME +" WHERE "+ myDatabase.COL_TAG +" LIKE "+"'"+Tag+"%'" ,null);
  if(cursor!=null &&cursor.moveToFirst())
  {
   do {

    int id=cursor.getInt(cursor.getColumnIndex(myDatabase.COL_ID));
    String note_name=cursor.getString(cursor.getColumnIndex(myDatabase.COL_NOTE_NAME));
    String tag=cursor.getString(cursor.getColumnIndex(myDatabase.COL_TAG));
    String note=cursor.getString(cursor.getColumnIndex(myDatabase.COL_NOTE));
    String image=cursor.getString(cursor.getColumnIndex(myDatabase.COL_IMAGE));

    Note n=new Note(note_name,tag,note,image,id);
    notes.add(n);



   }while (cursor.moveToNext());
   cursor.close();
   db.close();
  }
  return notes;

 }
 public Note getNoteById(int id)
 {
  Note n = null;
  db=myDatabase.getReadableDatabase();
  Cursor cursor=db.rawQuery("SELECT * FROM "+MyDatabase.TABLE_NAME+" WHERE "+MyDatabase.COL_ID+" =? ",new String[]{id+""});

  if(cursor!=null &&cursor.moveToFirst()) {



    String note_name = cursor.getString(cursor.getColumnIndex(myDatabase.COL_NOTE_NAME));
    String tag = cursor.getString(cursor.getColumnIndex(myDatabase.COL_TAG));
    String note = cursor.getString(cursor.getColumnIndex(myDatabase.COL_NOTE));
    String image = cursor.getString(cursor.getColumnIndex(myDatabase.COL_IMAGE));

     n = new Note(note_name, tag, note, image);




   cursor.close();
   db.close();
  }
  return n;




 }




}
