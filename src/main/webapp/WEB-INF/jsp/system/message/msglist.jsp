<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,jqgrid"></t:base>
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
					<div class="ibox-content" style="height: 100% ">
						<div id="sysMsgTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="sys/message/datagrid" tableContentId="sysMsgTable" searchGroupId="searchGroupId" fit="true" caption="系统消息" name="sysMsgTableList" pageSize="20" sortName="sendTime" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="sender" label="发送人" width="90"></t:dgCol>
		<t:dgCol name="content" label="消息内容" width="400"></t:dgCol>
		<t:dgCol name="sendTime" label="发送时间" width="110"></t:dgCol>
		<t:dgCol name="status" label="状态" width="90" dictionary="sysmsgread" display="zeroOrOne"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="sys/message/del?delete&id={id}"/>
		<t:dgToolBar url="sys/message/addorupdate" type="view" width="60%" height="70%"></t:dgToolBar>
		<t:dgToolBar type="refresh"></t:dgToolBar>
	</t:datagrid>
</body>


</html>