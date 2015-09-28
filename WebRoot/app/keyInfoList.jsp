<%@page contentType="text/html; charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title></title>
	<style type="text/css">
		#fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			color:#666;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
	</style>
	<script type="text/javascript">
	//因其他库使用了$，因此使用j代替
	var j = jQuery.noConflict(true);
	
	j.extend(j.fn.validatebox.defaults.rules, { 
		onlyNum: { 
		validator: function(value, param){ 
			var re = /\D/g;
			return !re.test(value); 
		}, 
		message: '只能输入整数' 
		} 
		});

		var url;
		function newUser(){
			j('#dlg').dialog('open').dialog('setTitle','添加key信息');
			j('#fm').form('clear');
			j('#isUse').attr('value',0);
			j('#submitCode').attr('value','%CC%E1%BD%BB');
			url = 'keyInfo_add.action';
		}
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('错误提示','请选择要修改的key','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('错误提示','每次只能修改一个key','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','修改key信息');
				j('#id').attr('value',row.keyId);
				j('#isUse').attr('value',row.isUse);
				j('#userName').attr('value',row.userName);
				j('#passWord').attr('value',row.passWord);
				j('#keyWd').attr('value',row.keyWd);
				j('#submitCode').attr('value',row.submit);
				j('#kcode').attr('value',row.kcode);
				j('#maxFGRNum').attr('value',row.maxFGRNum);
				j('#maxBTNum').attr('value',row.maxBTNum);
				j('#lowMaxFGNum').attr('value',row.lowMaxFGNum);
				j('#lowMaxBTNum').attr('value',row.lowMaxBTNum);
				url = 'keyInfo_add.action';
			}
		}
		function saveUser(){
			j('#fm').form('submit',{
				url: url,
				onSubmit: function(){
					return j(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						j('#dlg').dialog('close');		// close the dialog
						j('#dg').datagrid('reload');	// reload the user data
						j('#dg').datagrid('unselectAll');
					} else {
						j.messager.show({
							title: 'Error',
							msg: result.msg
						});
					}
				}
			});
		}
		function removeUser(){
			//var row = j('#dg').datagrid('getSelected');
			//var ids = [];
			var ids = '';
			var rows = j('#dg').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				//ids.push(rows[i].id);
				ids += rows[i].keyId + ';';
			}
			j.messager.defaults={ok:"确定",cancel:"取消"};
			if(ids==''){
				j.messager.alert('错误提示','请选择要删除的key','warning');
				return false;
			}

			if (rows){
			  j.messager.confirm('警告提示','删除选定的key后，其key所对应的自选商品（包括历史）将全部被删除，确定删除吗？',function(r2){
			  	if (r2){
					j.messager.confirm('警告提示','确定要删除所选中的key吗？',function(r){
						if (r){
							j.post('keyInfo_delAll.action?sj='+Math.random(),{ids:ids},function(result){
								if (result.success=="true"){
									j('#dg').datagrid('reload');	// reload the user data
									j('#dg').datagrid('unselectAll');
								} else {
									j.messager.alert('错误',result.msg);
								}
							},'json');
						}
					});
				}
			   });
			}
		}
		
		function reLoginStatus(){
			//var row = j('#dg').datagrid('getSelected');
			//var ids = [];
			var ids = '';
			var rows = j('#dg').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				//ids.push(rows[i].id);
				ids += rows[i].keyId + ';';
			}
			j.messager.defaults={ok:"确定",cancel:"取消"};
			if(ids==''){
				j.messager.alert('错误提示','请选择要登录状态重置的key','warning');
				return false;
			}

			if (rows){
					j.messager.confirm('警告提示','确定要重置选中key的登录状态吗？',function(r){
						if (r){
							j.post('keyInfo_reLoginStatus.action?sj='+Math.random(),{ids:ids},function(result){
								if (result.success=="true"){
									j('#dg').datagrid('reload');	// reload the user data
									j('#dg').datagrid('unselectAll');
								} else {
									j.messager.alert('错误',result.msg);
								}
							},'json');
						}
					});
			}
		}
		
		
		j(function () {
            j('#dg').datagrid({
                title: 'key信息列表',
                loadMsg: "数据加载中，请稍后……",
                striped: true,
                collapsible: true,
                url: 'keyInfo_getListJson.action?sj='+Math.random(),
                height:600,
                sortName: 'orderNo',
                sortOrder: 'desc',
                remoteSort: false,
                idField: 'keyId',
                frozenColumns: [[
                    { field: 'keyId', checkbox: true }
                ]],
                columns: [[
                	{ field: 'isUse', title: '是否可用', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '不可用';
                        	if(value=='1'){
                        		result = '可用';
                        	}
                            return result;
                        }
                    },
                    { field: 'userName', title: '用户名称', width: 80, align: 'center', sortable: true },
                    { field: 'passWord', title: '用户密码', width: 80, align: 'center', sortable: true },
                    { field: 'keyWd', title: 'key密码', width: 80, align: 'center', sortable: true },
                    { field: 'kcode', title: 'key编码', width: 150, align: 'center', sortable: true },
                    { field: 'maxFGRNum', title: '分割数量', width: 80, align: 'center', sortable: true },
                    { field: 'maxBTNum', title: '白条数量', width: 80, align: 'center', sortable: true },
                    { field: 'isLowFin', title: '最底价竞标状态', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '否';
                        	if(value=='1'){
                        		result = '完成';
                        	}
                            return result;
                        }
                    },
                    { field: 'submit', title: 'submit编码', hidden:'true' },
                    { field: 'isLoginOK', title: '登录状态', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '未登录';
                        	if(value=='1'){
                        		result = '已登录';
                        	}
                            return result;
                        }
                    }
                ]],
                pagination: false,
                rownumbers: true,
                onLoadSuccess: function (result) {
                	//j('#pp').pagination('options').total=result.total;
                },
                toolbar: '#toolbar'
            });
        });
		
		function init(){
				j.messager.confirm('警告提示','确定要准备竞标吗？',function(r){
					if (r){
						j.ajax({
							type: 'GET',
							async:false,
							url: 'oca_init.action?sj='+Math.random(),
							success:function(msg){
								var result = eval('('+msg+')');
								if(result.success=="true"){
									j.messager.alert('提示','竞标准备成功，请查看登录状态','warning');
									j('#dg').datagrid('reload');
								}else if(result.success == "loginfalse"){
									j.messager.alert('错误提示','以下Key登录失败：'+result.loginfail,'warning');
									j('#dg').datagrid('reload');
								}else{
									j.messager.alert('错误提示','错误信息：'+result.fail,'warning');
									j('#dg').datagrid('reload');
								}
							}
						});
					}
				});
		}
		
		function goJB(isLowFin){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('错误提示','请选择要启动的key','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('错误提示','每次只能启动一个key','warning');
			  return false;
			}
				j.messager.confirm('警告提示','确定要开始运行竞标进程吗？',function(r){
					if (r){
						j.ajax({
							type: 'GET',
							async:false,
							url: 'oca_jbStart.action?keyId='+row.keyId+'&isLowFin='+isLowFin+'&sj='+Math.random(),
							success:function(msg){
								var result = eval('('+msg+')');
								if(result.success=="true"){
									j.messager.alert('提示','启动成功，请查看登录状态','warning');
									j('#dg').datagrid('reload');
								}else if(result.success == "loginfalse"){
									j.messager.alert('错误提示','以下Key登录失败：'+result.loginfail,'warning');
									j('#dg').datagrid('reload');
								}else{
									j.messager.alert('错误提示','错误信息：'+result.fail,'warning');
									j('#dg').datagrid('reload');
								}
							}
						});
					}
				});
		}
		
		function getStartDate(){
					j.ajax({
						type: 'GET',
						url: 'keyInfo_getStartDate.action?sj='+Math.random()
					});
					alert("提交成功");
	}
		
		j(document).ready(function(){
			var username = '${session.username}';
			j.messager.defaults={ok:"确定",cancel:"取消"};
			if(username!="lw"){
				j("#addKey").linkbutton('disable');
				j("#updateKey").linkbutton('disable');
				j("#delKey").linkbutton('disable');
				j("#redoKey").linkbutton('disable');
				j("#go").linkbutton('disable');
			}
		});
		
		function query(){
			j('#dg').datagrid({url:"keyInfo_getListJson.action?sj="+Math.random(),method:"post"});
		}
		
		function openAddUnit(){
			j("#winDivIf").attr("src","keyInfo_list.action?appTableNMId=${param.appTableNMId}&isTableNM=1&sj="+Math.random());
			j('#winDivId').window('open');
		}
		
		function closeWin(){
			j('#winDivId').window('close');
			j('#dg').datagrid('reload');
		}
		
		function closeChildren(){
			parent.closeWin();
		}
		
		function setStartstatusText(){
			var status = j('#startstatus').attr('status');
			if(status=='0' || status==''){
				j('#startstatus').linkbutton({text:'启动+10进程'});
				j('#startstatus').attr('status','1');
			}else {
				j('#startstatus').linkbutton({text:'结束+10进程'});
				j('#startstatus').attr('status','0');
			}
			
		}
		
		//得到所有标
		function getALLBIAO(){
			
			//j.messager.confirm('警告提示','确定要收集所有标吗？',function(r){
				//if (r){
					j.ajax({
						type: 'GET',
						url: 'oca_infoZXTmp.action?sj='+Math.random()
					});
				//}
			//});
		}
		
		function setStartstatus(){
			var status = j('#startstatus').attr('status');
			var text = "";
			if(status=='0' || status==''){
				text = "确定要结束+10进程吗？";
				status ="0";
			}else{
				text = "确定要启动+10进程吗？";
				status ="1";
			}
			j.messager.confirm('警告提示',text,function(r){
				if (r){
					j.ajax({
						type: 'GET',
						async:false,
						url: 'oca_setStartstatus.action?startstatus='+status+'&sj='+Math.random()
					});
				}
			});
			setStartstatusText();
		}
		
		//显示服务器时间
		function disTime()
		{
			var JB_DATE_TIME = "${application.JB_DATE_TIME}";
			
			
			var d = new Date();
			var date3=JB_DATE_TIME-d.getTime();
			var leave1=date3%(24*3600*1000);    //计算天数后剩余的毫秒数
			var hours=Math.floor(leave1/(3600*1000));
			var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数

			var minutes=Math.floor(leave2/(60*1000));
			var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数

			var seconds=Math.round(leave3/1000);
			if(hours==0){
				if(minutes==10 && seconds==0){
					alert("请注意：距离竞标结束还有10分钟！");
				}
				if(minutes==5 && seconds==0){
					alert("请注意：距离竞标结束还有5分钟！");
				}
			}
			
			if(JB_DATE_TIME!=""){
				clock.innerText = hours + ':' + minutes + ':' + seconds;
			}
			setTimeout("disTime()",1000);
		}
		
		j(document).ready(function(){setStartstatusText();disTime();});	
		
	</script>
