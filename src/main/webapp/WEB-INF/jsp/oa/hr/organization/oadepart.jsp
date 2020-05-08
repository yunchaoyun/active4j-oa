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
		$("#departInfoForm").validate({
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
		$("#departInfoForm").submit();
	}
	
	//全部展开
	function doBtnExpandedAction() {
		$("#select-departs").treeview('expandAll', { silent: true });
	}
	
	//全部收起
	function doBtnUnExpandedAction() {
		$("#select-departs").treeview('collapseAll', { silent: true });
	}
	
	//左边树形结构刷新
	function doBtnRefreshTreeAction() {
		//请求后台数据，获取部门的树形结构
		$.post("common/getDepartTreeData", {}, function(d) {
			if(d.success) {
				var treeData = d.attributes.data;
				var df = eval(treeData);
				$("#select-departs").treeview({
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
		$("#name").val('');
		$("#deptNo").val('');
		$("#type").val('0');
		$("#parentId").val('');
		$("#departLabel").val('');
		$("#description").val('');
	}
	
	//部门的树形结构被选中方法
	function doBtnSelectionAction(id) {
		//赋值ID
		$("#nodeId").val(id);
		
		//请求后台数据，获取菜单的树形接口
		$.post("oa/hr/depart/getDepart", {id:id}, function(d) {
			if(d.success) {
				var depart = d.attributes.depart;
				var parentName = d.attributes.parentName;
				var parentId = d.attributes.parentId;
				
				$("#id").val(depart.id);
				$("#deptNo").val(depart.deptNo);
				$("#name").val(depart.name);
				$("#type").val(depart.type);
				$("#parentId").val(parentId);
				$("#departLabel").val(parentName);
				$("#description").val(depart.description);
				
			}
		});
	}
	
	//删除
	function doBtnDeleteAction() {
		var nodeId = $("#nodeId").val();
		if(null == nodeId || '' == nodeId) {
			qhTipWarning("请选择相关的部门信息!");
			return;
		}
		
		qhConfirm('你确定要删除该部门吗？', function(index) {
			// 关闭询问
			parent.layer.close(index);

			//请求后台数据，获取菜单的树形接口
			$.post("sys/dept/del", {id : nodeId}, function(d) {
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
						<h5>部门列表</h5>
					</div>
					<div>
						<div class="ibox-content">
							<p>
								<button class="btn btn-primary" type="button" onclick="doBtnNewAction();"><i class="fa fa-file-o"></i>&nbsp;新增</button>
								<button class="btn btn-primary" type="button" onclick="doBtnDeleteAction();"><i class="fa fa-remove"></i>&nbsp;删除</button>
	                        	<button class="btn btn-primary" type="button" onclick="doBtnExpandedAction();"><i class="fa fa-check-square-o"></i>&nbsp;展开</button>
	                        	<button class="btn btn-primary" type="button" onclick="doBtnUnExpandedAction();"><i class="fa fa-circle-o"></i>&nbsp;收起</button>
                      	    </p>
						<div id="select-departs"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>部门信息</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="departInfoForm" action="sys/dept/save" method="post">
							<input id="id" name="id" type="hidden" value="${depart.id }">
							<input type="hidden" name="nodeId" id="nodeId">
							<div class="form-group">
                                <label class="col-sm-3 control-label">部门编号 *</label>
                                <div class="col-sm-8">
                                    <input id="deptNo" name="deptNo" minlength="2" maxlength="20" type="text" class="form-control" required="" value="${depart.deptNo }">
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">部门名称 *</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2" maxlength="20" type="text" class="form-control" required="" value="${depart.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门类型 *</label>
                                <div class="col-sm-8">
                                   <select class="form-control m-b" name="type" id="type">
                                   		<c:forEach items="${types }" var="t">
                                   			<option value="${t.value }" <c:if test="${depart.type == t.value}">selected</c:if>>${t.label }</option>
                                   		</c:forEach>
                                   </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">上级部门</label>
                                <div class="col-sm-8">
                                     <div class="input-group">
                                     	<t:choose url="common/selectDepart" hiddenName="parentId" hiddenValue="${depart.parentId }" textValue="${parentDepartName }" textName="departLabel" hiddenId="parentId" textId="departLabel"></t:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门描述</label>
                                <div class="col-sm-8">
                                    <textarea id="description" name="description" class="form-control" rows="10">${depart.description }</textarea>
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
</html>