		// возвращает cookie если есть или undefined
		function getCookie(name) {
			var matches = document.cookie.match(new RegExp(
			  "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
			))
			return matches ? decodeURIComponent(matches[1]) : undefined 
		}
		
		// уcтанавливает cookie
		function setCookie(name, value, props) {
			props = props || {}
			var exp = props.expires
			if (typeof exp == "number" && exp) {
				var d = new Date()
				d.setTime(d.getTime() + exp*1000)
				exp = props.expires = d
			}
			if(exp && exp.toUTCString) { props.expires = exp.toUTCString() }

			value = encodeURIComponent(value)
			var updatedCookie = name + "=" + value
			for(var propName in props){
				updatedCookie += "; " + propName
				var propValue = props[propName]
				if(propValue !== true){ updatedCookie += "=" + propValue }
			}
			document.cookie = updatedCookie

		}
		
		function calcOffset() {
		    var serverTime = getCookie('serverTime');
		    serverTime = serverTime==null ? null : Math.abs(serverTime);
		    var clientTimeOffset = (new Date()).getTime() - serverTime;
		    setCookie('clientTimeOffset', clientTimeOffset);
		}
		
	 	
		function checkSession() {
		    var sessionExpiry = Math.abs(getCookie('sessionExpiry'));
		    var timeOffset = Math.abs(getCookie('clientTimeOffset'));
		    var localTime = (new Date()).getTime();
		    if (localTime - timeOffset > (sessionExpiry+15000)) { // 15 extra seconds to make sure
		        console.log('Сессия схохла');
		    } else {
		    	console.log('продолжаем...');
		    	setTimeout(checkSession, 10000);        
		    }
		}
		
		calcOffset();		
		checkSession();