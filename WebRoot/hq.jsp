<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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


function createXml(str){ 
　　	if(document.all){ 
　　		var xmlDom=new ActiveXObject("Microsoft.XMLDOM") 
　　		xmlDom.loadXML(str) 
　　		return xmlDom 
　　} 
　　else 
　　return new DOMParser().parseFromString(str, "text/xml") 
　　} 

//-->
</SCRIPT>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script type="text/javascript" 	src="log4javascript.js"></script>
<script language="javascript" src="tools.js"></script>
<link href="main.css" rel="stylesheet" type="text/css">
<title></title>
</head>






<body bgcolor="#CEDEDE" >
<form name=frm>
<SCRIPT LANGUAGE="JavaScript">
<!--
var alertPrices = new Array();
function setAlertPrices(v)
{
	var hasVal = false;
	var code = v.split(";")[0];
	for(var i=0;i<alertPrices.length;i++)
	{
		var c = alertPrices[i].split(";")[0];
		if(c == code)
		{
			hasVal = true;
			break;
		}
	}
	if(!hasVal)
	{
		alertPrices[alertPrices.length] = v;
	}	
}
function checkIfAlertPrice(code,price)
{
	var r = false;
	for(var i=0;i<alertPrices.length;i++)
	{
		var c = alertPrices[i].split(";")[0];
		var p = alertPrices[i].split(";")[1];
		if(c == code && p == price)
		{
			r = true;
			break;
		}
	}
	return r;
}
//-->
</SCRIPT>
<table width="94%"   border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#ffffff" class="buy_td">
 <TR bgcolor="#ffffff" height="30">
	<TD colspan=9>
	<input type=button name=addprivate value="启动保护" onclick="return deleteRec1()">
	<input type=button name=addprivate1 value="取消保护" onclick="return deleteRec10()">
	</td>
</TR>
</table>
<table width="94%" id=tb  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#465D71" class="buy_td">  
  <tr bgcolor="#6B88A3" height="30">
    <td width="3%" height="26" align=center>&nbsp;</td>
	<td width="7%" height="26" align=center>&nbsp;<span id=netStatus></span></td>
    <td width=""><div align="center"><span class="td_bt">标的号</span></div></td>
    <td width=""><div align="center"><span class="td_bt">品种</span></div></td>	
	<td width=""><div align="center"><span class="td_bt">提货库点</span></div></td>
    <td width=""><div align="center"><span class="td_bt">交易数量</span></div></td>
    <td width=""><div align="center"><span class="td_bt">提货日期</span></div></td>
	<td width=""><div align="center"><span class="td_bt">起报价</span></div></td>
	<td width=""><div align="center"><span class="td_bt">最新价格</span></div></td>	
	<!-- <td width=""><div align="center" class="td_bt">倒计时</div></td> -->
	<td width=""><div align="center" class="td_bt">竞价状态</div></td>
	<td width=""><div align="center" class="td_bt">最高价格</div></td>
	<td width=""><div align="center" class="td_bt">保护状态</div></td>
  </tr>
  <s:iterator value="zxList" id="ocr">
  	<SCRIPT LANGUAGE="JavaScript">
	<!--
		setAlertPrices("${ocr.bcode};${ocr.zxjg}");
	//-->
	</SCRIPT>
			<tr bgcolor="#EBFFE1" height="" align=center>
	<td width="3%" height="25">
		<input type=checkbox name=ck value='${ocr.bcode}:${ocr.maxPrice }'>
		</td>
	<td width="7%" height="25">
	<input name="Submit" type="button" onclick="winopen('${ocr.bcode};${ocr.btype};${ocr.bstorage};${ocr.dealNum};${ocr.ocrDate};${ocr.startPrice};${ocr.zxjg};${maxPrice }')" class="jj_btn" value="下 单" >
	</td>
	<td width="">${ocr.bcode}</td>
	<td width="">${ocr.btype}</td>
	<td width="" >${ocr.bstorage}</td>
	<td width="" align=right>${ocr.dealNum}</td>
	<td width="" >${ocr.ocrDate}</td>
	<td width="" align=right>${ocr.startPrice}</td>
	<td width="" align=right class=rf>${ocr.zxjg}</td>
	<!-- <td width="" ></td> -->					
	<td width="" >${ocr.isOK }
	<td width="" >${ocr.maxPrice }
	<td width="" >${ocr.status }
	</td>
	</tr>
  </s:iterator>
    
