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
	//��������ʹ����$�����ʹ��j����
	var j = jQuery.noConflict(true);
	
	j.extend(j.fn.validatebox.defaults.rules, { 
		onlyNum: { 
		validator: function(value, param){ 
			var re = /\D/g;
			return !re.test(value); 
		}, 
		message: 'ֻ����������' 
		} 
		});

		var url;
		function newUser(){
			j('#dlg').dialog('open').dialog('setTitle','���ϵͳ��Ϣ');
			j('#fm').form('clear');
			j('#isTest').attr('value','0');
			j('#localCatalog').attr('value','c:/jb/');
			url = 'systemInfo_add.action';
		}
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('������ʾ','��ѡ��Ҫϵͳ�ļ�¼','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('������ʾ','ÿ��ֻ���޸�һ����¼','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','�޸�ϵͳ��Ϣ');
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
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
			if(ids==''){
				j.messager.alert('������ʾ','��ѡ��Ҫɾ���ļ�¼','warning');
				return false;
			}

			if (rows){
				j.messager.confirm('������ʾ','ȷ��Ҫɾ����ѡ�еļ�¼��',function(r){
					if (r){
						j.post('systemInfo_delAll.action?sj='+Math.random(),{ids:ids},function(result){
							if (result.success=="true"){
								j('#dg').datagrid('reload');	// reload the user data
								j('#dg').datagrid('unselectAll');
							} else {
								j.messager.alert('����',result.msg);
							}
						},'json');
					}
				});
			}
		}
		
		
		j(function () {
            j('#dg').datagrid({
                title: '������־�б�',
                loadMsg: "���ݼ����У����Ժ󡭡�",
                striped: true,
                collapsible: true,
                url: 'jbLog_getListJson.action?sj='+Math.random(),
                height:600,
                remoteSort: false,
                columns: [[
                    { field: 'bcode', title: '��ı��', width: 100, align: 'center', sortable: true },
                    { field: 'btype', title: 'Ʒ��', width: 150, align: 'center', sortable: true },
                    { field: 'bstorage', title: '�������', width: 210, align: 'center', sortable: true },
                    { field: 'dealNum', title: '��������', width: 80, align: 'center', sortable: true },
                    { field: 'startPrice', title: '�𱨼�', width: 80, align: 'center', sortable: true },
                    { field: 'lowPrice', title: '�׼�', width: 80, align: 'center', sortable: true },
                    { field: 'delayTime', title: '�ӳ�ʱ��', width: 80, align: 'center', sortable: true },
                    { field: 'status', title: '״̬', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = 'ʧ��';
                        	if(value=='1'){
                        		result = '�ɹ�';
                        	}
                            return result;
                        }
                    },
                    { field: 'failInfo', title: 'ʧ��ԭ��', width: 210, align: 'center', sortable: true }
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
				j.messager.alert('������ʾ','��ѡ��Ҫ��ѯ��key','warning');
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
	ѡ��Key��<s:select id="keyId" name="keyId" list="keyInfos" listKey="keyId" listValue="userName" headerKey="-1" headerValue="--��ѡ��--"></s:select>
	����״̬��<s:select id="status" name="status" list="#{0:'ʧ��',1:'�ɹ�'}" listKey="key" listValue="value"></s:select>
	ʧ��ԭ��<input id="failInfo" type="text"/>
	Ʒ�֣�<input id="btype" type="text"/>
	������㣺<input id="bstorage" type="text"/>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">��ѯ</a>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">ϵͳ��Ϣ</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="systemInfo.sysId"/>
			<div class="fitem">
				<label>�Ƿ����:</label>
				<s:select id="isTest" name="systemInfo.isTest" list="#{0:'��',1:'��'}" listKey="key" listValue="value"></s:select>
			</div>
			<div class="fitem">
				<label>URL��ַ:</label>
				<input id="url" name="systemInfo.url" class="easyui-validatebox" required="true" missingMessage="URL��ַ����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>orderҳ�����:</label>
				<input id="orderParam" name="systemInfo.orderParam" class="easyui-validatebox" required="true" missingMessage="orderҳ���������Ϊ��"/>
			</div>
			<div class="fitem">
				<label>����·��:</label>
				<input id="localCatalog" name="systemInfo.localCatalog" class="easyui-validatebox" required="true" missingMessage="����·������Ϊ��"/>
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="saveUser()">����</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onClick="javascript:j('#dlg').dialog('close')">�ر�</a>
	</div>
	<div id="winDivId" class="easyui-window" closed="true" modal="true" title="��λ" style="width:840px;height:430px;padding:5px;">
		<iframe scrolling="no" frameborder="0" id="winDivIf" style="width:100%;height:100%;"></iframe>
	</div>
</body>
</html>
