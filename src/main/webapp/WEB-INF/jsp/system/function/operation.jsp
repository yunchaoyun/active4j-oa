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
						<t:formvalid action="sys/menu/saveop">
							<input name="id" type="hidden" value="${operation.id}">
							<input name="functionId" type="hidden" value="${functionId}">
							<div class="form-group">
                                <label class="col-sm-3 control-label">操作名称</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text" class="form-control m-b" required value="${operation.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">操作代码</label>
                                <div class="col-sm-8">
                                    <input id="code" name="code" type="text" class="form-control m-b" required value="${operation.code }">
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