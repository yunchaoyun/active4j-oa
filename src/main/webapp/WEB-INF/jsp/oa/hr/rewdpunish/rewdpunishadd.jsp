<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview,laydate,icheck,prettyfile,webuploader"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="row">
						<div class="col-sm-4">
							<div class="panel panel-default">
								<div class="panel-heading">员工选择</div>
								<div class="panel-body">
									<div id="select-oausers"></div>
								</div>
							</div>
						</div>
						<div class="col-sm-8">
							<t:formvalid action="oa/hr/rewdpunish/saveAdd">
							<input type="hidden" name="attachment" id="attachment" value="">
								<div class="panel panel-default">
									<div class="panel-heading">已选员工</div>
									<div class="panel-body" id="userPanel"></div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">填写数据</div>
									<div class="panel-body">
									
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">奖惩区分：</label>
											<div class="col-sm-4 m-b">
												<t:dictSelect name="rpType" type="select" typeGroupCode="rewdpunishtype" defaultVal="0"></t:dictSelect>
											</div>
										</div>						
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">奖惩名目：</label>
											<div class="col-sm-4 m-b">
												<input id="items" name="items" type="text" class="form-control" value="" required="required">
			                              	</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">奖惩金额：</label>
											<div class="col-sm-4 m-b">
												<input id="rpMoney" name="rpMoney" type="text" class="form-control" value=""  required="required">
			                              	</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">奖惩日期：</label>
											<div class="col-sm-4 m-b">
												<input class="laydate-icon form-control layer-date" id="rpDate" name="rpDate" required="">
											</div>
										</div>
										<div class="form-group">
			                          		<label class="col-sm-3 control-label m-b">附件:</label>
											<div class="col-sm-2">
												<div id="filePicker">上传附件</div>
											</div>
											<div class="col-sm-4">
												<div id="fileList" class="uploader-list"></div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">备注：</label>
											<div class="col-sm-6">
												<textarea rows="6" id="rpDemo" name="rpDemo" class="form-control"></textarea>
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
	//初始化Web Uploader
	var uploader = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto : true,

		// swf文件路径
		swf : 'static/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : 'func/upload/uploadFiles?db=1',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#filePicker',
		},

	});


	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function(file, percentage) {
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file, data) {
		var filePath = data.attributes.filePath; 
		$("#fileList").html(file.name);
		$("#attachment").val(filePath);
	});

	// 文件上传失败，显示上传出错。
	uploader.on('uploadError', function(file) {

	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function(file) {
		qhTipSuccess('上传完成....');
	});

});
	$(function() {

		$(function() {
			laydate({
				elem : "#rpDate",
				event : "focus",
				istime : true,
				format : 'YYYY-MM-DD'
			});
		});

		//用户树形结构
		$("#select-oausers").treeview({
			showCheckbox : true,
			data : ${userTreeStr},
			//节点被选中
			onNodeChecked : function(event, node) {
				//选中父节点展开
				if (node.state.checked) {
					$("#select-oausers").treeview('expandNode', [ node.nodeId, {
						silent : true
					} ]);
				}

				//递归选中子节点
				checkUserChildrens("select-oausers", node);

				var parent = $('#select-oausers').treeview('getParent', node);
				if(null != parent.state) {
					//循环遍历
					while (parent) {
						$("#select-oausers").treeview('checkNode', [ parent, {
							silent : true
						} ]);
						parent = $('#select-oausers').treeview('getParent', parent);
						if(null == parent.state) {
							return;
						}
					}
				}
			},
			//节点被取消选中
			onNodeUnchecked : function(event, node) {
				//递归取消选中子节点
				unUserCheckChildrens("select-oausers", node);
				//判断父节点的状态，需要先判断同级别节点是否有没有被选中，递归
				checkUserBrothers("select-oausers", node);
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

