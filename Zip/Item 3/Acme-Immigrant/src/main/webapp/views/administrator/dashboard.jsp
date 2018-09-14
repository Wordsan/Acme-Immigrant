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
		$("td:contains('?')").each(function(index, element) {
			element.textContent = element.textContent.replace("?", "\u20AC")
		});
		$("span").detach();
	});
</script>

<fieldset>
	<legend>
		<spring:message code="dashboard.app.user"></spring:message>
	</legend>
	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash13" />
		<display:column title="${dash13}">
			<jstl:out value="${minAppUser}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash14" />
		<display:column title="${dash14}">
			<jstl:out value="${maxAppUser}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash15" />
		<display:column title="${dash15}">
			<jstl:out value="${avgAppUser}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash16" />
		<display:column title="${dash16}">
			<jstl:out value="${stdAppUser}" />
		</display:column>
	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="dashboard.app.officer"></spring:message>
	</legend>
	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash17" />
		<display:column title="${dash17}">
			<jstl:out value="${minAppOff}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash18" />
		<display:column title="${dash18}">
			<jstl:out value="${maxAppOff}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash19" />
		<display:column title="${dash19}">
			<jstl:out value="${avgAppOff}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash20" />
		<display:column title="${dash20}">
			<jstl:out value="${stdAppOff}" />
		</display:column>
	</display:table>
</fieldset>

<fieldset>
	<legend>
		<spring:message code="dashboard.visa.price"></spring:message>
	</legend>

	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash21" />
		<display:column title="${dash21}">
			<jstl:out value="${minPrice}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash22" />
		<display:column title="${dash22}">
			<jstl:out value="${maxPrice}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash23" />
		<display:column title="${dash23}">
			<jstl:out value="${avgPrice}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash24" />
		<display:column title="${dash24}">
			<jstl:out value="${stdPrice}" />
		</display:column>
	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="dashboard.imm.inv"></spring:message>
	</legend>
	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash25" />
		<display:column title="${dash25}">
			<jstl:out value="${minImmInv}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash26" />
		<display:column title="${dash26}">
			<jstl:out value="${maxImmInv}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash27" />
		<display:column title="${dash27}">
			<jstl:out value="${avgImmInv}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash28" />
		<display:column title="${dash28}">
			<jstl:out value="${stdImmInv}" />
		</display:column>
	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="dashboard.time.decision"></spring:message>
	</legend>
	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash29" />
		<display:column title="${dash29}">
			<jstl:out value="${minTimeDecision}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash30" />
		<display:column title="${dash30}">
			<jstl:out value="${maxTimeDecision}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash31" />
		<display:column title="${dash31}">
			<jstl:out value="${avgTimeDecision}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash32" />
		<display:column title="${dash32}">
			<jstl:out value="${stdTimeDecision}" />
		</display:column>
	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="dashboard.visa.cat"></spring:message>
	</legend>
	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash33" />
		<display:column title="${dash33}">
			<jstl:out value="${minVisaCat}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash34" />
		<display:column title="${dash34}">
			<jstl:out value="${maxVisaCat}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash35" />
		<display:column title="${dash35}">
			<jstl:out value="${avgVisaCat}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash36" />
		<display:column title="${dash36}">
			<jstl:out value="${stdVisaCat}" />
		</display:column>
	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="dashboard.law.coun"></spring:message>
	</legend>
	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash37" />
		<display:column title="${dash37}">
			<jstl:out value="${minLawCount}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash38" />
		<display:column title="${dash38}">
			<jstl:out value="${maxLawCount}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash39" />
		<display:column title="${dash39}">
			<jstl:out value="${avgLawCount}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash40" />
		<display:column title="${dash40}">
			<jstl:out value="${stdLawCount}" />
		</display:column>
	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="dashboard.req.visa"></spring:message>
	</legend>
	<display:table name="minAppUser" pagesize="5"
		requestURI="dashboard/admin/display.do" class="displaytag">
		<spring:message code="dashboard.min" var="dash43" />
		<display:column title="${dash43}">
			<jstl:out value="${minReqVisa}" />
		</display:column>
		<spring:message code="dashboard.max" var="dash44" />
		<display:column title="${dash44}">
			<jstl:out value="${maxReqVisa}" />
		</display:column>
		<spring:message code="dashboard.avg" var="dash45" />
		<display:column title="${dash45}">
			<jstl:out value="${avgReqVisa}" />
		</display:column>
		<spring:message code="dashboard.std" var="dash46" />
		<display:column title="${dash46}">
			<jstl:out value="${stdReqVisa}" />
		</display:column>
	</display:table>
</fieldset>
