<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,webuploader"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			
			<div class="ibox float-e-margins">
				<div class="ibox-title">
				<h3>简历库</h3>
				</div>
					<div class="ibox-content">
						<t:formvalid action="oa/hr/recruit/cv/save">
							<input name="id" id = "id" type="hidden" value="${cv.id}">
							<input type="hidden" name="selfImage" id="selfImage" value="${cv.selfImage }">
							<input type="hidden" name="certiAttachment" id="certiAttachment" value="${cv.certiAttachment }">
							<input type="hidden" name="cvAttachment" id="cvAttachment" value="${cv.cvAttachment }">
				                        
                            <div class="panel-body">
		                            <div class="col-sm-10">
		                            
		                            	<div class="form-group">
			                          		<label class="col-sm-2 control-label">姓名*:</label>
			                                <div class="col-sm-4">
			                                    <input id="name" name="name" type="text" class="form-control" required="required" value="${cv.name }">
			                                </div>
			                                <label class="col-sm-2 control-label">性别:</label>
				                             <div class="col-sm-4">
				                                <select class="form-control m-b" name="sex" id="sex" >
				                                		<c:forEach items="${sexs }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.sex == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
				                             </div>
										</div>
										
										<div class="form-group">
			                                <label class="col-sm-2 control-label">联系电话*:</label>
			                                <div class="col-sm-4">
			                                    <input id="telNo" name="telNo" type="text" class="form-control" required="required" value="${cv.telNo }">
			                                </div>
											<label class="col-sm-2 control-label">E-mail:</label>
				                             <div class="col-sm-4">
			                                    <input id="email" name="email" type="text" class="form-control" value="${cv.email }">
				                             </div>
										</div>
			                                
				                        
		                            	<div class="form-group">
			                          		<label class="col-sm-2 control-label">应聘职位*:</label>
			                                <div class="col-sm-4">
			                                    <input id="cvJob" name="cvJob" type="text" class="form-control" required="required" value="${cv.cvJob }">
			                                </div>
			                                <label class="col-sm-2 control-label">选择计划:</label>
			                                <div class="col-sm-4">
			                                	<select class="form-control m-b" name="planId" id="planId" >
				                                		<c:forEach items="${plans }" var="t">
				                                			<option value="${t.id }" <c:if test="${plan.id == t.id}">selected</c:if>>${t.name }</option>
				                                		</c:forEach>
				                                </select>
			                                </div>
			                           </div>  
			                           
             				           <div class="form-group">
				                           	<label class="col-sm-2 control-label">招聘渠道:</label>
			                                <div class="col-sm-4">
			                                    <select class="form-control m-b" name="canalType" id="canalType" >
				                                		<c:forEach items="${canaltypes }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.canalType == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
			                                </div>
				                           	<label class="col-sm-2 control-label">工作年限:</label>
				                             <div class="col-sm-4">
				                                <select class="form-control m-b" name="jobLength" id="jobLength" >
				                                		<c:forEach items="${jobLengths }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.jobLength == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
				                             </div>
			                          		
			                           </div>
			                           
			                           <div class="form-group">
			                           		<label class="col-sm-2 control-label">期望工作城市:</label>
			                                <div class="col-sm-4">
			                                    <input id="hopeCity" name="hopeCity" type="text" class="form-control" value="${cv.hopeCity }">
			                                </div>
			                              	<label class="col-sm-2 control-label">现居住城市:</label>
				                             <div class="col-sm-4">
				                                  <input id="liveCity" name="liveCity" type="text" class="form-control" value="${cv.liveCity }">
				                             </div>
			                           </div>
			                           
                                   		<div class="form-group">
				                           	
			                          		<label class="col-sm-2 control-label">期望薪水(税前):</label>
			                                <div class="col-sm-4">
			                                    <input id="hopeWage" name="hopeWage" type="text" class="form-control" value="${cv.hopeWage }">
			                                </div>
			                                <label class="col-sm-2 control-label">到岗时间:</label>
				                             <div class="col-sm-4">
				                                <select class="form-control m-b" name="reportTime" id="reportTime" >
				                                		<c:forEach items="${reportTimes }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.reportTime == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
				                             </div>
			                           </div> 
			                           
                                             <div class="form-group">
				                           	<label class="col-sm-2 control-label">期望工作性质:</label>
				                             <div class="col-sm-4">
												<select class="form-control m-b" name="jobNature" id="jobNature" >
				                                		<c:forEach items="${jobnaturetypes }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.jobNature == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
				                             </div>
				                           	<label class="col-sm-2 control-label">出生日期:</label>
				                             <div class="col-sm-4">
				                             	<input id="birthDate" name="birthDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${cv.birthDate }" type="date" pattern="yyyy-MM-dd"/>'>
				                             </div>
			                          		
			                           </div>
			                           
			                           <div class="form-group">
			                           		<label class="col-sm-2 control-label">年龄:</label>
			                                <div class="col-sm-4">
			                                    <input id="age" name="age" type="text" class="form-control" value="${cv.age }">
				                             </div>
				                             <label class="col-sm-2 control-label">微信:</label>
				                             <div class="col-sm-4">
												<input id="weixin" name="weixin" type="text" class="form-control" value="${cv.weixin }">
				                             </div>
			                           </div>
			                              
                                       <div class="form-group">
                                       		<label class="col-sm-2 control-label">国籍:</label>
			                                <div class="col-sm-4">
			                                    <input id="nation" name="nation" type="text" class="form-control" value="${cv.nation }">
				                             </div>
				                             
				                           	<label class="col-sm-2 control-label">血型:</label>
				                             <div class="col-sm-4">
				                                <select class="form-control m-b" name="bloodType" id="bloodType" >
				                                		<c:forEach items="${bloodtypes }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.bloodType == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
				                             </div>
			                           </div> 

                                       <div class="form-group">
				                           	<label class="col-sm-2 control-label">毕业学校:</label>
				                             <div class="col-sm-4">
												<input id="gradSchool" name="gradSchool" type="text" class="form-control" value="${cv.gradSchool }">
				                             </div>
				                           	<label class="col-sm-2 control-label">所学专业:</label>
				                             <div class="col-sm-4">
												<input id="studyMajor" name="studyMajor" type="text" class="form-control" value="${cv.studyMajor }">
				                             </div>
			                          		
			                           </div>
			                           
			                           <div class="form-group">
			                           		<label class="col-sm-2 control-label">学位:</label>
			                                <div class="col-sm-4">
			                                    <input id="degree" name="degree" type="text" class="form-control" value="${cv.degree }">
				                             </div>
				                             <label class="col-sm-2 control-label">学历:</label>
				                             <div class="col-sm-4">
												<select class="form-control m-b" name="educationBack" id="educationBack" >
				                                		<c:forEach items="${degreeinfos }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.educationBack == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
				                             </div>
			                           </div>

                                             <div class="form-group">
				                           	<label class="col-sm-2 control-label">外语语种1:</label>
				                             <div class="col-sm-4">
												<input id="frnLanguage1" name="frnLanguage1" type="text" class="form-control" value="${cv.frnLanguage1 }">
				                             </div>
			                          		<label class="col-sm-2 control-label">外语水平1:</label>
											<div class="col-sm-4">
												<input id="frnLevel1" name="frnLevel1" type="text" class="form-control" value="${cv.frnLevel1 }">
											</div>
			                           </div>
			                           
                                      	<div class="form-group">
				                           	<label class="col-sm-2 control-label">外语语种2:</label>
				                             <div class="col-sm-4">
												<input id="frnLanguage2" name="frnLanguage2" type="text" class="form-control" value="${cv.frnLanguage2 }">
				                             </div>
			                          		<label class="col-sm-2 control-label">外语水平2:</label>
											<div class="col-sm-4">
												<input id="frnLevel2" name="frnLevel2" type="text" class="form-control" value="${cv.frnLevel2 }">
											</div>
			                           </div>
			                           
			                           <div class="form-group">
			                           		<label class="col-sm-2 control-label">简历状态:</label>
											<div class="col-sm-4">
												<select class="form-control m-b" name="status" id="status" >
				                                		<c:forEach items="${cvstatuss }" var="t">
				                                			<option value="${t.value }" <c:if test="${cv.status == t.value}">selected</c:if>>${t.label }</option>
				                                		</c:forEach>
				                                </select>
				                             </div>
			                           </div>
			                           <div class="form-group">
			                          		<label class="col-sm-2 control-label">特长:</label>
											<div class="col-sm-8">
												<textarea rows="8" id="speciality" name="speciality" class="form-control">${cv.speciality }</textarea>
			                                </div>
			                           </div>
			                           <div class="form-group">
			                          		<label class="col-sm-2 control-label">职业技能:</label>
											<div class="col-sm-8">
												<textarea rows="8" id="jobSkill" name="jobSkill" class="form-control">${cv.jobSkill }</textarea>
			                                </div>
			                           </div>
			                           <div class="form-group">
			                          		<label class="col-sm-2 control-label">工作经验:</label>
											<div class="col-sm-8">
												<textarea rows="8" id="experience" name="experience" class="form-control">${cv.experience }</textarea>
			                                </div>
			                           </div>
			                           
			                            <c:if test="${empty cv.id }">
										<div class="form-group">
			                          		<label class="col-sm-2 control-label m-b">上传证书:</label>
											<div class="col-sm-2">
												<div id="filePicker1">上传附件</div>
											</div>
											<div class="col-sm-4">
												<div id="fileList1" class="uploader-list"></div>
											</div>
										</div>
									</c:if>
									<c:if test="${not empty cv.id }">
										<div class="form-group">
			                          		<label class="col-sm-2 control-label m-b">上传证书:</label>
											<div class="col-sm-2">
												<div id="filePicker1">上传附件</div>
											</div>
											<div class="col-sm-4">
												<div id="fileList1" class="uploader-list">${certiAttachment}</div>
											</div>
										</div>
									</c:if>
									
									<c:if test="${empty cv.id }">
										<div class="form-group">
			                          		<label class="col-sm-2 control-label m-b">上传简历:</label>
											<div class="col-sm-2">
												<div id="filePicker2">上传附件</div>
											</div>
											<div class="col-sm-4">
												<div id="fileList2" class="uploader-list"></div>
											</div>
										</div>
									</c:if>
									<c:if test="${not empty cv.id }">
										<div class="form-group">
			                          		<label class="col-sm-2 control-label m-b">上传简历:</label>
											<div class="col-sm-2">
												<div id="filePicker2">上传附件</div>
											</div>
											<div class="col-sm-4">
												<div id="fileList2" class="uploader-list">${cvAttachment}</div>
											</div>
										</div>
									</c:if>
				                           
		                            </div>
		                            
		                            <div class="col-sm-2">
										<img id="imageUrl" alt="个人图片" class="img-responsive center-block" src="${cv.selfImage }" style="margin-top:2rem;width:13rem;height:13rem;border:1px solid #B2C5D9;">
										<!--dom结构部分-->
										<div id="uploader-demo" style="margin-left: 3.8rem; margin-top: 2rem">
											<div id="fileList" class="uploader-list"></div>
											<div id="filePicker">上传个人照片</div>
										</div>
		                            </div>
		                            
				                            
	                            </div>
						
							</t:formvalid>
						</div>
                    </div>
				</div>
			</div>
