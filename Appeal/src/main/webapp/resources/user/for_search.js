

function valid(){ 
	if($('#dateBegin').val() == '' || $('#dateEnd').val() == ''){
		$('.error_search').append('<h3>Поля "Дата начала" и "Дата окончания" необходимо заполнить</h3>');
		$('.error_search').css({'display':'block'});
		return true;}
	else{	return false;	}
	
}


function closemes_search(id,state,th){
	
	let tab_tr = $(th).parent().parent(); //tr
	if(state	> 	2){
		
		console.log('enter');
		
		//$('#divrefresh').css({'display':'block','width':$('#cont').width(),'height':$('#cont').height()});
		//$('#divrefresh').animate({opacity: 0.6}, 2000 );
		$.ajax({
			type : "GET",
			url : "close_search",
			data : ({petitId: id}),
			success : function(response) {
				
				
						let td_lock = tab_tr.children("td:nth-child(20)");
						let td_edit = tab_tr.children("td:nth-child(19)");
						let td_del = tab_tr.children("td:nth-child(18)");
						let td_id = tab_tr.children("td:nth-child(1)");
			        	td_edit.html("<i class='fa fa-pencil-square-o  fa-2x noactive' aria-hidden='true'></i>");
			        	td_del.html("<i class='fa fa-trash-o fa-2x noactive'></i>");
				       // td_lock.html("<a onclick=openmes('"+td_id.html()+"','"+role_o+"') id='iddel'  title='Восстановить закрытое обращение'><i class='fa fa-lock  fa-2x' aria-hidden='true'></i></a>");
			        	 td_lock.html("<a id='iddel'  title='Восстановить закрытое обращение'><i class='fa fa-lock  fa-2x' aria-hidden='true'></i></a>");
				
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
		//$('#info_socket').css({'display':'block'});
		//$('#info_socket').animate({opacity: 0.6}, 3000 );
		//setTimeout ("$('#info_socket').css({'display':'none'});",2500);
	}
}