</table>
<br>
<TABLE width="94%" align="center">
<TR>
	<TD align=center>
	<input type=hidden name=add>
	<input type=hidden name=lid value="${lidStr }">
	<input type=hidden name=netType value="1">
	</TD>
</TR>
</TABLE >
</form>
</body>
</html>
<script type="text/javascript">
<!--
/*实时刷新控制函数*/

var hqID = '${hqID }';//行情流水号
var curAmount = '0';//当前交易商品数量
var curSysStatus = '${curSysStatus }';//当前系统状态
var responseXML;
function startReading()
{
	var url = "/vendue11/servlet/getHqXML?partitionID=11&hqID="+ hqID +"&h="+Date();
	readNewData();
}

var status = "";//市场当前状态
var order = "";//当前交易节编号
var starttime = "";//当前交易节开始时间
var endtime = "";//当前交易节结束时间

var c = new Array('<font color=red>●</font>','<font color=green>●</font>');
var i = 2;

function display()
{
	netStatus.innerHTML = c[i%2];
	i++;
}

function readNewData()
{
	$.ajax({
		type: "POST",
		url:"${ctx}/hsdata_getHqXML.action?hqID="+ hqID,
		async: false,
		dataType:"json",
	    error: function(request) {
	    	//alert("请刷新页面");
	    	location.reload();
	    },
	    success: function(req) {
	    	//取得当前交易商品数量
	    	responseXML = createXml(req.responseXML);
			var curAmountXml = responseXML.getElementsByTagName("N");
			var curAmountXmlVal = 0;
			if(curAmountXml[0] != null && curAmountXml[0].firstChild != null)
			{
				curAmountXmlVal = curAmountXml[0].firstChild.data;
			}
			var sysStatusXml = responseXML.getElementsByTagName("S");
			var sysStatusXmlVal = 0;
			if(sysStatusXml[0] != null && sysStatusXml[0].firstChild != null)
			{
				sysStatusXmlVal = sysStatusXml[0].firstChild.data;
			}
			if(sysStatusXmlVal == 4) curAmountXmlVal = 0;
			if(curSysStatus != sysStatusXmlVal)
			{
				//curSysStatus = sysStatusXmlVal;
				//ifrm.location.reload();							
				//ifrm.location = "hq_refresh.jsp?lid=${lidStr}&netType=1&curSysStatus="+curSysStatus;
				//hq_refresh.jsp?lid=6969577109318703192&netType=1&curSysStatus=
				//window.parent.parent.frames("commodityFrame").frames("commodityList").location = "commodityList.jsp?lid=6969577109318703192&netType=1";		
				//if(curSysStatus == 2)//交易时间启用下单按钮
				//{
					//validSubmitBT(1);
				//}
				//else//非交易时间禁用下单按钮
				//{
					//validSubmitBT(0);
				//}
			}
			
			//取得行情增量数据
			var hqList = responseXML.getElementsByTagName("Q");
			for(var i=0;i<hqList.length;i++)
			{
				var nodelist = hqList[i].childNodes;
				var code = "";
				var price = "";
				var amount = "";
				var rtime = "";
				var hid = "";			
				if(nodelist[0] != null && nodelist[0].firstChild != null) code = nodelist[0].firstChild.data;//商品代码
				if(nodelist[1] != null && nodelist[1].firstChild != null) price = nodelist[1].firstChild.data;//价格
				if(nodelist[2] != null && nodelist[2].firstChild != null) amount = nodelist[2].firstChild.data;//数量
				if(nodelist[3] != null && nodelist[3].firstChild != null) rtime = nodelist[3].firstChild.data;//剩余时间
				if(nodelist[4] != null && nodelist[4].firstChild != null) hid = nodelist[4].firstChild.data;//行情id
				if(hid > hqID) hqID = hid;
				recoverData(code,price,amount,rtime);
				//更新所有商品
				//recoverDataCommodity(code,price,amount,rtime);
			}
			
			//更新系统状态
			//取得市场当前状态
			var xml = responseXML.getElementsByTagName("S");
			if(xml[0] != null && xml[0].firstChild != null)
			{
				status = xml[0].firstChild.data;
			}
			
			//取得当前交易节编号
			xml = responseXML.getElementsByTagName("U");
			if(xml[0] != null && xml[0].firstChild != null)
			{
				order = xml[0].firstChild.data;
			}
			
			//取得当前交易节开始时间
			xml = responseXML.getElementsByTagName("T1");
			if(xml[0] != null && xml[0].firstChild != null)
			{
				starttime = xml[0].firstChild.data;
			}
			
			//取得当前交易节结束时间
			xml = responseXML.getElementsByTagName("T2");
			if(xml[0] != null && xml[0].firstChild != null)
			{
				endtime = xml[0].firstChild.data;
			}
			
			//recoverSysData(status,order,starttime,endtime);				
			display();	
			window.setTimeout("startReading()",200);
	    }
	});
		
}

