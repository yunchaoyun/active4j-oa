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
						<t:formvalid action="oa/work/meetType/save">
							<input type="hidden" name="id" id="id" value="${type.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">会议类型名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2" maxlength="30" type="text" class="form-control" required="" value="${type.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea id="memo" name="memo" class="form-control" >${type.memo}</textarea>
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

