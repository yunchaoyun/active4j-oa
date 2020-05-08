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
						<t:formvalid action="oa/work/target/saveExcute">
							<input type="hidden" name="targetId" id="targetId" value="${target.id }">
                            <div class="form-group">
								<label class="col-sm-3 control-label">目标内容*：</label>
								<div class="col-sm-8">
									<p  class="form-control-static">${target.content }</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">目标完成数据：</label>
								<div class="col-sm-5">
									<input type="text" name="finishData" id="finishData" class="form-control">
								</div>
								<div class="col-sm-3">
									<c:choose>
										<c:when test="${target.unit == '0' }">
											<p class="form-control-static">%</p>
										</c:when>
										<c:when test="${target.unit == '1' }">
											<p class="form-control-static">万</p>
										</c:when>
										<c:when test="${target.unit == '2' }">
											<p class="form-control-static">个</p>
										</c:when>
									</c:choose>
								</div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">情况说明：</label>
                                <div class="col-sm-8">
                                    <textarea id="content" name="content" class="form-control" rows="10"></textarea>
                                </div>
                            </div>
						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$(function() {
});

</script>

</html>

