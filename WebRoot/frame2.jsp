<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!--����js����-->
<SCRIPT LANGUAGE="JavaScript">
<!--

function dateChk1(str){ //hh:mm
	var reg = /^(\d{2})\:(\d{2})$/;
	var r = str.match(reg);
	if(r==null){
		return false; 
	}else{
		if(r[1] < 0 || r[1] >= 24){
			return false;
		}
		if(r[2] < 0 || r[2] >= 60){
			return false;
		}
	}
	return true; 
} 

function dateChk(str){ //yyyy-mm-dd
	var reg = /^(\d{4})\-(\d{2})\-(\d{2})$/;
	var r = str.match(reg);
	if(r==null){
		return false; 
	}else{
		if(r[1] <= 1950 || r[1] > 2050){
			return false;
		}
		if(r[2] <= 0 || r[2] > 12){
			return false;
		}
		if(r[3] <= 0 || r[3] > 31){
			return false;
		}
	}
	return true; 
} 

function FormatNumber(srcStr,nAfterDot){
����var srcStr,nAfterDot;
����var resultStr,nTen;
����srcStr = ""+srcStr+"";
����strLen = srcStr.length;
����dotPos = srcStr.indexOf(".",0);
����if (dotPos == -1){
��������resultStr = srcStr+".";
��������for (i=0;i<nAfterDot;i++){
������������resultStr = resultStr+"0";
��������}
��������return resultStr;
����}
����else{
��������if ((strLen - dotPos - 1) >= nAfterDot){
������������nAfter = dotPos + nAfterDot + 1;
������������nTen =1;
������������for(j=0;j<nAfterDot;j++){
����������������nTen = nTen*10;
������������}
������������resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
������������return resultStr;
��������}
��������else{
������������resultStr = srcStr;
������������for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
����������������resultStr = resultStr+"0";
������������}
������������return resultStr;
��������}
����}
}

function FormatNumber1(srcStr,nAfterDot){
����var srcStr,nAfterDot;
����var resultStr,nTen;
����srcStr = ""+srcStr+"";
����strLen = srcStr.length;
����dotPos = srcStr.indexOf(".",0);
    //alert(strLen);
    //alert(dotPos);
����if (dotPos == -1){
��������resultStr = srcStr+".";
��������for (i=0;i<nAfterDot;i++){
������������resultStr = resultStr+"0";
��������}
��������return resultStr;
       
����}
����else{
��������if ((strLen - dotPos - 1) >= nAfterDot){

������������nAfter = dotPos + nAfterDot + 1;
������������nTen =1;
������������for(j=0;j<nAfterDot;j++){
����������������nTen = nTen*10;
������������}
������������resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
            resultStr=FormatNumber(resultStr,nAfterDot);
������������return resultStr;
��������}
��������else{
	
������������resultStr = srcStr;
������������for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
����������������resultStr = resultStr+"0";
������������}
������������return resultStr;
��������}
����}
}

//-->
</SCRIPT>
<!--�û�����ж�-->


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
</head>

<frameset rows="28,*" frameborder="NO" border="0" framespacing="0">
  <frame src="menu2.jsp" name="top2" frameborder="no" scrolling="no" noresize id="top2" APPLICATION="yes">
  <frame src="${ctx }/hsdata_orderList.action" name="orderList" frameborder="no" scrolling="auto" noresize id="orderList" APPLICATION="yes">
</frameset>
<noframes>
<body>
��ϵͳӦ���˿�ܼ���,�����������֧�ֿ��,��������߰汾���������
</body>
</noframes>
</html>
