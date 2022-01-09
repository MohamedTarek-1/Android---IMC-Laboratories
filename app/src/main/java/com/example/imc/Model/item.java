package com.example.imc.Model;

public class item {


    private String text,Subtext;
    private boolean isExpandable;


    public item(String text, String subtext, boolean isExpandable) {
        this.text = text;
        Subtext = subtext;
        this.isExpandable = isExpandable;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtext() {
        return Subtext;
    }

    public void setSubtext(String subtext) {
        Subtext = subtext;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }



}
