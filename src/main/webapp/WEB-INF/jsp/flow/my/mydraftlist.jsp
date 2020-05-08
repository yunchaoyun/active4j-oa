<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,datetimePicker,laydate"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight" dad="${categoryReplace }">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12" id="searchGroupId">
					</div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="mydraftTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="flow/biz/my/datagrid" tableContentId="mydraftTable" searchGroupId="searchGroupId" fit="true" caption="我的草稿" name="mydraftTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="categoryId" label="流程类别" replace="${categoryReplace}" query="true" queryId="categoryId" width="90"></t:dgCol>
		<t:dgCol name="workFlowName" label="流程名称" width="180"></t:dgCol>
		<t:dgCol name="projectNo" label="编号" width="120" query="true"></t:dgCol>
		<t:dgCol name="name" label="标题名称" width="220" query="true"></t:dgCol>
		<t:dgCol name="status" label="状态" width="60" dictionary="actstatus" classes="text-navy"></t:dgCol>
		<t:dgCol name="level" label="紧急程度" width="70" dictionary="workflowlevel" display="level"></t:dgCol>
		<t:dgCol name="createName" label="创建人" width="70"></t:dgCol>
		<t:dgCol name="createDate" label="创建时间" width="140" query="true" datefmt="yyyy-MM-dd HH:mm:ss" queryModel="group" datePlugin="laydate"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="290"></t:dgCol>
		<t:dgFunOpt label="删除" funName="doDelAction(id)" icon="fa fa-remove"></t:dgFunOpt>
		<t:dgToolBar url="flow/biz/my/addorupdate" type="editNo" width="90%" height="90%"></t:dgToolBar>
		<t:dgToolBar url="flow/biz/my/addorupdate" type="view" width="90%" height="90%"></t:dgToolBar>
		<t:dgToolBar type="refresh" ></t:dgToolBar>
	</t:datagrid>
	
	
	
</body>

<script type="text/javascript">
		$(function(){
			laydate({elem:"#createDate_begin",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
			laydate({elem:"#createDate_end",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
		});
		
		
		
		function doDelAction(id) {
			$.post("flow/biz/my/del", {id:id}, function(d) {
				if(d.success) {
					var url = d.obj;
					//再次请求删除
					$.post(url, {}, function(o) {
						if(o.success) {
							qhTipSuccess(o.msg);
						}else {
							qhWarning(o.msg);
						}
					})
				}else {
					qhWarning(o.msg);
				}
				//操作结束，刷新表格
				reloadTable('mydraftTableList');
			})
		}
</script>

</html>