</head>
<body style="margin:0px;padding:0px">
<div id="toolbar">
	<a id="addKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="newUser()">添加Key</a>
	<a id="updateKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="editUser()">修改Key</a>
	<a id="delKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeUser()">删除Key</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">查询</a>
	<a id="redoKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onClick="reLoginStatus()">重置登录状态</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="goJB(1)">启动竞标进程(到底)</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="goJB(0)">准备竞标进程(+10)</a>
	<a id="startstatus" href="javascript:void(0)" class="easyui-linkbutton" status="${application.START_STATUS }" onClick="setStartstatus()">启动+10进程</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onClick="getStartDate()">获取开始时间</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onClick="getALLBIAO()">获得所有标</a>
	
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="init()">竞标准备</a>
	
	<font color="red" style="font-weight: bold;font-size: 16px">竞标结束倒计时：<span style="font-size: 16px" id=clock>&nbsp;</span></font>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	<div id="dlg" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">key信息</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="keyInfo.keyId"/>
			<div class="fitem">
				<label>是否使用:</label>
				<s:select id="isUse" name="keyInfo.isUse" list="#{0:'不可用',1:'可用'}" listKey="key" listValue="value"></s:select>
			</div>
			<div class="fitem">
				<label>用户名称:</label>
				<input id="userName" name="keyInfo.userName" class="easyui-validatebox" required="true" missingMessage="用户名称不能为空"/>
			</div>
			<div class="fitem">
				<label>用户密码:</label>
				<input id="passWord" name="keyInfo.passWord" class="easyui-validatebox" required="true" missingMessage="用户密码不能为空"/>
			</div>
			<div class="fitem">
				<label>key密码:</label>
				<input id="keyWd" name="keyInfo.keyWd" class="easyui-validatebox" required="true" missingMessage="key密码不能为空"/>
			</div>
			<div class="fitem">
				<label>key编码:</label>
				<input id="kcode" name="keyInfo.kcode" class="easyui-validatebox" required="true" missingMessage="key编码不能为空"/>
			</div>
			<div class="fitem">
				<label>分割数量:</label>
				<input id="maxFGRNum" name="maxFGRNum" class="easyui-validatebox" required="true" missingMessage="分割数量不能为空"/>
			</div>
			<div class="fitem">
				<label>白条数量:</label>
				<input id="maxBTNum" name="maxBTNum" class="easyui-validatebox" required="true" missingMessage="白条数量不能为空"/>
			</div>
			<div class="fitem">
				<label>submit编码:</label>
				<input id="submitCode" name="keyInfo.submit" class="easyui-validatebox" required="true" missingMessage="submit编码不能为空"/>
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onClick="javascript:j('#dlg').dialog('close')">关闭</a>
	</div>
	<div id="winDivId" class="easyui-window" closed="true" modal="true" title="单位" style="width:840px;height:430px;padding:5px;">
		<iframe scrolling="no" frameborder="0" id="winDivIf" style="width:100%;height:100%;"></iframe>
	</div>
</body>
</html>
