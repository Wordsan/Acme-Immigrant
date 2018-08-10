<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<img src="${banner}" height="400px" width="1000px" alt="${systemName}" />
</div>

<div>
	<ul id="jMenu">
		<li><a class="fNiv"><spring:message code="master.page.list" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="visa/search.do"><spring:message
							code="master.page.list.visa" /></a></li>
				<li><a href="category/firsts.do"><spring:message
							code="master.page.list.category" /></a></li>
				<li><a href="country/list.do"><spring:message
							code="master.page.list.country" /></a></li>
			</ul></li>
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="officer/admin/create.do"><spring:message
								code="master.page.administrator.create.officer" /></a></li>
					<li><a href="investigator/admin/create.do"><spring:message
								code="master.page.administrator.create.investigator" /></a></li>
					<li><a href="dashboard/admin/display.do"><spring:message
								code="master.page.administrator.display.dashboard" /></a></li>
					<li><a href="law/admin/list.do"><spring:message
								code="master.page.administrator.list.law" /></a></li>
					<li><a href="requirement/admin/list.do"><spring:message
								code="master.page.administrator.list.requirement" /></a></li>
					<li><a href="configurationSystem/admin/edit.do"><spring:message
								code="master.page.admin.editcS" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('IMMIGRANT')">
			<li><a class="fNiv"><spring:message
						code="master.page.immigrant" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="creditCard/immigrant/create.do"><spring:message
								code="master.page.createCreditCard" /></a></li>
					<li><a href="creditCard/immigrant/list.do"><spring:message
								code="master.page.listCreditCard" /></a></li>
					<li><a href="application/immigrant/list.do"><spring:message
								code="master.page.immigrant.list.application" /></a></li>
					<li><a href="question/immigrant/list.do"><spring:message
								code="master.page.immigrant.list.question" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('OFFICER')">
			<li><a class="fNiv"><spring:message
						code="master.page.officer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/officer/list.do"><spring:message
								code="master.page.officer.list.application" /></a></li>
					<li><a href="application/officer/notAssigned.do"><spring:message
								code="master.page.officer.assign.application" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a href="views/misc/terms.jsp"><spring:message
						code="terms" /></a></li>
			<li><a href="nonAuth/immigrant/create.do"><spring:message
						code="master.page.register.immigrant" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('IMMIGRANT')">
			<jstl:set var="role" value="immigrant"></jstl:set>
		</security:authorize>
		<security:authorize access="hasRole('OFFICER')">
			<jstl:set var="role" value="officer"></jstl:set>
		</security:authorize>
		<security:authorize access="hasRole('INVESTIGATOR')">
			<jstl:set var="role" value="investigator"></jstl:set>
		</security:authorize>
		<security:authorize access="hasRole('ADMIN')">
			<jstl:set var="role" value="admin"></jstl:set>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li><a href="views/misc/terms.jsp"><spring:message
						code="terms" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="${role}/edit.do"><spring:message
								code="master.page.profile.action.1" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
