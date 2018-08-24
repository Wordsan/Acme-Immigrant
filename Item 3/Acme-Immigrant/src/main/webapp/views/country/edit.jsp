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

<form:form action="country/admin/edit.do" method="post"
	modelAttribute="country">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="laws" />
	<form:hidden path="visas" />

	<acme:textbox code="country.name" path="name" />

	<acme:textbox code="country.ISOCode" path="ISOCode" />

	<acme:textbox code="country.flag" path="flag"
		placeholder="https://imgur.es/sjfksdj.jpeg" />

	<acme:textbox code="country.wikiPage" path="wikiPage"
		placeholder="https://wikipedia.es/Spain" />

	<acme:submit name="save" code="save.button" />
	<acme:cancel url="country/admin/list.do" code="cancel.button" />
	<jstl:if test="${country.id != 0}">
		<button type="button" name="delete"
			onclick="$(form).attr('action','country/admin/delete.do');askSubmission('<spring:message code='confirm.msg'/>',$(form))">
			<spring:message code="delete.button" />
		</button>
	</jstl:if>
</form:form>