</body>

<script type="text/javascript">

//
$(function() {
	//初始化Web Uploader
	var uploader = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto : true,

		// swf文件路径
		swf : 'static/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : 'func/upload/uploadImages?db=1',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#filePicker',
			label : '上传个人图片'
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
		$("#imageUrl").attr("src", filePath);
		$("#selfImage").val(filePath);
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
	//初始化Web Uploader
	var uploader1 = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto : true,

		// swf文件路径
		swf : 'static/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : 'func/upload/uploadFiles?db=1',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#filePicker1',
		},

	});


	// 文件上传过程中创建进度条实时显示。
	uploader1.on('uploadProgress', function(file, percentage) {
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader1.on('uploadSuccess', function(file, data) {
		var filePath = data.attributes.filePath; 
		$("#fileList1").html(file.name);
		$("#certiAttachment").val(filePath);
	});

	// 文件上传失败，显示上传出错。
	uploader1.on('uploadError', function(file) {

	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader1.on('uploadComplete', function(file) {
		qhTipSuccess('上传完成....');
	});

});

$(function() {
	//初始化Web Uploader
	var uploader2 = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto : true,

		// swf文件路径
		swf : 'static/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : 'func/upload/uploadFiles?db=1',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#filePicker2',
		},

	});


	// 文件上传过程中创建进度条实时显示。
	uploader2.on('uploadProgress', function(file, percentage) {
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader2.on('uploadSuccess', function(file, data) {
		var filePath = data.attributes.filePath; 
		$("#fileList2").html(file.name);
		$("#cvAttachment").val(filePath);
	});

	// 文件上传失败，显示上传出错。
	uploader2.on('uploadError', function(file) {

	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader2.on('uploadComplete', function(file) {
		qhTipSuccess('上传完成....');
	});

});

	$(function() {
		laydate({elem:"#birthDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
	});
	
	
</script>

</html>
