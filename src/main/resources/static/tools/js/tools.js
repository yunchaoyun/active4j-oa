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
});

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
});

/** **********************************layer弹出框start*********************************************** */

// 提示框
function qhAlert(msg) {
	parent.layer.alert(msg);
}

// 询问框
function qhConfirm(msg, yesFunction, noFunction, option) {
	parent.layer.confirm(msg, option, yesFunction, noFunction);
}

// 右下角弹出框
function qhTip(msg, title, endFunction, width, height) {
	if (null == width || '' == width) {
		width = '340px';
	}
	if (null == height || '' == height) {
		height = '215px';
	}
	parent.layer.open({
		type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
		title : title, // 标题
		closeBtn : 1,
		shade : [ 0 ],
		area : [ width, height ],
		offset : 'rb', // 右下角弹出
		time : 4000, // 2秒后自动关闭
		shift : 2,
		content : msg, // iframe的url，no代表不显示滚动条
		end : endFunction
	});
}

/** **********************************layer弹出框end*********************************************** */

/** **********************************jqgrid表格公用方法start****************************************** */
// 公用删除按钮方法
function delObj(id, url, msg) {
	if (null != msg && '' != msg) {
		msg = "你确定要删除该记录吗?";
	}
	qhConfirm(msg, function(index) {
		// 关闭询问
		parent.layer.close(index);

		var paramsData = new Object();
		// 拆分url 参数 以便可以进行post提交
		if (url.indexOf("?") != -1) {
			var str = url.substr(url.indexOf("?") + 1);
			url = url.substr(0, url.indexOf("?"));
			var strs = str.split("?");
			for (var i = 0; i < strs.length; i++) {
				paramsData[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
			}
		}

		// 执行操作
		$.post(url, paramsData, function(data) {
			if (data.success) {
				// 成功提示
				qhTipSuccess(data.msg);
				// 刷新表格
				reloadTable(id);
			} else {
				// 失败提示
				qhTipWarning(data.msg);
			}
		});
	}, function() {

	});

}

// 新增弹出页面
function addQh(id, url, width, height, params) {
	width = width ? width : '90%';
	height = height ? height : '90%';
	// 参数处理
	if (params) {
		// 拼接参数
		var ps = params.split(",");
		if (ps) {
			var pUrl = "";
			for (var i = 0; i < ps.length; i++) {
				var param = ps[i];
				var p = $("#" + param).val();
				pUrl = param + "=" + p + "&" + pUrl;
			}
			url = url + "?" + pUrl;
		} else {
			return;
		}
	}
	parent.layer.open({
		type : 2,
		title : '新增',
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			//确定按钮回调
			//表单提交
			parent.frames['layui-layer-iframe' + index].submitL();
			
		},
		btn2 : function(index, layero) {
			//取消按钮回调
			
		},
		end: function() {
			// 操作结束，刷新表格
			reloadTable(id);
		}
	});

}

//新增弹出页面
function addQhNo(id, url, width, height, params) {
	width = width ? width : '90%';
	height = height ? height : '90%';
	// 参数处理
	if (params) {
		// 拼接参数
		var ps = params.split(",");
		if (ps) {
			var pUrl = "";
			for (var i = 0; i < ps.length; i++) {
				var param = ps[i];
				var p = $("#" + param).val();
				pUrl = param + "=" + p + "&" + pUrl;
			}
			url = url + "?" + pUrl;
		} else {
			return;
		}
	}
	parent.layer.open({
		type : 2,
		title : '新增',
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		end: function() {
			// 操作结束，刷新表格
			reloadTable(id);
		}
	});

}


// 编辑弹出页面
function editQh(id, url, width, height, params) {
	var rowId = $('#' + id).jqGrid('getGridParam', 'selrow');
	if (!rowId) {
		qhAlert('请选择要编辑的项目');
		return;
	}
	width = width ? width : '90%';
	height = height ? height : '90%';

	// 添加参数，表格的ID
	url += '?id=' + rowId;

	if (params) {
		// 拼接参数
		var ps = params.split(",");
		if (ps) {
			var pUrl = "";
			for (var i = 0; i < ps.length; i++) {
				var param = ps[i];
				var p = $("#" + param).val();
				pUrl = param + "=" + p + "&" + pUrl;
			}
			url = url + "?" + pUrl;
		} else {
			return;
		}
	}

	parent.layer.open({
		type : 2,
		title : '编辑',
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			//确定按钮回调
			//表单提交
			parent.frames['layui-layer-iframe' + index].submitL();
			
			// 操作结束，刷新表格
			reloadTable(id);
			
		},
		btn2 : function(index, layero) {
			//取消按钮回调
			
		},
		end: function() {
			// 操作结束，刷新表格
			reloadTable(id);
		}
	});

}

