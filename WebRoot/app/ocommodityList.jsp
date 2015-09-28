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
		//自选商品初始化，从竞标系统中抽取
		function infiXZ(){
			var keyId = j('#keyId').val();
			if(keyId==-1){
				j.messager.alert('错误提示','请选择需要抽取的key','warning');
				return false;
			}
			
			var win = j.messager.progress({
				title:'请等待...',
				msg:'正在抽取自选商品数据...'
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
						  j.messager.alert('错误提示','抽取自选商品失败，请重新抽取','warning');
						}
					}
				});
		}
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('错误提示','请选择要调整的商品','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('错误提示','每次只能调整一个商品','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','调整商品信息');
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
			j.messager.defaults={ok:"确定",cancel:"取消"};
			if(ids==''){
				j.messager.alert('错误提示','请选择要删除的key','warning');
				return false;
			}

			if (rows){
					j.messager.confirm('警告提示','确定要删除所选中的商品吗？',function(r){
						if (r){
							j.post('oca_delAll.action?sj='+Math.random(),{ids:ids},function(result){
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
		
		function delZXSP(){
			var keyId = j('#keyId').val();
			j.messager.confirm('警告提示','重新抽取自选商品，将会把原抽取的自选商品清空，确定继续吗？',function(r2){
			  	if (r2){
					j.messager.confirm('警告提示','确定要重新抽取自选商品吗？',function(r){
						if (r){
							j.post('oca_delZXSP.action?sj='+Math.random(),{keyId:keyId},function(result){
								if (result.success=="true"){
									j('#dg').datagrid('reload');	// reload the user data
									j("#cxcq").linkbutton('disable');
									j("#qkzxsp").linkbutton('disable');
									j("#zxcsh").linkbutton('enable');
								} else {
									j.messager.alert('错误',result.msg);
								}
							},'json');
						}
					});
				}
			   });
		}
		
		function delSCSP(){
			var keyId = j('#keyId').val();
				j.messager.confirm('警告提示','确定要还原抽取的自选商品吗？',function(r){
					if (r){
						j.post('oca_delSCSP.action?sj='+Math.random(),{keyId:keyId},function(result){
							if (result.success=="true"){
								j('#dg').datagrid('reload');	// reload the user data
							} else {
								j.messager.alert('错误',result.msg);
							}
						},'json');
					}
				});
		}
		
		j(document).ready(function(){
			j.messager.defaults={ok:"确定",cancel:"取消"};
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
				j.messager.confirm('警告提示','确认调整完毕后所有更改操作均不能再使用，确认继续吗？',function(r){
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
									j.messager.alert('提示','确认成功，若再次想调整，请联系管理员！','warning');
								}else{
									j.messager.alert('错误提示','请确认是否已经抽取自选商品以及做了调整','warning');
								}
							}
						});
					}
				});
			}else{
				j.messager.alert('错误提示','请选择要确认更改的key','warning');
			}
		}
		
		function unsaveOK(){
			var keyId = j('#keyId').val();
			if(keyId!=-1){
				j.messager.confirm('警告提示','确认要将选中的key取消确认调整完毕吗？',function(r){
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
									j.messager.alert('错误提示','取消调整有问题','warning');
								}
							}
						});
					}
				});
			}else{
				j.messager.alert('错误提示','请选择要确认更改的key','warning');
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
                title: '自选商品列表',
                loadMsg: "数据加载中，请稍后……",
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
                    { field: 'bcode', title: '标的编号', width: 100, align: 'center', sortable: true },
                    { field: 'btype', title: '品种', width: 150, align: 'center', sortable: true },
                    { field: 'bstorage', title: '交货库点', width: 210, align: 'center', sortable: true },
                    { field: 'dealNum', title: '交易数量', width: 80, align: 'center', sortable: true },
                    { field: 'startPrice', title: '起报价', width: 80, align: 'center', sortable: true },
                    { field: 'lowPrice', title: '底价', width: 80, align: 'center', sortable: true },
                    { field: 'delayTime', title: '延迟时间', width: 80, align: 'center', sortable: true },
                    { field: 'isLow', title: '是否竞标底价', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '是';
                        	if(value=='0'){
                        		result = '否';
                        	}
                            return result;
                        } 
                    },
                    { field: 'addLowPrice', title: '加减底价', width: 80, align: 'center', sortable: true },
                    { field: 'keyId', title: 'key的标识', hidden:'true'},
                    { field: 'orderNo', title: '排序号', hidden:'true' },
                    { field: 'ocaDate', title: '抽取日期', hidden:'true' }
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
				j.messager.alert('错误提示','请选择要查询的key','warning');
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
			
			window.open ('${ctx}/main.jsp','华商储备竞标辅助','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
		}
		
	</script>
