var data1 = 0;
var data2 = 0;
var data3 = 0;
var data4 = 0;
var data5 = 0;
var data6 = 0;
var data7 = 0;
var data8 = 0;
var date9 = 0;
var data10 = 0;
var url = "ws://localhost:61614/stomp";
var addressMiddleware="http://localhost:8088/Middleware/Controller/"
var t1;
var t2;
var t3;
var t4;
var t5;
var t6;
var t7;
var t8;
var t9;
var t10;
var number1 = "";
$.ajaxSetup({
	async : false
});
// http://jmesnil.net/stomp-websocket/doc/
// http://books.google.de/books?id=YIgdrqKZzYMC&pg=PA89&lpg=PA89&dq=activemq+stomp+over+websocket&source=bl&ots=F8Cdt_wXFO&sig=ir3BV-ZSaOVPTZQpEZ-2d98a8Y0&hl=de&sa=X&ei=X6yTUe6JHIrOtAbv4IGwDQ&ved=0CDwQ6AEwAjgK#v=onepage&q=activemq%20stomp%20over%20websocket&f=false
var callback1 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t1 = t1 + message.body + ":" + new Date().getTime() + ",";
		number1 = number1 + message.body + ",";
		data1 = message.body;
		$("#data1").text(message.body);

	} else {
		alert("got empty message");
	}
};
var callback2 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t2 = t2 + new Date().getTime() + ",";
		data2 = message.body;
		$("#data2").text(message.body);
	} else {
		alert("got empty message");
	}
};
var callback3 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t3 = t3 + new Date().getTime() + ",";
		data3 = message.body;
		$("#data3").text(message.body);
	} else {
		alert("got empty message");
	}
};
var callback4 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t4 = t4 + new Date().getTime() + ",";
		data4 = message.body;
		$("#data4").text(message.body);
	} else {
		alert("got empty message");
	}
};
var callback5 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t5 = t5 + new Date().getTime() + ",";
		data5 = message.body;
		$("#data5").text(message.body);
	} else {
		alert("got empty message");
	}
};
var callback6 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t6 = t6 + new Date().getTime() + ",";
		data6 = message.body;
		$("#data6").text(message.body);

	} else {
		alert("got empty message");
	}
};
var callback7 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t7 = t7 + new Date().getTime() + ",";
		data7 = message.body;
		$("#data7").text(message.body);
	} else {
		alert("got empty message");
	}
};
var callback8 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t8 = t8 + new Date().getTime() + ",";
		data8 = message.body;
		$("#data8").text(message.body);
	} else {
		alert("got empty message");
	}
};
var callback9 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t9 = t9 + new Date().getTime() + ",";
		data9 = message.body;
		$("#data9").text(message.body);
	} else {
		alert("got empty message");
	}
};
var callback10 = function(message) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		t10 = t10 + new Date().getTime() + ",";
		data10 = message.body;
		$("#data10").text(message.body);
	} else {
		alert("got empty message");
	}
};

var stompCallback = function(message, timeVariable, dataVariable, element) {
	// called when the client receives a STOMP message from the server
	if (message.body) {
		timeVariable = timeVariable + new Date().getTime() + ",";
		dataVariable = message.body;
		$(element).text(message.body);
	} else {
		alert("got empty message");
	}
};

var onconnect = function() {
	// called back after the client is connected and authenticated to the STOMP
	// server
	id = client.subscribe("/topic/Sensor1", stompCallback(message, t1, data1, "#data1"));
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor1");
	id = client.subscribe("/topic/Sensor2", callback2);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor2");
	id = client.subscribe("/topic/Sensor3", callback3);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor3");
	id = client.subscribe("/topic/Sensor4", callback4);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor4");
	id = client.subscribe("/topic/Sensor5", callback5);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor5");
	id = client.subscribe("/topic/Sensor6", callback6);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor6");
	id = client.subscribe("/topic/Sensor7", callback7);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor7");
	id = client.subscribe("/topic/Sensor8", callback8);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor8");
	id = client.subscribe("/topic/Sensor8", callback9);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor9");
	id = client.subscribe("/topic/Sensor8", callback10);
	$
			.get(addressMiddleware+"anmelden/2/SteuerungsMenue/Sensor10");

};

function connectToBroker() {
	client = Stomp.client(url);
	client.connect("guest", "guest", onconnect);
}
function start() {
	$.get(addressMiddleware+"start");
	document.getElementById("b1").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t1: </br>" + t1);
	};
	document.getElementById("b2").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t2: </br>" + t2);
	};
	document.getElementById("b3").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t3: </br>" + t3);
	};
	document.getElementById("b4").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t4: </br>" + t4);
	};
	document.getElementById("b5").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t5: </br>" + t5);
	};
	document.getElementById("b6").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t6: </br>" + t6);
	};
	document.getElementById("b7").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t7: </br>" + t7);
	};
	document.getElementById("b8").onclick = function() {
		$("#ausgabe").html("");
		$("#ausgabe").html("<br> t8: </br>" + t8);
	};
	document.getElementById("v1").onclick = function() {
		$("#ausgabe2").html("");
		$("#ausgabe2").html("<br> v1: </br>" + number1);
	};
}