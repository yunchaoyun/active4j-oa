<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid"></t:base>
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
						<div id="recruitViewTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/recruit/view/datagrid" tableContentId="recruitViewTable" searchGroupId="searchGroupId" fit="true" caption="面试记录" name="recruitViewTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="cvName" label="姓名" width="40" query="true" queryId="cvName"></t:dgCol>
		<t:dgCol name="cvSex" label="性别" width="40" dictionary="sex"/>
		<t:dgCol name="cvJob" label="职位" width="40"></t:dgCol>
		<t:dgCol name="viewType" label="面试方式" width="40" dictionary="viewtype"/>
		<t:dgCol name="viewDate" label="面试时间" width="40" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="content" label="面试内容" width="60"></t:dgCol>
		<t:dgCol name="suggestion" label="意见" width="60"></t:dgCol>
		<t:dgCol name="staff" label="面试人" width="40"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="60"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/recruit/view/del?id={id}"/>
		<t:dgToolBar url="oa/hr/recruit/view/addorupdate" type="view" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/view/addorupdate" type="add" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/recruit/view/addorupdate" type="edit" width="70%"></t:dgToolBar>
	</t:datagrid>
</body>

</html>