<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,peity"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-3">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>任务状态</h5>
							</div>
							<div class="ibox-content">
								<div id='external-events'>
									<c:forEach items="${lstData }" var="s">
										<div class='external-event navy-bg' onclick="searchAction('${s.value}')">${s.name }<span style="float:right">${s.num }</span></div>
									</c:forEach>
										<div class='external-event navy-bg' onclick="searchAction('')">全部<span style="float:right">${totalNum }</span></div>
		                        </div>
							</div>
						</div>
					</div>
					<div class="col-sm-9">
						<div class="row">
							<div class="col-sm-12">
								<div class="row">
									<div class="col-sm-12" id="searchGroupId">
									</div>
								</div>
								<div class="ibox">
									<div class="ibox-content">
										<div id="oaWorkMyCreateTaskListTable" class="jqGrid_wrapper"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/work/task/mycreatetaskdatagrid" tableContentId="oaWorkMyCreateTaskListTable" searchGroupId="searchGroupId" fit="true" caption="我创建的任务列表" name="oaWorkMyCreateTaskList" pageSize="20" sortName="createDate" sortOrder="desc" load="true" loadFun="init();">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="title" label="任务标题" width="120" query="true" inputCol="4"></t:dgCol>
		<t:dgCol name="appointUserName" label="任务分配人" width="70"></t:dgCol>
		<t:dgCol name="userName" label="任务责任人" width="70"></t:dgCol>
		<t:dgCol name="monitorUserName" label="任务监控人" width="70"></t:dgCol>
		<t:dgCol name="status" label="状态" width="60" dictionary="oataskstatus" display="status"></t:dgCol>
		<t:dgCol name="progress" label="进度" width="60" display="percent"></t:dgCol>
		<t:dgToolBar url="oa/work/task/addorupdate" type="add" width="60%" height="90%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/task/addorupdate" type="edit" width="60%" height="90%"></t:dgToolBar>
		<t:dgToolBar label="查看执行" icon="fa fa-cog" url="oa/work/task/view" type="read" width="80%" height="90%"></t:dgToolBar>
	</t:datagrid>

</body>
<script type="text/javascript">
	function init(){
		$.getScript("static/peity/js/init.js");
	}
	
	function searchAction(status) {
		var name = $("#title").val();
		$("#oaWorkMyCreateTaskList").jqGrid('setGridParam', {postData:{status:status,title:name}}).trigger('reloadGrid');
		
	}

</script>

</html>