<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,webuploader"></t:base>
<script type="text/javascript">
	$(function() {
		//初始化Web Uploader
		var uploader = WebUploader.create({

			// 选完文件后，是否自动上传。
			auto : true,

			// swf文件路径
			swf : 'static/webuploader/Uploader.swf',

			// 文件接收服务端。(数据库保存：1)
			server : 'func/upload/uploadImages?db=1',

			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : {
				id : '#filePicker',
				label : '点击修改头像'
			},

			// 只允许选择图片文件。
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,jpeg,bmp,png',
				mimeTypes : 'image/*'
			}
		});

		// 文件上传过程中创建进度条实时显示。
		uploader.on('uploadProgress', function(file, percentage) {
		});

		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		uploader.on('uploadSuccess', function(file, data) {
			var filePath = data.attributes.filePath;
			$("#imgUrl").attr("src", filePath);
			$("#headImgUrl").val(filePath);
			parent.$("#userHeadImg").attr("src", filePath);
		});

		// 文件上传失败，显示上传出错。
		uploader.on('uploadError', function(file) {

		});

		// 完成上传完了，成功或者失败，先删除进度条。
		uploader.on('uploadComplete', function(file) {
			qhTipSuccess('图片上传完成....');
		});

	});

	$(function() {
		$("#personInfoForm").validate({
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function(data) {
						if (data.success) {
							qhTipSuccess('保存成功');
							parent.$("#userHeadImg").attr("src", data.attributes.headImgUrl);
							parent.$("#mainUserName").html(data.attributes.realName);
							$("#infoUserName").html(data.attributes.realName);
						} else {
							qhTipWarning(data.msg);
						}
					},
					error : function(data) {
						qhTipError('系统错误，请联系系统管理员');
					}
				});
			}
		});
	});
	function submitAction() {
		$("#personInfoForm").submit();
	}
</script>


</head>
<body class="gray-bg">


	<div class="wrapper wrapper-content">
		<div class="row animated fadeInRight">
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>头像设置</h5>
					</div>
					<div>
						<div class="text-center ibox-content border-left-right">
							<img id="imgUrl" alt="image" class="img-responsive center-block img-circle" src="${user.headImgUrl }" style="height: 10rem; width: 10rem;">
						</div>
						<div class="ibox-content profile-content text-center">
							<h4>
								<strong id="infoUserName">${user.realName }</strong>
							</h4>
							<div class="user-button">
								<div class="row">
									<div class="col-sm-12">
										<!--dom结构部分-->
										<div id="uploader-demo">
											<div id="fileList" class="uploader-list"></div>
											<div id="filePicker">选择图片</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>个人信息</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="personInfoForm" action="sys/user/saveinfo" method="post">
							<input type="hidden" name="id" id="id" value="${user.id }">
							<input type="hidden" name="headImgUrl" id="headImgUrl" value="${user.headImgUrl }">
							<div class="form-group">
								<label class="col-sm-3 control-label">帐号：</label>
								<div class="col-sm-8">
									<p class="form-control-static">${user.userName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">真实姓名：</label>
								<div class="col-sm-8">
									<input id="realName" name="realName" minlength="2" maxlength="10" type="text" class="form-control" required="" value="${user.realName }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">部门：</label>
								<div class="col-sm-8">
									<p class="form-control-static">${user.deptName }</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">角色：</label>
								<div class="col-sm-8">
									<p class="form-control-static">${user.roleName }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">手机号码：</label>
								<div class="col-sm-8">
									<input id="telNo" name="telNo" type="text" class="form-control" value="${user.telNo }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">电子邮箱：</label>
								<div class="col-sm-8">
									<input id="email" name="email" type="email" class="form-control" value="${user.email }">
								</div>
							</div>
						</form>
						<div class="row">
							<div class="col-sm-11 text-right">
								<p>
									<button class="btn btn-info" onclick="submitAction();" type="submit">
										<i class="fa fa-check"></i>&nbsp;&nbsp;确定
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