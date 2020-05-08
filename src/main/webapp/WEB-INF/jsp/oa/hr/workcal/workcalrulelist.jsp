<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,laydate"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12" id="workcalRuleGroupId"></div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="workcalRuleTable" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 脚本部门 -->
	<!-- 脚本部分 -->
	<t:datagrid actionUrl="oa/hr/workcal/datagrid" tableContentId="workcalRuleTable" searchGroupId="workcalRuleGroupId" fit="true" caption="工作日历规则" name="workcalRuleGroupIdList" pageSize="20" sortName="currentStrDate" sortOrder="desc">
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="year" label="年份" width="70" query="true"></t:dgCol>
		<t:dgCol name="currentDateTime" label="日期" width="110" query="true" queryModel="group" datefmt="yyyy-MM-dd" datePlugin="laydate"></t:dgCol>
		<t:dgCol name="needSign" label="是否需要签到" width="80" dictionary="byesorno" display="switch"></t:dgCol>
		<t:dgCol name="weekStr" label="星期" width="70"></t:dgCol>
		<t:dgCol name="startTime" label="上班时间" width="90"></t:dgCol>
		<t:dgCol name="endTime" label="下班时间" width="90"></t:dgCol>
		<t:dgCol name="type" label="类型" width="70" query="true" dictionary="oaworkcaltype"></t:dgCol>
		<t:dgToolBar label="初始化" icon="fa fa-cog" type="define" funName="initYear"></t:dgToolBar>
		<t:dgToolBar label="调整时间" icon="fa fa-clock-o" type="define" funName="editTime"></t:dgToolBar>
		<t:dgToolBar label="修改" icon="fa fa-paste" type="define" funName="editType"></t:dgToolBar>
	</t:datagrid>
	
	
	<script type="text/javascript">
		$(function(){
			laydate({elem:"#currentDateTime_begin",event:"focus",istime: true, format: 'YYYY-MM-DD'});
			laydate({elem:"#currentDateTime_end",event:"focus",istime: true, format: 'YYYY-MM-DD'});
		});
		
		//初始化
		function initYear() {
			popNo("workcalRuleGroupIdList", "oa/hr/workcal/goInitYear", "初始化考勤日历", "40%", "50%");
		}
		
		//调整时间
		function editTime() {
			popNo("workcalRuleGroupIdList", "oa/hr/workcal/goEditTime", "调整时间", "50%", "70%");
		}
		
		//修改
		function editType() {
			popNo("workcalRuleGroupIdList", "oa/hr/workcal/goEditType", "修改", "50%", "70%");
		}
	</script>
</body>
</html>
