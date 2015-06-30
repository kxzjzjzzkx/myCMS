package org.marker.mushroom.service;

import java.util.List;
import java.util.Map;

import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.dao.ISupportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 栏目查询服务层
 * */
@Service(SystemStatic.SYSTEM_CHANNEL_SERVICE)
public class ChannelService {

	@Autowired private ISupportDao commonDao;
	
	
	public List<Map<String, Object>> findChannelsByPid(long pid){
		List<Map<String, Object>> queryOne = commonDao.queryForList("SELECT id, pid from mr_channel where pid=? or id=?", pid, pid);
		
//		dao.queryForList("SELECT c.id,c.pid from mr_channel c inner JOIN mr_tmp_for_channel t on c.pid = t.id AND c.id not IN(select id from mr_tmp_for_channel)", args);
		
		
		//
		
		
		
		
		return commonDao.queryForList("select * from mr_channel");
	}
	
	
}
