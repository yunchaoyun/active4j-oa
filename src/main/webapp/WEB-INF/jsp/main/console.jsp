<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="default"></t:base>
</head>

<body class="gray-bg">
<div class="row">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-success pull-right">月</span>
                        <h5>收入</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins">40 886,200</h1>
                        <div class="stat-percent font-bold text-success">98% <i class="fa fa-bolt"></i>
                        </div>
                        <small>总收入</small>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-info pull-right">全年</span>
                        <h5>订单</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins">275,800</h1>
                        <div class="stat-percent font-bold text-info">20% <i class="fa fa-level-up"></i>
                        </div>
                        <small>新订单</small>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-primary pull-right">今天</span>
                        <h5>访客</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins">106,120</h1>
                        <div class="stat-percent font-bold text-navy">44% <i class="fa fa-level-up"></i>
                        </div>
                        <small>新访客</small>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-danger pull-right">最近一个月</span>
                        <h5>活跃用户</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins">80,600</h1>
                        <div class="stat-percent font-bold text-danger">38% <i class="fa fa-level-down"></i>
                        </div>
                        <small>12月</small>
                    </div>
                </div>
            </div>
        </div>
    <div class="row  border-bottom white-bg dashboard-header">
        <div class="col-sm-3">
            <h2>Hello,Guest</h2>
            <small>欢迎扫描以下二维码关注我们：</small>
            <br>
            <br>
            <img src="https://zh-active4j-1251505225.cos.ap-shanghai.myqcloud.com/active4jboot/active4j-wx.jpg" width="100%" style="max-width:264px;">
            <br>
        </div>
        <div class="col-sm-5">
            <h2>
                active4j快速开发平台
            </h2>
            <p>Active4j-jsp是基于SpingBoot2.0轻量级的java快速开发框架。以Spring Framework为核心容器，Spring MVC为模型视图控制器，Mybatis Plus为数据访问层， Apache Shiro为权限授权层, Redis为分布式缓存，Quartz为分布式集群调度，JSP作为前端页面引擎，采用JSTL标签库封装组件的开源框架。</p>
            <p>Active4j-jsp定位于企业快速开发平台建设，代码全部开源，持续更新，共同维护。Active4j-jsp可以应用在任何J2EE的项目开发中，尤其适合企业信息管理系统（MIS），企业办公系统（OA），客户关系管理系统（CRM）等。</p>
            <p>
                <b>当前版本：</b>active4j-jsp版
            </p>
            <p>
                <b>官方网站：</b><span class="label"><a href="www.active4j.com" target="_blank">www.active4j.com</a></span>
            </p>
             <p>
                <b>QQ群：</b><span class="label label-warning">203802692</span>
            </p>
            <br>
            <p>
                <a class="btn btn-success btn-outline" href="https://github.com/yunchaoyun/active4j-jsp" target="_blank">
                    <i class="fa fa-home"> </i> github
                </a>
                <a class="btn btn-white btn-bitbucket" href="https://gitee.com/active4j/active4j-jsp" target="_blank">
                    <i class="fa fa-home"></i> gitee
                </a>
            </p>
        </div>
        <div class="col-sm-4">
            <h4>项目特点：</h4>
            <ol>
                <li>开箱即用，节省开发时间，提高开发效率</li>
                <li>代码全部开源，持续更新，共同维护</li>
                <li>支持分布式部署，session统一由redis进行管理</li>
                <li>基于SpringBoot，简化了大量项目配置和maven依赖，让您更专注于业务开发</li>
                <li>使用分层设计，分为dao，service，Controller，view层，层次清楚，低耦合，高内聚</li>
                <li>提供了诸多的UI组件</li>
                <li>友好的代码结构及注释，便于阅读及二次开发</li>
                <li>灵活的权限控制, 整合shiro，可控制到页面或按钮，满足绝大部分的权限需求,优化权限注解方便权限配置</li>
                <li>日志记录采用aop(LogAop类)方式，可对用户所有操作进行记录</li>
                <li>引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能</li>
                <li>数据统计报表：丰富的报表统计功能</li>
                <li>集成jsp页面，采用标准JSTL标签库对常用组件进行封装，便于将传统项目过度到springboot</li>
                <li>组件库丰富，对常用页面组件进行了代码封装，提高开发效率</li>
                <li>前端页面简洁优美，支持移动端</li>
                <li>更多……</li>
            </ol>
        </div>

    </div>
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-4">

                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>二次开发</h5>
                    </div>
                    <div class="ibox-content">
                        <p>我们是专业的软件基础平台与解决方案提供商，为企事业单位提供创新可靠的软件基础平台产品及技术服务</p>
                    </div>
                </div>
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>联系信息</h5>

                    </div>
                    <div class="ibox-content">
                        <p><i class="fa fa-send-o"></i> 官方网站：<a href="http://www.active4j.com/" target="_blank">www.active4j.com</a>
                        </p>
                        <p><i class="fa fa-qq"></i> QQ：<a href="javascript:;" target="_blank">113578375</a>
                        </p>
                        <p><i class="fa fa-weixin"></i> 微信号：<a href="javascript:;">chen_terry</a>
                        </p>
                        <p><i class="fa fa-credit-card"></i> qq群：<a href="javascript:;">203802692</a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>更新日志</h5>
                    </div>
                    <div class="ibox-content no-padding">
                        <div class="panel-body">
                            <div class="panel-group" id="version">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h5 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#version" href="#v1.0">v1.0</a><code class="pull-right">2020.3.24</code>
                                            </h5>
                                    </div>
                                    <div id="v1.0" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <div class="alert alert-warning">项目发布并开源</div>
                                        </div>
                                    </div>
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

