<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview,step"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="wf/flow/mng/save" cssClass="wizard-big">
							<!-- 隐藏字段信息 -->
							<input type="hidden" name="id" id="id" value="${mng.id }">
							<h1>基本信息</h1>
							<fieldset>
								<div class="row">
									<div class="col-sm-8">
										<div class="form-group">
											<label>流程编号 *</label> <input id="workflowNo" name="workflowNo" type="text" class="form-control" required="" value="${mng.workflowNo }">
										</div>
										<div class="form-group">
											<label>流程名称 *</label> <input id="name" name="name" type="text" class="form-control" required="" value="${mng.name }">
										</div>
										<div class="form-group">
											<label>流程类别 *</label> <select name="categoryId" class="form-control" required="">
												<c:forEach items="${lstCatogorys }" var="c">
													<option value="${c.id }" <c:if test="${mng.categoryId == c.id }">selected="selected"</c:if>>${c.name }</option>
												</c:forEach>
											</select>
										</div>
										<div class="form-group">
											<label>备注</label>
											<textarea id="memo" name="memo" class="form-control">${mng.memo }</textarea>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="text-center">
											<div style="margin-top: 20px">
												<i class="fa fa-sign-in" style="font-size: 180px;color: #e5e5e5 "></i>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<h1>权限设置</h1>
							<fieldset>
								<div class="row">
									<div class="row">
										<div class="col-sm-1"></div>
										<div class="col-sm-8">
											<div class="form-group">
												<div class="radio">
													<c:if test="${not empty mng.id }">
														<label><input type="radio" id="isAll0" name="isAll" value="0" <c:if test="${mng.type == '0'}">checked="checked"</c:if> style="margin-top: 3px;"/>所有用户</label> 
														<label><input type="radio" id="isAll1" name="isAll" value="1" <c:if test="${mng.type == '1'}">checked="checked"</c:if> style="margin-top: 3px;"/>指定用户</label>
														<script type="text/javascript">
															$(function() {
																var c = ${mng.type};
																if('0' == c) {
																	$('#select-roles').treeview('disableAll', { silent: true });
																	$('#select-users').treeview('disableAll', { silent: true });
																}
															});
														</script>
														
													</c:if>
													<c:if test="${empty mng.id }">
														<label><input type="radio" id="isAll0" name="isAll" value="0" checked="checked" style="margin-top: 3px;"/>所有用户</label> 
														<label><input type="radio" id="isAll1" name="isAll" value="1" style="margin-top: 3px;"/>指定用户</label>
														<script type="text/javascript">
															$(function() {
																$('#select-roles').treeview('disableAll', { silent: true });
																$('#select-users').treeview('disableAll', { silent: true });
															})
														</script>
													
													</c:if>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="tabs-container">
											<div class="tabs-left">
												<ul class="nav nav-tabs">
													<li class="active"><a data-toggle="tab" href="#tab-1"> 按角色</a></li>
													<li class=""><a data-toggle="tab" href="#tab-2"> 按用户</a></li>
												</ul>
												<div class="tab-content">
													<div id="tab-1" class="tab-pane active">
														<div class="panel-body">
															<div id="select-roles"></div>
														</div>
													</div>
													<div id="tab-2" class="tab-pane">
														<div class="panel-body">
															<div id="select-users"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-8">
										<div class="panel panel-default">
											<div class="panel-heading">角色</div>
											<div class="panel-body">
												<div class="row" id="rolePanel"></div>
											</div>
										</div>
										<div class="panel panel-default">
											<div class="panel-heading">用户</div>
											<div class="panel-body">
												<div class="row" id="userPanel"></div>
											</div>

										</div>
									</div>
								</div>
							</fieldset>

							<h1>表单设置</h1>
							<fieldset>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label>选择表单类别</label> 
											<select id="formCategory" name="formCategory" class="form-control" onchange="doBtnChangeFormAction();">
												<c:forEach items="${lstForms }" var="f">
													<option value="${f.id }" <c:if test="${formC.id == f.id }">selected='selected'</c:if>>${f.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label>选择表单 *</label> 
											<select id="formId" name="formId" class="form-control" required="">
												<c:forEach items="${lstFs }" var="s">
													<option value="${s.id }" <c:if test="${form.id == s.id }">selected='selected'</c:if>>${s.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</fieldset>

							<h1>选择流程</h1>
							<fieldset>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label>选择流程 *</label> 
											<select id="processId" name="processId" class="form-control" required="" onchange="doBtnSelectProcessAction();">
													<option value="-1"></option>
												<c:forEach items="${lstProcess }" var="c">
													<option value="${c.id}##${c.key}##${c.deploymentId}" <c:if test="${pros == c.id}">selected='selected'</c:if>>${c.name }</option>
												</c:forEach>
											</select>
											<!-- 隐藏字段 -->
											
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="ibox float-e-margins">
											<div class="ibox-content">
												<img id="flowImg" alt="" src="wf/flow/deploy/image?id=${firstId }">
						                    </div>
										</div>
									</div>
								</div>
							</fieldset>
						</t:formvalid>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- 脚本部分 -->
<script type="text/javascript">
	$(function() {
		//角色树形结构
		$("#select-roles").treeview({
			showCheckbox : true,
			data : ${roleTreeStr},
			//节点被选中
			onNodeChecked : function(event, node) {
				//选中父节点展开
				if (node.state.checked) {
					$("#select-roles").treeview('expandNode', [ node.nodeId, {
						silent : true
					} ]);
				}

				//递归选中子节点
				checkChildrens("select-roles", node);

				var parent = $('#select-roles').treeview('getParent', node);
				//循环遍历
				while (parent) {
					$("#select-roles").treeview('checkNode', [ parent, {
						silent : true
					} ]);
					parent = $('#select-roles').treeview('getParent', parent);
				}
			},
			//节点被取消选中
			onNodeUnchecked : function(event, node) {
				//递归取消选中子节点
				unCheckChildrens("select-roles", node);
				//判断父节点的状态，需要先判断同级别节点是否有没有被选中，递归
				checkBrothers("select-roles", node);
			},
			//节点被选择
			onNodeSelected : function(event, node) {

			}
		});

		//用户树形结构
		$("#select-users").treeview({
			showCheckbox : true,
			data : ${userTreeStr},
			//节点被选中
			onNodeChecked : function(event, node) {
				//选中父节点展开
				if (node.state.checked) {
					$("#select-users").treeview('expandNode', [ node.nodeId, {
						silent : true
					} ]);
				}

				//递归选中子节点
				checkUserChildrens("select-users", node);

				var parent = $('#select-users').treeview('getParent', node);
				//循环遍历
				while (parent) {
					$("#select-users").treeview('checkNode', [ parent, {
						silent : true
					} ]);
					parent = $('#select-users').treeview('getParent', parent);
				}
			},
			//节点被取消选中
			onNodeUnchecked : function(event, node) {
				//递归取消选中子节点
				unUserCheckChildrens("select-users", node);
				//判断父节点的状态，需要先判断同级别节点是否有没有被选中，递归
				checkUserBrothers("select-users", node);
			},
			//节点被选择
			onNodeSelected : function(event, node) {

			}
		});

		
		$("input[name='isAll']").click(function() {
			if(this.value == '0') {
				$('#select-roles').treeview('disableAll', { silent: true });
				$('#select-users').treeview('disableAll', { silent: true });
				$("#rolePanel").empty();
				$("#userPanel").empty();
			}else if(this.value == '1'){
				$('#select-roles').treeview('enableAll', { silent: true });
				$('#select-users').treeview('enableAll', { silent: true });
			}
		});
		
		
		//右边面板部分赋值
		//角色赋值
		var nodes = $('#select-roles').treeview('getEnabled', { silent: true });
		for(var i=0; i < nodes.length;i++) {
			var n = nodes[i];
			if (n.state.checked) {
				doBtnSelectAction(n);
			}
		}
		//用户赋值
		var nodes2 = $('#select-users').treeview('getEnabled', { silent: true });
		for(var i=0; i < nodes2.length;i++) {
			var n = nodes2[i];
			if (n.state.checked) {
				doBtnUserSelectAction(n);
			}
		}
		
		doBtnChangeFormAction();
	});

	//子节点的递归选中
	function checkChildrens(id, node) {
		//选中的节点事件
		doBtnSelectAction(node);
		//获得子节点
		var childrens = node.nodes;
		if (childrens) {
			//如果存在子节点，则全部勾选上
			for (var i = 0; i < childrens.length; i++) {
				var childNode = childrens[i];
				checkChildrens(id, childNode);
				$("#" + id).treeview('checkNode', [ childNode.nodeId, {
					silent : true
				} ]);
			}
		}
	}

	//子节点被取消选中
	function unCheckChildrens(id, node) {
		//取消节点
		doBtnUnSelectAction(node);
		//获得子节点
		var childrens = node.nodes;
		if (childrens) {
			//如果存在子节点，则全部勾选上
			for (var i = 0; i < childrens.length; i++) {
				var childNode = childrens[i];
				unCheckChildrens(id, childNode);
				$("#" + id).treeview('uncheckNode', [ childNode.nodeId, {
					silent : true
				} ]);
			}
		}
	}

	//根据兄弟节点的选中状态，判断父节点的选中状态，递归调用方法
	function checkBrothers(id, node) {
		var brothers = $('#' + id).treeview('getSiblings', node);
		if (brothers) {
			var isChecked = false;
			//如果存在兄弟节点
			for (var i = 0; i < brothers.length; i++) {
				var brother = brothers[i];
				if (brother.state.checked) {
					isChecked = true;
					break;
				}
				;
			}
			if (!isChecked) {
				var parent = $('#' + id).treeview('getParent', node);
				$("#" + id).treeview('uncheckNode', [ parent, {
					silent : true
				} ]);
				checkBrothers(id, parent);
			}
			;
		}
		;
	}

	//子节点的递归选中
	function checkUserChildrens(id, node) {
		//选中的节点事件
		doBtnUserSelectAction(node);
		//获得子节点
		var childrens = node.nodes;
		if (childrens) {
			//如果存在子节点，则全部勾选上
			for (var i = 0; i < childrens.length; i++) {
				var childNode = childrens[i];
				checkUserChildrens(id, childNode);
				$("#" + id).treeview('checkNode', [ childNode.nodeId, {
					silent : true
				} ]);
			}
		}
	}

	//子节点被取消选中
	function unUserCheckChildrens(id, node) {
		//取消节点
		doBtnUserUnSelectAction(node);
		//获得子节点
		var childrens = node.nodes;
		if (childrens) {
			//如果存在子节点，则全部勾选上
			for (var i = 0; i < childrens.length; i++) {
				var childNode = childrens[i];
				unUserCheckChildrens(id, childNode);
				$("#" + id).treeview('uncheckNode', [ childNode.nodeId, {
					silent : true
				} ]);
			}
		}
	}

	//根据兄弟节点的选中状态，判断父节点的选中状态，递归调用方法
	function checkUserBrothers(id, node) {
		var brothers = $('#' + id).treeview('getSiblings', node);
		if (brothers) {
			var isChecked = false;
			//如果存在兄弟节点
			for (var i = 0; i < brothers.length; i++) {
				var brother = brothers[i];
				if (brother.state.checked) {
					isChecked = true;
					break;
				}
				;
			}
			if (!isChecked) {
				var parent = $('#' + id).treeview('getParent', node);
				$("#" + id).treeview('uncheckNode', [ parent, {
					silent : true
				} ]);
				checkUserBrothers(id, parent);
			}
			;
		}
		;
	}

	//选中事件，在角色面板新增角色信息
	function doBtnSelectAction(node) {
		//是角色类型才进行添加
		if ('R' == node.type) {
			var html = "<div class='col-sm-4' id='" + node.id + "'><input type='hidden' name='RList' value='" + node.id + "'><div class='contact-box2'><div class='col-sm-4'><div class='text-center'><img alt='image' class='img-circle m-t-xs img-responsive' src='static/index/img/user.png'></div></div>";
			html += "<div class='col-sm-8'><h3><strong>"
					+ node.text
					+ "</strong></h3><strong>角色</strong></div><div class='clearfix'></div></div></div>";
			$("#rolePanel").append(html);
		}
	}

	//选中事件，在用户面板新增用户信息
	function doBtnUserSelectAction(node) {
		//是角色类型才进行添加
		if ('U' == node.type) {
			var html = "<div class='col-sm-4' id='" + node.id + "'><input type='hidden' name='UList' value='" + node.id + "'><div class='contact-box2'><div class='col-sm-4'><div class='text-center'><img alt='image' class='img-circle m-t-xs img-responsive' src='static/index/img/user.png'></div></div>";
			html += "<div class='col-sm-8'><h3><strong>"
					+ node.text
					+ "</strong></h3><strong>用户</strong></div><div class='clearfix'></div></div></div>";
			$("#userPanel").append(html);
		}
	}

	//取消选中事件，在角色面板上去除
	function doBtnUnSelectAction(node) {
		//是角色类型才进行删除
		if ('R' == node.type) {
			$("#" + node.id).remove();
		}
	}

	//取消选中事件，在用户面板上去除
	function doBtnUserUnSelectAction(node) {
		//是角色类型才进行删除
		if ('U' == node.type) {
			$("#" + node.id).remove();
		}
	}
	
	//流程选择，流程图变动
	function doBtnSelectProcessAction() {
		var id = $("#processId").val();
		$("#flowImg").attr("src","wf/flow/deploy/image?id=" + id);
	}
	
	//表单类别选择
	function doBtnChangeFormAction() {
		var formCategoryId = $("#formCategory").val();
		
		//查询该类别下的表单
		$.post("wf/flow/mng/getForms", {id : formCategoryId}, function(d){
			if(d.success) {
				var forms = d.attributes.forms;
				var sb = "";
				for(var i = 0; i < forms.length; i++) {
					var f = forms[i];
					sb += "<option value='" + f.id + "'>" + f.name + "</option>";
				}
				$("#formId").empty();
				$("#formId").append(sb);
			}
		}); 
	}
</script>



</html>

