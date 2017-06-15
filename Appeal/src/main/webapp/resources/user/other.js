/*
 * All different method with small logic
 */

$(document).ready(function(){
	
	
	
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
	
	$('#btn_add_subtype').click(function (event, a,b){
		if($('#type').val() == 1){
			
			let test = $("[id*='div_subtype']");
			for(let i = 0;i < test.length; i++){
				
				if(test[i].style.display == 'none'){
					if(a == undefined){
						test[i].style.display='block';
						let ch = test[i].children[1];
						ch.disabled = false;
						ch = test[i].children[3];
						ch.disabled = false;
					}
					if(a != undefined && a !='' && a != 0){
						test[i].style.display='block';
						let ch = test[i].children[1];
						ch.disabled = false;
						ch = test[i].children[3];
						ch.disabled = false;
					}
					if($('#type option:selected').text() != ''){
						
						// если тригер на событие клик был вызван руками
						if(a === undefined){ $('#type').trigger("change");}
						if(a != undefined && a!='' && a != 0){
									$('select[id="'+test[i].children[1].id+'"]').val(a);
									
									if(b != undefined && b!='' ){
										//$('.subcause_cl').trigger("change",[test[i].children[3].id,a,b]);
										eventChangeSubRefctif(test[i].children[3].id,a,b);
									}
						}
						
					}
					
					return;
				}
			}
		}
	});
	
	function mySandwich(html, callback) {
		if($('.subcause_cl option:selected').text() == ''){$('.subcause_cl').html(html);}

	    if (callback) {
	        callback();
	    }
	}
	
	/*
	 * Событие по полю ТИП обращения
	 */
	$('#type').change(function(event, a,b, c,d, e,s, p,z){
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
        
		$.getJSON(document.location.origin+'/Appeal/causes', {
		typeName : var_type.val(),
		ajax : 'true'
		}, function(data) {
			html = '<option value="0"></option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
			}
			html += '</option>';
			
			// пришли с тригера или ручками (при заполнении формы)
			if (event.originalEvent === undefined) {
				//if($('.subcause_cl option:selected').text() == ''){$('.subcause_cl').html(html);}
				
				// с- id subcause; d- id subrectif
				mySandwich(html, function(){
					if(c != undefined){ $('#btn_add_subtype').trigger("click",[c,d]);}
					if(e != undefined){ $('#btn_add_subtype').trigger("click",[e,s]);}
					if(p != undefined){ $('#btn_add_subtype').trigger("click",[p,z]);}
				});
				
				
				//if(s != undefined){ $('.subcause_cl').trigger("change",[s]);}
				
				
				//if(z != undefined){ $('.subcause_cl').trigger("change",[z]);}
		    } else {
		    	$('#cause').html(html);

				$('.subcause_cl').html(html);
				// при смене Типа 'руками' зачищаем все селекты 'Уточнение1'
				$('.subrectif_cl').html('');
				$('#rectif1').html('');
		    }
			
			// ПРИЧИНА НЕ UNDF
			if(a != undefined){
				$('#cause').html(html);
				if(a != ''){
					$("#cause option:contains('"+a+"')").prop('selected', true);
					$('#cause').trigger("change",[b]);
				}	
			}; 
		});
	});
	/*
	 * Событие по полю Причина
	 */
	$('#cause').change(function(event, a) {
				
				var html = ''; 
				$.getJSON(document.location.origin+'/Appeal/rectifs1', {
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
				    	
			    	if(a != undefined && a!= ''){
						$("#rectif1 option:contains('"+a+"')").prop('selected', true);
					};
					
					
					
				});
				
				
			});
	
	/*
	 * Событие по полю subcause (подпричины)
	 * Обработчик только для ручного режима
	 */
	$('.subcause_cl').change(
			
			function(event) {
				
				// определяем родителя селекта доп причины и берем дочерний узел с 'уточнением1'
				let flatemate_rectifs = this.parentNode.children[3];
				$.getJSON(document.location.origin+'/Appeal/rectifs1', {
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
	
	
	function eventChangeSubRefctif(dom_id_subrectif,id_subcause,id_subrectif){
		
		$.getJSON(document.location.origin+'/Appeal/rectifs1', {
			causeName : id_subcause,
			ajax : 'true'
		}, function(data) {
			var html = '<option value="0"></option>';
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
			}
			html += '</option>';
			
			//flatemate_rectifs.innerHTML = html;
			$('#'+dom_id_subrectif).html(html);
			
			$('select[id="'+dom_id_subrectif+'"]').val(id_subrectif);
			
		});
	}

	
	
});

