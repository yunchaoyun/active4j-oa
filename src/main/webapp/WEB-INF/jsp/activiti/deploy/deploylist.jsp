<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,prettyfile"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>部署流程</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">
								<form class="form-horizontal m-t" id="commonForm" method="post" enctype="multipart/form-data" action="wf/flow/deploy/deploy">
									<div class="form-group">
										<label class="col-sm-1 control-label">流程名称：</label>
										<div class="col-sm-4">
											<input id="name" name="name" type="text" class="form-control" required="">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-1 control-label">流程文件：</label>
										<div class="col-sm-4">
											<input name="file" type="file" required="" class="form-control">
										</div>
										<div class="col-sm-1 col-sm-offset-1">
											<button class="btn btn-primary" type="submit">提交</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12" id="searchGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="workflowDeployTableId" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部分 -->
	<t:datagrid actionUrl="wf/flow/deploy/datagrid" tableContentId="workflowDeployTableId"  fit="true" caption="流程部署列表" name="workflowDeployList" pageSize="20">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="id" label="部署ID"  width="180"></t:dgCol>
		<t:dgCol name="name" label="流程名称"  width="130"></t:dgCol>
		<t:dgCol name="deploymentTime" label="部署时间" datefmt="yyyy-MM-dd HH:mm"  width="120"></t:dgCol>
		<t:dgCol name="opt" label="操作" ></t:dgCol>
		<t:dgDelOpt label="删除" url="wf/flow/deploy/delete?id={id}"/>
	</t:datagrid>


	<script type="text/javascript">
		$(function() {
			$("#commonForm").validate({
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						success : function(o) {
							if (o.success) {
								qhTipSuccess('部署成功');
								reloadTable("workflowDeployList");
							} else {
								qhTipWarning(o.msg);
							}
						},
						error : function(data) {
							qhTipError('系统错误，请联系系统管理员');
						}
					});
				}
			});
		});
	</script>
</body>
</html>