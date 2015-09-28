<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/resources/JEasyUI/jquery-1.7.2.js"></script>
<!--¹«ÓÃjsº¯Êý-->
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
¡¡¡¡var srcStr,nAfterDot;
¡¡¡¡var resultStr,nTen;
¡¡¡¡srcStr = ""+srcStr+"";
¡¡¡¡strLen = srcStr.length;
¡¡¡¡dotPos = srcStr.indexOf(".",0);
¡¡¡¡if (dotPos == -1){
¡¡¡¡¡¡¡¡resultStr = srcStr+".";
¡¡¡¡¡¡¡¡for (i=0;i<nAfterDot;i++){
¡¡¡¡¡¡¡¡¡¡¡¡resultStr = resultStr+"0";
¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡return resultStr;
¡¡¡¡}
¡¡¡¡else{
¡¡¡¡¡¡¡¡if ((strLen - dotPos - 1) >= nAfterDot){
¡¡¡¡¡¡¡¡¡¡¡¡nAfter = dotPos + nAfterDot + 1;
¡¡¡¡¡¡¡¡¡¡¡¡nTen =1;
¡¡¡¡¡¡¡¡¡¡¡¡for(j=0;j<nAfterDot;j++){
¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡nTen = nTen*10;
¡¡¡¡¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡¡¡¡¡resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
¡¡¡¡¡¡¡¡¡¡¡¡return resultStr;
¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡else{
¡¡¡¡¡¡¡¡¡¡¡¡resultStr = srcStr;
¡¡¡¡¡¡¡¡¡¡¡¡for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡resultStr = resultStr+"0";
¡¡¡¡¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡¡¡¡¡return resultStr;
¡¡¡¡¡¡¡¡}
¡¡¡¡}
}

function FormatNumber1(srcStr,nAfterDot){
¡¡¡¡var srcStr,nAfterDot;
¡¡¡¡var resultStr,nTen;
¡¡¡¡srcStr = ""+srcStr+"";
¡¡¡¡strLen = srcStr.length;
¡¡¡¡dotPos = srcStr.indexOf(".",0);
    //alert(strLen);
    //alert(dotPos);
¡¡¡¡if (dotPos == -1){
¡¡¡¡¡¡¡¡resultStr = srcStr+".";
¡¡¡¡¡¡¡¡for (i=0;i<nAfterDot;i++){
¡¡¡¡¡¡¡¡¡¡¡¡resultStr = resultStr+"0";
¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡return resultStr;
       
¡¡¡¡}
¡¡¡¡else{
¡¡¡¡¡¡¡¡if ((strLen - dotPos - 1) >= nAfterDot){

¡¡¡¡¡¡¡¡¡¡¡¡nAfter = dotPos + nAfterDot + 1;
¡¡¡¡¡¡¡¡¡¡¡¡nTen =1;
¡¡¡¡¡¡¡¡¡¡¡¡for(j=0;j<nAfterDot;j++){
¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡nTen = nTen*10;
¡¡¡¡¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡¡¡¡¡resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
            resultStr=FormatNumber1(resultStr,nAfterDot);
¡¡¡¡¡¡¡¡¡¡¡¡return resultStr;
¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡else{
	
¡¡¡¡¡¡¡¡¡¡¡¡resultStr = srcStr;
¡¡¡¡¡¡¡¡¡¡¡¡for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡resultStr = resultStr+"0";
¡¡¡¡¡¡¡¡¡¡¡¡}
¡¡¡¡¡¡¡¡¡¡¡¡return resultStr;
¡¡¡¡¡¡¡¡}
¡¡¡¡}
}

//-->
</SCRIPT>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<link href="top.css" rel="stylesheet" type="text/css">
<title></title>
</head>

<body>
<table width="100%" height="77"  border="0" cellpadding="0" cellspacing="0" class="top_bt">
  <tr>
    <td width="" bgcolor="#175768"><div align="left"><img src="images/sys_bt3.jpg" width="237" height="69" align="absmiddle" /></div></td>
    <!--<td width="" bgcolor="#175768"><div align="left" class="top_txt">
      <div align="left">
        ¾ºÂô½»Ò×ÏµÍ³</div>
    </div></td>-->
    <td width="80%" valign="middle" bgcolor="#175768">
	<table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
      <tr>
	  <td style="display:none" align=right><span class="top_t">µ±Ç°½»Ò×½Ú£º</span></td><td align=left style="display:none"><span id=sysorder class="ts_txt">&nbsp;&nbsp;&nbsp;&nbsp;</span></td><td align=right><span class="top_t">¿ªÊÐÊ±¼ä£º</span></td><td align=left><span id=stime class="ts_txt">&nbsp;&nbsp;&nbsp;&nbsp;</span></td><td align=right><span class="top_t">µ¹¼ÆÊ±¿ªÊ¼Ê±¼ä£º</span></td><td align=left><span id=etime class="ts_txt">&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
	  </tr>
	  <tr>
	  <td colspan=2 style="display:none">&nbsp;</td><td align=right><span class="top_t" >ÏµÍ³×´Ì¬£º</span></td><td align=left ><span  class="imp" id=curstatus>&nbsp;&nbsp;&nbsp;&nbsp;</span></td><td align=right><span class="top_t">µ¹¼ÆÊ±(30Ãë)£º</span></td><td align=left ><span class="imp" id=ctime>&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
	  </tr>
	  <!--
	  <tr>
        <td ><div align="left"><span class="ts_txt"><span class="t1">µ±Ç°Ëù´¦½»Ò×½Ú£º</span> <span id=sysorder>&nbsp;&nbsp;&nbsp;&nbsp;</span> &nbsp;&nbsp;</span><span class="t1">±¾½Ú¿ªÊ¼Ê±¼ä£º</span> <span id=stime class="ts_txt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <span class="t1">±¾½Ú½áÊøÊ±¼ä£º</span><span id=etime class="ts_txt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div></td>
      </tr>
      <tr>
        <td width=""><div align="left"><span class="ts_txt"><span class="t1">ÏµÍ³×´Ì¬£º</span> </span><span class="imp" id=curstatus></span>&nbsp;&nbsp;<span class="ts_txt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="t1">µ¹¼ÆÊ±£º</span> </span><span class="imp" id=ctime></span></div></td>
      </tr>
	  -->
    </table>
	</td>
  </tr>
</table>
</body>
</html>

<script type="text/javascript">
<!--
/*ÊµÊ±Ë¢ÐÂ¿ØÖÆº¯Êý*/
var req;
function startReading()
{
	var url = "http://${application.SYSTEMINFO.url}/${application.SYSTEMINFO.urlContext}/servlet/getCountdownTimeXML?partitionID=${application.partitionID}&h="+Date();
	// /vendue11/servlet/getCountdownTimeXML?partitionID=11&h=Sun%20Nov%2017%2021:29:14%202013 
	//<R><CT>0</CT><CT>5</CT></R>
	//var url = "hqData.xml?h="+Date();
	readNewData();
}

function readNewData()
{	
	$.ajax({
		type: "POST",
		url:"${ctx}/hsdata_getCountdownTimeXML.action",
		async: false,
		dataType:"json",
	    error: function(request) {
	    	//alert("ÇëË¢ÐÂÒ³Ãæ");
	    	location.reload();
	    },
	    success: function(req) {
	    	//È¡µÃµ±Ç°½»Ò×ÉÌÆ·ÊýÁ¿
	    	responseXML = createXml(req.responseXML);
			
			var ctime;
			
			//È¡µÃÊÐ³¡µ±Ç°×´Ì¬
			var xml = responseXML.getElementsByTagName("CT");
			if(xml[0] != null && xml[0].firstChild != null)
			{
				ctime = xml[0].firstChild.data;
			}		
			if(ctime >= 0)
			{
				window.ctime.innerHTML = ctime;		
			}
			else
			{
				window.ctime.innerHTML = "--";
			}
	
			window.setTimeout("startReading()",800);
	    }
	});
}

startReading();

function countDown()
{	
	var t = window.ctime.innerHTML;	
	if(t != "" && t != "0" && t != "--")
	{					
		if(!isNaN(t) && t > 0)
		{
			var s = new Number(t);
			s -= 1;
			if(s > 0) window.ctime.innerHTML = s;
		}				
	}
	window.setTimeout("countDown()",400);
}
countDown()
function createXml(str){ 
¡¡¡¡	if(document.all){ 
¡¡¡¡		var xmlDom=new ActiveXObject("Microsoft.XMLDOM") 
¡¡¡¡		xmlDom.loadXML(str) 
¡¡¡¡		return xmlDom 
¡¡¡¡} 
¡¡¡¡else 
¡¡¡¡return new DOMParser().parseFromString(str, "text/xml") 
¡¡¡¡}
</script>