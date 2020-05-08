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
						<div id="courseCateTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/train/course/cate/datagrid" tableContentId="courseCateTable" searchGroupId="searchGroupId" fit="true" caption="课程类别" name="courseCateTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="课程类别名称" width="60" query="true"></t:dgCol>
		<t:dgCol name="createDate" label="加入时间" width="70" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="100"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/train/course/cate/del?id={id}"/>
		<t:dgToolBar url="oa/hr/train/course/cate/addorupdate" type="view" width="50%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/course/cate/addorupdate" type="add" width="50%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/course/cate/addorupdate" type="edit" width="50%"></t:dgToolBar>
	</t:datagrid>

</body>

</html>