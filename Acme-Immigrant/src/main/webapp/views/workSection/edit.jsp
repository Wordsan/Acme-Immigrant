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
	$(function() {
		a = $('#datepicker');
		a.datepicker();
		b = $('#datepicker2');
		b.datepicker();
		$('#datepicker').on("change", function() {
			a.datepicker("option", "dateFormat", "dd/mm/yy");
		});
		$('#datepicker2').on("change", function() {
			b.datepicker("option", "dateFormat", "dd/mm/yy");
		});

	});
</script>

<form:form
	action="workSection/immigrant/save.do?applicationId=${applicationId}"
	method="post" modelAttribute="workSection" id="form">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<div class="rel40">
		<spring:message code="workSection.startDate" />
		<form:input path="startDate" id="datepicker2" />
		<form:errors path="startDate" cssClass="error" />
		<br>
	</div>
	<div class="rel50">
		<spring:message code="workSection.endDate" />
		<form:input path="endDate" id="datepicker" />
		<form:errors path="endDate" cssClass="error" />
		<br>
	</div>
	<div class="rel40">
		<acme:textbox code="workSection.companyName" path="companyName" />
	</div>
	<div class="rel50">
		<acme:textbox code="workSection.position" path="position" />
	</div>
	<div class="rel20">
		<acme:cancel
			url="application/immigrant/display.do?applicationId=${applicationId}"
			code="cancel.button" />
		<acme:submit name="save" code="save.button" />
	</div>
</form:form>