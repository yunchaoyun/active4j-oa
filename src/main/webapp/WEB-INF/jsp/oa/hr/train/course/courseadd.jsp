<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,prettyfile,webuploader"></t:base>
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
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/train/course/save">
							<input type="hidden" name="id" id="id" value="${course.id }">
							<input type="hidden" name="attachment" id="attachment" value="${course.attachment }">
							
							<div class="form-group">
                                <label class="col-sm-3 control-label">课程名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text" class="form-control" required="required" value="${course.name }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程类型*：</label>
                                <div class="col-sm-8">
                               		<select class="form-control m-b" name="cateId" id="cateId" >
                                		<c:forEach items="${cates }" var="t">
                                			<option value="${t.id }" <c:if test="${cate.id == t.id}">selected</c:if>>${t.name }</option>
                                		</c:forEach>
                                	</select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">课时(小时)：</label>
                                <div class="col-sm-8">
                                    <input id="courseHour" name="courseHour" type="text" class="form-control" value="${course.courseHour }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">授课方式:</label>
                             	<div class="col-sm-8">
	                                <select class="form-control m-b" name="courseType" id="courseType" >
	                                		<c:forEach items="${courseTypes }" var="t">
	                                			<option value="${t.value }" <c:if test="${course.courseType == t.value}">selected</c:if>>${t.label }</option>
	                                		</c:forEach>
	                                </select>
                             	</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">费用：</label>
                                <div class="col-sm-8">
                                    <input id="needMoney" name="needMoney" type="text" class="form-control" value="${course.needMoney }">
                                </div>
                            </div>
                            
							<div class="form-group">
								<label class="col-sm-3 control-label">目标：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="target" name="target" class="form-control">${course.target }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">说明：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="memo" name="memo" class="form-control">${course.memo }</textarea>
								</div>
							</div>
							<c:if test="${empty course.id }">
								<div class="form-group">
	                          		<label class="col-sm-3 control-label m-b">相关附件:</label>
									<div class="col-sm-2">
										<div id="filePicker">上传附件</div>
									</div>
									<div class="col-sm-4">
										<div id="fileList" class="uploader-list"></div>
									</div>
								</div>
							</c:if>
							<c:if test="${not empty course.id }">
								<div class="form-group">
	                          		<label class="col-sm-3 control-label m-b">相关附件:</label>
									<div class="col-sm-2">
										<div id="filePicker">上传附件</div>
									</div>
									<div class="col-sm-4">
										<div id="fileList" class="uploader-list">${attachment}</div>
									</div>
								</div>
							</c:if>
							
						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>

