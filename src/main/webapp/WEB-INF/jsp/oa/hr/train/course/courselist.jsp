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
			<div class="col-sm-4">
				<div class="row">
					<div class="col-sm-12" id="departGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="courseCateTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-8">
				<div class="row">
					<div class="col-sm-12" id="courseGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="courseTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
			<!-- 页面隐藏字段 -->
			<input type="hidden" id="cateId" name="cateId" value="">
		</div>
	</div>

	<!-- 脚本部门 -->
	<!-- 类别表格 -->
	<t:datagrid actionUrl="oa/hr/train/course/cate/datagrid"  tableContentId="courseCateTable" fit="true" caption="课程类别列表" name="table_course_cate_list" pageSize="20" sortName="createDate" sortOrder="desc" height="670px">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="课程类别" width="120"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="140"></t:dgCol>
		<t:dgFunOpt label="查看课程" funName="getCourseByCate(id)" icon="fa fa-check"></t:dgFunOpt>
	</t:datagrid>
	<!-- 课程表格 -->
	<t:datagrid actionUrl="oa/hr/train/course/datagrid" tableContentId="courseTable" searchGroupId="courseGroupId" fit="true" caption="课程列表" name="table_course_list" pageSize="20" sortName="createDate" sortOrder="desc" height="500px">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="attachment" label="附件" hidden="true" width="20"></t:dgCol>
		<t:dgCol name="name" label="课程名称" query="true" labelCol="2" inputCol="3"></t:dgCol>
		<t:dgCol name="cateName" label="课程类别" ></t:dgCol>
		<t:dgCol name="courseType" label="授课方式" dictionary="coursetype" query="true" labelCol="2" inputCol="3"></t:dgCol>
		<t:dgCol name="courseHour" label="课时" ></t:dgCol>
		<t:dgCol name="needMoney" label="费用" ></t:dgCol>
		<t:dgCol name="opt" label="操作"></t:dgCol>
		<t:dgDelOpt label="删除" url="oa/hr/train/course/del?id={id}"/> 
		<t:dgToolBar url="oa/hr/train/course/addorupdate" type="view" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/course/addorupdate" type="add" width="70%"></t:dgToolBar>
		<t:dgToolBar url="oa/hr/train/course/addorupdate" type="edit" width="70%"></t:dgToolBar>
		<t:dgToolBar label="附件下载" icon="glyphicon glyphicon-resize-full" type="define" funName="doAttachment"></t:dgToolBar>
	</t:datagrid>
	
	<script type="text/javascript">
		function getCourseByCate(id) {
			$("#cateId").val(id);
			
			//课程表的查询
			$("#table_course_list").jqGrid('setGridParam',{postData:{cateId : id}}).trigger('reloadGrid');
		}
		
		//下载附件
		function doAttachment() {
			var rowId = $('#table_course_list').jqGrid('getGridParam','selrow');
			var rowData = $('#table_course_list').jqGrid('getRowData',rowId);
			
			if(!rowId) {
				qhAlert('请选择课程后再下载附件！');
				return;
			}
			
			if(!rowData.attachment) {
				qhAlert('该课程还未上传附件！');
				return;
			}
			
			location.href = "func/upload/download?id=" + rowData.attachment;				
		};
	
		
	</script>
</body>
</html>
