<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<c:forEach items="${mapResult }" var="m">
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h3>${m.key.name}</h3>
                    </div>
                    <div class="ibox-content">
                    	<div class="row">
                    		<c:forEach items="${m.value }" var="w" varStatus="s">
                    			<div class="col-sm-3" style="margin-bottom: 15px;line-height: 30px;">
                    				<h2 style="margin-bottom: 5px;"><i class="fa fa-share-alt"></i>&nbsp;&nbsp;<a href="wf/flow/center/go?id=${w.formId }&workflowId=${w.id}" class="J_menuItem2" data-index="400${s.index }">${w.workflowNo}/${w.name }</a></h2>
                        			<small style="margin-left: 35px;">by: ${w.createName }</small>
                    				<a class="btn btn-xs btn-primary" href="javascript:void(0);" onclick="doBtnPreviewAction('${w.processDefineId}');"><i class="fa fa-binoculars"></i> 预览</a>
                    				<a class="btn btn-xs btn-primary J_menuItem3" href="wf/flow/center/go?id=${w.formId }&workflowId=${w.id}" data-index="500${s.index }"><i class="fa fa-check"></i> 发起</a>
                    			</div>
                    		</c:forEach>
                    	</div>
                    </div>
                </div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
<script type="text/javascript">

//点击预览按钮
function doBtnPreviewAction(processDefineId) {

		parent.layer.open({
			type : 2,
			title : '预览',
			shadeClose : true,
			shade : 0.8,
			area : [ '80%', '90%'],
			content : "wf/flow/deploy/viewImage?id=" + processDefineId, // iframe的url
			btn : [ '取消' ],
			cancel : function(index, layero) {
				//取消按钮回调
				
			}
		});

}

</script>
</html>