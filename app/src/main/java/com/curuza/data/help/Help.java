package com.curuza.data.help;


public class Help {

    public static final String HELP_EXTRA = "help_code";

    private int mId;
    private int mHelpImage;
    private String name;
    private String description;

    public Help(int mId, int mHelpImage, String name,String description) {
        this.mId = mId;
        this.mHelpImage = mHelpImage;
        this.name = name;
        this.description = description;
    }


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmHelpImage() {
        return mHelpImage;
    }

    public void setmHelpImage(int mHelpImage) {
        this.mHelpImage = mHelpImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
