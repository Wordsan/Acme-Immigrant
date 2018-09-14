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

<form:form action="report/investigator/edit.do" modelAttribute="report"
	method="post">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="immigrant" />
	<form:hidden path="investigator" />

	<acme:textarea code="report.text" path="text" />

	<acme:textarea code="report.pictures" path="pictures" />

	<acme:cancel
		url="/view/immigrant/display.do?immigrantId=${report.immigrant.id}"
		code="cancel.button" />
	<acme:submit name="save" code="save.button" />

</form:form>