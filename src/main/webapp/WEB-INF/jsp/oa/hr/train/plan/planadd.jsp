<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,ckeditor"></t:base>
<script type="text/javascript" src="static/ckfinder/ckfinder.js"></script>  
<script type="text/javascript">
var editor = null;

$(function() {
	laydate({elem:"#startDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
	laydate({elem:"#endDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
	
    //初始化
	editor = CKEDITOR.replace('memo');
	
	CKFinder.setupCKEditor( editor, 'static/ckfinder/' );
});

 	function ckUpdate() {
	   editor.updateElement();  	
	    return true;
	} 

</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/train/plan/save" beforeSubmit="ckUpdate">
							<input type="hidden" name="id" id="id" value="${plan.id }">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">计划名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text" class="form-control" required="" value="${plan.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择课程*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="oa/hr/train/plan/selectCourse" hiddenName="courseIds" hiddenValue="${courseIds }" textValue="${courseNames }" textName="courseNames" width="80%" height="80%" hiddenId="courseIds" textId="courseNames"></t:choose>
                                   	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">计划周期:</label>
                             	<div class="col-sm-8">
	                                <select class="form-control m-b" name="cycle" id="cycle" >
	                                		<c:forEach items="${plancycles }" var="t">
	                                			<option value="${t.value }" <c:if test="${plan.cycle == t.value}">selected</c:if>>${t.label }</option>
	                                		</c:forEach>
	                                </select>
                             	</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">开始日期：</label>
                                <div class="col-sm-8">
                                    <input id="startDate" name="startDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${plan.startDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">结束日期：</label>
                                <div class="col-sm-8">
                                    <input id="endDate" name="endDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${plan.endDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
                            
                            <div class="form-group">
								<label class="col-sm-3 control-label">培训目标：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="target" name="target" class="form-control">${plan.target }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">培训说明：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="memo" name="memo" class="form-control">${plan.memo }</textarea>
								</div>
							</div>
							
						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>

