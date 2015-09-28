package com.jb.action;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.jb.dao.OCommodityDao;
import com.jb.model.JbiaoUtil;
import com.jb.model.KeyInfo;
import com.jb.model.OCommodityAdjust;
import com.jb.model.OCommodityRecord;
import com.jb.model.SystemInfo;
import com.jb.unit.PropertiesUtil;
import com.opensymphony.xwork2.ActionSupport;


public class UrlDataAction extends ActionSupport implements ApplicationAware,SessionAware{

	
	private Map session;
	private Map application;
	private JSONObject msg;
	private JSONObject jsonData;
	private String userName = "";
	private KeyInfo keyInfo = new KeyInfo();
	private List<OCommodityRecord> zxList = new ArrayList<OCommodityRecord>();
	private String lidStr = "";
	private String hqID = "";
	private String curSysStatus = "";
	private Map<String,String> orderMap = new HashMap<String, String>();
	private String selectBAll = "";
	private Map<String,String> selectMap = new HashMap<String, String>();
	private String bcode = "";
	private String orderPrice = "";
	private OCommodityDao ocommodityDao;
	//需要保护的标号,标号:最高价格;
	private String protectBCodes = "";
	private List<Map> finList = new ArrayList<Map>();
	private String lastTime = "";
	/**
	 * 得到top2.jsp中,服务器生成的时间
	 * @return
	 */
	public String getHSServerTime(){
		jsonData = new JSONObject();
		getUserNameOrSession();
		jsonData.put("USERNAME", userName);
		keyInfo = (KeyInfo) application.get(userName+"KEYINFO");
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		
		//得到top2地址
		String top2Url = "http://" + systemInfo.getUrl()+"/"+systemInfo.getUrlContext()+"/submit/top2.jsp";
		long startTime = System.currentTimeMillis();
		String top2Str = execUrl(httpclient, localContext, top2Url);
		int subIndex = top2Str.indexOf("var i = t.getTime() - '")+23;
		long userTime = System.currentTimeMillis() - startTime;
		String timeLong = String.valueOf(Long.parseLong(top2Str.substring(subIndex,subIndex+13))+userTime);
		jsonData.put("timeLong", timeLong);
		System.out.println("userTime="+userTime);
		 
		return "getJson";
	}
	
	public String getOrderXML(){
		
		jsonData = new JSONObject();
		getUserNameOrSession();
		jsonData.put("USERNAME", userName);
		keyInfo = (KeyInfo) application.get(userName+"KEYINFO");
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		
		//得到hqXML地址
		String orderXML = "http://" + systemInfo.getUrl()+"/"+systemInfo.getUrlContext()+"/servlet/getOrderXML?partitionID="+systemInfo.getUrlContext().replace("vendue", "")+"&lastTime="+ lastTime + "&h="+new Random().nextInt();
		String orderXMLStr = execUrl(httpclient, localContext, orderXML);
		jsonData.put("responseXML", orderXMLStr);
		
		return "getJson";
	}
	
	
	public String getHqXML(){
		
		jsonData = new JSONObject();
		getUserNameOrSession();
		jsonData.put("USERNAME", userName);
		keyInfo = (KeyInfo) application.get(userName+"KEYINFO");
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		
		//得到hqXML地址
		String hqXML = "http://" + systemInfo.getUrl()+"/"+systemInfo.getUrlContext()+"/servlet/getHqXML?partitionID="+systemInfo.getUrlContext().replace("vendue", "")+"&hqID="+ hqID +"&h="+new Random().nextInt();
		String hqXMLStr = execUrl(httpclient, localContext, hqXML);
		jsonData.put("responseXML", hqXMLStr);
		
		return "getJson";
	}
	
	public String getCountdownTimeXML(){
		
		jsonData = new JSONObject();
		getUserNameOrSession();
		jsonData.put("USERNAME", userName);
		keyInfo = (KeyInfo) application.get(userName+"KEYINFO");
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		
		//得到hqXML地址
		String hqXML = "http://" + systemInfo.getUrl()+"/"+systemInfo.getUrlContext()+"/servlet/getCountdownTimeXML?partitionID="+systemInfo.getUrlContext().replace("vendue", "")+"&h="+new Random().nextInt();
		String hqXMLStr = execUrl(httpclient, localContext, hqXML);
		jsonData.put("responseXML", hqXMLStr);
		
		return "getJson";
	}
	
