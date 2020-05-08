<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,datetimePicker,jqgrid"></t:base>
 	<link rel="shortcut icon" href="favicon.ico"> <link href="static/builder/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="static/builder/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="static/builder/css/animate.min.css" rel="stylesheet">
    <link href="static/builder/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="static/builder/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="static/builder/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="static/builder/css/oaui.css" rel="stylesheet">

	<style>
        .droppable-active{background-color:#ffe!important}.tools a{cursor:pointer;font-size:80%}.form-body .col-md-6,.form-body .col-md-12{min-height:400px}.draggable{cursor:move}
    </style>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
		
		
			<div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>元素</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                           
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="alert alert-info">
                            拖拽左侧的表单元素到右侧区域，即可生成相应的HTML代码，表单代码，轻松搞定！
                        </div>
                        <form role="form" class="form-horizontal m-t">
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">文本框：</label>
                                <div class="col-sm-9">
                                    <input type="text" name="" class="form-control" placeholder="请输入文本">
                                    <span class="help-block m-b-none">说明文字</span>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">多行文本框：</label>
                                <div class="col-sm-9">
                                    <textarea type="text" name="" class="form-control" placeholder="请输入文本"></textarea>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">密码框：</label>
                                <div class="col-sm-9">
                                    <input type="password" class="form-control" name="password" placeholder="请输入密码">
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">下拉列表：</label>
                                <div class="col-sm-9">
                                    <select class="form-control" name="">
                                        <option>选项 1</option>
                                        <option>选项 2</option>
                                        <option>选项 3</option>
                                        <option>选项 4</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">文件域：</label>
                                <div class="col-sm-9">
                                    <input type="file" name="" class="form-control">
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">纯文本：</label>

                                <div class="col-sm-9">
                                    <p class="form-control-static">这里是纯文字信息</p>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">单选框：
                                </label>

                                <div class="col-sm-9">
                                    <label class="radio-inline">
                                        <input type="radio" checked="" value="option1" id="optionsRadios1" name="optionsRadios">选项1</label>
                                    <label class="radio-inline">
                                        <input type="radio" value="option2" id="optionsRadios2" name="optionsRadios">选项2</label>

                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">复选框：</label>

                                <div class="col-sm-9">
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="option1" id="inlineCheckbox1">选项1</label>
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="option2" id="inlineCheckbox2">选项2</label>
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="option3" id="inlineCheckbox3">选项3</label>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">切换按钮：
                                </label>

                                <div class="col-sm-9">
                                    <label class="toggle-switch switch-solid">
			                            <input type="checkbox" id="status" checked>
			                            <span></span>
			                        </label>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">日期选择：</label>

                                <div class="col-sm-9">
		                            <div class="input-group date">
		                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                                <input type="text" class="form-control">
		                            </div>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">省市级联动：</label>

                                <div class="col-sm-9">
	                            	<div class="distpicker">
	                            		<div class="form-group" style="margin:0px; padding-right:5px;float:left;">
	                            			<label class="sr-only" for="province10">Province</label>
	                            			<select class="form-control" id="province10">
	                            			</select>
	                            		</div>
	                            		<div class="form-group" style="margin:0px; padding-right:5px;float:left">
	                            			<label class="sr-only" for="city10">City</label>
	                            			<select class="form-control" id="city10">
	                            			</select>
	                            		</div>
	                            		<div class="form-group" style="margin:0px; padding-right:5px;float:left">
	                            			<label class="sr-only" for="district10">District</label>
	                            			<select class="form-control" id="district10">
	                            			</select>
	                            		</div>
	                            	</div>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group draggable">
                                <div class="col-sm-12 col-sm-offset-3">
                                    <button class="btn btn-primary" type="submit">保存</button>
                                    <button class="btn btn-white" type="submit">取消</button>
                                </div>
                            </div>
                        </form>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>拖拽左侧表单元素到此区域</h5>
                        <div class="ibox-tools">
                            请选择显示的列数：
                            <select id="n-columns">
                                <option value="1">显示1列</option>
                                <option value="2">显示2列</option>
                            </select>
                        </div>
                    </div>

                    <div class="ibox-content">
                        <div class="row form-body form-horizontal m-t">
                            <div class="col-md-12 droppable sortable">
                            </div>
                            <div class="col-md-6 droppable sortable" style="display: none;">
                            </div>
                            <div class="col-md-6 droppable sortable" style="display: none;">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning" data-clipboard-text="testing" id="copy-to-clipboard">复制代码</button>
                    </div>
                </div>
                
                
                
        	</div>
		</div>
	</div>
	
    <script src="static/builder/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="static/builder/js/content.min.js?v=1.0.0"></script>
    <script src="static/builder/js/jquery-ui-1.10.4.min.js"></script>
    <script src="static/builder/js/plugins/beautifyhtml/beautifyhtml.js"></script>
    <script src="static/builder/js/distpicker.data.min.js"></script>
    <script src="static/builder/js/distpicker.min.js"></script>
	<script>
        $(document).ready(function(){setup_draggable();$("#n-columns").on("change",function(){var v=$(this).val();if(v==="1"){var $col=$(".form-body .col-md-12").toggle(true);$(".form-body .col-md-6 .draggable").each(function(i,el){$(this).remove().appendTo($col)});$(".form-body .col-md-6").toggle(false)}else{var $col=$(".form-body .col-md-6").toggle(true);$(".form-body .col-md-12 .draggable").each(function(i,el){$(this).remove().appendTo(i%2?$col[1]:$col[0])});$(".form-body .col-md-12").toggle(false)}});$("#copy-to-clipboard").on("click",function(){var $copy=$(".form-body").clone().appendTo(document.body);$copy.find(".tools, :hidden").remove();$.each(["draggable","droppable","sortable","dropped","ui-sortable","ui-draggable","ui-droppable","form-body"],function(i,c){$copy.find("."+c).removeClass(c).removeAttr("style")});var html=html_beautify($copy.html());$copy.remove();$modal=get_modal(html).modal("show");$modal.find(".btn").remove();$modal.find(".modal-title").html("复制HTML代码");$modal.find(":input:first").select().focus();return false})});var setup_draggable=function(){$(".draggable").draggable({appendTo:"body",helper:"clone"});$(".droppable").droppable({accept:".draggable",helper:"clone",hoverClass:"droppable-active",drop:function(event,ui){$(".empty-form").remove();var $orig=$(ui.draggable);if(!$(ui.draggable).hasClass("dropped")){var $el=$orig.clone().addClass("dropped").css({"position":"static","left":null,"right":null}).appendTo(this);var id=$orig.find(":input").attr("id");if(id){id=id.split("-").slice(0,-1).join("-")+"-"+(parseInt(id.split("-").slice(-1)[0])+1);$orig.find(":input").attr("id",id);$orig.find("label").attr("for",id)}$('<p class="tools col-sm-12 col-sm-offset-3">						<a class="edit-link">编辑HTML<a> | 						<a class="remove-link">移除</a></p>').appendTo($el)}else{if($(this)[0]!=$orig.parent()[0]){var $el=$orig.clone().css({"position":"static","left":null,"right":null}).appendTo(this);$orig.remove()}}}}).sortable()};var get_modal=function(content){var modal=$('<div class="modal" style="overflow: auto;" tabindex="-1">	<div class="modal-dialog"><div class="modal-content"><div class="modal-header"><a type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</a><h4 class="modal-title">编辑HTML</h4></div><div class="modal-body ui-front">	<textarea class="form-control" 	style="min-height: 200px; margin-bottom: 10px;font-family: Monaco, Fixed">'+content+'</textarea><button class="btn btn-success">更新HTML</button></div>				</div></div></div>').appendTo(document.body);return modal};$(document).on("click",".edit-link",function(ev){var $el=$(this).parent().parent();var $el_copy=$el.clone();var $edit_btn=$el_copy.find(".edit-link").parent().remove();var $modal=get_modal(html_beautify($el_copy.html())).modal("show");$modal.find(":input:first").focus();$modal.find(".btn-success").click(function(ev2){var html=$modal.find("textarea").val();if(!html){$el.remove()}else{$el.html(html);$edit_btn.appendTo($el)}$modal.modal("hide");return false})});$(document).on("click",".remove-link",function(ev){$(this).parent().parent().remove()});
    	$(".input-group.date").datetimepicker({
    	   format: "yyyy-mm-dd",
    	   minView: "month",
    	   autoclose: true
    	 });
    	 //省市区初始化
         $('.distpicker').distpicker({
             province: '省份名',
             city: '城市名',
             district: '区名',
             autoSelect: true,
             placeholder: false
         });
    </script>

</body>