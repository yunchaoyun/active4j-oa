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
						<div id="selectPlanTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/train/plan/datagrid" tableContentId="selectPlanTable" fit="true" caption="培训计划列表" name="selectPlanTableList" pageSize="20"  height="100%" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true"></t:dgCol>
		<t:dgCol name="name" label="计划名称" width="40" query="true"></t:dgCol>
		<t:dgCol name="cycle" label="计划周期" width="20" dictionary="plancycle" query="true"></t:dgCol>
		<t:dgCol name="startDate" label="开始日期" width="40" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="endDate" label="结束日期" width="40" datefmt="yyyy-MM-dd"></t:dgCol>
		<t:dgCol name="target" label="培训目标" width="60"></t:dgCol>
		<t:dgCol name="memo" label="培训说明" width="60"></t:dgCol>
	</t:datagrid>

</body>

<script type="text/javascript">

	function getValue() {
		var id = $("#selectPlanTableList").jqGrid('getGridParam', 'selrow');
		
		var rowData = $("#selectPlanTableList").jqGrid('getRowData',id);
		
		var node = new Object(); 
		node.id = id;
		node.text = rowData.name;
		
		return node;
	};

</script>

</html>