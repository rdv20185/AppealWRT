$(document).ready(function() {
	init();	
});

var tt ='';
var flag = 0;

callback = function(message) {
	if(flag == 0){
		/*
		 * Если submit нажата с Appeal/refresh/ то отправляется
		 * по сокету message на всех клиентов - это и есть сигнал всем обновляться 
		 */
		if(location.href.indexOf("Appeal/refresh/") < 0)
		{		
			forWS(tt);
		}
	}

	flag = 0;
};

reconnect = function(){
	setTimeout(init, 5000);
}

function init(){
	//if(location.href.indexOf("Appeal/refresh/") < 0)
	//{	
		var socket = new SockJS("/Appeal/notify");
		var stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			stompClient.subscribe("/topic/notify",  callback);
		
		});
		socket.onclose = reconnect;
	//}	
}






/*
 * УДАЛЕНИЕ ЗАПИСИ
 */
function del(id,role){ 
	$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
	$('#divrefresh').animate({opacity: 0.6}, 3000 );
	// SEND QUERY AND PROCESS RESPONSE
	flag = 1;
	$.ajax({
		type : "GET",
		url : "delete",
		data : ({petitId: id}),
		success : function(response) {
	        table(response,role);
	        $('#divrefresh').animate({opacity: 0.0}, 2000 );
	        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
		},
		error : function(e) {
			alert("ERROR: ", +'обновите страницу');
			$('#divrefresh').animate({opacity: 0.0}, 2000 );
	        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
	
}


function closemes(id,role){ 
	// SEND QUERY AND PROCESS RESPONSE
	flag = 1;
	$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
	$('#divrefresh').animate({opacity: 0.6}, 3000 );
	$.ajax({
		type : "GET",
		url : "close",
		data : ({petitId: id}),
		success : function(response) {
	        table(response,role);
	        $('#divrefresh').animate({opacity: 0.0}, 2000 );
	        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
	        
		},
		error : function(e) {
			alert("ERROR: ", +'обновите страницу');
			$('#divrefresh').animate({opacity: 0.0}, 2000 );
			setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
		},
		done : function(e) {
			console.log("DONE");
			
		}
	});
	
}


function openmes(id,role){
	$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
	$('#divrefresh').animate({opacity: 0.6}, 3000 );
	// SEND QUERY AND PROCESS RESPONSE
	flag = 1;
	$.ajax({
		type : "GET",
		url : "open",
		data : ({petitId: id}),
		success : function(response) {
	        table(response,role);
	        $('#divrefresh').animate({opacity: 0.0}, 2000 );
	        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
		},
		error : function(e) {
			alert("ERROR: ", +'обновите страницу');
			$('#divrefresh').animate({opacity: 0.0}, 2000 );
			setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
		},
		done : function(e) {
			console.log("DONE");
			
		}
	});
	
}
/*
 * ФУНКЦИЯ ОТРАБАТЫВАЕТ В СОКЕТЕ 
 * 
 */

function forWS(role){
	
	$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
	$('#divrefresh').animate({opacity: 0.6}, 3000 );
	$.ajax({
		type : "GET",
		url : "allist",
		success : function(response) {
			//console.log('stay here '+JSON.stringify(response));
	        tablews(response,role);
	        $('#divrefresh').animate({opacity: 0.0}, 2000 );
	        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
		},
		error : function(e) {
			alert("ERROR: ", +'обновите страницу');
			$('#divrefresh').animate({opacity: 0.0}, 2000 );
	        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
		},
		done : function(e) {
			console.log("DONE");
		}
	});

}



/*
 *  фУНКЦИЯ ОТРАБАТЫВАЕТ НАЖАТИЕ НА ФОРМЕ
 */
function addJS(role){ 
	/*
	 * В petit.jsp после прогрузки страницы запускается addJS 
	 * Тем самым происходит инициализыция переменной tt
	 */
	tt = role;
	
	if(location.href.indexOf("Appeal/refresh/") < 0)
	{		
		
		$("#draggable").submit(function(event) {
			var btn= $(this).find("input[type=submit]:focus").val();
			console.log('@@ '+btn);
			// Prevent the form from submitting via the browser.
			event.preventDefault();
			
			if(btn == 'Завершить'){
				if(valid()){
					body(btn,role);
				}
			}
			else{
				body(btn,role);
			}
					
		});
		
	}	
	else{
		// $('#draggable').delay(2000).submit();
	}
}

function body(btn,role){
	
	if($('#connectid').val() == 0){
		$('.errorrep').append('<h3>Поле "Связь" обязательно для заполнения</h3>');
		$('.errorrep').css({'display':'block'});
		
	}
	else{
		$('.errorrep').empty();
		$('.errorrep').css({'display':'none'});

	
		// CREATE HTTP QUERY
		var values = {};
		$.each($("form").serializeArray(), function (i, field) {
		    values[field.name] =field.value;
		});
		//added name button
		values["submitted"] = btn;
		flag = 1;
		
		$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
		$('#divrefresh').animate({opacity: 0.6}, 3000 );
	
		// SEND QUERY AND PROCESS RESPONSE
		$.ajax({
			type : "GET",
			url : "add",
			data : values,
			success : function(response) {
				//console.log('stay here '+JSON.stringify(response));
		        table(response,role);
		        $('#divrefresh').animate({opacity: 0.0}, 2000 );
		        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
			},
			error : function(e) {
				
				alert("ERROR: ", +'обновите страницу');
				console.log('Тест'+ JSON.stringify(e));
				
				$('#divrefresh').animate({opacity: 0.0}, 2000 );
		        setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
			},
			done : function(e) {
				console.log("DONE");
				
			}
		});
	}	
}



/*
 * ФУНКЦИЯ ОТРИСОВЫВАЕТ НА МОРДЕ ТАБЛИЦУ  
 */

function table(response,role){
	
	role = role.replace(/\s/g, ''); // space
	$container = $("#cont");
    $container.empty();
	
	 userInfo =  
		 "<thead><tr><th class='cuting2'>НОМЕР</th><th class='cuting2'>ДАТА ПОСТУПЛЕНИЯ</th><th>ДАТА ИЗМЕНЕНИЯ</th><th class='cuting2'>ТИП</th><th>ФАМИЛИЯ</th><th>ИМЯ</th><th class='cuting2'>ОТЧЕСТВО</th><th>ТЕЛЕФОН</th><th  class='cuting2'>РЕГИСТРАТОР</th><th>ИСПОЛНИТЕЛЬ</th><th></th><th></th><th></th><th></th></tr></thead><tbody>";
	
	$.each(response, function(index, value) {
									
	if(value.blockger2016.state == 1){
		var cssClassonUser = "blink";
	}
	
	if(value.blockger2016.state != 1){
		var cssClassonUser = "";
	}
	
	if(value.blockger2016.state == 2){
		var cssClass = "someclass2";
	}
	
	if(value.blockger2016.state == 3 || value.blockger2016.state == 4){
		var cssClass = "someclass3";
	}
	var type ='';
	if (value.typeId == 1) type = 'ЖАЛОБА';
	if (value.typeId == 2) type = 'ЗАЯВЛЕНИЕ';
	if (value.typeId == 3) type = 'КОНСУЛЬТАЦИЯ';
	if (value.typeId == 4) type = 'ПРЕДЛОЖЕНИЕ';
	
	if (value.surname == null) value.surname = '';
	if (value.name == null) value.name = '';
	if (value.patrony == null) value.patrony = '';
	if (value.tel == null) value.tel = '';
	
	userInfo +="<tr class="+cssClass+"><td class='cuting2'>"+value.id+"</td><td class='cuting2'>"+value.dateInput+"</td><td>"+value.blockger2016.date_change+"</td><td class='cuting2'>"+type+"</td><td class='cuting'>"+value.surname+"</td><td class='cuting'>"+value.name+"</td><td class='cuting2'>"+value.patrony+"</td><td class='cuting'>"+value.tel+"</td><td class='cuting2'>"+value.blockger2016.regname+"</td><td class="+cssClassonUser+">"+value.username+"</td>";
	
	if(value.blockger2016.state != 4){
		userInfo +="<td><a href='nightcallfile/"+value.id+"' title='Прослушать'><i class='fa fa-headphones fa-2x'></i></a></td> <td><a onclick=del('"+value.id+"','"+role+"') id='iddel' title='Удалить'><i class='fa fa-trash-o fa-2x'></i></a></td><td><a id='iddel' href='refresh/"+value.id+"' title='Редактировать'><i class='fa fa-pencil-square-o  fa-2x' aria-hidden='true'></i></a></td>";
		
		var n = role.indexOf("ROLE_ADMIN");
		if (n >= 0){
			if(value.presentId == 2){
				if(value.blockger2016.state != 2){
					userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
				}
				if(value.blockger2016.state == 2){
					userInfo +="<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>";
				}
			}
			if(value.presentId != 2){
				userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
			}
		}else{
			userInfo +="<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>";
		}
	}

	
	if(value.blockger2016.state == 4){
		userInfo +="<td><i class='fa fa-headphones fa-2x noactive'></i></a></td><td><i class='fa fa-trash-o fa-2x noactive'></i></td><td><i class='fa fa-pencil-square-o  fa-2x noactive' aria-hidden='true'></i></td>";
		var n = role.indexOf("ROLE_ADMIN");
		if (n >= 0){
			userInfo +="<td><a onclick=openmes('"+value.id+"','"+role+"') id='iddel'  title='Восстановить закрытое обращение'><i class='fa fa-lock  fa-2x' aria-hidden='true'></i></a></td>";
		}else{
			userInfo +="<td><i class='fa fa-lock  fa-2x noactive' aria-hidden='true'></i></td>";
		}
	}
	
	userInfo +="</tr>";
})

userInfo +="</tbody";
$container.append(userInfo);

// зачищаем форму
$("form").each(function(){
   this.reset();
});
// добовляем п умолчанию dateInput
$( "#dateInput" ).datepicker( "setDate", new Date());

	
}



function tablews(response,role){
	
	role = role.replace(/\s/g, ''); // space
	$container = $("#cont");
    $container.empty();
	
	 userInfo =  
	 "<thead><tr><th class='cuting2'>НОМЕР</th><th class='cuting2'>ДАТА ПОСТУПЛЕНИЯ</th><th>ДАТА ИЗМЕНЕНИЯ</th><th class='cuting2'>ТИП</th><th>ФАМИЛИЯ</th><th>ИМЯ</th><th class='cuting2'>ОТЧЕСТВО</th><th>ТЕЛЕФОН</th><th  class='cuting2'>РЕГИСТРАТОР</th><th>ИСПОЛНИТЕЛЬ</th><th></th><th></th><th></th><th></th></tr></thead><tbody>";
	
	$.each(response, function(index, value) {
									
	if(value.blockger2016.state == 1){
		var cssClassonUser = "blink";
	}
	
	if(value.blockger2016.state != 1){
		var cssClassonUser = "";
	}
	
	if(value.blockger2016.state == 2){
		var cssClass = "someclass2";
	}
	
	if(value.blockger2016.state == 3 || value.blockger2016.state == 4){
		var cssClass = "someclass3";
	}
	var type ='';
	if (value.typeId == 1) type = 'ЖАЛОБА';
	if (value.typeId == 2) type = 'ЗАЯВЛЕНИЕ';
	if (value.typeId == 3) type = 'КОНСУЛЬТАЦИЯ';
	if (value.typeId == 4) type = 'ПРЕДЛОЖЕНИЕ';
	
	if (value.surname == null) value.surname = '';
	if (value.name == null) value.name = '';
	if (value.patrony == null) value.patrony = '';
	if (value.tel == null) value.tel = '';
	
	userInfo +="<tr class="+cssClass+"><td class='cuting2'>"+value.id+"</td><td class='cuting2'>"+value.dateInput+"</td><td>"+value.blockger2016.date_change+"</td><td class='cuting2'>"+type+"</td><td class='cuting'>"+value.surname+"</td><td class='cuting'>"+value.name+"</td><td class='cuting2'>"+value.patrony+"</td><td class='cuting'>"+value.tel+"</td><td class='cuting2'>"+value.blockger2016.regname+"</td><td class="+cssClassonUser+">"+value.username+"</td>";
	
	if(value.blockger2016.state != 4){
		userInfo +="<td><a href='nightcallfile/"+value.id+"' title='Прослушать'><i class='fa fa-headphones fa-2x'></i></a></td> <td><a onclick=del('"+value.id+"','"+role+"') id='iddel' title='Удалить'><i class='fa fa-trash-o fa-2x'></i></a></td><td><a id='iddel' href='refresh/"+value.id+"' title='Редактировать'><i class='fa fa-pencil-square-o  fa-2x' aria-hidden='true'></i></a></td>";
		
		var n = role.indexOf("ROLE_ADMIN");
		if (n >= 0){
			if(value.presentId == 2){
				if(value.blockger2016.state != 2){
					userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
				}
				if(value.blockger2016.state == 2){
					userInfo +="<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>";
				}
			}
			if(value.presentId != 2){
				userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
			}
		}else{
			userInfo +="<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>";
		}
	}

	
	if(value.blockger2016.state == 4){
		userInfo +="<td><i class='fa fa-headphones fa-2x noactive'></i></a></td><td><i class='fa fa-trash-o fa-2x noactive'></i></td><td><i class='fa fa-pencil-square-o  fa-2x noactive' aria-hidden='true'></i></td>";
		var n = role.indexOf("ROLE_ADMIN");
		if (n >= 0){
			userInfo +="<td><a onclick=openmes('"+value.id+"','"+role+"') id='iddel'  title='Восстановить закрытое обращение'><i class='fa fa-lock  fa-2x' aria-hidden='true'></i></a></td>";
		}else{
			userInfo +="<td><i class='fa fa-lock  fa-2x noactive' aria-hidden='true'></i></td>";
		}
	}
	
	userInfo +="</tr>";
})

userInfo +="</tbody";
$container.append(userInfo);
	
}


function valid(){

	 if($('#type').val() == 0){
		$('.errorrep').append('<h3>Поле "Тип" обязательно для заполнения</h3>');
		$('.errorrep').css({'display':'block'});
		
		return false;
	}
	else if($('#cause').val() == 0){
		$('.errorrep').append('<h3>Поле "Причина" обязательно для заполнения</h3>');
		$('.errorrep').css({'display':'block'});
		
		return false;
	}
	else{
		$('.errorrep').empty();
		$('.errorrep').css({'display':'none'});
		
		return true;
	}
	
}

