<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,treeview"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-5">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>目标显示</h5>
							</div>
							<div class="ibox-content">
								<div id="select-targets"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-7">
						<div class="ibox float-e-margins">
							<div class="ibox-content">
								<div class="row">
									<div class="col-sm-12">
										<div class="m-b-md">
											<input type="hidden" id="id" name="id" value="${target.id }">
											<h2>个人目标</h2>
										</div>
										<div class="col-sm-5">
											<dl class="dl-horizontal">
												<dt>状态：</dt>
												<dd id="statusSpan">
													<c:choose>
														<c:when test="${target.status == '0' }"><span class="label label-primary">新建</span></c:when>
														<c:when test="${target.status == '1' }"><span class="label label-info">进行中</span></c:when>
														<c:when test="${target.status == '2' }"><span class="label label-success">完成</span></c:when>
														<c:when test="${target.status == '3' }"><span class="label label-warning">未完成</span></c:when>
														<c:when test="${target.status == '4' }"><span class="label label-danger">放弃</span></c:when>
														<c:otherwise>
															其他
														</c:otherwise>
													</c:choose>
												</dd>
											</dl>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<dl class="dl-horizontal">
												<dt>目标内容：</dt>
												<dd id="content">${target.content}</dd>
											</dl>
										</div>
										<div class="col-sm-5">
											<dl class="dl-horizontal">
												<dt>目标类型：</dt>
												<dd id="type">
													<c:choose>
														<c:when test="${target.type == '0' }">公司目标</c:when>
														<c:when test="${target.type == '1' }">部门目标</c:when>
														<c:when test="${target.type == '2' }">个人目标</c:when>
													</c:choose>
												</dd>
												<dt>目标类别：</dt>
												<dd id="category">
													<c:choose>
														<c:when test="${target.category == '0' }">年度目标</c:when>
														<c:when test="${target.category == '1' }">季度目标</c:when>
														<c:when test="${target.category == '2' }">月度目标</c:when>
														<c:when test="${target.category == '3' }">每周目标</c:when>
														<c:when test="${target.category == '4' }">每日目标</c:when>
													</c:choose>
												</dd>
												<dt>目标值：</dt>
												<dd id="dataRecordSpan">
													<c:choose>
				                                		<c:when test="${target.dataRecord == '0'}">
				                                		</c:when>
				                                		<c:otherwise>
				                                			${target.dataRecord}
				                                			<c:choose>
				                                				<c:when test="${target.unit == '0'}">
				                                					%
				                                				</c:when>
				                                				<c:when test="${target.unit == '1'}">
				                                					万
				                                				</c:when>
				                                				<c:when test="${target.unit == '2'}">
				                                					个
				                                				</c:when>
				                                				<c:otherwise></c:otherwise>
				                                			</c:choose>
				                                		</c:otherwise>
				                                	</c:choose>
												</dd>
											</dl>
										</div>
										<div class="col-sm-5">
											<dl class="dl-horizontal">
												<dt>最后更新：</dt>
												<dd id="updateDate"><fmt:formatDate value="${target.updateDate }" type="time" pattern="yyyy年MM月dd HH:mm"/></dd>
												<dt>创建于：</dt>
												<dd id="createDate"><fmt:formatDate value="${target.createDate }" type="time" pattern="yyyy年MM月dd HH:mm"/></dd>
												<dt>已完成值：</dt>
												<dd id="finishRecordSpan">
													<c:choose>
				                                		<c:when test="${target.finishRecord < '0'}">
				                                		</c:when>
				                                		<c:otherwise>
				                                			${target.finishRecord}
				                                			<c:choose>
				                                				<c:when test="${target.unit == '0'}">
				                                					%
				                                				</c:when>
				                                				<c:when test="${target.unit == '1'}">
				                                					万
				                                				</c:when>
				                                				<c:when test="${target.unit == '2'}">
				                                					个
				                                				</c:when>
				                                				<c:otherwise></c:otherwise>
				                                			</c:choose>
				                                		</c:otherwise>
				                                	</c:choose>
												</dd>
											</dl>
										</div>
										<div class="row">
											<input type="hidden" name="dataRecord" id="dataRecord" value="${target.dataRecord }">
											<input type="hidden" name="finishRecord" id="finishRecord" value="${target.finishRecord }">
											<div class="col-sm-10" id="divprogress">
											</div>
										</div>
										<div class="row">
											<p style="margin-left: 40px;">
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="changeStartAction();">
													<i class="fa fa-file-o"></i>&nbsp;开始执行
												</button>
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="changeFinishAction();">
													<i class="fa fa-paste"></i>&nbsp;目标完成
												</button>
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="changeNoFinishAction();">
													<i class="fa fa-paste"></i>&nbsp;目标未完成
												</button>
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="changeGiveUpAction();">
													<i class="fa fa-paste"></i>&nbsp;放弃目标
												</button>
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="addRecordAction();">
													<i class="fa fa-tasks"></i>&nbsp;目标跟踪
												</button>
											</p>
										</div>
										<div class="row m-t-sm">
											<div class="col-sm-12">
												<div class="panel blank-panel">
													<div class="panel-heading">
														<div class="panel-options">
															<ul class="nav nav-tabs">
																<li><a href="javascript:void(0);" data-toggle="tab">目标执行</a></li>
															</ul>
														</div>
													</div>
				
													<div class="panel-body">
														<div class="tab-content">
															<div class="tab-pane active" id="tab-1">
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
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
		initData();
	});

	//初始化页面数据
	function initData() {
		var id = $("#id").val();

		//请求后台数据，获取目标的树形结构
		$.post("oa/work/target/getTreeData", {id : id}, function(d) {
			if (d.success) {
				var treeData = d.attributes.data;
				var df = eval(treeData);
				$("#select-targets").treeview({
					showCheckbox : false,
					data : df,
					//节点被选择
					onNodeSelected : function(event, node) {
						doBtnSelectionAction(node.id);
					}
				});
				//展开全部
				$("#select-targets").treeview('expandAll', { silent: true });
				
			}
		});
		
		changeProgress();
		
		getRecord(id);
	}
	
	function getRecord(id) {
		$.post("oa/work/target/getRecord", {id: id}, function(o){
			if(o.success) {
				var lstExcutes = o.attributes.lstExcutes;
				var html = "";
				for(var i = 0; i < lstExcutes.length; i++) {
					var excute = lstExcutes[i];
					html += '<div class="social-feed-box">';
					html += '<div class="social-avatar">';
					html += '<a href="#" class="pull-left"> <img alt="image" src="'+  excute.userHeadImg +'"></a>';
					var dateTime = new Date(excute.createDate).QHformat('yyyy-MM-dd hh:mm:ss');
					html += '<div class="media-body"><a href="#">' + excute.userName + '</a><small class="text-muted">' + dateTime + '</small></div></div>';
					if(excute.unit == '0'){
						html += '<div class="social-body"><p>' + excute.content  + '</p><h5>实时进度：' + excute.finishRecord + '%</h5></div>';
					}else if(excute.unit == '1'){
						html += '<div class="social-body"><p>' + excute.content  + '</p><h5>实时进度：' + excute.finishRecord + '万</h5></div>';
					}else if(excute.unit == '2'){
						html += '<div class="social-body"><p>' + excute.content  + '</p><h5>实时进度：' + excute.finishRecord + '个</h5></div>';
					}
					
				}
				$("#tab-1").empty();
				$("#tab-1").append(html);
				
			}
		});
	}
	
	//目标的树形结构被选中方法
	function doBtnSelectionAction(id) {
		$.post("oa/work/target/getTarget", {id:id}, function(d){
			if(d.success){
				var target = d.attributes.target;
				$("#id").val(target.id);
				
				//目标状态
				if('0' == target.status) {
					$("#statusSpan").html("<span class='label label-primary'>新建</span>");
				}else if('1' == target.status){
					$("#statusSpan").html("<span class='label label-info'>进行中</span>");
				}else if('2' == target.status){
					$("#statusSpan").html("<span class='label label-success'>完成</span>");
				}else if('3' == target.status){
					$("#statusSpan").html("<span class='label label-warning'>未完成</span>");
				}else if('4' == target.status){
					$("#statusSpan").html("<span class='label label-danger'>放弃</span>");
				}
				
				$("#content").html(target.content);
				
				//目标类型
				if('0' == target.type) {
					$("#type").html("公司目标");
				}else if('1' == target.type){
					$("#type").html("部门目标");
				}else if('2' == target.type){
					$("#type").html("个人目标");
				}
				
				//目标类别
				if('0' == target.category) {
					$("#category").html("年度目标");
				}else if('1' == target.category){
					$("#category").html("季度目标");
				}else if('2' == target.category){
					$("#category").html("月度目标");
				}else if('3' == target.category){
					$("#category").html("每周目标");
				}else if('4' == target.category){
					$("#category").html("每日目标");
				}
				
				if(null == target.updateDate || '' == target.updateDate){
				}else{
					$("#updateDate").html(new Date(target.updateDate).QHformat('yyyy年MM月dd hh:mm'));
				}
				if(null == target.createDate || '' == target.createDate){
				}else{
					$("#createDate").html(new Date(target.createDate).QHformat('yyyy年MM月dd hh:mm'));
				}
				
				$("#dataRecord").val(target.dataRecord);
				$("#finishRecord").val(target.finishRecord);
				
				if(target.dataRecord > 0) {
					if(target.unit == '0') {
						$("#dataRecordSpan").html(target.dataRecord + '%');
						$("#finishRecordSpan").html(target.finishRecord + '%');
					}else if(target.unit == '1'){
						$("#dataRecordSpan").html(target.dataRecord + '万');
						$("#finishRecordSpan").html(target.finishRecord + '万');
					}else if(target.unit == '2'){
						$("#dataRecordSpan").html(target.dataRecord + '个');
						$("#finishRecordSpan").html(target.finishRecord + '个');
					}
				}else{
					$("#dataRecordSpan").html('');
					$("#finishRecordSpan").html('');
				}
				
				
				changeProgress();
				
				getRecord(id);
			}
		});
	}
	
	function changeProgress() {
		var dataRecord = $("#dataRecord").val();
		var finishRecord = $("#finishRecord").val();
		if(null == dataRecord || '' == dataRecord || '0' == dataRecord) {
			$("#divprogress").empty();
		}else {
			if(null == finishRecord || '' == finishRecord) {
				finishRecord = '0';
			}
			var percent = Number(finishRecord) / Number(dataRecord) * 100;
			
			$("#divprogress").empty();
			var html = '<dl class="dl-horizontal">';
			html += '<dt>当前进度：</dt>';
			html += '<dd>';
			html += '<div class="progress progress-striped active m-b-sm">';
			html += '<div style="width: '+ percent +'%;" class="progress-bar"></div>';
			html += '</div>';
			html += '<small>当前已完成总进度的 <strong>' + percent + '%</strong></small>';
			html += '</dd>';
			html += '</dl>';
			
			$("#divprogress").append(html);
		}
	}
	
	//开始执行目标
	function changeStartAction() {
		var id = $("#id").val();
		
		qhConfirm("你确定要开始该目标吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("oa/work/target/doChange", {id : id, status:'1'}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					
					$("#statusSpan").html("<span class='label label-info'>进行中</span>");
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//目标已完成
	function changeFinishAction() {
		var id = $("#id").val();
		qhConfirm("你确定已经完成该目标吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("oa/work/target/doChange", {id : id, status:'2'}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					
					$("#statusSpan").html("<span class='label label-success'>完成</span>");
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//目标未完成
	function changeNoFinishAction() {
		var id = $("#id").val();
		qhConfirm("你确定该目标没有完成吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("oa/work/target/doChange", {id : id, status:'3'}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					
					$("#statusSpan").html("<span class='label label-warning'>未完成</span>");
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//放弃目标
	function changeGiveUpAction() {
		var id = $("#id").val();
		qhConfirm("你确定要放弃该目标吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("oa/work/target/doChange", {id : id, status:'4'}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					
					$("#statusSpan").html("<span class='label label-danger'>放弃</span>");
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//添加记录
	function addRecordAction() {
		var id = $("#id").val();
		
		var statusStr = $("#statusSpan").html();
		if(statusStr == '新建') {
			qhTipInfo("请先开始该目标");
		}
		
		parent.layer.open({
			type : 2,
			title : '添加记录',
			shadeClose : true,
			shade : 0.8,
			area : [ '60%', '70%' ],
			content : 'oa/work/target/addRecord?id=' + id, 
			btn : [ '确定', '取消' ],
			yes : function(index, layero) {
				//确定按钮回调
				//表单提交
				parent.frames['layui-layer-iframe' + index].submitL();
				qhTipSuccess("操作成功");
				
			},
			btn2 : function(index, layero) {
				//取消按钮回调
				
			},
			end: function() {
				doBtnSelectionAction(id);
			}
		});
	}
</script>

</html>