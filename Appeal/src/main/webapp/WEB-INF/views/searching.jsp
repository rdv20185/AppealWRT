<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% 
response.setHeader("Cache-Control","cache"); //HTTP 1.1 
response.setHeader("Pragma","cache"); //HTTP 1.0 
response.setDateHeader ("Expires", 10000); //prevents caching at the proxy server 
%> 


<html>
<head>
	<link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/style2.css"/>" type="text/css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome-4.6.1/css/font-awesome.min.css">
	<link rel="stylesheet" href="<c:url value="/resources/css/bliking.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/menu.css"/>" type="text/css"/>
	
	<link rel="stylesheet" href="<c:url value="/resources/css/tab_search.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value="/resources/jquery/ui/1.11.2/themes/smoothness/jquery-ui.css"/>">
	<script src="<c:url value="/resources/jquery/jquery-1.10.2.js"/>"></script>
	<script src="<c:url value="/resources/jquery/ui/1.11.2/jquery-ui.js"/>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/user/expir_session.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/user/for_search.js"></script>
	
	<c:url var="findTypesURL" value="/types" />
	<c:url var="findCausesURL" value="/causes" />
	<c:url var="findRectifs1URL" value="/rectifs1" />
	<c:url var="findRectifs2URL" value="/rectifs2" />
	<c:url var="findRectifs3URL" value="/rectifs3" />
	<c:url var="findRectifs4URL" value="/rectifs4" />
	
	<script type="text/javascript">
	$(document).ready(function() {
		
			$.getJSON('${findTypesURL}', {
				ajax : 'true'
			}, function(data) {
				var html = '<option value="0"></option>';
				var len = data.length;
				for ( var i = 0; i < len; i++) {
					html += '<option value="' + data[i].id + '">'
							+ data[i].name + '</option>';
				}
				html += '</option>';

				$('#type').html(html);
			});
		});
	</script>
	
	<script type="text/javascript">
	$(document).ready(function() { 
		$('#type').change(
				function() {
					$.getJSON('${findCausesURL}', {
						typeName : $(this).val(),
						ajax : 'true'
					}, function(data) {
						var html = '<option value="0"></option>';
						var len = data.length;
						for ( var i = 0; i < len; i++) {
							html += '<option value="' + data[i].id + '">'
									+ data[i].name + '</option>';
						}
						html += '</option>';
	
						$('#cause').html(html);
					});
				});
		
		
			
	});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#cause").change(onSelectChange);
		});
	
		function onSelectChange() {
			var selected = $("#cause option:selected");		
			var output = "";
			if(selected.val() != 0){
				output = selected.text();
			}
			$("#output").html(output);
		}
	</script>
	
	<script type="text/javascript">
	$(document).ready(function() { 

		
			$("#search_form").submit(function(event) {
				if(valid()){	
					var btn= $(this).find("input[type=submit]:focus").val();
					console.log('@@ '+btn);
					// Prevent the form from submitting via the browser.
					event.preventDefault();
				}
			
					
		});
		
		$(function() {
            $("#inbound_div").tooltip();
         });
		
		$('#cause').change(
			function() {
				$.getJSON('${findRectifs1URL}', {
					causeName : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';

					$('#rectif1').html(html);
				});
			});
		
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#rectif1").change(onSelectChange);
		});
	
		function onSelectChange() {
			var selected = $("#rectif1 option:selected");		
			var output = "";
			if(selected.val() != 0){
				output = selected.text();
			}
			$("#output").html(output);
		}
	</script>
	
	<script type="text/javascript">
	$(document).ready(function() { 
		$('#rectif1').change(
			function() {
				$.getJSON('${findRectifs2URL}', {
					rectif1Name : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';

					$('#rectif2').html(html);
				});
			});
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#rectif2").change(onSelectChange);
		});
	
		function onSelectChange() {
			var selected = $("#rectif2 option:selected");		
			var output = "";
			if(selected.val() != 0){
				output = selected.text();
			}
			$("#output").html(output);
		}
	</script>
	
	<script type="text/javascript">
	$(document).ready(function() { 
		$('#rectif2').change(
			function() {
				$.getJSON('${findRectifs3URL}', {
					rectif2Name : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';

					$('#rectif3').html(html);
				});
			});
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#rectif3").change(onSelectChange);
		});
	
		function onSelectChange() {
			var selected = $("#rectif3 option:selected");		
			var output = "";
			if(selected.val() != 0){
				output = selected.text();
			}
			$("#output").html(output);
		}
	</script>
	
	<script type="text/javascript">
	$(document).ready(function() { 
		$('#rectif3').change(
			function() {
				$.getJSON('${findRectifs4URL}', {
					rectif3Name : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0">-</option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';

					$('#rectif4').html(html);
				});
			});
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#rectif4").change(onSelectChange);
		});
	
		function onSelectChange() {
			var selected = $("#rectif4 option:selected");		
			var output = "";
			if(selected.val() != 0){
				output = selected.text();
			}
			$("#output").html(output);
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".hide").hide();
			$(".btn-slide").click(function(){
		        $(".hide").slideToggle("slow");
		        $(this).toggleClass("active");
		    });
		});
	</script>	
	
	
	<script>
		$(function() {
			$( "#dateInput" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateBegin" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#dateEnd" ).datepicker({dateFormat:'dd.mm.yy'});
		});
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
<h3><spring:message code="label.searching" /></h3>

