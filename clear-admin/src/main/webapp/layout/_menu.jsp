<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

		<!-- BEGIN SIDEBAR -->

		<div class="page-sidebar nav-collapse collapse">

			<!-- BEGIN SIDEBAR MENU -->

			<ul class="page-sidebar-menu">

				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->

					<div class="sidebar-toggler hidden-phone"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->

				</li>

				<li>
					<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->

					<form class="sidebar-search">

						<div class="input-box">

							<a href="javascript:;" class="remove"></a> <input type="text"
								placeholder="Search..." /> <input type="button" class="submit"
								value=" " />

						</div>

					</form> <!-- END RESPONSIVE QUICK SEARCH FORM -->

				</li>

				<li class="active"><a href="javascript:;"> <i
						class="icon-cogs"></i> <span class="title">清算管理</span> <span
						class="selected"></span> <span class="arrow open"></span>

				</a>

					<ul class="sub-menu">

						<li><span class="selected"></span><a href="<%=request.getContextPath()%>/admin/admin.jsp">
								清算处理</a></li>

						<li><a href="<%=request.getContextPath()%>/admin/stepResult.jsp">清算结果查询</a></li>

					</ul></li>
					

				<sec:authorize access="hasRole('ROLE_ADMIN')"> 
				<li class="open"><a href="javascript:;"> <i
						class="icon-bookmark-empty"></i> <span class="title">清算场次管理</span>

						<span class="arrow open"></span>

				</a>

					<ul class="sub-menu" style="display: block;">

						<li><a href="<%=request.getContextPath()%>/config/stepConfig.jsp">清算场次配置</a></li>
						
						<li><a href="<%=request.getContextPath()%>/config/stepDebug.jsp">清算场次单步测试</a></li>

					</ul>
				</li>
				</sec:authorize>

			</ul>

			<!-- END SIDEBAR MENU -->

		</div>

		<!-- END SIDEBAR -->
