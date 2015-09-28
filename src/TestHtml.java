import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.jb.unit.PropertiesUtil;


public class TestHtml {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = PropertiesUtil.getTestDate("c:\\testSrc\\order[1].txt");
		try {
			System.out.println(System.currentTimeMillis());
			Parser parser = new Parser(str);
			 NodeFilter filter = new TagNameFilter("input");
//			NodeFilter filter = new StringFilter("orderpricename");
	         NodeList nodes = parser.extractAllNodesThatMatch(filter);
	         String paramStr = "";
	         if(nodes!=null) {
	                for (int i = 0; i < nodes.size(); i++) {
	                	InputTag inputTag = (InputTag) nodes.elementAt(i);
	                    //ÅÐ¶ÏÊÇ·ñ°üº¬orderpricename
	                	String name = inputTag.getAttribute("name");
	                	String value = inputTag.getAttribute("value");
	                	if(value == null){
	                		value = "";
	                	}
	                	if(name.indexOf("orderpricename")!=-1 || name.indexOf("jn")!=-1 || name.indexOf("opn")!=-1){
	                		if(name.indexOf("orderpricename")!=-1){
	                			value = "22360";
	                		}
	                		paramStr += name + "=" + value + "&";
	                	}
	                }
	            }
	         System.out.println(System.currentTimeMillis());
	         System.out.println(paramStr.substring(0,paramStr.lastIndexOf("&")));
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
