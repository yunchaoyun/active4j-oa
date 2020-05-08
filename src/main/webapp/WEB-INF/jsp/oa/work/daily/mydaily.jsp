<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,laydate,icheck"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<t:formvalid action="oa/work/daily/save">
							<input type="hidden" name="id" id="id" value="${oaWorkDaily.id }">
                            <div class="form-group">
								<label class="col-sm-3 control-label">日报标题*：</label>
								<div class="col-sm-8">
									<input type="text" name="title" id="title" class="form-control" required="" value="${oaWorkDaily.title }">
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">日报内容*：</label>
                                <div class="col-sm-8">
                                    <textarea id="content" name="content" class="form-control" rows="12">${oaWorkDaily.content }</textarea>
                                </div>
                            </div>
						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript"></script>

</html>

