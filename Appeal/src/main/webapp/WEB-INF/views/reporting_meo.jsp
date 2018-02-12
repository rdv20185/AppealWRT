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
			$( "#db" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#de" ).datepicker({dateFormat:'dd.mm.yy'});
			
			$( "#db_am" ).datepicker({dateFormat:'yymm'});
			$( "#de_am" ).datepicker({dateFormat:'yymm'});
			
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
<h2>Отчеты МЭО</h2>
</div>
<div id = main2>
	<form:form method="post" action="" commandName="dateReport">
	<form:errors path="*" cssClass="errorblock" element="div" />
	</form:form>
	
	<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
	<h3>Сведения о количестве и стоимости абортов, проведенных за счет средств обязательного медицинского страхования</h3>
	<h5>Данный отчет делает выборку на основе даты лечения. При переходе между годами учитывайте вероятность подачи реестров на оплату задним числом.</h5>
	<h5   style="margin:3px; color: red;">- 20.04.2017 Внесены изменения в отчет (мбк О07 убрана из сумм шестой строки отчета) За март нарастающим итогом по измененному алгоритму (commit 290599b23daaab43368efce330e97556b68a977a)</h5>
	<form:form method="post" action="report_meo_abortion.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
		<table cellspacing='15'>
			<tr>
				<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
				<form:input id="db" path="dateBegin" class='reportParam' /></td>
				<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
				<form:input id="de" path="dateEnd" class='reportParam' /></td>
			</tr>
			<tr>	
				<td>
				Выбирете базу пролеченных:
				</td>>
				<td>
					<form:select class="css-input" path="typeQuery">
	      					<form:option value="Abortion 2018 year.sql" label="Collect2018" />
							<form:option value="Abortion 2017 year.sql" label="Collect2017" />
					</form:select>
				</td>
				
			</tr>
			<tr>
				<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			</tr>
		</table>
	</form:form>
	<br>
	<a href="<c:url value="/cooked_report_meo_abortion" />">Скачать</a>
	<br><br><hr/>
	
	<h3>Распределение числа выездов бригад скорой медицинской помощи ПО ВРЕМЕНИ доезда</h3>
	<h5>Выборка работает по дате подачи реестров (не по дате лечения)<br>
	ПРИМЕЧАНИЕ: Даты для полного годового отчета будут с 202501 по 202601 (необходим первый месяц следующего  года)</h5>
	<h5 style="margin:3px; color: red;">Данные за январь, февраль не снимать (формировались в "ручном режиме") То есть формирование отчета осуществлять с 01.03.2017</h5>
	<form:form method="post" action="report_meo_ambulance.html" commandName="dateReport" onsubmit="subDisableButton('submit');">
		<table cellspacing='15'>
			<tr>
				<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label>
				<form:input id="db_am" path="dateBegin" class='reportParam' /></td>
				<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label>
				<form:input id="de_am" path="dateEnd" class='reportParam' /></td>
			</tr>
			<tr>	
				<td>
				Выбирете базу пролеченных:
				</td>>
				<td>
					<form:select class="css-input" path="typeQuery">
	      					<form:option value="Collect2018" label="Collect2018" />
							<form:option value="Collect2017" label="Collect2017" />
					</form:select>
				</td>
				
			</tr>
			<tr>
				<td><input id="submit" type="submit" value="<spring:message code="label.report"/>" /></td>
			</tr>
		</table>
	</form:form>
	<br>
	<a href="<c:url value="/cooked_report_meo_ambulance" />">Скачать</a>
	<br><br><hr/>
	</sec:authorize>
</div>
</body>
<script>
	subEnableButton('submit');
	subEnableButton('reportAppealPay');
	subEnableButton('reportConsultOther');
</script>
</html>
