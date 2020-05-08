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
						<div id="oaWorkDailyTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/work/daily/datagrid" tableContentId="oaWorkDailyTable" searchGroupId="searchGroupId" fit="true" caption="我的日报" name="oaWorkDailyTableList" pageSize="20" sortName="createDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="title" label="标题" width="100" query="true"></t:dgCol>
		<t:dgCol name="status" label="日报状态" width="60" dictionary="dailystatus" query="true"></t:dgCol>
		<t:dgCol name="submitDate" label="提交时间" width="120" datefmt="yyyy-MM-dd HH:mm"></t:dgCol>
		<t:dgToolBar url="oa/work/daily/addorupdate" type="add" width="50%" height="80%"></t:dgToolBar>
		<t:dgToolBar url="oa/work/daily/addorupdate" type="view" width="50%" height="80%"></t:dgToolBar>
		<t:dgToolBar label="删除草稿" icon="glyphicon glyphicon-remove" type="define" funName="doDel"></t:dgToolBar>
		<t:dgToolBar label="提交" icon="glyphicon glyphicon-ok" type="define" funName="doSubmit"></t:dgToolBar>
	</t:datagrid>
</body>

<script type="text/javascript">

//提交日报
function doSubmit() {
	var rowId = $('#oaWorkDailyTableList').jqGrid('getGridParam','selrow');
	if(!rowId) {
		qhAlert('请先选择日报后再提交！');
		return;
	}
	
	qhConfirm("你确定要提交该日报草稿吗?", function(index) {
		//关闭询问
		parent.layer.close(index);
		
		//是
		$.post("oa/work/daily/doSubmit", {id : rowId}, function(d){
			if(d.success) {
				qhTipSuccess(d.msg);
				//操作结束，刷新表格
				reloadTable('oaWorkDailyTableList');
			} else{
				qhTipWarning(d.msg);
			}
		}); 
		
	}, function() {
		//否
	});
};

//删除日报草稿
function doDel() {
	var rowId = $('#oaWorkDailyTableList').jqGrid('getGridParam','selrow');
	if(!rowId) {
		qhAlert('请先选择草稿后再删除！');
		return;
	}
	
	qhConfirm("你确定要删除该日报草稿吗?", function(index) {
		//关闭询问
		parent.layer.close(index);
		
		$.post("oa/work/daily/delete", {id : rowId}, function(d){
			if(d.success) {
				qhTipSuccess(d.msg);
				//操作结束，刷新表格
				reloadTable('oaWorkDailyTableList');
			}else{
				qhTipWarning(d.msg);
			}
		}); 
		
	}, function() {
		//否
	});
	
	
	
}


</script>

</html>