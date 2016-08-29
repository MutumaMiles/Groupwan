package com.easyshare;

import java.io.File;

/**
 * Created by mutuma on 7/30/2016.
 */
public interface OnlineFriends
{
    abstract void friend(String name, File profilePicture);
    abstract void writeMessage(String message);
    abstract void notification(String message);
}
