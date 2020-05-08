<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview,laydate,webuploader"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/pact/saveEdit">
							<input type="hidden" id="id" name="id" value="${pact.id }">
							<input type="hidden" name="attachment" id="attachment" value="${pact.attachment }">
									<div class="form-group">
										<div>
											<label class="col-sm-3 control-label m-b">工号：</label>
											<div class="col-sm-4 m-b">
												<input id="userNo" name="userNo" type="text" class="form-control" value="${pact.userNo }">
			                              	</div>
										</div>
									</div>								
										<!-- <div class="form-group">
											<label class="col-sm-3 control-label m-b">姓名：</label>
											<div class="col-sm-4 m-b">
												<input id="name" name="name" type="text" class="form-control" value="">
			                              	</div>
										</div> -->
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">合同编号：</label>
											<div class="col-sm-4 m-b">
												<input id="pactNo" name="pactNo" type="text" class="form-control" value="${pact.pactNo }">
			                              	</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">合同类型：</label>
											<div class="col-sm-4 m-b">
												<t:dictSelect name="pactType" type="select" typeGroupCode="contracttype" defaultVal="${pact.pactType }"></t:dictSelect>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">是否签订培训协议：</label>
											<div class="col-sm-4 m-b">
												<t:dictSelect name="canTrainPact" type="select" typeGroupCode="yesorno" defaultVal="${pact.canTrainPact }"></t:dictSelect>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">有无固定期限合同：</label>
											<div class="col-sm-4 m-b">
												<t:dictSelect name="canGuDing" type="select" typeGroupCode="yesorno" defaultVal="${pact.canGuDing }"></t:dictSelect>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">生效期：</label>
											<div class="col-sm-4 m-b">
												<input class="laydate-icon form-control layer-date" id="effectDate" name="effectDate" required="" value='<fmt:formatDate value="${pact.effectDate }" type="date" pattern="yyyy-MM-dd"/>'>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label m-b">失效期：</label>
											<div class="col-sm-4 m-b">
												<input class="laydate-icon form-control layer-date" id="noEffectDate" name="noEffectDate" required="" value='<fmt:formatDate value="${pact.noEffectDate }" type="date" pattern="yyyy-MM-dd"/>'>
											</div>
										</div>
										<div class="form-group">
			                          		<label class="col-sm-3 control-label m-b">附件:</label>
											<%-- <div class="col-sm-5">
												<div id="uploader-demo">
															<div id="fileList" class="uploader-list">${attachment }</div>
															<div id="filePicker">重新选择</div>
												</div>
											</div> --%>
											<div class="col-sm-2">
												<div id="filePicker">重新选择</div>
											</div>
											<div class="col-sm-4">
												<div id="fileList" class="uploader-list">${attachment }</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-3 control-label">备注：</label>
											<div class="col-sm-6">
												<textarea rows="6" id="memo" name="memo" class="form-control">${pact.memo }</textarea>
											</div>
										</div>
						</t:formvalid>
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
				elem : "#effectDate",
				event : "focus",
				istime : true,
				format : 'YYYY-MM-DD'
			});
			laydate({
				elem : "#noEffectDate",
				event : "focus",
				istime : true,
				format : 'YYYY-MM-DD'
			});
		});

	});
</script>
</html>

