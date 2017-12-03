package com.example.sinh.starchat.IModel;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by ADMIN on 12/3/2017.
 */


public class Author implements IUser {

   /*...*/

    String id, name, avatar;
    static int i = 0;

    @Override
    public String getId() {
        return ""+(i++)%4;
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
