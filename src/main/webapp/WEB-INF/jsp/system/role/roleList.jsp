<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,jqgrid,treeview,icheck"></t:base>
</head>
<body class="gray-bg">
	<!-- 页面部分 -->
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-7">
				<div class="row">
					<div class="col-sm-12" id="searchGroupId">
					</div>
				</div>
				<div class="ibox">
					<div class="ibox-content">
						<div id="tableContentId" class="jqGrid_wrapper"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
			 	<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>菜单列表</h5>
						<div class="ibox-tools" style="margin-top: -2px;">
                            <a class="btn btn-primary btn-bitbucket btn-xs" onclick="doBtnSaveAction();">
								<i class="fa fa-save"></i>
							</a>
                        </div>
					</div>
					<div class="ibox-content">
						<p>
                        	<button class="btn btn-primary " type="button" onclick="selectAll();"><i class="fa fa-check-square-o"></i>&nbsp;全选</button>
                        	<button class="btn btn-primary " type="button" onclick="unSelectAll();"><i class="fa fa-circle-o"></i>&nbsp;取消</button>
                        </p>
						<div id="select-menus"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="ibox-title">
					<h5>操作按钮列表</h5>
					<div class="ibox-tools" style="margin-top: -2px;">
                            <a class="btn btn-primary btn-bitbucket btn-xs" onclick="doBtnSaveBtnsAction();">
								<i class="fa fa-save"></i>
							</a>
                        </div>
				</div>
				<div class="ibox-content" id="btnPannels">
				</div>
			</div>
			
			<!-- 隐藏字段，保存角色ID -->
			<input type="hidden" id="roleId" name="roleId" value="">
			<!-- 隐藏字段，保存菜单界面ID -->
			<input type="hidden" id="functionId" name="functionId" value="">
		</div>
	</div>
	
	
	<!-- 脚本部分 -->
	<t:datagrid actionUrl="sys/role/treeDataGrid" treeGrid="true" treeColomnName="roleName" tableContentId="tableContentId" fit="true" caption="角色管理" name="table_role_list" pageSize="1000" height="600px" >
		<t:dgCol name="id" label="编号" hidden="true" key="true" width="20"></t:dgCol>
		<t:dgCol name="roleName" label="角色名称" width="120"></t:dgCol>
		<t:dgCol name="roleCode" label="角色编码" width="80"></t:dgCol>
		<t:dgCol name="orderNo" label="角色排序" width="80"></t:dgCol>
		<t:dgCol name="description" label="角色描述" width="180"></t:dgCol>
		<t:dgCol name="opt" label="操作" width="150"></t:dgCol>
		<t:dgDelOpt label="删除" url="sys/role/del?id={id}" operationCode="sys:role:del"/>
		<t:dgFunOpt label="设置权限" funName="setfunbyrole(id)" icon="fa fa-check" operationCode="sys:role:view"></t:dgFunOpt>
		<t:dgToolBar url="sys/role/addorupdate" type="add" width="60%" operationCode="sys:role:add"></t:dgToolBar>
		<t:dgToolBar url="sys/role/addorupdate" type="edit" width="60%" operationCode="sys:role:edit"></t:dgToolBar>
	</t:datagrid>
	
	<script type="text/javascript">
		//设置权限
		//在菜单列表中，生成树形结构
		function setfunbyrole(id) {
			//赋值隐藏字段，以便保存的时候获取
			$("#roleId").val(id);
			
			//请求后台数据，获取菜单的树形接口
			$.post("common/getMenusTreeDate", {roleId : id}, function(d) {
				if(d.success) {
					var treeData = d.attributes.data;
					var df = eval(treeData);
					$("#select-menus").treeview({
						showCheckbox : true,
						data : df,
						//节点被选中
						onNodeChecked: function(event, node) {
							//选中父节点展开
							if(node.state.checked){
								$("#select-menus").treeview('expandNode', [ node.nodeId, {silent: true } ]);
							}
							checkedChildren(node);
							//获得子节点
							//var childrens = node.nodes;
							//if(childrens) {
								//如果存在子节点，则全部勾选上
							//	for(var i = 0; i < childrens.length; i++) {
							//		var childNode = childrens[i];
							//		$("#select-menus").treeview('checkNode', [childNode.nodeId, { silent: true } ]);
							//	}
							//}
							var parent = $('#select-menus').treeview('getParent', node );
							//循环遍历
							while(parent){
								$("#select-menus").treeview('checkNode', [parent, { silent: true } ]);
								parent = $('#select-menus').treeview('getParent', parent );
							}
						},
						//节点被取消选中
						onNodeUnchecked: function(event, node) {
							unCheckedChildren(node);
							//获得子节点
							//var childrens = node.nodes;
							//if(childrens) {
								//如果存在子节点，则全部勾选上
							//	for(var i = 0; i < childrens.length; i++) {
							//		var childNode = childrens[i];
							//		$("#select-menus").treeview('uncheckNode', [childNode.nodeId, { silent: true } ]);
							//	}
							//}
							//判断父节点的状态，需要先判断同级别节点是否有没有被选中，递归
							checkBrothers(node);
						},
						//节点被选择
						onNodeSelected : function(event, node) {
							doShowButtonsAction(node.id);
						}
					}); 
				}
			});
		}
		
		//根据兄弟节点的选中状态，判断父节点的选中状态，递归调用方法
		function checkBrothers(node) {
			var brothers = $('#select-menus').treeview('getSiblings', node);
			if(brothers) {
				var isChecked = false;
				//如果存在兄弟节点
				for(var i = 0; i < brothers.length; i++) {
					var brother = brothers[i];
					if(brother.state.checked) {
						isChecked = true;
						break;
					};
				}
				if(!isChecked) {
					var parent = $('#select-menus').treeview('getParent', node );
					$("#select-menus").treeview('uncheckNode', [parent, { silent: true } ]);
					checkBrothers(parent);
				};
			};
		}
		
		//递归寻找子节点  选中
		function checkedChildren(node){
			if(node){
				//获得子节点
				var childrens = node.nodes;
				if(childrens) {
					//如果存在子节点，则全部勾选上
					for(var i = 0; i < childrens.length; i++) {
						var childNode = childrens[i];
						$("#select-menus").treeview('checkNode', [childNode.nodeId, { silent: true } ]);
						checkedChildren(childNode);
					}
				}
			}
		}
		
		//递归寻找子节点   取消选中
		function unCheckedChildren(node) {
			if(node) {
				//获得子节点
				var childrens = node.nodes;
				if(childrens) {
					//如果存在子节点，则全部勾选上
					for(var i = 0; i < childrens.length; i++) {
						var childNode = childrens[i];
						$("#select-menus").treeview('uncheckNode', [childNode.nodeId, { silent: true } ]);
						unCheckedChildren(childNode);
					}
				}
			}
		}
		
		//树形列表的全选
		function selectAll(){
			$("#select-menus").treeview('checkAll', { silent: true });

		}
		//树形列表的取消全选
		function unSelectAll() {
			$("#select-menus").treeview('uncheckAll', { silent: true });
		}
		//保存
		function doBtnSaveAction() {
			//获取角色ID
			var roleId = $("#roleId").val();
			
			//获得所有被选中的节点数组
			var nodes = $("#select-menus").treeview('getChecked');
			if(nodes) {
				//将被选中的节点ID，拼接成字符串传入后台
				var nodesIds = new Array();
				for(var i = 0; i < nodes.length; i++) {
					nodesIds.push(nodes[i].id);
				}
				var ids = nodesIds.join();
				$.post("sys/role/updateAuthority", {rolefunctions:ids, roleId : roleId}, function(d){
					if(d.success) {
						qhTipSuccess(d.msg);
					}else{
						qhTipAlert(d.msg);
					}
				});
			}
		}
		//显示出按钮
		function doShowButtonsAction(functionId) {
			//获取角色ID
			var roleId = $("#roleId").val();
			//赋值菜单界面ID
			$("#functionId").val(functionId);
			
			$.post("sys/role/getOperationListForFunction", {roleId:roleId, functionId : functionId}, function(d){
				if(d.success) {
					 //按钮集合
					 var operationList = d.attributes.operationList;
					 //具备权限的按钮集合
					 var operationCodes = d.attributes.operationCodes;
					 if(operationList) {
						 $("#btnPannels").empty();
						 var html = "";
						 for(var i = 0; i < operationList.length; i++) {
							 var operation = operationList[i];
							 //判断是否具有按钮的权限，有的话checked
							 if($.inArray(operation.code, operationCodes)!==-1) {
								 html += "<div class='checkbox i-checks'><label><input name='operationCheckbox' type='checkbox' value='" + operation.code + "' checked=''> <i></i> " + operation.name + "</label></div>";
							 }else {
								 html += "<div class='checkbox i-checks'><label><input name='operationCheckbox' type='checkbox' value='" + operation.code + "' > <i></i> " + operation.name + "</label></div>";
							 }
							 
						 }
						 $("#btnPannels").append(html);
						 
						 //刷新checkbox的样式
						 $(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});
					 }
				}else{
					qhTipAlert(d.msg);
				}
			});
		}
		//操作按钮的权限保存
		function doBtnSaveBtnsAction() {
			//获取角色ID
			var roleId = $("#roleId").val();
			//获取菜单界面ID
			var functionId = $("#functionId").val();
			
			var operationcodes = "";
			$("input[name='operationCheckbox']").each(function(i){
				   if(this.checked){
					   operationcodes+=this.value+",";
				   }
			 });
			
			$.post("sys/role/updateOperation", {roleId : roleId, functionId : functionId, operationcodes : operationcodes}, function(d){
				if(d.success) {
					qhTipSuccess(d.msg);
				}else{
					qhTipAlert(d.msg);
				}
			});
		}
		
	</script>
</body>
</html>