</head>
<body style="margin:0px;padding:0px">
<div id="toolbar">
	选择Key：<s:select id="keyId" name="keyId" list="keyInfos" listKey="keyId" listValue="userName" headerKey="-1" headerValue="--请选择--"></s:select>
	<a id="zxcsh" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onClick="infiXZ()">自选商品初始化</a>
	<a id="tzsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="editUser()">调整商品</a>
	<a id="cxcq" plain="true"  href="javascript:void(0)" class="easyui-linkbutton" onclick="delZXSP()" iconCls="icon-cancel">重新抽取</a>
	<a id="qkzxsp" plain="true" href="javascript:void(0)" class="easyui-linkbutton" onclick="delSCSP()" iconCls="icon-cancel">还原自选商品</a>
	<a id="scsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeUser()">删除商品</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">查询</a>
	<a id="saveOK" href="#" class="easyui-linkbutton" onclick="saveOK()" iconCls="icon-save">确认调整完毕</a>
	<s:if test='#session.username=="lw"'>
	<a id="unsaveOK" href="#" class="easyui-linkbutton" onclick="unsaveOK()" iconCls="icon-reload">取消调整完毕</a>
	</s:if>
	<a id="tzsp1" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onClick="winopen()">进入系统</a>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">自选商品</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="oca.ocaId"/>
		<input type="hidden" id="ocaDate" name="oca.ocaDate"/>
		<input type="hidden" id="orderNo" name="oca.orderNo"/>
		<input type="hidden" id="keyIdF" name="oca.keyInfo.keyId"/>
			<div class="fitem">
				<label>延迟时间:</label>
				<input id="delayTime" name="oca.delayTime" class="easyui-validatebox" required="true" missingMessage="延迟时间不能为空，无延迟请填0"/>
			</div>
			<div class="fitem">
				<label>是否竞标底价:</label>
				<s:select id="isLow" name="oca.isLow" list="#{1:'是',0:'否'}" listKey="key" listValue="value"></s:select>
			</div>
			<div class="fitem">
				<label>底价:</label>
				<input id="lowPrice" readonly="readonly" name="oca.lowPrice" class="easyui-validatebox" required="true" missingMessage="底价不能为空"/>
			</div>
			<div class="fitem">
				<label>加减底价:</label>
				<input id="addLowPrice" readonly="readonly" name="oca.addLowPrice" class="easyui-validatebox"/>
			</div>
			<div class="fitem">
				<label>标的编号:</label>
				<input id="bcode" name="oca.bcode" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
			</div>
			<div class="fitem">
				<label>品种:</label>
				<input id="btype" name="oca.btype" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
			</div>
			<div class="fitem">
				<label>交货库点:</label>
				<input id="bstorage" name="oca.bstorage" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
			</div>
			<div class="fitem">
				<label>交易数量:</label>
				<input id="dealNum" name="oca.dealNum" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
			</div>
			<div class="fitem">
				<label>起报价:</label>
				<input id="startPrice" name="oca.startPrice" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
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
