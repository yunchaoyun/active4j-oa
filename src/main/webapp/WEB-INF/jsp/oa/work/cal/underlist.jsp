<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,fullcalender2,jqueryUi,icheck"></t:base>

<style type="text/css">


</style>

</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>下属人员列表</h5>
                    </div>
                    <div class="ibox-content">
                    	<p>
							<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="doBtnNewAction();">
								<i class="fa fa-file-o"></i>&nbsp;新增
							</button>
						</p>
                        <div id='external-events'>
                            <p>下属人员列表</p>
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
	
	<!-- 隐藏字段 -->
	<input type="hidden" id="ids" name="ids" value="${ids }">
</body>

<script type="text/javascript">

	$(function() {
		init();
	});
	
	//初始化页面数据
	function init() {
		//左边待办事项
		$.post("oa/work/cal/getUnderData", {}, function(o){
			if(o.success){
				var lstData = o.attributes.lstData;
				
				var html = '<p>下属人员列表</p>';
				for(var i = 0; i < lstData.length; i++) {
					var cal = lstData[i];
					html += '<div class="external-event"><input type="checkbox" checked="checked" value="'+cal.id+'" class="i-checks" name="employers">'+ cal.name +'</div>';
				}
				
				$("#external-events").empty();
				$("#external-events").append(html);
				
				initCheck();
				
				//右边日程表
				initCalendar();
			}else {
				qhTipError(o.msg);
			}
		});
	}
	
	//check控件的效果
	function initCheck() {
		$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});
		
		$("input[name='employers']").on('ifChanged', function(event){ 
			  var ids = [];
			  $("input[name='employers']:checked").each(function(){ 
				  var v = $(this).val();
				  ids.push(v);
			  });
			  
			  if(ids.length > 0) {
				  var strIds = ids.join(",");
				  $("#ids").val(strIds);
			  }else {
				  $("#ids").val("");
			  }
			  $('#calendar').fullCalendar('refetchEvents');
		}); 
	}
	
	//初始化日程表
	function initCalendar(){
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
			droppable: false,
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
	function calInitEvents(start, end,callback) {
		var startDate = new Date(start).QHformat('yyyy-MM-dd hh:mm:ss');
		var endDate = new Date(end).QHformat('yyyy-MM-dd hh:mm:ss');
		var ids = $("#ids").val();
		$.post("oa/work/cal/getUnderCalEvent", {ids:ids,start:startDate,end:endDate}, function(o){
			if(o.success){
				var lstData = o.attributes.lstData;
				if(null != lstData) {
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
				 $('#calendar').fullCalendar('refetchEvents');
			}
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
				 $('#calendar').fullCalendar('refetchEvents');
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