/*
 * closemes бежит по таблице и ищет ид и зкрывает НО бежит на каждом клиенте
 * доработать чтобы бежал только на том где есть это сообщение
 */

$(document).ready(function() {
	
	// инициализация сокета
	init();	
});

var this_js_script = $('script[src*=controller]'); // or better regexp to get the file name..

var my_var_1 = this_js_script.attr('data-my_var_1');   
if (typeof my_var_1 === "undefined" ) {
   var my_var_1 = 'some_default_value';
}

var tt ='';
var flag = 0;
var for_night_regname = "auto_callnight5001_callnight5002_callnight5003";
var for_night_username = "callnight5003_ТФОМС_callnight5001_callnight5002_auto_СИМАЗ_РОСНО_ИНГОССТРАХ";
var call5001 = "call5001", call5002 = "call5002", call5003 = "call5003";
var all_assign5001 ="ТФОМС_СИМАЗ_РОСНО_ИНГОССТРАХ_call5001";
var all_assign5002 ="ТФОМС_СИМАЗ_РОСНО_ИНГОССТРАХ_call5002";
var all_assign5003 ="ТФОМС_СИМАЗ_РОСНО_ИНГОССТРАХ_call5003";
var for_tfoms = my_var_1+"_ТФОМС";
var for_tfoms_1 = "hamitov_eremina_mityanina";
var for_ingos = my_var_1+"_ИНГОССТРАХ";
var for_rosno = my_var_1+"_РОСНО";
var for_simaz = my_var_1+"_СИМАЗ";
var admin = "smyvin_vasilyeva";
	
/*
 * Метод отрабатывает на клиенте отрисовку строки таблицы после закрытия (нажатия на открытый замочек)
 * другим клиентом
 */
function process_callback_closemes(pr){

	 try{
		 
		 $('#cont > tbody  > tr').each(function (i) {
			 
			 	let tdId = $(this).children("td:nth-child(1)");
			 	
		        if(tdId.html().indexOf(pr.id) >= 0){
		        	
		        	$('#info_text').html("Обращение № "+pr.id+" было закрыто пользователем "+pr.user+" в "+pr.time);
		        	//$('#info_socket').css({'display':'block'});
		        	$('#info_socket').finish();
					$('#info_socket').animate({opacity: 0.2}, 0 );
					$('#info_socket').animate({opacity: 1}, 0 );
		    		$('#info_socket').animate({opacity: 0.0}, 5000 );
		        	
		        	// tt - глобальная
		        	let role_o = tt.replace(/\s/g, ''); // space
		        	let n = role_o.indexOf("ROLE_ADMIN");
		        	let tdLock = $(this).children("td:nth-child(14)");
		        	let tdEdit = $(this).children("td:nth-child(13)");
		        	let tdDel = $(this).children("td:nth-child(12)");
		        	
		        	tdEdit.html("<i class='fa fa-pencil-square-o  fa-2x noactive' aria-hidden='true'></i>");
		        	tdDel.html("<i class='fa fa-trash-o fa-2x noactive'></i>");
		        	
		    		if (n >= 0){
			        	tdLock.html("<a onclick=openmes('"+tdId.html()+"','"+role_o+"') id='iddel'  title='Восстановить закрытое обращение'><i class='fa fa-lock  fa-2x' aria-hidden='true'></i></a>");
		    		}
		    		else{ tdLock.html("<i class='fa fa-lock  fa-2x noactive' aria-hidden='true'></i>"); }
		        	
		        	console.log((i+1));
		        	
		        	
		        	 throw new BreakException();
		        }
		 });
		 
	 } catch (e) {
		  if (e);
		}
};


/*
 * Метод отрабатывает на клиенте отрисовку строки таблицы после добавления обращения(кнопка завершить или сохранить)
 * 
 */
