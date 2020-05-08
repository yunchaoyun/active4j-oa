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
				<div class="ibox">
					<div class="ibox-content">
						<div id="selectCourseTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/train/course/datagrid" tableContentId="selectCourseTable" multiSelect="true" fit="true" caption="课程列表" name="selectCourseTableList" pageSize="20"  height="100%" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true"></t:dgCol>
		<t:dgCol name="name" label="课程名称" query="true"></t:dgCol>
		<t:dgCol name="cateName" label="课程类别" ></t:dgCol>
		<t:dgCol name="courseType" label="授课方式" dictionary="coursetype" query="true"></t:dgCol>
		<t:dgCol name="courseHour" label="课时" ></t:dgCol>
		<t:dgCol name="needMoney" label="费用" ></t:dgCol>
	</t:datagrid>

</body>

<script type="text/javascript">

	function getValue() {
		//多选拼接
		var rowIds = $("#selectCourseTableList").jqGrid('getGridParam', 'selarrrow');
		
		var ids = '';
		var names = '';
		
		if(rowIds.length > 0) {
			
			for ( var i = 0; i < rowIds.length; i++) {
				//得到当前行数据
				var rowData = $("#selectCourseTableList").jqGrid('getRowData',rowIds[i]);
				ids = ids + rowIds[i] + ",";
				names = names + rowData.name + ",";
			}
			
			ids = ids.substring(0, ids.length-1);
			names = names.substring(0, names.length-1);
			
			var node = new Object(); 
			node.id = ids;
			node.text = names;
			return node;
		}
	};

</script>

</html>