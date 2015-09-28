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
			j('#dlg').dialog('open').dialog('setTitle','添加系统信息');
			j('#fm').form('clear');
			j('#isTest').attr('value','0');
			j('#localCatalog').attr('value','c:/jb/');
			url = 'systemInfo_add.action';
		}
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('错误提示','请选择要系统的记录','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('错误提示','每次只能修改一条记录','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','修改系统信息');
				j('#id').attr('value',row.sysId);
				j('#url').attr('value',row.url);
				j('#isTest').attr('value',row.isTest);
				j('#orderParam').attr('value',row.orderParam);
				j('#localCatalog').attr('value',row.localCatalog);
				
				url = 'systemInfo_add.action';
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
				ids += rows[i].sysId + ';';
			}
			j.messager.defaults={ok:"确定",cancel:"取消"};
			if(ids==''){
				j.messager.alert('错误提示','请选择要删除的记录','warning');
				return false;
			}

			if (rows){
				j.messager.confirm('警告提示','确定要删除所选中的记录吗？',function(r){
					if (r){
						j.post('systemInfo_delAll.action?sj='+Math.random(),{ids:ids},function(result){
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
                title: '竞标日志列表',
                loadMsg: "数据加载中，请稍后……",
                striped: true,
                collapsible: true,
                url: 'jbLog_getListJson.action?sj='+Math.random(),
                height:600,
                remoteSort: false,
                columns: [[
                    { field: 'bcode', title: '标的编号', width: 100, align: 'center', sortable: true },
                    { field: 'btype', title: '品种', width: 150, align: 'center', sortable: true },
                    { field: 'bstorage', title: '交货库点', width: 210, align: 'center', sortable: true },
                    { field: 'dealNum', title: '交易数量', width: 80, align: 'center', sortable: true },
                    { field: 'startPrice', title: '起报价', width: 80, align: 'center', sortable: true },
                    { field: 'lowPrice', title: '底价', width: 80, align: 'center', sortable: true },
                    { field: 'delayTime', title: '延迟时间', width: 80, align: 'center', sortable: true },
                    { field: 'status', title: '状态', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '失败';
                        	if(value=='1'){
                        		result = '成功';
                        	}
                            return result;
                        }
                    },
                    { field: 'failInfo', title: '失败原因', width: 210, align: 'center', sortable: true }
                ]],
                pagination: false,
                rownumbers: true,
                onLoadSuccess: function (result) {
                	//j('#pp').pagination('options').total=result.total;
                },
                toolbar: '#toolbar'
            });
        });
		
		j(document).ready(function(){
			var username = '${session.username}';
			if(username!="lw"){
				j("#addKey").linkbutton('disable');
				j("#updateKey").linkbutton('disable');
				j("#delKey").linkbutton('disable');
			}
		});
		
		function query(){
			var keyId = j('#keyId').val();
			if(keyId==-1){
				j.messager.alert('错误提示','请选择要查询的key','warning');
				return false;
			}
			var status = j('#status').val();
			var failInfo = j('#failInfo').val();
			var btype = j('#btype').val();
			var bstorage = j('#bstorage').val();
			j('#dg').datagrid({url:"jbLog_getListJson.action?sj="+Math.random()+"&keyId="+keyId+"&status="+status+"&failInfo="+failInfo+"&btype="+btype+"&bstorage="+bstorage,method:"post"});
		}
		
		function openAddUnit(){
			j("#winDivIf").attr("src","systemInfo_list.action?appTableNMId=${param.appTableNMId}&isTableNM=1&sj="+Math.random());
			j('#winDivId').window('open');
		}
		
		function closeWin(){
			j('#winDivId').window('close');
			j('#dg').datagrid('reload');
		}
		
		function closeChildren(){
			parent.closeWin();
		}
	</script>
</head>
<body style="margin:0px;padding:0px">
<div id="toolbar">
	选择Key：<s:select id="keyId" name="keyId" list="keyInfos" listKey="keyId" listValue="userName" headerKey="-1" headerValue="--请选择--"></s:select>
	竞标状态：<s:select id="status" name="status" list="#{0:'失败',1:'成功'}" listKey="key" listValue="value"></s:select>
	失败原因：<input id="failInfo" type="text"/>
	品种：<input id="btype" type="text"/>
	交货库点：<input id="bstorage" type="text"/>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">查询</a>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">系统信息</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="systemInfo.sysId"/>
			<div class="fitem">
				<label>是否测试:</label>
				<s:select id="isTest" name="systemInfo.isTest" list="#{0:'否',1:'是'}" listKey="key" listValue="value"></s:select>
			</div>
			<div class="fitem">
				<label>URL地址:</label>
				<input id="url" name="systemInfo.url" class="easyui-validatebox" required="true" missingMessage="URL地址不能为空"/>
			</div>
			<div class="fitem">
				<label>order页面参数:</label>
				<input id="orderParam" name="systemInfo.orderParam" class="easyui-validatebox" required="true" missingMessage="order页面参数不能为空"/>
			</div>
			<div class="fitem">
				<label>本地路径:</label>
				<input id="localCatalog" name="systemInfo.localCatalog" class="easyui-validatebox" required="true" missingMessage="本地路径不能为空"/>
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
