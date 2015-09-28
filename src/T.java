import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jb.unit.PropertiesUtil;


public class T {

	public static void main(String[] args) {
		
		Pattern pattern = Pattern.compile("(\t)([a-z0-9]){3}\r\n"); 
		Matcher matcher = pattern.matcher("ad3fff\r\n\tff1\r\n"); 
		StringBuffer buffer = new StringBuffer(); 
		while(matcher.find()){ 
			System.out.println(matcher.replaceAll("Java"));  
		} 
//		
//		String str = "ad3fff\r\nfff1\r\n";
//		Pattern pattern = Pattern.compile(".*(fff1).*");
//		  Matcher matcher = pattern.matcher(str);
//		  boolean b= matcher.matches();
//		  //当条件满足时，将返回true，否则返回false
//		  System.out.println(b);
	}
}
