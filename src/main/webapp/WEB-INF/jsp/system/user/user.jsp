<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck"></t:base>
	<script type="text/javascript">
		$(function() {
			$("#roleid").val("${roleId}".split(",")).trigger("change");
		});
	
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="sys/user/saveUser">
							<input type="hidden" name="id" id="id" value="${user.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">用户名*：</label>
                                <div class="col-sm-8">
                                    <input id="userName" name="userName" <c:if test="${not empty user.id }">readonly="readonly"</c:if> minlength="2" maxlength="20" type="text" class="form-control" required="" value="${user.userName }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">真实姓名*：</label>
                                <div class="col-sm-8">
                                    <input id="realName" name="realName" minlength="2" maxlength="10" type="text" class="form-control" required="" value="${user.realName }">
                                </div>
                            </div>
                            <c:if test="${empty user.id }">
	                            <div class="form-group">
	                                <label class="col-sm-3 control-label">密码*：</label>
	                                <div class="col-sm-8">
	                                    <input id="password" name="password" minlength="2" maxlength="20" type="password" class="form-control" required="">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-3 control-label">重复密码*：</label>
	                                <div class="col-sm-8">
	                                    <input id="repassword" name="repassword" minlength="2" maxlength="20" type="password" class="form-control" required="" equalTo='#password' >
	                                </div>
	                            </div>
                            </c:if>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门*：</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                     	<t:choose url="common/selectDepart" hiddenName="deptId" hiddenValue="${user.deptId }" textValue="${deptName }" textName="departLabel" hiddenId="departId" textId="departLabel"></t:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">角色：</label>
                                <div class="col-sm-8">
                                     <select class="form-control m-b select2" name="roleid" id="roleid" multiple="multiple" >
                                     	<c:forEach items="${lstRoles}" var="role">
                                     		<option value="${role.id }">${role.roleName}</option>
                                     	</c:forEach>
                                     </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">手机号码：</label>
                                <div class="col-sm-8">
                                    <input id="telNo" name="telNo" type="text" class="form-control" value="${user.telNo }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">常用邮箱：</label>
                                <div class="col-sm-8">
                                    <input id="email" name="email" type="email" class="form-control" value="${user.email }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否管理员：</label>
                                <div class="col-sm-8">
                                	<c:choose>
                                		<c:when test="${empty user.admin}">
                                			<t:dictSelect name="admin" type="radio" typeGroupCode="byesorno" defaultVal="false"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="admin" type="radio" typeGroupCode="byesorno" defaultVal="${user.admin}"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
                                	
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

