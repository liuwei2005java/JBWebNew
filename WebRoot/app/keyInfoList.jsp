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
			j('#dlg').dialog('open').dialog('setTitle','���key��Ϣ');
			j('#fm').form('clear');
			j('#isUse').attr('value',0);
			j('#submitCode').attr('value','%CC%E1%BD%BB');
			url = 'keyInfo_add.action';
		}
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('������ʾ','��ѡ��Ҫ�޸ĵ�key','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('������ʾ','ÿ��ֻ���޸�һ��key','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','�޸�key��Ϣ');
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
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
			if(ids==''){
				j.messager.alert('������ʾ','��ѡ��Ҫɾ����key','warning');
				return false;
			}

			if (rows){
			  j.messager.confirm('������ʾ','ɾ��ѡ����key����key����Ӧ����ѡ��Ʒ��������ʷ����ȫ����ɾ����ȷ��ɾ����',function(r2){
			  	if (r2){
					j.messager.confirm('������ʾ','ȷ��Ҫɾ����ѡ�е�key��',function(r){
						if (r){
							j.post('keyInfo_delAll.action?sj='+Math.random(),{ids:ids},function(result){
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
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
			if(ids==''){
				j.messager.alert('������ʾ','��ѡ��Ҫ��¼״̬���õ�key','warning');
				return false;
			}

			if (rows){
					j.messager.confirm('������ʾ','ȷ��Ҫ����ѡ��key�ĵ�¼״̬��',function(r){
						if (r){
							j.post('keyInfo_reLoginStatus.action?sj='+Math.random(),{ids:ids},function(result){
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
                title: 'key��Ϣ�б�',
                loadMsg: "���ݼ����У����Ժ󡭡�",
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
                	{ field: 'isUse', title: '�Ƿ����', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '������';
                        	if(value=='1'){
                        		result = '����';
                        	}
                            return result;
                        }
                    },
                    { field: 'userName', title: '�û�����', width: 80, align: 'center', sortable: true },
                    { field: 'passWord', title: '�û�����', width: 80, align: 'center', sortable: true },
                    { field: 'keyWd', title: 'key����', width: 80, align: 'center', sortable: true },
                    { field: 'kcode', title: 'key����', width: 150, align: 'center', sortable: true },
                    { field: 'maxFGRNum', title: '�ָ�����', width: 80, align: 'center', sortable: true },
                    { field: 'maxBTNum', title: '��������', width: 80, align: 'center', sortable: true },
                    { field: 'isLowFin', title: '��׼۾���״̬', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '��';
                        	if(value=='1'){
                        		result = '���';
                        	}
                            return result;
                        }
                    },
                    { field: 'submit', title: 'submit����', hidden:'true' },
                    { field: 'isLoginOK', title: '��¼״̬', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = 'δ��¼';
                        	if(value=='1'){
                        		result = '�ѵ�¼';
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
				j.messager.confirm('������ʾ','ȷ��Ҫ׼��������',function(r){
					if (r){
						j.ajax({
							type: 'GET',
							async:false,
							url: 'oca_init.action?sj='+Math.random(),
							success:function(msg){
								var result = eval('('+msg+')');
								if(result.success=="true"){
									j.messager.alert('��ʾ','����׼���ɹ�����鿴��¼״̬','warning');
									j('#dg').datagrid('reload');
								}else if(result.success == "loginfalse"){
									j.messager.alert('������ʾ','����Key��¼ʧ�ܣ�'+result.loginfail,'warning');
									j('#dg').datagrid('reload');
								}else{
									j.messager.alert('������ʾ','������Ϣ��'+result.fail,'warning');
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
			  j.messager.alert('������ʾ','��ѡ��Ҫ������key','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('������ʾ','ÿ��ֻ������һ��key','warning');
			  return false;
			}
				j.messager.confirm('������ʾ','ȷ��Ҫ��ʼ���о��������',function(r){
					if (r){
						j.ajax({
							type: 'GET',
							async:false,
							url: 'oca_jbStart.action?keyId='+row.keyId+'&isLowFin='+isLowFin+'&sj='+Math.random(),
							success:function(msg){
								var result = eval('('+msg+')');
								if(result.success=="true"){
									j.messager.alert('��ʾ','�����ɹ�����鿴��¼״̬','warning');
									j('#dg').datagrid('reload');
								}else if(result.success == "loginfalse"){
									j.messager.alert('������ʾ','����Key��¼ʧ�ܣ�'+result.loginfail,'warning');
									j('#dg').datagrid('reload');
								}else{
									j.messager.alert('������ʾ','������Ϣ��'+result.fail,'warning');
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
					alert("�ύ�ɹ�");
	}
		
		j(document).ready(function(){
			var username = '${session.username}';
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
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
				j('#startstatus').linkbutton({text:'����+10����'});
				j('#startstatus').attr('status','1');
			}else {
				j('#startstatus').linkbutton({text:'����+10����'});
				j('#startstatus').attr('status','0');
			}
			
		}
		
		//�õ����б�
		function getALLBIAO(){
			
			//j.messager.confirm('������ʾ','ȷ��Ҫ�ռ����б���',function(r){
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
				text = "ȷ��Ҫ����+10������";
				status ="0";
			}else{
				text = "ȷ��Ҫ����+10������";
				status ="1";
			}
			j.messager.confirm('������ʾ',text,function(r){
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
		
		//��ʾ������ʱ��
		function disTime()
		{
			var JB_DATE_TIME = "${application.JB_DATE_TIME}";
			
			
			var d = new Date();
			var date3=JB_DATE_TIME-d.getTime();
			var leave1=date3%(24*3600*1000);    //����������ʣ��ĺ�����
			var hours=Math.floor(leave1/(3600*1000));
			var leave2=leave1%(3600*1000);        //����Сʱ����ʣ��ĺ�����

			var minutes=Math.floor(leave2/(60*1000));
			var leave3=leave2%(60*1000);      //�����������ʣ��ĺ�����

			var seconds=Math.round(leave3/1000);
			if(hours==0){
				if(minutes==10 && seconds==0){
					alert("��ע�⣺���뾺���������10���ӣ�");
				}
				if(minutes==5 && seconds==0){
					alert("��ע�⣺���뾺���������5���ӣ�");
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
	<a id="addKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="newUser()">���Key</a>
	<a id="updateKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="editUser()">�޸�Key</a>
	<a id="delKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeUser()">ɾ��Key</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">��ѯ</a>
	<a id="redoKey" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onClick="reLoginStatus()">���õ�¼״̬</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="goJB(1)">�����������(����)</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="goJB(0)">׼���������(+10)</a>
	<a id="startstatus" href="javascript:void(0)" class="easyui-linkbutton" status="${application.START_STATUS }" onClick="setStartstatus()">����+10����</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onClick="getStartDate()">��ȡ��ʼʱ��</a>
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onClick="getALLBIAO()">������б�</a>
	
	<a id="go" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onClick="init()">����׼��</a>
	
	<font color="red" style="font-weight: bold;font-size: 16px">�����������ʱ��<span style="font-size: 16px" id=clock>&nbsp;</span></font>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	<div id="dlg" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">key��Ϣ</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="keyInfo.keyId"/>
			<div class="fitem">
				<label>�Ƿ�ʹ��:</label>
				<s:select id="isUse" name="keyInfo.isUse" list="#{0:'������',1:'����'}" listKey="key" listValue="value"></s:select>
			</div>
			<div class="fitem">
				<label>�û�����:</label>
				<input id="userName" name="keyInfo.userName" class="easyui-validatebox" required="true" missingMessage="�û����Ʋ���Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�û�����:</label>
				<input id="passWord" name="keyInfo.passWord" class="easyui-validatebox" required="true" missingMessage="�û����벻��Ϊ��"/>
			</div>
			<div class="fitem">
				<label>key����:</label>
				<input id="keyWd" name="keyInfo.keyWd" class="easyui-validatebox" required="true" missingMessage="key���벻��Ϊ��"/>
			</div>
			<div class="fitem">
				<label>key����:</label>
				<input id="kcode" name="keyInfo.kcode" class="easyui-validatebox" required="true" missingMessage="key���벻��Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�ָ�����:</label>
				<input id="maxFGRNum" name="maxFGRNum" class="easyui-validatebox" required="true" missingMessage="�ָ���������Ϊ��"/>
			</div>
			<div class="fitem">
				<label>��������:</label>
				<input id="maxBTNum" name="maxBTNum" class="easyui-validatebox" required="true" missingMessage="������������Ϊ��"/>
			</div>
			<div class="fitem">
				<label>submit����:</label>
				<input id="submitCode" name="keyInfo.submit" class="easyui-validatebox" required="true" missingMessage="submit���벻��Ϊ��"/>
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
