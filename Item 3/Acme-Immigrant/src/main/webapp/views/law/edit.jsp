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

<form:form action="law/admin/edit.do" method="post" modelAttribute="law">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="relatedLaws" />
	<form:hidden path="createdAt" />
	<form:hidden path="abrogatedAt" />
	<form:hidden path="requirements" />

	<acme:textbox code="law.title" path="title" />

	<acme:textarea code="law.text" path="text" />

	<acme:select items="${countries}" itemLabel="name" code="law.country"
		path="country" />

	<acme:submit name="save" code="save.button" />
	<acme:cancel url="law/admin/list.do" code="cancel.button" />
	<jstl:if test="${law.id != 0}">
		<button type="button" name="delete"
			onclick="$(form).attr('action','law/admin/delete.do');askSubmission('<spring:message code='confirm.msg'/>',$(form))">
			<spring:message code="delete.button" />
		</button>
		<acme:button name="abrogate"
			url="law/admin/abrogate.do?lawId=${law.id}" code="abrogate.button" />
	</jstl:if>
</form:form>