function process_callback_add(pr){
	
				$('#info_text').html("Обращение № "+pr.id.id+" было добавлено пользователем "+pr.user+" в "+pr.time);
				//$('#info_socket').css({'display':'block'});
				$('#info_socket').finish();
				$('#info_socket').animate({opacity: 0.2}, 0 );
				$('#info_socket').animate({opacity: 1}, 0 );
				$('#info_socket').animate({opacity: 0.0}, 5000 );
	
	
				// tt - глобальная
				let role_o = tt.replace(/\s/g, ''); // space
				let n = role_o.indexOf("ROLE_ADMIN");
				
				let cssClassonUser = "blink";
				if(pr.id.blockger2016.state == 1){
					cssClassonUser = "blink";
				}
				
				if(pr.id.blockger2016.state != 1){
					cssClassonUser = "";
				}
		 		
				let typ ='';
				if (pr.id.typeId == 1) typ = 'ЖАЛОБА';
				if (pr.id.typeId == 2) typ = 'ЗАЯВЛЕНИЕ';
				if (pr.id.typeId == 3) typ = 'КОНСУЛЬТАЦИЯ';
				if (pr.id.typeId == 4) typ = 'ПРЕДЛОЖЕНИЕ';
	
				let text = "<tr>"+ "<td class='cuting2'>"+pr.id.id+"</td>"+"<td class='cuting2'>"+pr.id.dateInput+"</td>"+"<td>"+pr.id.date_change+"</td>"+"<td class='cuting2'>"+typ+"</td>"+
				"<td class='cuting'>"+pr.id.surname+"</td>"+ "<td class='cuting'>"+pr.id.name+"</td>"+ "<td class='cuting2'>"+pr.id.patrony+"</td>"+ "<td>"+pr.id.tel+"</td>"+  "<td class='cuting2'>"+pr.id.blockger2016.regname+"</td>"+ "<td class="+cssClassonUser+">"+pr.id.username+"</td>"+
				"<td><a href='nightcallfile/"+pr.id.id+"' title='Прослушать'><i class='fa fa-headphones fa-2x'></i></a></td> <td><a onclick=del('"+pr.id.id+"','"+role_o+"') id='iddel' title='Удалить'><i class='fa fa-trash-o fa-2x'></i></a></td>"+"" +
				"<td><a id='iddel' href='refresh/"+pr.id.id+"' title='Редактировать'><i class='fa fa-pencil-square-o  fa-2x' aria-hidden='true'></i></a></td>";
				
				if (n >= 0){
		        	text +=	"<td><a onclick=closemes('"+pr.id.id+"','"+role_o+"','"+pr.id.blockger2016.state+"',this,'"+my_var_1+"') id='iddel'  title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
	    		}
	    		else{ text += "<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>"; }

				text +="</tr>";
				$('#cont > tbody').prepend(text);
	
};

/*
 * вынес логику  отработки добавления записи на клиенте
 */
