/**
  * 
  */
 package com.li72.test;

import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import java.util.ArrayList;
 import java.util.List;

import org.apache.http.HttpEntity;
 import org.apache.http.HttpResponse;
 import org.apache.http.NameValuePair;
 import org.apache.http.client.ClientProtocolException;
 import org.apache.http.client.HttpClient;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.impl.client.DefaultHttpClient;
 import org.apache.http.message.BasicNameValuePair;
 import org.apache.http.util.EntityUtils;

/**
  * �����ƣ�BengTest �������� �����ˣ�li72 ����ʱ�䣺2012-3-30 ����06:07:00 �޸��ˣ�li72 �޸�ʱ�䣺2012-3-30
  * ����06:07:00 �޸ı�ע��
 */
public class BengMy {

 public static void tryLogin() {

  // ����Ĭ�ϵ�httpClientʵ��.
   HttpClient httpclient = new DefaultHttpClient();
   // ����httppost
   HttpPost httppost = new HttpPost(
     "http://wenming.bjyouth.net/app/user/register.php?m=register");
   // ������������
  List<NameValuePair> formparams = new ArrayList<NameValuePair>();
   formparams.add(new BasicNameValuePair("code_salt","%E9%AA%8C%E8%AF%81%E7%A0%81"));
   formparams.add(new BasicNameValuePair("login_email", "weiwei33111%40126.com"));
   formparams.add(new BasicNameValuePair("login_name", "weiwei38493"));
   formparams.add(new BasicNameValuePair("login_pass", "weiwei"));
   formparams.add(new BasicNameValuePair("login_pass_repeat", "weiwei"));
   formparams.add(new BasicNameValuePair("receive", "1"));
   UrlEncodedFormEntity uefEntity;
   try {
    uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
    httppost.setEntity(uefEntity);
    System.out.println("executing request " + httppost.getURI());
    HttpResponse response;
    response = httpclient.execute(httppost);
    HttpEntity entity = response.getEntity();
    if (entity != null) {
     System.out.println("--------------------------------------");
     System.out.println("Response content: "
       + EntityUtils.toString(entity, "UTF-8"));
     System.out.println("--------------------------------------");
     
    }
   } catch (ClientProtocolException e) {
    e.printStackTrace();
   } catch (UnsupportedEncodingException e1) {
    e1.printStackTrace();
   } catch (IOException e) {
    e.printStackTrace();
   } finally {
    // �ر�����,�ͷ���Դ
   httpclient.getConnectionManager().shutdown();
   }
  }

 public static void main(String[] args) {
   tryLogin();
  }

}
