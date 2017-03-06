/*
 * All different method with small logic
 */

$(document).ready(function(){
	
	/*
	* Пустые поля прячем по "+"(добавить)
	*/
	$("[id*='div_subresponse']").each(function(){
		if($(this).children("input:first").val() == ''){
			$(this).css({'display':'none'});
		}
	})
	
	/*
	*	Событие клик на кнопке "+" 
	*/
	$('#btn_add_subresponse').click(function add_subresponse(){
		
		let test = $("[id*='div_subresponse']");
		for(let i = 0;i < test.length; i++){
			if(test[i].style.display == 'none'){
				test[i].style.display='block';
				return;
			}
		}
	});

	
});

