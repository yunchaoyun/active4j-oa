<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
					
							<t:formvalid action="oa/hr/bankinfo/saveEdit">
								<input type="hidden" id="id" name="id" value="${bank.id }">
								<div class="panel panel-default">
									<div class="panel-heading">填写数据</div>
									<div class="panel-body">
										<div class="form-group">
											<label class="col-sm-2 control-label m-b">开户行：</label>
											<div class="col-sm-4 m-b">
												<input class="form-control" type="text" id="bank" name="bank" required="" value="${bank.bank }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label m-b">卡号：</label>
											<div class="col-sm-4 m-b">
												<input class="form-control" type="digits" id="cardNo" name="cardNo" required="" value="${bank.cardNo }">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">备注：</label>
											<div class="col-sm-6">
												<textarea rows="6" id="memo" name="memo" class="form-control">${bank.memo }</textarea>
											</div>
										</div>
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

