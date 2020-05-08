<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,echarts"></t:base>
</head>
<body class="gray-bg">
		<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-4">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>用户目标列表</h5>
							</div>
							<div class="ibox-content">
								<form method="post" class="form-horizontal">
									<div class="form-group">
										<div class="row">
											<label class="col-sm-4 control-label">目标内容</label>
											<div class="col-sm-8 m-b">
												<input type="text" name="content" id="content" value="" class="form-control">
											</div>
											<label class="col-sm-4 control-label">目标类别</label>
											<div class="col-sm-8 m-b">
												<select class="form-control" name="category" id="category">
													<option value=""></option>
													<c:forEach items="${lstCategorys }" var="c">
														<option value="${c.key }">${c.value }</option>
													</c:forEach>
												</select>
											</div>
											<label class="col-sm-4 control-label">目标状态</label>
											<div class="col-sm-8 m-b">
												<select class="form-control" name="status" id="status">
													<option value=""></option>
													<c:forEach items="${lstStatus }" var="s">
														<option value="${s.key }">${s.value }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 text-right">
												<p>
													<button class="btn btn-info" type="button" onclick="searchAction();">
														<i class="fa fa-search"></i>&nbsp;&nbsp;查询
													</button>
													<button class="btn btn-info" type="button" style="margin-left:3px;" onclick="resetAction();">
														<i class="fa fa-refresh"></i>&nbsp;&nbsp;重置
													</button>
												</p>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12">
												<p>
													<button class="btn btn-primary" style="margin-left:3px;" type="button" onclick="doBtnViewAction();">
														<i class="fa fa-cog"></i>&nbsp;查看执行
													</button>
												</p>
											</div>
										</div>
									</div>
								</form>
								<div class="table-responsive" id="tablecontent"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="ibox-title">
							<h5>执行目标列表</h5>
						</div>
						<div class="ibox">
							<div class="ibox-content">
								<div id="echartsContainer" style="height: 600px"></div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- 隐藏字段 -->
	<input type="hidden" name="id" value="" id="id">

</body>
<script type="text/javascript">
$(function() {
	//表格数据显示
	goPage(1);

});

function goPage(currentPage) {
	var field = "id,content,status,category";
	var content = $("#content").val();
	var category = $("#category").val();
	var status = $("#status").val();
	$.post("oa/work/target/getPartinData",
					{
						page : currentPage,
						rows : 20,
						field : field,
						content : content,
						category : category,
						status : status
					},
					function(data) {
						var list = data.rows;
						var totalPage = data.totalPage;
						var pagerHtml = "";
						if (totalPage > 1) {
							pagerHtml = getPager(currentPage, totalPage);
						}

						var dataHtml = '<table class="table table-hover no-margins"><thead><tr><th>内容</th><th>类别</th><th>状态</th></tr></thead><tbody>';
						for (var i = 0; i < list.length; i++) {
							var d = list[i];
							dataHtml += '<tr onclick=changeId("' + d.id
									+ '");>';
							dataHtml += '<td>' + d.content + '</a>';
							if (d.category == '0') {
								dataHtml += '<td>年度目标</td>';
							} else if (d.category == '1') {
								dataHtml += '<td>季度目标</td>';
							} else if (d.category == '2') {
								dataHtml += '<td>月度目标</td>';
							} else if (d.category == '3') {
								dataHtml += '<td>每周目标</td>';
							} else if (d.category == '4') {
								dataHtml += '<td>每日目标</td>';
							}

							if (d.status == '0') {
								dataHtml += '<td><span class="label label-primary">新建<span></td>';
							} else if (d.status == '1') {
								dataHtml += '<td><span class="label label-info">进行中<span></td>';
							} else if (d.status == '2') {
								dataHtml += '<td><span class="label label-success">完成<span></td>';
							} else if (d.status == '3') {
								dataHtml += '<td><span class="label label-warning">未完成<span></td>';
							} else if (d.status == '4') {
								dataHtml += '<td><span class="label label-danger">放弃<span></td>';
							}

							dataHtml += '</tr>';
						}
						dataHtml += '</tbody>';
						$("#tablecontent").empty();
						$("#tablecontent").append(dataHtml);
						$("#tablecontent").append(pagerHtml);
					});
	
	initChart();
}

function initChart() {
	//基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echartsContainer'),
			'macarons');

	myChart.showLoading();
	
	$.post("oa/work/target/getChartData", {select:'2'}, function(o){
		myChart.hideLoading();
		var data = o.attributes.data;
		var chartData = eval(data);
		// 指定图表的配置项和数据
		var option = {
			title : {
				text : '正在执行目标',
				left : 'center'
			},
			tooltip : {
				formatter: function (info) {
					var name = info.name;
					return name;
	            }
			},
			legend : {
				show : false
			},
			series : [ {
				type : 'treemap',
				name : '正在执行目标',
				visibleMin : 300,
				itemStyle : {
					normal : {
						borderColor : '#fff'
					}
				},
			    leafDepth:1,
			    label: {
                    normal: {
                    	textStyle:{fontSize: 14}
                    }
	            },
				data: chartData
			} ]
		};
		
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
		
		myChart.on('dblclick', function (params) {
		   doBtnChartViewAction(params.data.id);
		});
	});
	
}

function changeId(id) {
	$("#id").val(id);
}

function searchAction() {
	goPage(1);
}

function resetAction() {
	$("#content").val('');
	$("#category").val('');
	$("#status").val('');
	goPage(1);
}

function doBtnViewAction() {
	var id = $("#id").val();
	if (null == id || '' == id) {
		qhTipWarning("请选择要编辑的数据");
		return;
	}
	parent.layer.open({
		type : 2,
		title : '查看执行',
		shadeClose : true,
		shade : 0.8,
		area : [ '85%', '90%' ],
		content : 'oa/work/target/view?id=' + id,
		end : function() {
			goPage(1);
		}
	});
}

function doBtnChartViewAction(id){
	if (null == id || '' == id) {
		qhTipWarning("请选择要编辑的数据");
		return;
	}
	parent.layer.open({
		type : 2,
		title : '查看执行',
		shadeClose : true,
		shade : 0.8,
		area : [ '85%', '90%' ],
		content : 'oa/work/target/view?id=' + id,
		end : function() {
			goPage(1);
		}
	});
}

</script>
</html>