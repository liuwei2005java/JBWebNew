package com.jb.action;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.jb.dao.CClassCityDao;
import com.jb.dao.JBLogDao;
import com.jb.dao.KeyInfoDao;
import com.jb.dao.OCommodityDao;
import com.jb.dao.SystemInfoDao;
import com.jb.model.CommodityClassify;
import com.jb.model.JbiaoUtil;
import com.jb.model.KeyInfo;
import com.jb.model.OCommodityAdjust;
import com.jb.model.OCommodityRecord;
import com.jb.model.OCommodityRecordAll;
import com.jb.model.SwitchStatus;
import com.jb.model.SystemInfo;
import com.jb.unit.ProcessorMain;
import com.jb.unit.ProcessorMainInfo;
import com.jb.unit.PropertiesUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OCommodityAction extends ActionSupport implements ApplicationAware,SessionAware{

	private List<KeyInfo> keyInfos = new ArrayList<KeyInfo>();
	private String msg = "";
	private KeyInfoDao keyInfoDao;
	private OCommodityDao ocommodityDao;
	private String ids = "";
	private String keyId = "";
	private OCommodityAdjust oca = new OCommodityAdjust();
	private int isTZOK = 0;
	private JBLogDao jbLogDao;
	private SystemInfoDao systemInfoDao;
	private CClassCityDao cclassCityDao;
	private String isLowFin = "1";
	private Map application;
	private Map session;
	//+10��������״̬
	private String startstatus = "0";

	public String list() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		String username = session.get("username").toString();
		keyInfos = keyInfoDao.getListByUserName(username);
		return SUCCESS;
	}

	// �ж�ʹ��
	public String flag() {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		SwitchStatus switchStatus = ocommodityDao.getSwitchStatus(keyId,
				dateStr);
		KeyInfo keyInfoTmp = keyInfoDao.get(keyId);
		if (switchStatus == null) {
			switchStatus = new SwitchStatus();
			switchStatus.setIsInfoCQ(0);
			switchStatus.setIsTZOK(0);
			switchStatus.setSsId(null);
			switchStatus.setKeyInfo(keyInfoTmp);
			switchStatus.setSetDate(dateStr);
			ocommodityDao.saveOrUpdateSS(switchStatus);
			jsonObject.put("isInfoCQ", "0");
			jsonObject.put("isTZOK", "0");
		} else {
			jsonObject.put("isInfoCQ", switchStatus.getIsInfoCQ());
			jsonObject.put("isTZOK", switchStatus.getIsTZOK());
		}
		try {
			msg = jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "check";
	}

	public String update() {
		JSONObject jsonObject = new JSONObject();

		ocommodityDao.updateAdjust(oca);

		jsonObject.put("success", "true");
		try {
			msg = jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "check";
	}

	public String getListJson() {
		JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("keyInfo") || name.equals("commodityClassify")) {
					return true;
				} else {
					return false;
				}
			}
		});

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("total", 1);

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());

		List<OCommodityAdjust> ocas = ocommodityDao.getAdjustByKeyIdAndDate(
				keyId, dateStr,-1);
		for (Iterator iterator = ocas.iterator(); iterator.hasNext();) {
			OCommodityAdjust obj = (OCommodityAdjust) iterator.next();
			JSONObject pinfo = JSONObject.fromObject(obj, cfg);
			pinfo.put("keyId", obj.getKeyInfo().getKeyId());
			String jsonStr = pinfo.toString();
			jsonArray.add(jsonStr);
		}
		jsonObject.put("rows", jsonArray);
		try {
			msg = jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "check";
	}
	
	
	/**
	 * �����ʼ��
	 * @return
	 */
	public String init(){
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		JbiaoUtil jbiaoUtil = (JbiaoUtil) application.get("JBIAOUTIL");
		if(systemInfo == null){
			systemInfo = systemInfoDao.getAll().get(0);
			application.put("SYSTEMINFO", systemInfo);
			application.put("partitionID", systemInfo.getUrlContext().replace("vendue", ""));

			jbiaoUtil = new JbiaoUtil(systemInfo);
			// �õ����еĲ���
			jbiaoUtil.setYzmUrl(PropertiesUtil.getValue("YZM_URL"));
			jbiaoUtil.setLoginUrl(PropertiesUtil.getValue("LOGIN_URL"));
			jbiaoUtil.setOrderUrl(PropertiesUtil.getValue("ORDER_URL"));
			jbiaoUtil.setOrderUrlNoId(PropertiesUtil.getValue("ORDER_URL"));
			jbiaoUtil.setSubmitUrl(PropertiesUtil.getValue("SUBMIT_URL"));
			jbiaoUtil.setLoginUrlIp(PropertiesUtil.getValue("LOGIN_URL_IP"));
			jbiaoUtil.setZxFrame(PropertiesUtil.getValue("ZX_FRAME"));
			jbiaoUtil.setZxUrl(PropertiesUtil.getValue("ZX_URL"));
			jbiaoUtil.setJbFinUrl(PropertiesUtil.getValue("ZJ_FIN_URL"));
			jbiaoUtil.setIsTest();
			jbiaoUtil.setLocalCatalog();
			
			application.put("JBIAOUTIL",jbiaoUtil);
			
		}
		
		
		// �õ�����key
		List<KeyInfo> keyInfoList = keyInfoDao.getListAllLogin(dateStr,keyId);
		ExecutorService pool = Executors.newFixedThreadPool(keyInfoList.size());
		// ����CompletionServiceʵ��
		CompletionService completionService = new ExecutorCompletionService(
				pool);
		Future future = null;
		String returnVal = "";
		// �ж��Ƿ�����key����¼�ɹ�
		boolean isAllOK = true;
		for (Iterator iterator = keyInfoList.iterator(); iterator.hasNext();) {
			KeyInfo keyObj = (KeyInfo) iterator.next();
			// �õ�key��״̬
			SwitchStatus ss = ocommodityDao.getSwitchStatus(keyObj.getKeyId(),
					dateStr);
			Callable r = new ProcessorMainInfo(jbiaoUtil, keyObj, jbLogDao,keyInfoDao, ss,systemInfo,application,session,ocommodityDao);
			completionService.submit(r);
		}
		pool.shutdown();
		
		jsonObject.put("success", "true");

		msg = jsonObject.toString();
		return "check";
	}
	

	// ��������
	public String jbStart() {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		String failKey = "";
		SystemInfo systemInfo = (SystemInfo) application.get("SYSTEMINFO");
		JbiaoUtil jbiaoUtil = (JbiaoUtil) application.get("JBIAOUTIL");
		if(systemInfo == null){
			systemInfo = systemInfoDao.getAll().get(0);
			application.put("SYSTEMINFO", systemInfo);
			application.put("partitionID", systemInfo.getUrlContext().replace("vendue", ""));

			jbiaoUtil = new JbiaoUtil(systemInfo);
			// �õ����еĲ���
			jbiaoUtil.setYzmUrl(PropertiesUtil.getValue("YZM_URL"));
			jbiaoUtil.setLoginUrl(PropertiesUtil.getValue("LOGIN_URL"));
			jbiaoUtil.setOrderUrl(PropertiesUtil.getValue("ORDER_URL"));
			jbiaoUtil.setOrderUrlNoId(PropertiesUtil.getValue("ORDER_URL"));
			jbiaoUtil.setSubmitUrl(PropertiesUtil.getValue("SUBMIT_URL"));
			jbiaoUtil.setLoginUrlIp(PropertiesUtil.getValue("LOGIN_URL_IP"));
			jbiaoUtil.setZxFrame(PropertiesUtil.getValue("ZX_FRAME"));
			jbiaoUtil.setZxUrl(PropertiesUtil.getValue("ZX_URL"));
			jbiaoUtil.setJbFinUrl(PropertiesUtil.getValue("ZJ_FIN_URL"));
			jbiaoUtil.setIsTest();
			jbiaoUtil.setLocalCatalog();
			
			application.put("JBIAOUTIL",jbiaoUtil);
			
		}
		// û��ȷ�ϵ�key����
		String failStr = "����Keyû�б�ȷ��";
		/*----------�ж��Ƿ����е�key���Ѿ�ȷ�Ϲ���-------------------*/
		List<KeyInfo> keyInfoFlag = keyInfoDao.isAllOK(dateStr,keyId);
		if (keyInfoFlag.size() > 0) {
			for (Iterator iterator = keyInfoFlag.iterator(); iterator.hasNext();) {
				KeyInfo key = (KeyInfo) iterator.next();
				failStr += key.getUserName() + "  ";
			}
			jsonObject.put("success", "false");
			jsonObject.put("fail", failStr);
			msg = jsonObject.toString();
			return "check";
		}

		/*----------�ж��Ƿ����е�key���Ѿ�ȷ�Ϲ���-------------------*/

		/*-----------��ȡ��ҳ��Ϣ��ʼ-----------------*/
		
		// �õ�����key
		List<KeyInfo> keyInfoList = keyInfoDao.getListAllLogin(dateStr,keyId);
//		if (keyInfoList.size() == 0) {
//			jsonObject.put("success", "false");
//			jsonObject.put("fail", "����key���ѵ�¼�ɹ�");
//			msg = jsonObject.toString();
//			return "check";
//		}
		ExecutorService pool = Executors.newFixedThreadPool(keyInfoList.size());
		// ����CompletionServiceʵ��
		CompletionService completionService = new ExecutorCompletionService(
				pool);
		Future future = null;
		String returnVal = "";
		// �ж��Ƿ�����key����¼�ɹ�
		boolean isAllOK = true;
		// ���������key���û�
		// String failKey = "";
		for (Iterator iterator = keyInfoList.iterator(); iterator.hasNext();) {
			KeyInfo keyObj = (KeyInfo) iterator.next();
			// �õ�key��״̬
			SwitchStatus ss = ocommodityDao.getSwitchStatus(keyObj.getKeyId(),
					dateStr);
			// ����key��id�͵�ǰ���ڣ��������е���ѡ��Ʒ,�˴����ҵ���һ�¾��굽�׵ı�
			List<OCommodityAdjust> ocaList = new ArrayList<OCommodityAdjust>();
			//���ҷ�һ�¾��굽�׵ı�
			List<CommodityClassify> ocaNoLowList = new ArrayList<CommodityClassify>();
			/*------------����Ϊ��10Ԫ����--------------------*/
			if("0".equals(isLowFin)){
//				ocaNoLowList = cclassCityDao.getALLByCurrentDateKeyId(keyObj.getKeyId(), dateStr);
				
			}else{
				ocaList = ocommodityDao.getAdjustByKeyIdAndDate(keyObj.getKeyId(), dateStr,1);
			}
			/*------------����Ϊ��10Ԫ����--------------------*/
			Callable r = new ProcessorMain(jbiaoUtil, keyObj, false, ocaList,
					jbLogDao, keyInfoDao, ocommodityDao, ss,ocaNoLowList,systemInfo,0,isLowFin,application,session);
			completionService.submit(r);
			// try {
			// future = completionService.submit(r);
			// returnVal = (String) future.get(10l,TimeUnit.SECONDS);
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// returnVal = "errorException";
			// }
			// if(returnVal.indexOf("error")!=-1){
			// keyObj.setIsLoginOK(0);
			// isAllOK = false;
			// failKey += keyObj.getUserName() + " ";
			// }else{
			// keyObj.setIsLoginOK(1);
			// }
			// keyInfoDao.saveOrupdate(keyObj);
		}
		pool.shutdown();
		if (isAllOK) {
			jsonObject.put("success", "true");
		} else {
			jsonObject.put("success", "loginfalse");
			jsonObject.put("loginfail", failKey);
		}

		msg = jsonObject.toString();
		return "check";
	}
	
	//������Ʒ���༶��
	private List<Map> getClassLevelList(String keyId,String dateStr){
		
		
		return null;
	}

	public String infoZX() {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		/*-----------��ȡ��ҳ��Ϣ��ʼ-----------------*/
		SystemInfo systemInfo = systemInfoDao.getAll().get(0);
		JbiaoUtil jbiaoUtil = new JbiaoUtil(systemInfo);
		// �õ����еĲ���
		jbiaoUtil.setYzmUrl(PropertiesUtil.getValue("YZM_URL"));
		jbiaoUtil.setLoginUrl(PropertiesUtil.getValue("LOGIN_URL"));
		jbiaoUtil.setOrderUrl(PropertiesUtil.getValue("ORDER_URL"));
		jbiaoUtil.setSubmitUrl(PropertiesUtil.getValue("SUBMIT_URL"));
		jbiaoUtil.setLoginUrlIp(PropertiesUtil.getValue("LOGIN_URL_IP"));
		jbiaoUtil.setZxFrame(PropertiesUtil.getValue("ZX_FRAME"));
		jbiaoUtil.setZxUrl(PropertiesUtil.getValue("ZX_URL"));
		jbiaoUtil.setIsTest();
		jbiaoUtil.setLocalCatalog();

		// ��ʼ��������
		KeyInfo keyInfoThread = keyInfoDao.get(keyId);
		// �õ�key��״̬
		SwitchStatus ss = ocommodityDao.getSwitchStatus(keyId, dateStr);
		ExecutorService pool = Executors.newFixedThreadPool(1);
		// ����CompletionServiceʵ��
		CompletionService completionService = new ExecutorCompletionService(
				pool);
		Callable r = new ProcessorMain(jbiaoUtil, keyInfoThread, true, null,
				jbLogDao, keyInfoDao, ocommodityDao, ss,null,systemInfo,0,isLowFin,application,session);
		completionService.submit(r);

		Future future = null;
		String zxJspStr = "";
		try {
			future = completionService.take();
			zxJspStr = (String) future.get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �ж��Ƿ��д���
		if (zxJspStr.indexOf("error") == -1) {
			Reader reader = new StringReader(zxJspStr);
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

			getTableContentAndSave(trElement, keyInfoThread, dateStr);

			// �õ���Ʒ����
			boolean isOK = cclassCityDao.setCClassifyByAdjust(dateStr, keyId);
			if (isOK) {
				// ��ԭʼ���е����ݣ����Ƶ���������
				isOK = ocommodityDao.recordAll2Adjust(keyId, dateStr);
				if (isOK) {
					msg = "true";
					SwitchStatus ssTmp = ocommodityDao.getSwitchStatus(keyId,
							dateStr);
					if (ssTmp == null) {
						ssTmp = new SwitchStatus();
						ssTmp.setIsInfoCQ(1);
						ssTmp.setIsTZOK(0);
						ssTmp.setSsId(null);
						ssTmp.setKeyInfo(keyInfoThread);
						ssTmp.setSetDate(dateStr);
					} else {
						ssTmp.setIsInfoCQ(1);
						ssTmp.setIsTZOK(0);
					}
					ocommodityDao.saveOrUpdateSS(ssTmp);
				} else {
					msg = "false";
				}
			} else {
				msg = "false";
			}
		} else {
			msg = "false";
		}

		pool.shutdown();

		return "check";
	}
	
	public String infoZXTmp() {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		/*-----------��ȡ��ҳ��Ϣ��ʼ-----------------*/
		SystemInfo systemInfo = systemInfoDao.getAll().get(0);
		JbiaoUtil jbiaoUtil = new JbiaoUtil(systemInfo);
		// �õ����еĲ���
		jbiaoUtil.setYzmUrl(PropertiesUtil.getValue("YZM_URL"));
		jbiaoUtil.setLoginUrl(PropertiesUtil.getValue("LOGIN_URL"));
		jbiaoUtil.setOrderUrl(PropertiesUtil.getValue("ORDER_URL"));
		jbiaoUtil.setSubmitUrl(PropertiesUtil.getValue("SUBMIT_URL"));
		jbiaoUtil.setLoginUrlIp(PropertiesUtil.getValue("LOGIN_URL_IP"));
		jbiaoUtil.setZxFrame(PropertiesUtil.getValue("ZX_FRAME"));
		jbiaoUtil.setZxUrl(PropertiesUtil.getValue("ZX_URL"));
		jbiaoUtil.setIsTest();
		jbiaoUtil.setLocalCatalog();

		// ��ʼ��������
		KeyInfo keyInfoThread = keyInfoDao.getListUseredAll().get(0);
		// �õ�key��״̬
		SwitchStatus ss = ocommodityDao.getSwitchStatus(keyInfoThread.getKeyId(), dateStr);
		ExecutorService pool = Executors.newFixedThreadPool(10);
		// ����CompletionServiceʵ��
		CompletionService completionService = new ExecutorCompletionService(
				pool);
		for (int i = 1; i <=6; i++) {
			Callable r = new ProcessorMain(jbiaoUtil, keyInfoThread, true, null,
					jbLogDao, keyInfoDao, ocommodityDao, ss,null,systemInfo,i,isLowFin,application,session);
			completionService.submit(r);

			Future future = null;
			String zxJspStr = "";
			try {
				future = completionService.take();
				zxJspStr = (String) future.get();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// �ж��Ƿ��д���
			if (zxJspStr.indexOf("error") == -1) {
				Reader reader = new StringReader(zxJspStr);
				Source source = null;
				try {
					source = new Source(reader);

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//ʱ��
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				//���浽�ļ�   
				String dateStrTT = sdf.format(new Date());
				String dateStrTT1 = sdf1.format(new Date());
				PropertiesUtil.string2File(zxJspStr, "c:\\jbsp\\"+dateStrTT1 + "\\" + dateStrTT + "+" + i +".html");
//				List<Element> elementList = source
//						.getAllElements(HTMLElementName.TABLE);
//				Element trElement = elementList.get(0);
				
//				getTableContentAndSaveTmp(trElement, keyInfoThread, dateStr);

			} else {
				msg = "false";
			}
		}
		

		pool.shutdown();

		return "check";
	}

	public String delZXSP() {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		ocommodityDao.delRecord(keyId, dateStr);
		ocommodityDao.delAdjust(keyId, dateStr);

		SwitchStatus switchStatus = ocommodityDao.getSwitchStatus(keyId,
				dateStr);
		switchStatus.setIsInfoCQ(0);
		switchStatus.setIsTZOK(0);
		ocommodityDao.saveOrUpdateSS(switchStatus);

		jsonObject.put("success", "true");
		msg = jsonObject.toString();
		return "check";
	}

	public String saveOK() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		SwitchStatus switchStatus = ocommodityDao.getSwitchStatus(keyId,
				dateStr);
		if (switchStatus == null || switchStatus.getIsInfoCQ() == 0) {
			msg = "false";
		} else {
			switchStatus.setIsTZOK(isTZOK);
			ocommodityDao.saveOrUpdateSS(switchStatus);
			msg = "true";
		}

		return "check";
	}

	public String delSCSP() {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());

		ocommodityDao.delAdjust(keyId, dateStr);
		// �õ���Ʒ����
		boolean isOK = cclassCityDao.setCClassifyByAdjust(dateStr, keyId);
		// ��ԭʼ���е����ݣ����Ƶ���������
		isOK = ocommodityDao.recordAll2Adjust(keyId, dateStr);
		
		
		jsonObject.put("success", "true");
		msg = jsonObject.toString();
		return "check";
	}

	public String delAll() {
		JSONObject jsonObject = new JSONObject();
		String[] idArrs = ids.split(";");
		ocommodityDao.delAll(idArrs);
		jsonObject.put("success", "true");
		msg = jsonObject.toString();
		return "check";
	}
	
	private void getTableContentAndSave(Element element, KeyInfo ki,
			String dateStr) {
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		int index = 0;
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement
						.getAllElements(HTMLElementName.TD);
				try {
					OCommodityRecord ocr = new OCommodityRecord();
					// ���
					Element codeElement = tdList.get(2);
					ocr.setBcode(codeElement.getContent().toString());
					// Ʒ��
					Element pzElement = tdList.get(3);
					ocr.setBtype(pzElement.getContent().toString());
					// �������
					Element jhkdElement = tdList.get(4);
					ocr.setBstorage(jhkdElement.getContent().toString());
					// ��������
					Element numElement = tdList.get(5);
					String num = numElement.getContent().toString();
					ocr.setDealNum(num);
					try {
//						ocr.setDealNum(num.substring(0, num.lastIndexOf(".")));
						ocr.setDealNum(num);
					} catch (Exception e) {
						// TODO: handle exception
					}

					// �𱨼�
					Element qbjElement = tdList.get(7);
					String qbj = qbjElement.getContent().toString();
					ocr.setStartPrice(qbj);
					try {
						ocr.setStartPrice(qbj
								.substring(0, qbj.lastIndexOf(".")));
					} catch (Exception e) {
						// TODO: handle exception
					}
					// ���¼۸�
					Element zxElement = tdList.get(8);
					String zx = zxElement.getContent().toString();
					ocr.setZxjg(zx);
					// �����  d 
					ocr.setOrderNo(index);
					ocr.setKeyInfo(ki);
					ocr.setOcrDate(dateStr);
					ocr.setOcrId(null);
					ocommodityDao.saveRecord(ocr);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
	}

	/**
	 * ����ҳ�����б�
	 * @param element
	 * @param ki
	 * @param dateStr
	 */
	private void getTableContentAndSaveTmp(Element element, KeyInfo ki,
			String dateStr) {
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		//ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String dateStrHM = sdf.format(new Date());
		int index = 0;
		for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
			Element trElement = (Element) iterator.next();
			if (index > 0) {
				List<Element> tdList = trElement
						.getAllElements(HTMLElementName.TD);
				try {
					OCommodityRecordAll ocr = new OCommodityRecordAll();
					// ���
					Element codeElement = tdList.get(1);
					ocr.setBcode(codeElement.getContent().toString());
					// Ʒ��
					Element pzElement = tdList.get(2);
					ocr.setBtype(pzElement.getContent().toString());
					// �������
					Element jhkdElement = tdList.get(3);
					ocr.setBstorage(jhkdElement.getContent().toString());
					// ��������
					Element numElement = tdList.get(4);
					String num = numElement.getContent().toString();
					ocr.setDealNum(num);
					try {
						ocr.setDealNum(num.substring(0, num.lastIndexOf(".")));
					} catch (Exception e) {
						// TODO: handle exception
					}

					// �𱨼�
					Element qbjElement = tdList.get(6);
					String qbj = qbjElement.getContent().toString();
					ocr.setStartPrice(qbj);
					try {
						ocr.setStartPrice(qbj
								.substring(0, qbj.lastIndexOf(".")));
					} catch (Exception e) {
						// TODO: handle exception
					}
					// ���¼۸�
					Element zxElement = tdList.get(7);
					String zx = zxElement.getContent().toString();
					ocr.setZxjg(zx);
					// �����  d 
					ocr.setOrderNo(index);
					ocr.setOcrDate(dateStr);
					ocr.setOcrId(null);
					ocr.setInsertDate(dateStrHM);
					ocommodityDao.saveRecordAll(ocr);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			index++;

		}
	}
	
	public void setStartstatus(){
		application.put("START_STATUS", startstatus);
	}
	
	public void setALLJBStatus(){
		application.put("ALL_JB_STATUS", 1);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public KeyInfoDao getKeyInfoDao() {
		return keyInfoDao;
	}

	public void setKeyInfoDao(KeyInfoDao keyInfoDao) {
		this.keyInfoDao = keyInfoDao;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<KeyInfo> getKeyInfos() {
		return keyInfos;
	}

	public void setKeyInfos(List<KeyInfo> keyInfos) {
		this.keyInfos = keyInfos;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public OCommodityDao getOcommodityDao() {
		return ocommodityDao;
	}

	public void setOcommodityDao(OCommodityDao ocommodityDao) {
		this.ocommodityDao = ocommodityDao;
	}

	public OCommodityAdjust getOca() {
		return oca;
	}

	public void setOca(OCommodityAdjust oca) {
		this.oca = oca;
	}

	public int getIsTZOK() {
		return isTZOK;
	}

	public void setIsTZOK(int isTZOK) {
		this.isTZOK = isTZOK;
	}

	public JBLogDao getJbLogDao() {
		return jbLogDao;
	}

	public void setJbLogDao(JBLogDao jbLogDao) {
		this.jbLogDao = jbLogDao;
	}

	public SystemInfoDao getSystemInfoDao() {
		return systemInfoDao;
	}

	public void setSystemInfoDao(SystemInfoDao systemInfoDao) {
		this.systemInfoDao = systemInfoDao;
	}

	public CClassCityDao getCclassCityDao() {
		return cclassCityDao;
	}

	public void setCclassCityDao(CClassCityDao cclassCityDao) {
		this.cclassCityDao = cclassCityDao;
	}

	public String getIsLowFin() {
		return isLowFin;
	}

	public void setIsLowFin(String isLowFin) {
		this.isLowFin = isLowFin;
	}

	public void setApplication(Map application) {
		// TODO Auto-generated method stub
		this.application = application;
	}

	public String getStartstatus() {
		return startstatus;
	}

	public void setStartstatus(String startstatus) {
		this.startstatus = startstatus;
	}

	public void setSession(Map session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

}
