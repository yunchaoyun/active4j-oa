<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,laydate"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/work/target/save">
							<input type="hidden" name="id" value="${target.id }" id="id">
							<input type="hidden" name="type" id="type" value="0">
                            <div class="form-group">
								<label class="col-sm-3 control-label">目标内容*：</label>
								<div class="col-sm-8">
									<input type="text" name="content" id="content" class="form-control" required="" value="${target.content }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">上级目标：</label>
								<div class="col-sm-8">
									<select class="form-control" name="parentTargetId" id="parentTargetId">
										<option value=""></option>
										<c:forEach items="${lstTargets }" var="t">
											<option value="${t.id}" <c:if test="${t.id == target.parentTargetId }">selected='selected'</c:if>>${t.content }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">目标量化数据：</label>
								<div class="col-sm-5">
									<input type="text" name="dataRecord" id="dataRecord" value="${target.dataRecord }" class="form-control">
								</div>
								<div class="col-sm-3">
									<c:choose>
                                		<c:when test="${empty  target.id}">
                                			<t:dictSelect name="unit" type="select" typeGroupCode="oatargetunit" defaultVal="1"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="unit" type="select" typeGroupCode="oatargetunit" defaultVal="${target.unit }"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">目标类别：</label>
                                <div class="col-sm-8">
                                	<c:choose>
                                		<c:when test="${empty  target.id}">
                                			<t:dictSelect name="category" type="select" typeGroupCode="oatargetcategory" defaultVal="3"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="category" type="select" typeGroupCode="oatargetcategory" defaultVal="${target.category }"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
                                	
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">参与人：</label>
                                <div class="col-sm-8">
                                	<div class="input-group">
                                		<t:choose url="commonController.do?selectUsers" hiddenName="participantIds" hiddenValue="${target.participantIds }" textValue="${target.participants }" textName="participants" hiddenId="participantIds" textId="participants"></t:choose>
                                	</div>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label m-b">目标时间 从：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="startDate" name="startDate"  value='<fmt:formatDate value="${target.startDate }" type="date" pattern="yyyy-MM-dd"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label m-b">到：</label>
								<div class="col-sm-4 m-b">
									<input class="laydate-icon form-control layer-date" id="endDate" name="endDate" value='<fmt:formatDate value="${target.endDate }" type="date" pattern="yyyy-MM-dd"/>'>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea id="memo" name="memo" class="form-control" >${target.memo}</textarea>
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
	laydate({
		elem : "#startDate",
		event : "focus",
		istime : false,
		format : 'YYYY-MM-DD'
	});
	
	laydate({
		elem : "#endDate",
		event : "focus",
		istime : false,
		format : 'YYYY-MM-DD'
	});
	
	
});

</script>

</html>

