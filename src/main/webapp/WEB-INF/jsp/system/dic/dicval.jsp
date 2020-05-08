<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="sys/dic/saveval">
							<input name="id" type="hidden" value="${dicValue.id }">
							<input name="name" type="hidden" value="${name }">
							<input name="parentId" type="hidden" value="${parentId }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">参数名称*</label>
                                <div class="col-sm-8">
                                    <input id="label" name="label" minlength="1" maxlength="20" type="text" class="form-control" required value="${dicValue.label }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">参数值*</label>
                                <div class="col-sm-8">
                                    <input id="value" name="value" minlength="1" maxlength="20" type="text" class="form-control" required value="${dicValue.value }">
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