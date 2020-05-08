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
						<form class="form-horizontal m-t" id="commonForm" action="flow/biz/my/delete" method="post">
							<input type="hidden" name="id" id="id" value="${baseId }">
	                            <div class="form-group">
	                                <label class="col-sm-3 control-label">删除理由：</label>
	                                <div class="col-sm-8">
	                                    <textarea id="reason" name="reason" class="form-control"></textarea>
	                                </div>
	                            </div>
	                    </form>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
//表单验证
	$(function() {
		$("#commonForm").validate({
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function(d) {
						if (d.success) {
							var url = d.obj;
							//再次请求删除
							$.post(url, {}, function(o) {
								if(o.success) {
									qhTipSuccess('保存成功');
									location.href='common/goSuccess';
								}else {
									qhWarning(o.msg);
								}
							})
						} else {
							qhTipWarning(d.msg);
						}
					},
					error : function(data) {
						qhTipError('系统错误，请联系系统管理员');
					}
				});
			}
		});
	});

	function submitL() {
		$("#commonForm").submit();
	}

</script>
</html>

