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

<display:table name="categories" id="category"
	requestURI="${requestURI}" pagesize="5" class="displaytag" >

	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink link="category/display.do?categoryId=${category.id}"
			value="${disp}" />
	</display:column>

	<spring:message code="category.name" var="categoryTitle"></spring:message>
	<display:column title="${categoryTitle }" sortable="true">
		<acme:displayLink link="category/list.do?parentId=${category.id}"
			value="${category.name}" />
	</display:column>

	<spring:message code="category.parent" var="categoryParent"></spring:message>
	<display:column property="parent.name" title="${categoryParent }"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.new" var="createCategory"></spring:message>
	<spring:url value="category/admin/create.do" var="url"></spring:url>
	<a href="${url}"><jstl:out value="${createCategory}"></jstl:out></a>
</security:authorize>