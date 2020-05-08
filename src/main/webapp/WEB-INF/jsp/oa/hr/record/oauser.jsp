<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default,select2,icheck,laydate,webuploader"></t:base>
<style>  
    #filePicker div:nth-child(2){width:100%!important;height:100%!important;}  
    #filePicker2 div:nth-child(2){width:100%!important;height:100%!important;}  
    #filePicker3 div:nth-child(2){width:100%!important;height:100%!important;}  
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			
				<div class="ibox float-e-margins">
				<div class="ibox-title">
				<h3>人事信息</h3>
				</div>
					<div class="ibox-content">
						<t:formvalid action="oa/hr/user/save">
						
							<input name="oaUserId" type="hidden" value="${oaUser.id}">
							<input type="hidden" name="selfImage" id="selfImage" value="${oaUser.selfImage }">
							<input type="hidden" name="idCardImage" id="idCardImage" value="${oaUser.idCardImage }">
							<input type="hidden" name="certificateImage" id="certificateImage" value="${oaUser.certificateImage }">
						
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
					                                <label class="col-sm-2 control-label">真实姓名*：</label>
					                                <div class="col-sm-4">
					                                    <input id="realName" name="realName" minlength="1" maxlength="50" type="text" class="form-control" required="" value="${oaUser.realName }">
					                                </div>
					                            </div>
					                            
					                            <div class="form-group">
                                                	  <label class="col-sm-2 control-label">手机号码：</label>
					                                  <div class="col-sm-4">
						                                  <input id="phoneNo" name="phoneNo" type="text" class="form-control" required="" value="${oaUser.phoneNo }">
						                              </div>
						                              
						                              <label class="col-sm-2 control-label">常用邮箱：</label>
					                                  <div class="col-sm-4">
					                                  	<input id="email" name="email" type="text" class="form-control" value="${oaUser.email }">
					                                  </div>
						                              
					                            </div>
					                            
					                            <div class="form-group">
					                            	<label class="col-sm-2 control-label">办公电话：</label>
						                            <div class="col-sm-4">
						                            	<input id="officePhone" name="officePhone" type="text" class="form-control" value="${oaUser.officePhone }">
						                            </div>
					                            
					                                <label class="col-sm-2 control-label">上级姓名：</label>
					                                <div class="col-sm-4">
					                                	<input id="chiefName" name="chiefName" type="text" class="form-control" value="${oaUser.chiefName }">
					                                </div>
					                                
					                            </div>
					                            
					                            <div class="form-group">
					                             	<label class="col-sm-2 control-label">部门*：</label>
					                                <div class="col-sm-5">
					                                    <div class="input-group">
					                                     	<t:choose url="common/selectDepart" hiddenName="deptId" hiddenValue="${oaUser.deptId }" textValue="${parentDepartName }" textName="departLabel" hiddenId="departId" textId="departLabel"></t:choose>
					                                    </div>
				                                	</div>
					                                    				                                
					                            </div>
					                            
					                            <div class="form-group">
					                                 <label class="col-sm-2 control-label">岗位*：</label>
					                                 <div class="col-sm-5">
					                                    <div class="input-group">
					                                   	  <t:choose url="common/selectJob" hiddenName="jobId" hiddenValue="${oaUser.jobId }" textValue="${parentJobName }" textName="jobLabel" hiddenId="jobId" textId="jobLabel"></t:choose>
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
					                                    <input id="englishName" name="englishName" type="text" class="form-control" value="${oaUser.englishName }">
					                                </div>
					                                <label class="col-sm-2 control-label">身份证号:</label>
					                                <div class="col-sm-4">
					                                    <input id="idCard" name="idCard" type="text" class="form-control" value="${oaUser.idCard }">
					                                </div>

												</div>
												
												<div class="form-group">
					                                <label class="col-sm-2 control-label">出生日期:</label>
					                                <div class="col-sm-4">
					                                    <input id="birthDate" name="birthDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${oaUser.birthDate }" type="date" pattern="yyyy-MM-dd"/>'>
					                                </div>
													<label class="col-sm-2 control-label">性别:</label>
						                             <div class="col-sm-4">
						                                <select class="form-control m-b" name="sex" id="sex" >
						                                		<c:forEach items="${sexs }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.sex == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
												</div>
					                                
						                        
				                            	<div class="form-group">
				                            		<label class="col-sm-2 control-label">微信号:</label>
					                                <div class="col-sm-4">
					                                    <input id="weixin" name="weixin" type="text" class="form-control" value="${oaUser.weixin }">
					                                </div>
					                          		<label class="col-sm-2 control-label">QQ:</label>
					                                <div class="col-sm-4">
					                                    <input id="qq" name="qq" type="text" class="form-control" value="${oaUser.qq }">
					                                </div>
					                                
					                           </div>  
					                           
                  				               <div class="form-group">
                  				               		<label class="col-sm-2 control-label">国家:</label>
					                                <div class="col-sm-4">
					                                    <input id="country" name="country" type="text" class="form-control" value="${oaUser.country }">
					                                </div>
						                           	<label class="col-sm-2 control-label">血型:</label>
					                                <div class="col-sm-4">
					                                    <input id="bloodType" name="bloodType" type="text" class="form-control" value="${oaUser.bloodType }">
					                                </div>
					                          		
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">婚姻状况:</label>
						                             <div class="col-sm-4">
						                                <select class="form-control m-b" name="canMarriage" id="canMarriage" >
						                                		<c:forEach items="${canMarriages }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.canMarriage == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
					                           		<label class="col-sm-2 control-label">民族:</label>
					                                <div class="col-sm-4">
					                                    <input id="nation" name="nation" type="text" class="form-control" value="${oaUser.nation }">
					                                </div>
					                              	
					                           </div>
					                           
                                       			<div class="form-group">
                                       				<label class="col-sm-2 control-label">户口类型:</label>
						                             <div class="col-sm-4">
						                                <select class="form-control m-b" name="accountType" id="accountType" >
						                                		<c:forEach items="${accountTypes }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.accountType  == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
						                           	<label class="col-sm-2 control-label">政治面貌:</label>
						                             <div class="col-sm-4">
						                                <select class="form-control m-b" name="political" id="political" >
						                                		<c:forEach items="${politicals }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.political == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
					                          		
					                           </div> 
					                           
                                               <div class="form-group">
                                               		<label class="col-sm-2 control-label">毕业时间:</label>
					                                <div class="col-sm-4">
					                                    <input id="graduateDate" name="graduateDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${oaUser.graduateDate }" type="date" pattern="yyyy-MM-dd"/>'>
					                                </div>
						                           	<label class="col-sm-2 control-label">户口地区:</label>
						                             <div class="col-sm-4">
														<input id="residence" name="residence" type="text" class="form-control" value="${oaUser.residence }">
						                             </div>
					                          		
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">最高学历:</label>
						                             <div class="col-sm-4">
						                                <select class="form-control m-b" name="highestEducation" id="highestEducation" >
						                                		<c:forEach items="${highestEducations }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.highestEducation == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
					                           		<label class="col-sm-2 control-label">毕业学校:</label>
					                                <div class="col-sm-4">
					                                    <input id="graduateSchool" name="graduateSchool" type="text" class="form-control" value="${oaUser.graduateSchool }">
						                             </div>
					                           </div>
					                              
                                               <div class="form-group">
                                               		<label class="col-sm-2 control-label">所学专业:</label>
						                             <div class="col-sm-4">
														<input id="major" name="major" type="text" class="form-control" value="${oaUser.major }">
						                             </div>
						                           	<label class="col-sm-2 control-label">培养方式:</label>
						                             <div class="col-sm-4">
						                                <select class="form-control m-b" name="developType" id="developType" >
						                                		<c:forEach items="${developTypes }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.developType == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
					                           </div> 

                                               <div class="form-group">
                                               		<label class="col-sm-2 control-label">第一外语:</label>
					                                <div class="col-sm-4">
					                                    <input id="fstForeignLang" name="fstForeignLang" type="text" class="form-control" value="${oaUser.fstForeignLang }">
						                             </div>
						                           	<label class="col-sm-2 control-label">第二外语:</label>
						                             <div class="col-sm-4">
														<input id="secForeignLang" name="secForeignLang" type="text" class="form-control" value="${oaUser.secForeignLang }">
						                             </div>
					                          		
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">第三外语:</label>
						                             <div class="col-sm-4">
														<input id="thdForeignLang" name="thdForeignLang" type="text" class="form-control" value="${oaUser.thdForeignLang }">
						                             </div>
					                           		<label class="col-sm-2 control-label">其他语种:</label>
					                                <div class="col-sm-4">
					                                    <input id="otherLang" name="otherLang" type="text" class="form-control" value="${oaUser.otherLang }">
						                             </div>
					                           </div>

                                               <div class="form-group">
                                               		<label class="col-sm-2 control-label">证书类型:</label>
						                             <div class="col-sm-4">
														<select class="form-control m-b" name="certificateType" id="certificateType" >
						                                		<c:forEach items="${certificateTypes }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.certificateType == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
						                           	<label class="col-sm-2 control-label">职业证书:</label>
						                             <div class="col-sm-4">
														<input id="certificate" name="certificate" type="text" class="form-control" value="${oaUser.certificate }">
						                             </div>
					                           </div>
					                           
                                               <div class="form-group">
						                           	<label class="col-sm-2 control-label">首次参加工作时间:</label>
						                             <div class="col-sm-4">
						                             <input id="fstWorkDate" name="fstWorkDate" type="text" class="laydate-icon form-control layer-date" value='<fmt:formatDate value="${oaUser.fstWorkDate }" type="date" pattern="yyyy-MM-dd"/>'>
					                                </div>
					                                <label class="col-sm-2 control-label">工龄:</label>
						                             <div class="col-sm-4">
														<input id="workAge" name="workAge" type="text" class="form-control" value="${oaUser.workAge }">
						                             </div>
 					                          		
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">社保断档:</label>
													<div class="col-sm-4">
														<input id="socailShort" name="socailShort" type="text" class="form-control" value="${oaUser.socailShort }">
						                             </div>
						                             <label class="col-sm-2 control-label">上家雇主:</label>
						                             <div class="col-sm-4">
						                             <input id="lastEmploy" name="lastEmploy" type="text" class="form-control" value="${oaUser.lastEmploy }">
					                                </div>
					                           </div>
					                          
					                           	<div class="form-group">
					                           		<label class="col-sm-2 control-label">合同编号:</label>
						                             <div class="col-sm-4">
						                             <input id="contractNo" name="contractNo" type="text" class="form-control" value="${oaUser.contractNo }">
					                                </div>
						                           	<label class="col-sm-2 control-label">合同类型:</label>
						                             <div class="col-sm-4">
														<select class="form-control m-b" name="contractType" id="contractType" >
						                                		<c:forEach items="${contractTypes }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUser.contractType == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                                </select>
						                             </div>
					                           </div>
					                           
					                           <div class="form-group">
					                           		<label class="col-sm-2 control-label">合同年限:</label>
													<div class="col-sm-4">
														<input id="contractTerm" name="contractTerm" type="text" class="form-control" value="${oaUser.contractTerm }">
						                             </div>
						                             <label class="col-sm-2 control-label">合同开始日期:</label>
						                             <div class="col-sm-4">
														<input id="contractStartDate" name="contractStartDate" type="text" class="laydate-icon form-control layer-date" value="${oaUser.contractStartDate }">
					                                </div>
					                                
					                           </div>
 												
					                           <div class="form-group">
					                           		
					                          		<label class="col-sm-2 control-label">合同结束日期:</label>
													<div class="col-sm-4">
														<input id="contractEndDate" name="contractEndDate" type="text" class="laydate-icon form-control layer-date" value="${oaUser.contractEndDate }">
					                                </div>
					                           </div>
						                           
				                            </div>
				                            
				                            <div class="col-sm-2">
													<img id="imageUrl" alt="个人图片" class="img-responsive center-block" src="${oaUser.selfImage }" style="margin-top:2rem;width:12rem;height:13rem;border:1px solid #B2C5D9;">
														<!--dom结构部分-->
														<div id="uploader-demo" style="margin-left: 4.5rem; margin-top: 2rem">
															<div id="fileList" class="uploader-list"></div>
															<div id="filePicker">上传个人照片</div>
														</div>
														
													<img id="idCardImageUrl" alt="身份证图片" class="img-responsive center-block" src="${oaUser.idCardImage }" style="margin-top:5rem;width:18rem;height:14rem;border:1px solid #B2C5D9;">
 														<div id="uploader-demo2" style="margin-left: 4.5rem; margin-top: 2rem">
															<div id="fileList2" class="uploader-list"></div>
															<div id="filePicker2">上传身份证图片</div>
														</div>
														
														<img id="certificateImageUrl" alt="证书图片" class="img-responsive center-block" src="${oaUser.certificateImage }" style="margin-top:5rem;width:10rem;height:10rem;border:1px solid #B2C5D9;">
 														<div id="uploader-demo3" style="margin-left: 4.5rem; margin-top: 2rem">
															<div id="fileList3" class="uploader-list"></div>
															<div id="filePicker3">上传证书图片</div>
														</div>
				                            </div>
				                            
				                            
				                            </div>
				                        </div>
				                        
				                        
                				        <div id="tab-3" class="tab-pane">
				                            <div class="panel-body">
					                            <div class="col-sm-12">
					                            
						                            <div class="form-group">
							                           	<label class="col-sm-2 control-label">成本类型:</label>
							                             <div class="col-sm-4">
							                             	<select class="form-control m-b" name="payType" id="payType" >
						                                		<c:forEach items="${payTypes }" var="t">
						                                			<option value="${t.value }" <c:if test="${oaUserPay.payType == t.value}">selected</c:if>>${t.label }</option>
						                                		</c:forEach>
						                               	 	</select>
						                                </div>	
							                           	<label class="col-sm-2 control-label">成本编号:</label>
							                             <div class="col-sm-4">
															<input id="payNo" name="payNo" type="text" class="form-control" value="${oaUserPay.payNo }">
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">基本工资:</label>
							                             <div class="col-sm-4">
															<input id="baseMoney" name="baseMoney" type="text" class="form-control" value="${oaUserPay.baseMoney }">
						                                </div>
						                                <label class="col-sm-2 control-label">绩效工资:</label>
							                             <div class="col-sm-4">
															<input id="performMoney" name="performMoney" type="text" class="form-control" value="${oaUserPay.performMoney }">
						                                </div>
						                           </div>
						                           
						                           
						                           <div class="form-group">
						                                <label class="col-sm-2 control-label">公积金号:</label>
							                             <div class="col-sm-4">
						                                <input id="fundNo" name="fundNo" type="text" class="form-control" value="${oaUserPay.fundNo }">
						                                </div>
							                           	<label class="col-sm-2 control-label">公司承担养老保险:</label>
							                             <div class="col-sm-4">
															<input id="companyAged" name="companyAged" type="text" class="form-control" value="${oaUserPay.companyAged }">
						                                </div>
						                           </div>
						                           
						                           	<div class="form-group">
							                           	<label class="col-sm-2 control-label">个人承担养老保险:</label>
							                             <div class="col-sm-4">
															<input id="personalAged" name="personalAged" type="text" class="form-control" value="${oaUserPay.personalAged }">
						                                </div>
						                                <label class="col-sm-2 control-label">公司承担公积金:</label>
							                             <div class="col-sm-4">
															<input id="companyFund" name="companyFund" type="text" class="form-control" value="${oaUserPay.companyFund }">
						                                </div>
						                                	
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">个人承担公积金:</label>
							                             <div class="col-sm-4">
						                                <input id="personalFund" name="personalFund" type="text" class="form-control" value="${oaUserPay.personalFund }">
						                                </div>
						                                <label class="col-sm-2 control-label">公司承担失业金:</label>
							                             <div class="col-sm-4">
															<input id="companyUnEmploy" name="companyUnEmploy" type="text" class="form-control" value="${oaUserPay.companyUnEmploy }">
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
							                           	<label class="col-sm-2 control-label">个人承担失业金:</label>
							                             <div class="col-sm-4">
															<input id="personalUnemploy" name="personalUnemploy" type="text" class="form-control" value="${oaUserPay.personalUnemploy }">
						                                </div>
						                                <label class="col-sm-2 control-label">公司承担医疗保险:</label>
							                             <div class="col-sm-4">
															<input id="companyMedical" name="companyMedical" type="text" class="form-control" value="${oaUserPay.companyMedical }">
						                                </div>	
						                           </div>
						                           
						                           <div class="form-group">
							                           	<label class="col-sm-2 control-label">个人承担医疗保险:</label>
							                             <div class="col-sm-4">
						                                <input id="personalMedical" name="personalMedical" type="text" class="form-control" value="${oaUserPay.personalMedical }">
						                                </div>
							                           	<label class="col-sm-2 control-label">生育保险:</label>
							                             <div class="col-sm-4">
															<input id="fertility" name="fertility" type="text" class="form-control" value="${oaUserPay.fertility }">
						                                </div>
							                           	
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">工伤保险:</label>
							                             <div class="col-sm-4">
															<input id="occuInjury" name="occuInjury" type="text" class="form-control" value="${oaUserPay.occuInjury }">
						                                </div>
						                                <label class="col-sm-2 control-label">工资开户行:</label>
							                             <div class="col-sm-4">
															<input id="payBank" name="payBank" type="text" class="form-control" value="${oaUserPay.payBank }">
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
							                           	<label class="col-sm-2 control-label">工资账号:</label>
							                             <div class="col-sm-4">
						                                <input id="wageNo" name="wageNo" type="text" class="form-control" value="${oaUserPay.wageNo }">
						                                </div>
							                           	<label class="col-sm-2 control-label">社保缴纳地:</label>
							                             <div class="col-sm-4">
															<input id="socialArea" name="socialArea" type="text" class="form-control" value="${oaUserPay.socialArea }">
						                                </div>
						                           </div>
						                           
						                           <div class="form-group">
						                           		<label class="col-sm-2 control-label">社保号码:</label>
							                             <div class="col-sm-4">
															<input id="socialNo" name="socialNo" type="text" class="form-control" value="${oaUserPay.socialNo }">
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
								                    <div class="ibox-title" style = "border-color: #fff; margin-top: -10px ">
								                        <div class="ibox-tools">
															<button id="jytijiao" class="btn btn-primary btn-sm " type="button"><i class="fa fa-check"></i>&nbsp;增加</button>
															<button id="jyshanchu"  class="btn btn-primary btn-sm " type="button"><i class="fa fa-remove"></i>&nbsp;删除</button>
								                        </div>
								                    </div>
								                    <div class="ibox-content">
								
								                        <div class="table-responsive">
								                            <table class="table table-striped">
								                               <thead>
								                                    <tr>
								                                        <th></th>
								                                        <th>时间(从)</th>
								                                        <th>时间(到)</th>
								                                        <th>学校名称</th>
								                                    </tr>
								                                </thead>
								                                <tbody id="jiaoyu">
								                                
								                                <c:if test="${empty user.id }">
								                                    <tr id="jiaoyutr1">
								                                        <td>
								                                            <input type="checkbox" id="checks1" class="checks1" name="input[]">
								                                        </td>
								                                        <td><input id="beforeDate" name="studys[1].beforeDate" type="text" class="form-control" value=""></td>
								                                        <td><input id="endDate" name="studys[1].endDate" type="text" class="form-control" value=""></td>
								                                        <td><input id="schoolName" name="studys[1].schoolName" type="text" class="form-control" value=""></td>
								                                    </tr>
																</c:if>
																<c:if test="${not empty user.id }">
																	<c:forEach items="${userStudys }" var="m" varStatus="status">
																		<tr id="jiaoyutr${status.count}">
									                                        <td>
									                                            <input type="checkbox" id="checks${status.count}" class="checks${status.count}" name="input[]">
									                                        </td>
									                                        <td><input id="beforeDate" name="studys[${status.count}].beforeDate" type="text" class="form-control" value="${m.beforeDate }"></td>
									                                        <td><input id="endDate" name="studys[${status.count}].endDate" type="text" class="form-control" value="${m.endDate }"></td>
									                                        <td><input id="schoolName" name="studys[${status.count}].schoolName" type="text" class="form-control" value="${m.schoolName }"></td>
								                                    	</tr>
																	</c:forEach>
																
																</c:if>
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
								                    <div class="ibox-title" style = "border-color: #fff; margin-top: -10px ">
								                        <div class="ibox-tools">
															<button id="gztijiao" class="btn btn-primary btn-sm " type="button"><i class="fa fa-check"></i>&nbsp;增加</button>
															<button id="gzshanchu"  class="btn btn-primary btn-sm " type="button"><i class="fa fa-remove"></i>&nbsp;删除</button>
								                        </div>
								                    </div>
								                    <div class="ibox-content">
								
								                        <div class="table-responsive">
								                            <table class="table table-striped">
								                               <thead>
								                                    <tr>
								                                        <th></th>
								                                        <th>时间(从)</th>
								                                        <th>时间(到)</th>
								                                        <th>公司名称</th>
								                                        <th>担任职务</th>
								                                        <th>工作描述</th>
								                                    </tr>
								                                </thead>
								                                <tbody id="gongzuo">
								                                	<c:if test="${empty user.id }">
									                                    <tr id="gongzuotr1">
									                                        <td>
									                                            <input type="checkbox" id="gzchecks1" class="gzchecks1" name="input[]">
									                                        </td>
									                                        <td><input id="beforeWorkDate" name="works[1].beforeWorkDate" type="text" class="form-control" value=""></td>
									                                        <td><input id="endWorkDate" name="works[1].endWorkDate" type="text" class="form-control" value=""></td>
									                                        <td><input id="companyName" name="works[1].companyName" type="text" class="form-control" value=""></td>
									                                        <td><input id="job" name="works[1].job" type="text" class="form-control" value=""></td>
									                                        <td><input id="workInfo" name="works[1].workInfo" type="text" class="form-control" value=""></td>
									                                    </tr>
								                                    </c:if>
								                                    
								                                    <c:if test="${not empty user.id }">
																		<c:forEach items="${userWorks }" var="m" varStatus="status">
																			<tr id="gongzuotr${status.count}">
																			<td>
									                                            <input type="checkbox" id="gzchecks${status.count}" class="gzchecks${status.count}" name="input[]">
									                                        </td>
									                                        <td><input id="beforeWorkDate" name="works[${status.count}].beforeWorkDate" type="text" class="form-control" value="${m.beforeWorkDate }"></td>
									                                        <td><input id="endWorkDate" name="works[${status.count}].endWorkDate" type="text" class="form-control" value="${m.endWorkDate }"></td>
									                                        <td><input id="companyName" name="works[${status.count}].companyName" type="text" class="form-control" value="${m.companyName }"></td>
									                                        <td><input id="job" name="works[${status.count}].job" type="text" class="form-control" value="${m.job }"></td>
									                                        <td><input id="workInfo" name="works[${status.count}].workInfo" type="text" class="form-control" value="${m.workInfo }"></td>
																			</tr>
																		</c:forEach>
																	</c:if>

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
				          
 						</t:formvalid>
                    </div>
				</div>
			</div>
		</div>
