<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,xform"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="height: 800px;">
						<div id="__gef_container__" style="padding-left:5px;">
							<div id="__gef_palette__" style="float:left;width:260px;">
								<ul class="nav nav-tabs" id="myTab">
									<li class="active"><a href="#operation" data-toggle="tab">操作</a></li>
									<li><a href="#form" data-toggle="tab">表单</a></li>
									<li><a href="#prop" data-toggle="tab">属性</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="operation">
										<div>
											<div class="xf-pallete" title="label">
												<img src="static/xform/images/xform/new_label.png"> label
											</div>
											<div class="xf-pallete" title="textfield">
												<img src="static/xform/images/xform/new_input.png"> textfield
											</div>
											<div class="xf-pallete" title="password">
												<img src="static/xform/images/xform/new_secret.png"> password
											</div>
											<div class="xf-pallete" title="textarea">
												<img src="static/xform/images/xform/new_textarea.png"> textarea
											</div>
											<div class="xf-pallete" title="select">
												<img src="static/xform/images/xform/new_select.png"> select
											</div>
											<div class="xf-pallete" title="radio">
												<img src="static/xform/images/xform/new_item.png"> radio
											</div>
											<div class="xf-pallete" title="checkbox">
												<img src="static/xform/images/xform/new_itemset.png"> checkbox
											</div>
											<div class="xf-pallete" title="fileupload">
												<img src="static/xform/images/xform/new_upload.png"> fileupload
											</div>
											<div class="xf-pallete" title="datepicker">
												<img src="static/xform/images/xform/new_range.png"> datepicker
											</div>
											<div class="xf-pallete" title="userpicker">
												<img src="static/xform/images/xform/userpicker.png"> userpicker
											</div>
										</div>
									</div>
									<div class="tab-pane" id="form">
										<div class="popover" style="display:block;position:relative;">
											<h3 class="popover-title">title</h3>
											<div class="popover-content">
												<div id="xf-form-attribute" class="controls"></div>
											</div>
										</div>
									</div>
									<div class="tab-pane" id="prop">
										<div class="popover" style="display:block;position:relative;">
											<h3 class="popover-title">属性</h3>
											<div class="popover-content">
												<div id="xf-form-attribute" class="controls">
													<label> 名称 <input id="xFormName" type="text">
													</label> <label> 标识 <input id="xFormCode" type="text">
													</label>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="__gef_center__">
								<div id="__gef_toolbar__">
									<div style="width:50px;float:left;">&nbsp;</div>
									<div class="btn-group">
										<button class="btn" onclick="doSave()">save</button>
										<!--
			<button class="btn" onclick="alert(xform.doExport())">export</button>
			<button class="btn" onclick="doImport()">import</button>
-->
										<button class="btn" onclick="xform.addRow()">add row</button>
										<button class="btn" onclick="xform.removeRow()">remove row</button>
										<button class="btn" onclick="doChangeMode(this)">change to merge mode</button>
										<button class="btn" onclick="doMerge()">merge</button>
										<button class="btn" onclick="doSplit()">split</button>
									</div>
								</div>


								<div id="__gef_canvas__" style="overflow:auto;">
									<div id="xf-center" class="xf-center" unselectable="on">
										<div id="xf-layer-form" class="xf-layer-form">
											<form id="xf-form" action="#" method="post" class="controls"></form>
										</div>
										<div id="xf-layer-mask" class="xf-layer-mask"></div>
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

</html>

