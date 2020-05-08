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
					<div class="ibox-content">
						<div id="select-users"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#select-users").treeview({
			data : ${userTreeStr},
			showCheckbox : true,
			//节点被选中
			onNodeChecked: function(event, node) {
				//选中父节点展开
				if(node.state.checked){
					$("#select-users").treeview('expandNode', [ node.nodeId, {silent: true } ]);
				}
				
				//递归选中子节点
				checkChildrens("select-users", node);
				
				
			},
			//节点被取消选中
			onNodeUnchecked : function(event, node) {
				//递归取消选中子节点
				unCheckChildrens("select-users", node);
				//判断父节点的状态，需要先判断同级别节点是否有没有被选中，递归
				checkBrothers("select-users", node);
			},
		});
	});
	
	
	//子节点的递归选中
	function checkChildrens(id, node) {
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
	
	//子节点的递归选中
	function checkChildrens(id, node) {
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
	
	function getValue() {
		var nodes = $('#select-users').treeview('getChecked');
		
		if(nodes && nodes.length <= 0) {
			layer.alert("请选择相关人员");
			return;
		}
		
		var ids = '';
		var names = '';
		for(var i = 0; i < nodes.length; i++) {
			ids = ids + nodes[i].id + ",";
			names = names + nodes[i].text + ",";
		}
		
		ids = ids.substring(0, ids.length-1);
		names = names.substring(0, names.length-1);
		
		var node = new Object(); 
		node.id = ids;
		node.text = names;
		return node;
		//给父窗口赋值
		/* parent.$("#departLabel").val(node.text);
		parent.$("#departId").val(node.id); 
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭 */
		
	}
</script>
</html>

