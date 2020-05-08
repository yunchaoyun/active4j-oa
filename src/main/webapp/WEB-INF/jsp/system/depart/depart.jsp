<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,icheck"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="sys/dept/save">
							<input id="id" name="id" type="hidden" value="${depart.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">部门编号 *</label>
                                <div class="col-sm-8">
                                    <input id="deptNo" name="deptNo" type="text" class="form-control" required="" value="${depart.deptNo }">
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">部门名称 *</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2" maxlength="20" type="text" class="form-control" required="" value="${depart.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门类型 *</label>
                                <div class="col-sm-8">
                                   <select class="form-control m-b" name="type" id="type">
                                   		<c:forEach items="${types }" var="t">
                                   			<option value="${t.value }" <c:if test="${depart.type == t.value}">selected</c:if>>${t.label }</option>
                                   		</c:forEach>
                                   </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">上级部门</label>
                                <div class="col-sm-8">
                                     <div class="input-group">
                                     	<t:choose url="common/selectDepart" hiddenName="parentId" hiddenValue="${depart.parentId }" textValue="${parentDepartName }" textName="departLabel" hiddenId="parentId" textId="departLabel"></t:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">职能描述</label>
                                <div class="col-sm-8">
                                    <textarea name="description" class="form-control">${depart.description }</textarea>
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

