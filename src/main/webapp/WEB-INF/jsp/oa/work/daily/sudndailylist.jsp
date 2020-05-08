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
            <div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>日程表</h5>
                    </div>
                    <div class="ibox-content">
                        <div id="calendar"></div>
                    </div>
                </div>
            </div>
            
    		<div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>下属人员日报</h5>
                    </div>
                    <div class="ibox-content">
                        <table class="table">
                            <tbody id="sudnlist"></tbody>
                        </table>
                    </div>
                </div>
            </div>
            
        	<div class="col-sm-4">
	        	<div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>下属人员日报</h5>
	                    </div>
					<div class="ibox-content" style="font-size: 13px" id = "sudndailylist">
						<!-- <div class="ibox-content"> -->
							<!-- <table class="table table-hover">
	                            <thead>
	                                <tr>
	                                    <th>标题</th><th>提交人</th><th>提交时间</th><th>操作</th>
	                                </tr>
	                            </thead>
	                            <tbody id = "sudndailylist">
	                            </tbody>
	                        </table> -->
					        
			        </div>
						<!-- </div> -->
					</div>
				</div>
           </div> 
           
		</div>
	</div>
</body>

<script type="text/javascript">

	$(function() {
		initCalendar();
	});
	
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
			defaultView : 'month',
			header : {
				left: 'prev,next today',
				center: 'title',
				right: 'month'
			},
			allDaySlot : false,
			editable: true,
			eventLimit: true,
			droppable: true,
			dayClick: function (date, allDay, jsEvent, view){
				var strdate = date.format("YYYY-MM-DD");
				doGetUnderAction(strdate);
			},
            events: function(start, end, timezone, callback) {
            	var strdate = new Date().QHformat('yyyy-MM-dd');
            	calInitEvents(strdate,callback);
            }
		});
	}
	
	//初始化日程表的数据
	function calInitEvents(strdate,callback) {
		
		doGetUnderAction(strdate);
	}
	
	function doGetUnderAction(strdate) {
		
		$("#sudndailylist").empty();
		
		//获取下属
		$.post("oa/work/daily/getUnderData", {strdate: strdate}, function(o){
			if(o.success){
				var lstData = o.attributes.lstData;
				
				var html = '';
				for(var i = 0; i < lstData.length; i++) {
					var cal = lstData[i];
					
					html += '<tr>';
					html += '<td>'+ cal.name +'</td>';
					
					if(cal.dailyStatus == '1'){
						html += '<td>已提交</td>';
						html += '<td><button type="button" class="btn btn-primary btn-xs" onclick="goView(' + "'" + cal.id + "'" +","+ "'" + cal.strDate + "'" +');">查看</button>';
						html += '</td>';
					} else if(cal.dailyStatus == '0') {
						html += '<td>未提交</td>';
						html += '<td><button type="button" class="btn btn-danger btn-xs" onclick="doWarn(' + "'" + cal.id + "'" +","+ "'" + cal.strDate + "'" +');">提醒</button></td>';
					}
					
					html += '</tr>';
				}
				
				$("#sudnlist").empty();
				$("#sudnlist").append(html);
		}else {
			qhTipError(o.msg);
		}
	});
	
	}
	
	//查看日报列表
	function goView(id,strdate) {
		
		//获取下属
		$.post("oa/work/daily/goView", {id : id, strdate: strdate}, function(o){
			if(o.success){
				var lstData = o.attributes.lstData;
				
				var html = '';
				for(var i = 0; i < lstData.length; i++) {
					var d = lstData[i];
					
					html += '<p><strong>标题：</strong>'+ d.dailyTitle +'</p>';
					html += '<p style="word-break:break-all; word-wrap:break-word ;"><strong>内容：</strong>'+ d.content +'</p>';
					html += '<p><strong>提交时间：</strong>'+ d.submitDate +'</p>';
					html += ' <p><strong>提交人：</strong>'+ d.submitName +'</p>';
				}
				
				$("#sudndailylist").empty();
				$("#sudndailylist").append(html);
		}else {
			qhTipError(o.msg);
		}
	});
	
	}
	
	//提醒
	function doWarn(id,strdate) {
		$.post("oa/work/daily/doWarn", {id : id, strdate: strdate}, function(o){
			if(o.success){
				qhTipSuccess(o.msg);
			}else {
				qhTipError(o.msg);
			}
		});
	
	}
	
	//查看详细弹出
	function viewAction(id) {
		parent.layer.open({
			type : 2,
			title : '我的日报',
			shadeClose : true,
			shade : 0.8,
			area : [ '50%', '80%' ],
			content : 'oa/work/daily/addorupdate&id='+id,
			yes : function(index, layero) {
				
			},
			btn2 : function(index, layero) {

			},
			end : function() {
				doBtnRefreshAction();
			}
		});
	}
	
</script>

</html>