function body_add(pr){
	
	let reg_name = pr.id.blockger2016.regname || "";
	
	if(admin.indexOf(my_var_1) >= 0){
		console.log('вошли в админов ');
		process_callback_add(pr);
	}
	
	// если пользователь принадлежит группе ночников и "прилетает" создатель из группы ночников
	if(for_night_regname.indexOf(my_var_1) >= 0 && for_night_regname.indexOf(reg_name) >= 0){
		console.log('вошли в ночников ');
		// совпадение для исполнителей
		if(for_night_username.indexOf(pr.id.username) >= 0){
			
			/* "прилетевшее обновление" классифицируется по типу (закрытие, добовление и тд)
			 *  и добовляется на клиента
			 */ 
			process_callback_add(pr);
		}
	}	
	else if (reg_name.indexOf(my_var_1) >= 0 && my_var_1.indexOf(call5001) >= 0 && all_assign5001.indexOf(pr.id.username) >= 0){
		console.log('вошли в 5001 ');
		process_callback_add(pr);
	}
	else if (reg_name.indexOf(my_var_1) >= 0 && my_var_1.indexOf(call5002) >= 0 && all_assign5002.indexOf(pr.id.username) >= 0){
		console.log('вошли в 5002 ');
		process_callback_add(pr);
	}
	else if (reg_name.indexOf(my_var_1) >= 0 && my_var_1.indexOf(call5003) >= 0 && all_assign5003.indexOf(pr.id.username) >= 0){
		console.log('вошли в 5003 ');
		process_callback_add(pr);
	}
	else if(for_tfoms.indexOf(pr.id.username) >= 0 && for_tfoms_1.indexOf(my_var_1) >= 0){
		console.log('вошли для тфомс');
		process_callback_add(pr);
	}
	else if(pr.id.blockger2016.regname.indexOf('kuznetsova') >= 0){
		console.log('вошли для кузнецова');
		process_callback_add(pr);
	}
	else if(for_ingos.indexOf(pr.id.username) >= 0 && my_var_1.indexOf('smo_ingos') >= 0){
		console.log('вошли для ИНГОС');
		process_callback_add(pr);
	}
	else if(for_rosno.indexOf(pr.id.username) >= 0 && my_var_1.indexOf('smo_rosno') >= 0){
		console.log('вошли для РОСНО');
		process_callback_add(pr);
	}
	else if(for_simaz.indexOf(pr.id.username) >= 0 && my_var_1.indexOf('smo_simaz') >= 0){
		console.log('вошли для симаз');
		process_callback_add(pr);
	}
	else{
		console.log('вошли в никуда ');
		//console.log('reg_name '+reg_name+' my_var_1 '+my_var_1+' call5001 '+call5001+' pr.id.username '+pr.id.username+' all_assign5001 '+all_assign5001);
		//console.log(reg_name.indexOf(my_var_1) >= 0);
		//console.log(my_var_1.indexOf(call5001) >= 0);
		//console.log(all_assign5001.indexOf(pr.id.username) >= 0);
		//forWS(tt);
	}
};

callback = function(message) {
	if(flag == 0){
		/*
		 * Если submit нажата с Appeal/refresh/ то отправляется
		 * по сокету message на всех клиентов - это и есть сигнал всем обновляться 
		 */
		if(location.href.indexOf("Appeal/refresh/") < 0)
		{		
			
			let pr = JSON.parse(message.body);
			let qw = pr.process || "";
			if(qw.indexOf('closemes') >= 0 ){ process_callback_closemes(pr); }
			else if(qw.indexOf('add') >= 0){	body_add(pr);	 }
			else{forWS(tt);}
				
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


function closemes(id,role,state,th,us){
	
	let tab_tr = $(th).parent().parent(); //tr
	if(state	> 	2){
		flag = 1;
		
		
		//$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
		//$('#divrefresh').animate({opacity: 0.6}, 2000 );
		$.ajax({
			type : "GET",
			url : "close",
			data : ({petitId: id,role : role, user: us}),
			success : function(response) {
				
			        	// tt - глобальная
			        	let role_o = tt.replace(/\s/g, ''); // space
				
						let td_lock = tab_tr.children("td:nth-child(14)");
						let td_edit = tab_tr.children("td:nth-child(13)");
						let td_del = tab_tr.children("td:nth-child(12)");
						let td_id = tab_tr.children("td:nth-child(1)");
			        	td_edit.html("<i class='fa fa-pencil-square-o  fa-2x noactive' aria-hidden='true'></i>");
			        	td_del.html("<i class='fa fa-trash-o fa-2x noactive'></i>");
				        td_lock.html("<a onclick=openmes('"+td_id.html()+"','"+role_o+"') id='iddel'  title='Восстановить закрытое обращение'><i class='fa fa-lock  fa-2x' aria-hidden='true'></i></a>");
				
				        //$('#divrefresh').animate({opacity: 0.0}, 2000 );
				        //setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
			},
			error : function(e) {
				alert("ERROR: ", +'обновите страницу');
				//$('#divrefresh').animate({opacity: 0.0}, 2000 );
				//setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
			},
			done : function(e) {
				//console.log("DONE");
				
			}
		});
	}else{
		$('#info_text').html("Вы пытаетесь закрыть не отработанное обращение.");
		$('#info_socket').css({'display':'block'});
		$('#info_socket').animate({opacity: 0.6}, 3000 );
		setTimeout ("$('#info_socket').css({'display':'none'});",2500);
	}
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
 * обновляет на всех клиентах кроме того с которого произошло
 * добавление,удаление и тп 
 * ОБНОВЛЯЕТ ВЕСЬ СПИСОК 
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
			//console.log('@@ '+btn);
			// Prevent the form from submitting via the browser.
			event.preventDefault();
			
			if(btn == 'Завершить'){
				if(valid(role)){
					new_body(btn,role);
				}
			}
			else if(btn == 'Сохранить'){
				if(valid_save(role)){
					new_body(btn,role);
				}
				
			}
			else{
				new_body(btn,role);
			}
					
		});
		
	}	
	else{
		// $('#draggable').delay(2000).submit();
	}
}


