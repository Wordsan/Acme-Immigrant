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

<acme:display code="actor.name" value="${immigrant.name}" />
<acme:display code="actor.surname" value="${immigrant.surname}" />
<acme:display code="actor.email" value="${immigrant.email}" />
<jstl:if
	test="${immigrant.phoneNumber!= '' or immigrant.phoneNumber != null}">
	<acme:display code="actor.phoneNumber" value="${immigrant.phoneNumber}" />
</jstl:if>
<jstl:if test="${immigrant.address!= '' or immigrant.address != null}">
	<acme:display code="actor.address" value="${immigrant.address}" />
</jstl:if>

<fieldset>
	<legend>
		<spring:message code="immigrant.reports"></spring:message>
	</legend>
	<jstl:forEach items="${immigrant.reports}" var="report">
		<acme:display code="report.text" value="${report.text}"></acme:display>
		<jstl:if test="${report.pictures != null and report.pictures != ''}">
			<acme:display code="report.picture" value="${report.pictures}"></acme:display>
		</jstl:if>
	</jstl:forEach>
</fieldset>
<security:authorize access="hasRole('OFFICER')">
	<jstl:if test="${immigrant.investigator == null}">
		<fieldset>
			<legend>
				<spring:message code="investigator.assign"></spring:message>
			</legend>
			<form
				action="investigator/officer/assign.do?immigrantId=${immigrant.id}"
				method="post">
				<select name="investigatorId">
					<option value="">---</option>
					<jstl:forEach items="${investigators}" var="investigator">
						<option value="${investigator.id}">
							<jstl:out value="${investigator.name}" />
							<jstl:out value="${investigator.surname}" />
						</option>
					</jstl:forEach>
				</select>
				<button type="submit">
					<spring:message code="investigator.assign" />
				</button>
			</form>
		</fieldset>
	</jstl:if>
</security:authorize>
<security:authorize access="hasRole('INVESTIGATOR')">
	<fieldset>
		<spring:message code="report.write" var="buCreate" />
		<acme:displayLink
			link="/report/investigator/create.do?immigrantId=${immigrant.id}"
			value="${buCreate}" />
	</fieldset>
</security:authorize>