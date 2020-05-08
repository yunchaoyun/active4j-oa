<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,prettyfile,webuploader"></t:base>
<script type="text/javascript">
$(function() {
	laydate({elem:"#viewDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
});

</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/recruit/view/save">
							<input type="hidden" name="id" id="id" value="${view.id }">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择简历*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="oa/hr/recruit/view/selectCv" hiddenName="cvId" hiddenValue="${cv.id }" textValue="${cv.name }" textName="cvName" width="80%" height="80%" hiddenId="cvId" textId="cvName"></t:choose>
                                   	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">面试时间：</label>
                                <div class="col-sm-8">
                                    <input id="viewDate" name="viewDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${view.viewDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">面试方式:</label>
                             	<div class="col-sm-8">
	                                <select class="form-control m-b" name="viewType" id="viewType" >
	                                		<c:forEach items="${viewTypes }" var="t">
	                                			<option value="${t.value }" <c:if test="${view.viewType == t.value}">selected</c:if>>${t.label }</option>
	                                		</c:forEach>
	                                </select>
                             	</div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">面试内容：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="content" name="content" class="form-control">${view.content }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">面试建议：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="suggestion" name="suggestion" class="form-control">${view.suggestion }</textarea>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">面试人：</label>
                                <div class="col-sm-8">
                                    <input id="staff" name="staff" type="text" class="form-control" value="${view.staff }">
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

