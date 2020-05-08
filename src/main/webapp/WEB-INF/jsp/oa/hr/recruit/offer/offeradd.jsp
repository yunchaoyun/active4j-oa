<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,prettyfile,webuploader"></t:base>
<script type="text/javascript">
$(function() {
	laydate({elem:"#reportDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
});

</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/recruit/offer/save">
							<input type="hidden" name="id" id="id" value="${offer.id }">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">选择简历*：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="oa/hr/recruit/offer/selectCv" hiddenName="cvId" hiddenValue="${cv.id }" textValue="${cv.name }" textName="cvName" width="80%" height="80%" hiddenId="cvId" textId="cvName"></t:choose>
                                   	</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">要求入职时间：</label>
                                <div class="col-sm-8">
                                    <input id="reportDate" name="reportDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${offer.reportDate }" type="date" pattern="yyyy-MM-dd"/>'>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">入职部门：</label>
                                <div class="col-sm-8">
                                    <input id="jobDepart" name="jobDepart" type="text" class="form-control" value="${offer.jobDepart }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">试用期薪资：</label>
                                <div class="col-sm-8">
                                    <input id="testWage" name="testWage" type="text" class="form-control" value="${offer.testWage }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">转正后薪资：</label>
                                <div class="col-sm-8">
                                    <input id="formalWage" name="formalWage" type="text"  class="form-control" value="${offer.formalWage }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">OFFER状态:</label>
                             	<div class="col-sm-8">
	                                <select class="form-control m-b" name="status" id="status" >
	                                		<c:forEach items="${offerStatuss }" var="t">
	                                			<option value="${t.value }" <c:if test="${offer.status == t.value}">selected</c:if>>${t.label }</option>
	                                		</c:forEach>
	                                </select>
                             	</div>
                            </div>
							<div class="form-group">
								<label class="col-sm-3 control-label">其他协商事宜：</label>
								<div class="col-sm-8">
									<textarea rows="8" id="memo" name="memo" class="form-control">${offer.memo }</textarea>
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

