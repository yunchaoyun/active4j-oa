/**
 * 点击图片，刷新验证码
 */
$('#randCodeImage').click(function(){
    reloadRandCodeImage();
});


/**
 * 刷新验证码
 */
function reloadRandCodeImage() {
    var date = new Date();
    var img = document.getElementById("randCodeImage");
    img.src='verCode?a=' + date.getTime();
}

/**
 * 登录表单提交
 */
function doSubmit() {
	if(!$("#signupForm").valid()) {
		return;
	}
	
	var userName = $("#userName").val();
	var password = $("#password").val();
	var randCode = $("#randCode").val();
	
	//登录
	$.post("login", {userName:userName, password:password, randCode:randCode}, function(data) {
		if(data.success) {
			toastr.success('登录成功');
			location.href = 'index'; //后台主页
		}else {
			toastr.warning(data.msg);
		}
	});
}

$.validator.setDefaults({
	highlight : function(e) {
		$(e).closest(".form-group").removeClass("has-success").addClass(
				"has-error");
	},
	success : function(e) {
		e.closest(".form-group").removeClass("has-error").addClass(
				"has-success");
	},
	errorElement : "span",
	errorPlacement : function(e, r) {
		e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent()
				.parent() : r.parent());
	},
	errorClass : "help-block m-b-none",
	validClass : "help-block m-b-none"
}), 

$(function() {
	// 提示信息初始化
	toastr.options = {
		"closeButton" : true,
		"debug" : false,
		"progressBar" : true,
		"positionClass" : "toast-top-center",
		"onclick" : null,
		"showDuration" : "400",
		"hideDuration" : "1000",
		"timeOut" : "2000",
		"extendedTimeOut" : "1000",
		"showEasing" : "swing",
		"hideEasing" : "linear",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut"
	};
	
	var e = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		onkeyup:false,
		rules : {
			userName : "required",
			password : {
				required : !0
			},
			randCode : {
				required : !0
			}
		},
		messages : {
			userName : {
				required : e + "请输入您的用户名",
			},
			password : {
				required : e + "请输入您的密码"
			},
			randCode : {
				required : e + "请输入验证码"
			}
		}
	}), $("#username").focus(function() {
		var e = $("#userName").val(), r = $("#password").val();
		e && r && !this.value && (this.value = e + "." + r);
	});
});