<div class="error_search" style="display:none; margin-bottom: 15px; color:red;"></div>

<form:form method="post" action="search" commandName="petit" name='search_form' id="search_form">

	<table>
		<tr>
			<td><form:label path="id"><spring:message code="label.id" /></form:label></td>
			<td><form:input path="id" /></td>
		
			<!--  прячу	 -->
			<td><form:hidden id="dateInput" path="dateInput" /></td>
			
			<td><form:label path="dateBegin"><spring:message code="label.dateBegin" /></form:label></td>
			<td><form:input id="dateBegin" path="dateBegin" /></td>
			
			<td><form:label path="dateEnd"><spring:message code="label.dateEnd" /></form:label></td>
			<td><form:input id="dateEnd" path="dateEnd" /></td>
			
			<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
			<td><form:label path="username"><spring:message code="label.username" /></form:label></td>
			<td>
				<form:select path="username" id="presentIdhtml">
					<form:option value="0" label="" />
	    			<form:option value="ROLE_SIMAZ" label="Симаз" />
	    			<form:option value="ROLE_ROSNO" label="ВТБ МС" />
	    			<form:option value="ROLE_INGOS" label="Ингосстрах" />
	    			
				</form:select>
				
			</td>
			</sec:authorize>
		</tr>
		<tr>	
			<td><form:label path="sourceId"><spring:message code="label.source" /></form:label></td>
			<td><form:select path="sourceId">
				<form:option value="0" label="" />
    			<form:options items="${sourceList}"/>
			</form:select></td>
			
			<td><form:label path="presentId"><spring:message code="label.present" /></form:label></td>
			<td>
				<form:select path="presentId" id="presentIdhtml">
					<form:option value="0" label="" />
	    			<form:options items="${presentList}"/>
				</form:select>
			</td>
			
			
			<td><form:label path="letterNum"><spring:message code="label.letterNum" /></form:label></td>
			<td><form:input path="letterNum" /></td>
			
			<td><form:label path="letterDate"><spring:message code="label.letterDate" /></form:label></td>
			<td><form:input path="letterDate" /></td>

			<td><form:label path="conectId"><spring:message code="label.conect" /></form:label></td>
			<td><form:select path="conectId">
				<form:option value="0" label="" />
    			<form:options items="${conectList}"/>
			</form:select></td>

			<!--<td><form:label path="intermedId"><spring:message code="label.intermed" /></form:label></td>
			<td><form:select path="intermedId">
    			<form:options items="${intermedList}"/>
			</form:select></td>-->

		</tr>
		<tr>
			<td><form:label path="surname"><spring:message code="label.surname" /></form:label></td>
			<td><form:input path="surname" /></td>

			<td><form:label path="name"><spring:message code="label.name" /></form:label></td>
			<td><form:input path="name" /></td>

			<td><form:label path="patrony"><spring:message code="label.patrony" /></form:label></td>
			<td><form:input path="patrony" /></td>
		</tr>
		<tr>
			<td><form:label path="policy"><spring:message code="label.policy" /></form:label></td>
			<td><form:input path="policy" /></td>

			<td><form:label path="tel"><spring:message code="label.tel" /></form:label></td>
			<td><form:input path="tel" /></td>

			<td><form:label path="adress"><spring:message code="label.adress" /></form:label></td>
			<td><form:input path="adress" /></td>
		</tr>
		<tr>
			<td><form:label path="terId"><spring:message code="label.ter" /></form:label></td>
			<td><form:select path="terId">
				<form:option value="0" label="" />
				<form:option value="54" label="Новосибирскaя область" />
    			<form:options items="${terList}"/>
			</form:select></td>
	
			<td><form:label path="terAnswerId"><spring:message code="label.terAnswer" /></form:label></td>
			<td><form:select path="terAnswerId">
				<form:option value="0" label="" />
				<form:option value="54" label="Новосибирскaя область" />				
    			<form:options items="${terList}"/>
			</form:select></td>
			
			<td><form:label path="tematsprId"><spring:message code="label.tematspr" /></form:label></td>
		<td><form:select path="tematsprId">
			<form:option value="0" label="" />
   		
		</form:select></td>
		
		</tr>
	</table>
	<table>	
		<tr>
			<td><form:label path="typeId"><spring:message code="label.type" /></form:label></td>
			<td><form:select id="type" path="typeId"></form:select></td>
			<td><div id="inbound_div" style="display: -webkit-inline-box;"  title="Отметка покажет обращения которые в работе, т.е. либо мигают желтым,либо закрашены желтым. Если отметка не проставлена,то обращения которые завершены, в случае с  письменными жалобами - проставлена дата исходщего сообщения (т.е. отправлен ответ)">В работе <input type="checkbox" name="searchcheckinbound" id="searchcheckinbound"  value="checkinbound"></div></td>
			<td><div id="overdueappeal_div" style="display: -webkit-inline-box;"  title="Наличие отметки покажет обращения у которых превышена дата окончательного или промежуточного ответа (в случае письменного обращения) или же в случае устного обращения покажет не завершенный статус (то есть можно допустить что устное обращение (звонок) не обратан или вовсе не было общения с обратившимся)">Просроченные обращения <input type="checkbox" name="overdueappeal" id="overdueappeal"  value="overdueappeal"></div></td>
			<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
			<td>
				<form:select style="margin-left: 10px;" id="claim_inshur" path="blockger2016.claim_inshur" >
					<option value="">С претензией к СМО</option>
					<form:options items="${insurList}"/>
				</form:select>
			</td>	
			</sec:authorize>
		</tr>
		<tr>			
			<td><form:label path="causeId"><spring:message code="label.cause" /></form:label></td>
			<td><form:select id="cause" path="causeId">
			</form:select></td>
		</tr>
		<tr>			
			<td><form:label path="rectif1Id"><spring:message code="label.rectif1" /></form:label></td>
			<td><form:select id="rectif1" path="rectif1Id">
			</form:select></td>
		</tr>
	</table>
	<div class='hide'>
	<table>			
		<tr>	
			<td><form:label path="rectif2Id"><spring:message code="label.rectif2" /></form:label></td>
			<td><form:select id="rectif2" path="rectif2Id">
			</form:select></td>
		</tr>
		<tr>	
			<td><form:label path="rectif3Id"><spring:message code="label.rectif3" /></form:label></td>
			<td><form:select id="rectif3" path="rectif3Id">
			</form:select></td>
		</tr>
		<tr>	
			<td><form:label path="rectif4Id"><spring:message code="label.rectif4" /></form:label></td>
			<td><form:select id="rectif4" path="rectif4Id">
			</form:select></td>
		</tr>
	</table>
	</div>
	<div class='hide'>
	<table>
		<tr>
			<td><form:label path="last1"><spring:message code="label.last1" /></form:label></td>
			<td><form:input path="last1" /></td>
	
			<td><form:label path="last2"><spring:message code="label.last2" /></form:label></td>
			<td><form:input path="last2" /></td>
			
			<td><form:label path="hspId"><spring:message code="label.hsp" /></form:label></td>
			<td><form:select path="hspId">
				<form:option value="0" label="" />
    			<form:options items="${hspList}"/>
			</form:select></td>
		</tr>
	</table>
	</div>
	<div class='hide'>
	<table>		
		<tr>
			<td><form:label path="moId"><spring:message code="label.mo" /></form:label></td>
			<td><form:select path="moId">
				<form:option value="0" label="" />
    			<form:options items="${moList}"/>
			</form:select></td>
			
			<td><form:label path="insurId"><spring:message code="label.insur" /></form:label></td>
			<td><form:select path="insurId">
				<form:option value="0" label="" />
    			<form:options items="${insurList}"/>
			</form:select></td>
		
			<!--<td><form:label path="placeId"><spring:message code="label.place" /></form:label></td>
			<td><form:select path="placeId">
    			<form:options items="${placeList}"/>
			</form:select></td>-->
		</tr>
	</table>
	</div>
	<div class='hide'>
	<table>
		<tr>
			<td><form:label path="validId"><spring:message code="label.valid" /></form:label></td>
			<td><form:select path="validId">
				<form:option value="0" label="" />
    			<form:options items="${validList}"/>
			</form:select></td>
	
			<td><form:label path="compens"><spring:message code="label.compens" /></form:label></td>
			<td><form:input id="compensDate" path="compens" /></td>
	
			<td><form:label path="satisf"><spring:message code="label.satisf" /></form:label></td>
			<td><form:input path="satisf" /></td>
	
			<td><form:label path="compensSource"><spring:message code="label.compensSource" /></form:label></td>
			<td><form:input path="compensSource" /></td>
	
			<td><form:label path="compensCode"><spring:message code="label.compensCode" /></form:label></td>
			<td><form:input path="compensCode" /></td>
	
			<td><form:label path="compensSum"><spring:message code="label.compensSum" /></form:label></td>
			<td><form:input path="compensSum" /></td>
		</tr>
	</table>
	</div>
	<div class='hide'>
	<table>		
		<tr>
			<td><form:label path="propos"><spring:message code="label.propos" /></form:label></td>
			<td><form:input path="propos" /></td>
		</tr>
	</table>
	</div>
	<br>
	<table>
		<tr>
			<td><input name="more_button" class='btn-slide' type="button" value="<spring:message code="label.more"/>"/></td>
			<td colspan="2"><input name="ok_button" type="submit" value="<spring:message code="label.searchpetit"/>" /></td>
		</tr>
	</table>
