<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default"></t:base>
<script type="text/javascript" src="static/workcal/workcal.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row animated fadeInRight">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>工作日历</h5>
					</div>
					<div>
						<div class="ibox-content">
							<div class="row">
								<form class="form-horizontal m-t">
									<div class="form-group">
										<label class="col-sm-1 control-label m-b">年份：</label>
										<div class="col-sm-1 m-b">
											<select class="form-control" id="selectYear" name="selectYear" onchange="doBtnSelectYearAction();">
												<c:forEach items="${lstYears }" var="y">
													<option value="${y}"<c:if test="${y == currentYear }">selected='selected'</c:if>>${y}</option>
												</c:forEach>
											</select>
										</div>
											<div class="col-sm-10 m-b">
									<div class="ibox">
										<div class="text-center">
											<h2 class="m-b" id="yearTitle">${currentYear}年度公司考勤日历</h2>
										</div>
									</div>
								</div>
									</div>
								</form>
							</div>
							<div class="row">
								<div class="col-sm-2">
									<ul class="category-list" style="padding: 0; margin-left: 20px; margin-top: 50px;">
										<li><a href="javascript:void(0);"><span class="label" style="background-color: #000000">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;当前日期 </a></li>
										<li><a href="javascript:void(0);"><span class="label" style="background-color: #F2DEDE">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;工作日 </a></li>
										<li><a href="javascript:void(0);"><span class="label" style="background-color: #23c6c8">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;法定假日 </a></li>
										<li><a href="javascript:void(0);"><span class="label" style="background-color: #ADD8E6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;公司假期 </a></li>
										<li><a href="javascript:void(0);"><span class="label" style="background-color: #EEB422">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;公司调休 </a></li>
									</ul>
								</div>
								<div class="col-sm-10" id="m-main"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	$(function() {
		var workCalendar = new WorkCalendar(${currentYear});
		workCalendar.render('#m-main');
		workCalendar.markWorkdays(${workdays});
		workCalendar.markHolidays(${holidays});
		workCalendar.markExtrdays(${extrdays});
		workCalendar.markOffdays(${offdays});
		workCalendar.markNow();
	});
	
	
	function doBtnSelectYearAction() {
		var sy = $("#selectYear").val();
		
		$("#m-main").empty();
		$("#yearTitle").html(sy + '年度公司考勤日历');
		
		
		//请求后台数据
		$.post("oa/hr/workcal/getYearData", {year:sy}, function(d) {
			if(d.success) {
				
				var wd = d.attributes.workdays;
				var hd = d.attributes.holidays;
				var ed = d.attributes.extrdays;
				var of = d.attributes.offdays;
				workCalendar = new WorkCalendar(sy);
				workCalendar.render('#m-main');
				workCalendar.markWorkdays(wd);
				workCalendar.markHolidays(hd);
				workCalendar.markExtrdays(ed);
				workCalendar.markOffdays(of);
				workCalendar.markNow();
			}
		});
		
		
	}
</script>
</html>