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

<form:form
	action="workSection/immigrant/edit.do?applicationId=${applicationId}"
	method="post" modelAttribute="workSection">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="workSection.companyName" path="companyName" />
	<acme:textbox code="workSection.position" path="position" />
	<acme:textbox code="workSection.startDate" path="startDate" />
	<acme:textbox code="workSection.endDate" path="endDate" />
	<acme:cancel
		url="application/immigrant/display.do?applicationId=${applicationId}"
		code="cancel.button" />
	<acme:submit name="save" code="save.button" />
</form:form>