package org.marker.mushroom.utils;

import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.security.Base64;
import org.marker.mushroom.security.DES;
import org.marker.mushroom.security.MD5;

/**
 * 密码加密生成器
 * 
 * @author marker
 * */
public class GeneratePass {

	public static String encode(String password) {
		String key = SystemConfig.getInstance().get("secret_key");
		try {
			return MD5.getMD5Code(Base64.encode(DES.encrypt(
					password.getBytes(), key)));
		} catch (Exception e) {
		}
		return password;
	}

}
