<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('IMMIGRANT')">
	<jstl:set var="role" value="immigrant"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('OFFICER')">
	<jstl:set var="role" value="officer"></jstl:set>
</security:authorize>
<security:authorize access="hasRole('INVESTIGATOR')">
	<jstl:set var="role" value="investigator"></jstl:set>
</security:authorize>
<fieldset>
	<legend>
		<spring:message code="application.status.title" />
	</legend>
	<form action="${requestURI}" method="get">
		<select name="status">
			<option value="">---</option>
			<security:authorize access="hasRole('IMMIGRANT')">
				<option value="OPENED">
					<spring:message code="application.status.opened" />
				</option>
			</security:authorize>
			<option value="AWAITING">
				<spring:message code="application.status.awaiting" />
			</option>
			<option value="REJECTED">
				<spring:message code="application.status.rejected" />
			</option>
			<option value="ACCEPTED">
				<spring:message code="application.status.accepted" />
			</option>
		</select>
		<button type="submit">
			<spring:message code="application.status.filter" />
		</button>
	</form>
</fieldset>

<display:table name="applications" id="app" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink
			link="application/${role}/display.do?applicationId=${app.id}"
			value="${disp}" />
	</display:column>

	<security:authorize access="hasRole('OFFICER')">
		<jstl:if test="${app.officer == null}">
			<display:column>
				<spring:message code="assign.button" var="ass"></spring:message>
				<acme:displayLink
					link="application/officer/assign.do?applicationId=${app.id}"
					value="${ass}" />
			</display:column>
		</jstl:if>
	</security:authorize>

	<spring:message code="application.ticker" var="appTicker"></spring:message>
	<display:column property="ticker" title="${appTicker}" sortable="true" />

	<spring:message code="application.visa" var="appVisa"></spring:message>
	<display:column property="visa.clase" title="${appVisa}"
		sortable="true" />

</display:table>