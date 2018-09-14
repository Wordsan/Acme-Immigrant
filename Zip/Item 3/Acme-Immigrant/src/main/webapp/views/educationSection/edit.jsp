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
		a.datepicker({
			changeYear : true,
			yearRange: "1930:2018"
		});
		$('#datepicker').on("change", function() {
			a.datepicker("option", "dateFormat", "dd/mm/yy");
		});

	});
</script>


<form:form
	action="educationSection/immigrant/edit.do?applicationId=${applicationId}"
	method="post" modelAttribute="educationSection">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="educationSection.degreeName" path="degreeName" />
	<acme:textbox code="educationSection.institution" path="institution" />
	<spring:message code="educationSection.awarded" />
	<input type="text" id="datepicker" size="30" name="awarded">
	<form:errors path="awarded" cssClass="error" />
	<br>
	<spring:message code="educationSection.level" />
	<form:select path="level">
		<form:option value="NONE">
			<spring:message code="educationSection.level.none" />
		</form:option>
		<jstl:if test="${idioma == 'en'}">
			<form:option value="ELEMENTARY">
				<spring:message code="educationSection.level.elementary" />
			</form:option>
		</jstl:if>
		<form:option value="PRIMARY">
			<spring:message code="educationSection.level.primary" />
		</form:option>
		<jstl:if test="${idioma == 'en'}">
			<form:option value="SECONDARY">
				<spring:message code="educationSection.level.secondary" />
			</form:option>
		</jstl:if>
		<form:option value="HIGH">
			<spring:message code="educationSection.level.high" />
		</form:option>
		<form:option value="BACHELOR">
			<spring:message code="educationSection.level.bachelor" />
		</form:option>
		<form:option value="UNIVERSITYDEGREE">
			<spring:message code="educationSection.level.degree" />
		</form:option>
		<form:option value="MASTER">
			<spring:message code="educationSection.level.master" />
		</form:option>
		<form:option value="DOCTORATE">
			<spring:message code="educationSection.level.doctorate" />
		</form:option>
	</form:select>
	<br>
	<acme:cancel
		url="application/immigrant/display.do?applicationId=${applicationId}"
		code="cancel.button" />
	<acme:submit name="save" code="save.button" />
</form:form>