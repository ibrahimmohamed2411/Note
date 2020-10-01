package com.example.note;

import java.io.Serializable;

public class Note
{
    private String noteName;
    private String tag;
    private String note;
    private String image;
    private int id;

    public Note(String noteName, String tag, String note, String image) {
        this.noteName = noteName;
        this.tag = tag;
        this.note = note;
        this.image = image;
    }

    public Note(String noteName, String tag, String note, String image, int id) {
        this.noteName = noteName;
        this.tag = tag;
        this.note = note;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
