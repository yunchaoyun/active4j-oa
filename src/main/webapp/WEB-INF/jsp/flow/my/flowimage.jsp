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
					<div class="ibox-content" >
						<embed src="wf/flow/deploy/imageProcess?id=${businessKey }" style="width: 100%; height: 100%">
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>