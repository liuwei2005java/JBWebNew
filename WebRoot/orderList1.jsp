<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
<!--用户身份判断-->


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="main.css" rel="stylesheet" type="text/css">
<title></title>
</head>

<script type="text/javascript">

/*实时刷新控制函数*/

var lastTime = "${lastTime}";//委托更新时间

function startReading()
{
	var url = "/vendue11/servlet/getOrderXML?partitionID=11&lastTime="+ lastTime +"&h="+Date();
	///vendue11/servlet/getOrderXML?partitionID=11&lastTime=1999-09-09/09:09:09&h=Sun%20Nov%2017%2021:49:08%202013 
	//<R><T>1999-09-09/09:09:09</T></R>
	readNewData();
}

function readNewData()
{
	$.ajax({
		type: "POST",
		url:"${ctx}/hsdata_getOrderXML.action?lastTime="+lastTime,
		async: false,
		dataType:"json",
	    error: function(request) {
	    	//alert("请刷新页面");
	    	location.reload();
	    },
	    success: function(req) {
	    	//取得当前交易商品数量
	    	responseXML = createXml(req.responseXML);
			//取得委托最新更新时间
			var lastModiytimeXml = responseXML.getElementsByTagName("T");
			if(lastModiytimeXml[0] != null && lastModiytimeXml[0].firstChild != null) lastTime = lastModiytimeXml[0].firstChild.data;
			
			
			//取得委托增量数据
			var orderList = responseXML.getElementsByTagName("S");
			for(var i=0;i<orderList.length;i++)
			{
				var nodelist = orderList[i].childNodes;
				var sid;
				var code;
				var price;
				var amount;
				var validAmount;
				var createtime;
				var modifytime;
				if(nodelist[0] != null && nodelist[0].firstChild != null) sid = nodelist[0].firstChild.data;//委托号
				if(nodelist[1] != null && nodelist[1].firstChild != null) code = nodelist[1].firstChild.data;//标的号
				if(nodelist[2] != null && nodelist[2].firstChild != null) price = nodelist[2].firstChild.data;//委托价格
				if(nodelist[3] != null && nodelist[3].firstChild != null) amount = nodelist[3].firstChild.data;//委托数量
				if(nodelist[4] != null && nodelist[4].firstChild != null) validAmount = nodelist[4].firstChild.data;//有效委托数量
				if(nodelist[5] != null && nodelist[5].firstChild != null) createtime = nodelist[5].firstChild.data;//委托时间
				if(nodelist[6] != null && nodelist[6].firstChild != null) modifytime = nodelist[6].firstChild.data;//更新时间
				recoverData(sid,code,price,amount,validAmount,createtime,modifytime);
			}
			window.setTimeout("startReading()",1000);
	    }
	});
}

function recoverData(sid,code,price,amount,validAmount,createtime,modifytime)
{
	var tbObj = tb;
	var hasVal = false;
	for(var j=1;j<tbObj.rows.length;j++)
	{		    
		var c = tbObj.rows(j).cells(1).innerHTML;
		if(c == sid)
		{
			hasVal = true;
			if(validAmount <= 0)
			{
				//删除被抢单的记录
				tbObj.deleteRow(j);
			}												
			break;
		}
	}
	if(!hasVal && validAmount > 0)
	{
		//插入新的委托纪录
		var newRow = tbObj.insertRow(1);
		newRow.bgColor="#EBFFE1";
		newRow.align = "center";
		newRow.noWrap = "true";
		newRow.height = 23;
		var newCell = newRow.insertCell(0);
		newCell.innerHTML = '<span class="xbt1" >>></span>';
		newCell = newRow.insertCell(1);//委托号
		newCell.innerHTML = sid;
		newCell = newRow.insertCell(2);//标的号
		newCell.innerHTML = code;
		newCell = newRow.insertCell(3);//报价
		newCell.align = "right";
		newCell.innerHTML = price;
		newCell = newRow.insertCell(4);//数量
		newCell.align = "right";
		newCell.innerHTML = amount;
		newCell = newRow.insertCell(5);//提交时间
		newCell.innerHTML = createtime;
		newCell = newRow.insertCell(6);//有效成交数量
		newCell.align = "right";
		if(validAmount > 0) newCell.className = "rf";
		newCell.innerHTML = validAmount;
		newCell = newRow.insertCell(7);//修改时间
		newCell.innerHTML = modifytime;		
	}
}
function createXml(str){ 
　　	if(document.all){ 
　　		var xmlDom=new ActiveXObject("Microsoft.XMLDOM") 
　　		xmlDom.loadXML(str) 
　　		return xmlDom 
　　} 
　　else 
　　return new DOMParser().parseFromString(str, "text/xml") 
　　}
window.setTimeout("startReading()",1000);

</script>

<body bgcolor="#CEDEDE" >
<table width="94%" id=tb  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#465D71" class="buy_td">
  <tr bgcolor="#6B88A3">
    <td width="3%" height="26"><div align="center" class="xbt">&nbsp;</div></td>
		  <td width="" height="26"><div align="center" class="xbt">委托号</div></td>		 
		  <td width="" ><div align="center" class="xbt">标的号</div></td>
		  <td width="" ><div align="center" class="xbt">报价</div></td>
          <td width="" ><div align="center" class="xbt">数量</div></td>
          <td width="" ><div align="center" class="xbt">提交时间</div></td>
          <td width="" ><div align="center" class="xbt">有效成交数量</div></td>
          <td width="" ><div align="center" class="xbt">修改时间</div></td>
  </tr>  
  <s:iterator value="finList" id="f">
  	<tr bgcolor="#EBFFE1" height="23" align=center>
		<td width="3%" height="">
		<span class="xbt1" >>></span>
		</td>
		<td width="">${f.wth }</td>
		<td width="" >${f.bcode }</td>
		<td width="" align=right>${f.bj }</td>
		<td width="" align=right>${f.sl }</td>
		<td width="">${f.tjsj }</td>
		<td width="" align=right class=rf>${f.yxsl }</td>
		<td width="">${f.xgsj }</td>
	</tr>
  </s:iterator>
</table>
<br>
</body>
</html>
	