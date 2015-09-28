<%@page contentType="text/html; charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
	<script type="text/javascript">
		//因其他库使用了$，因此使用j代替
		var j = jQuery.noConflict(true);
		var ids = "";
		function addTab(title,content,id){
			if(ids.indexOf(id)!=-1){
				j('#tabsId').tabs('select',title);
			}else {
				j('#tabsId').tabs('add',{
					id:id,
					title:title,
					iconCls:'icon-save',
					closable:true,
					onOpen:function(){
						j('#tabsIf').attr('src',content);
					}
				});
				ids = ids + id + ";";
			}
			
		}
		
		j(function(){
			j('#tt2').datagrid({
				title:'My Title',
				iconCls:'icon-save',
				width:600,
				height:350,
				nowrap: false,
				striped: true,
				fit: true,
				url:'datagrid_data.json',
				sortName: 'code',
				sortOrder: 'desc',
				idField:'code',
				frozenColumns:[[
	                {field:'ck',checkbox:true},
	                {title:'code',field:'code',width:80,sortable:true}
				]],
				columns:[[
			        {title:'Base Information',colspan:3},
					{field:'opt',title:'Operation',width:100,align:'center', rowspan:2,
						formatter:function(value,rec){
							return '<span style="color:red">Edit Delete</span>';
						}
					}
				],[
					{field:'name',title:'Name',width:120},
					{field:'addr',title:'Address',width:120,rowspan:2,sortable:true},
					{field:'col4',title:'Col41',width:150,rowspan:2}
				]],
				pagination:true,
				rownumbers:true
			});
		});
		
		j(function(){
			j('#westTree').tree({
				checkbox: false,
				data: ${session.menuJson},
				onClick:function(node){
					j(this).tree('toggle', node.target);
					try{
						addTab(node.text,node.attributes.url,node.id)
					}catch(e){}
					
				}
			});
		});
		
		
		
	setTimeout('window.moveTo(0,0)',50);
	setTimeout('window.resizeTo(screen.availWidth,screen.availHeight)',50); 
		
	</script>
</head>
<body class="easyui-layout">
	<div region="west" iconCls="icon-reload" title="菜单" split="true" style="width:180px;">
		<ul id="westTree"></ul>
	</div>
	
	<div region="center" title="" style="overflow:hidden;">
		<div class="easyui-tabs" id="tabsId" fit="true" border="false">
		<iframe scrolling="auto" frameborder="0" id="tabsIf" style="width:100%;height:100%;"></iframe>
		</div>
	</div>
</body>
</html>
