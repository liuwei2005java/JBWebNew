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
		//��ѡ��Ʒ��ʼ�����Ӿ���ϵͳ�г�ȡ
		function infiXZ(){
			var keyId = j('#keyId').val();
			if(keyId==-1){
				j.messager.alert('������ʾ','��ѡ����Ҫ��ȡ��key','warning');
				return false;
			}
			
			var win = j.messager.progress({
				title:'��ȴ�...',
				msg:'���ڳ�ȡ��ѡ��Ʒ����...'
			});
			
			j.ajax({
					type: 'GET',
					async:false,
					url: 'oca_infoZX.action?sj='+Math.random(),
					data: 'keyId='+keyId,
					success:function(msg){
						j.messager.progress('close');
						if(msg=="true"){
						  j('#dg').datagrid('reload');
						  j("#cxcq").linkbutton('enable');
						  j("#qkzxsp").linkbutton('enable');
						  j("#zxcsh").linkbutton('disable');
						}else {
						  j.messager.alert('������ʾ','��ȡ��ѡ��Ʒʧ�ܣ������³�ȡ','warning');
						}
					}
				});
		}
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('������ʾ','��ѡ��Ҫ��������Ʒ','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('������ʾ','ÿ��ֻ�ܵ���һ����Ʒ','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','������Ʒ��Ϣ');
				j('#fm').form('clear');
				j('#id').attr('value',row.ocaId);
				j('#ocaDate').attr('value',row.ocaDate);
				j('#orderNo').attr('value',row.orderNo);
				j('#keyIdF').attr('value',row.keyId);
				j('#bcode').attr('value',row.bcode);
				j('#btype').attr('value',row.btype);
				j('#bstorage').attr('value',row.bstorage);
				j('#dealNum').attr('value',row.dealNum);
				j('#startPrice').attr('value',row.startPrice);
				j('#lowPrice').attr('value',row.lowPrice);
				j('#delayTime').attr('value',row.delayTime);
				j('#isLow').attr('value',row.isLow);
				j('#addLowPrice').attr('value',row.addLowPrice);
				
				url = 'oca_update.action';
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
				ids += rows[i].ocaId + ';';
			}
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
			if(ids==''){
				j.messager.alert('������ʾ','��ѡ��Ҫɾ����key','warning');
				return false;
			}

			if (rows){
					j.messager.confirm('������ʾ','ȷ��Ҫɾ����ѡ�е���Ʒ��',function(r){
						if (r){
							j.post('oca_delAll.action?sj='+Math.random(),{ids:ids},function(result){
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
							j.post('oca_delZXSP.action?sj='+Math.random(),{keyId:keyId},function(result){
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
		
		function delSCSP(){
			var keyId = j('#keyId').val();
				j.messager.confirm('������ʾ','ȷ��Ҫ��ԭ��ȡ����ѡ��Ʒ��',function(r){
					if (r){
						j.post('oca_delSCSP.action?sj='+Math.random(),{keyId:keyId},function(result){
							if (result.success=="true"){
								j('#dg').datagrid('reload');	// reload the user data
							} else {
								j.messager.alert('����',result.msg);
							}
						},'json');
					}
				});
		}
		
		j(document).ready(function(){
			j.messager.defaults={ok:"ȷ��",cancel:"ȡ��"};
			j("#keyId").change(flagKey);
			j("#cxcq").linkbutton('disable');
			j("#qkzxsp").linkbutton('disable');
			try{
				j("#unsaveOK").linkbutton('disable');
			}catch(e){}
		});
		
		function saveOK(){
			var keyId = j('#keyId').val();
			if(keyId!=-1){
				j.messager.confirm('������ʾ','ȷ�ϵ�����Ϻ����и��Ĳ�����������ʹ�ã�ȷ�ϼ�����',function(r){
					if (r){
						j.ajax({
							type: 'GET',
							url: 'oca_saveOK.action?sj='+Math.random(),
							data: 'keyId='+keyId+'&isTZOK=1',
							success:function(msg){
								if(msg=="true"){
									j("#cxcq").linkbutton('disable');
									j("#qkzxsp").linkbutton('disable');
									j("#zxcsh").linkbutton('disable');
									j("#tzsp").linkbutton('disable');
									j("#scsp").linkbutton('disable');
									j("#saveOK").linkbutton('disable');
									try{
										j("#unsaveOK").linkbutton('enable');
									}catch(e){}
									j.messager.alert('��ʾ','ȷ�ϳɹ������ٴ������������ϵ����Ա��','warning');
								}else{
									j.messager.alert('������ʾ','��ȷ���Ƿ��Ѿ���ȡ��ѡ��Ʒ�Լ����˵���','warning');
								}
							}
						});
					}
				});
			}else{
				j.messager.alert('������ʾ','��ѡ��Ҫȷ�ϸ��ĵ�key','warning');
			}
		}
		
		function unsaveOK(){
			var keyId = j('#keyId').val();
			if(keyId!=-1){
				j.messager.confirm('������ʾ','ȷ��Ҫ��ѡ�е�keyȡ��ȷ�ϵ��������',function(r){
					if (r){
						j.ajax({
							type: 'GET',
							url: 'oca_saveOK.action?sj='+Math.random(),
							data: 'keyId='+keyId+'&isTZOK=0',
							success:function(msg){
								if(msg=="true"){
									j("#cxcq").linkbutton('enable');
									j("#qkzxsp").linkbutton('enable');
									j("#zxcsh").linkbutton('disable');
									j("#tzsp").linkbutton('enable');
									j("#scsp").linkbutton('enable');
									j("#saveOK").linkbutton('enable');
									try{
										j("#unsaveOK").linkbutton('disable');
									}catch(e){}
									
								}else{
									j.messager.alert('������ʾ','ȡ������������','warning');
								}
							}
						});
					}
				});
			}else{
				j.messager.alert('������ʾ','��ѡ��Ҫȷ�ϸ��ĵ�key','warning');
			}
		}
		
		function flagKey(){
			var keyId = j('#keyId').val();
			if(keyId!=-1){
				j.ajax({
					type: 'GET',
					url: 'oca_flag.action?sj='+Math.random(),
					data: 'keyId='+keyId,
					success:function(msg){
						var result = eval('('+msg+')');
						j("#cxcq").linkbutton('disable');
						if(result.isInfoCQ==1){
							j("#cxcq").linkbutton('enable');
							j("#qkzxsp").linkbutton('enable');
							j("#zxcsh").linkbutton('disable');
							j("#saveOK").linkbutton('enable');
							j("#tzsp").linkbutton('enable');
							j("#scsp").linkbutton('enable');
							try{
								j("#unsaveOK").linkbutton('disable');
							}catch(e){}
						}
						if(result.isTZOK==1){
							j("#cxcq").linkbutton('disable');
							j("#qkzxsp").linkbutton('disable');
							j("#zxcsh").linkbutton('disable');
							j("#tzsp").linkbutton('disable');
							j("#scsp").linkbutton('disable');
							j("#saveOK").linkbutton('disable');
							try{
								j("#unsaveOK").linkbutton('enable');
							}catch(e){}
						}
						if(result.isTZOK==0 && result.isInfoCQ==0){
							j("#cxcq").linkbutton('disable');
							j("#qkzxsp").linkbutton('disable');
							j("#zxcsh").linkbutton('enable');
							j("#tzsp").linkbutton('enable');
							j("#scsp").linkbutton('enable');
						}
					}
				});
			}
		}
		
		j(function () {
            j('#dg').datagrid({
                title: '��ѡ��Ʒ�б�',
                loadMsg: "���ݼ����У����Ժ󡭡�",
                striped: true,
                collapsible: true,
                url: 'oca_getListJson.action?sj='+Math.random(),
                height:600,
                remoteSort: false,
                idField: 'ocaId',
                frozenColumns: [[
                    { field: 'ocaId', checkbox: true }
                ]],
                columns: [[
                    { field: 'bcode', title: '��ı��', width: 100, align: 'center', sortable: true },
                    { field: 'btype', title: 'Ʒ��', width: 150, align: 'center', sortable: true },
                    { field: 'bstorage', title: '�������', width: 210, align: 'center', sortable: true },
                    { field: 'dealNum', title: '��������', width: 80, align: 'center', sortable: true },
                    { field: 'startPrice', title: '�𱨼�', width: 80, align: 'center', sortable: true },
                    { field: 'lowPrice', title: '�׼�', width: 80, align: 'center', sortable: true },
                    { field: 'delayTime', title: '�ӳ�ʱ��', width: 80, align: 'center', sortable: true },
                    { field: 'isLow', title: '�Ƿ񾺱�׼�', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '��';
                        	if(value=='0'){
                        		result = '��';
                        	}
                            return result;
                        } 
                    },
                    { field: 'addLowPrice', title: '�Ӽ��׼�', width: 80, align: 'center', sortable: true },
                    { field: 'keyId', title: 'key�ı�ʶ', hidden:'true'},
                    { field: 'orderNo', title: '�����', hidden:'true' },
                    { field: 'ocaDate', title: '��ȡ����', hidden:'true' }
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
			j('#dg').datagrid({url:"oca_getListJson.action?sj="+Math.random()+"&keyId="+keyId,method:"post"});
		}
		
		function openAddUnit(){
			j("#winDivIf").attr("src","oca_list.action?appTableNMId=${param.appTableNMId}&isTableNM=1&sj="+Math.random());
			j('#winDivId').window('open');
		}
		
		function closeWin(){
			j('#winDivId').window('close');
			j('#dg').datagrid('reload');
		}
		
		function closeChildren(){
			parent.closeWin();
		}
		
		function winopen(){
			
			window.open ('${ctx}/main.jsp','���̴������긨��','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
		}
		
	</script>
</head>
<body style="margin:0px;padding:0px">
<div id="toolbar">
	ѡ��Key��<s:select id="keyId" name="keyId" list="keyInfos" listKey="keyId" listValue="userName" headerKey="-1" headerValue="--��ѡ��--"></s:select>
	<a id="zxcsh" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="infiXZ()">��ѡ��Ʒ��ʼ��</a>
	<a id="tzsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="editUser()">������Ʒ</a>
	<a id="cxcq" plain="true"  href="javascript:void(0)" class="easyui-linkbutton" onclick="delZXSP()" iconCls="icon-cancel">���³�ȡ</a>
	<a id="qkzxsp" plain="true" href="javascript:void(0)" class="easyui-linkbutton" onclick="delSCSP()" iconCls="icon-cancel">��ԭ��ѡ��Ʒ</a>
	<a id="scsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeUser()">ɾ����Ʒ</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">��ѯ</a>
	<a id="saveOK" href="#" class="easyui-linkbutton" onclick="saveOK()" iconCls="icon-save">ȷ�ϵ������</a>
	<s:if test='#session.username=="lw"'>
	<a id="unsaveOK" href="#" class="easyui-linkbutton" onclick="unsaveOK()" iconCls="icon-reload">ȡ���������</a>
	</s:if>
	<a id="tzsp1" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onClick="winopen()">����ϵͳ</a>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">��ѡ��Ʒ</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="oca.ocaId"/>
		<input type="hidden" id="ocaDate" name="oca.ocaDate"/>
		<input type="hidden" id="orderNo" name="oca.orderNo"/>
		<input type="hidden" id="keyIdF" name="oca.keyInfo.keyId"/>
			<div class="fitem">
				<label>�ӳ�ʱ��:</label>
				<input id="delayTime" name="oca.delayTime" class="easyui-validatebox" required="true" missingMessage="�ӳ�ʱ�䲻��Ϊ�գ����ӳ�����0"/>
			</div>
			<div class="fitem">
				<label>�Ƿ񾺱�׼�:</label>
				<s:select id="isLow" name="oca.isLow" list="#{1:'��',0:'��'}" listKey="key" listValue="value"></s:select>
			</div>
			<div class="fitem">
				<label>�׼�:</label>
				<input id="lowPrice" readonly="readonly" name="oca.lowPrice" class="easyui-validatebox" required="true" missingMessage="�׼۲���Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�Ӽ��׼�:</label>
				<input id="addLowPrice" readonly="readonly" name="oca.addLowPrice" class="easyui-validatebox"/>
			</div>
			<div class="fitem">
				<label>��ı��:</label>
				<input id="bcode" name="oca.bcode" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>Ʒ��:</label>
				<input id="btype" name="oca.btype" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�������:</label>
				<input id="bstorage" name="oca.bstorage" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>��������:</label>
				<input id="dealNum" name="oca.dealNum" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
			</div>
			<div class="fitem">
				<label>�𱨼�:</label>
				<input id="startPrice" name="oca.startPrice" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="����Ϊ��"/>
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
