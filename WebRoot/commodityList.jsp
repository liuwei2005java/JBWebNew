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
            resultStr=FormatNumber1(resultStr,nAfterDot);
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
<script language="javascript" src="tools.js"></script>
<title></title>
</head>




<body bgcolor="#CEDEDE" >
<form name=frm>
<!--
<table width="94%"   border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#ffffff" class="buy_td">
 <TR bgcolor="#ffffff" height="30">
	<TD colspan=9>&nbsp;&nbsp;炎議催:	<input type=text name=code value="">&nbsp;&nbsp;住歯垂泣:		<input type=text name=storage value="">&nbsp;&nbsp;<input type=button onclick="search()" value="臥儂"></td><TD align=right><input type=button name=addprivate value="紗秘徭僉斌瞳" onclick="return deleteRec(frm,tb,'ck')"><input type=hidden name=add></TD>
</TR>
</table>
-->
<input type=hidden name=code>
<input type=hidden name=storage>
<input type=hidden name=add>
<table width="94%" id=tb  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#465D71" class="buy_td"> 
  <tr bgcolor="#6B88A3" height="30">
    <td width="7%" height="26" align=center>&nbsp;</td>
    <td width=""><div align="center"><span class="td_bt">炎議催</span></div></td>
    <td width=""><div align="center"><span class="td_bt">瞳嶽</span></div></td>	
	<td width=""><div align="center"><span class="td_bt">住歯垂泣</span></div></td>
    <td width=""><div align="center"><span class="td_bt">住叟方楚</span></div></td>
    <td width=""><div align="center"><span class="td_bt">戻歯晩豚</span></div></td>
	<td width=""><div align="center"><span class="td_bt">軟烏勺</span></div></td>
	<td width=""><div align="center"><span class="td_bt">恷仟勺鯉</span></div></td>	
	<td width=""><div align="center" class="td_bt">斌瞳�蠻�</div></td>
  </tr>
     				  
</table>
<br>
<table width="94%" align="center">
  <tr>
  <td align=right>
  輝念1/0匈		慌0訳		
  
  
	  <font color=gray>遍匈</font>	  
	  <font color=gray>貧匈</font>
	  
	  <font color=gray>和匈</font>	  
	  <font color=gray>硫匈</font>
	  
  </td>
  </tr>
</table>
<input type=hidden name=pageIndex value="1">
<input type=hidden name=lid value="6969577109318703192">
<input type=hidden name=netType value="1">
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function search(code, storage)
{
	frm.code.value = code;
	frm.storage.value = storage;
	frm.pageIndex.value = 1;
	frm.submit();
}

function deleteRec1()
{
	return deleteRec(frm,tb,'ck');
}

function pgTurn(idx)
{
	frm.pageIndex.value = idx;
	frm.submit();
}

function viewDetail(code)
{
window.open("http://124.127.44.53:20023/vendue11/submit/commodityDetail.jsp?id="+code,"","toolbar=no,location=no,resizable=no,scrollbars=yes,menubar=no,width=518,height=575,top=50,left=100");
}

function winopen(id)
{
	//window.open("order.jsp?id="+id,"","toolbar=no,location=no,scrollbars=yes,menubar=no,width=401,height=330");
	//window.showModalDialog("order.jsp?id="+id,"", "dialogWidth=420px; dialogHeight=280px;dialogTop=300;dialogLeft=100; status=yes;scroll=yes;help=no;"); 
	window.showModelessDialog("order.jsp?id="+id,"", "dialogWidth=420px; dialogHeight=280px;dialogTop=300;dialogLeft=100; status=yes;scroll=yes;help=no;"); 
}
-->
</SCRIPT>
