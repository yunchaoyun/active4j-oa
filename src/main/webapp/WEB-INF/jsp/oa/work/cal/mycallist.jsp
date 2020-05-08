<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,fullcalender2,jqueryUi"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>待办事项</h5>
                    </div>
                    <div class="ibox-content">
                    	<p>
							<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="doBtnNewAction();">
								<i class="fa fa-file-o"></i>&nbsp;新增
							</button>
							<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="doBtnRefreshAction();">
								<i class="fa fa-refresh"></i>&nbsp;刷新
							</button>
						</p>
                        <div id='external-events'>
                            <p>待安排日程</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-9">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>日程表</h5>
                    </div>
                    <div class="ibox-content">
                        <div id="calendar"></div>
                    </div>
                </div>
            </div>
		</div>
	</div>
</body>

<script type="text/javascript">

	$(function() {
		init();
	});
	
	//初始化页面数据
	function init() {
		//左边待办事项
		$.post("oa/work/cal/getData", {}, function(o){
			if(o.success){
				var lstData = o.attributes.lstData;
				
				var html = '<p>待安排日程</p>';
				for(var i = 0; i < lstData.length; i++) {
					var cal = lstData[i];
					html += '<div data-id='+cal.id+' class="external-event navy-bg" style="width: 80%">'+ cal.title +'</div>';
					html += "<button class='btn btn-outline btn-primary btn-xs' style='float:right; margin-right: 3px; margin-top: -30px;'  title='删除' onclick=deleteAction('"+cal.id+"')><i class='fa fa-remove'></i></button>";
					html += "<button class='btn btn-outline btn-primary btn-xs' style='float:right; margin-right: 25px; margin-top: -30px;'  title='编辑' onclick=editAction('"+cal.id+"')><i class='fa fa fa-paste'></i></button>";
				}
				
				$("#external-events").empty();
				$("#external-events").append(html);
				
				//右边日程表
				initCalendar();
				
			}else {
				qhTipError(o.msg);
			}
		});
		
		
	}
	
	//初始化日程表
	function initCalendar(){
		//左边可拖拽事项
		$('#external-events .external-event').each(function() {
			$(this).data('event', {
				title: $.trim($(this).text()), 
				stick: true 
			});

			$(this).draggable({
				zIndex: 999,
				revert: true,      
				revertDuration: 0  
			});

		});
		
		$('#calendar').fullCalendar({
			defaultView : 'agendaWeek',
			header : {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay,listWeek'
			},
			allDaySlot : false,
			editable: true,
			eventLimit: true,
			droppable: true,
            eventDrop :function(event, delta, revertFunc, jsEvent, ui, view) {
            	var id = event.id;
            	var start = event.start.format("YYYY-MM-DD HH:mm:ss");
            	var end = event.end.format("YYYY-MM-DD HH:mm:ss");
            	updateEvent(id, start, end);	
            },
            eventResize:function(event, delta, revertFunc, jsEvent, ui, view){
            	var id = event.id;
            	var start = event.start.format("YYYY-MM-DD HH:mm:ss");
            	var end = event.end.format("YYYY-MM-DD HH:mm:ss");
            	updateEvent(id, start, end);	
            },
            eventClick: function(event, jsEvent, view) {
            	editAction(event.id);
            },
            drop: function(date) {
            	$(this).remove();
            	
            	var id = $(this).attr("data-id");
            	var start = date.format("YYYY-MM-DD HH:mm:ss");
            	updateEvent(id, start,null);
			},
			dayClick: function (date, allDay, jsEvent, view){
				doBtnNewAction();
			},
            events: function(start, end, timezone, callback) {
            	calInitEvents(start,end,callback);
            }
		});
	}
	
	//初始化日程表的数据
	function calInitEvents(start, end, callback) {
		var startDate = new Date(start).QHformat('yyyy-MM-dd hh:mm:ss');
		var endDate = new Date(end).QHformat('yyyy-MM-dd hh:mm:ss');
		$.post("oa/work/cal/getCalEvent", {start:startDate,end:endDate}, function(o){
			if(o.success){
				var lstData = o.attributes.lstData;
				var events = [];
				for(var i = 0; i < lstData.length; i++) {
					var cal = lstData[i];
					events.push({
                        title: cal.title,
                        start: cal.startTime,
                        end:cal.endTime,
                        id:cal.id
                    });
				}
				callback(events);
			}
		});
	}
	
	function doBtnNewAction() {
		parent.layer.open({
			type : 2,
			title : '添加日程',
			shadeClose : true,
			shade : 0.8,
			area : [ '50%', '80%' ],
			content : 'oa/work/cal/addorupdate',
			btn : [ '确定', '取消' ],
			yes : function(index, layero) {
				//确定按钮回调,表单提交
				parent.frames['layui-layer-iframe' + index].submitL();
				
			},
			btn2 : function(index, layero) {
				//取消按钮回调

			},
			end : function() {
				doBtnRefreshAction();
			}
		});
	}
	
	function doBtnRefreshAction() {
		$.post("oa/work/cal/getData", {}, function(o){
			if(o.success){
				var lstData = o.attributes.lstData;
				
				var html = '<p>待安排日程</p>';
				for(var i = 0; i < lstData.length; i++) {
					var cal = lstData[i];
					html += '<div data-id='+cal.id+' class="external-event navy-bg" style="width: 80%">'+ cal.title +'</div>';
					html += "<button class='btn btn-outline btn-primary btn-xs' style='float:right; margin-right: 3px; margin-top: -30px;'  title='删除' onclick=deleteAction('"+cal.id+"')><i class='fa fa-remove'></i></button>";
					html += "<button class='btn btn-outline btn-primary btn-xs' style='float:right; margin-right: 25px; margin-top: -30px;'  title='编辑' onclick=editAction('"+cal.id+"')><i class='fa fa fa-paste'></i></button>";
				}
				
				$("#external-events").empty();
				$("#external-events").append(html);
				
				//右边日程表
				initCalendar();
				
			}else {
				qhTipError(o.msg);
			}
		});
		
		$('#calendar').fullCalendar('refetchEvents');
	}
	
	function deleteAction(id) {
		qhConfirm("你确定要删除该日程吗?", function(index) {
			//关闭询问
			parent.layer.close(index);
			
			//是
			$.post("oa/work/cal/delete", {id : id}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
					
					doBtnRefreshAction();
				}
			}); 
			
		}, function() {
			//否
		});
	}
	
	function editAction(id) {
		parent.layer.open({
			type : 2,
			title : '添加日程',
			shadeClose : true,
			shade : 0.8,
			area : [ '50%', '80%' ],
			content : 'oa/work/cal/addorupdate?id='+id,
			btn : [ '确定', '取消' ],
			yes : function(index, layero) {
				//确定按钮回调,表单提交
				parent.frames['layui-layer-iframe' + index].submitL();
				
			},
			btn2 : function(index, layero) {
				//取消按钮回调

			},
			end : function() {
				doBtnRefreshAction();
			}
		});
	}
	
	function updateEvent(id, start, end){
		$.post("oa/work/cal/updateEvent", {id:id,start:start, end:end}, function(o){
			if(o.success){
						
			}else {
				qhTipError(o.msg);
			};
		});
	};
</script>

</html>