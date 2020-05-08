<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,treeview,icheck"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12" id="menuGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="dicTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部门 -->
	<t:datagrid actionUrl="sys/dic/treeDataGrid" treeGrid="true" treeColomnName="dicName" tableContentId="dicTable" fit="true" caption="字典管理" name="table_dic_list" pageSize="2000" >
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="dicName" label="字典名称"></t:dgCol>
		<t:dgCol name="dicCode" label="字典编码"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="140"></t:dgCol>
		<t:dgDelOpt label="删除" url="sys/dic/del?id={id}" operationCode="sys:dic:del"/>
		<t:dgToolBar label="新增字典值" icon="fa fa-file-o" type="define" funName="addval" operationCode="sys:dic:addval"></t:dgToolBar>
		<t:dgToolBar url="sys/dic/add" type="add" width="60%" operationCode="sys:dic:add"></t:dgToolBar>
		<t:dgToolBar url="sys/dic/add" type="edit" width="60%" operationCode="sys:dic:edit"></t:dgToolBar>
	</t:datagrid>
	
	<script type="text/javascript">
		//新增字典值
		function addval() {
			var rowId = $('#table_dic_list').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要添加的字典项');
				return;
			}
			
			var url = "sys/dic/addval?id=" + rowId;
			parent.layer.open({
			    type: 2,
			    title: '新增字典值',
			    shadeClose: true,
			    shade: 0.8,
			    area: ['60%', '80%'],
			    content: url, //iframe的url
			    btn : [ '确定', '取消' ],
				yes : function(index, layero) {
					//确定按钮回调
					//表单提交
					parent.frames['layui-layer-iframe' + index].submitL();
					
				},
				btn2 : function(index, layero) {
					//取消按钮回调
					
				},
				end: function() {
					// 操作结束，刷新表格
					$("#table_dic_list").trigger("reloadGrid");
				}
			});
		}
	
	</script>
	
</body>
</html>
