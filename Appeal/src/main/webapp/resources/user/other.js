/*
 * All different method with small logic
 */

$(document).ready(function(){
	
	/*
	* Пустые поля прячем по "+"(добавить)
	*/
	$("[id*='div_subresponse'],[id*='div_subtype']").each(function(){
		if($(this).children("input:first").val() == ''){
			$(this).css({'display':'none'});
		}
	})
	
	$("[id*='div_subtype']").each(function(){
		
		var $t = $(this).children()[1];
		//alert($( "t option:selected" ).text());
		if($( "t option:selected" ).text() == ''){
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
	

	/*
	 * Кнопка добавления доп причин "+"  
	 */
	
	$('#btn_add_subtype').click(function add_subtype(){
		
		let test = $("[id*='div_subtype']");
		for(let i = 0;i < test.length; i++){
			if(test[i].style.display == 'none'){
				test[i].style.display='block';
				let ch = test[i].children[1];
				ch.disabled = false;
				if($('#type option:selected').text() != ''){
					
					$('#type').trigger("change");
				}
				return;
			}
		}
	});
	
	/*
	 * Событие по полю ТИП обращения
	 */
	$('#type').change(function(event){
		
		let html = '';
		
		$.getJSON('causes', {
		typeName : $(this).val(),
		ajax : 'true'
		}, function(data) {
			html = '<option value="0"></option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
			}
			html += '</option>';
			
			// пришли с тригера или ручками 
			if (event.originalEvent === undefined) {
				if($('.subcause_cl option:selected').text() == ''){$('.subcause_cl').html(html);}
		    } else {
		    	$('#cause').html(html);
				$('.subcause_cl').html(html);
		    }
		});
		
			
				
				
				
			
		
			
			
	});

	
	
});

