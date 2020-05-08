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
					<div class="ibox-content" style="min-height: 850px;">
						<t:formvalid action="wf/form/form/save">
							<input type="hidden" name="id" id="id" value="${form.id }">
							<div class="form-group">
								<div class="row">
	                                <label class="col-sm-1 control-label m-b">表单名称*：</label>
	                                <div class="col-sm-2 m-b">
	                                    <input id="name" name="name" type="text" class="form-control" required="" value="${form.name }">
	                                </div>
	                                <label class="col-sm-1 control-label m-b">表单标识*：</label>
	                                <div class="col-sm-2 m-b">
	                                    <input id="code" name="code" type="text" class="form-control" required="" value="${form.code }">
	                                </div>
	                                <label class="col-sm-1 control-label m-b">表单类别 *</label> 
	                                <div class="col-sm-2 m-b">
	                                	<select name="categoryId" class="form-control">
											<c:forEach items="${lstCatogorys }" var="c">
												<option value="${c.id }" <c:if test="${c.id == form.categoryId }">selected='selected'</c:if> >${c.name }</option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-1 control-label m-b">表单类型 *</label> 
	                                <div class="col-sm-2 m-b">
	                                	<t:dictSelect name="type" type="select" typeGroupCode="sysform" clickEvent="changeType();"></t:dictSelect>
									</div>
								</div>
								<div class="row">
									<label class="col-sm-1 control-label m-b">表单路径：</label>
	                                <div class="col-sm-5 m-b">
	                                    <input id="path" name="path" type="text" class="form-control" value="${form.path }">
	                                </div>
                                </div>
                            </div>
						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	//改变类型
	function changeType() {
	}


</script>
</html>

