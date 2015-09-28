package com.jb.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {

	/**
	 * 解析自选商品XML
	 * @param xmlStr
	 * @return
	 */
	public static List<Map> getZX(String xmlStr){
		List<Map> result = new ArrayList<Map>();
		Map objMap = new HashMap();
		Document doc = null;
		// 创建一个文档对象
		try {
			doc = DocumentHelper.parseText(xmlStr);
			Element rootElt = doc.getRootElement();
			Iterator qList = rootElt.elementIterator("Q");
			while(qList.hasNext()){
				Element qElement = (Element) qList.next();
				objMap = new HashMap();
				objMap.put("C", qElement.elementText("C"));
				objMap.put("P", qElement.elementText("P"));
				objMap.put("A", qElement.elementText("A"));
				objMap.put("CD", qElement.elementText("CD"));
				objMap.put("ID", qElement.elementText("ID"));
				result.add(objMap);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 解析成功竞标XML   标号1;标号2;标号3
	 * @param xmlStr
	 * @return
	 */
	public static Map getFIN(String xmlStr){
		Map result = new HashMap();
		String yxBHS = "";
		Document doc = null;
		String T = "";
		// 创建一个文档对象
		try {
			doc = DocumentHelper.parseText(xmlStr);
			Element rootElt = doc.getRootElement();
			Iterator qList = rootElt.elementIterator("S");
			//得到T，也就是lastTime
			T = rootElt.elementText("T");
			while(qList.hasNext()){
				Element qElement = (Element) qList.next();
				float validAmount = Float.parseFloat(qElement.elementText("VA"));
				if(validAmount>0){
					yxBHS += qElement.elementText("C") + ";";
				}
				
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("yxBHS", yxBHS);
		result.put("T", T);
		return result;
	}
	
	public static void main(String[] args) {
//		getZX("<?xml version=\"1.0\" encoding=\"GB2312\"?><R><Q><C>CNMDN13F718</C><P>40000.0</P><A>600316</A><CD>26</CD><ID>1862883</ID></Q><N>829</N><S>2</S><U>1</U><T1>14:00:00</T1><T2>14:30:30</T2></R>");
//		Map map = getFIN("<R><S><SID>1138911</SID><C>CNMDN13F906</C><P>40010.00</P><A>249.00000</A><VA>0.00000</VA><ST>2013-10-22/14:02:54</ST><MT>2013-10-22/14:30:09</MT></S><S><SID>1140197</SID><C>CNMDN13F908</C><P>50000.00</P><A>199.20000</A><VA>199.20000</VA><ST>2013-10-22/14:15:14</ST><MT>2013-10-22/14:15:14</MT></S><S><SID>1139922</SID><C>CNMDN13F908</C><P>40010.00</P><A>199.20000</A><VA>0.00000</VA><ST>2013-10-22/14:12:31</ST><MT>2013-10-22/14:15:14</MT></S><S><SID>1138885</SID><C>CNMDN13F010</C><P>40080.00</P><A>298.80000</A><VA>0.00000</VA><ST>2013-10-22/14:02:34</ST><MT>2013-10-22/14:02:41</MT></S><S><SID>1138798</SID><C>CNMDN13F015</C><P>40010.00</P><A>99.60000</A><VA>0.00000</VA><ST>2013-10-22/14:01:39</ST><MT>2013-10-22/14:02:28</MT></S><T>2013-10-22/14:30:09</T></R>");
//		System.out.println(map);
		String str = "adfs";
		System.out.println(str.indexOf("d"));
	}
}
