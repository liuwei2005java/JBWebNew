<%@page contentType = "text/html; charset=GBK" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript" src="${ctx}/resources/JEasyUI/jquery-1.7.2.js"></script>

<!-- jqueryeasyui -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/JEasyUI/themes/default/easyui.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/JEasyUI/themes/icon.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/JEasyUI/demo.css"></link>


<script type="text/javascript" src="${ctx}/resources/JEasyUI/jquery.easyui.min.js"></script>
<!-- jqueryeasyui -->

<script type="text/javascript">
function getContxtPath(){
	return "${ctx}";
}
function getRootId(){
	return '${session.changsuoId}';
}
function resize(obj){
	var hgt = 500;
	if(obj.contentWindow.document.body.scrollHeight>hgt){
		obj.height = obj.contentWindow.document.body.scrollHeight;
	}else{
		obj.height = hgt;
	}
}

/**
 * 弹出窗口的返回按钮
 */
function goback(){
	if(window.opener==null){//不是弹出
		history.back();
	}else{
		window.close();
	}
}

function setReadonlyAll(){
	var origLength;
	origLength = document.all.length;
	var tagName = "";
	for(i=0;i<origLength;i++){
		if(document.all[i]){
			obj = document.all[i];
			tagName=obj.tagName;
			if(tagName=="SELECT" || tagName=="INPUT" || tagName=="TEXTAREA" ){
		    	obj.readOnly=true;
		    	if(tagName=="SELECT"){
		    		obj.disabled=true;
		    	}
		    	if(tagName=="INPUT"){
			    	obj.onclick=function(){};
			    	obj.onfocus=function(){};
			    	obj.onblur=function(){};
			    	if(obj.type=="checkbox"){
				    	obj.disabled=true;
			    	}
		    	}
		    	obj.className="readonly";
			}else if(tagName=="A"){
				if(obj.href!="" || typeof(obj.onclick)=="function")
					obj.style.display="none";
					//obj.parentNode.removeChild(obj);
			}
		}
	}
}

function chooseAllEle(thisObj,targetObjName){
	//var chooseAllObj = document.getElementById("chooseAll");
	var obj = document.getElementsByName(targetObjName);
	for(var x=0;x<obj.length;x++){
		if(thisObj.checked==true)
			document.getElementsByName(targetObjName)[x].checked = true;
		else
			document.getElementsByName(targetObjName)[x].checked = false ;
	}
}

function openWindowComm(title,tableNMCode,unitCode){
	var yearStr = "${param.yearStr}";
	var monthStr = "${param.monthStr}";
	var width = screen.width-10;
	var height = screen.height-70;
	var url = "${ctx}/appDataFile_setLNCharts.action?tableNMCode="+tableNMCode+"&yearStr="+yearStr+"&unitCode="+unitCode+"&monthStr="+monthStr;
	window.open(url,title,'height="+height+",width="+width+",top=0,left=0,toolbar=no,menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
}

//打开重点公司或重点项目历年
function openWindowCTLN(title,deptCode){
	var width = screen.width-10;
	var height = screen.height-70;
	var url = "${ctx}/appShow_showTwoCompItem.action?deptCode="+deptCode;
	window.open(url,title,'height="+height+",width="+width+",top=0,left=0,toolbar=no,menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
}
</script>

