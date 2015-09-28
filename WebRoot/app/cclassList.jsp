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
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('������ʾ','��ѡ��Ҫ��������Ʒ����','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('������ʾ','ÿ��ֻ�ܵ���һ����Ʒ����','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','������Ʒ������Ϣ');
				j('#fm').form('clear');
				j('#id').attr('value',row.ccId);
				j('#currentDate').attr('value',row.currentDate);
				j('#keyIdF').attr('value',row.keyId);
				j('#classLevel').attr('value',row.classLevel);
				j('#btype').attr('value',row.btype);
				j('#bstorage').attr('value',row.bstorage);
				if(row.levelOrder==0){
					j('#levelOrder').attr('value','0');
				}else{
					j('#levelOrder').attr('value',row.levelOrder);
				}
				if(row.jxnum==0){
					j('#jxnum').attr('value','0');
				}else{
					j('#jxnum').attr('value',row.jxnum);
				}
				
				j('#startPrice').attr('value',row.startPrice);
				j('#lowPrice').attr('value',row.lowPrice);
				j('#addLowPrice').attr('value',row.addLowPrice);
				j('#dealNum').attr('value',row.dealNum);
				j('#ccCode').attr('value',row.ccCode);
				url = 'cclass_modify.action';
			}
		}
		function saveUser(){
			var levelCode = j('#jxnum').val();
			if(levelCode==-1){
				j.messager.alert('������ʾ','��ѡ����༶��','warning');
			  	return false;
			}
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
				ids += rows[i].ccId + ';';
			}
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
			if(ids==''){
				j.messager.alert('������ʾ','��ѡ��Ҫɾ������Ʒ����','warning');
				return false;
			}

			if (rows){
					j.messager.confirm('������ʾ','ɾ����Ʒ���ཫͬʱɾ����Ӧ��������Ʒ��ȷ��Ҫɾ����ѡ�е���Ʒ������',function(r){
						if (r){
							j.post('cclass_delAll.action?sj='+Math.random(),{ids:ids},function(result){
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
		
		function delZXSP(){
			var keyId = j('#keyId').val();
			j.messager.confirm('������ʾ','���³�ȡ��ѡ��Ʒ�������ԭ��ȡ����ѡ��Ʒ��գ�ȷ��������',function(r2){
			  	if (r2){
					j.messager.confirm('������ʾ','ȷ��Ҫ���³�ȡ��ѡ��Ʒ��',function(r){
						if (r){
							j.post('cclass_delZXSP.action?sj='+Math.random(),{keyId:keyId},function(result){
								if (result.success=="true"){
									j('#dg').datagrid('reload');	// reload the user data
									j("#cxcq").linkbutton('disable');
									j("#qkzxsp").linkbutton('disable');
									j("#zxcsh").linkbutton('enable');
								} else {
									j.messager.alert('����',result.msg);
								}
							},'json');
						}
					});
				}
			   });
		}
		
		j(document).ready(function(){
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
		});
		
		j(function () {
            j('#dg').datagrid({
                title: '��ѡ��Ʒ�����б�',
                loadMsg: "���ݼ����У����Ժ󡭡�",
                striped: true,
                collapsible: true,
                url: 'cclass_getListJson.action?sj='+Math.random(),
                height:600,
                remoteSort: false,
                idField: 'ccId',
                frozenColumns: [[
                    { field: 'ccId', checkbox: true }
                ]],
                columns: [[
                    { field: 'bstorage', title: '�������', width: 210, align: 'center', sortable: true },
                    { field: 'btype', title: 'Ʒ��', width: 150, align: 'center', sortable: true },
                    { field: 'startPrice', title: '�𱨼�', width: 80, align: 'center', sortable: true },
                    { field: 'dealNum', title: '��������', width: 80, align: 'center', sortable: true },
                    { field: 'lowPrice', title: '�׼�', width: 80, align: 'center', sortable: true },
                    { field: 'addLowPrice', title: '�Ӽ��׼�', width: 80, align: 'center', sortable: true },
                    { field: 'jxnum', title: '��ѡ����', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = value;
                        	if(value=='0'){
                        		result = '';
                        	}
                            return result;
                        }, sortable: true },
                    { field: 'classLevel', title: '���༶��', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '';
                        	if(value==3){
                        		result = '��ѡ';
                        	}else if(value==2){
                        		result = '��ѡ';
                        	}else if(value==1){
                        		result = '��ѡ';
                        	}
                            return result;
                        }},
                    { field: 'levelOrder', title: '��������', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = value;
                        	if(value=='0'){
                        		result = '';
                        	}
                            return result;
                        }, sortable: true },
                    { field: 'keyId', title: 'key�ı�ʶ', hidden:'true'},
                    { field: 'currentDate', title: '��ǰ����', hidden:'true' }
                ]],
                pagination: false,
                rownumbers: true,
                onLoadSuccess: function (result) {
                	//j('#pp').pagination('options').total=result.total;
                },
                toolbar: '#toolbar'
            });
        });
		
		
		function query(){
			var keyId = j('#keyId').val();
			if(keyId==-1){
				j.messager.alert('������ʾ','��ѡ��Ҫ��ѯ��key','warning');
				return false;
			}
			j('#dg').datagrid({url:"cclass_getListJson.action?sj="+Math.random()+"&keyId="+keyId,method:"post"});
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
	<a id="tzsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="editUser()">������Ʒ����</a>
	<a id="scsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeUser()">ɾ����Ʒ����</a>
	<!-- 
	<a id="qkzxsp" plain="true" href="javascript:void(0)" class="easyui-linkbutton" onclick="delSCSP()" iconCls="icon-cancel">��ԭ��Ʒ����</a>
	 -->
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">��ѯ</a>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">��ѡ��Ʒ����</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="commodityClassify.ccId"/>
		<input type="hidden" id="currentDate" name="commodityClassify.currentDate"/>
		<input type="hidden" id="keyIdF" name="commodityClassify.keyId"/>
		<input type="hidden" id="ccCode" name="commodityClassify.ccCode"/>
			<div class="fitem">
				<label>�׼�:</label>
				<input id="lowPrice" name="commodityClassify.lowPrice" class="easyui-validatebox" required="true" missingMessage="�׼۲���Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�Ӽ��׼�:</label>
				<input id="addLowPrice" name="commodityClassify.addLowPrice" />
			</div>
			<div class="fitem">
				<label>���༶��:</label>
				<s:select id="classLevel" name="commodityClassify.classLevel" list="#{3:'��ѡ',2:'��ѡ',1:'��ѡ'}" listKey="key" listValue="value" headerKey="-1" headerValue="--��ѡ��--"></s:select>
			</div>
			<div class="fitem">
				<label>��������:</label>
				<input id="jxnum" name="commodityClassify.jxnum" class="easyui-validatebox" required="true" missingMessage="������������Ϊ��"/>
			</div>
			<div class="fitem">
				<label>��������:</label>
				<input id="levelOrder" name="commodityClassify.levelOrder" class="easyui-validatebox" required="true" missingMessage="����˳����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�������:</label>
				<input id="bstorage" name="commodityClassify.bstorage" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>Ʒ��:</label>
				<input id="btype" name="commodityClassify.btype" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�𱨼�:</label>
				<input id="startPrice" name="commodityClassify.startPrice" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>��������:</label>
				<input id="dealNum" name="commodityClassify.dealNum" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
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
