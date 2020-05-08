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
						<div id="selectNeedTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/recruit/need/datagrid" tableContentId="selectNeedTable" multiSelect="true" fit="true" caption="需求列表" name="selectNeedTableList" pageSize="20"  height="100%" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true"></t:dgCol>
		<t:dgCol name="needJob" label="需求职位" ></t:dgCol>
		<t:dgCol name="departName" label="需求部门" ></t:dgCol>
		<t:dgCol name="needNum" label="需求人数" ></t:dgCol>
		<t:dgCol name="preDate" label="预计用工时间" datefmt="yyyy-MM-dd"></t:dgCol>
	</t:datagrid>

</body>

<script type="text/javascript">

	function getValue() {
		//多选拼接
		var rowIds = $("#selectNeedTableList").jqGrid('getGridParam', 'selarrrow');
		
		var ids = '';
		var names = '';
		
		if(rowIds.length > 0) {
			
			for ( var i = 0; i < rowIds.length; i++) {
				//得到当前行数据
				var rowData = $("#selectNeedTableList").jqGrid('getRowData',rowIds[i]);
				ids = ids + rowIds[i] + ",";
				names = names + rowData.needJob + ",";
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