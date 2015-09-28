package com.jb.unit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.jb.model.JbiaoUtil;
import com.jb.model.OCommodityRecord;

public class PropertiesUtil {

	private static ResourceBundle bundle = ResourceBundle.getBundle("app");

	public static String getValue(String key) {
		try {
			return bundle.getString(key);
		} catch (Exception ex) {
			return null;
		}
	}

	// 测试的时候使用
	public static String getTestDate(String patchStr) {
		File file = new File(patchStr);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
	 * 
	 * @param res
	 *            原字符串
	 * @param filePath
	 *            文件路径
	 * @return 成功标记
	 */
	public static boolean string2File(String res, String filePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			if (!distFile.exists())
				distFile.createNewFile();
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 得到提交地址
	 * 
	 * @param jbiaoUtil
	 * @param localContext
	 * @param httpclient
	 * @param orderprice
	 * @param code
	 * @param totalAmount
	 * @return
	 */
	public static String getSubmitURL(JbiaoUtil jbiaoUtil,
			HttpClientContext localContext,
			CloseableHttpAsyncClient httpclient, String orderprice, String code,int maxPrice) {
		String submitURL = jbiaoUtil.getSubmitUrl();
		// 得到orderFlag地址
		HttpGet getorderFlag = new HttpGet(jbiaoUtil.getOrderUrlNoId() + code);
		// HttpGet getTest111 = new
		// HttpGet("http://124.127.44.53:20023/vendue8/submit/top2.jsp");
		String orderFlagValue = "";
		String otherParams = "";
		String orderStr = "";
		Future<HttpResponse> futureOrder = httpclient.execute(getorderFlag,
				localContext, null);
		HttpResponse response12 = null;
		HttpEntity entity33 = null;
		try {
			response12 = futureOrder.get();
			entity33 = response12.getEntity();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			orderStr = EntityUtils.toString(entity33, "gbk");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ------------处理下单页隐藏域中打乱字符处理
		Pattern pattern = Pattern.compile("(\t)([a-z0-9]){3}\r\n\t");
		Matcher matcher = pattern.matcher(orderStr);
		while (matcher.find()) {
			orderStr = matcher.replaceAll("");
		}
		// ------------处理下单页隐藏域中打乱字符处理
		orderFlagValue = getOrderFlag(orderStr);
		//gggg  -1正式-2测试
		otherParams = getParamByContent(orderStr, orderprice,
				jbiaoUtil.getIsTest(),maxPrice,"-2");
		if("-1".equals(otherParams)){
			return "-1";
		}
		submitURL += "?orderFlag=" + orderFlagValue + "&"
				+ otherParams;

		return submitURL;
	}

	/**
	 * 根据提交竞标金额页面源码,得到OrderFlag
	 * 
	 * @param jspStr
	 * @return
	 */
	public static String getOrderFlag(String jspStr) {
		String str = "";
		try {
			str = jspStr.substring(jspStr.indexOf("frm.orderFlag.value"));
			str = str.substring(str.indexOf("\"") + 1, str.indexOf("\";"));
		} catch (Exception e) {
			// TODO: handle exception
			str = "";
		}
		return str;
	}
	
	/**
	 * 得到order.jsp页面的金额
	 * 
	 * @param content
	 * @param orderprice
	 * @return
	 */
	public static String getJINEByContent(String content) {
		Reader reader = new StringReader(content);
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
		Element trElement = elementList.get(1);
		List<Element> trList = trElement.getAllElements(HTMLElementName.TR);
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
				List<Element> tdList = trElement
						.getAllElements(HTMLElementName.TD);
				try {
					// 标号
					Element codeElement = tdList.get(0);
					List<Element> tds = codeElement.getAllElements(HTMLElementName.TD);
					int i = 0;
					for(Element td: tds){
						if(i==8){
							String tdStr = td.getContent().toString().replace("&nbsp;", "");
							return tdStr.substring(0,tdStr.indexOf("."));
						}
						i++;
					}
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
		}
		
		return "";
	}
	

	/**
	 * 华商改变后使用的方法(新)
	 * 
	 * @param content
	 * @param orderprice
	 * @return
	 */
	public static String getParamByContent(String content, String orderprice,
			String isTest,int maxPrice,String flag) {
		
		if("-1".equals(orderprice)){
			orderprice = getJINEByContent(content);
			orderprice = String.valueOf(Integer.valueOf(orderprice)+10);
			if(Integer.valueOf(orderprice) > maxPrice){
				return "-1";
			}
		}
		
		String paramStr = "";
		if ("1".equals(isTest)) {
			content = PropertiesUtil.getTestDate("c:\\testSrc\\order[1].txt");
		}
		String submitpricename = getSubmitpricename(content);
		try {
			// 如果这行报错,使用下面那一行
			// Parser parser = new Parser(content);
			Parser parser = createParser(content);
			NodeFilter filter = new TagNameFilter("input");
			// NodeFilter filter = new StringFilter("orderpricename");
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			String name = "";
			String value = "";
			String type = "";
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					InputTag inputTag = (InputTag) nodes.elementAt(i);
					// 判断是否包含orderpricename
					name = inputTag.getAttribute("name");
					value = inputTag.getAttribute("value");
					type = inputTag.getAttribute("type");
					
					if(name.equals("orderFlag") || name.equals("submitpricename")){
						continue;
					}
					
					if ((value == null || "".equals(value)) && "-2".equals(flag)) {
						value = orderprice;
					}else if(value == null || "".equals(value)){
						value = "";
					}
					if (type == null || "".equals(type)) {
						type = "text";
					}

					if ("text".equals(type) || "hidden".equals(type)) {
						if (submitpricename.equals(name)) {
							if("-1".equals(flag)){
								if((Integer.valueOf(value))>maxPrice){
									return "-1";
								}else {
									value = String.valueOf(Integer.valueOf(value)+10);
								}
							}else {
								value = orderprice;
							}
							
						}
						paramStr += name + "=" + value + "&";
					}

				}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if("-1".equals(flag)){
			paramStr += "submitpricename=" + submitpricename;
		}else {
			paramStr = paramStr.substring(0,paramStr.lastIndexOf("&"));
		}
		
		return paramStr;
	}
	

	/**
	 * 得到提交后台的金额字段name
	 * 
	 * @param jspStr
	 * @return
	 */
	private static String getSubmitpricename(String jspStr) {
		String str = "";
		try {
			str = jspStr.substring(jspStr.indexOf("frm.submitpricename.value"));
			str = str.substring(str.indexOf("\"") + 1, str.indexOf("\";"));
		} catch (Exception e) {
			// TODO: handle exception
			str = "";
		}
		return str;
	}

	private static Parser createParser(String inputHTML) {
		Lexer mLexer = new Lexer(new Page(inputHTML));
		return new Parser(mLexer, new DefaultParserFeedback(
				DefaultParserFeedback.QUIET));
	}

	// 得到自选商品的session号
	public static String getZXlidVal(String jspStr) {
		String str = "";
		try {
			str = jspStr.substring(jspStr.indexOf("hq.jsp?netType=1&lid="),
					jspStr.indexOf("\" name=\"hqList\""));
			str = str.substring(str.indexOf("lid=") + 4);
		} catch (Exception e) {
			// TODO: handle exception
			str = "";
		}
		return str;
	}
	
	
	/**
	 * 判断是否下单成功
	 * @param entity
	 * @return
	 */
	public static boolean isSuccessed(HttpEntity entity){
		boolean flag = false;
		try {
			String pageVal = EntityUtils.toString(entity, "gbk");
		    String returnVal = pageVal.substring(pageVal.indexOf("alert('")+7,pageVal.indexOf("')"));
		    if(returnVal.indexOf("报单成功")!= -1 ){
		    	flag = true;
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	// 求两个数组的差集
	public static List<String> minus(String[] arr1, String[] arr2) {
		LinkedList<String> list = new LinkedList<String>();
		LinkedList<String> history = new LinkedList<String>();
		List<String> retList = new ArrayList<String>();
		String[] longerArr = arr1;
		String[] shorterArr = arr2;
		// 找出较长的数组来减较短的数组
		if (arr1.length > arr2.length) {
			longerArr = arr2;
			shorterArr = arr1;
		}
		for (String str : longerArr) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : shorterArr) {
			if (list.contains(str)) {
				history.add(str);
				list.remove(str);
			} else {
				if (!history.contains(str)) {
					list.add(str);
				}
			}
		}
		
		for(String s : list){
			if(!flagArray(arr2, s)){
				retList.add(s);
			}
		}
		
		return retList;
	}

	/**
	 * 判断元素是否存在于该数组中
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean flagArray(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
}
