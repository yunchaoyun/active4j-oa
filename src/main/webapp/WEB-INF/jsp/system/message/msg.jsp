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
						<t:formvalid action="">
							<div class="form-group">
                                <label class="col-sm-3 control-label">发送人*：</label>
                                <div class="col-sm-8">
                                    <input id="sender" name="sender" type="text" class="form-control" required="" value="${msg.sender }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">发送时间*：</label>
                                <div class="col-sm-8">
                                    <input id="sendTime" name="sendTime" type="text" class="form-control" required="" value='<fmt:formatDate value="${msg.sendTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>' >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">内容：</label>
                                <div class="col-sm-8">
                                    <textarea id="content" name="content" class="form-control" rows="10">${msg.content }</textarea>
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

