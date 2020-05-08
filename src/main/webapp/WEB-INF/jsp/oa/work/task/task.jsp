<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,summernote,laydate"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/work/task/save" beforeSubmit="setEditValue();">
							<input type="hidden" name="id" id="id" value="${task.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">任务标题*：</label>
                                <div class="col-sm-8">
                                    <input id="title" name="title" type="text" class="form-control" required="" value="${task.title }">
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">上级任务：</label>
								<div class="col-sm-8">
									<select class="form-control" name="parentTaskId" id="parentTaskId">
										<option value=""></option>
										<c:forEach items="${lstTasks }" var="t">
											<option value="${t.id}" <c:if test="${t.id == task.parentTaskId }">selected='selected'</c:if>>${t.title }</option>
										</c:forEach>
									</select>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分配人：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="appointUserId" hiddenValue="${appointUserId }" textValue="${appointUserName }" textName="appointUserName" hiddenId="appointUserId" textId="appointUserName"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">责任人：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="userId" hiddenValue="${userId }" textValue="${userName }" textName="userName" hiddenId="userId" textId="userName"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">监控人：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="common/selectUsers" hiddenName="monitorUserId" hiddenValue="${monitorUserId }" textValue="${monitorUserName }" textName="monitorUserName" hiddenId="monitorUserId" textId="monitorUserName"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label m-b">任务时间 从：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="startTime" name="startTime"  value='<fmt:formatDate value="${task.startTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">到：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="endTime" name="endTime"  value='<fmt:formatDate value="${task.endTime }" type="both" pattern="yyyy-MM-dd HH:mm"/>'>
								</div>
							</div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">任务内容：</label>
								<div class="col-sm-8">
									<div id="summernote"></div>
									<input type="hidden" name="content" id="content" value="">
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
	$("#summernote").summernote({
		lang : "zh-CN",
		height : 300,
		// 重写图片上传  
		onImageUpload : function(files, editor, $editable) {
			uploadFile(files[0], editor, $editable);
		}
	});

	var content = '${task.content}';

	$('#summernote').code(content);
	
	
	laydate({
		elem : "#startTime",
		event : "focus",
		istime : true,
		format : 'YYYY-MM-DD hh:mm'
	});
	
	laydate({
		elem : "#endTime",
		event : "focus",
		istime : true,
		format : 'YYYY-MM-DD hh:mm'
	});
});

function uploadFile(file, editor, $editable) {
	var filename = false;
	try {
		filename = file['name'];
	} catch (e) {
		filename = false;
	}
	if (!filename) {
		$(".note-alarm").remove();
	}

	//以上防止在图片在编辑器内拖拽引发第二次上传导致的提示错误  
	data = new FormData();
	data.append("file", file);
	data.append("key", filename); //唯一性参数  

	$.ajax({
		data : data,
		type : "POST",
		url : "func/upload/uploadImages?db=1",
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			var o = $.parseJSON(data);
			if(o.success) {
				var filePath = o.attributes.filePath;
				editor.insertImage($editable, filePath);
			}else {
				qhTipError("上传失败!" + o.msg);
			}
		},
		error : function() {
			qhTipError("上传失败!");
			return;
		}
	});
}

function setEditValue() {

	var content = $('#summernote').code();

	$("#content").val(content);

	return true;
}

</script>

</html>

