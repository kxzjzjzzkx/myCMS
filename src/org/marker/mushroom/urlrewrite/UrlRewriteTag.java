package org.marker.mushroom.urlrewrite;

import java.util.List;

import org.marker.urlrewrite.URLRewrite;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;


/**
 * URL重写标签(for freemarker)
 * ${encoder("/cms?p=index")}
 * @author marker
 * */
public class UrlRewriteTag implements TemplateMethodModel {
 
 
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		String url = URLRewrite.me().encoder((String) args.get(0));
		return  url;
	} 

}
