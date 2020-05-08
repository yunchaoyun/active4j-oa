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
						<t:formvalid action="wf/form/category/save">
							<input type="hidden" name="id" id="id" value="${category.id }">
							<div class="form-group">
                                <label class="col-sm-3 control-label">类别名称*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" minlength="2" maxlength="30" type="text" class="form-control" required="" value="${category.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">排序*：</label>
                                <div class="col-sm-8">
                                    <input id="sort" name="sort" type="text" class="form-control" required="" digits="true" value="${category.sort }">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea id="memo" name="memo" class="form-control" >${category.memo}</textarea>
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