</form:form>

<h3><spring:message code="label.title" /></h3><br>
<c:if test="${searchListSize ne null}">
<h5 id='searchParam'><spring:message code="label.searchParam" />:
<c:if test="${petitParam.type.typeName ne null}"><spring:message code="label.type" /> - <c:out value="${petitParam.type.typeName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.cause.causeName ne null}"><spring:message code="label.cause" /> - <c:out value="${petitParam.cause.causeName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.rectif1.rectif1Name ne null}"><spring:message code="label.rectif1" /> - <c:out value="${petitParam.rectif1.rectif1Name}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.rectif2.rectif2Name ne null}"><spring:message code="label.rectif2" /> - <c:out value="${petitParam.rectif2.rectif2Name}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.rectif3.rectif3Name ne null}"><spring:message code="label.rectif3" /> - <c:out value="${petitParam.rectif3.rectif3Name}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.rectif4.rectif4Name ne null}"><spring:message code="label.rectif4" /> - <c:out value="${petitParam.rectif4.rectif4Name}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.num ne ''}"><spring:message code="label.id" /> - <c:out value="${petitParam.num}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.dateBegin ne ''}"><spring:message code="label.dateBegin" /> - <c:out value="${petitParam.dateBegin}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.dateEnd ne ''}"><spring:message code="label.dateEnd" /> - <c:out value="${petitParam.dateEnd}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.dateInput ne ''}"><spring:message code="label.dateInput" /> - <c:out value="${petitParam.dateInput}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.username ne ''}"><spring:message code="label.username" /> - <c:out value="${petitParam.username}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.source.sourceName ne null}"><spring:message code="label.source" /> - <c:out value="${petitParam.source.sourceName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.present.presentName ne null}"><spring:message code="label.present" /> - <c:out value="${petitParam.present.presentName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.letterNum ne ''}"><spring:message code="label.letterNum" /> - <c:out value="${petitParam.letterNum}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.letterDate ne ''}"><spring:message code="label.letterDate" /> - <c:out value="${petitParam.letterDate}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.conect.conectName ne null}"><spring:message code="label.conect" /> - <c:out value="${petitParam.conect.conectName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.surname ne ''}"><spring:message code="label.surname" /> - <c:out value="${petitParam.surname}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.name ne ''}"><spring:message code="label.name" /> - <c:out value="${petitParam.name}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.patrony ne ''}"><spring:message code="label.patrony" /> - <c:out value="${petitParam.patrony}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.policy ne ''}"><spring:message code="label.last1" /> - <c:out value="${petitParam.policy}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.tel ne ''}"><spring:message code="label.tel" /> - <c:out value="${petitParam.tel}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.adress ne ''}"><spring:message code="label.adress" /> - <c:out value="${petitParam.adress}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.ter.terName ne null}"><spring:message code="label.ter" /> - <c:out value="${petitParam.ter.terName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.terAnswer.terName ne null}"><spring:message code="label.terAnswer" /> - <c:out value="${petitParam.terAnswer.terName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.tematspr.tematsprName ne null}"><spring:message code="label.tematspr" /> - <c:out value="${petitParam. tematspr. tematsprName }"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.last1 ne 0}"><spring:message code="label.last1" /> - <c:out value="${petitParam.last1}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.last2 ne 0}"><spring:message code="label.last2" /> - <c:out value="${petitParam.last2}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.hsp.hspName ne null}"><spring:message code="label.hsp" /> - <c:out value="${petitParam.hsp.hspName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.mo.moName ne null}"><spring:message code="label.mo" /> - <c:out value="${petitParam.mo.moName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.insur.insurName ne null}"><spring:message code="label.insur" /> - <c:out value="${petitParam.insur.insurName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.valid.validName ne null}"><spring:message code="label.valid" /> - <c:out value="${petitParam.valid.validName}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.compens ne ''}"><spring:message code="label.compens" /> - <c:out value="${petitParam.compens}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.satisf ne ''}"><spring:message code="label.satisf" /> - <c:out value="${petitParam.satisf}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.compensSource ne ''}"><spring:message code="label.compensSource" /> - <c:out value="${petitParam.compensSource}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.compensCode ne ''}"><spring:message code="label.compensCode" /> - <c:out value="${petitParam.compensCode}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.compensSum ne ''}"><spring:message code="label.compensSum" /> - <c:out value="${petitParam.compensSum}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
<c:if test="${petitParam.propos ne ''}"><spring:message code="label.propos" /> - <c:out value="${petitParam.propos}"/>;&nbsp&nbsp&nbsp&nbsp</c:if>
</h5><br>
<h5>Найдено: ${searchListSize}</h5><br></c:if>
<a href="generate/excel.xls">Экспорт в Excel</a>  
<c:if test="${!empty searchList}">
	<table class="tab_search" style="margin-top:10px;">
	<thead>
		<tr>
			<th><spring:message code="label.id" /></th>      
		    <th><spring:message code="label.dateInput" /></th>
		    <th>Дата письма</th>
		    <th>Дата завершения обращения</th>
   		    <!-- <th><spring:message code="label.dateBegin" /></th>
		    <th><spring:message code="label.dateEnd" /></th>-->  
		    <th><spring:message code="label.source" /></th>
		    <th>Тип МО</th>
		    <th class="cuting2"><spring:message code="label.present" /></th>
		    <th><spring:message code="label.letterNum" /></th>
		    <th class="cuting"><spring:message code="label.mo" /></th>
		    <!--<th><spring:message code="label.letterDate" /></th>
		    <th><spring:message code="label.conect" /></th>
		    <th><spring:message code="label.intermed" /></th>-->
		    <th class="cuting3"><spring:message code="label.type" /></th>
		    <th class="cuting"><spring:message code="label.surname" /></th>
		    <th class="cuting2"><spring:message code="label.name" /></th>
		    <!--<th><spring:message code="label.patrony" /></th>
		    <th><spring:message code="label.policy" /></th>
		    <th><spring:message code="label.tel" /></th>
		    <th><spring:message code="label.adress" /></th> -->
		    <th class="cuting2"><spring:message code="label.ter" /></th>
		    <!--<th><spring:message code="label.terAnswer" /></th>
		    <th><spring:message code="label.last1" /></th>
		    <th><spring:message code="label.last2" /></th>
		    <th><spring:message code="label.insur" /></th>
		    <th><spring:message code="label.place" /></th>-->
		    <th class="cuting2"><spring:message code="label.cause" /></th>
		    <th class="cuting3"><spring:message code="label.rectif1" /></th>
		    <!-- <th><spring:message code="label.rectif2" /></th>
		    <th><spring:message code="label.rectif3" /></th>
		    <th><spring:message code="label.rectif4" /></th>-->
		    <th  class="cuting3"><spring:message code="label.valid" /></th>
		    <!-- <th><spring:message code="label.compens" /></th>-->
		    <th  class="cuting3"><spring:message code="label.satisf" /></th>
		    <th><spring:message code="label.compensSum" /></th>
		    <!-- <th><spring:message code="label.compensSource" /></th>
		    <th><spring:message code="label.compensCode" /></th>
		    
		    <th><spring:message code="label.propos" /></th>-->
		    <th>Регистратор</th>
		    <th>Исполнитель</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
				
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${searchList}" var="petit">
			<c:set var="statecl" value="${petit.blockger2016.state}"/>
  			
  			<c:if test="${(statecl == 3 || statecl == 4)}">
		  		<c:set value="someclass3" var="cssClass"></c:set>
			</c:if> 
			<c:if test="${(statecl == 1)}">
	  			<c:set value="someclass blink" var="cssClassonUser"></c:set>
	  			<c:set value="" var="cssClass"></c:set>
			</c:if>
			<c:if test="${(statecl != 1)}">
	  			<c:set value="" var="cssClassonUser"></c:set>
			</c:if>
			<c:if test="${(statecl == 2)}">
	  			<c:set value="someclass2" var="cssClass"></c:set>
			</c:if> 
		
			<tr  class="${cssClass}">
				<td  align="center">${petit.id} ${petit.num}</td>      
			    <td>${petit.dateInput}</td>
			    <td>${petit.letterDate}</td>
			    <td>${petit.blockger2016.date_end}</td>
				<!-- <td>${petit.dateBegin}</td>
			    <td>${petit.dateEnd}</td>-->  
			    <td>${petit.source.sourceName}</td>
			    <td>${petit.hsp.hspName}</td>
			    <td class="cuting2">${petit.present.presentName}</td>
			    <td>${petit.letterNum}</td>
			    <td class="cuting">${petit.mo.moName}</td>
			    <!--<td>${petit.letterDate}</td>
			    <td>${petit.conect.conectName}</td>
			    <td>${petit.intermedId}</td>-->
			    <td class="cuting3">${petit.type.typeName}</td>
			    <td class="cuting">${petit.surname}</td>
			    <td class="cuting2">${petit.name}</td>
			    <!--<td>${petit.patrony}</td>
			    <td>${petit.policy}</td>
			    <td>${petit.tel}</td>
			    <td>${petit.adress}</td> -->
			    <td class="cuting2">${petit.ter.terName}</td>
			    <td class="cuting2">${petit.tematspr.tematsprName}</td>
			    <!--<td>${petit.terAnswer.terName}</td>
			    <td>${petit.last1}</td>
			    <td>${petit.last2}</td>
			    <td>${petit.insurId}</td>	
			    <td>${petit.placeId}</td>-->
			    <td class="cuting2">${petit.cause.causeName}</td>
			    <td class="cuting3">${petit.rectif1.rectif1Name}</td>
			   	<!-- <td>${petit.rectif2.rectif2Name}</td>
			    <td>${petit.rectif3.rectif3Name}</td>
			    <td>${petit.rectif4.rectif4Name}</td>-->
			    <td class="cuting3">${petit.valid.validName}</td>
			    <!-- <td>${petit.compens}</td>-->
			    <td class="cuting3">${petit.satisf}</td>
			    <td>${petit.compensSum}</td>
			    <!-- <td>${petit.compensSource}</td>
			    <td>${petit.compensCode}</td>
			    
			    <td>${petit.propos}</td>-->
			    <td>${petit.blockger2016.regname}</td>
				<td class="${cssClassonUser}">${petit.username}</td>	
				
				 <td><a  href="nightcallfile/${petit.id}" title="Прослушать"><i class="fa fa-headphones fa-2x"></i></a></td>
				 <td><a href="more/${petit.id}" title="Подробнее"><i class="fa fa-wpforms   fa-2x" aria-hidden="true"></i></a></td>
			    <c:if test="${(statecl != 4)}">
			    	<td><a id="iddel" onclick='del("${petit.id}","${role}")'  title="Удалить"><i class="fa fa-trash-o fa-2x"></i></a></td>
					<td><a id="iddel" href="refresh/${petit.id}" title="Редактировать"><i class="fa fa-pencil-square-o  fa-2x" aria-hidden="true"></i></a></td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<c:if test="${(petit.presentId == 2)}">
						    <c:if test="${(statecl != 2)}">
								<td><a id="iddel" onclick='closemes_search("${petit.id}","${petit.blockger2016.state}",this)' title="Закрыть обращение"><i class="fa fa-unlock  fa-2x" aria-hidden="true"></i></a></td>
							</c:if>
							<c:if test="${(statecl == 2)}">
								<td><i class="fa fa-unlock  fa-2x noactive" aria-hidden="true"></i></td>
							</c:if>
						</c:if>
						<c:if test="${(petit.presentId != 2)}">
							<td><a id="iddel" onclick='closemes_search("${petit.id}","${petit.blockger2016.state}",this)' title="Закрыть обращение"><i class="fa fa-unlock  fa-2x" aria-hidden="true"></i></a></td>
						</c:if>
					</sec:authorize>
					<sec:authorize access="!hasRole('ROLE_ADMIN')">
						<td><i class="fa fa-unlock  fa-2x noactive" aria-hidden="true"></i></td>
					</sec:authorize>
				</c:if>
				
				<c:if test="${(statecl == 4)}">
					<td><i class="fa fa-trash-o fa-2x noactive"></i></td>
					<td><i class="fa fa-pencil-square-o  fa-2x noactive" aria-hidden="true"></i></td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<td><a id="iddel" onclick='openmes("${petit.id}","${role}")' title="Восстановить закрытое обращение"><i class="fa fa-lock  fa-2x" aria-hidden="true"></i></a></td>
					</sec:authorize>
					<sec:authorize access="!hasRole('ROLE_ADMIN')">
						<td><i class="fa fa-lock  fa-2x noactive" aria-hidden="true"></i></td>
					</sec:authorize>
				</c:if>
									    

			</tr>
		</c:forEach>
	</tbody>
	</table>
</c:if>
</div>
</body>
<script>
	document.getElementById('compensDate').value= ''
</script>
</html>