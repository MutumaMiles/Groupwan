package com.easyshare;


import java.io.File;

/**
 * Created by mutuma on 7/30/2016.
 */
public class ListItems
{
    private String fileName;
    private File folderIcon;

    public ListItems(String fName,File fIcon)
    {
        this.fileName=fName;
        this.folderIcon=fIcon;
    }
    public String getFriendName()
    {
        return fileName;
    }
    public File getFriendsProfilePic()
    {
        return folderIcon;
    }
}