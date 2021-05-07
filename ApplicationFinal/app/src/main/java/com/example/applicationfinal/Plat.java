package com.example.applicationfinal;


public class Plat {
    private String typePlat;
    private String note;

    public Plat (String typePlat, String note) {
        this.typePlat = typePlat;
        this.note = note;
    }

    public String getTypePlat() {
        return typePlat;
    }

    public void setTypePlat(String typePlat) {
        this.typePlat = typePlat;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFullName()  {
        return this.typePlat;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString()  {
        return this.getFullName() + " - (" + this.note+")";
    }
}
