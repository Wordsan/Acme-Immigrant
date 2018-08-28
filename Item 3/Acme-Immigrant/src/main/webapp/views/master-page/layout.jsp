<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico" />

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen"
	type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="styles/jquery-ui.structure.css"
	type="text/css">
<link rel="stylesheet" href="styles/jquery-ui.theme.css" type="text/css">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript" src="scripts/cookie.js">
	
</script>
<script type="text/javascript">
	$(document).ready(function() {
		if (window.location.href.search("message") != -1) {
			b = window.location.href.split("&")[window.location.href.split("&").length - 1].split("=")[1];
			a = $('<span class="message" id="mensaje"></span>');
			msg = $("<spring:message/>");
			msg.attr("code", b);
			console.log(a);
			console.log(msg);
			a.append(msg);
			$("div [class=rel100]").prepend(a);
		}
		$("#jMenu").jMenu();
		var options = {
			message : document.getElementById("cookiesMsg").textContent,
			moreinfo : "views/misc/cookies.jsp"
		};
		var cb = new Cookiebanner(options);
		cb.run();

	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}

	function relativeRedir(loc) {
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
				loc = loc.substr(1);
			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
</script>

</head>

<body>

	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<h1>
			<tiles:insertAttribute name="title" />
		</h1>
		<tiles:insertAttribute name="body" />
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>
		<jstl:if test="${messageCode != null}">
			<br />
			<span class="message"><spring:message code="${messageCode}" /></span>
		</jstl:if>
		<span class="message" id="errorMsg"><spring:message
				code="forbbiden.access.error" /></span>
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>
	<spring:message code="cookies.msg" var="msg" />
	<spring:message code="cookies.about" var="about" />
	<p style="display: none;" id="cookiesMsg">
		<jstl:out value="${msg}" />
	</p>
	<p style="display: none;" id="cookiesAbout">
		<jstl:out value="${about}" />
	</p>
</body>
</html>
