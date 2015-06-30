package org.marker.urlrewrite;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 


/**
 * URL重写
 * (采用URL规则方式实现，此版本为没有优化的版本，但处理速度也是相当快的)
 * @author marker
 * @date 2013-05-06
 * */
public final class URLRewrite {
	
	/** 参数正则表达式 */
	public static final Pattern parameterPattern = Pattern.compile("\\{[a-zA-Z_0-9]+\\}");
	
	/** 编码URL时地址纠正使用的正则表达式 */
	private static final Pattern RIGHT_URL = Pattern.compile("\\w+\\=");
	
	/** 规则参数 */
	public static final Map<String,Parameter> ruleParameter = new HashMap<String,Parameter>();
	
	/** 规则 */
	private static final Map<String,RewriteRule> rules = new HashMap<String,RewriteRule>();
	
	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 * */
	private static class SingletonHolder {
		public final static URLRewrite instance = new URLRewrite();     
	}
	
	
	public static URLRewrite me(){
		return SingletonHolder.instance;
	}



	public String baseUrl;
	
	/** 默认构造 */
	private URLRewrite(){
//		ruleParameter.put("{channel}", new Parameter("p","{channel}","([a-zA-Z_0-9]+)"));
//		ruleParameter.put("{type}", new Parameter("type","{type}","([a-zA-Z_0-9]+)"));
//		ruleParameter.put("{id}", new Parameter("id","{id}","([0-9]+)"));
//		
//		//初始化两个规则
//		putRule("列表","{channel}.html");
//		putRule("内容","{channel}/{type}/cms-thread-{id}.html");
	}
	
	
	/**
	 * 添加规则参数
	 * @param Parameter param
	 * @return Parameter
	 * */
	public Parameter put(Parameter param){
		return ruleParameter.put(param.getExpress(), param);
	}
	
	/**
	 * 移除规则
	 * @param key 规则名称
	 * */
	public RewriteRule remove(String key){
		return rules.remove(key);
	}
	
	/**
	 * 设置规则
	 * @param key 标识
	 * @param urlrule 规则
	 * */
	public void putRule(String key, String urlrule) {
		Matcher matcher = parameterPattern.matcher(urlrule);
		String inRegex = new String(urlrule);//解析好的URL正则表达式
		String inUrl    = "";//真实访问地址
		String outRegex = "";//出站regex
		String outUrl = new String(urlrule);
		int sequence = 1;
		while(matcher.find()){
			Parameter param = ruleParameter.get(matcher.group());
			if(param != null){
				inUrl += param.getKey() + "=$" + sequence +"&";
				outRegex  += param.getKey() + "="+param.getRegex()+"&";
				outUrl = outUrl.replaceAll(Pattern.quote(param.getExpress()), "\\$"+sequence++);
			}
		} 
		for(String express : ruleParameter.keySet()){
			Parameter param = ruleParameter.get(express);
			inRegex = inRegex.replaceAll(Pattern.quote(express), param.getRegex()); 
		}
		
		if(inUrl.length() > 1){
			inUrl    = inUrl.substring(0, inUrl.length() - 1); 
			outRegex = outRegex.substring(0, outRegex.length() - 1); 
		} 
		
		//正则
		Pattern inPattern  = Pattern.compile(inRegex);
		Pattern outPattern = Pattern.compile(baseUrl.substring(0, baseUrl.length()-1)+"\\?" + outRegex);
		 
		RewriteRule rule = new RewriteRule(inPattern,baseUrl+inUrl,outPattern,outUrl);
//		System.out.println(rule.toString());
		rules.put(key, rule);
	}

 
	
	/**
	 * 解码
	 * */
	public String decoder(String url){
		for(String key : rules.keySet()){
			RewriteRule rule = rules.get(key);
			Matcher m = rule.inPattern.matcher(url);
			if(m.matches()){ 
				return m.replaceAll(rule.inResult);
			}
		}
		return url;
	}
	
 
	
	/**
	 * 编码(将普通url地址编码为伪静态地址)
	 * */
	public String encoder(String url){
		for(String key : rules.keySet()){
			RewriteRule rule = rules.get(key);
			/* URL地址纠正 start */
			Matcher ma = RIGHT_URL.matcher(rule.outPattern.toString());
			String urla = "";
			while(ma.find()){
				String field = ma.group().split("=")[0];
				int findex = url.indexOf(field);
				if(findex == -1) continue;
				int and = 0;
				if(url.lastIndexOf("&") == findex-1 || url.lastIndexOf("&") == -1)//代表最后一个
					and = url.length();
				else
					and = url.indexOf("&", findex); 
				urla += url.substring(findex, and)+"&";
			}
			if(urla.length() > 1){
				urla = urla.substring(0, urla.length()-1);
			
			}
			int a = url.indexOf("?");
			if(a != -1){//如果有问号
				urla = url.substring(0,a+1) + urla; 
			}
			/* URL地址纠正 end */
			
			Matcher m = rule.outPattern.matcher( urla ); 
			if(m.find()){
				return m.replaceAll(rule.outResult);
			}
		}
		return url;
	}
}
