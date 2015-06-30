package org.marker.mushroom.core.config;

import java.io.IOException;
import java.util.Properties;

public interface IDefaultConfig {

	
	String get(String key);
	
	void put(String key, String value);
	
	Properties getProperties();
	
	void store() throws IOException;
}
