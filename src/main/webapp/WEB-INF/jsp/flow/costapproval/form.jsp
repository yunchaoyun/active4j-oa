<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
							<div class="form-group">
								<label class="col-sm-3 control-label">申请编号：</label>
								<div class="col-sm-5">
									<input id="projectNo" name="projectNo" minlength="2" type="text" class="form-control" required="" value="${base.projectNo }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">名称：</label>
								<div class="col-sm-5">
									<input id="name" name="name" minlength="2" type="text" class="form-control" required="" value="${base.name }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">所属部门：</label>
								<div class="col-sm-5">
									<div class="input-group">
                                     	<t:choose url="common/selectDepart" hiddenName="deptId" hiddenValue="${biz.deptId }" textValue="${deptName }" textName="departLabel" hiddenId="departId" textId="departLabel"></t:choose>
                                    </div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">金额：</label>
								<div class="col-sm-5">
									<input id="amount" name="amount" type="number" class="form-control" required="" value="${biz.amount }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">财务月：</label>
								<div class="col-sm-5">
									<input class="laydate-icon form-control layer-date" id="month" name="month" value="${biz.month }">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">理由：</label>
								<div class="col-sm-5">
									<textarea id="reason" name="reason" class="form-control">${biz.reason }</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">紧急程度：</label>
								<div class="col-sm-5">
									<c:choose>
                                		<c:when test="${empty base.level}">
                                			<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="0"></t:dictSelect>
                                		</c:when>
                                		<c:otherwise>
                                			<t:dictSelect name="level" type="radio" typeGroupCode="workflowlevel" defaultVal="${base.level}"></t:dictSelect>
                                		</c:otherwise>
                                	</c:choose>
								</div>
							</div>