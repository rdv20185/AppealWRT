<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<title><spring:message code="label.title" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value="/resources/jquery/ui/1.11.2/themes/smoothness/jquery-ui.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/menu.css"/>" type="text/css"/>
	<script src="<c:url value="/resources/jquery/jquery-1.10.2.js"/>"></script>
	<script src="<c:url value="/resources/jquery/ui/1.11.2/jquery-ui.js"/>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/user/expir_session.js"></script>
	<script>
		$(function() {
			$( "#dateBegin" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateEnd" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateBeginAppealPay" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateEndAppealPay" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateBeginConsultOther" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateEndConsultOther" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateBeginCountDetail" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateEndCountDetail" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#db" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#de" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#di3" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#de3" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#db_1" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#de_1" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#db_2" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#de_2" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#de_3" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#de_4" ).datepicker({dateFormat:'dd.mm.yy'});
			
		});
	</script>
	<script>
		function subDisableButton(buttonId) { 
			var obj = document.getElementById(buttonId);
			if (obj) { obj.disabled = true; } 
		}
	</script>
	<script>
		function subEnableButton(buttonId) {
			var obj = document.getElementById(buttonId); 
			if (!obj) { obj.disabled = false; };
		}
	</script>
</head>
<body>
<div id = top-menu>
<a href="<c:url value="/logout" />">
	<spring:message code="label.logout" />
</a>
<a href="<c:url value="/index" />">
	<spring:message code="label.index" />
</a>
<a href="<c:url value="/searching" />">
	<spring:message code="label.searching" />
</a>
<a href="<c:url value="/reporting" />">
	<spring:message code="label.report" />
</a>
</div>
<div id = main-menu>  
<h2><spring:message code="label.title" /></h2>
</div>
<div id = main2>
<form:form method="post" action="" commandName="dateReport">
<form:errors path="*" cssClass="errorblock" element="div" />
</form:form>

<h3><spring:message code="label.pg_form" /></h3>
<form:form method="post" action="report.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
	<table cellspacing='15'>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="dateBegin" path="dateBegin" class='reportParam' /></td>
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="dateEnd" path="dateEnd" class='reportParam' /></td>
			<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			
			<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
				<td><form:checkbox path="insurcomp" value="smo_ingos"/>Ингосстрах</td>
				<td><form:checkbox path="insurcomp" value="smo_rosno"/>ВТБ-МС</td>
				<td><form:checkbox path="insurcomp" value="smo_simaz"/>СимазМед</td>
			</sec:authorize>
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/report_1_1" />">
	<spring:message code="label.pg_form_1_1" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/report_1_2" />">
	<spring:message code="label.pg_form_1_2" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/report_2_1" />">
	<spring:message code="label.pg_form_2_1" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/report_2_3" />">
	<spring:message code="label.pg_form_2_3" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<br><br><hr/>

<h3>Нагрузка на страхового представителя 1,2 и 3 уровней</h3>
<form:form method="post" action="report_power_sp.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
	<table cellspacing='15'>
		<tr>
		<td>Минимальная дата должна быть не ранее  16.06.2017</td>
		</tr>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="de_3" path="dateBegin" class='reportParam' /></td>
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="de_4" path="dateEnd" class='reportParam' /></td>
			<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			
			<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
				<td><form:checkbox path="insurcomp" value="smo_ingos"/>Ингосстрах</td>
				<td><form:checkbox path="insurcomp" value="smo_rosno"/>ВТБ-МС</td>
				<td><form:checkbox path="insurcomp" value="smo_simaz"/>СимазМед</td>
			</sec:authorize>
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/report_power_sp_file" />">Скачать</a>

<br><br><hr/>

<h3>Таблица 9. Сведения о деятельности страховых представителей</h3>
<form:form method="post" action="report_strax3.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
	<table cellspacing='15'>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="di3" path="dateBegin" class='reportParam' /></td>
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="de3" path="dateEnd" class='reportParam' /></td>
			<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			
			<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
				<td><form:checkbox path="insurcomp" value="smo_ingos"/>Ингосстрах</td>
				<td><form:checkbox path="insurcomp" value="smo_rosno"/>ВТБ-МС</td>
				<td><form:checkbox path="insurcomp" value="smo_simaz"/>СимазМед</td>
			</sec:authorize>
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/report_strax3_formreport" />">Скачать</a>
<br><br><hr/>

<h3>Отчет по работе экспертизы качества медицинской помощи, проведенной по случаям оказания МП при злокачественных новообразованиях,сопровождающихся выраженным болевым синдромом</h3>
<form:form method="post" action="drugs.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
	<table cellspacing='15'>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="db_2" path="dateBegin" class='reportParam' /></td>
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="de_2" path="dateEnd" class='reportParam' /></td>
			<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			
			<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
				<td><form:checkbox path="insurcomp" value="smo_ingos"/>Ингосстрах</td>
				<td><form:checkbox path="insurcomp" value="smo_rosno"/>Росно</td>
				<td><form:checkbox path="insurcomp" value="smo_simaz"/>СимазМед</td>
			</sec:authorize>
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/report_drugs.html" />">Скачать</a>
<br><br><hr/>

<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
<h3><spring:message code="label.call_centr" /></h3>
<form:form method="post" action="report_call.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
	<table cellspacing='15'>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="db" path="dateBegin" class='reportParam' /></td>
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="de" path="dateEnd" class='reportParam' /></td>
			
			<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/report_cc" />">
	<spring:message code="label.call_cc" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/report_cc2" />">
	<spring:message code="label.call_calc" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;

<br><br><hr/>
</sec:authorize>



<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
<h3>Отчет по письменным обращениям граждан, поступившим в ТФОМС НСО</h3>
<form:form method="post" action="report_1.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
	<table cellspacing='15'>
		<tr>
			<h5>Введите период в который обращения поступили (зарегистрированы канцелярией)</h5>		
		</tr>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="db_1" path="dateBegin" class='reportParam' /></td>
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="de_1" path="dateEnd" class='reportParam' /></td>
			
			<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			
			<td><form:checkbox path="insurcomp" value="smo_ingos"/>Ингосстрах</td>
			<td><form:checkbox path="insurcomp" value="smo_rosno"/>Росно</td>
			<td><form:checkbox path="insurcomp" value="smo_simaz"/>СимазМед</td>
			
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/report_1" />">Скачать</a>

<br><br><hr/>
</sec:authorize>



<h3><spring:message code="label.reportAppealPay" /></h3>
<form:form name='reportAppealPay_form' method="post" action="reportAppealPay" commandName="dateReport" onsubmit="subDisableButton('reportAppealPay');">
	<table cellspacing='15'>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="dateBeginAppealPay" path="dateBegin" class='reportParam' /></td>
			
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="dateEndAppealPay" path="dateEnd" class='reportParam' /></td>

			<td><input name='reportAppealPay_button' id="reportAppealPay" type="submit" value="<spring:message code="label.report"/>" /></td>
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/reportAppealPayFile" />">
	<spring:message code="label.download" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<br><br><hr/>

<h3><spring:message code="label.reportConsultOther" /></h3>
<form:form method="post" action="reportConsultOther" commandName="dateReport" onsubmit="subDisableButton('reportConsultOther');">
	<table cellspacing='15'>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="dateBeginConsultOther" path="dateBegin" class='reportParam' /></td>
			
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="dateEndConsultOther" path="dateEnd" class='reportParam' /></td>

			<td><input id="reportConsultOther" type="submit" value="<spring:message code="label.report"/>" /></td>
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/reportConsultOtherFile" />">
	<spring:message code="label.download" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<br><br><hr/>

<h3><spring:message code="label.reportCountDetail" /></h3>
<form:form method="post" action="reportCountDetail" commandName="dateReport" onsubmit="subDisableButton('reportCountDetail');">
	<table cellspacing='15'>
		<tr>
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
			<form:input id="dateBeginCountDetail" path="dateBegin" class='reportParam' /></td>
			
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
			<form:input id="dateEndCountDetail" path="dateEnd" class='reportParam' /></td>
			
			<td><form:label path="clinic"><spring:message code="label.clinic" /></form:label>
			<form:checkbox id="clinicCountDetail" path="clinic" /></td>

			<td><form:label path="eachMedicalOrg"><spring:message code="label.eachMedicalOrg" /></form:label>
			<form:checkbox id="eachMedicalOrgCountDetail" path="eachMedicalOrg" /></td>
			
			<td><input id="reportCountDetail" type="submit" value="<spring:message code="label.report"/>" /></td>
		</tr>
	</table>
</form:form>
<br>
<a href="<c:url value="/reportCountDetailFile" />">
	<spring:message code="label.download" />
</a>&nbsp;&nbsp;&nbsp;&nbsp;
<br><br><hr/>

</div>
</body>
<script>
	subEnableButton('submit');
	subEnableButton('reportAppealPay');
	subEnableButton('reportConsultOther');
</script>
</html>
