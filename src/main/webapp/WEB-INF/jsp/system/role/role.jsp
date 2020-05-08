<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid"></t:base>
<script type="text/javascript">
	//选择一级菜单，还是下级菜单
	function changeRole() {
		var type = $("#roleLevel").val();

		if ('0' == type) {
			$("#parentControl").hide();
			$("#parentRole").val(null);
		} else {
			$("#parentControl").show();
		}
	}

	$(function() {
		var level = '${role.level}';
		if(level) {
			if (level == '0') {
				$("#parentControl").hide();
				$("#parentRole").val(null);
			} else {
				$("#parentControl").show();
			}
		}else {
			$("#parentControl").hide();
			$("#parentRole").val(null);
		}
		
	})
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="sys/role/save">
							<input type="hidden" name="id" id="id" value="${role.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">角色名称*：</label>
                                <div class="col-sm-8">
                                    <input id="roleName" name="roleName" minlength="2" maxlength="20" type="text" class="form-control" required value="${role.roleName }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">角色编码*：</label>
                                <div class="col-sm-8">
                                    <input id="roleCode" name="roleCode" minlength="2" maxlength="20" type="text" class="form-control" required value="${role.roleCode }">
                                </div>
                            </div>
                            <div class="form-group">
								<label class="col-sm-3 control-label">角色等级：</label>
								<div class="col-sm-8">
									<select class="form-control m-b" name="level" id="roleLevel" onchange="changeRole();">
										<option value="0" <c:if test="${role.level eq 0}">selected="selected"</c:if>>一级角色</option>
										<option value="1" <c:if test="${role.level > 0}">selected="selected"</c:if>>下级角色</option>
									</select>
								</div>
							</div>
							<div class="form-group" id="parentControl" style="display: none;">
								<label class="col-sm-3 control-label">上级角色</label>
								<div class="col-sm-8">
									<select class="form-control m-b" name="parentId" id="parentRole">
										<c:forEach items="${lstTrees}" var="f">
											<option value="${f.value}" <c:if test="${role.parentId == f.value }">selected='selected'</c:if>>${f.key}</option>
										</c:forEach>
									</select>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">角色描述：</label>
                                <div class="col-sm-8">
                                    <textarea name="description" class="form-control">${role.description }</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">角色备注：</label>
                                <div class="col-sm-8">
                                    <textarea name="memo" class="form-control">${role.memo }</textarea>
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