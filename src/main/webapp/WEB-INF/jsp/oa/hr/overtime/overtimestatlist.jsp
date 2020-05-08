<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>加班统计</h5>
					</div>
					<div class="ibox-content">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>序号</th>
									<th>用户</th>
									<th>部门</th>
									<th>总加班小时数</th>
									<th>平时加班小时数</th>
									<th>周末加班小时数</th>
									<th>节假日加班小时数</th>
									<th>已使用加班小时数</th>
									<th>剩余加班小时数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${lstResult }" var="time" varStatus="xh">
									<tr>
	                                    <td>${xh.index + 1 }</td>
	                                    <td>${time.realname }</td>
	                                    <td>${time.departname }</td>
	                                    <td>${time.total }</td>
	                                    <td>${time.workday }</td>
	                                    <td>${time.weekend }</td>
	                                    <td>${time.holiday }</td>
	                                    <td>${time.used }</td>
	                                    <td>${time.sheng }</td>
                                	</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>

</html>