</body>

<script type="text/javascript">

//
$(function() {
	//初始化Web Uploader
	var uploader = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto : true,

		// swf文件路径
		swf : 'static/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : 'func/upload/uploadImages?db=1',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#filePicker',
			label : '上传个人图片'
		},

		// 只允许选择图片文件。
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
		}
	});

	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function(file, percentage) {
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file, data) {
		var filePath = data.attributes.filePath;
		$("#imageUrl").attr("src", filePath);
		$("#selfImage").val(filePath);
	});

	// 文件上传失败，显示上传出错。
	uploader.on('uploadError', function(file) {

	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function(file) {
		qhTipSuccess('图片上传完成....');
	});
	
	//初始化Web Uploader
	var uploader1 = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto : true,

		// swf文件路径
		swf : 'static/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : 'func/upload/uploadImages?db=1',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#filePicker2',
			label : '上传身份证图片'
		},

		// 只允许选择图片文件。
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
		}
	});

	// 文件上传过程中创建进度条实时显示。
	uploader1.on('uploadProgress', function(file, percentage) {
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader1.on('uploadSuccess', function(file, data) {
		var filePath = data.attributes.filePath;
		$("#idCardImageUrl").attr("src", filePath);
		$("#idCardImage").val(filePath);
	});

	// 文件上传失败，显示上传出错。
	uploader1.on('uploadError', function(file) {

	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader1.on('uploadComplete', function(file) {
		qhTipSuccess('图片上传完成....');
	});
	
	
	
	//初始化Web Uploader
	var uploader2 = WebUploader.create({

		// 选完文件后，是否自动上传。
		auto : true,

		// swf文件路径
		swf : 'static/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : 'func/upload/uploadImages?db=1',

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#filePicker3',
			label : '上传证书图片'
		},

		// 只允许选择图片文件。
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
		}
	});

	// 文件上传过程中创建进度条实时显示。
	uploader2.on('uploadProgress', function(file, percentage) {
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader2.on('uploadSuccess', function(file, data) {
		var filePath = data.attributes.filePath;
		$("#certificateImageUrl").attr("src", filePath);
		$("#certificateImage").val(filePath);
	});

	// 文件上传失败，显示上传出错。
	uploader2.on('uploadError', function(file) {

	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader2.on('uploadComplete', function(file) {
		qhTipSuccess('图片上传完成....');
	});


});

	$(function() {
		laydate({elem:"#birthDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
		laydate({elem:"#graduateDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
		laydate({elem:"#fstWorkDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
		laydate({elem:"#contractStartDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
		laydate({elem:"#contractEndDate",event:"focus",istime: false, format: 'YYYY-MM-DD'});
		
		$("#oaUserInfoForm").validate({
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					success : function(data) {
						var o = $.parseJSON(data);
						if (o.success) {
							qhTipSuccess('操作成功');
							/* location.href='commonController.do?goSuccess'; */
						} else {
							qhTipWarning(o.msg);
						}
					},
					error : function(data) {
						qhTipError('系统错误，请联系系统管理员');
					}
				});
			}
		});
	});
	
	
	//保存
	function doBtnUserAction() {
		$("#oaUserInfoForm").submit();
	}
	
	var i = $("#jiaoyu").children("tr").length;
	//教育提交点击事件
	$("#jytijiao").click(function(){
		i++;
		
		var html = '';
		html += '<tr id="jiaoyutr'+i+'">'
			+'<td>'
			+'<input type="checkbox" id="checks'+i+'" class="checks'+i+'" name="input[]">'
			+'</td>'
			+'<td><input id="beforeDate'+i+'" name="studys['+i+'].beforeDate" type="text" class="form-control" value=""></td>'
			+'<td><input id="endDate'+i+'" name="studys['+i+'].endDate" type="text" class="form-control" value=""></td>'
			+'<td><input id="schoolName'+i+'" name="studys['+i+'].schoolName" type="text" class="form-control" value=""></td>'
			+'</tr>';
		
		$("#jiaoyu").append(html);
		
	});
	
	
	//教育删除点击事件
	$("#jyshanchu").click(function(){
		var n = 1;
		
		//根据选择删除行
		for(var m=1; m < i+1; m++) {
			if($("#checks"+m+"").is(':checked')) {
				$("#jiaoyutr"+m+"").remove();
			} else {
				n++;
			}
		};

		//判断是否未选择点击删除
 		if(i+1 == n) {
 			qhTipWarning("请选择要删除的教育经历!");
		}; 
		
		
	});
	
	
	var x = $("#gongzuo").children("tr").length;
	//工作提交点击事件
	$("#gztijiao").click(function(){
		x++;
		
		var html = '';
		html += '<tr id="gongzuotr'+x+'">'
			+'<td>'
			+'<input type="checkbox" id="gzchecks'+x+'" class="gzchecks'+x+'" name="input[]">'
			+'</td>'
			+'<td><input id="beforeWorkDate'+x+'" name="works['+x+'].beforeWorkDate" type="text" class="form-control" value=""></td>'
			+'<td><input id="endWorkDate'+x+'" name="works['+x+'].endWorkDate" type="text" class="form-control" value=""></td>'
			+'<td><input id="companyName'+x+'" name="works['+x+'].companyName" type="text" class="form-control" value=""></td>'
			+'<td><input id="job'+x+'" name="works['+x+'].job" type="text" class="form-control" value=""></td>'
			+'<td><input id="workInfo'+x+'" name="works['+x+'].workInfo" type="text" class="form-control" value=""></td>'
			+'</tr>';
		
		$("#gongzuo").append(html);
		
	});
	
	
	//工作删除点击事件
	$("#gzshanchu").click(function(){
		var z = 1;
		
		for(var y=1; y < x+1; y++) {
			if($("#gzchecks"+y+"").is(':checked')) {
				$("#gongzuotr"+y+"").remove();
			} else {
				z++;
			}
		};
		
 		if(x+1 == z) {
 			qhTipWarning("请选择要删除的工作经历!");
		};
		
	});
	
</script>

</html>
