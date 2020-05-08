<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,datetimePicker,laydate"></t:base>
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
					<div class="ibox-content">
						<div id="groupWaitTaskTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="flow/biz/task/datagridGroup" tableContentId="groupWaitTaskTable" searchGroupId="searchGroupId" fit="true" caption="承接审批" name="groupWaitTask" pageSize="20" sortName="applyDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="categoryId" label="流程类别" replace="${categoryReplace}" query="true" queryId="categoryId" width="90"></t:dgCol>
		<t:dgCol name="workFlowName" label="流程名称" width="70"></t:dgCol>
		<t:dgCol name="projectNo" label="编号" width="120" query="true"></t:dgCol>
		<t:dgCol name="name" label="标题名称" width="120" query="true"></t:dgCol>
		<t:dgCol name="status" label="状态" width="70" dictionary="actstatus" classes="text-navy"></t:dgCol>
		<t:dgCol name="level" label="紧急程度" width="70" dictionary="workflowlevel" display="level"></t:dgCol>
		<t:dgCol name="applyName" label="申请人" width="80" query="true"></t:dgCol>
		<t:dgCol name="applyDate" label="申请时间" width="120" query="true" datefmt="yyyy-MM-dd HH:mm:ss" queryModel="group" datePlugin="laydate"></t:dgCol>
		<t:dgToolBar label="承接" icon="fa fa-check-circle" type="define" funName="getApprove"></t:dgToolBar>
		<t:dgToolBar label="查看详情" icon="fa fa-eye" url="flow/biz/task/viewDetail" type="check" width="90%" height="90%"></t:dgToolBar>
		<t:dgToolBar label="查看流程图" icon="fa fa-binoculars" url="flow/biz/my/viewImage" type="read" width="80%" height="80%"></t:dgToolBar>
		<t:dgToolBar type="refresh" ></t:dgToolBar>
	</t:datagrid>
	
	<script type="text/javascript">
		$(function(){
			laydate({elem:"#applyDate_begin",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
			laydate({elem:"#applyDate_end",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
		});
		
		
		//承接审批任务
		function getApprove() {
			var rowId = $('#groupWaitTask').jqGrid('getGridParam','selrow');
			if(!rowId) {
				qhAlert('请选择要编辑的项目');
				return;
			}
			
			qhConfirm("你确定要承接该审批任务吗?", function(index) {
				//关闭询问
				parent.layer.close(index);
				
				//是
				$.post("flow/biz/task/getApproves", {id : rowId}, function(d){
					if(d.success) {
						qhTipSuccess(d.msg);
						//操作结束，刷新表格
						reloadTable('groupWaitTask');
					}
				}); 
				
			}, function() {
				//否
			});
		}
	</script>
</body>
</html>