package com.jb.unit;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.asprise.util.ocr.OCR;
import com.jb.dao.JBLogDao;
import com.jb.dao.KeyInfoDao;
import com.jb.dao.OCommodityDao;
import com.jb.model.CommodityClassify;
import com.jb.model.JbiaoUtil;
import com.jb.model.KeyInfo;
import com.jb.model.OCommodityAdjust;
import com.jb.model.SwitchStatus;
import com.jb.model.SystemInfo;

public class ProcessorMain implements Callable {

	/**
	 * error001:代表登录失败，验证码错误
	 */
	
	private JbiaoUtil jbiaoUtil = new JbiaoUtil();
	private KeyInfo keyInfo = new KeyInfo();
	//是否初始化，也就是从系统中抽取自选商品
	private boolean isInfo = false;
	//自选商品list,一下竞标到底
	private List<OCommodityAdjust> ocaList = new ArrayList<OCommodityAdjust>();
	//自选商品list,非一下竞标到底的标
	private List<CommodityClassify> ocaNoLowList = new ArrayList<CommodityClassify>();
	private JBLogDao jbLogDao;
	private KeyInfoDao keyInfoDao;
	private OCommodityDao ocommodityDao;
	private SwitchStatus ss = new SwitchStatus();
	private SystemInfo systemInfo = new SystemInfo();
	private int pageAll = 0;
	private String isLowFin = "";
	private Map application;
	private Map session;

	public ProcessorMain(JbiaoUtil jbiaoUtil, KeyInfo keyInfo,boolean isInfo,List<OCommodityAdjust> ocaList,JBLogDao jbLogDao,KeyInfoDao keyInfoDao,OCommodityDao ocommodityDao,SwitchStatus ss,List<CommodityClassify> ocaNoLowList,SystemInfo systemInfo,int pageAll,String isLowFin,Map application,Map session) {
		this.jbiaoUtil = jbiaoUtil;
		this.keyInfo = keyInfo;
		this.isInfo = isInfo;
		this.ocaList = ocaList;
		this.jbLogDao = jbLogDao;
		this.keyInfoDao = keyInfoDao;
		this.ocommodityDao = ocommodityDao;
		this.ss = ss;
		this.ocaNoLowList = ocaNoLowList;
		this.systemInfo = systemInfo;
		this.pageAll = pageAll;
		this.isLowFin = isLowFin;
		this.application = application;
		this.session = session;
	}

	public String call() throws ClientProtocolException, IOException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
		//判断session中是否有httpclient对象
		boolean isSessionHave = true;
		// 程序开始执行时间
		long startTime = System.currentTimeMillis();
		System.out.println("sssssssss" + keyInfo.getUserName() + ":"
				+ startTime);
	    CloseableHttpAsyncClient httpclient = null;
	    //reate local HTTP context
	    HttpClientContext localContext = null;
	    try {
//	    	httpclient = (CloseableHttpAsyncClient) session.get("HTTPCLIENT");
//	    	localContext = (HttpClientContext) session.get("LOCALCONTEXT");
		} catch (Exception e) {
			// TODO: handle exception
		}
	   //判断httpclient对象为null，则需要重新获取对象
//	   if(httpclient==null){
		   isSessionHave = false;
		   RequestConfig requestConfig = RequestConfig.custom()
		            .setSocketTimeout(500000)
		            .setConnectTimeout(500000).build();
		   httpclient = HttpAsyncClients.custom()
		            .setDefaultRequestConfig(requestConfig)
		            .build();
		   CookieStore cookieStore = new BasicCookieStore();
		   localContext = HttpClientContext.create();
		   localContext.setCookieStore(cookieStore);
	       httpclient.start();
//	   }
	   
//	   if(!isSessionHave){
		// 验证码地址
	       HttpGet httpget = new HttpGet(jbiaoUtil.getYzmUrl());
	       // Pass local context as a parameter
	       Future<HttpResponse> future = httpclient.execute(httpget, localContext,null);
			/** --------------测试区域-------------- */
			
