package com.jb.unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jb.model.KeyInfo;

public class TTDbUnit {

	/**------------------内存数据库使用-----------------------*/
	public static void delTTForSql(String sql){
		Connection conn = TTConnectionUnit.getTTConnectionInstance();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 自选商品添加
	 */
	public static void insertZX(List<Map> zxList,String keyId){
		Connection conn = TTConnectionUnit.getTTConnectionInstance();
		PreparedStatement pstmt = null;
		String sql = "insert into ZX_COMMODITY(B_CODE,B_TYPE,B_STORAGE,DEAL_NUM,START_PRICE,DQ_PRICE,ORDERNO,KEY_ID,IS_YX,VIP_CODE,FENLEI_CODE) values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			int index = 0;
			for (Iterator iterator = zxList.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				index++;
				String bcode = map.get("bcode").toString();
				String btype = map.get("btype").toString();
				String bstorage = map.get("bstorage").toString();
				String dealNum = map.get("dealNum").toString();
				String startjiage = map.get("startjiage").toString();
				String newjiage = map.get("newjiage").toString();
				String isYx = map.get("isYx").toString();
				String felleiCode = map.get("felleiCode").toString();
				pstmt.setString(1, bcode);
				pstmt.setString(2, btype);
				pstmt.setString(3, bstorage);
				pstmt.setString(4, dealNum);
				pstmt.setString(5, startjiage);
				pstmt.setString(6, newjiage);
				pstmt.setInt(7, index);
				pstmt.setString(8, keyId);
				pstmt.setString(9, isYx);
				pstmt.setString(10, "");
				pstmt.setString(11, felleiCode);
				pstmt.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 添加已竞标完成
	 * @param finList
	 * @param keyId
	 */
	public static void insertFin(List<Map> finList,String keyId){
		Connection conn = TTConnectionUnit.getTTConnectionInstance();
		PreparedStatement pstmt = null;
		String sql = "insert into FIN_COMMODITY(B_CODE,VALID_PRICE,KEY_ID) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			int index = 0;
			for (Iterator iterator = finList.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				index++;
				String bcode = map.get("bcode").toString();
				String bprice = map.get("bprice").toString();
				pstmt.setString(1, bcode);
				pstmt.setString(2, bprice);
				pstmt.setString(3, keyId);
				pstmt.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据标号更新最新价格和当前所属会员编码
	 * @param bcode
	 * @param price
	 * @param vipCode
	 */
	public static void updateZXPriceByBCode(String bcode,String price,String vipCode,String isYX){
		String sql = "update ZX_COMMODITY set DQ_PRICE=?,IS_YX=? where B_CODE=?";
//		String insertVIPCodeSql = "insert into VIPCODE_TAB(B_CODE,DQ_PRICE,VIP_CODE,CREATEDATE) values(?,?,?,sysdate)";
		Connection conn = TTConnectionUnit.getTTConnectionInstance();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, price);
			pstmt.setString(2, isYX);
			pstmt.setString(3, bcode);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO: handle exception
		} finally{
			try {
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		//插入到VIPCODE_TAB表，记录这些标都是哪些人在抢
//		try {
//			pstmt = conn.prepareStatement(insertVIPCodeSql);
//			pstmt.setString(1, bcode);
//			pstmt.setString(2, price);
//			pstmt.setString(3, vipCode);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//		} finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	public static List<Map> getCommodityClassifyByKeyId(String keyId){
		List<Map> result = new ArrayList<Map>();
		Map objMap = new HashMap();
		String sql = "select LOW_PRICE,JX_NUM,CC_CODE from COMMODITY_CLASSIFY where KEY_ID=? and JX_NUM>0";
		Connection conn = TTConnectionUnit.getTTConnectionInstance();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				objMap = new HashMap();
				String lowPrice = rs.getString(1);
				int jxNum = rs.getInt(2);
				String felleiCode = rs.getString(3);
				objMap.put("LOW_PRICE", lowPrice);
				objMap.put("JX_NUM", jxNum);
				objMap.put("felleiCode", felleiCode);
				result.add(objMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static Map getZXListByKeyIdFenlei(String keyId,String fenleiCode){
		Map result = new HashMap();
		//有效个数
		int yxNum = 0;
		List<Map> zxList = new ArrayList<Map>();
		Map objMap = new HashMap();
		String sql = "select B_CODE,DQ_PRICE,IS_YX,DEAL_NUM from ZX_COMMODITY where KEY_ID=? AND FENLEI_CODE=? ORDER BY TO_NUMBER(DQ_PRICE)";
		Connection conn = TTConnectionUnit.getTTConnectionInstance();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyId);
			pstmt.setString(2, fenleiCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String isYX = rs.getString(3);
				objMap = new HashMap();
				if("0".equals(isYX)){
					objMap.put("B_CODE", rs.getString(1));
					objMap.put("DQ_PRICE", rs.getString(2));
					objMap.put("DEAL_NUM", rs.getString(4));
					zxList.add(objMap);
				}else{
					yxNum++;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		result.put("yxNum", yxNum);
		result.put("zxList", zxList);
		return result;
	}
	
//	public List<Map> 
	
}
