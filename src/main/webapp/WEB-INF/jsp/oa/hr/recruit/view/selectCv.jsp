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
						<div id="selectCvTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/recruit/cv/datagrid" tableContentId="selectCvTable" fit="true" caption="简历列表" name="selectCvTableList" pageSize="20"  height="100%" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true"></t:dgCol>
		<t:dgCol name="name" label="姓名" width="30"></t:dgCol>
		<t:dgCol name="sex" label="性别" width="30" dictionary="sex"/>
		<t:dgCol name="age" label="年龄" width="20"></t:dgCol>
		<t:dgCol name="educationBack" label="学历" width="30" dictionary="degreeinfo" ></t:dgCol>
		<t:dgCol name="cvJob" label="职位" width="50"></t:dgCol>
		<t:dgCol name="telNo" label="联系电话" width="60"></t:dgCol>
		<t:dgCol name="email" label="电子邮箱" width="60"></t:dgCol>
		<t:dgCol name="status" label="状态" width="20" dictionary="cvstatus"></t:dgCol>
	</t:datagrid>

</body>

<script type="text/javascript">

	function getValue() {
		var id = $("#selectCvTableList").jqGrid('getGridParam', 'selrow');
		
		var rowData = $("#selectCvTableList").jqGrid('getRowData',id);
		
		var node = new Object(); 
		node.id = id;
		node.text = rowData.name;
		
		return node;
	};

</script>

</html>