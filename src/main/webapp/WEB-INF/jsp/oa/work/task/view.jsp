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
								<h5>任务显示</h5>
							</div>
							<div class="ibox-content">
								<div id="select-tasks"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-7">
						<div class="ibox float-e-margins">
							<div class="ibox-content">
								<div class="row">
									<div class="col-sm-12">
										<div class="m-b-md">
											<input type="hidden" id="id" name="id" value="${task.id }">
											<h2>${task.title }</h2>
										</div>
										<div class="col-sm-5">
											<dl class="dl-horizontal">
												<dt>状态：</dt>
												<dd id="statusSpan">
													<c:choose>
														<c:when test="${task.status == '0' }"><span class="label label-primary">新建</span></c:when>
														<c:when test="${task.status == '1' }"><span class="label label-info">进行中</span></c:when>
														<c:when test="${task.status == '2' }"><span class="label label-success">完成</span></c:when>
														<c:when test="${task.status == '3' }"><span class="label label-warning">未完成</span></c:when>
														<c:when test="${task.status == '4' }"><span class="label label-danger">放弃</span></c:when>
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
												<dt>任务内容：</dt>
												<dd id="content">${task.content}</dd>
											</dl>
										</div>
										<div class="col-sm-6">
											<dl class="dl-horizontal">
												<dt>任务分配人：</dt>
												<dd id="appointUserName">
													${task.appointUserName }
												</dd>
												<dt>任务责任人：</dt>
												<dd id="userName">
													${task.userName }
												</dd>
												<dt>任务监控人：</dt>
												<dd id="monitorUserName">
													${task.monitorUserName }
												</dd>
											</dl>
										</div>
										<div class="col-sm-6">
											<dl class="dl-horizontal">
												<dt>创建于：</dt>
												<dd id="createDate"><fmt:formatDate value="${task.createDate }" type="time" pattern="yyyy年MM月dd HH:mm"/></dd>
												<dt>任务安排时间：</dt>
												<dd id="startTime">
													<fmt:formatDate value="${task.startTime }" type="time" pattern="yyyy年MM月dd HH:mm"/>&nbsp;到
													<fmt:formatDate value="${task.endTime }" type="time" pattern="yyyy年MM月dd HH:mm"/>
												</dd>
												<dt>任务实际时间：</dt>
												<dd id="actStartTime">
													<fmt:formatDate value="${task.actStartTime }" type="time" pattern="yyyy年MM月dd HH:mm"/>&nbsp;到
													<fmt:formatDate value="${task.actEndTime }" type="time" pattern="yyyy年MM月dd HH:mm"/>
												</dd>
											</dl>
										</div>
										<div class="row">
											<input type="hidden" name="progress" id="progress" value="${task.progress }">
											<div class="col-sm-10" id="divprogress">
											</div>
										</div>
										<div class="row">
											<p style="margin-left: 40px;">
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="changeStartAction();">
													<i class="fa fa-file-o"></i>&nbsp;开始执行
												</button>
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="changeGiveUpAction();">
													<i class="fa fa-paste"></i>&nbsp;放弃任务
												</button>
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="addRecordAction();">
													<i class="fa fa-tasks"></i>&nbsp;任务回复
												</button>
												<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="addExcuteAction();">
													<i class="fa fa-tasks"></i>&nbsp;任务跟踪
												</button>
											</p>
										</div>
										<div class="row m-t-sm">
											<div class="col-sm-12">
												<div class="panel blank-panel">
													<div class="panel-heading">
														<div class="panel-options">
															<ul class="nav nav-tabs">
																<li class="active"><a href="#tab-1" data-toggle="tab">任务回复</a></li>
																<li class=""><a href="#tab-2" data-toggle="tab">任务跟踪</a>
                                                			</li>
															</ul>
														</div>
													</div>
				
													<div class="panel-body">
														<div class="tab-content">
															<div class="tab-pane active" id="tab-1">
															</div>
															 <div class="tab-pane" id="tab-2">
			                                                <table class="table table-striped">
			                                                    <thead>
			                                                        <tr>
			                                                            <th>开始时间</th>
			                                                            <th>结束时间</th>
			                                                            <th>进度</th>
			                                                            <th>说明</th>
			                                                        </tr>
			                                                    </thead>
			                                                    <tbody id="tab2body">
			                                                        <tr>
			                                                            <td>
			                                                                11月7日 22:03
			                                                            </td>
			                                                            <td>
			                                                                11月7日 20:11
			                                                            </td>
			                                                            <td class="project-completion">
								                                            <small>当前进度： 28%</small>
								                                            <div class="progress progress-mini">
								                                                <div style="width: 28%;" class="progress-bar"></div>
								                                            </div>
								                                        </td>
			                                                            <td>
			                                                                <p class="small">
			                                                                    	已经测试通过
			                                                                </p>
			                                                            </td>
			                                                        </tr>
			                                                    </tbody>
			                                                </table>
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
		$.post("oa/work/task/getTreeData", {id : id}, function(d) {
			if (d.success) {
				var treeData = d.attributes.data;
				var df = eval(treeData);
				$("#select-tasks").treeview({
					showCheckbox : false,
					data : df,
					//节点被选择
					onNodeSelected : function(event, node) {
						doBtnSelectionAction(node.id);
					}
				});
				//展开全部
				$("#select-tasks").treeview('expandAll', { silent: true });
				
			}
		});
		
		changeProgress();
		
		getRecord(id);
		
		getExcute(id);
	}
	
	function doBtnSelectionAction(id) {
		$.post("oa/work/task/getTask", {id:id}, function(d){
			if(d.success){
				var task = d.attributes.task;
				$("#id").val(task.id);
				
				//目标状态
				if('0' == task.status) {
					$("#statusSpan").html("<span class='label label-primary'>新建</span>");
				}else if('1' == task.status){
					$("#statusSpan").html("<span class='label label-info'>进行中</span>");
				}else if('2' == task.status){
					$("#statusSpan").html("<span class='label label-success'>完成</span>");
				}else if('3' == task.status){
					$("#statusSpan").html("<span class='label label-warning'>取消</span>");
				}else if('4' == task.status){
					$("#statusSpan").html("<span class='label label-danger'>放弃</span>");
				}else if('5' == task.status){
					$("#statusSpan").html("<span class='label label-danger'>超期</span>");
				}
				
				$("#content").html(task.content);
				
				$("#appointUserName").html(task.appointUserName);
				$("#userName").html(task.userName);
				$("#monitorUserName").html(task.monitorUserName);
				$("#startTime").html(new Date(task.startTime).QHformat('yyyy年MM月dd hh:mm') + "&nbsp;到" + new Date(task.endTime).QHformat('yyyy年MM月dd hh:mm'));
				if(null == task.actEndTime || '' == task.actEndTime ){
					if(null == task.actStartTime || '' == task.actStartTime) {
						$("#actStartTime").html("");
					}else {
						$("#actStartTime").html(new Date(task.actStartTime).QHformat('yyyy年MM月dd hh:mm'));
					}
				}else {
					$("#actEndTime").html(new Date(task.actStartTime).QHformat('yyyy年MM月dd hh:mm') + "&nbsp;到" + new Date(task.actEndTime).QHformat('yyyy年MM月dd hh:mm'));
				}
				
				
				
				
				
				if(null == task.createDate || '' == task.createDate){
				}else{
					$("#createDate").html(new Date(task.createDate).QHformat('yyyy年MM月dd hh:mm'));
				}
				
				$("#progress").val(task.progress);
				
				
				changeProgress();
				
				getRecord(id);
				
				getExcute(id);
			}
		});
	}
	
	//进度条的改变
	function changeProgress(){
		var progress = $("#progress").val();
		var percent = Number(progress);
		
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
	
	//开始执行任务
	function changeStartAction() {
		var id = $("#id").val();
		
		qhConfirm("你确定要开始该任务吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("oa/work/task/doChange", {id : id, status:'1'}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					
					$("#statusSpan").html("<span class='label label-info'>进行中</span>");
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//放弃任务
	function changeGiveUpAction() {
		var id = $("#id").val();
		
		qhConfirm("你确定要开始该任务吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("oa/work/task/doChange", {id : id, status:'4'}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					
					$("#statusSpan").html("<span class='label label-danger'>放弃</span>");
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	//添加回复
	function addRecordAction() {
		var id = $("#id").val();
		
		parent.layer.open({
			type : 2,
			title : '添加回复',
			shadeClose : true,
			shade : 0.8,
			area : [ '50%', '70%' ],
			content : 'oa/work/task/addRecord?id=' + id, 
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
				getRecord(id);
			}
		});
	}
	
	//获取回复内容
	function getRecord(id) {
		$.post("oa/work/task/getComments", {id: id}, function(o){
			if(o.success) {
				var lstExcutes = o.attributes.lstExcutes;
				var html = "";
				for(var i = 0; i < lstExcutes.length; i++) {
					var excute = lstExcutes[i];
					html += '<div class="social-feed-box">';
					html += '<div class="social-avatar">';
					html += '<a href="#" class="pull-left"> <img alt="image" src="'+  excute.userHeadImg +'"></a>';
					var dateTime = new Date(excute.replyDate).QHformat('yyyy-MM-dd hh:mm:ss');
					html += '<div class="media-body"><a href="#">' + excute.userName + '</a><small class="text-muted">' + dateTime + '</small></div></div>';
					html += '<div class="social-body"><p>' + excute.content  + '</p></div>';
				}
				$("#tab-1").empty();
				$("#tab-1").append(html);
				
			}
		});
	}
	
	//添加执行
	function addExcuteAction() {
		var id = $("#id").val();
		
		parent.layer.open({
			type : 2,
			title : '添加执行日志',
			shadeClose : true,
			shade : 0.8,
			area : [ '50%', '70%' ],
			content : 'oa/work/task/addExcute?id=' + id, 
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
				getExcute(id);
			}
		});
	}
	
	//获取执行内容
	function getExcute(id) {
		$.post("oa/work/task/getExcutes", {id: id}, function(o){
			if(o.success) {
				var lstExcutes = o.attributes.lstExcutes;
				var html = "";
				for(var i = 0; i < lstExcutes.length; i++) {
					var excute = lstExcutes[i];
					html += '<tr>';
					var startTime = new Date(excute.startTime).QHformat('MM月dd日 hh:mm');
					var endTime = new Date(excute.endTime).QHformat('MM月dd日 hh:mm');
					html += '<td>'+startTime+'</td><td>'+endTime+'</td>';
					html += '<td class="project-completion"><small>当前进度： ' + excute.progress + '%</small><div class="progress progress-mini"><div style="width: '+excute.progress+'%;" class="progress-bar"></div></div> </td>';
                    html += '<td><p class="small">'+excute.description+'</p></td>'; 
                    html += "</tr>";
				}
				$("#tab2body").empty();
				$("#tab2body").append(html);
				
			}
		});
	}
</script>

</html>

