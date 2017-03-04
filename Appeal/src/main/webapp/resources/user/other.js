/*
 * All different method with small logic
 */

$(document).ready(function(){
	
	if(	$("[id*='div_subresponse'] input[id*='date_subresponse']").val() == ''){
		$("[id*='div_subresponse']").css({'display':'none'});
	}

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

