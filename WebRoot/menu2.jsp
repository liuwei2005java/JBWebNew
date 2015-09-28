<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!--巷喘js痕方-->
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
　　var srcStr,nAfterDot;
　　var resultStr,nTen;
　　srcStr = ""+srcStr+"";
　　strLen = srcStr.length;
　　dotPos = srcStr.indexOf(".",0);
　　if (dotPos == -1){
　　　　resultStr = srcStr+".";
　　　　for (i=0;i<nAfterDot;i++){
　　　　　　resultStr = resultStr+"0";
　　　　}
　　　　return resultStr;
　　}
　　else{
　　　　if ((strLen - dotPos - 1) >= nAfterDot){
　　　　　　nAfter = dotPos + nAfterDot + 1;
　　　　　　nTen =1;
　　　　　　for(j=0;j<nAfterDot;j++){
　　　　　　　　nTen = nTen*10;
　　　　　　}
　　　　　　resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
　　　　　　return resultStr;
　　　　}
　　　　else{
　　　　　　resultStr = srcStr;
　　　　　　for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
　　　　　　　　resultStr = resultStr+"0";
　　　　　　}
　　　　　　return resultStr;
　　　　}
　　}
}

function FormatNumber1(srcStr,nAfterDot){
　　var srcStr,nAfterDot;
　　var resultStr,nTen;
　　srcStr = ""+srcStr+"";
　　strLen = srcStr.length;
　　dotPos = srcStr.indexOf(".",0);
    //alert(strLen);
    //alert(dotPos);
　　if (dotPos == -1){
　　　　resultStr = srcStr+".";
　　　　for (i=0;i<nAfterDot;i++){
　　　　　　resultStr = resultStr+"0";
　　　　}
　　　　return resultStr;
       
　　}
　　else{
　　　　if ((strLen - dotPos - 1) >= nAfterDot){

　　　　　　nAfter = dotPos + nAfterDot + 1;
　　　　　　nTen =1;
　　　　　　for(j=0;j<nAfterDot;j++){
　　　　　　　　nTen = nTen*10;
　　　　　　}
　　　　　　resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
            resultStr=FormatNumber(resultStr,nAfterDot);
　　　　　　return resultStr;
　　　　}
　　　　else{
	
　　　　　　resultStr = srcStr;
　　　　　　for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
　　　　　　　　resultStr = resultStr+"0";
　　　　　　}
　　　　　　return resultStr;
　　　　}
　　}
}

//-->
</SCRIPT>
<!--喘薩附芸登僅-->


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="main.css" rel="stylesheet" type="text/css">
<title>涙炎籾猟亀</title>
</head>



<body bgcolor="#CEDEDE">
<table width="94%" height="28"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="bottom"><img id=imgID src="images/03.jpg" width="593" height="25" border="0" usemap="#Map"></td>
	<td align="right">&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
<map name="Map">
    <area shape="rect" coords="9,1,128,24" href="#" onclick="link(1)" alt="嗤丼烏勺斌瞳">
</map>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function link(v)
{
	if(v == 1)
	{
		imgID.src = "images/03.jpg";
		window.parent.frames('orderList').location='${ctx}/hsdata_orderList.action';
	}
	else if(v == 2)
	{
		imgID.src = "images/04.jpg";
		window.parent.frames('orderList').location='firmCapital.jsp';
	}
	else if(v == 3)
	{
		imgID.src = "images/05.jpg";
		window.parent.frames('orderList').location='tradeList.jsp';
	}	
	else if(v == 4)
	{
		imgID.src = "images/06.jpg";
		window.parent.frames('orderList').location='orderListAll.jsp';
	}
}


//-->
</SCRIPT>
