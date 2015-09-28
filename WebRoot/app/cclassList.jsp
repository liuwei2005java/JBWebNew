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
				
		function editUser(){
			var row = j('#dg').datagrid('getSelected');
			var rows = j('#dg').datagrid('getSelections');
			if(rows.length==0){
			  j.messager.alert('错误提示','请选择要调整的商品分类','warning');
			  return false;
			}else if(rows.length>1){
			  j.messager.alert('错误提示','每次只能调整一个商品分类','warning');
			  return false;
			}
			if (row){
				j('#dlg').dialog('open').dialog('setTitle','调整商品分类信息');
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
				j.messager.alert('错误提示','请选择分类级别','warning');
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
			j.messager.defaults={ok:"确定",cancel:"取消"};
			if(ids==''){
				j.messager.alert('错误提示','请选择要删除的商品分类','warning');
				return false;
			}

			if (rows){
					j.messager.confirm('警告提示','删除商品分类将同时删除对应饿所有商品，确定要删除所选中的商品分类吗？',function(r){
						if (r){
							j.post('cclass_delAll.action?sj='+Math.random(),{ids:ids},function(result){
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
							j.post('cclass_delZXSP.action?sj='+Math.random(),{keyId:keyId},function(result){
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
		
		j(document).ready(function(){
			j.messager.defaults={ok:"确定",cancel:"取消"};
		});
		
		j(function () {
            j('#dg').datagrid({
                title: '自选商品分类列表',
                loadMsg: "数据加载中，请稍后……",
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
                    { field: 'bstorage', title: '交货库点', width: 210, align: 'center', sortable: true },
                    { field: 'btype', title: '品种', width: 150, align: 'center', sortable: true },
                    { field: 'startPrice', title: '起报价', width: 80, align: 'center', sortable: true },
                    { field: 'dealNum', title: '交易数量', width: 80, align: 'center', sortable: true },
                    { field: 'lowPrice', title: '底价', width: 80, align: 'center', sortable: true },
                    { field: 'addLowPrice', title: '加减底价', width: 80, align: 'center', sortable: true },
                    { field: 'jxnum', title: '竞选数量', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = value;
                        	if(value=='0'){
                        		result = '';
                        	}
                            return result;
                        }, sortable: true },
                    { field: 'classLevel', title: '分类级别', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = '';
                        	if(value==3){
                        		result = '首选';
                        	}else if(value==2){
                        		result = '二选';
                        	}else if(value==1){
                        		result = '三选';
                        	}
                            return result;
                        }},
                    { field: 'levelOrder', title: '级别排序', width: 80, align: 'center',
                        formatter: function (value, rec) {
                        	var result = value;
                        	if(value=='0'){
                        		result = '';
                        	}
                            return result;
                        }, sortable: true },
                    { field: 'keyId', title: 'key的标识', hidden:'true'},
                    { field: 'currentDate', title: '当前日期', hidden:'true' }
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
	选择Key：<s:select id="keyId" name="keyId" list="keyInfos" listKey="keyId" listValue="userName" headerKey="-1" headerValue="--请选择--"></s:select>
	<a id="tzsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onClick="editUser()">调整商品分类</a>
	<a id="scsp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onClick="removeUser()">删除商品分类</a>
	<!-- 
	<a id="qkzxsp" plain="true" href="javascript:void(0)" class="easyui-linkbutton" onclick="delSCSP()" iconCls="icon-cancel">还原商品分类</a>
	 -->
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onClick="query()">查询</a>
</div>
	<table id="dg" class="easyui-datagrid"></table>
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">自选商品分类</div>
		<form id="fm" method="post">
		<input type="hidden" id="id" name="commodityClassify.ccId"/>
		<input type="hidden" id="currentDate" name="commodityClassify.currentDate"/>
		<input type="hidden" id="keyIdF" name="commodityClassify.keyId"/>
		<input type="hidden" id="ccCode" name="commodityClassify.ccCode"/>
			<div class="fitem">
				<label>底价:</label>
				<input id="lowPrice" name="commodityClassify.lowPrice" class="easyui-validatebox" required="true" missingMessage="底价不能为空"/>
			</div>
			<div class="fitem">
				<label>加减底价:</label>
				<input id="addLowPrice" name="commodityClassify.addLowPrice" />
			</div>
			<div class="fitem">
				<label>分类级别:</label>
				<s:select id="classLevel" name="commodityClassify.classLevel" list="#{3:'首选',2:'二选',1:'三选'}" listKey="key" listValue="value" headerKey="-1" headerValue="--请选择--"></s:select>
			</div>
			<div class="fitem">
				<label>竞标数量:</label>
				<input id="jxnum" name="commodityClassify.jxnum" class="easyui-validatebox" required="true" missingMessage="竞标数量不能为空"/>
			</div>
			<div class="fitem">
				<label>级别排序:</label>
				<input id="levelOrder" name="commodityClassify.levelOrder" class="easyui-validatebox" required="true" missingMessage="级别顺序不能为空"/>
			</div>
			<div class="fitem">
				<label>交货库点:</label>
				<input id="bstorage" name="commodityClassify.bstorage" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
			</div>
			<div class="fitem">
				<label>品种:</label>
				<input id="btype" name="commodityClassify.btype" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
			</div>
			<div class="fitem">
				<label>起报价:</label>
				<input id="startPrice" name="commodityClassify.startPrice" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
			</div>
			<div class="fitem">
				<label>交易数量:</label>
				<input id="dealNum" name="commodityClassify.dealNum" readonly="readonly" class="easyui-validatebox" required="true" missingMessage="不能为空"/>
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
