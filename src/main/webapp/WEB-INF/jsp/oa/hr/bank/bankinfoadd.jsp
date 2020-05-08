<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="row">
						<div class="col-sm-4">
							<div class="panel panel-default">
								<div class="panel-heading">人员选择</div>
								<div class="panel-body">
									<div id="select-users"></div>
								</div>
							</div>
						</div>
						<div class="col-sm-8">
							<t:formvalid action="oa/hr/bankinfo/saveAdd">
								<div class="panel panel-default">
									<div class="panel-heading">已选人员</div>
									<div class="panel-body" id="userPanel"></div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">填写数据</div>
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-2 control-label m-b">开户行：</label>
											<div class="col-sm-4 m-b">
												<input class="form-control" type="text" id="bank" name="bank" required="">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label m-b">卡号：</label>
											<div class="col-sm-4 m-b">
												<input class="form-control" type="digits" id="cardNo" name="cardNo" required="">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">备注：</label>
											<div class="col-sm-6">
												<textarea rows="6" id="memo" name="memo" class="form-control"></textarea>
											</div>
										</div>
									</div>
								</div>
							</t:formvalid>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {

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
				if(null != parent.state) {
					//循环遍历
					while (parent) {
						$("#select-users").treeview('checkNode', [ parent, {
							silent : true
						} ]);
						parent = $('#select-users').treeview('getParent', parent);
						if(null == parent.state) {
							return;
						}
					}
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
	});

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
				if(null != parent.state) {
					$("#" + id).treeview('uncheckNode', [ parent, {
						silent : true
					} ]);
					checkUserBrothers(id, parent);
				}
			}
			;
		}
		;
	}

	//选中事件，在用户面板新增用户信息
	function doBtnUserSelectAction(node) {
		var html = "<div class='col-sm-3' id='" + node.id + "'><input type='hidden' name='UList' value='" + node.id + "'><div class=''>";
		html += "<div class='col-sm-12'><h3><strong>"
				+ node.text
				+ "</strong></h3></div><div class='clearfix'></div></div></div>";
		$("#userPanel").append(html);
	}

	//取消选中事件，在用户面板上去除
	function doBtnUserUnSelectAction(node) {
		$("#" + node.id).remove();
	}
</script>
</html>

