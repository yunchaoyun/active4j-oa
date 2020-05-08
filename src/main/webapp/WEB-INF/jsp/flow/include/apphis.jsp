<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>审批信息</h5>
			</div>
			<div class="ibox-content">
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>审批意见</th>
								<th>审批人</th>
								<th>审批时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${lstComments }" var="c">
								<c:choose>
									<c:when test="${currentName == c.userId }">
										<tr style="background-color: #cbe5df">
											<td>${c.fullMessage }</td>
											<td>${c.userId }</td>
											<td><fmt:formatDate value="${c.time }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td>${c.fullMessage }</td>
											<td>${c.userId }</td>
											<td><fmt:formatDate value="${c.time }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
									</c:otherwise>
								</c:choose>
								
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>