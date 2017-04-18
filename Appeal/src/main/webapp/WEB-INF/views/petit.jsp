<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<sec:authentication var="principal" property="principal" />
<sec:authentication var="role" property="principal.authorities" />



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="label.title" /></title>
	
	<link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>" type="text/css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/logosheader.css" />
	<link rel="stylesheet" href="<c:url value="/resources/jquery/ui/1.11.2/themes/smoothness/jquery-ui.css"/>">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome-4.6.1/css/font-awesome.min.css">
	<link rel="stylesheet" href="<c:url value="/resources/css/bliking.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/style2.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/newform.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<c:url value="/resources/css/menu.css"/>" type="text/css"/>
	
	
	<script src="<c:url value="/resources/jquery/jquery-1.10.2.js"/>"></script>
	<script src="<c:url value="/resources/jquery/ui/1.11.2/jquery-ui.js"/>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/libs/sockjs/sockjs.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/libs/stomp-websocket/lib/stomp.min.js"></script>
	<script type="text/javascript"  data-my_var_1="${principal.username}" src="${pageContext.request.contextPath}/resources/user/controller.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/user/expir_session.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/user/other.js"></script>
	
	
	<c:url var="findTypesURL" value="/types" />
	<c:url var="findCausesURL" value="/causes" />
	<c:url var="findRectifs1URL" value="/rectifs1" />
	<c:url var="findRectifs2URL" value="/rectifs2" />
	<c:url var="findRectifs3URL" value="/rectifs3" />
	<c:url var="findRectifs4URL" value="/rectifs4" />
	
	<script>
	function cancelback() {
		parent.history.back();
		};

		
	function numberone() {
		
		
		if($('#sel').val() != '0'){
			
			$('#addpetit').prop('disabled', false);
		}
		else{
			$('#addpetit').prop('disabled', true);
			}
		
	 }	

	function refreshp() {
		location.reload();
	 }	
	function refreshnightcall() {
		location.href='refnc';
	 }	

	function downcall() {
		location.href='nightcallfile';
	 }	

	function changepresentId() {
		if($("#presentId" ).val() !=1){
			$('#inboundLetter').fadeIn();
			
			$('#inbound_from').fadeIn();
			$('#addpetit').fadeOut();
			$('#sel').fadeOut();
		}else{
			$('#inboundLetter').fadeOut();
			$('#inbound_from').fadeOut();
			
			$('#addpetit').fadeIn();
			$('#sel').fadeIn();	
		}
		
	 }
		
	$(document).ready(function() {
		
		$('#testclick').click(function(event){
			$('#info_socket').finish();
			$('#info_socket').animate({opacity: 0.2}, 0 );
			$('#info_socket').animate({opacity: 1}, 0 );
    		$('#info_socket').animate({opacity: 0.0}, 5000 );
    		
		});
		
		$("#info_socket").hover(function() {
			if($('#info_socket').css('opacity') != 0) {
		    	$('#info_socket').stop();
		    	$('#info_socket').css({'opacity':'1'});
			}
		}, function() {
            $(this).animate({opacity: 0.0}, 5000 );
        });
		

		
			/*	Метол из файла contriller.js	*/
			addJS('${role}');
		
			if($("#presentId" ).val() !=1){
				$('#inboundLetter').fadeIn();
				$('#inbound_from').fadeIn();
				
				$('#addpetit').fadeOut();
				$('#sel').fadeOut();
			}else{
				$('#inboundLetter').fadeOut();
				$('#inbound_from').fadeOut();
				
				$('#addpetit').fadeIn();
				$('#sel').fadeIn();
			}
		
			var user = '${principal.username}';
			if(user != 'ernso' && user != 'call5001' && user != 'call5002' && user != 'call5003'
				&& user != 'callnight5001'
				&& user != 'callnight5002'
				&& user != 'callnight5003'){
				$.getJSON('${findTypesURL}', {
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
					}
					html += '</option>';
	
					$('#type').html(html);
				});
			}

			if(user =='callnight5001' || user =='callnight5002' || user =='callnight5003')
			{
				if(location.href.indexOf("index")>=0)
				{
					$('#draggable').hide();
					$('#main').addClass('forrefresh');
					console.log('test ');
				}
				
			}
			
			$('#connectid').click(function(){
				var epr = $( "#connectid option:selected" ).text();
				if(epr.indexOf('ИНТЕРНЕТ') > -1 || epr.indexOf('ПОЧТА') > -1){
					$("#presentId").val(2);
					changepresentId();
					$('#inboundLetter').fadeIn();
				}
				else{
					$("#presentId").val(1);
					changepresentId();
					$('#inboundLetter').fadeOut();
				}
				
			});
	});
		
	</script>
	<script type="text/javascript">
	$(document).ready(function() { 
		// добовляем стиль css если адрес сайта содердит refresh 
		if(window.location.href.indexOf("refresh") > -1) {
				$('#main').addClass('forrefresh');
		    }

		
		$('#type').change(
				function() {
					$.getJSON('${findCausesURL}', {
						typeName : $(this).val(),
						ajax : 'true'
					}, function(data) {
						var html = '<option value="0"></option>';
						var len = data.length;
						for ( var i = 0; i < len; i++) {
							html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
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
		$('#cause').change(
			function() {
				$.getJSON('${findRectifs1URL}', {
					causeName : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
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
						html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
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
						html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
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
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
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

			$(".hide2").hide();
			$(".btn-slide2").click(function(){
		        $(".hide2").slideToggle("slow");
		        $(this).toggleClass("active");
		    });
		});
	</script>
	
	<script>
		$(function() {
			$("#date_redirect").datepicker({dateFormat:'dd.mm.yy'});
		});
	
		$(function() {
			$( "#dateInput" ).datepicker({dateFormat:'dd.mm.yy'});
			if(location.href.indexOf("Appeal/refresh/") < 0) $( "#dateInput" ).datepicker( "setDate", new Date());
		});
		$(function() {
			$( "#datebetween" ).datepicker({dateFormat:'dd.mm.yy'});
		});
		$(function() {
			$( "#date_med_doc" ).datepicker({dateFormat:'dd.mm.yy'});
		});
		$(function() {
			$( "#date_passOmer" ).datepicker({dateFormat:'dd.mm.yy'});
		});
		$(function() {
			$( "#date_receive" ).datepicker({dateFormat:'dd.mm.yy'});
		});
		$(function() {
			$( "#date_response" ).datepicker({dateFormat:'dd.mm.yy'});
		});
		$(function() {
			$( "#date_subresponse" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#date_subresponse1" ).datepicker({dateFormat:'dd.mm.yy'});
			$( "#date_subresponse2" ).datepicker({dateFormat:'dd.mm.yy'});
		});
		
	</script>
	<script>
		//setTimeout ("$('body').animate({opacity: 1}, 3000 );", 10);
		$(document).ready(function() 
		{	
		setInterval(function () {
		
		setTimeout ("$('#tf').removeClass('tfoms').addClass('tfoms2');", 1000);//+4
		setTimeout ("$('#tf').removeClass('tfoms2').addClass('tfoms');", 8000);
		
		setTimeout ("$('#simaz').removeClass('sim').addClass('sim2');", 12000);
		setTimeout ("$('#simaz').removeClass('sim2').addClass('sim');", 19000);
		
		setTimeout ("$('#rosno').removeClass('ro').addClass('ro2');", 23000);
		setTimeout ("$('#rosno').removeClass('ro2').addClass('ro');", 30000);
		
		setTimeout ("$('#ingos').removeClass('in').addClass('in2');", 34000);
		setTimeout ("$('#ingos').removeClass('in2').addClass('in');", 41000);
            
        }, 45000);
		
		
		 $(function() {
             $('#navigation div').stop().animate({'marginLeft':'-2px'},1000);

             $('#navigation > li').hover(
                 function () {
                	 $('.dim').css({display:'block',position:'fixed',height:'100%'});
                     $('div',$(this)).stop().animate({'marginLeft':'-485px'},200);
                 },
                 function () {
                	 $('.dim').css({display:'none'});
                     $('div',$(this)).stop().animate({'marginLeft':'-2px'},200);
                 }
             );
         });
		
		
		});

		 $(function() {
			    $( "#draggable" ).draggable();
			  });
		 
		</script>
	
</head>
<body>
<div class="dim"></div>
<ul id="navigation">
            <li class="home"><div title="в разработке"></div></li>
         </ul>
<div id = top-menu>


  <nav>
      <ul class="menu">
        <li><a href="<c:url value="/logout" />">Выйти из приложения</a></li>
        <li><a href="<c:url value="/index" />">Главная</a></li>
        <li><a href="<c:url value="/searching" />">Поиск</a></li>
        <li><a href="<c:url value="/reporting" />">Создать отчет</a></li>
        
        <li><a href="" class="submenu-link">Документация</a>
          <ul class="submenu">
            <li><a href="">Регламент</a></li>
            <li><a href="<c:url value="/downloadmanual" />">Инструкция пользователя</a></li>
            <li><a href="<c:url value="/downloadreestr" />">Реестр страховых представителей 25.09.16</a></li>
            <li><a href="<c:url value="/downloadreestr1117_1" />">Сводный реестр страховых представителей на 10.04.17</a></li>
          </ul>
        </li>
        <sec:authorize access="hasAnyRole('ROLE_TFOMS')">
        <li><a href="<c:url value="/reportingMEO.html" />">Отчеты МЭО</a></li>
        </sec:authorize>
      </ul>
    </nav>



<div style="float:right; margin-right:15px; font-weight: bold;">
	<i class="fa fa-user" aria-hidden="true"></i> Пользователь: <c:out value="${principal.username}"/>
</div>

</div>

<div id ="main-menu">
<div style="float:left; padding: 10px;">
<i style="margin-left:10px;" class="fa fa-heartbeat fa-2x " aria-hidden="true"></i>
<i style="margin-left:5px;" class="fa fa-phone-square fa-2x" aria-hidden="true"></i>
<i style="margin-left:5px;" class="fa fa-headphones fa-2x" aria-hidden="true"></i>
<h2 style="display: -webkit-inline-box;display: -moz-inline-box;display: -o-inline-box; margin-left:10px;"><spring:message code="label.title" /></h2>
</div>


<div style="float:right; margin-right: 75px;">
		<img src="${pageContext.request.contextPath}/resources/images/tf.png"  id="tf" class="tfoms">
        <img src="${pageContext.request.contextPath}/resources/images/simaz.png" id="simaz" class="sim">
		<img src="${pageContext.request.contextPath}/resources/images/rosno.png" id="rosno" class="ro">
		<img src="${pageContext.request.contextPath}/resources/images/ingos.png" id="ingos" class="in">
		</div>
</div>


<div id ="main">

 <c:if test="${petit.id ne null}">
  			<c:set value="foreditbackgr" var="cssforedit"></c:set>
</c:if>
<c:if test="${petit.id eq null}">
  			<c:set value="register " var="cssforedit"></c:set>
</c:if>


<form:form method="post" action="add" commandName="petit" name='petit_form' class="${cssforedit}" id="draggable">

 

	<form:errors path="*" cssClass="errorblock" element="div" />
	<form:hidden path="id" name='id'/>
	<form:hidden path="blockger2016.filecall"/>
	<form:hidden path="blockger2016.date_plan_end"/> 
	<form:hidden path="num"/>

<h1>
	<c:if test="${petit.id eq null}">
		<spring:message code="label.petits" />
	</c:if>
	<c:if test="${petit.id ne null}">
		<spring:message code="label.nepetits" />
	</c:if>
</h1>

<div class="errorrep" style="display:none; margin-bottom: 15px; color:red;"></div>
<fieldset class="row1">
	<legend>Данные обращения</legend>
	  <p>
	  	<c:if test="${petit.id eq null}">
	      <form:label style="font-weight: bold;" path="dateInput"><spring:message code="label.dateInput" /></form:label>
	      <form:input id="dateInput" path="dateInput"/>
	   	</c:if>
	   	<c:if test="${petit.id ne null}">
			<spring:message code="label.id" /><c:out value="${petit.num}" />
			
			
			<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				<form:label style="font-weight: bold;" path="dateInput"><spring:message code="label.dateInput" /></form:label>
	      		<form:input id="dateInput" path="dateInput"/>	  
			</sec:authorize>
			
			<sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
				<spring:message code="label.dateInput" /><c:out value="${petit.dateInput}" />
				<form:hidden id="tupin" path="dateInput" value="${petit.dateInput}"/>	  
			</sec:authorize>
	   	</c:if>
	   	
	 	<form:label path="sourceId"><spring:message code="label.source" /></form:label>
	    <form:select path="sourceId">
			<form:options items="${sourceList}"/>
		</form:select>
	  </p>
	  <p>
			<form:label path="conectId"><spring:message code="label.conect" /></form:label>
				<form:select path="conectId" id="connectid">
				<form:options items="${conectList}"/>
			</form:select>  
		
		
		<form:label path="presentId"><spring:message code="label.present" /></form:label>
		<form:select id="presentId" onclick='changepresentId()' path="presentId">
	 		<form:options items="${presentList}"/>
		</form:select>
		<form:select id="inbound_from"  path="blockger2016.inbound_from">
		<option value="" disabled selected>От кого</option>
	 		<form:options items="${inbound_fromList}"/>
		</form:select>
	  </p>
<sec:authorize access="hasAnyRole('ROLE_TFOMS','DEVELOPER','ROLE_SMO','ROLE_ADMIN')">	  
	  <p>
	  	<form:label path="letterNum"><spring:message code="label.letterNum" /></form:label>
		<form:input path="letterNum" />
		<form:label path="letterDate"><spring:message code="label.letterDate" /></form:label>
		<form:input path="letterDate" />
	  </p>
</sec:authorize>	  
</fieldset>
<fieldset class="row2">
	<legend>Персональные данные</legend>
	<p>
		<form:label style="font-weight: bold;" path="surname"><spring:message code="label.surname" /></form:label>
		<form:input class="css-input" path="surname" />

		<form:label style="font-weight: bold;" path="name"><spring:message code="label.name" /></form:label>
		<form:input  path="name"/>

		<form:label style="font-weight: bold;"  path="patrony"><spring:message code="label.patrony" /></form:label>
		<form:input class="css-input" path="patrony" />
	</p>
	<p>
		<form:label  style="font-weight: bold;" path="policy"><spring:message code="label.policy" /></form:label>
		<form:input  path="policy" />

		<form:label  style="font-weight: bold;" path="tel"><spring:message code="label.tel" /></form:label>
		<form:input  path="tel"/>

		<form:label  style="font-weight: bold;" path="adress"><spring:message code="label.adress" /></form:label>
		<form:input  path="adress" />	
	</p>
	<p>
		<form:label path="terId"><spring:message code="label.ter" /></form:label>
		<form:select path="terId">
			<form:option value="0" label="" />
   			<form:options items="${terList}"/>
		</form:select>
	
		<form:label path="terAnswerId"><spring:message code="label.terAnswer" /></form:label>
		<form:select path="terAnswerId">
				<form:option value="54" label="54 Новосибирскaя область" />				
    			<form:options items="${terList}"/>
		</form:select>
	</p>
</fieldset>  
<fieldset class="row777">
	<legend>Тип и причина обращения</legend>
	<p>
	<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_SMO','ROLE_ADMIN')">
				<form:label path="typeId"><spring:message code="label.type" /></form:label>
				<span id="typeWarning" style="color:#ff8000">!
					<span style="font-size:8">${petit.type.typeName}</span>
				</span>
			<form:select id="type" path="typeId" onchange="document.getElementById('typeWarning').hidden = true;"></form:select>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ER')">
				<form:label path="typeId"><spring:message code="label.type" /></form:label>
				<span id="typeWarning" style="color:#ff8000">!
					<span style="font-size:8">${petit.type.typeName}</span>
				</span>
			<form:select id="type" path="typeId" onchange="document.getElementById('typeWarning').hidden = true;">
			<form:option value="0" label=""/>
			<form:option value="3" label="Консультация" />
			</form:select>
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_TFOMS')">
			<form:select style="margin-left: 100px;" id="claim_inshur" path="blockger2016.claim_inshur">
			<option value="" disabled selected>Претензия к СМО</option>
			<form:options items="${insurList}"/>
			</form:select>
	</sec:authorize>		
	</p>
	<p>
			<form:label path="causeId"><spring:message code="label.cause" /></form:label>
				<span id="causeWarning" style="color:#ff8000">!
					<span style="font-size:8">${petit.cause.causeName}</span>
				</span>
			<form:select id="cause" path="causeId" onchange="document.getElementById('causeWarning').hidden = true;"></form:select>	
	</p>
	<p>			
			<form:label path="rectif1Id"><spring:message code="label.rectif1" /></form:label>
				<span id="rectif1Warning" style="color:#ff8000">!
					<span style="font-size:8">${petit.rectif1.rectif1Name}</span>
				</span>
			<form:select id="rectif1" path="rectif1Id" onchange="document.getElementById('rectif1Warning').hidden = true;"></form:select>
	</p>
</fieldset>
<sec:authorize access="hasAnyRole('ROLE_TFOMS','DEVELOPER','ROLE_SMO','ROLE_ADMIN')">
<fieldset class="row777">
                <legend>Подробнее</legend>
                <div class="hide">
                <p>
                	
						<form:label path="rectif2Id"><spring:message code="label.rectif2" /></form:label>
						<span id="rectif2Warning" style="color:#ff8000;font-size:8">${petit.rectif2.rectif2Name}</span>
						<form:select id="rectif2" path="rectif2Id" onchange="document.getElementById('rectif2Warning').hidden = true;"></form:select>
						
						<form:label path="rectif3Id"><spring:message code="label.rectif3" /></form:label>
						<span id="rectif3Warning" style="color:#ff8000;font-size:8">${petit.rectif3.rectif3Name}</span>
						<form:select id="rectif3" path="rectif3Id" onchange="document.getElementById('rectif3Warning').hidden = true;"></form:select>

						<form:label path="rectif4Id"><spring:message code="label.rectif4" /></form:label>
						<span id="rectif4Warning" style="color:#ff8000;font-size:8">${petit.rectif4.rectif4Name}</span>
						<form:select id="rectif4" path="rectif4Id" onchange="document.getElementById('rectif4Warning').hidden = true;" ></form:select>
                	
                </p>
                <p>
							
					<form:label path="hspId"><spring:message code="label.hsp" /></form:label>
					<form:select path="hspId">
						<form:option value="0" label="" />
		    			<form:options items="${hspList}"/>
					</form:select>
					
					<form:label path="blockger2016.typempid">Вид МП</form:label>
					<form:select path="blockger2016.typempid" id="typempid">
						<form:options items="${typeMP}"/>
					</form:select>
					
                </p>
                <p>
					<form:label path="moId"><spring:message code="label.mo" /></form:label>
					<form:select path="moId">
						<form:option value="0" label="" />
    					<form:options items="${moList}"/>
					</form:select>
			
					<form:label path="insurId"><spring:message code="label.insur" /></form:label>
					<form:select path="insurId">
						<form:option value="0" label="" />
    					<form:options items="${insurList}"/>
					</form:select>
                </p>
                <p>
					<form:label path="validId"><spring:message code="label.valid" /></form:label>
					<form:select path="validId">
						<form:option value="0" label="" />
    					<form:options items="${validList}"/>
					</form:select>
	
					<form:label path="compens"><spring:message code="label.compens" /></form:label>
					<form:input class="css-input" path="compens" />
	
					<form:label path="satisf"><spring:message code="label.satisf" /></form:label>
					<form:select class="css-input" path="satisf">
	      					<form:option value="" label="" />
							<form:option value="ДА" label="ДА" />
							<form:option value="НЕТ" label="НЕТ" />
					</form:select>
	
					<form:label path="compensSource"><spring:message code="label.compensSource" /></form:label>
					<form:input class="css-input" path="compensSource" placeholder="СМО МО или ТФОМС"/>
	
					<form:label path="compensCode"><spring:message code="label.compensCode" /></form:label>
					<form:input class="css-input" path="compensCode" placeholder="Код дефекта"/>
	
					<form:label path="compensSum"><spring:message code="label.compensSum" /></form:label>
					<form:input class="css-input" path="compensSum" pattern="^[^.]*$" title="Не используйте точку"/>
                </p>
                <p>
					<form:label style="width: 144px;" path="causeNote"><spring:message code="label.causeNote" /></form:label>
					<form:textarea rows="2" cols="100" value="0" path="causeNote" />
                </p>
                </div>
                <p>
                	<input class='btn-slide' type="button" value="<spring:message code="label.more"/>"/>
                </p><br>
</sec:authorize>                
                <div class="hide2">
					<p>
					<c:if test="${petit.bloutboindletter2016.date_between eq null}">
							<form:label style="float:left;" path="bloutboindletter2016.date_between">Дата промежуточного ответа</form:label>
		      				<form:input style="float:left;" id="datebetween"  path="bloutboindletter2016.date_between"/>
					</c:if>
					<c:if test="${petit.bloutboindletter2016.date_between ne null}">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<form:label style="float:left;" path="bloutboindletter2016.date_between">Дата промежуточного ответа</form:label>
		      				<form:input style="float:left;" id="datebetween"  path="bloutboindletter2016.date_between"/>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN')">
							<form:label style="float:left;" path="bloutboindletter2016.date_between">Дата промежуточного ответа</form:label>
							<div style="float:left; font-size:13px;"><c:out value="${petit.bloutboindletter2016.date_between}" /></div>
							<form:hidden path="bloutboindletter2016.date_between" value="${petit.bloutboindletter2016.date_between}"/>
						</sec:authorize>
				   	</c:if>
							
							<form:label style="font-size:13px;" path="bloutboindletter2016.date_passmedDoc">Дата передачи мед.документации в ОМЭР</form:label>
      						<form:input id="date_passOmer" path="bloutboindletter2016.date_passmedDoc"/>
					</p>
					<p>
							<form:label style="float:none;" path="bloutboindletter2016.date_querymedDoc">Дата запроса мед.документации</form:label>
		      				<form:input style="float:none;" id="date_med_doc" path="bloutboindletter2016.date_querymedDoc"/>
					</p>                
	      			<p>
	      				<form:label path="bloutboindletter2016.date_obtainAkt">Дата получения Актов МЭ</form:label>
	      				<form:input id="date_receive" path="bloutboindletter2016.date_obtainAkt"/>
	      			</p>
	      			<p>
	      			<div id="div_response" style="font-size:12px;">
	      				<form:label path="bloutboindletter2016.many[0].date_subquery">Дата дополнительного запроса/ответа</form:label>
	      				<form:input id="date_subresponse"  path="bloutboindletter2016.many[0].date_subquery"/>
	      				<form:label path="bloutboindletter2016.many[0].note">Заметка</form:label>
	      				<form:input type="text"  path="bloutboindletter2016.many[0].note"/>
	      			</div>	
	      			</p>
	      			<p>
	      			<button type="button" id="btn_add_subresponse" title="Добавить дату дополнительного запроса/ответа">+</button>
	      			</p>
	      			<p>
	      			<div id="div_subresponse1">
	      				<form:label path="bloutboindletter2016.many[1].date_subquery">Дата дополнительного запроса/ответа</form:label>
	      				<form:input id="date_subresponse1"  path="bloutboindletter2016.many[1].date_subquery"/>
	      				<form:label path="bloutboindletter2016.many[1].note">Заметка</form:label>
	      				<form:input type="text"  path="bloutboindletter2016.many[1].note"/>
      				</div>
	      			</p>
	      			<p>
	      			<div id="div_subresponse2">
	      				<form:label path="bloutboindletter2016.many[2].date_subquery">Дата дополнительного запроса/ответа</form:label>
	      				<form:input id="date_subresponse2"  path="bloutboindletter2016.many[2].date_subquery"/>
	      				<form:label path="bloutboindletter2016.many[2].note">Заметка</form:label>
	      				<form:input type="text"  path="bloutboindletter2016.many[2].note"/>
	      			</div>	
	      			</p>
	      			<p>	
	      				<form:label path="bloutboindletter2016.date_response">Дата окончательного ответа гражданину</form:label>
	      				<form:input id="date_response" path="bloutboindletter2016.date_response"/>
	      				
	      				<form:label path="bloutboindletter2016.numOutLetter">Номер письма</form:label>
	      				<form:input path="bloutboindletter2016.numOutLetter"/>
	      				
	      				<form:label path="bloutboindletter2016.responsible">Отвественный</form:label>
	      				<form:select path="bloutboindletter2016.responsible" id="responsible">
	      					<form:option value="" label="" />
	      					<form:option value="kuznetsova" label="kuznetsova"/>
							<form:option value="mityanina" label="mityanina" />
							<form:option value="smyvin" label="smyvin" />
							<form:option value="eremina" label="eremina" />
							<form:option value="hamitov" label="hamitov" />
							<form:option value="smo_simaz" label="smo_simaz" />
 							<form:option value="smo_ingos" label="smo_ingos" />
 							<form:option value="smo_ingos_01" label="smo_ingos_01" />
 							<form:option value="smo_rosno_01" label="smo_rosno_01" />
 							<form:option value="smo_rosno_02" label="smo_rosno_02" />
 							<form:option value="smo_rosno_03" label="smo_rosno_03" />
 							<form:option value="smo_rosno_04" label="smo_rosno_04" />
 							<form:option value="smo_rosno_05" label="smo_rosno_05" />
 							<form:option value="smo_rosno_06" label="smo_rosno_06" />
 							<form:option value="smo_rosno_07" label="smo_rosno_07" />
 							<form:option value="smo_rosno_08" label="smo_rosno_08" />
 							<form:option value="smo_rosno_09" label="smo_rosno_09" />
 							<form:option value="smo_rosno_10" label="smo_rosno_10" />
 							<form:option value="smo_rosno_11" label="smo_rosno_11" />
 							<form:option value="smo_rosno_12" label="smo_rosno_12" />
 							<form:option value="smo_rosno_13" label="smo_rosno_13" />
 							<form:option value="smo_rosno_14" label="smo_rosno_14" />
 							<form:option value="smo_rosno_15" label="smo_rosno_15" />
 							<form:option value="smo_rosno_16" label="smo_rosno_16" />
 							<form:option value="smo_rosno_17" label="smo_rosno_17" />
 							<form:option value="smo_rosno_18" label="smo_rosno_18" />
 							<form:option value="smo_rosno_19" label="smo_rosno_19" />
 							<form:option value="smo_rosno_20" label="smo_rosno_20" />
 							<form:option value="smo_rosno_21" label="smo_rosno_21" />
 							<form:option value="smo_rosno_22" label="smo_rosno_22" />
 							<form:option value="smo_rosno_23" label="smo_rosno_23" />
 							<form:option value="smo_rosno_24" label="smo_rosno_24" />
 							<form:option value="smo_rosno_25" label="smo_rosno_25" />
 							<form:option value="smo_rosno_26" label="smo_rosno_26" />
 							<form:option value="smo_rosno_27" label="smo_rosno_27" />
 							<form:option value="smo_rosno_28" label="smo_rosno_28" />
 							<form:option value="smo_rosno_29" label="smo_rosno_29" />
 							<form:option value="smo_rosno_30" label="smo_rosno_30" />
 							<form:option value="smo_rosno_31" label="smo_rosno_31" />
 							<form:option value="smo_rosno_32" label="smo_rosno_32" />
 							<form:option value="smo_rosno_33" label="smo_rosno_33" />
 							<form:option value="smo_rosno_34" label="smo_rosno_34" />
 							<form:option value="smo_rosno_35" label="smo_rosno_35" />
 							<form:option value="smo_rosno_36" label="smo_rosno_36" />
 							<form:option value="smo_rosno_37" label="smo_rosno_37" />
 							<form:option value="smo_rosno_38" label="smo_rosno_38" />
 							<form:option value="smo_rosno_39" label="smo_rosno_39" />
 							<form:option value="smo_rosno_40" label="smo_rosno_40" />
 							<form:option value="smo_rosno_41" label="smo_rosno_41" />
 							<form:option value="smo_rosno_42" label="smo_rosno_42" />
 							<form:option value="smo_rosno_43" label="smo_rosno_43" />
 							<form:option value="smo_rosno_44" label="smo_rosno_44" />
 							<form:option value="smo_rosno_45" label="smo_rosno_45" />
						</form:select>
                	</p>
                	<p>
                		<form:label path="bloutboindletter2016.date_redirect">Дата перенаправления для рассмотрения</form:label>
	      				<form:input id="date_redirect" path="bloutboindletter2016.date_redirect"/>
	      				
	      				<form:label path="bloutboindletter2016.redirect_adress">Адресат</form:label>
							<form:select path="bloutboindletter2016.redirect_adress" id="redirect_adress">
								<form:option value="0" label="" />
								<form:options items="${inbound_fromList}"/>
							</form:select>
							
                	</p>
                	
                </div>
                <p>
                	<input id="inboundLetter" class='btn-slide2' type="button" value="Исходящее письмо"/>
                	
                </p>
</fieldset>

<fieldset class="row777">
                <legend>Выберите действие</legend>
                <p>
                	<c:if test="${petit.id ne null}">
                		<sec:authorize access="hasRole('ROLE_NIGHT')">
							<form:select  id="sel" path="username" onclick="numberone()">
								<form:option label="Назначить"  value="0" />
								<form:options items="${listassign}"/>
							</form:select>
							<input name="cancel_button" onclick="cancelback()" type="button" value="<spring:message code="label.cancelpetit"/>"/>
							
							<input type="submit"  id="addpetit" name="submit" value="Назначить" disabled="disabled" 
								onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
							/>
						
							<input name="submit"  type="submit" value="<spring:message code="label.endpetit"/>" 
								onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
							/>
							
							<a  href="../nightcallfile/${petit.id}" class="blink" style=" float: right; background: none;" title="Прослушать">Прослушать</a>
						</sec:authorize>
						
                		<sec:authorize access="!hasRole('ROLE_NIGHT')">
                		<c:if test="${(petit.presentId == 2)}">
							<input name="cancel_button" onclick="cancelback()" type="button" value="<spring:message code="label.cancelpetit"/>"/>
							
							<input name="submit" type="submit" value="<spring:message code="label.editpetit"/>" 
								onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
							/>
							
							<c:set var="gu" value="${petit.blockger2016.state}"/>
							<c:if test="${(gu < 3)}">	
								<input name="submit" type="submit" value="<spring:message code="label.endpetit"/>" 
									onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
								/>
							</c:if>
						</c:if>
						<c:if test="${(petit.presentId != 2)}">
							<input name="cancel_button" onclick="cancelback()" type="button" value="<spring:message code="label.cancelpetit"/>"/>
							
							<input name="submit" type="submit" value="<spring:message code="label.editpetit"/>" 
								onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
							/>
							
							<c:set var="gu" value="${petit.blockger2016.state}"/>
							<c:if test="${(gu < 3)}">	
								<input name="submit" type="submit" value="<spring:message code="label.endpetit"/>" 
									onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
								/>
							</c:if>
						</c:if>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
						  	<!-- 	<input name="сlose_button" onclick="" type="button" value="Закрыть"/> -->
						</sec:authorize>				
						
					</c:if>
					
					<c:if test="${petit.id eq null}">
					<form:select  id="sel" path="username" onclick="numberone()">
						<form:option label="Назначить"  value="0" />
						<form:options items="${listassign}"/>
					</form:select>
					
						<input type="submit"  id="addpetit" name="submit" value="<spring:message code="label.addpetit"/>" disabled="disabled" 
							onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
						/>
						
						<input name="submit" id="change_edit" type="submit" value="<spring:message code="label.endpetit"/>" 
							onclick="document.getElementById('typeWarning').hidden = false;document.getElementById('causeWarning').hidden = false;document.getElementById('rectif1Warning').hidden = false;"
						/>
					</c:if>
					
                </p>
</fieldset>
                
  
<!-- ============================================================================================  -->
  
  <c:if test="${petit.id eq null}">
	<form:hidden path="blockger2016.state" value="${1}" />
	
	<sec:authorize access="hasRole('ROLE_TFOMS')">
		<form:hidden path="blockger2016.regsource_id" value="${1}" />
		<form:hidden path="blockger2016.regname" value="${principal.username}" />
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_SMO')">
		<form:hidden path="blockger2016.regsource_id" value="${2}" />
		<form:hidden path="blockger2016.regname" value="${principal.username}" />
	</sec:authorize>
	
	<sec:authorize access="hasRole('ROLE_ER')">
		<form:hidden path="blockger2016.regsource_id" value="${3}" />
		<form:hidden path="blockger2016.regname" value="${principal.username}" />
	</sec:authorize>
	</c:if>
	
<!-- ============================================================================================  -->

<c:if test="${petit.id ne null}">
	<c:set var="statec2" value="${petit.blockger2016.state}"/>
	<c:if test="${(statec2 == 3)}">
		&nbsp<spring:message code="label.dateand" />&nbsp<c:out value="${petit.blockger2016.date_end}" />
	</c:if>				
				
	<form:hidden path="blockger2016.idblockger2016"/>
	<form:hidden path="blockger2016.state" value="${petit.blockger2016.state}" />
	<form:hidden path="blockger2016.regsource_id"/>
	<form:hidden path="blockger2016.regname"/>
	
	<form:hidden path="bloutboindletter2016.idletter"/>
	<c:if test="${petit.presentId == 2}">	
		<form:hidden path="bloutboindletter2016.many[0].idlettermany"/>
		<form:hidden path="bloutboindletter2016.many[1].idlettermany"/>
		<form:hidden path="bloutboindletter2016.many[2].idlettermany"/>
	</c:if>
	<!-- Если пользователь проваливается в форму редактирования  при state =3 (завершен)
	то передаем тот же самый date_end по новой и в базе срабатывает триггер иначе в базу добавить пустая date_end-->
	<input type="hidden" name="fil" value="${petit.blockger2016.date_end}"/>
</c:if>  
	
<!-- ============================================================================================  -->
</form:form>



<hr>
<c:if test="${petit.id eq null}">
   <!-- <button id="testclick">ТЕСТ</button> -->
<section>
<sec:authorize access="!hasRole('ROLE_NIGHT')">
	<!-- <input type="button" id="refreshpage" onclick="refreshp()" value="Обновить" style="    float: right; margin-top: -40px;"></input>  -->
</sec:authorize>
<sec:authorize access="hasRole('ROLE_NIGHT')">
<!-- <input type="button" id="refreshnightcall" onclick="refreshnightcall()" value="Обновить НЧ"></input> -->	
</sec:authorize>
<div id="info_socket">
<div class="msgbox"></div>
<div id="info_text">вы пытаетесь закрыть не обработанное сообщение</div>
</div>

<div style="overflow:auto; height:500px;   position: relative;" id="aroundtab"> 

<div  id="divrefresh"  style="height:100%; width:1439px; background: #000000; opacity: 0.6; position: absolute; display:none; z-index: 100; top:0; left:0;"></div>
<div style="width: 100%; height: 100%; position: absolute; z-index: 10; top:0; left:0;">
<table class="secondtab" id="cont">
    <thead>
        <tr>
        <th class="cuting2"><spring:message code="label.id" /></th>      
		    <th class="cuting2">Дата поступления</th>
		    <!-- <th>Дата изменения</th> -->
		    <th class="cuting2">Дата ответа</th>
		    <th><spring:message code="label.type" /></th>
		    
		     <!-- <th>Причина</th>
		    <th>Связь</th>
		    <th>Ghtlcn</th> -->
		    
		    <th>Фамилия</th>
		    <th>Имя</th>
			<th class="cuting2"><spring:message code="label.patrony" /></th>
			<th><spring:message code="label.tel" /></th>
		    <th>Регистратор</th></th>
		    <th>Исполнитель</th>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
			
        </tr>
    </thead>
    <tbody>
          <c:forEach items="${petitList}" var="petit" >
          
        
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
			
    	<tr class="${cssClass}">
      			
				<!-- <td>${petit.num}</td> -->
				<!-- <td>${petit.dateBegin}</td>
			    <td>${petit.dateEnd}</td>
			    <td>${petit.present.presentName}</td>
			    <td>${petit.letterNum}</td>
			    
			    <td>${petit.letterDate}</td>
			    <td>${petit.conect.conectName}</td>
			    <td>${petit.intermedId}</td>
			    
			    
			    
			    <td>${petit.policy}</td>
			    
			    <td>${petit.adress}</td>
			    
			    <td>${petit.terAnswer.terName}</td>
			    <td>${petit.last1}</td>
			    <td>${petit.last2}</td>
			    <td>${petit.hspId}</td>
			    <td>${petit.insurId}</td>	
			    <td>${petit.placeId}</td>
			    <td>${petit.rectif1.rectif1Name}</td>
			    <td>${petit.rectif2.rectif2Name}</td>
			    <td>${petit.rectif3.rectif3Name}</td>
			    <td>${petit.rectif4.rectif4Name}</td>
			    <td>${petit.valid.validName}</td>
			     <td>${petit.compens}</td>
			     <td>${petit.satisf}</td>
			     <td>${petit.compensSource}</td>
			    <td>${petit.compensCode}</td>
			    <td>${petit.compensSum}</td>
			    <td>${petit.propos}</td>
			    <td>${petit.source.sourceName}</td>
				<td>${petit.mo.moName}</td>
				 			    
			    -->
			    
			    <td  class="cuting2">${petit.id}</td>      
			    <td class="cuting2">${petit.dateInput}</td>
			    <%-- <td>${petit.blockger2016.date_change}</td> --%>
			    <c:if test="${(petit.presentId == 2)}">
			    
			    <td class="cuting2">${petit.blockger2016.date_plan_end}</td>
			    </c:if>
			    <c:if test="${(petit.presentId == 1 )}">
			    	<td  class="cuting2"></td>
			    </c:if>
			    <td  class="cuting2">${petit.type.typeName}</td>
			     <!-- <td style="overflow-x: hidden; overflow-y: hidden; white-space: nowrap; max-width: 15px;">${petit.cause.causeName}</td>
			    <td style="overflow-x: hidden; overflow-y: hidden; white-space: nowrap; max-width: 15px;">${petit.conectId}</td>
			    <td style="overflow-x: hidden; overflow-y: hidden; white-space: nowrap; max-width: 15px;">${petit.presentId}</td> -->        
				<td class="cuting">${petit.surname}</td>
				<td  class="cuting">${petit.name}</td>
			    <td  class="cuting2">${petit.patrony}</td>
			    <td>${petit.tel}</td>
				<!-- <td style="overflow-x: hidden; overflow-y: hidden; white-space: nowrap; max-width: 65px;">${petit.ter.terName}</td> -->
				<td>${petit.blockger2016.regname}</td>
				<td class="${cssClassonUser}">${petit.username}</td>
				
				
			    
			    <td><a  href="nightcallfile/${petit.id}" title="Прослушать"><i class="fa fa-headphones fa-2x"></i></a></td>
			    <c:if test="${(statecl != 4)}">
			    	<td><a id="iddel" onclick='del("${petit.id}","${role}")'  title="Удалить"><i class="fa fa-trash-o fa-2x"></i></a></td>
					<td><a id="iddel" href="refresh/${petit.id}" title="Редактировать"><i class="fa fa-pencil-square-o  fa-2x" aria-hidden="true"></i></a></td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<c:if test="${(petit.presentId == 2)}">
						    <c:if test="${(statecl != 2)}">
								<td><a id="iddel" onclick='closemes("${petit.id}","${role}","${petit.blockger2016.state}",this,"${principal.username}")' title="Закрыть обращение"><i class="fa fa-unlock  fa-2x" aria-hidden="true"></i></a></td>
							</c:if>
							<c:if test="${(statecl == 2)}">
								<td><i class="fa fa-unlock  fa-2x noactive" aria-hidden="true"></i></td>
							</c:if>
						</c:if>
						<c:if test="${(petit.presentId != 2)}">
							<td><a id="iddel" onclick='closemes("${petit.id}","${role}","${petit.blockger2016.state}",this,"${principal.username}")' title="Закрыть обращение"><i class="fa fa-unlock  fa-2x" aria-hidden="true"></i></a></td>
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
</div>
</div>
</section>
</c:if>
<br>

	
</body>
</html>