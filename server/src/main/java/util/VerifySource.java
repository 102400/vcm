package util;

import util.MD5;

public class VerifySource {
    
    private VerifySource() {}
	
	private static final String LEFT = "#vc";
	private static final String RIGHT = "@*!6^xs";
	
	private static String f(String str) {
		return LEFT + str + RIGHT;
	}
	
	public static String getSource(String str) {
		return f(str);
	}
	
	public static String getMD5(String str) {
		return MD5.code(f(str));
	}
	
	public static String getMD5(int str) {
		return getMD5(str + "");
	}

}
