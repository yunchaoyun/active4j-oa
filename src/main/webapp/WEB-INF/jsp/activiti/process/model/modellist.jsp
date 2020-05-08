<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,jqgrid"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12" id="searchGroupId">
					</div>
				</div>
				<div class="ibox">
					<div class="ibox-content" style="height: 100% ">
						<div id="modellistTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="activiti/process/model/datagrid" tableContentId="modellistTable" searchGroupId="searchGroupId" fit="true" caption="流程模型" name="modellistTableList" pageSize="20" sortName="sort" sortOrder="asc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="key" label="流程key" width="90" query="true"></t:dgCol>
		<t:dgCol name="name" label="名称" width="60" query="true"></t:dgCol>
		<t:dgCol name="version" label="版本" width="60"></t:dgCol>
		<t:dgCol name="createTime" label="创建时间" width="160" datefmt="yyyy-MM-dd HH:mm:ss"></t:dgCol>
		<t:dgCol name="lastUpdateTime" label="最后更新时间" width="160" datefmt="yyyy-MM-dd HH:mm:ss"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="wf/flow/category/delete?id={id}"/>
		<t:dgFunOpt label="设计" funName="doDesignAction(id)" icon="fa fa-binoculars"></t:dgFunOpt>
		<t:dgFunOpt label="部署" funName="doDeployAction(id)" icon="fa fa-binoculars"></t:dgFunOpt>
		<t:dgToolBar url="activiti/process/model/addorupdate" type="add" width="40%" height="70%"></t:dgToolBar>
	</t:datagrid>
</body>

<script type="text/javascript">

	function doDesignAction(rowId) {
		var url = 'modeler.html?modelId=' + rowId;
		parent.layer.open({
			type : 2,
			title : '编辑',
			shadeClose : true,
			shade : 0.8,
			area : [ '100%', '100%' ],
			content : url, // iframe的url
			btn : ['取消' ],
			btn2 : function(index, layero) {
				//取消按钮回调
				
			},
			end: function() {
				// 操作结束，刷新表格
				reloadTable(id);
			}
		});
	}
	
	function doDeployAction(rowId) {
		qhConfirm("你确定要部署该流程吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("activiti/process/model/deploy/" + rowId, {}, function(data){
				if(data.success) {
					qhTipSuccess(data.msg);
					//操作结束，刷新表格
					reloadTable('modellistTableList');
				}else {
					qhTipWarning(data.msg);
				}
			}); 
			
		}, function() {
			//否
		});
	}
</script>

</html>