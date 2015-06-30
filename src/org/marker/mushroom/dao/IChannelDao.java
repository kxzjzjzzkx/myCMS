package org.marker.mushroom.dao;

import java.util.List;

import org.marker.mushroom.beans.Channel;

public interface IChannelDao extends ISupportDao {

	Channel queryByUrl(String url);
	
	List<Channel> findAll();
}