			HttpResponse response1 = null;
			HttpEntity entity1 = null;
			try {
				response1 = future.get();
				entity1 = response1.getEntity();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			BufferedInputStream in = null;
			// InputStream in = null;
			// FileOutputStream bos= null;
			String randNumStr = "";
			try {
				BiImage bi = new BiImage();
				in = new BufferedInputStream(entity1.getContent());

				bi.initialize(in);
				//		
				BufferedImage image = bi.monochrome(jbiaoUtil.getLocalCatalog()
						+ keyInfo.getTempImgNm());
				System.out.println(System.getProperty("java.library.path"));

				randNumStr = new OCR().recognizeEverything(image);
				// replace O to 0
				randNumStr = randNumStr.replace("O", "0");
				System.out.println("=============" + randNumStr + "=========");
				
				// bos = new FileOutputStream("c:/jb/testheibai.jpg");

				// //直接输入验证码。。。。。。。。。。。。。。
				// bos = new FileOutputStream("c:/jb/yanzhengma.jpg");
				// int bytesRead = 0;
				// byte[] buffer = new byte[2048];
				// while (in.read(buffer) > 0) {
				// bos.write(buffer);
				// }
				// bos.flush();
				// bos.close();
				// Scanner scan = new Scanner(System.in);
				// System.out.print("输入验证码");
				// str = scan.nextLine();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					// bos.close();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			String loginUrl = jbiaoUtil.getLoginUrl()
					.replace("(USERNAME)", keyInfo.getUserName())
					.replace("(PASSWORD)", keyInfo.getPassWord())
					.replace("(KEYPWD)", keyInfo.getKeyWd())
					.replace("(RANDNUM)", randNumStr)
					.replace("(SUBMIT)", keyInfo.getSubmit())
					.replace("(KCODE)", keyInfo.getKcode())
					.replace("\r", "")
					.replace("\n", "");
			HttpGet httpgetLogin = null;
			try {
				httpgetLogin = new HttpGet(loginUrl);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				return "error001";
			}
			
			String loginSource = "";
			HttpResponse response = null;
			HttpEntity entity = null;
			Future<HttpResponse> futureLogin = null;
			// 判断是否登录成功，页面返回window.location='main.jsp'为登录成功
			while (true) {
				futureLogin = httpclient.execute(httpgetLogin, localContext,null);
				try {
					response = futureLogin.get();
					entity = response.getEntity();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				loginSource = EntityUtils.toString(entity, "gbk");
				if (loginSource.indexOf("window.location='main.jsp'") != -1) {
//					createFile(loginSource, jbiaoUtil.getLocalCatalog() + keyInfo.getLoginSourceNm());
					//login success
					ss.setIsLoginOK(1);
					ocommodityDao.saveOrUpdateSS(ss);
					//将httpclient对象和localContext放到session中
					
					
					application.put(keyInfo.getUserName()+"HTTPCLIENT",httpclient);
					application.put(keyInfo.getUserName()+"LOCALCONTEXT",localContext);
					application.put(keyInfo.getUserName()+"KEYINFO", keyInfo);
			    	session.put("KEYINFO-USERNAME", keyInfo.getUserName());
					break;
				} else {
					System.out.println("------“" + keyInfo.getUserName()+ "”登录失败--------");
//					ss.setIsLoginOK(0);
//					ocommodityDao.saveOrUpdateSS(ss);
					return "error001";
				}
			}
//	   }
       
			
//		if(1==1){
//			//--测试--
//			return "";
//		}

		// Scanner scan = new Scanner(System.in);
		// System.out.print("输入验证码");
		// String str = scan.nextLine();

		/** -----------开始登陆------------- */
		//开始登陆
		//username=(USERNAME)&password=(PASSWORD)&keypwd=(KEYPWD)&randNumInput=(RANDNUM)&Submit=(SUBMIT)&kcode=(KCODE)&logType=1&netType=1
		
		
		// HttpResponse response = httpclient.execute(httpget);

		// kx.getContent(entity);
		// FileOutputStream bos12= new FileOutputStream("c:/jb/" +
		// userInfo.getLoginSourceName());
		
		/*------------自选商品抽取-----------------*/
		if(isInfo){
			if(pageAll==0){
				String zxURLStr = "";
				// 判断是否测试
				if ("0".equals(jbiaoUtil.getIsTest())) {
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
					
					String zxFrameStr = EntityUtils.toString(entityZXFrame, "gbk");
					String lidStr = PropertiesUtil.getZXlidVal(zxFrameStr);
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
					
					zxURLStr = EntityUtils.toString(entityZXURL, "gbk");
					System.out.println("------------获得自选商品页面源码结束-------------------");
					
				}else {
					zxURLStr = PropertiesUtil.getTestDate("c:\\testSrc\\hq[1].html");
				}
				return zxURLStr;
			}else{
				String zxURLStr = "";
				// 判断是否测试   此处为获取所有商品使用的方法
				if ("0".equals(jbiaoUtil.getIsTest())) {
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
					
					String zxFrameStr = EntityUtils.toString(entityZXFrame, "gbk");
					String lidStr = PropertiesUtil.getZXlidVal(zxFrameStr);
					//开始获取自选商品页面
					HttpGet getZXURL = new HttpGet("http://124.127.44.51:20022/vendue8/commodityList.jsp?code=&storage=&add=&pageIndex="+pageAll+"&lid="+lidStr);
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
					
					zxURLStr = EntityUtils.toString(entityZXURL, "gbk");
					System.out.println("------------获得自选商品页面源码结束-------------------");
					
				}else {
					zxURLStr = PropertiesUtil.getTestDate("c:\\testSrc\\sysp.html");
				}
				return zxURLStr;
			}
			
		}
		/*------------自选商品抽取-----------------*/
		
		
		// 提交请求地址
		//String hosturl = jbiaoUtil.getSubmitUrl();
		// 得到orderFlag地址
		HttpGet getorderFlag = new HttpGet(jbiaoUtil.getOrderUrl());
		// HttpGet getTest111 = new
		// HttpGet("http://124.127.44.53:20023/vendue8/submit/top2.jsp");
		String orderFlagValue = "";
		String orderStr = "";
		try {
			orderFlagValue = (String) session.get("ORDERFLAGVALUE");
			if(orderFlagValue == null){
				orderFlagValue = "";
			}
		} catch (Exception e) {
			// TODO: handle exception 
			orderFlagValue = "";
		}
		//当orderFlagValue为空时，需要抽取
		if("".equals(orderFlagValue)){
			// 判断是否测试
			if ("0".equals(jbiaoUtil.getIsTest())) {
				while (true) {
					Future<HttpResponse> futureOrder = httpclient.execute(getorderFlag, localContext,null);
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
					
					orderStr = EntityUtils.toString(entity33, "gbk");
					//------------处理下单页隐藏域中打乱字符处理
					if (!"".equals(orderFlagValue)) {
	//					JBDate jbDate = new JBDate();
	//					jbDate.setJbId(null);
	//					jbDate.setStatus(0);
	//					jbDate.setDateInfo(new Timestamp(new Date().getTime()));
	//					keyInfoDao.setJBDate(jbDate);
						session.put("ORDERFLAGVALUE", orderFlagValue);
						break;
					}
				}
			}
		}

		

		// String[] strarrs = new String[]{
		// "orderPrice=50430&code=GSDY12F027&totalAmount=300&orderFlag="+orderFlagValue+otherParam
		// };
		/**--------------开始注释----------------*/
		//竞标一下到底使用
		if("1".equals(isLowFin)){
			String[] strarrs = new String[ocaList.size()];
			// 延迟时间
			String[] times = new String[ocaList.size()];
			int index = 0;
			//分割或白条标识
			String fgOrBt = "";
			String params = "";
			for (Iterator iterator = ocaList.iterator(); iterator.hasNext();) {
				
				OCommodityAdjust oca = (OCommodityAdjust) iterator.next();
				//判断是分割冻猪瘦肉还是白条
				if(oca.getBtype().indexOf("分割冻猪瘦肉") != -1){
					fgOrBt = "[FG]";
				}else {
					fgOrBt = "[BT]";
				}
				//白条或分割;竞拍价格;竞标code;吨数
				params = fgOrBt + ";"+ oca.getLowPrice() + ";" + oca.getBcode() + ";" + oca.getDealNum();
				strarrs[index] = params;
				try {
					if(oca.getDelayTime()!=null && !"".equals(oca.getDelayTime())){
						times[index] = oca.getDelayTime();
					}else{
						times[index] = "0";
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					times[index] = "0";
				}
				index++;
			}

			exeThea(strarrs, httpclient, localContext,startTime, times);
			//此处更新switch_stat的一次到底竞标标识（1）
			//ss.setIsLowFin(1);
			//ocommodityDao.saveOrUpdateSS(ss);
		}
		
		/**---------------------以下是10元降的代码区域-------------------------*/
		if("0".equals(isLowFin)){
//			String finStr = "";
			String zxStr = "";
			//记录循环的次数，主要是第一次循环使用
			int indexNum = 0;
			//处理后的List<Map>，也就是在开始替换nowBidList
			List<Map> nowBidLastList = null;
			List finBidList = null;
			List<Map> fenleiList = null;
			//记录已经完成页面的XML的lastTime
			String T = "";
			//记录自选商品行情ID
			String hqID = "";
			//循环次数
			long index = 0l;
			while(1>0){
				//每次+10元竞标开始状态
				String START_STATUS = "0";
				try {
					START_STATUS = application.get("START_STATUS").toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if("1".equals(START_STATUS)){
					if(index==0){
						//清空ZX_COMMODITY和FIN_COMMODITY
						String delStr = "delete from ZX_COMMODITY where KEY_ID='"+keyInfo.getKeyId()+"'";
//						TTDbUnit.delTTForSql(delStr);
						ocommodityDao.execSql(delStr);
						//得到有效竞标列表
//						finStr = getWebPageSource(httpclient, "c:\\testSrc\\orderList[1].txt", false);
						//得到自选商品列表
						zxStr = getWebPageSource(httpclient,localContext, "c:\\testSrc\\hq[1].html", true);
						//根据有效竞标列表页面源码，得到所有的有效标Map<标号,有效价格>
//						finBidList = getBidFinPageSource(finStr);
						//得到自选商品标的数据
						nowBidLastList = this.getZXSPBySource(zxStr);
						//3333.htl
						//<R><S><SID>1138911</SID><C>CNMDN13F906</C><P>40010.00</P><A>249.00000</A><VA>0.00000</VA><ST>2013-10-22/14:02:54</ST><MT>2013-10-22/14:30:09</MT></S><S><SID>1140197</SID><C>CNMDN13F908</C><P>50000.00</P><A>199.20000</A><VA>199.20000</VA><ST>2013-10-22/14:15:14</ST><MT>2013-10-22/14:15:14</MT></S><S><SID>1139922</SID><C>CNMDN13F908</C><P>40010.00</P><A>199.20000</A><VA>0.00000</VA><ST>2013-10-22/14:12:31</ST><MT>2013-10-22/14:15:14</MT></S><S><SID>1138885</SID><C>CNMDN13F010</C><P>40080.00</P><A>298.80000</A><VA>0.00000</VA><ST>2013-10-22/14:02:34</ST><MT>2013-10-22/14:02:41</MT></S><S><SID>1138798</SID><C>CNMDN13F015</C><P>40010.00</P><A>99.60000</A><VA>0.00000</VA><ST>2013-10-22/14:01:39</ST><MT>2013-10-22/14:02:28</MT></S><T>2013-10-22/14:30:09</T></R>
						//<R><Q><C>CNMDN13F718</C><P>40000.0</P><A>600316</A><CD>26</CD><ID>1862883</ID></Q><N>829</N><S>2</S><U>1</U><T1>14:00:00</T1><T2>14:30:30</T2></R>
						
						//将第一次从页面获得的已经竞标成功的添加到内存数据库中
//						TTDbUnit.insertFin(finBidList, keyInfo.getKeyId());
						//将第一次从页面获取的自选商品添加到内存数据库中
//						TTDbUnit.insertZX(nowBidLastList, keyInfo.getKeyId());
						ocommodityDao.insertZX(nowBidLastList, keyInfo.getKeyId());
						//从内存数据库中得到分类
//						fenleiList = TTDbUnit.getCommodityClassifyByKeyId(keyInfo.getKeyId());
						//检查是否需要按照优先顺序排序--?
						fenleiList = ocommodityDao.getFenLei(keyInfo.getKeyId());
					}
					index++;
//					if(index==10000){
//						fenleiList = TTDbUnit.getCommodityClassifyByKeyId(keyInfo.getKeyId());
//						index = 1l;
//					}
					
					//记录有效的标号
					Map yxMap = new HashMap();
					try {
						yxMap = XmlUtil.getFIN(this.getZXorFin(false, httpclient,localContext,T,""));
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String yxBHS = yxMap.get("yxBHS").toString();
					T = yxMap.get("T").toString();
					List<Map> zxList = new ArrayList<Map>();
					try {
						zxList = XmlUtil.getZX(this.getZXorFin(true, httpclient,localContext,"",hqID));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					Processor10 r0 = new Processor10(orderFlagValue, zxList, keyInfo, ocommodityDao, fenleiList, yxBHS, otherParam, hosturl, httpclient, cookies, response, entity, jbLogDao);
					//根据自选商品code，更新价格和状态
					for (Iterator iterator = zxList.iterator(); iterator.hasNext();) {
						Map map = (Map) iterator.next();
						String bcode = map.get("C").toString();
						String price = map.get("P").toString();
						String vipCode = map.get("A").toString();
						hqID = map.get("ID").toString();
						//判断是否有效标
						String isYX = "0";
						//此处增加判断其他所有key的code--?
						if(keyInfo.getUserName().equals(vipCode)){
							isYX = "1";
						}
//						TTDbUnit.updateZXPriceByBCode(bcode, price, vipCode,isYX);
						//更新标的价格信息,以及添加日志,可以考虑先放到内存中,或增加使用内存数据库--?
						// /vendue11/servlet/getOrderXML?partitionID=11&lastTime=2013-12-17/16:14:24&h=Tue%20Dec%2017%2016:22:07%202013 HTTP/1.1
						// /vendue11/servlet/getHqXML?partitionID=11&hqID=1872040&h=Tue%20Dec%2017%2016:22:07%202013 HTTP/1.1
						//区别?--?
						ocommodityDao.updateZXPriceByBCode(bcode, price, vipCode, isYX);
					}
					//循环分类list
					for (Iterator iterator = fenleiList.iterator(); iterator.hasNext();) {
						Map map = (Map) iterator.next();
						float lowPrice = Float.parseFloat(map.get("LOW_PRICE").toString());
						int jxNum = Integer.parseInt(map.get("JX_NUM").toString());
						String fenleiCode = map.get("CC_CODE").toString();
						//根据keyId和指标分类code，得到非有效标列表和有效的个数,考虑加入到内存数据库中--?
//						Map zxMap = TTDbUnit.getZXListByKeyIdFenlei(keyInfo.getKeyId(), fenleiCode);
						Map zxMap = ocommodityDao.getZXListByKeyIdFenlei(keyInfo.getKeyId(), fenleiCode);
						//得到有效标的个数
						int yxNum = Integer.parseInt(zxMap.get("yxNum").toString());
						//无效标号和当前价格列表
						List<Map> wzzxList = (List<Map>) zxMap.get("zxList");
						//此分类还差多少标
						int syNum = jxNum - yxNum;
						List<HttpGet> requests = new ArrayList<HttpGet>();
						int requestNum = 0;
						if(syNum>0){
							int forNum = 0;
							for (Iterator iterator2 = wzzxList.iterator(); iterator2
									.hasNext();) {
								
								Map map2 = (Map) iterator2.next();
								String bcode = map2.get("B_CODE").toString();
//								String dealNum = map2.get("DEAL_NUM").toString();
								if(yxBHS.indexOf(bcode)!=-1){
									continue;
								}
								forNum++;
								if(syNum<forNum){
									break;
								}
								float dqPriceF = Float.parseFloat(map2.get("DQ_PRICE").toString());
								int dqPrice = (int)dqPriceF;
								//判断是否进行
								boolean flag = true;
								if(jbiaoUtil.getSystemInfo().getIsAddCut()==0){
									//判断当前价格-10，是否小于最高底线
									if((dqPrice-10)<lowPrice){
										flag = false;
									}
								}else {
									//判断当前价格+10，是否大于最高底线
									if((dqPrice+10)>lowPrice){
										flag = false;
									}
								}
								
								if(flag){
									//向服务器发起下单申请
//									String d = String.valueOf(Float.parseFloat(dealNum)*100);
//									if("99".equals(fenleiCode)){
//										d = "9960";
//									}else if("199".equals(fenleiCode)){
//										d = "19920";
//									}else{
//										d= "29880";
//									}
									//判断加还是减10
									if(jbiaoUtil.getSystemInfo().getIsAddCut()==0){
										dqPrice = dqPrice - 10;
									}else {
										dqPrice = dqPrice + 10;
									}
									String url = PropertiesUtil.getSubmitURL(jbiaoUtil, localContext, httpclient,  String.valueOf(dqPrice), bcode,-1);
									HttpGet request = new HttpGet(url);
									httpclient.execute(request,localContext, new FutureCallback<HttpResponse>() {

					                    public void completed(final HttpResponse response) {
					                    	boolean flag = PropertiesUtil.isSuccessed(response.getEntity());
					                    	if(flag){
					                    		System.out.println("报单成功");
					                    	}else{
					                    		System.out.println("报单失败");
					                    	}
//					                    	System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
					                    }

					                    public void failed(final Exception ex) {
					                    	System.out.println("超时");
//					                    	System.out.println(request.getRequestLine() + "->" + ex);
					                    }

					                    public void cancelled() {
					                    	System.out.println("取消");
					                    }

					                });
//									requests.add(new HttpGet(url));
								}
								
							}
							
//							//开始提交
//							for(final HttpGet request: requests){
//								httpclient.execute(request,localContext, new FutureCallback<HttpResponse>() {
//
//				                    public void completed(final HttpResponse response) {
//				                    	boolean flag = isSuccessed(response.getEntity());
//				                    	if(flag){
//				                    		System.out.println("报单成功");
//				                    	}else{
//				                    		System.out.println("报单失败");
//				                    	}
//				                    	System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
//				                    }
//
//				                    public void failed(final Exception ex) {
//				                    	System.out.println("超时");
//				                    	System.out.println(request.getRequestLine() + "->" + ex);
//				                    }
//
//				                    public void cancelled() {
//				                    	System.out.println("取消");
//				                    }
//
//				                });
//							}
						}
						
					}
				}else{
					index = 0l;
//					//不停循环order.jsp页面，避免session超时
//					HttpResponse response12 = httpclient.execute(getTest111);
//					HttpEntity entity33 = response12.getEntity();
//					String orderStrTMP = EntityUtils.toString(entity33, "gbk");
//					List<Map> zxList = new ArrayList<Map>();
//					try {
//						zxList = XmlUtil.getZX(this.getZXorFin(true, httpclient,localContext,""));
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (ExecutionException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					//根据自选商品code，更新价格和状态
//					for (Iterator iterator = zxList.iterator(); iterator.hasNext();) {
//						Map map = (Map) iterator.next();
//						String bcode = map.get("C").toString();
//						String price = map.get("P").toString();
//						String vipCode = map.get("A").toString();
//						//判断是否有效标
//						String isYX = "0";
//						if(keyInfo.getUserName().equals(vipCode)){
//							isYX = "1";
//						}
////						TTDbUnit.updateZXPriceByBCode(bcode, price, vipCode,isYX);
//						ocommodityDao.updateZXPriceByBCode(bcode, price, vipCode, isYX);
//					}
					
					Future<HttpResponse> futureOrder = httpclient.execute(getorderFlag, localContext,null);
					HttpResponse response12 = null;
					try {
						response12 = futureOrder.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				
				
				//查询对比，是否有丢标的，若有则重新发起
//				for (Iterator iterator = nowBidList.iterator(); iterator.hasNext();) {
//					Map map = (Map) iterator.next();
//					//第一次循环，在标的分类中，找到最合适的标加/减
//					nowBidLastList = null;
//					nowBidLastList = getSuitableBid(nowBidLastList, zxStr, map,finBidList,indexNum);
//				}
				
	//			for (Iterator iterator = ocaNoLowList.iterator(); iterator.hasNext();) {
	//				OCommodityAdjust oca = (OCommodityAdjust) iterator.next();
	//				for (Iterator iterator2 = nowBidLastList.iterator(); iterator2
	//						.hasNext();) {
	//					Map map = (Map) iterator2.next();
	//					if(oca.getBtype().equals(map.get("btype").toString()) && oca.getBstorage().equals(map.get("bstorage").toString())){
	//						nowBidLastList.remove(map);
	//					}else {
	//						Map mapOca = new HashMap();
	//						mapOca.put("bcode", oca.getBcode());
	//						mapOca.put("dealnum", oca.getDealNum());
	//						mapOca.put("nowprice", oca.getStartPrice());
	//						mapOca.put("addLowPrice", oca.getAddLowPrice());
	//						mapOca.put("btype", oca.getBtype());
	//						mapOca.put("bstorage", oca.getBstorage());
	//						nowBidList.add(mapOca);
	//					}
	//				}
	//				
	//			}
				
	//			if(indexNum>0){
//					nowBidList = nowBidLastList;
//					String paramTmp = "";
//					String strarrsTmp = "";
//					for (Iterator iterator = nowBidList.iterator(); iterator.hasNext();) {
//						Map objMap = (Map) iterator.next();
//	//					while(1>0){
//							//判断是否大于或小于最低价
//							double nowprice = getNowprice(objMap);
//							if(nowprice==-1){
//								nowBidList.remove(objMap);
//								break;
//							}
//							paramTmp = "orderPrice="+String.valueOf(nowprice).substring(0,String.valueOf(nowprice).lastIndexOf("."))+"&code="+objMap.get("bcode").toString()+"&totalAmount="+objMap.get("dealnum").toString().substring(0,objMap.get("dealnum").toString().lastIndexOf("."))+"&orderFlag=";
//							strarrsTmp = paramTmp + orderFlagValue + otherParam;
//							Processor r0 = new Processor(strarrsTmp,hosturl,httpclient,cookies, response, entity,0,keyInfo,null,jbLogDao,true);
//							boolean flag = r0.run1();
//							try {
//						    	Thread.sleep(10000);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}
//	//						if(flag){
//	//							break;
//	//						}else{
//	//							objMap.put("nowprice", nowprice);
//	//						}
//	//					}
//						
//					}
					
					
	//			}
				
				
//				indexNum++;
//				
//				//退出条件
//				if(nowBidList.size()==0){
//					break;
//				}
			}
			
			/**
			 * 【首先将自选商品里的code存放在session里；第一次登陆成功后，不停的访问order.jsp页面，然后不停的从application里取状态位，位1时开始全速+/-10；记录最早得到order.jsp的时间，然后可以查看竞标系统运行的时间】
			 * 执行非一下到底的标(第一次进来)
			 * 1、将ocaNoLowList中标的编码放到List<Map>中
			 * 2、得到自选商品的源码内容
			 * 3、根据标的编码，在源码内容里找到同此标同一分类中有效价最高/最低的标，并将“标号、现有效价格、吨数”放到Map中
			 * 4、for刚得到的List<Map>
			 * 5、执行每一个标的时候，若第一次失败，则判断-/+10以后的金额，是否大于/小于最低价（若不大于/小于则继续减/加10提交）
			 * 6、提交成功以后，将标号和金额放到一个Map<标号,金额>中
			 * 执行非一下到底的标(第一次以后进来)
			 * 1、得到竞标成功的页面源码内容  【并筛选只得到失去的标】
			 * 2、得到每一个标的标号，然后在6中的Map中得到金额，同现在的比较，若相等，则不需要再处理，如果不相等，则将标号放到new ArrayList中
			 * 3、重复2-6
			 * 
			 * 结束退出条件：当所有标均超了最低/最高价
			 */
		}
		/**---------------------以上是10元降的代码区域-------------------------*/
		
		// 程序开始执行时间
		long endTime = System.currentTimeMillis();
		System.out.println("eeeeeeeee" + keyInfo.getUserName() + ":" + endTime);
		/**--------------结束注释----------------*/
		
		return "";
	}
	
	
	/**
	 * 得到自选商品或已经竞选完成的xml
	 * @param isZX   true 自选   false 已经完成
	 * @param httpclient
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	private String getZXorFin(boolean isZX,CloseableHttpAsyncClient httpclient,HttpClientContext localContext,String T,String hqID) throws ClientProtocolException, IOException, InterruptedException, ExecutionException{
		String xmlStr = "";
		String url = "";
		if(isZX){
			//自选
			if("".equals(hqID)){
				hqID = "0";
				System.out.println(hqID);
			}
			url = "http://"+systemInfo.getUrl()+"/servlet/getHqXML?partitionID=8&hqID="+hqID+"&h="+System.currentTimeMillis();
		}else{
			if("".equals(T)){
				T = "1999-09-09/09:09:09";
			}
			//已经竞选完成的
			url = "http://"+systemInfo.getUrl()+"/servlet/getOrderXML?partitionID=11&lastTime="+T+"&h="+System.currentTimeMillis();
		}
		// 判断是否测试
		if ("0".equals(jbiaoUtil.getIsTest())) {
//			System.out.println("------------获得自选商品页面源码开始-------------------");
			HttpGet getZXFrame = new HttpGet(url);
			Future<HttpResponse> futureZX = httpclient.execute(getZXFrame, localContext,null);
			HttpResponse responseZXFrame = futureZX.get();
			HttpEntity entityZXFrame = responseZXFrame.getEntity();
			xmlStr = EntityUtils.toString(entityZXFrame, "gbk");
//			System.out.println("------------获得自选商品页面源码结束-------------------");
			
		}else {
			if(isZX){
				//<R><S><SID>1138911</SID><C>CHBDR137F470</C><P>26000.00</P><A>300.00000</A><VA>0.00000</VA><ST>2013-10-22/14:02:54</ST><MT>2013-10-22/14:30:09</MT></S><S><SID>1140197</SID><C>CHBDR137F476</C><P>26000.00</P><A>300.00000</A><VA>300.00000</VA><ST>2013-10-22/14:15:14</ST><MT>2013-10-22/14:15:14</MT></S><T>2013-10-22/14:30:09</T></R>
				//<R><Q><C>CHBDR137F470</C><P>26000.00</P><A>600316</A><CD>26</CD><ID>1862883</ID></Q><Q><C>CHBDR137F475</C><P>26000.0</P><A>600416</A><CD>26</CD><ID>1862883</ID></Q><N>829</N><S>2</S><U>1</U><T1>14:00:00</T1><T2>14:30:30</T2></R>
				
				xmlStr = "<R><Q><C>CHBDR137F470</C><P>26000.00</P><A>600316</A><CD>26</CD><ID>1862883</ID></Q><Q><C>CHBDR137F475</C><P>26000.0</P><A>600416</A><CD>26</CD><ID>1862883</ID></Q><N>829</N><S>2</S><U>1</U><T1>14:00:00</T1><T2>14:30:30</T2></R>";
			}else{
				xmlStr = "<R><S><SID>1138911</SID><C>CHBDR137F470</C><P>26000.00</P><A>300.00000</A><VA>0.00000</VA><ST>2013-10-22/14:02:54</ST><MT>2013-10-22/14:30:09</MT></S><S><SID>1140197</SID><C>CHBDR137F476</C><P>26000.00</P><A>300.00000</A><VA>300.00000</VA><ST>2013-10-22/14:15:14</ST><MT>2013-10-22/14:15:14</MT></S><T>2013-10-22/14:30:09</T></R>";
			}
			
		}
		
		return xmlStr;
	}
	
	
	//得到下一个竞标的金额，-1为不符合要求
	private double getNowprice(Map objMap){
		//判断是否大于或小于最低价
		double nowprice = Double.parseDouble(objMap.get("nowprice").toString());
		double addLowPrice = Double.parseDouble(objMap.get("addLowPrice").toString());
		if(systemInfo.getIsAddCut()==0){
			nowprice = nowprice - 10;
			if(nowprice<addLowPrice){
				nowprice = -1;
			}
		}else{
			nowprice = nowprice + 10;
			if(nowprice>addLowPrice){
				nowprice = -1;
			}
		}
		return nowprice;
	}
	
	/**
	 * 得到页面源码
	 * @param httpclient
	 * @param testPath
	 * @param isZX  true自选商品 false完成的列表
	 * @return
	 */
	private String getWebPageSource(CloseableHttpAsyncClient httpclient,HttpClientContext localContext,String testPath,boolean isZX){
		String zxURLStr = "";
		try {
			// 判断是否测试
			if ("0".equals(jbiaoUtil.getIsTest())) {
				System.out.println("------------获得页面源码开始-------------------");
				String lidStr = "";
				if(isZX){
					HttpGet getZXFrame = new HttpGet(jbiaoUtil.getZxFrame());
					Future<HttpResponse> futureZX = httpclient.execute(getZXFrame, localContext,null);
					HttpResponse responseZXFrame = futureZX.get();
					HttpEntity entityZXFrame = responseZXFrame.getEntity();
					String zxFrameStr = EntityUtils.toString(entityZXFrame, "gbk");
					lidStr = PropertiesUtil.getZXlidVal(zxFrameStr);
				}
				//开始获取自选商品页面
				String urlStr = "";
				if(isZX){
					urlStr = jbiaoUtil.getZxUrl()+lidStr;
				}else{
					urlStr = jbiaoUtil.getJbFinUrl();
				}
				HttpGet getZXURL = new HttpGet(urlStr);
				Future<HttpResponse> futureZXURL = httpclient.execute(getZXURL, localContext,null);
				HttpResponse responseZXURL = futureZXURL.get();
				HttpEntity entityZXURL = responseZXURL.getEntity();
				zxURLStr = EntityUtils.toString(entityZXURL, "gbk");
				System.out.println("------------获得页面源码结束-------------------");
				
			}else {
				zxURLStr = PropertiesUtil.getTestDate(testPath);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return zxURLStr;
	}
	
	private Map getByteJhkd(Element trElement0,String bcode){
		Map map = new HashMap();
		List<Element> trList = trElement0.getAllElements(HTMLElementName.TR);
		int index = 0;
		//临时存放标信息的map
		Map tempMap = new HashMap();
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				try {
					// 标号
					Element codeElement = tdList.get(2);
					// 品种
					Element pzElement = tdList.get(3);
					// 交货库点
					Element jhkdElement = tdList.get(4);
					if(bcode.equals(codeElement.getContent().toString())){
						map.put("btype", pzElement.getContent().toString());
						map.put("bstorage", jhkdElement.getContent().toString());
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;
		}
		return map;
	}
	
	/**
	 * 得到最合适的标
	 * @return
	 */
	private List<Map> getSuitableBid(List<Map> nowBidLastList,String pageSource,Map bidMap,List finBidList,int indexNum){
		Reader reader = new StringReader(pageSource);
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
		Element trElement0 = elementList.get(0);
		
		List<Element> trList = trElement0.getAllElements(HTMLElementName.TR);
		int index = 0;
		//临时存放标信息的map
		Map tempMap = new HashMap();
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
				try {
					// 标号
					Element codeElement = tdList.get(2);
					// 品种
					Element pzElement = tdList.get(3);
					// 交货库点
					Element jhkdElement = tdList.get(4);
					// 交易数量
					Element numElement = tdList.get(5);
					// 起报价
					Element qbjElement = tdList.get(7);
					// 最新价格
					Element zxjElement = tdList.get(8);
					//判断品种、交货库点相同的，认为是该标的分类表
					//根据code得到类型和库点
					String bcode = "";
					Map finMap = null;
					boolean flag = false;
					//判断相等的数量
					int errorNum = 0;
					if(indexNum>0){
						if(finBidList.size()>0){
							for (Iterator iterator2 = finBidList.iterator(); iterator2
									.hasNext();) {
								bcode = (String) iterator2.next();
								finMap = getByteJhkd(trElement0, bcode);
								if(finMap.get("btype").toString().equals(pzElement.getContent().toString()) && finMap.get("bstorage").toString().equals(jhkdElement.getContent().toString())){
//									if(pzElement.getContent().toString().equals(bidMap.get("btype")) && jhkdElement.getContent().toString().equals(bidMap.get("bstorage").toString())){
										errorNum++;
										break;
//									}
								}
							}
							
						}
						
					}
					if(indexNum==0){
						if(pzElement.getContent().toString().equals(bidMap.get("btype")) && jhkdElement.getContent().toString().equals(bidMap.get("bstorage").toString())){
							flag = true;
						}
					}else{
						
						if(errorNum>0 || finBidList.size()==0){
							if(pzElement.getContent().toString().equals(bidMap.get("btype")) && jhkdElement.getContent().toString().equals(bidMap.get("bstorage").toString())){
								flag = true;
							}
								
						}
					}
						
					
					if(flag){
						double zxj = Double.parseDouble(zxjElement.getContent().toString());
						if(zxj==0){
							tempMap.put("bcode", codeElement.getContent().toString());
							tempMap.put("dealnum", numElement.getContent().toString());
							tempMap.put("nowprice", qbjElement.getContent().toString());
							tempMap.put("addLowPrice", bidMap.get("addLowPrice").toString());
							tempMap.put("btype", pzElement.getContent().toString());
							tempMap.put("bstorage", jhkdElement.getContent().toString());
							break;
						}else{
							if(tempMap.get("nowprice")==null){
								tempMap.put("bcode", codeElement.getContent().toString());
								tempMap.put("dealnum", numElement.getContent().toString());
								tempMap.put("nowprice", zxjElement.getContent().toString());
								tempMap.put("addLowPrice", bidMap.get("addLowPrice").toString());
								tempMap.put("btype", pzElement.getContent().toString());
								tempMap.put("bstorage", jhkdElement.getContent().toString());
							}else{
								double zxjTmp = Double.parseDouble(tempMap.get("nowprice").toString());
								//判断：如果是每次减10元，则取最大的数，否则取最小的，不管是加或减，zxj为0.0的话都为最大
								int isAddCut = systemInfo.getIsAddCut();
								if(isAddCut==0){
									zxj = zxj>zxjTmp?zxj:zxjTmp;
								}else {
									zxj = zxj>zxjTmp?zxjTmp:zxj;
								}
								tempMap.put("bcode", codeElement.getContent().toString());
								tempMap.put("dealnum", numElement.getContent().toString());
								tempMap.put("nowprice", zxj);
								tempMap.put("addLowPrice", bidMap.get("addLowPrice").toString());
								tempMap.put("btype", pzElement.getContent().toString());
								tempMap.put("bstorage", jhkdElement.getContent().toString());
							}
						}
						
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
		if(nowBidLastList==null){
			nowBidLastList = new ArrayList<Map>();
		}
		if(!tempMap.isEmpty()){
			nowBidLastList.add(tempMap);
		}
		
		return nowBidLastList;
	}
	
	/**
	 * 根据自选商品源码得到对应的标信息
	 * @param pageSource
	 * @return
	 */
	private List getZXSPBySource(String pageSource){
		List<Map> result = new ArrayList<Map>();
		Document doc = Jsoup.parse(pageSource, "GBK");
		org.jsoup.nodes.Element table = doc.getElementById("tb");
		Elements trs = table.getElementsByTag("tr");
		int index = 0;
		Map objMap = new HashMap();
		for(org.jsoup.nodes.Element tr:trs){
			objMap = new HashMap();
			Elements tds = tr.children();
			index++;
			if(index==1){
				continue;
			}
			
			String bcode = tds.get(2).text().replace(" ", "").replace(" ", "");
			String btype = tds.get(3).text().replace(" ", "").replace(" ", "");
			String bstorage = tds.get(4).text().replace(" ", "").replace(" ", "");
			String dealNum = tds.get(5).text().replace(" ", "").replace(" ", "");
//			dealNum = dealNum.substring(0,dealNum.indexOf("."));
			String startjiage = tds.get(7).text().replace(" ", "").replace(" ", "");
			String newjiage = tds.get(8).text().replace(" ", "").replace(" ", "");
			String isYx = tds.get(10).text().replace(" ", "").replace(" ", "");
			isYx = "非有效报价".equals(isYx)?"0":"1";
			String felleiCode = btype+"_"+bstorage+"_"+dealNum;
			objMap.put("btype", btype);
			objMap.put("bstorage", bstorage);
			objMap.put("dealNum", dealNum);
			objMap.put("startjiage", startjiage);
			objMap.put("newjiage", newjiage);
			objMap.put("bcode", bcode);
			objMap.put("isYx", isYx);
			objMap.put("CC_CODE", felleiCode);
			result.add(objMap);
		}
		return result;
	}
	
	/**
	 * 根据有效竞标列表页面源码，得到所有的有效标Map<标号,有效价格>
	 * @param element
	 * @return
	 */
	private List getBidFinPageSource(String pageSource){
		List bidCodeList = new ArrayList();
		Document doc = Jsoup.parse(pageSource, "GBK");
		org.jsoup.nodes.Element table = doc.getElementById("tb");
		Elements trs = table.getElementsByTag("tr");
		int index = 0;
		Map objMap = new HashMap();
		for(org.jsoup.nodes.Element tr:trs){
			objMap = new HashMap();
			Elements tds = tr.children();
			index++;
			if(index==1){
				continue;
			}
			String bcode = tds.get(2).text().replace(" ", "").replace(" ", "");
			String bprice = tds.get(3).text().replace(" ", "").replace(" ", "");
			objMap.put("bcode", bcode);
			objMap.put("bprice", bcode);
			objMap.put("orderno", index);
			bidCodeList.add(objMap);
		}
		
//		Reader reader = new StringReader(pageSource);
//		Source source = null;
//		try {
//			source = new Source(reader);
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<Element> elementList = source
//				.getAllElements(HTMLElementName.TABLE);
//		Element trElement0 = elementList.get(0);
//		
//		List<Element> trList = trElement0.getAllElements(HTMLElementName.TR);
//		List bidCodeList = new ArrayList();
//		int index = 0;
//		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
//			Element trElement = (Element) iterator.next();
//			if (index > 0) {
//				List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
//				try {
//					// 标号
//					Element codeElement = tdList.get(2);
//					// 价格
//					Element pzElement = tdList.get(3);
//					bidCodeList.add(codeElement.getContent().toString());
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			}
//			index++;
//
//		}
		return bidCodeList;
	}
	
	public void exeThea(String[] arrs,CloseableHttpAsyncClient httpclient,HttpClientContext localContext,long startTime,String[] times){
		//将arrs和times转为list
		List<String> arrsList = new ArrayList<String>(Arrays.asList(arrs));
		List<String> timesList = new ArrayList<String>(Arrays.asList(times));
		//已经完成的记录list
		List<String> finArrsList = new ArrayList<String>();
		List<String> finAtimesList = new ArrayList<String>();
		try {
			//分割数量
			int maxFGNumIndex = 0;
			//白条数量
			int maxBTNum = 0;
			while(1>0){
				String url = "";
				finArrsList = new ArrayList<String>();
				finAtimesList = new ArrayList<String>();
				for (int i = 0; i < arrsList.size(); i++) {
					String params = arrsList.get(i);
					//白条或分割;竞拍价格;竞标code;吨数
					String[] paramsArrs = params.split(";");
					//url地址头[FG]或[BT]
					String hostHard = paramsArrs[0];
					url = PropertiesUtil.getSubmitURL(jbiaoUtil, localContext, httpclient, paramsArrs[1], paramsArrs[2],-1);
					//判断是分割还是白条，在hostUrl中前4个字符为[FG]为分割，[BT]为白条
					if("[FG]".equals(hostHard)){
						//判断分割成功的数量是否小于最多的数量
						if(maxFGNumIndex < ss.getMaxFGRNum()){
							try {
								Thread.sleep(Integer.parseInt(timesList.get(i)));
							} catch (Exception e) {
								// TODO: handle exception
							}
							HttpGet hg = new HttpGet(url);
							Future<HttpResponse> future = httpclient.execute(hg, localContext,null);
							HttpEntity entity = future.get().getEntity();
							boolean flag = PropertiesUtil.isSuccessed(entity);
							//如果flag为true说明已经成功，则maxFGNumIndex+1
							if(flag){
								maxFGNumIndex++;
							}
							//记录执行过的arrsList和timesList
							finArrsList.add(arrsList.get(i));
							finAtimesList.add(timesList.get(i));
							/*-----异步记录日志--------------*/
							Processor processor = new Processor(entity, keyInfo, ocaList.get(i), jbLogDao);
							processor.start();
							/*-----异步记录日志--------------*/
						}
					}else {
						//判断白条成功的数量是否小于最多的数量
						if(maxBTNum < ss.getMaxBTNum()){
							try {
								Thread.sleep(Integer.parseInt(timesList.get(i)));
							} catch (Exception e) {
								// TODO: handle exception
							}
							HttpGet hg = new HttpGet(url);
							Future<HttpResponse> future = httpclient.execute(hg, localContext,null);
							HttpEntity entity = future.get().getEntity();
							boolean flag = PropertiesUtil.isSuccessed(entity);
							//如果flag为true说明已经成功，则maxFGNumIndex+1
							if(flag){
								maxBTNum++;
							}
							//记录执行过的arrsList和timesList
							finArrsList.add(arrsList.get(i));
							finAtimesList.add(timesList.get(i));
							/*-----异步记录日志--------------*/
							Processor processor = new Processor(entity, keyInfo, ocaList.get(i), jbLogDao);
							processor.start();
							/*-----异步记录日志--------------*/
						}
					}
				}
				
				//判断是否白条和分割是否都已经满足个数
				if(maxBTNum >= ss.getMaxBTNum() && maxFGNumIndex >= ss.getMaxFGRNum()){
					break;
				}
				//将finArrsList和finAtimesList包含的数据，分别在arrsList和timesList中删除
				arrsList = this.delFinJB(finArrsList, arrsList);
				timesList = this.delFinJB(finAtimesList, timesList);
			}
		} catch (Exception e) {
			
		}
		
	System.out.println("--------------程序运行时间："+(System.currentTimeMillis()-startTime));
	  
  }
	
	
	/**
	 * 删除arrsList中包含的finList的记录
	 * @param finList
	 * @param arrsList
	 * @return
	 */
	private List<String> delFinJB(List<String> finList,List<String> arrsList){
		for (Iterator iterator = finList.iterator(); iterator.hasNext();) {
			String fin = (String) iterator.next();
			for (Iterator iterator2 = arrsList.iterator(); iterator2.hasNext();) {
				String s = (String) iterator2.next();
				if(fin.equals(s)){
					arrsList.remove(s);
					break;
				}
			}
			
		}
		
		return arrsList;
	}
	
	public void setCookie(DefaultHttpClient httpclient, List<Cookie> cookies) {
	    if (cookies.isEmpty()) {
	      System.out.println("Cookie is empty.");
	      return;
	    } else {
	      for (int i = 0; i < cookies.size(); i++) {
	        System.out.println((i + 1) + " - " + cookies.get(i).toString());
	        httpclient.getCookieStore().addCookie(cookies.get(i));
	      }
	      System.out.println();
	    }
	  }
	
	public void showResponseStatus(HttpResponse response) {
	    // System.out.println(response.getProtocolVersion());
	    // System.out.println(response.getStatusLine().getStatusCode());
	    // System.out.println(response.getStatusLine().getReasonPhrase());
	    System.out.println(response.getStatusLine().toString());
	    System.out.println("-------------------------\r\n");
	  }
	
	private static void createFile(String content,String path){
		  FileOutputStream fos = null;
		  OutputStreamWriter osw = null;
		  BufferedWriter bw = null;
		  try {
			  fos = new FileOutputStream(path);
			  osw = new OutputStreamWriter(fos);
			  bw = new BufferedWriter(osw);
			  bw.write(content);
			  bw.flush();
		  } catch (Exception e) {
			  try {
				if(bw != null){
					bw.close();
				}
			} catch (Exception e1) {}
			try {
				if(osw != null){
					osw.close();
				}
			} catch (Exception e1) {}
			try {
				if(fos != null){
					fos.close();
				}
			} catch (Exception e1) {}
		  }
	  }
	
	public static void main(String[] args) {
//		String xmlStr = "";
//		try {
//			DefaultHttpClient httpclient = new DefaultHttpClient();
//			System.out.println("------------获得自选商品页面源码开始-------------------");
//			Date date = new Date();
//			
//			HttpGet getZXFrame = new HttpGet("http://124.127.44.53:20023/vendue11/servlet/getOrderXML?partitionID=11&lastTime=1999-09-09/09:09:09&h="+System.currentTimeMillis());
//			HttpResponse responseZXFrame = httpclient.execute(getZXFrame);
//			HttpEntity entityZXFrame = responseZXFrame.getEntity();
//			xmlStr = EntityUtils.toString(entityZXFrame, "gbk");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		System.out.println(xmlStr);
//		System.out.println("------------获得自选商品页面源码结束-------------------");
		
		
	}
}
