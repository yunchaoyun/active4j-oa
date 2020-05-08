<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,prettyfile,webuploader"></t:base>
<script type="text/javascript">
$(function() {
	laydate({elem:"#startDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
	laydate({elem:"#endDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
});

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
						<t:formvalid action="oa/hr/recruit/plan/save">
							<input type="hidden" name="id" id="id" value="${plan.id }">
							<input type="hidden" name="attachment" id="attachment" value="${plan.attachment }">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">计划名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text" class="form-control" required="" value="${plan.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">招聘需求*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="oa/hr/recruit/plan/selectNeed" hiddenName="needJobIds" hiddenValue="${needIds }" textValue="${needJobs }" textName="needJobs" width="80%" height="80%" hiddenId="needJobIds" textId="needJobs"></t:choose>
                                   	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">招聘渠道:</label>
                             	<div class="col-sm-8">
	                                <select class="form-control m-b" name="canalType" id="canalType" >
	                                		<c:forEach items="${canalTypes }" var="t">
	                                			<option value="${t.value }" <c:if test="${plan.canalType == t.value}">selected</c:if>>${t.label }</option>
	                                		</c:forEach>
	                                </select>
                             	</div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">人数合计：</label>
                                <div class="col-sm-8">
                                    <input id="planNum" name="planNum" type="text" class="form-control" required="" value="${plan.planNum }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">预算费用：</label>
                                <div class="col-sm-8">
                                    <input id="money" name="money" type="text" class="form-control" value="${plan.money }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">状态:</label>
                             	<div class="col-sm-8">
	                                <select class="form-control m-b" name="status" id="status" >
	                                		<c:forEach items="${planStatus }" var="t">
	                                			<option value="${t.value }" <c:if test="${plan.status == t.value}">selected</c:if>>${t.label }</option>
	                                		</c:forEach>
	                                </select>
                             	</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">招聘时间（从）：</label>
                                <div class="col-sm-8">
                                    <input id="startDate" name="startDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${plan.startDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">招聘时间（到）：</label>
                                <div class="col-sm-8">
                                    <input id="endDate" name="endDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${plan.endDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">说明：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="discription" name="discription" class="form-control">${plan.discription }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="memo" name="memo" class="form-control">${plan.memo }</textarea>
								</div>
							</div>
							<c:if test="${empty plan.id }">
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
							<c:if test="${not empty plan.id }">
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

