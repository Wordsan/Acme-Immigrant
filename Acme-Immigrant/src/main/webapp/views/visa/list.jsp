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
<script type="text/javascript">
	$(document).ready(function() {
		$("tr").each(function() {
			p = this.children[3];
			if (p.textContent.includes("?")) {
				p.textContent = p.textContent.replace("?", "\u20AC");
			}
		});

	});
</script>
<form:form action="visa/search.do" method="get"
	modelAttribute="searchVisa">
	<acme:textbox code="visa.search" path="keyword" />
	<acme:submit name="save" code="search.button" />
</form:form>


<display:table name="visas" id="visa" requestURI="${requestUri}"
	pagesize="5" class="displaytag">
	<display:column>
		<spring:message code="display.button" var="disp"></spring:message>
		<acme:displayLink link="visa/display.do?visaId=${visa.id}"
			value="${disp}" />
	</display:column>

	<security:authorize access="hasRole('IMMIGRANT')">
		<display:column>
			<spring:message code="application.create" var="apply"></spring:message>
			<acme:displayLink
				link="application/immigrant/create.do?visaId=${visa.id}"
				value="${apply}" />
		</display:column>
	</security:authorize>

	<spring:message code="visa.clase" var="visaClase"></spring:message>
	<display:column property="clase" title="${visaClase}" sortable="true" />

	<spring:message code="visa.description" var="visaDescription"></spring:message>
	<display:column property="description" title="${visaDescription}"
		sortable="true" />

	<spring:message code="visa.price" var="visaPrice"></spring:message>
	<display:column property="price" title="${visaPrice}" sortable="true" />

	<spring:message code="visa.country" var="visaCountry"></spring:message>
	<display:column property="country.name" title="${visaCountry}"
		sortable="true" />

	<spring:message code="visa.category" var="visaCategory"></spring:message>
	<display:column property="category.name" title="${visaCategory}"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="visa.new" var="createVisa"></spring:message>
	<spring:url value="visa/admin/create.do" var="url"></spring:url>
	<a href="${url}"><jstl:out value="${createVisa}"></jstl:out></a>
</security:authorize>