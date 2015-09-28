<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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
    <td valign="bottom" width="20%"><img id=imgID src="images/01.jpg" width="282" height="25" border="0" usemap="#Map"></td>
    <td valign="bottom" width="10%" align=center><!--<img src="images/ht_btn.jpg" style="cursor:hand" onclick="showContract()" width="90" height="24" border="0">--></td>
    <td width="" valign="middle"><div align="center" style="display:none" id="st"><img src="images/d.gif" width="62" height="20" align="absmiddle">&nbsp;<span class="t2" id=remaintime>--</span>&nbsp;昼</div></td>
    <td valign="bottom" align=right>汽了�唆孱�圷/欽&nbsp;&nbsp;&nbsp;</td>  
  </tr>
</table>

<map name="Map">
  <area shape="rect" coords="4,-3,122,25" href="#" onclick="link(1)">
  <area shape="rect" coords="152,2,257,25" href="#" onclick="link(2)">
</map>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function link(v)
{
	if(v == 1)
	{
		imgID.src = "images/01.jpg";
		//window.parent.frames('commodityList').location='http://124.127.44.53:20023/vendue11/hq.jsp?netType=1';
		parent.document.getElementById('frm1').rows = "28,*,0";
	}
	else if(v == 2)
	{
		imgID.src = "images/02.jpg";
		parent.document.getElementById('frm1').rows = "28,0,*";
		//window.parent.frames('commodityList').location='../commodityList.jsp';
	}	
}

/*function setTime()
{
	var t1 = remaintime.innerText;
	if(!isNaN(t1) && t1 > 0)
	{
		remaintime.innerText = t1 - 1;
	}
	else
	{
		remaintime.innerText = "--";
		window.setTimeout("setDis('none');",2);
	}	
}

function setDis(v)
{
	st.style.display = v;
}

function showContract()
{
	window.open("contractDemo.html","contract","toolbar=no,location=no,resizable=yes,scrollbars=yes,menubar=no,width=800,height=350");
}*/

//-->
</SCRIPT>