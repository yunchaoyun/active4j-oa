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
						<div id="select-jobs"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#select-jobs").treeview({
			data : ${treeData}
		});
	});
	
	function getValue() {
		var nodes = $('#select-jobs').treeview('getSelected');
		if(nodes && nodes.length <= 0) {
			layer.alert("请选择相关岗位");
			return;
		}
		var node = nodes[0];
		//给父窗口赋值
		/* parent.$("#departLabel").val(node.text);
		parent.$("#departId").val(node.id); 
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭 */
		
		return node;
	}
</script>
</html>

