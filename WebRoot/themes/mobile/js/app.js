// JavaScript Document
function startgps() {  
	var gps = navigator.geolocation;  
	if(gps){  
 		gps.getCurrentPosition(showgps, function(error) {  
 				 alert("Got an error, code: " + error.code + " message: "+ error.message);  
 	 	}, {maximumAge: 10000}); // 这里设置超时为10000毫秒，即10秒  
 	} else {  
 		showgps();  
	}  
} 

function showgps(position) {  
	if (position) {  
		var latitude = position.coords.latitude;  
 	 	var longitude = position.coords.longitude;
		$("#latitude").val(latitude);
		$("#longitude").val(longitude);
		
		var url = "/plugin?plugin=mobileapp&method=location";
		var params = "latitude="+latitude+"&longitude="+longitude;
		$.getJSON(url,params,function(json){
			$("#address").val(json.message);
		});
		
		
		
    } else 
 	 	alert("position is null");
}


function renderReverse(obj){
	
	alert(obj.status);
}