	/**
	 * 提交
	 * @return
	 */
	public String submit(){
		jsonData = new JSONObject();
		boolean flag = false;
		getUserNameOrSession();
		
		JbiaoUtil jbiaoUtil = (JbiaoUtil) application.get("JBIAOUTIL");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		
		String url = PropertiesUtil.getSubmitURL(jbiaoUtil, localContext, httpclient,  orderPrice,bcode,-1);
		HttpGet request = new HttpGet(url);
		httpclient.execute(request,localContext, new FutureCallback<HttpResponse>() {

            public void completed(final HttpResponse response) {
            	boolean flag = PropertiesUtil.isSuccessed(response.getEntity());
            	if(flag){
            		System.out.println("报单成功");
            		flag = true;
            	}else{
            		System.out.println("报单失败");
            	}
//            	System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
            }

            public void failed(final Exception ex) {
            	System.out.println("超时");
//            	System.out.println(request.getRequestLine() + "->" + ex);
            }

            public void cancelled() {
            	System.out.println("取消");
            }

        });
		jsonData.put("flag", flag);
		return "getJson";
	}
	
	/**
	 * 停止保护
	 * @return
	 */
	public String unProtect(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		getUserNameOrSession();
		KeyInfo k = (KeyInfo) application.get(userName + "KEYINFO");
		session.put("isRun","0");
		ocommodityDao.updateOCommodityAdjustStatus(k.getKeyId(), "未保护", dateStr, null);
		return "getJson";
	}
	