//编辑弹出页面
function editQhNo(id, url, width, height, params) {
	var rowId = $('#' + id).jqGrid('getGridParam', 'selrow');
	if (!rowId) {
		qhAlert('请选择要编辑的项目');
		return;
	}
	width = width ? width : '90%';
	height = height ? height : '90%';

	// 添加参数，表格的ID
	url += '?id=' + rowId;

	if (params) {
		// 拼接参数
		var ps = params.split(",");
		if (ps) {
			var pUrl = "";
			for (var i = 0; i < ps.length; i++) {
				var param = ps[i];
				var p = $("#" + param).val();
				pUrl = param + "=" + p + "&" + pUrl;
			}
			url = url + "?" + pUrl;
		} else {
			return;
		}
	}

	parent.layer.open({
		type : 2,
		title : '编辑',
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		end: function() {
			// 操作结束，刷新表格
			reloadTable(id);
		}
	});

}

//查看弹出页面
function viewQh(id, url, width, height, params) {
	var rowId = $('#' + id).jqGrid('getGridParam', 'selrow');
	if (!rowId) {
		qhAlert('请选择要查看的项目');
		return;
	}
	width = width ? width : '90%';
	height = height ? height : '90%';

	// 添加参数，表格的ID
	url += '?id=' + rowId;

	if (params) {
		// 拼接参数
		var ps = params.split(",");
		if (ps) {
			var pUrl = "";
			for (var i = 0; i < ps.length; i++) {
				var param = ps[i];
				var p = $("#" + param).val();
				pUrl = param + "=" + p + "&" + pUrl;
			}
			url = url + "?" + pUrl;
		} else {
			return;
		}
	}

	parent.layer.open({
		type : 2,
		title : '查看',
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		end: function() {
			// 操作结束，刷新表格
			reloadTable(id);
		} 
	});

}


// 自定义弹出页面
function pop(id, url, title, width, height) {
	var rowId = $('#' + id).jqGrid('getGridParam', 'selrow');
	if (!rowId) {
		qhAlert('请选择要编辑的项目');
		return;
	}
	width = width ? width : '90%';
	height = height ? height : '90%';

	// 添加参数，表格的ID
	url += '?id=' + rowId;

	parent.layer.open({
		type : 2,
		title : title,
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			//确定按钮回调
			//表单提交
			parent.frames['layui-layer-iframe' + index].submitL();
			// 操作结束，刷新表格
			reloadTable(id);
		},
		btn2 : function(index, layero) {
			//取消按钮回调
			
		},
		end: function() {
			// 操作结束，刷新表格
			reloadTable(id);
		}
	});

}

//自定义弹出页面
function popNo(id, url, title, width, height) {
	parent.layer.open({
		type : 2,
		title : title,
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			//确定按钮回调
			//表单提交
			parent.frames['layui-layer-iframe' + index].submitL();
			// 操作结束，刷新表格
			reloadTable(id);
		},
		btn2 : function(index, layero) {
			//取消按钮回调
			
		},
		end: function() {
			// 操作结束，刷新表格
			reloadTable(id);
		}
	});

}

//自定义弹出页面
function read(id, url, title, width, height) {
	var rowId = $('#' + id).jqGrid('getGridParam', 'selrow');
	if (!rowId) {
		qhAlert('请选择要编辑的项目');
		return;
	}
	width = width ? width : '90%';
	height = height ? height : '90%';

	// 添加参数，表格的ID
	url += '?id=' + rowId;

	parent.layer.open({
		type : 2,
		title : title,
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		btn : [ '取消' ],
		cancel : function(index, layero) {
			//取消按钮回调
			reloadTable(id);
		}
	});
}

