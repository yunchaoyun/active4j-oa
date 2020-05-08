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
				<div class="ibox">
					<div class="ibox-content" style="text-align: center; min-height: 500px">
						<i class="fa fa-warning" style="font-size: 100px; margin-top:150px; margin-bottom:20px; color: yellow;"></i>
						<p>保存失败，错误信息:${message }, 请返回重新操作</p>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>