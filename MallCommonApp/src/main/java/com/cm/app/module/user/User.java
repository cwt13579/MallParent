package com.cm.app.module.user;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{

	private static final long serialVersionUID = 1L;
    public static User dao = new User();
}