function new_body(btn,role){
	
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
		values["role"] = role;
		
		flag = 1;
		
		//$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
		//$('#divrefresh').animate({opacity: 0.6}, 3000 );
	
		// SEND QUERY AND PROCESS RESPONSE
		$.ajax({
			type : "GET",
			url : "add",
			data : values,
			success : function(response) {
				//console.log('stay here '+JSON.stringify(response));
				//   table(response,role);
				
				// tt - глобальная
				let role_o = tt.replace(/\s/g, ''); // space
				let n = role_o.indexOf("ROLE_ADMIN");
				
				let cssClassonUser = "blink";
				if(response[0].blockger2016.state == 1){
					cssClassonUser = "blink";
				}
				
				if(response[0].blockger2016.state != 1){
					cssClassonUser = "";
				}
				
				if(response[0].blockger2016.state == 2){
					cssClassonUser2 = "someclass2";
				}
				
				if(response[0].blockger2016.state != 2){
					cssClassonUser2 = "";
				}
		 		
				let typ ='';
				if (response[0].typeId == 1) typ = 'ЖАЛОБА';
				if (response[0].typeId == 2) typ = 'ЗАЯВЛЕНИЕ';
				if (response[0].typeId == 3) typ = 'КОНСУЛЬТАЦИЯ';
				if (response[0].typeId == 4) typ = 'ПРЕДЛОЖЕНИЕ';
	
				let text = "<tr class="+cssClassonUser2+">"+ "<td class='cuting2'>"+response[0].id+"</td>"+"<td class='cuting2'>"+response[0].dateInput+"</td>"+"<td>"+response[0].date_change+"</td>"+"<td class='cuting2'>"+typ+"</td>"+
				"<td class='cuting'>"+response[0].surname+"</td>"+ "<td class='cuting'>"+response[0].name+"</td>"+ "<td class='cuting2'>"+response[0].patrony+"</td>"+ "<td>"+response[0].tel+"</td>"+  "<td class='cuting2'>"+response[0].blockger2016.regname+"</td>"+ "<td class="+cssClassonUser+">"+response[0].username+"</td>"+
				"<td><a href='nightcallfile/"+response[0].id+"' title='Прослушать'><i class='fa fa-headphones fa-2x'></i></a></td> <td><a onclick=del('"+response[0].id+"','"+role_o+"') id='iddel' title='Удалить'><i class='fa fa-trash-o fa-2x'></i></a></td>"+"" +
				"<td><a id='iddel' href='refresh/"+response[0].id+"' title='Редактировать'><i class='fa fa-pencil-square-o  fa-2x' aria-hidden='true'></i></a></td>";
				
				if (n >= 0){
		        	text +=	"<td><a onclick=closemes('"+response[0].id+"','"+role_o+"','"+response[0].blockger2016.state+"',this,'"+my_var_1+"') id='iddel'  title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
	    		}
	    		else{ text += "<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>"; }

				text +="</tr>";
				$('#cont > tbody').prepend(text);
				
				
				// зачищаем форму
				$("form").each(function(){
				   this.reset();
				   $('#inbound_from').fadeOut();
				});
				// прячем доп причины
				let test = $("[id*='div_subtype']");
				for(let i = 0;i < test.length; i++){
					if(test[i].style.display == 'block'){
						let ch = test[i].children[1];
						ch.disabled = true;
						ch = test[i].children[3];
						ch.disabled = true;
						test[i].style.display='none';
					}	
				}
				// добовляем п умолчанию dateInput
				$( "#dateInput" ).datepicker( "setDate", new Date());
		        
		        
		        //$('#divrefresh').animate({opacity: 0.0}, 2000 );
		        //setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
			},
			error : function(e) {
				
				alert("ERROR: ", +'обновите страницу');
				console.log('Тест'+ JSON.stringify(e));
				
				//$('#divrefresh').animate({opacity: 0.0}, 2000 );
		        //setTimeout ("$('#divrefresh').css({'display':'none'});",2500);
			},
			done : function(e) {
				console.log("DONE");
				
			}
		});
	}	
}



