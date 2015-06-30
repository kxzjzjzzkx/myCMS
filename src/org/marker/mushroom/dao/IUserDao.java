package org.marker.mushroom.dao;

import java.io.Serializable;

import org.marker.mushroom.beans.User;

public interface IUserDao {

	User queryByNameAndPass(String name, String pass);
	
	boolean updateLoginTime(Serializable id);
}
