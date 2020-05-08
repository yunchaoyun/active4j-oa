<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview"></t:base>
<script type="text/javascript">
	$(function() {
		
		//显示表格
		doBtnRefreshTreeAction();
		
		//表单提交
		$("#jobInfoForm").validate({
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function(o) {
						if (o.success) {
							qhTipSuccess('保存成功');
							doBtnRefreshTreeAction();
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
	
	//提交
	function submitAction() {
		$("#jobInfoForm").submit();
	}
	
	//全部展开
	function doBtnExpandedAction() {
		$("#select-jobs").treeview('expandAll', { silent: true });
	}
	
	//全部收起
	function doBtnUnExpandedAction() {
		$("#select-jobs").treeview('collapseAll', { silent: true });
	}
	
	//左边树形结构刷新
	function doBtnRefreshTreeAction() {
		//请求后台数据，获取岗位的树形结构
		$.post("common/getJobTreeData", {}, function(d) {
			if(d.success) {
				var treeData = d.attributes.data;
				var df = eval(treeData);
				$("#select-jobs").treeview({
					showCheckbox : false,
					data : df,
					//节点被选择
					onNodeSelected : function(event, node) {
						doBtnSelectionAction(node.id);
					}
				}); 
			}
		});
	}
	
	//新增
	function doBtnNewAction() {
		$("#id").val('');
		$("#jobNo").val('');
		$("#jobName").val('');
		$("#parentId").val('');
		$("#jobLabel").val('');
		$("#requireStr").val('');
		$("#description").val('');
	}
	
	//岗位的树形结构被选中方法
	function doBtnSelectionAction(id) {
		//赋值ID
		$("#nodeId").val(id);
		
		//请求后台数据，获取菜单的树形接口
		$.post("oa/hr/job/getJob", {id:id}, function(d) {
			if(d.success) {
				var job = d.attributes.job;
				var parentName = d.attributes.parentName;
				var parentId = d.attributes.parentId;
				
				$("#id").val(job.id);
				$("#jobNo").val(job.jobNo);
				$("#jobName").val(job.jobName);
				$("#parentId").val(parentId);
				$("#jobLabel").val(parentName);
				$("#requireStr").val(job.requireStr);
				$("#description").val(job.description);
				
			}
		});
	}
	
	//删除
	function doBtnDeleteAction() {
		var nodeId = $("#nodeId").val();
		
		if(null == nodeId || '' == nodeId) {
			qhTipWarning("请选择相关的岗位信息!");
			return;
		}
		
		qhConfirm('你确定要删除该岗位吗？', function(index) {
			// 关闭询问
			parent.layer.close(index);

			$.post("oa/hr/job/del", {id : nodeId}, function(data) {
				var d = $.parseJSON(data);
				if(d.success) {
					qhTipSuccess('删除成功');
					doBtnNewAction();
					doBtnRefreshTreeAction();
					$("#nodeId").val('');
				}else {
					qhTipWarning(d.msg);
				}
			});
		}, function() {

		});
		
	}
</script>
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
		<div class="row animated fadeInRight">
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>岗位列表</h5>
					</div>
					<div>
						<div class="ibox-content">
							<p>
								<button class="btn btn-primary" type="button" onclick="doBtnNewAction();"><i class="fa fa-file-o"></i>&nbsp;新增</button>
								<button class="btn btn-primary" type="button" onclick="doBtnDeleteAction();"><i class="fa fa-remove"></i>&nbsp;删除</button>
	                        	<button class="btn btn-primary" type="button" onclick="doBtnExpandedAction();"><i class="fa fa-check-square-o"></i>&nbsp;全部展开</button>
	                        	<button class="btn btn-primary" type="button" onclick="doBtnUnExpandedAction();"><i class="fa fa-circle-o"></i>&nbsp;全部收起</button>
                      	    </p>
						<div id="select-jobs"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>岗位信息</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="jobInfoForm" action="oa/hr/job/save" method="post">
							<input type="hidden" name="id" id="id" value="${job.id }">
							<input type="hidden" name="nodeId" id="nodeId">
							<div class="form-group">
								<label class="col-sm-3 control-label">岗位编号*：</label>
								<div class="col-sm-8">
									<input id="jobNo" name="jobNo" minlength="2" maxlength="20" type="text" class="form-control" required="" value="${job.jobNo }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">岗位名称*：</label>
								<div class="col-sm-8">
									<input id="jobName" name="jobName" minlength="2" maxlength="20" type="text" class="form-control" required="" value="${job.jobName }">
								</div>
							</div>
							 <div class="form-group">
                                <label class="col-sm-3 control-label">上级：</label>
                                <div class="col-sm-8">
                                     <div class="input-group">
                                     	<t:choose url="common/selectJob" hiddenName="parentId" hiddenValue="${job.parentId }" textValue="${parentJobName }" textName="jobLabel" hiddenId="parentId" textId="jobLabel"></t:choose>
                                    </div>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">入职要求：</label>
                                <div class="col-sm-8">
                                    <textarea id="requireStr" name="requireStr" class="form-control"  rows="10">${job.description }</textarea>
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">职责描述：</label>
                                <div class="col-sm-8">
                                    <textarea id="description" name="description" class="form-control" rows="10">${job.description }</textarea>
                                </div>
                            </div>
						</form>
						<div class="row">
							<div class="col-sm-11 text-right" style="margin-top: 30px;">
								<p>
									<button class="btn btn-info" onclick="submitAction();" type="submit">
										<i class="fa fa-check"></i>&nbsp;&nbsp;保存
									</button>
								</p>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>