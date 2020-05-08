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
						<t:formvalid action="oa/hr/workcal/saveInitYear" beforeSubmit="bf();">
	                            <div class="form-group">
	                                <label class="col-sm-3 control-label">初始化年份：</label>
	                                <div class="col-sm-8">
	                                    <input id="year" name="year" minlength="4" maxlength="4" type="number" class="form-control m-b" required="">
	                                    <span class="m-b text text-danger">初始化当前年考勤日历规则，将会覆盖之前数据，请谨慎操作</span>
	                                </div>
	                            </div>
						</t:formvalid>
						 <div id="mypro" class="progress progress-striped active hidden">
                            <div style="width: 100%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="100" role="progressbar" class="progress-bar progress-bar-success">
                            </div>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

	function bf() {
		
		$("#mypro").removeClass("hidden");
		
		return true;
	}

</script>

</html>

