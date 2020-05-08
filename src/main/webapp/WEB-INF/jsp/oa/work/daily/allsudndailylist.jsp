<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,laydate"></t:base>
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
						<div id="oaWorkAllSudnDailyTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oaWorkAllSudnDailyController.do?datagrid" tableContentId="oaWorkAllSudnDailyTable" searchGroupId="searchGroupId" fit="true" caption="我的日报" name="oaWorkSudnDailyTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="title" label="标题" width="100" query="true"></t:dgCol>
		<t:dgCol name="status" label="日报状态" width="60" dictionary="dailystatus" query="true"></t:dgCol>
		<t:dgCol name="submitDate" label="提交时间" width="120" datefmt="yyyy-MM-dd HH:mm"></t:dgCol>
		<t:dgCol name="submitName" label="提交人" width="70"></t:dgCol>
		<t:dgToolBar url="oaWorkAllSudnDailyController.do?addorupdate" type="view" width="50%" height="80%"></t:dgToolBar>
	</t:datagrid>
</body>

<script type="text/javascript">

</script>

</html>