//自定义弹出页面
function docheck(id, url, title, width, height) {
	var rowId = $('#' + id).jqGrid('getGridParam', 'selrow');
	if (!rowId) {
		qhAlert('请选择要编辑的项目');
		return;
	}
	width = width ? width : '90%';
	height = height ? height : '90%';

	// 添加参数，表格的ID
	url += '?id=' + rowId;

	parent.layer.open({
		type : 2,
		title : title,
		shadeClose : true,
		shade : 0.8,
		area : [ width, height ],
		content : url, // iframe的url
		cancel : function(index, layero) {
			//取消按钮回调
			reloadTable(id);
		}
	});
}

// 刷新表格
function reloadTable(id) {
	try {
		$("#" + id).trigger("reloadGrid");
	} catch (ex) {

	}
}

/** **********************************jqgrid表格公用方法end****************************************** */

/** **********************************toastr使用方法start******************************************** */

// 成功提示
function qhTipSuccess(msg, title) {
	toastr.success(msg, title);
}

// 信息提示
function qhTipInfo(msg, title) {
	toastr.info(msg, title);
}

// 警告提示
function qhTipWarning(msg, title) {
	toastr.warning(msg, title);
}

// 错误提示
function qhTipError(msg, title) {
	toastr.error(msg, title);
}

/** **********************************toastr使用方法end******************************************** */

/** **********************************业务使用方法start******************************************** */

// 退出系统
function exit() {
	qhConfirm('你确定要退出系统吗?', function() {
		// 是
		location.href = 'logout';

	}, function() {
		// 否
	});
}

/** **********************************业务使用方法end******************************************** */

/** **********************************js日期格式化start******************************************** */
/** 
 * 时间对象的格式化; 
 */  
Date.prototype.QHformat = function(format) {  
    /* 
     * eg:format="yyyy-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" : this.getMonth() + 1, // month  
        "d+" : this.getDate(), // day  
        "h+" : this.getHours(), // hour  
        "m+" : this.getMinutes(), // minute  
        "s+" : this.getSeconds(), // second  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" : this.getMilliseconds()  
        // millisecond  
    }  
  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  
/** **********************************js日期格式化end******************************************** */


/************************************自定义分页栏start**********************************************/
//取自定义分页显示的最小页
function getMinPage(currentPage, totalPage) {
	if(currentPage <= 5 || totalPage <= 10) {
		return 1;	
	}else {
		return totalPage - 9;
	}
}

//取自定义分页显示的最大页
function getMaxPage(currentPage, totalPage) {
	if(totalPage <= 10) {
		return totalPage;
	}
	var diff = totalPage - currentPage;
	if(diff <= 5) {
		return totalPage;
	}else{
		if(currentPage <= 5) {
			return 10;
		}else {
			return currentPage + 5;
		}
	}
}

//拼接分页工具栏
function getPager(currentPage, totalPage) {
	var min = getMinPage(currentPage, totalPage);
	var max = getMaxPage(currentPage, totalPage);
	var html = "<div class='hr-line-dashed'></div><div class='text-center'><div class='btn-group'>";
	html += "<button class='btn btn-white' type='button' onclick=goPre();><i class='fa fa-chevron-left'></i></button>";
	for(var i = min; i <= max; i++) {
		if(currentPage == i) {
			html += "<button class='btn pagebtn btn-white active' onclick=goPage(" + i + ")>" + i + "</button>";
		}else {
			html += "<button class='btn btn-white' onclick=goPage(" + i + ")>" + i + "</button>";
		}
	}
	html += " <button class='btn btn-white' type='button' onclick=goNext(" + max + ")><i class='fa fa-chevron-right'></i></button>";
	html += "</div></div>";
	
	return html;
}

function goPre() {
	var i = $(".pagebtn").html();
	if(i <= 1) {
		goPage(1);
	}else {
		i = i - 1;
		goPage(i);
	}
}

function goNext(max) {
	var i = $(".pagebtn").html();
	if(i >= max) {
		goPage(max);
	}else {
		i = Number(i) + 1;
		goPage(i);
	}
}


/************************************自定义分页栏end***************************************************/


