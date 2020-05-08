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
						<t:formvalid action="sys/dic/save">
							<input name="id" type="hidden" value="${dic.id }">
							<input name="oldCode" type="hidden" value="${dic.code }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">字典编码*：</label>
                                <div class="col-sm-8">
                                    <input id="code" name="code" minlength="2" maxlength="20" type="text" class="form-control" required value="${dic.code }">
                               		<span class="help-block m-b-none">编码范围在2~20位字符</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">字典名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2" maxlength="20" type="text" class="form-control" required value="${dic.name }">
                                	<span class="help-block m-b-none">名称范围在2~20位字符</span>
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