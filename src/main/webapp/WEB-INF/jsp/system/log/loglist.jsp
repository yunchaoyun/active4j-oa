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
						<div id="jqGrid_wrapper" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="sys/log/datagrid" tableContentId="jqGrid_wrapper" searchGroupId="searchGroupId" fit="true" caption="日志管理" name="table_list_2" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="userName" label="用户名" width="120" query="true"></t:dgCol>
		<t:dgCol name="name" label="操作名称" width="120"></t:dgCol>
		<t:dgCol name="type" label="日志类型" replace="登录_1,退出_2,保存_3,新增_4,删除_5,更新_6,正常_7,异常_8" query="true"></t:dgCol>
		<t:dgCol name="operatorTime" label="操作时间" width="180" datefmt="yyyy-MM-dd HH:mm:ss" query="true" queryModel="group" datePlugin="laydate"></t:dgCol>
		<t:dgCol name="broswer" label="浏览器类型"  width="100"></t:dgCol>
		<t:dgCol name="ip" label="IP地址" width="100"></t:dgCol>
		<t:dgCol name="className" label="操作类名" width="140"></t:dgCol>
		<t:dgCol name="methodName" label="方法名" width="120"></t:dgCol>
		<t:dgCol name="params" label="参数" width="260"></t:dgCol>
		<t:dgCol name="memo" label="备注" width="120"></t:dgCol>
		<t:dgToolBar></t:dgToolBar>
	</t:datagrid>
<script type="text/javascript">
		$(function(){
			laydate({elem:"#operatorTime_begin",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
			laydate({elem:"#operatorTime_end",event:"focus",istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
		});
</script>
</body>
</html>