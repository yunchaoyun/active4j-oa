<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid"></t:base>
<script type="text/javascript">
	//选择一级菜单，还是下级菜单
	function changeMenu() {
		var type = $("#functionLevel").val();

		if ('0' == type) {
			$("#parentControl").hide();
			$("#parentFunction").val(null);
		} else {
			$("#parentControl").show();
		}
	}

	$(function() {
		var level = '${function.level}';
		if(level) {
			if (level == '0') {
				$("#parentControl").hide();
				$("#parentFunction").val(null);
			} else {
				$("#parentControl").show();
			}
		}else {
			$("#parentControl").hide();
			$("#parentFunction").val(null);
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
						<t:formvalid action="sys/menu/save">
							<input name="id" type="hidden" value="${function.id}">
							<div class="form-group">
								<label class="col-sm-3 control-label">菜单名称*</label>
								<div class="col-sm-8">
									<input id="functionName" name="name" minlength="1" maxlength="20" type="text" class="form-control m-b" required value="${function.name }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">菜单等级</label>
								<div class="col-sm-8">
									<select class="form-control m-b" name="level" id="functionLevel" onchange="changeMenu();">
										<option value="0" <c:if test="${function.level eq 0}">selected="selected"</c:if>>一级菜单</option>
										<option value="1" <c:if test="${function.level > 0}">selected="selected"</c:if>>下级菜单</option>
									</select>
								</div>
							</div>
							<div class="form-group" id="parentControl" style="display: none;">
								<label class="col-sm-3 control-label">父级菜单</label>
								<div class="col-sm-8">
									<select class="form-control m-b" name="parentId" id="parentFunction">
										<c:forEach items="${lstTrees}" var="f">
											<option value="${f.value}" <c:if test="${function.parentId == f.value }">selected='selected'</c:if>>${f.key}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">菜单地址</label>
								<div class="col-sm-8">
									<input id="functionUrl" name="url" type="text" class="form-control m-b" value="${function.url }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">菜单图标</label>
								<div class="col-sm-8">
										<t:chooseIcon hiddenName="icon" hiddenId="icon" hiddenValue="${function.icon }"></t:chooseIcon>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">菜单排序*</label>
								<div class="col-sm-8">
									<input id="functionOrder" name="orderNo" type="text" class="form-control m-b" required value="${function.orderNo }">
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