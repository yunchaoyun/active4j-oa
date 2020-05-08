<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>流程信息</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" action="" method="post">
							<div class="form-group">
								<label class="col-sm-1 control-label">流程名称：</label>
								<div class="col-sm-5">
									<input id="workFlowName" name="workFlowName" minlength="2" type="text" class="form-control" required="" value="${base.name }" disabled="disabled">
								</div>
								<label class="col-sm-1 control-label">申请人：</label>
								<div class="col-sm-2">
									<input id="applyName" name="applyName" minlength="2" type="text" class="form-control" required="" value="${base.applyName }" disabled="disabled">
								</div>
								<label class="col-sm-1 control-label">申请时间：</label>
								<div class="col-sm-2">
									<input id="applyDate" name="applyDate" minlength="2" type="text" class="form-control" required="" value='<fmt:formatDate value="${base.applyDate }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>' disabled="disabled">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>