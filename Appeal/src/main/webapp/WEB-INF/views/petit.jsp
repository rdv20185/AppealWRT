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
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/user/other.js"></script> --%>
	
	
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
		
		/*
		* Пустые поля прячем по "+"(добавить)
		*/
		$("[id*='div_subresponse'],[id*='div_subtype']").each(function(){
			//alert($(this).children("input:first").val())
			if($(this).children("input:first").val() == '' || $(this).children("input:first").val() === undefined){
				$(this).css({'display':'none'});
			}
		})
		
		$("[id*='div_subtype']").each(function(){
			
			var $t = $(this).children()[1];
			//alert('ds '+$( "t option:selected" ).text());
			if($( "t option:selected" ).text() == ''){
				$(this).css({'display':'none'});
			}
		})
		
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
					
					var fg = '${petit.type.typeName}';
					if(fg != ''){
						fg = fg.substr(0,1).toUpperCase()+fg.substr(1).toLowerCase();
						$("#type option:contains('"+fg+"')").prop('selected', true);
						$.getScript(document.location.origin+"/Appeal/resources/user/other.js",function(){
							/* alert('${petit.subtype[0].subcause}'); */
						//alert('TEst 1 '+${petit.subtype[0].subcause}+'\n'+${petit.subtype[0].subrectif}+'\n'+ ${petit.subtype[1].subcause}+'\n'+${petit.subtype[1].subrectif});							
							$('#type').trigger("change",['${petit.cause.causeName}','${petit.rectif1.rectif1Name}',
								'${petit.subtype[0].subcause}','${petit.subtype[0].subrectif}',
								'${petit.subtype[1].subcause}','${petit.subtype[1].subrectif}',
								'${petit.subtype[2].subcause}','${petit.subtype[2].subrectif}']);
							
							/* <c:forEach items="${petit.subtype}" var="pt">
					    		$('#btn_add_subtype').trigger("click",['${pt.subcause}']);
							</c:forEach> */
						});
						
					}else{
						$.getScript(document.location.origin+"/Appeal/resources/user/other.js",function(){});
					}
					
				});
			}else{
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
			$("#letterDate").datepicker({dateFormat:'dd.mm.yy'});
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
            <%-- <li><a href="<c:url value="/downloadreestr" />">Реестр страховых представителей 25.09.16</a></li> --%>
            <li><a href="<c:url value="/downloadreestr1117_1" />">Сводный реестр страховых представителей</a></li>
          </ul>
        </li>
        <li><a class="submenu-link">Остальное</a>
          <ul class="submenu">
            <li><a href=""></a></li>
            <li><a  id="opener">Загрузка звонков</a></li>
            <li><a  id="opengr">Обновление реестра СП</a></li>
          </ul>
        </li>
        <sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
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
	<c:if test="${petit.blockger2016.date_end ne null}">
		<form:hidden path="blockger2016.date_end" />
	</c:if> 

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
		<form:input path="letterDate"  id="letterDate"/>
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
			<form:select   id="type" path="typeId" onchange="document.getElementById('typeWarning').hidden = true;">
				
			</form:select>
			
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
	<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
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
                </fieldset>
</sec:authorize>    	
	
	
<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_SMO','ROLE_ADMIN')">
<fieldset  class="row777">	
	<p>
	  <div>
	   <div style="float:left;"><button type="button" id="btn_add_subtype" title="Добавить причину">+</button></div>
	   <div style="float:left; height: 21px; width: 24px; margin-left: 3px;"><button type="button" id="btn_del_subtype" title="Удалить причину">-</button></div>
	   <div style="float: left; font-size: 11px; font-style: oblique; margin-top: 3px; margin-left: 13px;">Добавить/Удалить дополнительную причину (только для Жалоб)</div>
	   </div>
	</p>
	<p>
	      			<div id="div_subtype1">
	      			
	      			<form:label path="subtype[0].subcause">Причина</form:label>
					<form:select class="subcause_cl" id="subcause0" path="subtype[0].subcause" disabled="true"></form:select>
					
	      			<form:label path="subtype[0].subrectif">Уточнение</form:label>
					<form:select class="subrectif_cl" id="subrectif0" path="subtype[0].subrectif" disabled="true"></form:select>
						
						
				<p>
					<form:label path="subtype[0].subhspId">Тип МО</form:label>							
					<form:select path="subtype[0].subhspId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
		    			<form:options items="${hspList}"/>
					</form:select>
					
					<form:label path="subtype[0].subtypempid">Вид МП</form:label>
					<form:select path="subtype[0].subtypempid" disabled="true">
						<form:options disabled="disabled" items="${typeMP}"/>
					</form:select>
					
                </p>
                <p>
                	<form:label path="subtype[0].submoId">МО</form:label>
					<form:select path="subtype[0].submoId" disabled="true">
						<form:option disabled="disabled" value="0" label="" />
    					<form:options items="${moList}"/>
					</form:select>
			
					<form:label path="subtype[0].subinsurId">CМО</form:label>
					<form:select path="subtype[0].subinsurId" disabled="true">
						<form:option disabled="disabled" value="0" label="" />
    					<form:options items="${insurList}"/>
					</form:select>
                </p>
                <p>
                	<form:label path="subtype[0].subvalidId">Обоснованность</form:label>
					<form:select path="subtype[0].subvalidId" disabled="true">
						<form:option disabled="disabled" value="0" label="" />
    					<form:options items="${validList}"/>
					</form:select>
	
					<form:label path="subtype[0].subcompens" disabled="true">Компенсация</form:label>
					<form:input  type="number"  step="any" class="css-input" path="subtype[0].subcompens" pattern="\d{1,6}\.?\d{1,2}" title="Используйте точку/Два знака после точки" disabled="true" />
	
	
					<form:label path="subtype[0].subsatisf" disabled="true">Удовлетворена</form:label>
					<form:select class="css-input" path="subtype[0].subsatisf" disabled="true">
	      					<form:option disabled="disabled" value="" label="" />
							<form:option value="ДА" label="ДА" />
							<form:option value="НЕТ" label="НЕТ" />
					</form:select>
	
	
					<form:label path="subtype[0].subcompensSource" >Источник компенсации</form:label>
					<form:input class="css-input" path="subtype[0].subcompensSource" placeholder="СМО МО или ТФОМС" disabled="true"/>
	
					<form:label path="subtype[0].subcompensCode">Код компенсации</form:label>
					<form:input class="css-input" path="subtype[0].subcompensCode" placeholder="Код дефекта" disabled="true"/>
	
	
					<form:label path="subtype[0].subcompensSum">Сумма компенсации</form:label>
					<form:input type="number"  step="any"  class="css-input" path="subtype[0].subcompensSum" pattern="\d{1,6}\.?\d{1,2}" title="Используйте точку/Два знака после точки"  disabled="true"/>
                </p>
                <p>
					<form:label style="width: 144px;" path="subtype[0].subcauseNote"><spring:message code="label.causeNote" /></form:label>
					<form:textarea rows="2" cols="100" value="0" path="subtype[0].subcauseNote"  disabled="true"/>
                </p>
      				</div>
	</p>
	<p>
	      			<div id="div_subtype2">
	      				
	      				<form:label path="subtype[1].subcause">Причина</form:label>
						<form:select  class="subcause_cl" id="subcause1" path="subtype[1].subcause" disabled="true"></form:select>
					
	      				<form:label path="subtype[1].subrectif">Уточнение</form:label>
						<form:select class="subrectif_cl" id="subrectif1" path="subtype[1].subrectif"  disabled="true"></form:select>
	      			
	      		<p>
					<form:label path="subtype[1].subhspId">Тип МО</form:label>							
					<form:select path="subtype[1].subhspId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
		    			<form:options items="${hspList}"/>
					</form:select>
					
					<form:label path="subtype[1].subtypempid">Вид МП</form:label>
					<form:select path="subtype[1].subtypempid" disabled="true">
						<form:options  disabled="disabled" items="${typeMP}"/>
					</form:select>
					
                </p>
                <p>
                	<form:label path="subtype[1].submoId">МО</form:label>
					<form:select path="subtype[1].submoId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
    					<form:options items="${moList}"/>
					</form:select>
			
					<form:label path="subtype[1].subinsurId">CМО</form:label>
					<form:select path="subtype[1].subinsurId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
    					<form:options items="${insurList}"/>
					</form:select>
                </p>
                <p>
                	<form:label path="subtype[1].subvalidId">Обоснованность</form:label>
					<form:select path="subtype[1].subvalidId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
    					<form:options items="${validList}"/>
					</form:select>
	
					<form:label path="subtype[1].subcompens" disabled="true">Компенсация</form:label>
					<form:input type="number"  step="any" class="css-input" path="subtype[1].subcompens" pattern="\d{1,6}\.?\d{1,2}" title="Используйте точку/Два знака после точки"  disabled="true"/>
	
	
					<form:label path="subtype[1].subsatisf" disabled="true">Удовлетворена</form:label>
					<form:select class="css-input" path="subtype[1].subsatisf"  disabled="true">
	      					<form:option  disabled="disabled" value="" label="" />
							<form:option value="ДА" label="ДА" />
							<form:option value="НЕТ" label="НЕТ" />
					</form:select>
	
	
					<form:label path="subtype[1].subcompensSource" >Источник компенсации</form:label>
					<form:input class="css-input" path="subtype[1].subcompensSource" placeholder="СМО МО или ТФОМС" disabled="true"/>
	
					<form:label path="subtype[1].subcompensCode">Код компенсации</form:label>
					<form:input class="css-input" path="subtype[1].subcompensCode" placeholder="Код дефекта" disabled="true"/>
	
	
					<form:label path="subtype[1].subcompensSum">Сумма компенсации</form:label>
					<form:input type="number"  step="any" class="css-input" path="subtype[1].subcompensSum" pattern="\d{1,6}\.?\d{1,2}" title="Используйте точку/Два знака после точки"  disabled="true"/>
                </p>
                <p>
					<form:label style="width: 144px;" path="subtype[1].subcauseNote"><spring:message code="label.causeNote" /></form:label>
					<form:textarea rows="2" cols="100" value="0" path="subtype[1].subcauseNote"  disabled="true"/>
                </p>
	      				
	      			</div>	
	</p>
	<p>
	      			<div id="div_subtype3">
	      				
	      				<form:label path="subtype[2].subcause">Причина</form:label>
						<form:select class="subcause_cl" id="subcause2" path="subtype[2].subcause" disabled="true"></form:select>
					
	      				<form:label path="subtype[2].subrectif">Уточнение</form:label>
						<form:select class="subrectif_cl" id="subrectif2" path="subtype[2].subrectif" disabled="true"></form:select>
	      			
	      		<p>
					<form:label path="subtype[2].subhspId">Тип МО</form:label>							
					<form:select path="subtype[2].subhspId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
		    			<form:options items="${hspList}"/>
					</form:select>
					
					<form:label path="subtype[2].subtypempid">Вид МП</form:label>
					<form:select path="subtype[2].subtypempid" disabled="true">
						<form:options  disabled="disabled" items="${typeMP}"/>
					</form:select>
					
                </p>
                <p>
                	<form:label path="subtype[2].submoId">МО</form:label>
					<form:select path="subtype[2].submoId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
    					<form:options items="${moList}"/>
					</form:select>
			
					<form:label path="subtype[2].subinsurId">CМО</form:label>
					<form:select path="subtype[2].subinsurId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
    					<form:options items="${insurList}"/>
					</form:select>
                </p>
                <p>
                	<form:label path="subtype[2].subvalidId">Обоснованность</form:label>
					<form:select path="subtype[2].subvalidId" disabled="true">
						<form:option  disabled="disabled" value="0" label="" />
    					<form:options items="${validList}"/>
					</form:select>
	
					<form:label path="subtype[2].subcompens" disabled="true">Компенсация</form:label>
					<form:input type="number"  step="any" class="css-input" path="subtype[2].subcompens" pattern="\d{1,6}\.?\d{1,2}" title="Используйте точку/Два знака после точки"  disabled="true"/>
	
	
					<form:label path="subtype[2].subsatisf" disabled="true">Удовлетворена</form:label>
					<form:select class="css-input" path="subtype[2].subsatisf" disabled="true">
	      					<form:option  disabled="disabled" value="" label="" />
							<form:option value="ДА" label="ДА" />
							<form:option value="НЕТ" label="НЕТ" />
					</form:select>
	
	
					<form:label path="subtype[2].subcompensSource" >Источник компенсации</form:label>
					<form:input class="css-input" path="subtype[2].subcompensSource" placeholder="СМО МО или ТФОМС" disabled="true"/>
	
					<form:label path="subtype[2].subcompensCode">Код компенсации</form:label>
					<form:input class="css-input" path="subtype[2].subcompensCode" placeholder="Код дефекта" disabled="true"/>
	
	
					<form:label path="subtype[2].subcompensSum">Сумма компенсации</form:label>
					<form:input type="number"  step="any" class="css-input" path="subtype[2].subcompensSum" pattern="\d{1,6}\.?\d{1,2}" title="Используйте точку/Два знака после точки"  disabled="true"/>
                </p>
                <p>
					<form:label style="width: 144px;" path="subtype[2].subcauseNote"><spring:message code="label.causeNote" /></form:label>
					<form:textarea rows="2" cols="100" value="0" path="subtype[2].subcauseNote"  disabled="true"/>
                </p>	
	      		</div>	
	</p>
</sec:authorize>	
</fieldset>
            
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
	      					<form:options items="${responsible}"/>
	      					
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
	
	<sec:authorize access="hasAnyRole('ROLE_TFOMS','ROLE_ADMIN')">
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
			    
			    <c:if test="${(petit.blockger2016.date_end == null)}">
			    <td class="cuting2">${petit.blockger2016.date_plan_end}</td>
			    </c:if>
			    <c:if test="${(petit.blockger2016.date_end != null)}">
			    <td class="cuting2">${petit.blockger2016.date_end}</td>
			    </c:if>
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

 <div id="dialog-message" title="Процедура загрузки звонков">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
    Процедура загрузки звонков позволяет загружать файлы в формате xml. Необходима для пакетного обмена результатами совершенных звонков застрахованных лиц. Формат пакетов на основе приказа №79.
  </p>
  <p>
    <div class="container">
    <h2>Загрузить звоноки</h2>
    <hr>
    <!-- File Upload From -->
    <form name ="submit_file" action="fileUpload_hotcall" method="post" enctype="multipart/form-data">
      <div class="form-group">
        <input class="form-control" type="file" name="file">
      </div>
      <br>
      <div class="form-group">
        <button class="btn btn-primary" type="submit">загрузить</button>
      </div>
    </form>
    <br />

    <!-- Bootstrap Progress bar -->
    <div class="progress">
      <div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
        aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
    </div>
    <!-- <div id="progressbar"><div class="progress-label">Загрузка...</div></div> -->

    <!-- Alert -->
    <div id="alertMsg" style="color: red;font-size: 18px;"></div>
  </div>
  </p>
</div>

<div id="dialog-message-gr" title="Обновление реестра страховых представителей">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
    Процедура обновления реестра страховых представителей позволяет держать в актуальном состоянии файл формата xls.
  </p>
  <p>
    <div class="container">
    <h2>Загрузить файл</h2>
    <hr>
    <!-- File Upload From -->
    <form name ="submit_file_gr" action="fileUpload_gr_sp" method="post" enctype="multipart/form-data">
      <div class="form-group">
        <input id="filegr" class="form-control" type="file" name="file">
      </div>
      <br>
      <div class="form-group">
        <button id="btnsubgr" class="btn btn-primary" type="submit">Обновить</button>
      </div>
    </form>
    <br />

    <!-- Bootstrap Progress bar -->
    <div class="progress">
      <div id="progressBargr" class="progress-bar progress-bar-success" role="progressbar"
        aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
    </div>
    <!-- <div id="progressbar"><div class="progress-label">Загрузка...</div></div> -->

    <!-- Alert -->
    <div id="alertMsggr" style="color: red;font-size: 18px;"></div>
  </div>
  </p>
</div>


<script>
$(document).ready(function() {
	
/*	$( function() {
		
	    $( "#dialog-message" ).dialog({
	    	resizable: false,
	        height: "auto",
	        width: 600,
	      autoOpen: false,
	      show: {
	        effect: "blind",
	        duration: 1000
	      },
	      hide: {
	        effect: "explode",
	        duration: 1000
	      },
	      modal: true,
	      buttons: {
	        Ok: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });
	    
	    $( "#opener" ).on( "click", function() {
	      $( "#dialog-message" ).dialog( "open" );
	      
	      
	      $(function() {
	  		$('#btnsubhotcall').click(function(e) {
	  			e.preventDefault();
	  			//Disable submit button
	  			$(this).prop('disabled',true);
	  			
	  			var filename = $('input[type=file]').val().replace(/C:\\fakepath\\/i, '');
	  			console.log('filename '+filename);
	  			if(filename.indexOf('zip') < 0 && filename.indexOf('rar') < 0){
	  				$('#alertMsg').text('Необходим файл с расширением *.zip или *.rar');
	  				$('button[type=submit]').prop('disabled',false);
	  				
	  				throw "Bad extanshion";
	  				
	  			}
	  			
	  			var form = document.forms["submit_file"];
	  			var formData = new FormData(form);
	  				
	  			// Ajax call for file uploaling
	  			var ajaxReq = $.ajax({
	  				url : 'fileUpload_hotcall?${_csrf.parameterName}=${_csrf.token}',
	  				type : 'POST',
	  				data : formData,
	  				cache : false,
	  				contentType : false,
	  				processData : false,
	  				xhr: function(){
	  					//Get XmlHttpRequest object
	  					 var xhr = $.ajaxSettings.xhr() ;
	  					
	  					//Set onprogress event handler 
	  					 xhr.upload.onprogress = function(event){
	  						var perc = Math.round((event.loaded / event.total) * 100);
	  						$('#progressBar').text(perc + '%');
	  						$('#progressBar').css('width',perc + '%');
	  					 };
	  					 return xhr ;
	  				},
	  				beforeSend: function( xhr ) {
	  					//Reset alert message and progress bar
	  					$('#alertMsg').text('');
	  					$('#progressBar').text('');
	  					$('#progressBar').css('width','0%');
	                  }
	  			});

	  			// Called on success of file upload
	  			ajaxReq.done(function(msg) {
	  				$('#alertMsg').text(msg);
	  				$('input[type=file]').val('');
	  				$('button[type=submit]').prop('disabled',false);
	  			});
	  			
	  			// Called on failure of file upload
	  			ajaxReq.fail(function(jqXHR) {
	  				$('#alertMsg').text('Возникла ощибка\n'+jqXHR.responseText+'('+jqXHR.status+
	  						' - '+jqXHR.statusText+')');
	  				$('button[type=submit]').prop('disabled',false);
	  			});
	  		});
	  	}); 
	      
	      
	    });
	
	});   */
	

		
		  $( "#dialog-message-gr" ).dialog({
		    	resizable: false,
		        height: "auto",
		        width: 600,
		      autoOpen: false,
		      show: {
		        effect: "blind",
		        duration: 1000
		      },
		      hide: {
		        effect: "explode",
		        duration: 1000
		      },
		      modal: true,
		      buttons: {
		        Ok: function() {
		          $( this ).dialog( "close" );
		        }
		      }
		    });
		
	    
	    $( "#opengr" ).on( "click", function() {
		      $( "#dialog-message-gr" ).dialog( "open" );
		      
		    });
	    
	    
	  	$(function() {
			
			$('#btnsubgr').click(function(e) {
				e.preventDefault();
				//Disable submit button
				$(this).prop('disabled',true);
				
				var filename = $('#filegr').val().replace(/C:\\fakepath\\/i, '');
				console.log('filename2 '+filename+' - '+filename.indexOf('xls'));
				if(filename.indexOf('xls') < 0 ){
					$('#alertMsggr').text('Необходим файл с расширением *.xls');
					$('#btnsubgr').prop('disabled',false);
					
					throw "Bad extanshion";
					
				}
				
				var form = document.forms["submit_file_gr"];	
				var formData = new FormData(form);
					
				// Ajax call for file uploaling
				var ajaxReq = $.ajax({
					url : 'fileUpload_gr_sp?${_csrf.parameterName}=${_csrf.token}',
					type : 'POST',
					data : formData,
					cache : false,
					contentType : false,
					processData : false,
					xhr: function(){
						//Get XmlHttpRequest object
						 var xhr = $.ajaxSettings.xhr() ;
						
						//Set onprogress event handler 
						 xhr.upload.onprogress = function(event){
							var perc = Math.round((event.loaded / event.total) * 100);
							$('#progressBargr').text(perc + '%');
							$('#progressBargr').css('width',perc + '%');
						 };
						 return xhr ;
					},
					beforeSend: function( xhr ) {
						//Reset alert message and progress bar
						$('#alertMsggr').text('');
						$('#progressBargr').text('');
						$('#progressBargr').css('width','0%');
	                }
				});

				// Called on success of file upload
				ajaxReq.done(function(msg) {
					$('#alertMsggr').text(msg);
					$('input[type=file]').val('');
					$('#btnsubgr').prop('disabled',false);
				});
				
				// Called on failure of file upload
				ajaxReq.fail(function(jqXHR) {
					$('#alertMsggr').text('Возникла ощибка\n'+jqXHR.responseText+'('+jqXHR.status+
							' - '+jqXHR.statusText+')');
					$('#btnsubgr').prop('disabled',false);
				});
			});
		});
	
	
	

	
   
	
	//$("#type").hover(function(){
		//alert('sd');
		//$("#type option:contains('Жалоба')").prop('selected', true);
	//});	
});	
</script>
</body>
</html>