startReading();

function recoverData(code,price,amount,rtime)
{
	var tbObj = tb;
	for(var j=1;j<tbObj.rows.length;j++)
	{		    
		var c = tbObj.rows(j).cells(2).innerHTML;
		if(c == code)
		{
			//更新价格
			if(price > 0)
			{
				tbObj.rows(j).cells(8).innerHTML = "<span class=rf>"+ FormatNumber(price,2) +"</span>";
				tbObj.rows(j).cells(0).innerHTML = "<input type=checkbox name=ck value='"+code+":"+FormatNumber(price,2)+"'>";
			}
			else
			{
				tbObj.rows(j).cells(8).innerHTML = FormatNumber(price,2);
			}

			//如果已达到限价，则将该标的下单按钮禁用
			if(checkIfAlertPrice(code,price))
			{
				invalidTheSubmitBT(j - 1);
			}
			
			if('${userName}' != amount)
			{						
				tbObj.rows(j).cells(9).innerHTML = '&nbsp;<font color=grey>非有效报价</font>';
			}									
			else
			{
				tbObj.rows(j).cells(9).innerHTML = '&nbsp;<b><font color=green>有效报价</font></b>';
			}

			//更新数量
			//tbObj.rows(j).cells(2).innerHTML = amount;

			/*
			//更新剩余时间
			if(rtime > 0)
			{
				tbObj.rows(j).cells(8).className = "rf";
				tbObj.rows(j).cells(8).innerHTML = rtime;
			}
			else if(rtime == 0)
			{
				tbObj.rows(j).cells(8).innerHTML = rtime;
			}
			*/			
			break;
		}
	}	
}

function recoverDataCommodity(code,price,amount,rtime)
{
	var tbObj = window.parent.parent.frames("commodityFrame").frames("commodityList").tb;
	for(var j=1;j<tbObj.rows.length;j++)
	{		    
		var c = tbObj.rows(j).cells(1).innerHTML;
		if(c == code)
		{
			//更新价格
			if(price > 0)
			{
				tbObj.rows(j).cells(7).innerHTML = "<span class=rf>"+ FormatNumber(price,2) +"</span>";
			}
			else
			{
				tbObj.rows(j).cells(7).innerHTML = FormatNumber(price,2);
			}	
			break;
		}
	}	
}

