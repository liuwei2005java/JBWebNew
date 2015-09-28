<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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
            resultStr=FormatNumber(resultStr,nAfterDot);
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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="top.css" rel="stylesheet" type="text/css">
<title></title>
</head>
<script type="text/javascript">
/*ÊµÊ±Ë¢ÐÂ¿ØÖÆº¯Êý*/
var req=null;
var starttime;
var timeoutcout=0;

var cnt = 0;

function readNewData()
{
	
	if(req.readyState==4 && req.status == 200)
	{
		//freshKeyOn();
		timeoutcout=0;
		var nameXml = req.responseXML.getElementsByTagName("N");
		var nameXmlVal = 0;
		if(nameXml[0] != null && nameXml[0].firstChild != null)
		{
			nameXmlVal = nameXml[0].firstChild.data;
		}
		if(nameXmlVal == 'null')
		{
			alert("ÄúµÄµÇÂ½ÒÑ³¬Ê±»òÕß¸ÃÕÊºÅÔÚÆäËûµØ·½µÇÂ¼£¬ÇëÖØÐÂµÇÂ½¡£");
			window.parent.parent.location = "error.html";
			return;
		}
		cnt = 0;
	}
	else if(req.readyState==4 && req.status != 200)
	{		
		
		//freshKeyOn();
		if(cnt > 200)
		{
			//alert("ÄúµÄµÇÂ½ÒÑ³¬Ê±£¬ÇëÖØÐÂµÇÂ½£¡");
			//window.parent.parent.location = "error.html";
			//return;
		}
		cnt++;
		
	}	
	
}



///////////////////////////////////////


/*window.setTimeout("checkTimeout()",1000);	
function checkTimeout()
{
	var lastTime=new Date();
	
	var n=lastTime - starttime ;
	
	if(n>60000)
	{
		req.abort();
		timeoutcout++;
	}
	if (timeoutcout>30)
	{
		//alert("ÄúµÄµÇÂ½ÒÑ³¬Ê±£¬ÇëÖØÐÂµÇÂ½!!");
		//window.parent.parent.location = "error.html";
		//return;
	}
	window.setTimeout("checkTimeout()",1000);
}*/

</script>


<body>
<form name=frm action="logOff.jsp">
<table width="100%" height="77"  border="0" cellpadding="0" cellspacing="0" class="top_bt">
  <tr>
    <td bgcolor="#175768" valign="middle">
	<table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align=right><span class="top_t">±ê×¼Ê±¼ä: </span></td><td align=left><span class="ts_txt">

<span class="time_num" id=clock>&nbsp;</span>


<SCRIPT LANGUAGE="JavaScript">
<!--
//È¡µÃ¿Í»§¶ËÓë·þÎñÆ÷µÄÊ±¼ä²î
var t = new Date();
var i;

$(document).ready(function() {
	$.ajax({
		type: "POST",
		url:"${ctx}/hsdata_getHSServerTime.action",
		async: false,
		dataType:"json",
	    error: function(request) {
	    	alert(11);
	    },
	    success: function(data) {
	    	i = t.getTime() - data.timeLong;
	    	document.getElementById("userNameId").innerText = data.USERNAME;
	    	disTime();
	    }
	});
});

//ÏÔÊ¾·þÎñÆ÷Ê±¼ä
function disTime()
{
	var d = new Date();
	d.setTime(d.getTime() - i);
	clock.innerText = disNum(d.getHours()) + ':' + disNum(d.getMinutes()) + ':' + disNum(d.getSeconds());
	setTimeout("disTime()",1000);
}

function disNum(n)
{
	if(n >= 0 && n < 10)
	{
		return '0' + n;
	}
	else 
	{
		return n;
	}
}



//-->
</SCRIPT></span></td>
		<td align=right></td>
      </tr>
      <tr>
        <td align=right><span class="top_t">µ±Ç°ÓÃ»§: </span></td><td align=left><span id="userNameId" class="ts_txt"></span></td>
		<td align=right ></td>
      </tr>
    </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function chgPwd()
{
	window.open("chgpwd.jsp","","toolbar=no,location=no,scrollbars=yes,menubar=no,width=300,height=150");
}
//-->
</SCRIPT>
