<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="activiti/process/model/create">
							<input type="hidden" name="id" id="id" value="${model.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">模型名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2" maxlength="30" type="text" class="form-control" required="" value="${model.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">模型Key*：</label>
                                <div class="col-sm-8">
                                    <input id="key" name="key" type="text" class="form-control" required="" value="${model.key }">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">描述：</label>
                                <div class="col-sm-8">
                                    <textarea id="description" name="description" class="form-control" >${model.description}</textarea>
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

