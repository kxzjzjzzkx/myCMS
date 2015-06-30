package org.marker.urlrewrite;

import java.util.regex.Pattern;

/**
 * 
 * @author marker
 * */
public class RewriteRule {

	//进站正则
	public Pattern inPattern;
	public String inResult;
	
	//出站正则
	public Pattern outPattern;
	public String outResult;
	
	public RewriteRule(Pattern inPattern, String inResult, Pattern outPattern,String outResult){
		this.inPattern = inPattern;
		this.inResult = inResult;
		this.outPattern = outPattern;
		this.outResult = outResult;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("InPattern: "+this.inPattern.toString()+"\n");
		sb.append("InUrl: "+this.inResult+"\n");
		sb.append("OutPattern: "+this.outPattern.toString()+"\n");
		sb.append("OutUrl: "+this.outResult+"\n");
		return sb.toString();
	}
}
