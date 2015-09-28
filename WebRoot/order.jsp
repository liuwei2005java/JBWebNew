<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/resources/JEasyUI/jquery-1.7.2.js"></script>
<!--公用js函数-->
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--用户身份判断-->





<html>
<head>
<base target="_self"> 
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="main.css" rel="stylesheet" type="text/css">
<title>报单</title>
</head>
<style>
.kp {
	background-color: #E7E7E7;
	border: 1px solid #73A7B5;
}
</style>
<script>
		//self.moveTo(0, 0); 
		//alert(screen.width+";"+screen.height);
		//self.resizeTo(screen.width,screen.height);
		//alert('进来就执行！');
		//window.resizeTo(500,500);
		
		//window.moveTo(, );
		//self.moveTo(700, 700);
		//self.resizeTo(screen.width,screen.height);
</script>

	<body bgcolor="#CEDEDE">
	<form name="frm" onsubmit="return false;">
	<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
	  <tr>
	    <td valign="top">
	      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="0">
	        <tr>
	          <td valign="middle"><table width="100%" height="130"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#175768">
	            <tr bgcolor="#CEDEDE">
	              <td width="" height="26"><div align="center" class="dsb">商品编码</div></td>
	              <td width="" bgcolor="#FFFFFF" class="dsb">&nbsp;${selectMap.b_code }</td>
	              <td width=""><div align="center" class="dsb">品 种</div></td>
	              <td width="" bgcolor="#FFFFFF" class="dsb">&nbsp;${selectMap.b_pz }</td>
	            </tr>
				<tr bgcolor="#CEDEDE">
	              <td width="" height="26"><div align="center" class="dsb">交易数量（吨）</div></td>
	              <td width="" bgcolor="#FFFFFF" class="dsb">&nbsp;${selectMap.b_jysl }</td>
	              <td width=""><div align="center" class="dsb">最新价格（元/吨）</div></td>
	              <td width="" bgcolor="#FFFFFF" class="dsb">&nbsp;${selectMap.b_zxjg }</td>
	            </tr>
	            <tr bgcolor="#CEDEDE">
	              <td><div align="center" class="dsb">起报价（元/吨）</div></td>
	              <td bgcolor="#FFFFFF" class="dsb">&nbsp;${selectMap.b_qbj }</td>
	              <td height="26"><div align="center" class="dsb">报价（元/吨）</div></td>
	              <td bgcolor="#FFFFFF">
	              	  <input name="orderPrice" id="orderPriceId"
	              			 value="${selectMap.b_zxjg }" 
	              			 type="text" class="k" size="13">
				      <input type=button name=addp4 value="+10" class="kp" 
				      	     onkeydown="if(event.keyCode==13) return false;" onclick="chgprice7(0)">
				  </td>
	            </tr>
	            <tr bgcolor="#CEDEDE">
	              <td colspan="3"><div align="center" class="dsb" style="color: red;font-weight: bold;font-size: 14px" >最高价格</div></td>
	              <td colspan="1" bgcolor="#FFFFFF" class="dsb" style="color: red;font-weight: bold;font-size: 14px">&nbsp;${selectMap.maxPrice}</td>
	            </tr>		            
	          </table>
	            <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="30%">&nbsp;</td>
	                <td width="40%">
	                	
	                	<div style="height:25px"></div>
	                	
	                	<div align="right">
	                		<input name="queren" type="button" onclick="submit1();" value="确认"/>
	                 	    <input name="canclebt" type="button" onclick="window.close()" class="k" value="取消">
	                	</div>
	                </td>
	                <td width="30%">&nbsp;</td>
	              </tr>
	            </table>
	          </td>
	        </tr>
	      </table>
	      <br>
		  <table width="100%" height="30"  border="0" align="center" cellpadding="0" cellspacing="0">
	        <tr>
	          <td class="bt1">
				提示：可以直接输入金额,但输入的金额不能超过"最高金额"。
	          </td>
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
var lastPrice = '70910.0';
var stepPrice = '10.0';	
var beginPrice = '20000.0';
var alertPrice = '0.0';
var maxPrice = '${selectMap.maxPrice}';
function chgprice7(type)
{
	var orderPrice = frm.orderPrice.value;
	var newPrice = parseFloat(orderPrice) + parseFloat(stepPrice);
	if(parseFloat(maxPrice) < newPrice){
		alert("报价已超最高上限");
		frm.addp4.focus();
	}else {
		frm.orderPrice.value = newPrice;
	}
}

function submit1(){
	var bcode = "${selectMap.b_code }";
	var orderPrice = $("#orderPriceId").val();
	if(parseFloat(maxPrice) < parseFloat(orderPrice)){
		alert("报价已超最高上限");
		frm.addp4.focus();
		return false;
	}
	$.ajax({
		type: "POST",
		url:"${ctx}/hsdata_submit.action?bcode="+bcode+"&orderPrice="+orderPrice,
		async: false,
		dataType:"json",
	    error: function(request) {
	    	alert(11);
	    },
	    success: function(data) {
	    	if(data.flag==true){
	    		alert("报单成功!");
	    	}else{
	    		alert("报单失败!");
	    	}
	    	window.close();
	    }
	});
}

//-->
</SCRIPT>
