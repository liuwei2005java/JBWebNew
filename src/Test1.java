import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import com.jb.model.KeyInfo;
import com.jb.model.OCommodityRecord;
import com.jb.unit.PropertiesUtil;

public class Test1 {
	public static void main(String[] args) {
		String str = PropertiesUtil.getTestDate("c:\\testSrc\\hq[1].txt");
		Reader reader = new StringReader(str);
		Source source = null;
		try {
			source = new Source(reader);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Element> elementList = source
				.getAllElements(HTMLElementName.TABLE);
		Element trElement = elementList.get(0);
		getTableContentAndSave(trElement);
	}
	
	private static void getTableContentAndSave(Element element) {
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		int index = 0;
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				try {
					// 标号
					Element codeElement = tdList.get(2);
					System.out.println(codeElement.getContent().toString());
					// 品种
					Element pzElement = tdList.get(3);
					System.out.println(pzElement.getContent().toString());
					// 交货库点
					Element jhkdElement = tdList.get(4);
					System.out.println(jhkdElement.getContent().toString());
					// 交易数量
					Element numElement = tdList.get(5);
					System.out.println(numElement.getContent().toString());
					// 起报价
					Element qbjElement = tdList.get(7);
					System.out.println(qbjElement.getContent().toString());
					// 最新价格
					Element zxjElement = tdList.get(8);
					System.out.println(zxjElement.getContent().toString());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
	}
	
	private static void getTableContentAndSave1(Element element) {
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		int index = 0;
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				try {
					// 标号
					Element codeElement = tdList.get(2);
					System.out.println(codeElement.getContent().toString());
					// 价格
					Element pzElement = tdList.get(3);
					System.out.println(pzElement.getContent().toString());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
	}
}