/*
 * Depricated
 * After optimizaton 
 * 
 */
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
		 "<thead><tr><th class='cuting2'>НОМЕР</th><th class='cuting2'>ДАТА ПОСТУПЛЕНИЯ</th><th  class='cuting2'>ДАТА ОТВЕТА</th><th class='cuting2'>ТИП</th><th>ФАМИЛИЯ</th><th>ИМЯ</th><th class='cuting2'>ОТЧЕСТВО</th><th>ТЕЛЕФОН</th><th>РЕГИСТРАТОР</th><th>ИСПОЛНИТЕЛЬ</th><th></th><th></th><th></th><th></th></tr></thead><tbody>";
	
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
	let type ='';
	let type2 ='';
	if (value.typeId == 1) type = 'ЖАЛОБА';
	if (value.typeId == 2) type = 'ЗАЯВЛЕНИЕ';
	if (value.typeId == 3) type = 'КОНСУЛЬТАЦИЯ';
	if (value.typeId == 4) type = 'ПРЕДЛОЖЕНИЕ';
	
	if (value.presentId == 1) type2 = '';
	if (value.presentId == 2) type2 = value.blockger2016.date_plan_end;
	
	
	
	if (value.surname == null) value.surname = '';
	if (value.name == null) value.name = '';
	if (value.patrony == null) value.patrony = '';
	if (value.tel == null) value.tel = '';
	
	userInfo +="<tr class="+cssClass+"><td class='cuting2'>"+value.id+"</td><td class='cuting2'>"+value.dateInput+"</td><td  class='cuting2'>"+type2+"</td><td class='cuting2'>"+type+"</td><td class='cuting'>"+value.surname+"</td><td class='cuting'>"+value.name+"</td><td class='cuting2'>"+value.patrony+"</td><td class='cuting'>"+value.tel+"</td><td>"+value.blockger2016.regname+"</td><td class="+cssClassonUser+">"+value.username+"</td>";
	
	if(value.blockger2016.state != 4){
		userInfo +="<td><a href='nightcallfile/"+value.id+"' title='Прослушать'><i class='fa fa-headphones fa-2x'></i></a></td> <td><a onclick=del('"+value.id+"','"+role+"') id='iddel' title='Удалить'><i class='fa fa-trash-o fa-2x'></i></a></td><td><a id='iddel' href='refresh/"+value.id+"' title='Редактировать'><i class='fa fa-pencil-square-o  fa-2x' aria-hidden='true'></i></a></td>";
		
		var n = role.indexOf("ROLE_ADMIN");
		if (n >= 0){
			if(value.presentId == 2){
				if(value.blockger2016.state != 2){
					userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"','"+value.blockger2016.state+"',this,'"+my_var_1+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
				}
				if(value.blockger2016.state == 2){
					userInfo +="<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>";
				}
			}
			if(value.presentId != 2){
				userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"','"+value.blockger2016.state+"',this,'"+my_var_1+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
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
// прячем доп причины
let test = $("[id*='div_subtype']");
for(let i = 0;i < test.length; i++){
	if(test[i].style.display == 'block'){
		let ch = test[i].children[1];
		ch.disabled = true;
		ch = test[i].children[3];
		ch.disabled = true;
		test[i].style.display='none';
	}	
}
// добовляем п умолчанию dateInput
$( "#dateInput" ).datepicker( "setDate", new Date());

	
}



function tablews(response,role){
	
	role = role.replace(/\s/g, ''); // space
	$container = $("#cont");
    $container.empty();
	
	 userInfo =  
	 "<thead><tr><th class='cuting2'>НОМЕР</th><th class='cuting2'>ДАТА ПОСТУПЛЕНИЯ</th><th  class='cuting2'>ДАТА ОТВЕТА</th><th class='cuting2'>ТИП</th><th>ФАМИЛИЯ</th><th>ИМЯ</th><th class='cuting2'>ОТЧЕСТВО</th><th>ТЕЛЕФОН</th><th>РЕГИСТРАТОР</th><th>ИСПОЛНИТЕЛЬ</th><th></th><th></th><th></th><th></th></tr></thead><tbody>";
	
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
	
	if (value.presentId == 1) type2 = '';
	if (value.presentId == 2) type2 = value.blockger2016.date_plan_end;
	
	if (value.surname == null) value.surname = '';
	if (value.name == null) value.name = '';
	if (value.patrony == null) value.patrony = '';
	if (value.tel == null) value.tel = '';
	
	userInfo +="<tr class="+cssClass+"><td class='cuting2'>"+value.id+"</td><td class='cuting2'>"+value.dateInput+"</td><td  class='cuting2'>"+type2+"</td><td class='cuting2'>"+type+"</td><td class='cuting'>"+value.surname+"</td><td class='cuting'>"+value.name+"</td><td class='cuting2'>"+value.patrony+"</td><td class='cuting'>"+value.tel+"</td><td>"+value.blockger2016.regname+"</td><td class="+cssClassonUser+">"+value.username+"</td>";
	
	if(value.blockger2016.state != 4){
		userInfo +="<td><a href='nightcallfile/"+value.id+"' title='Прослушать'><i class='fa fa-headphones fa-2x'></i></a></td> <td><a onclick=del('"+value.id+"','"+role+"') id='iddel' title='Удалить'><i class='fa fa-trash-o fa-2x'></i></a></td><td><a id='iddel' href='refresh/"+value.id+"' title='Редактировать'><i class='fa fa-pencil-square-o  fa-2x' aria-hidden='true'></i></a></td>";
		
		var n = role.indexOf("ROLE_ADMIN");
		if (n >= 0){
			if(value.presentId == 2){
				if(value.blockger2016.state != 2){
					userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"','"+value.blockger2016.state+"',this,'"+my_var_1+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
				}
				if(value.blockger2016.state == 2){
					userInfo +="<td><i class='fa fa-unlock  fa-2x noactive' aria-hidden='true'></i></td>";
				}
			}
			if(value.presentId != 2){
				userInfo +="<td><a id='iddel' onclick=closemes('"+value.id+"','"+role+"','"+value.blockger2016.state+"',this,'"+my_var_1+"') title='Закрыть обращение'><i class='fa fa-unlock  fa-2x' aria-hidden='true'></i></a></td>";
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


function valid(var_role){
	
	let n = var_role.indexOf("ROLE_ER");
	 let n2 = var_role.indexOf("ROLE_ADMIN");
	 
	 if($('#connectid').val() == 7 && n < 0 && n2 < 0 ){
			$('.errorrep').append('<h3>У Вас нет прав создавать обращение с типом "Горячая линия" в поле Связь</h3>');
			$('.errorrep').css({'display':'block'});
			$('#draggable').animate({
		        scrollTop:  0
	    	});
			return false;
	 }

	 else if($('#type').val() == 0){
		$('.errorrep').append('<h3>Поле "Тип" обязательно для заполнения</h3>');
		$('.errorrep').css({'display':'block'});
		$('#draggable').animate({
	        scrollTop:  0
    	});
		return false;
	}
	else if($('#cause').val() == 0){
		$('.errorrep').append('<h3>Поле "Причина" обязательно для заполнения</h3>');
		$('.errorrep').css({'display':'block'});
		$('#draggable').animate({
	        scrollTop:  0
    	});
		return false;
	}
	else if(($('#type').val() == 1 && $('#cause').val() == 2 && $('#rectif1').val() == 0) ||
			($('#type').val() == 1 && $('#cause').val() == 4 && $('#rectif1').val() == 0) ||
			($('#type').val() == 1 && $('#cause').val() == 11 && $('#rectif1').val() == 0)||
			($('#type').val() == 1 && $('#cause').val() == 13 && $('#rectif1').val() == 0)){
		
		$('.errorrep').append('<h3>Поле "Уточнение1" обязательно для заполнения</h3>');
		$('.errorrep').css({'display':'block'});
		$('#draggable').animate({
	        scrollTop:  0
    	});
		return false;
	}
	else if($('#inbound_from').val() == null && $('#inbound_from').is(':visible')){
		$('.errorrep').append('<h3>Поле "От кого" обязательно для заполнения</h3>');
		$('.errorrep').css({'display':'block'});
		$('#draggable').animate({
	        scrollTop:  0
    	});
		return false;
	}
	else if(	($('#date_redirect').val() != '' && $('#date_redirect').is(':visible')  ||  $('#redirect_adress').val() != 0) &&
				($('#date_redirect').val() == '' ||  $('#redirect_adress').val() == 0)){
		
					$('.errorrep').append('<h3>При заполненом поле "Дата перенаправления для рассмотрения" и "Адресат" необходимо "Дата окончательного ответа гражданину","Номер","Ответственный"</h3>');
					$('.errorrep').css({'display':'block'});
					$('#draggable').animate({
				        scrollTop:  0
			    	});
					return false;
	}
	else if(	(($('#redirect_adress').val() != 0 && $('#redirect_adress').is(':visible')) && $('#date_redirect').val() != '') &&
				($('#date_response').val() == 0 || $('#responsible').val() == 0)){
			$('.errorrep').append('<h3>При заполненом поле "Дата перенаправления для рассмотрения" и "Адресат" необходимо "Дата окончательного ответа гражданину","Номер","Ответственный"</h3>');
			$('.errorrep').css({'display':'block'});
			$('#draggable').animate({
		        scrollTop:  0
	    	});
			return false;
	}
    else if($('#moId').val() != 0 && ($('#hspId').val() == 0 || $('#typempid').val() == 0)){
    	$('.errorrep').append('<h3>При заполненном поле "МО" заполнение полей "Вид МП" и "Тип МО" обязательно</h3>');
    	$('.errorrep').css({'display':'block'});
    	$('#draggable').animate({
	        scrollTop:  0
    	});
    	return false;
	}else{
		$('.errorrep').empty();
		$('.errorrep').css({'display':'none'});
		
		return true;
	}
	
}

function valid_save(var_role){
	
	 let n = var_role.indexOf("ROLE_ER");
	 let n2 = var_role.indexOf("ROLE_ADMIN");
	 
	 if($('#connectid').val() == 7 && n < 0 && n2 < 0 ){
			$('.errorrep').append('<h3>У Вас нет прав создавать обращение с типом "Горячая линия" в поле Связь</h3>');
			$('.errorrep').css({'display':'block'});
			$('#draggable').animate({
		        scrollTop:  0
	    	});
			return false;
	 }
	 else{
		$('.errorrep').empty();
		$('.errorrep').css({'display':'none'});
		
		return true;
	 }
	
}

//======================================= edit ======================================================

