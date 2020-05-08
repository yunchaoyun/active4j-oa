<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/hr/train/course/cate/save">
							<input type="hidden" name="id" id="id" value="${cate.id }">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程类别*：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text" class="form-control" required="" value="${cate.name }">
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

