package org.marker.mushroom.module;

import org.marker.mushroom.beans.SQLDataEngine;
import org.marker.mushroom.core.AppResolving;

public interface IModuleFactory {
	
	public StringBuilder parse(String tableName, SQLDataEngine sqlDataEngine);
	
	public void parse(AppResolving resv);
}
