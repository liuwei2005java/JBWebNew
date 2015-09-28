package com.jb.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TTConnectionUnit {

	private static Connection conn = null;
	
	public static Connection getTTConnectionInstance(){
		
//		System.setProperty( "java.library.path", "D:\\Program Files\\Java\\jdk1.6.0_43\\bin;C:\\Windows\\Sun\\Java\\bin;C:\\Windows\\system32;C:\\Windows;D:\\Program Files\\Java\\jdk1.6.0_43\\jre\\bin;D:\\TimesTen\\TT1122~1\\bin;D:\\TimesTen\\TT1122~1\\ttoracle_home\\instantclient_11_2;D:\\Program Files\\Java\\jdk1.6.0_43;d:\\u01\\app\\product\\11.2.0\\dbhome_1\\bin;C:\\Program Files (x86)\\Common Files\\NetSarang;C:\\Program Files (x86)\\Intel\\iCLS Client\\;C:\\Program Files\\Intel\\iCLS Client\\;C:\\Windows\\system32;C:\\Windows;C:\\Windows\\System32\\Wbem;C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\;C:\\Program Files\\Intel\\WiFi\\bin\\;C:\\Program Files\\Common Files\\Intel\\WirelessCommon\\;C:\\Program Files\\Intel\\Intel(R) Management Engine Components\\DAL;C:\\Program Files\\Intel\\Intel(R) Management Engine Components\\IPT;C:\\Program Files (x86)\\Intel\\Intel(R) Management Engine Components\\DAL;C:\\Program Files (x86)\\Intel\\Intel(R) Management Engine Components\\IPT;C:\\Program Files\\ThinkPad\\Bluetooth Software\\;C:\\Program Files\\ThinkPad\\Bluetooth Software\\syswow64;C:\\Program Files (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x86;C:\\Program Files (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x64;C:\\Program Files (x86)\\Common Files\\Adobe\\AGL;C:\\Program Files\\TortoiseSVN\\bin;C:\\wkhtmltopdf;D:\\Program Files (x86)\\VisualSVN Server\\bin;C:\\Program Files\\Intel\\WiFi\\bin\\;C:\\Program Files\\Common Files\\Intel\\WirelessCommon\\;C:\\Program Files (x86)\\IDM Computer Solutions\\UltraEdit\\;." );
//		System.out.println(System.getProperty("java.library.path"));
		if(conn == null){
			String URL = "jdbc:timesten:client:DSN=jbweb";
			try {
				Class.forName("com.timesten.jdbc.TimesTenDriver"); 
				conn = DriverManager.getConnection(URL);
				conn.setAutoCommit(false);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO Auto-generated catch   block
			}
		}
		
		return conn;
	}
	
	
	public static void closeTTConnection(){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}
		
	}
	
	public static void main(String[] args) {
		TTConnectionUnit.getTTConnectionInstance();
	}
}
