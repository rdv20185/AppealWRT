

function notify(){
	console.log('test ');
};

function restart(){
	console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@');
	setTimeout(init, 10000);
}

function init(){
var socket = new SockJS("/Appeal/notify");
var stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
	console.log('Connected:@@@@@@@@@ ' + frame);
	stompClient.subscribe("/topic/notify",  notify());
	//socket.onclose = restart(); 

});
}

init();



