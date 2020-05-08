<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,webuploader"></t:base>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h3>个人档案</h3>
				</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="oaUserInfoForm" action="">
						
				                <div class="tabs-container">
				                    <ul class="nav nav-tabs">
				                        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">账号信息</a>
				                        </li>
				                        <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">档案信息</a>
				                        </li>
				                        <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">成本信息</a>
				                        </li>
				                    </ul>
				                    <div class="tab-content">
				                    
				                        <div id="tab-1" class="tab-pane active">
				                        
				                            <div class="panel-body">
				                            <div class="col-sm-12">
                    							<div class="form-group">
					                                <label class="col-sm-1 control-label">真实姓名：</label>
					                                <div class="col-sm-2">
					                                	<p class="form-control-static">${oaUser.realName }</p>
					                                </div>
					                            </div>
					                            
					                            <div class="form-group">
                                                    
                                                      <label class="col-sm-1 control-label">手机号码：</label>
					                                  <div class="col-sm-2">
					                                 	 <p class="form-control-static">${oaUser.phoneNo }</p>
						                              </div>
						                              
						                              <label class="col-sm-1 control-label">办公电话：</label>
						                                <div class="col-sm-2">
						                                	<p class="form-control-static">${oaUser.officePhone }</p>
						                                </div>
						                                
						                              <label class="col-sm-1 control-label">常用邮箱：</label>
					                                <div class="col-sm-2">
					                                	<p class="form-control-static">${oaUser.email }</p>
					                                </div>
					                            </div>
					                            
					                            <div class="form-group">
					                                <label class="col-sm-1 control-label">上级姓名：</label>
					                                <div class="col-sm-2">
					                                    <div class="input-group">
					                                    	<p class="form-control-static">${oaUser.chiefName }</p>
					                                    </div>
					                                </div>
					                                
					                                <label class="col-sm-1 control-label">部门：</label>
					                                <div class="col-sm-2">
					                                    <div class="input-group">
					                                    	<p class="form-control-static">${parentDepartName }</p>
					                                    </div>
				                                	</div>
				                                	
				                                	 <label class="col-sm-1 control-label">岗位：</label>
					                                 <div class="col-sm-2">
					                                    <div class="input-group">
					                                    	<p class="form-control-static">${parentJobName }</p>
					                                    </div>
					                                 </div>		
					                            </div>
					                            
				                            </div>
				                        </div>
				                       </div> 
				                        
				                        <div id="tab-2" class="tab-pane">
				                            <div class="panel-body">
				                            <div class="col-sm-10">
				                            
				                            	<div class="form-group">
					                          		<label class="col-sm-2 control-label">英文名:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.englishName }</p>
					                                </div>
					                                <label class="col-sm-2 control-label">身份证号:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.idCard }</p>
					                                </div>

												</div>
												
												<div class="form-group">
					                                <label class="col-sm-2 control-label">出生日期:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static"><fmt:formatDate value="${oaUser.birthDate }" type="date" pattern="yyyy-MM-dd"/></p>
					                                </div>
													<label class="col-sm-2 control-label">性别:</label>
						                             <div class="col-sm-4">
						                             		<p class="form-control-static">${sex }</p>
						                             </div>
												</div>
					                                
						                        
				                            	<div class="form-group">
					                                <label class="col-sm-2 control-label">国家:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.country }</p>
					                                </div>
					                                <label class="col-sm-2 control-label">民族:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.nation }</p>
					                                </div>
					                           </div>  
					                           
                  				               <div class="form-group">
						                           <label class="col-sm-2 control-label">血型:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.bloodType }</p>
					                                </div>
						                           	<label class="col-sm-2 control-label">婚姻状况:</label>
						                             <div class="col-sm-4">
					                             		<p class="form-control-static">${canMarriage }</p>
						                             </div>
					                          		
					                           </div>
					                           
					                           <div class="form-group">
					                              	<label class="col-sm-2 control-label">户口类型:</label>
						                             <div class="col-sm-4">
						                                <p class="form-control-static">${accountType }</p>
						                             </div>
						                             <label class="col-sm-2 control-label">政治面貌:</label>
						                             <div class="col-sm-4">
						                                <p class="form-control-static">${political }</p>
						                             </div>
					                           </div>
					                           
                                       			<div class="form-group">
                                       				<label class="col-sm-2 control-label">微信号:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.weixin }</p>
					                                </div>
					                                <label class="col-sm-2 control-label">QQ:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.qq }</p>
					                                </div>
					                           </div> 
					                           
                                               <div class="form-group">
						                           	<label class="col-sm-2 control-label">户口地区:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.residence }</p>
						                             </div>
						                           	<label class="col-sm-2 control-label">最高学历:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${highestEducation }</p>
						                             </div>
					                          		
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">毕业学校:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.graduateSchool }</p>
						                             </div>
						                             <label class="col-sm-2 control-label">所学专业:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.major }</p>
						                             </div>
					                           </div>
					                              
                                               <div class="form-group">
                                               		<label class="col-sm-2 control-label">毕业时间:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static"><fmt:formatDate value="${oaUser.graduateDate }" type="date" pattern="yyyy-MM-dd"/></p>
					                                </div>
						                           	<label class="col-sm-2 control-label">培养方式:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${developType }</p>
						                             </div>
					                           </div> 

                                               <div class="form-group">
                                               		<label class="col-sm-2 control-label">第一外语:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.fstForeignLang }</p>
						                             </div>
						                           	<label class="col-sm-2 control-label">第二外语:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.secForeignLang }</p>
						                             </div>
					                           </div>
					                           
					                           <div class="form-group">
					                          		 <label class="col-sm-2 control-label">第三外语:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.thdForeignLang }</p>
						                             </div>
					                           		<label class="col-sm-2 control-label">其他语种:</label>
					                                <div class="col-sm-4">
					                                	<p class="form-control-static">${oaUser.otherLang }</p>
						                             </div>
					                           </div>

                                               <div class="form-group">
                                               		<label class="col-sm-2 control-label">证书类型:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${certificateType }</p>
						                             </div>
						                           	<label class="col-sm-2 control-label">职业证书:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.certificate }</p>
						                             </div>
					                           </div>
					                           
                                               <div class="form-group">
						                           	<label class="col-sm-2 control-label">首次参加工作时间:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static"><fmt:formatDate value="${oaUser.fstWorkDate }" type="date" pattern="yyyy-MM-dd"/></p>
					                                </div>
						                           	<label class="col-sm-2 control-label">工龄:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.workAge }</p>
						                             </div>
 					                          		
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">社保断档:</label>
													<div class="col-sm-4">
														<p class="form-control-static">${oaUser.socailShort }</p>
						                             </div>
						                             <label class="col-sm-2 control-label">上家雇主:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.lastEmploy }</p>
					                                </div>
					                           </div>
					                          
					                           	<div class="form-group">
						                           	<label class="col-sm-2 control-label">合同类型:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${contractType }</p>
						                             </div>
					                          		<label class="col-sm-2 control-label">合同年限:</label>
													<div class="col-sm-4">
														<p class="form-control-static">${oaUser.contractTerm }</p>
						                             </div>
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">合同编号:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.contractNo }</p>
					                                </div>
					                                <label class="col-sm-2 control-label">合同开始日期:</label>
						                             <div class="col-sm-4">
						                             	<p class="form-control-static">${oaUser.contractStartDate }</p>
					                                </div>
					                           </div>
 												
					                           <div class="form-group">
					                          		<label class="col-sm-2 control-label">合同结束日期:</label>
													<div class="col-sm-4">
														<p class="form-control-static">${oaUser.contractEndDate }</p>
					                                </div>
					                           </div>
						                           
				                            </div>
				                            
				                            <div class="col-sm-2">
													<img id="imageUrl" alt="个人图片" class="img-responsive center-block" src="${oaUser.selfImage }" style="margin-top:2rem;width:12rem;height:13rem;border:1px solid #B2C5D9;">
														<!--dom结构部分-->
														<label class="control-label" style="margin-left: 6.8rem;">个人照片</label>
														
													<img id="idCardImageUrl" alt="身份证图片" class="img-responsive center-block" src="${oaUser.idCardImage }" style="margin-top:5rem;width:18rem;height:14rem;border:1px solid #B2C5D9;">
 														<label class="control-label" style="margin-left: 6.8rem;">身份证照片</label>
 														
 													<img id="certificateImageUrl" alt="证书图片" class="img-responsive center-block" src="${oaUser.certificateImage }" style="margin-top:5rem;width:10rem;height:10rem;border:1px solid #B2C5D9;">
				                            		<label class="control-label" style="margin-left: 6.8rem;">证书照片</label>
				                            </div>
				                            
				                            
				                            </div>
				                        </div>
				                        
				                        
                				        <div id="tab-3" class="tab-pane">
				                            <div class="panel-body">
					                            <div class="col-sm-12">
					                            
						                             <div class="form-group">
							                           	<label class="col-sm-2 control-label">成本类型:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${payType }</p>
						                                </div>	
							                           	<label class="col-sm-2 control-label">成本编号:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.payNo }</p>
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">基本工资:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.baseMoney }</p>
						                                </div>
						                                <label class="col-sm-2 control-label">绩效工资:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.performMoney }</p>
						                                </div>
						                           </div>
						                           
						                           
						                           <div class="form-group">
						                                <label class="col-sm-2 control-label">公积金号:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.fundNo }</p>
						                                </div>
							                           	<label class="col-sm-2 control-label">公司承担养老保险:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.companyAged }</p>
						                                </div>
						                           </div>
						                           
						                           	<div class="form-group">
							                           	<label class="col-sm-2 control-label">个人承担养老保险:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.personalAged }</p>
						                                </div>
						                                <label class="col-sm-2 control-label">公司承担公积金:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.companyFund }</p>
						                                </div>
						                                	
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">个人承担公积金:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.personalFund }</p>
						                                </div>
						                                <label class="col-sm-2 control-label">公司承担失业金:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.companyUnEmploy }</p>
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
							                           	<label class="col-sm-2 control-label">个人承担失业金:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.personalUnemploy }</p>
						                                </div>
						                                <label class="col-sm-2 control-label">公司承担医疗保险:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.companyMedical }</p>
						                                </div>	
						                           </div>
						                           
						                           <div class="form-group">
							                           	<label class="col-sm-2 control-label">个人承担医疗保险:</label>
							                             <div class="col-sm-4">
							                              	<p class="form-control-static">${oaUserPay.personalMedical }</p>
						                                </div>
							                           	<label class="col-sm-2 control-label">生育保险:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.fertility }</p>
						                                </div>
							                           	
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">工伤保险:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.occuInjury }</p>
						                                </div>
						                                <label class="col-sm-2 control-label">工资开户行:</label>
							                             <div class="col-sm-4">
							                              	<p class="form-control-static">${oaUserPay.payBank }</p>
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
							                           	<label class="col-sm-2 control-label">工资账号:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.wageNo }</p>
						                                </div>
							                           	<label class="col-sm-2 control-label">社保缴纳地:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.socialArea }</p>
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">社保号码:</label>
							                             <div class="col-sm-4">
							                             	<p class="form-control-static">${oaUserPay.socialNo }</p>
						                                </div>
						                           </div>
					                            
					                            </div> 
				                            
				                            </div>
				                        </div>
				                        
				                        
				                    </div>
				
				                </div>
						
						<div class="tabs-container" style="margin-top: 5rem">
			                    <ul class="nav nav-tabs">
			                        <li class="active"><a data-toggle="tab" href="#tab-4" aria-expanded="true">教育经历</a>
			                        </li>
			                        <li class=""><a data-toggle="tab" href="#tab-5" aria-expanded="false">工作经历</a>
			                        </li>
			                    </ul>
			                    
			                    <div class="tab-content">
			                    
			                        <div id="tab-4" class="tab-pane active">
			                            <div class="panel-body">
			                            	<div class="col-sm-12">

							                   <div class="ibox float-e-margins">
								                    <div class="ibox-content">
								
								                        <div class="table-responsive">
								                            <table class="table table-striped">
								                               <thead>
								                                    <tr>
								                                        <th>时间(从)</th>
								                                        <th>时间(到)</th>
								                                        <th>学校名称</th>
								                                    </tr>
								                                </thead>
								                                <tbody id="jiaoyu">
								                                
																	<c:forEach items="${userStudys }" var="m">
																		<tr id="jiaoyutr0">
									                                        
									                                        <td>
									                                        	<p class="form-control-static">${m.beforeDate }</p>
									                                        </td>
									                                        <td>
									                                        	<p class="form-control-static">${m.endDate }</p>
									                                        </td>
									                                        <td>
									                                        	<p class="form-control-static">${m.schoolName }</p>
									                                        </td>
								                                    	</tr>
																	</c:forEach>
																
								                                </tbody>
								                            </table>
								                        </div>
								                    </div>
								                </div>
			                            	
				                            </div>	
			                            </div>
			                        </div>
			                        
			                        
			                        <div id="tab-5" class="tab-pane">
			                            <div class="panel-body">
			                            	<div class="col-sm-12">

							                   <div class="ibox float-e-margins">
								                    <div class="ibox-content">
								
								                        <div class="table-responsive">
								                            <table class="table table-striped">
								                               <thead>
								                                    <tr>
								                                        <th>时间(从)</th>
								                                        <th>时间(到)</th>
								                                        <th>公司名称</th>
								                                        <th>担任职务</th>
								                                        <th>工作描述</th>
								                                    </tr>
								                                </thead>
								                                <tbody id="gongzuo">
								                                    
																		<c:forEach items="${userWorks }" var="m">
																			<tr id="gongzuotr0">
										                                        <td>
										                                        	<p class="form-control-static">${m.beforeWorkDate }</p>
										                                       	</td>
										                                        <td>
										                                        	<p class="form-control-static">${m.endWorkDate }</p>
										                                        </td>
										                                        <td>
										                                        	<p class="form-control-static">${m.companyName }</p>
										                                        </td>
										                                        <td>
										                                        	<p class="form-control-static">${m.job }</p>
										                                       	</td>
										                                        <td>
										                                        	<p class="form-control-static">${m.workInfo }</p>
										                                        </td>
																			</tr>
																		</c:forEach>

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
				          
						</form>
                    </div>
				</div>
			</div>
		</div>
</body>


</html>
