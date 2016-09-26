

function valid(){ 
	if($('#dateBegin').val() == '' || $('#dateEnd').val() == ''){
		$('.error_search').append('<h3>Поля "Дата начала" и "Дата окончания" необходимо заполнить</h3>');
		$('.error_search').css({'display':'block'});
		return true;}
	else{	return false;	}
	
}