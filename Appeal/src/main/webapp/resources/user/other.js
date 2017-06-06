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
		if($('#type').val() == 1){
			let test = $("[id*='div_subtype']");
			for(let i = 0;i < test.length; i++){
				if(test[i].style.display == 'none'){
					test[i].style.display='block';
					let ch = test[i].children[1];
					ch.disabled = false;
					ch = test[i].children[3];
					ch.disabled = false;
					if($('#type option:selected').text() != ''){
						
						$('#type').trigger("change");
					}
					return;
				}
			}
		}
	});
	
	/*
	 * Событие по полю ТИП обращения
	 */
	$('#type').change(function(event){
		
		let html = '';
        let var_type = 	$(this);
        
        // зачищаем доп причины при условии что выбрана не жалоба
        if(var_type.val() == 1){
        	
        	//$('#btn_add_subtype').css({'display':'block'});
        }else{
        	//$('#btn_add_subtype').css({'display':'none'});
        	
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
        }
		
		$.getJSON('causes', {
		typeName : var_type.val(),
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
				// при смене Типа 'руками' зачищаем все селекты 'Уточнение1'
				$('.subrectif_cl').html('');
				$('#rectif1').html('');
				
		    }
		});
	});
	/*
	 * Событие по полю Причина
	 */
	
	$('#cause').change(
			function() {
				$.getJSON('rectifs1', {
					causeName : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
					}
					html += '</option>';

					
				    	// заполняем основное 'уточнение1'
				    	$('#rectif1').html(html);
					/*class="subrectif_cl" id="subrectif"*/			
					
					
				});
			});
	
	/*
	 * Событие по полю subcause (подпричины)
	 */
	$('.subcause_cl').change(

			function() {
				// определяем родителя селекта доп причины и берем дочерний узел с 'уточнением1'
				let flatemate_rectifs = this.parentNode.children[3];
				$.getJSON('rectifs1', {
					causeName : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="0"></option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
					}
					html += '</option>';

					flatemate_rectifs.innerHTML = html;
				});
			});

	
	
});