	/**
	 * 启动保护
	 */
	public void protect(){
		//第一次
		//得到已抢到的页面源码,分析得出都有哪些code
		//得到自选商品页面源码,找到需要抢的标
		//考虑在order.jsp页面得到最新价格
		//无限循环
		//方案1:监听已抢到XML,监听是否有保护的标丢失,同时监听自选商品XML,当出现更新时,查看是否有需要保护的标
		//方案2:监听已抢到的页面,当发现有丢失标时,去order.jsp页面得到最新价格,但这种情况,不能有多个key拥有同一个标
		getUserNameOrSession();
		//开始保护进程,1开始,0或null为未开始
		session.put("isRun","1");
		String isRun = "1";
		JbiaoUtil jbiaoUtil = (JbiaoUtil) application.get("JBIAOUTIL");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		KeyInfo k = (KeyInfo) application.get(userName + "KEYINFO");
		
		String orderListUrlStr = "";
		String finBCodes = "";
		List<String> submitBCodeList = new ArrayList<String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		//得到所有的保护标号
		String protectBCodeTmp = "";
		Map<String,Integer> bcodeMaxPriMap = new HashMap<String,Integer>();
		String[] protectBCodeArrs = protectBCodes.split(";");
		for(String s:protectBCodeArrs){
			protectBCodeTmp += s.split(":")[0] + ";";
			bcodeMaxPriMap.put(s.split(":")[0], Integer.valueOf(s.split(":")[1]));
			ocommodityDao.updateOCommodityAdjustStatus(k.getKeyId(), "已保护", dateStr, s.split(":")[0]);
		}
		
		
		while("1".equals(isRun)){
			orderListUrlStr = execUrl(httpclient, localContext, "http://" + systemInfo.getUrl()+"/"+systemInfo.getUrlContext()+"/submit/orderList.jsp");
			Reader reader = new StringReader(orderListUrlStr);
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
			finBCodes = getFinBCode(trElement);
			//得到需要竞标的标号
			submitBCodeList = PropertiesUtil.minus(protectBCodeTmp.split(";"), finBCodes.split(";"));
			//得到最高价格
			int maxPrice = -1;
			for(String code:submitBCodeList){
				try {
					maxPrice = bcodeMaxPriMap.get(code);
				} catch (Exception e) {
					maxPrice = -1;
					// TODO: handle exception
				}
				if(maxPrice==-1){
					continue;
				}
				
				//得到order.jsp页面源码
				String url = PropertiesUtil.getSubmitURL(jbiaoUtil, localContext, httpclient,  "-1", code,maxPrice);
				if("-1".equals(url)){
					bcodeMaxPriMap.remove(code);
					continue;
				}
				HttpGet request = new HttpGet(url);
				httpclient.execute(request,localContext, new FutureCallback<HttpResponse>() {

                    public void completed(final HttpResponse response) {
                    	boolean flag = PropertiesUtil.isSuccessed(response.getEntity());
                    	if(flag){
                    		System.out.println("报单成功");
                    	}else{
                    		System.out.println("报单失败");
                    	}
//                    	System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    }

                    public void failed(final Exception ex) {
                    	System.out.println("超时");
//                    	System.out.println(request.getRequestLine() + "->" + ex);
                    }

                    public void cancelled() {
                    	System.out.println("取消");
                    }

                });
				
				
				
			}
			isRun = session.get("isRun").toString();
		}
		
	}
	
	/**
	 * 得到orderList页面
	 * @return
	 */
	public String orderList(){
		getUserNameOrSession();
		
		JbiaoUtil jbiaoUtil = (JbiaoUtil) application.get("JBIAOUTIL");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		
		String orderListUrlStr = execUrl(httpclient, localContext, "http://" + systemInfo.getUrl()+"/"+systemInfo.getUrlContext()+"/submit/orderList.jsp");
		
		
		Reader reader = new StringReader(orderListUrlStr);
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
		
		finList = getTableContentFIN(trElement);
		lastTime = subString("var lastTime = \"", "\";//委托更新时间", orderListUrlStr);
		return "orderList1";
	}
	
	/**
	 * 得到HQ页面
	 * @return
	 */
	public String hq(){
		getUserNameOrSession();
		
		JbiaoUtil jbiaoUtil = (JbiaoUtil) application.get("JBIAOUTIL");
		CloseableHttpAsyncClient httpclient = (CloseableHttpAsyncClient) application.get(userName + "HTTPCLIENT");
		HttpClientContext localContext = (HttpClientContext) application.get(userName + "LOCALCONTEXT");
		
		//判断是否已经查询该key的所有标
		Map<String,String[]> keyBIAOMap = (Map<String, String[]>) session.get("keyBIAOMap");
		if(keyBIAOMap == null){
			keyBIAOMap = new HashMap<String, String[]>();
			//-----------测试-----------------
//			String[] arrs1 = new String[2];
//			arrs1[0] = "22880";
//			arrs1[1] = "未保护";
//			keyBIAOMap.put("GSDR112F001", arrs1);
//			String[] arrs2 = new String[2];
//			arrs2[0] = "23880";
//			arrs2[1] = "未保护";
//			keyBIAOMap.put("GSDR112F002", arrs2);
			//-----------测试-----------------
			KeyInfo k = (KeyInfo) application.get(userName + "KEYINFO");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String dateStr = df.format(new Date());
			List<OCommodityAdjust> ocas = ocommodityDao.getAdjustByKeyIdAndDate(
					k.getKeyId(), dateStr,-1);
			for(OCommodityAdjust o:ocas){
				String[] arrs = new String[2];
				arrs[0] = o.getLowPrice();
				arrs[1] = o.getStatus()==null?"未保护":o.getStatus();
				keyBIAOMap.put(o.getBcode(), arrs);
			}
		}
		
		
		
		System.out.println("------------获得自选商品页面源码开始-------------------");
		HttpGet getZXFrame = new HttpGet(jbiaoUtil.getZxFrame());
		Future<HttpResponse> futureZX = httpclient.execute(getZXFrame, localContext,null);
		HttpResponse responseZXFrame = null;
		HttpEntity entityZXFrame = null;
		try {
			responseZXFrame = futureZX.get();
			entityZXFrame = responseZXFrame.getEntity();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String zxFrameStr = "";
		try {
			zxFrameStr = EntityUtils.toString(entityZXFrame, "gbk");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lidStr = PropertiesUtil.getZXlidVal(zxFrameStr);
		//开始获取自选商品页面
		HttpGet getZXURL = new HttpGet(jbiaoUtil.getZxUrl()+lidStr);
		Future<HttpResponse> futureINFO = httpclient.execute(getZXURL, localContext,null);
		HttpResponse responseZXURL = null;
		HttpEntity entityZXURL = null;
		try {
			responseZXURL = futureINFO.get();
			entityZXURL = responseZXURL.getEntity();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String zxURLStr = "";
		try {
			zxURLStr = EntityUtils.toString(entityZXURL, "gbk");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("------------获得自选商品页面源码结束-------------------");
		
		//得到hqID
		hqID = subString("var hqID = '", "';//行情流水号", zxURLStr);
		curSysStatus = subString("var curSysStatus = '", "';//当前系统状态", zxURLStr);
		
		
		Reader reader = new StringReader(zxURLStr);
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
		
		zxList = getTableContent(trElement,keyBIAOMap);
		
		return "hq";
	}
	
	public String order(){
		
		//标的号;品种;提货库点;交易数量;提货日期;起报价;最新价格	
		String[] selectArrs = selectBAll.split(";");
		selectMap.put("b_code", selectArrs[0]);
		selectMap.put("b_pz", selectArrs[1]);
		selectMap.put("b_thkd", selectArrs[2]);
		selectMap.put("b_jysl", selectArrs[3]);
		selectMap.put("b_rq", selectArrs[4]);
		selectMap.put("b_qbj", selectArrs[5]);
		String zxjg = Float.valueOf(selectArrs[6])==0?selectArrs[5]:selectArrs[6];
		zxjg = String.valueOf(Float.valueOf(zxjg)+10);
		selectMap.put("b_zxjg", zxjg.substring(0,zxjg.lastIndexOf(".")));
		selectMap.put("maxPrice", selectArrs[7]);
		return "order";
	}
	
	/**
	 * 截取字符串
	 * @param start 开始字符
	 * @param end 结束字符
	 * @param zxURLStr 需要截取的字符
	 * @return
	 */
	private String subString(String start,String end,String zxURLStr){
		return zxURLStr.substring(zxURLStr.indexOf(start)+start.length(),zxURLStr.indexOf(end));
	}
	
	/**
	 * 得到所有标号
	 * @param element
	 * @return
	 */
	private String getFinBCode(Element element){
		String retStr = "";
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		int index = 0;
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement
						.getAllElements(HTMLElementName.TD);
				try {
					// 标号
					Element codeElement = tdList.get(2);
					String bcode = codeElement.getContent().toString();
					retStr +=bcode+";";
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
		return retStr;
	}
	
	
	/**
	 * 得到有效报价
	 * @param element
	 * @return
	 */
	private List<Map> getTableContentFIN(Element element) {
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		List<Map> retList = new ArrayList<Map>();
		int index = 0;
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement
						.getAllElements(HTMLElementName.TD);
				try {
					Map<String,String> finMap = new HashMap<String, String>();
					// 委托号
					Element wthElement = tdList.get(1);
					String wth = wthElement.getContent().toString();
					finMap.put("wth", wth);
					// 标号
					Element codeElement = tdList.get(2);
					String bcode = codeElement.getContent().toString();
					finMap.put("bcode", bcode);
					// 报价
					Element bjElement = tdList.get(3);
					finMap.put("bj", bjElement.getContent().toString());
					// 数量
					Element slElement = tdList.get(4);
					finMap.put("sl", slElement.getContent().toString());
					// 提交时间
					Element tjsjElement = tdList.get(5);
					finMap.put("tjsj", tjsjElement.getContent().toString());

					// 有效成交数量
					Element yxslElement = tdList.get(6);
					finMap.put("yxsl", yxslElement.getContent().toString());
					
					// 修改时间
					Element xgsjElement = tdList.get(7);
					finMap.put("xgsj", xgsjElement.getContent().toString());
					
					retList.add(finMap);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
		
		return retList;
	}
	
	
	/**
	 * 得到自选商品
	 * @param element
	 * @return
	 */
	private List<OCommodityRecord> getTableContent(Element element,Map<String,String[]> keyBIAOAll) {
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		List<OCommodityRecord> retList = new ArrayList<OCommodityRecord>();
		int index = 0;
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement
						.getAllElements(HTMLElementName.TD);
				try {
					OCommodityRecord ocr = new OCommodityRecord();
					// 标号
					Element codeElement = tdList.get(2);
					String bcode = codeElement.getContent().toString();
					ocr.setBcode(bcode);
					// 品种
					Element pzElement = tdList.get(3);
					ocr.setBtype(pzElement.getContent().toString());
					// 交货库点
					Element jhkdElement = tdList.get(4);
					ocr.setBstorage(jhkdElement.getContent().toString());
					// 交易数量
					Element numElement = tdList.get(5);
					String num = numElement.getContent().toString();
					ocr.setDealNum(num);
					

					// 交易数量
					Element dateElement = tdList.get(6);
					String date = dateElement.getContent().toString();
					ocr.setOcrDate(date);
					
					// 起报价
					Element qbjElement = tdList.get(7);
					String qbj = qbjElement.getContent().toString();
					ocr.setStartPrice(qbj);
					try {
						ocr.setStartPrice(qbj
								.substring(0, qbj.lastIndexOf(".")));
					} catch (Exception e) {
						// TODO: handle exception
					}
					// 最新价格
					Element zxElement = tdList.get(8);
					String zx = zxElement.getContent().toString();
					ocr.setZxjg(zx);
					
					// 是否有效
					Element isOKElement = tdList.get(10);
					String isOK = isOKElement.getContent().toString();
					ocr.setIsOK(isOK);
					
					String[] arrs = keyBIAOAll.get(bcode);
					//最高价格
					ocr.setMaxPrice(arrs[0]);
					//状态
					ocr.setStatus(arrs[1]);
					retList.add(ocr);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
		
		return retList;
	}
	
	/**
	 * 执行url返回页面结果
	 * @param httpclient
	 * @param localContext
	 * @param url
	 * @return
	 */
	private String execUrl(CloseableHttpAsyncClient httpclient,HttpClientContext localContext,String url){
		HttpGet getorderFlag = null;
		try {
			getorderFlag = new HttpGet(url);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		Future<HttpResponse> future = httpclient.execute(getorderFlag, localContext,null);
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = future.get();
			entity = response.getEntity();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlStr = "";
		try {
			urlStr = EntityUtils.toString(entity, "gbk");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlStr;
	}
	
	private void getUserNameOrSession(){
		if(userName == null || "".equals(userName)){
			userName = session.get("KEYINFO-USERNAME").toString();
		}
	}
	

	public void setSession(Map session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

	public void setApplication(Map application) {
		// TODO Auto-generated method stub
		this.application = application;
	}
	
	public JSONObject getMsg() {
		return msg;
	}

	public void setMsg(JSONObject msg) {
		this.msg = msg;
	}

	public JSONObject getJsonData() {
		return jsonData;
	}

	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public KeyInfo getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(KeyInfo keyInfo) {
		this.keyInfo = keyInfo;
	}
	
	public List<OCommodityRecord> getZxList() {
		return zxList;
	}

	public void setZxList(List<OCommodityRecord> zxList) {
		this.zxList = zxList;
	}

	public String getLidStr() {
		return lidStr;
	}

	public void setLidStr(String lidStr) {
		this.lidStr = lidStr;
	}

	public String getHqID() {
		return hqID;
	}

	public void setHqID(String hqID) {
		this.hqID = hqID;
	}

	public String getCurSysStatus() {
		return curSysStatus;
	}

	public void setCurSysStatus(String curSysStatus) {
		this.curSysStatus = curSysStatus;
	}

	public Map<String, String> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, String> orderMap) {
		this.orderMap = orderMap;
	}

	public String getSelectBAll() {
		return selectBAll;
	}

	public void setSelectBAll(String selectBAll) {
		this.selectBAll = selectBAll;
	}


	public Map<String, String> getSelectMap() {
		return selectMap;
	}

	public void setSelectMap(Map<String, String> selectMap) {
		this.selectMap = selectMap;
	}

	public String getBcode() {
		return bcode;
	}

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public OCommodityDao getOcommodityDao() {
		return ocommodityDao;
	}

	public void setOcommodityDao(OCommodityDao ocommodityDao) {
		this.ocommodityDao = ocommodityDao;
	}

	public String getProtectBCodes() {
		return protectBCodes;
	}

	public void setProtectBCodes(String protectBCodes) {
		this.protectBCodes = protectBCodes;
	}

	public List<Map> getFinList() {
		return finList;
	}

	public void setFinList(List<Map> finList) {
		this.finList = finList;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public static void main(String[] args) {
		String str = "取得客户端与服务器的时间差var t = new Date();var i = t.getTime() - '1442849027326';";
		System.out.println(str.indexOf("var i = t.getTime() - '")+23);
		System.out.println(str.substring(str.indexOf("var i = t.getTime() - '")+23,str.indexOf("var i = t.getTime() - '")+23+13));
	}
}
