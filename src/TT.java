import java.text.SimpleDateFormat;
import java.util.Date;

import com.jb.unit.PropertiesUtil;


public class TT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		PropertiesUtil.string2File("aaaaaaaaa", "c:\\jbsp\\"+sdf.format(new Date())+".html");
	}

}
