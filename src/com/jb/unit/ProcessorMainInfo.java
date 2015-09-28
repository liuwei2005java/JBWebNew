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

public class ProcessorMainInfo implements Callable {

	/**
	 * error001:代表登录失败，验证码错误
	 */
	
	private JbiaoUtil jbiaoUtil = new JbiaoUtil();
	private KeyInfo keyInfo = new KeyInfo();
	private JBLogDao jbLogDao;
	private KeyInfoDao keyInfoDao;
	private SwitchStatus ss = new SwitchStatus();
	private SystemInfo systemInfo = new SystemInfo();
	private OCommodityDao ocommodityDao;
	private Map application;
	private Map session;

	public ProcessorMainInfo(JbiaoUtil jbiaoUtil, KeyInfo keyInfo,JBLogDao jbLogDao,KeyInfoDao keyInfoDao,SwitchStatus ss,SystemInfo systemInfo,Map application,Map session,OCommodityDao ocommodityDao) {
		this.jbiaoUtil = jbiaoUtil;
		this.keyInfo = keyInfo;
		this.jbLogDao = jbLogDao;
		this.keyInfoDao = keyInfoDao;
		this.ss = ss;
		this.systemInfo = systemInfo;
		this.application = application;
		this.session = session;
		this.ocommodityDao = ocommodityDao;
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
		
		return "";
	}
	
	
}