function recoverSysData(status_v,order_v,starttime_v,endtime_v)
{
	var winObj = window.parent.parent.parent.frames("top").frames("top1");
	
	var t = winObj.ctime.innerHTML;
	
	if(winObj != null)
	{
		//设置市场当前状态	
		var statusStr = "";//市场状态描述
		if(status_v == 2)
		{
			statusStr = "<span class=rf>开市(交易中)</font>";
		}
		else if(status_v == 3)
		{
			statusStr = "交易结束";
		}
		else if(status_v == 4)
		{
			statusStr = "暂停交易";
		}
		else if(status_v == 5)
		{
			statusStr = "闭市";
		}
		else if(status_v == 9)
		{
			statusStr = "开市(等待交易)";
		}
		else
		{
			statusStr = "等待交易";
		}
		winObj.curstatus.innerHTML = statusStr;
		
		if(status_v == 2)
		{
			//设置当前交易节编号
			winObj.sysorder.innerHTML = order_v;
			
			//设置交易节开市时间
			winObj.stime.innerHTML = starttime_v;
			
			//设置交易节结束时间
			winObj.etime.innerHTML = subTime(endtime_v, '30');			
		}	
		else
		{
			//设置当前交易节编号
			winObj.sysorder.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;";
			
			//设置交易节开市时间
			winObj.stime.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			
			//设置交易节结束时间
			winObj.etime.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		}
	}
	
	
	
}

function subTime(s, i)
{
	var time = new Date();
	time.setHours(s.split(":")[0]);
	time.setMinutes(s.split(":")[1]);
	time.setSeconds(s.split(":")[2]);
    var rtime = new Date();	
	rtime.setTime(time.getTime() - i*1000);

	return fmtTime(rtime.getHours())+":"+fmtTime(rtime.getMinutes())+":"+fmtTime(rtime.getSeconds());
}

function fmtTime(v)
{
	if(parseInt(v) >= 0 && parseInt(v) < 10)
	{
		return "0" + v;
	}
	else
	{
		return v;
	}
}

function deleteRec1()
{
	//复选框获取值
    var oCheckbox = document.getElementsByName("ck");
    var arr = "";
    for(var i=0;i<oCheckbox.length;i++)
    {
         if(oCheckbox[i].checked)
         {    
        	 arr += oCheckbox[i].value+";";
         }
    }
    if(arr==""){
    	alert("请选择要进行保护的标");
    	return false;
    }
    if (confirm("确定要对选定的标进行保护吗？")==true){ 
    	$.ajax({
    		type: "POST",
    		url:"${ctx}/hsdata_protect.action?protectBCodes="+arr,
    		async: true,
    		dataType:"json",
    	    error: function(request) {
    	    	alert(11);
    	    },
    	    success: function(data) {
    	    	setTimeout(reloadhq,3000);
    	    	alert("保护成功");
    	    }
    	});
    }
}

function deleteRec10()
{
	if (confirm("确定要取消保护吗？")==true){ 
		$.ajax({
			type: "POST",
			url:"${ctx}/hsdata_unProtect.action",
			async: false,
			dataType:"json",
		    error: function(request) {
		    	alert(11);
		    },
		    success: function(data) {
		    	setTimeout(reloadhq,1000);
		    	alert("取消保护成功");
		    }
		});
	}
}

function reloadhq(){
	location.reload();
}

function winopen(id)
{
	window.showModelessDialog("${ctx}/hsdata_order.action?selectBAll="+id,"", "dialogWidth=550px; dialogHeight=300px;dialogTop=300;dialogLeft=100; status=yes;scroll=yes;help=no;"); 
	//window.open("http://124.127.44.53:20023/vendue11/submit/order.jsp?id="+id,"", "width=550px; height=300px;top=300;left=100; status=yes;scroll=yes;help=no;");
}

function validSubmitBT(v)
{
	var bts = document.all("Submit");
	var disabled;
	if(v == 0)//disable
	{
		disabled = true;
	}
	else if(v == 1)//enable
	{
		disabled = false;
	}
	if(bts != null)
	{
		if(!bts.length)
		{
			bts.disabled = disabled;
		}
		else
		{
			for(var i=0;i<bts.length;i++)
			{
				bts[i].disabled = disabled;
			}
		}
	}	
}

function invalidTheSubmitBT(idx)
{
	var bts = document.all("Submit");	
	if(bts != null)
	{
		if(!bts.length)
		{
			bts.disabled = true;
		}
		else
		{
			for(var i=0;i<bts.length;i++)
			{
				if(i == idx)
				{
					bts[i].disabled = true;
					break;
				}
				
			}
		}
	}	
}
//-->
</SCRIPT>

<iframe width=0 height=0 id=ifrm frameborder=0 src="" APPLICATION="yes"/>
