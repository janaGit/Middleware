
var rowId=0;
var rowsController=0;
var getRowsController=0;
var url = "ws://localhost:61614/stomp";
var client;
var id;
var actual;
var clicked=false;
var XY;
var structure="init,searchMethod,newRow,newRow,newRow,newRow,newRow,newRow,newRow,ok!";
//http://stackoverflow.com/questions/6685249/jquery-performing-synchronous-ajax-requests
$.ajaxSetup({async:false});
function controllerInit(){
	 $.post("Servlet",{type:"init"});
}
function start(){
    $.get("http://localhost:8088/Middleware/Controller/start");
	$.get("http://localhost:8088/Middleware/Controller/anmelden/2/SteuerungsMenue/GUI");
	 connectToBroker();
 }

function newRow()
 {var tabelle=document.getElementById("table");

var row = document.createElement("tr");
row.setAttribute("id",""+rowId);

 for (var i=0;i<3;i++){
	 var tabellenzelle = document.createElement("td");
 var select=document.createElement("select");
 //http://stackoverflow.com/questions/2402146/html-select-selected-option-background-color-css-style
// http://stackoverflow.com/questions/2402146/html-select-selected-option-background-color-css-style
 select.setAttribute("style","width:140px; background-color:white");
 select.setAttribute("id",rowId+"_"+i);
// select.setAttribute("onChange","setMethods1("+select.getAttribute("id")+","+rowId+")");
tabellenzelle.appendChild(select);
row.appendChild(tabellenzelle);}

 var tabellenzelle = document.createElement("td");
row.appendChild(tabellenzelle);
 for (var j=3;j<6;j++){
	 var tabellenzelle = document.createElement("td");
 var select=document.createElement("select");
 select.setAttribute("id",rowId+"_"+j);
 select.setAttribute("style","width:140px;background-color:white");
 //select.setAttribute("onChange","setMethods("+select+","+rowId+")");
tabellenzelle.appendChild(select);
row.appendChild(tabellenzelle);}
//http://stackoverflow.com/questions/5656392/how-to-create-input-type-text-dynamically
//http://forum.joergkrusesweb.de/textfeld-mit-javascript-deaktivieren-t-696-1.html
var tabellenzelle = document.createElement("td");
 var textField=document.createElement("input");
 textField.setAttribute("id",rowId+"_"+6);
 textField.setAttribute("style","width:140px");
 textField.setAttribute("type","text");
 textField.setAttribute("readonly","true");
tabellenzelle.appendChild(textField);
row.appendChild(tabellenzelle);


tabellenzelle=document.createElement("td");
var deleteButton=document.createElement("input");
deleteButton.setAttribute("type","button");
deleteButton.setAttribute("value","-");
deleteButton.setAttribute("id",rowId+"_"+7);
deleteButton.setAttribute("onclick","deleteRow("+row.id+")");
row.appendChild(deleteButton);
tabelle.appendChild(row);

structure=structure+rowId+"_0,"+rowId+"_1,"+rowId+"_2,"+rowId+"_3,"+rowId+"_4,"+rowId+"_5,"+rowId+"_6,"+rowId+"_7,newRow,ok!";
rowId=rowId+1;

setClasses();
setMethods();
setNames();
setData();

 }
function addNewRow(j){ 
	for(var i=0;i<j;i++){
		newRow();
	}  rowsController=rowsController+j;
	$.post("http://localhost:8088/Middleware/Controller/addSimpleStructure/SteuerungsMenue/steuerung/GUI/steuerMenue/"+structure+"/0/8");
	$("#newRow").focus();
	actual="newRow";
	
	$("#newRow").css({'background':'lightgrey'});	
}
//http://stackoverflow.com/questions/170997/what-is-the-best-way-to-remove-a-table-row-with-jquery
function deleteRow(i){
	$('#'+i).remove();
	rowsController=rowsController-1;
		var split= i+"_0,"+i+"_1,"+i+"_2,"+i+"_3,"+i+"_4,"+i+"_5,"+i+"_6,"+i+"_7,newRow,ok!";
	  var splitedStructure= structure.split(split);
	  structure="";
	  for(var n=0;n<splitedStructure.length;n++){
		  structure=structure+splitedStructure[n];
	  }
	 $.post("http://localhost:8088/Middleware/Controller/addSimpleStructure/SteuerungsMenue/steuerung/GUI/steuerMenue/"+structure+"/0/8");
actual="newRow";
$("#newRow").focus();
$("#newRow").css({'background':'lightgrey'});	
}
function setClasses(){
	var tabelle=document.getElementById("table");
	var rows=tabelle.getElementsByTagName("tr");
	for(var n=2;n<rows.length;n++){ 
	var row=rows[n];
    var rowid = row.id;
    if($('#'+rowid+'_0 option:selected').text()==""){
send("","",rowid,"getClasses1","0");
//http://forum.jquery.com/topic/binding-an-event-to-a-select-onchange
$('#'+rowid+'_0').change(function()
		    {
		      classChanged(this,1);
		     methodChanged(this);
		    });
	 

		send("","",rowid,"getClasses2","3");	
		//http://forum.jquery.com/topic/binding-an-event-to-a-select-onchange
		$('#'+rowid+'_3').change(function()
				    {
				      classChanged(this,2);
				     methodChanged(this);
				    });
	}}	
	
 }
 
 
 //http://homepages.thm.de/~hg51/Veranstaltungen/JEE/Aufgaben/jee-ue-02.pdf
 //http://forum.jswelt.de/javascript/53133-jquery-tabellen-spalten-werte-ausgew-hlten-zeilen-auslesen.html
 //http://stackoverflow.com/questions/47824/how-do-you-remove-all-the-options-of-a-select-box-and-then-add-one-option-and-se
function setMethods(){
	var tabelle=document.getElementById("table");
	var rows=tabelle.getElementsByTagName("tr");
	for(var n=2;n<rows.length;n++){ 
	var row=rows[n];
    var rowid = row.id;
    if($('#'+rowid+'_1 option:selected').text()==""){
	var select1=$('#'+rowid+'_0 option:selected').text();
	if(select1.valueOf()!="".valueOf()){
send(select1,"",rowid,"Method1","1");

	}}
    if($('#'+rowid+'_4 option:selected').text()==""){
	var select2=$('#'+rowid+'_3 option:selected').text();
	if(select2.valueOf()!="".valueOf()){
		send(select2,"",rowid,"Method2","4");
		$('#'+rowid+'_4').change(function()
			    {
			      methodChanged(this);
			     
			    });	
			}
	} }
	 }
function setNames(select){
	var tabelle=document.getElementById("table");
	var rows=tabelle.getElementsByTagName("tr");
	for(var n=2;n<rows.length;n++){ 
	var row=rows[n];
    var rowid = row.id;
    if($('#'+rowid+'_2 option:selected').text()==""){
	var select1=$('#'+rowid+'_0 option:selected').text();
	if(select1.valueOf()!="".valueOf()){
     send(select1,"",rowid,"Names1","2");
	
	}} 
	if($('#'+rowid+'_5 option:selected').text()==""){
	var select2=$('#'+rowid+'_3 option:selected').text();
	if(select2.valueOf()!="".valueOf()){
		send(select2,"",rowid,"Names2","5");
			
			}
	$('#'+rowid+'_5').change(function()
		    {
		      methodChanged(this);
		     
		    });	
	
	}
	
	}
	
}
function setData(){
	var tabelle=document.getElementById("table");
	var rows=tabelle.getElementsByTagName("tr");
	for(var n=2;n<rows.length;n++){ 
	var row=rows[n];
    var rowid = row.id; 
	if($('#'+rowid+'_6 option:selected').text()==""){
	var select2=$('#'+rowid+'_3 option:selected').text();
	var select3=$('#'+rowid+'_4 option:selected').text();
	var select4=$('#'+rowid+'_5 option:selected').text();
	if(select2.valueOf()!="".valueOf()&&select3.valueOf()!="".valueOf()&&select4.valueOf()!="".valueOf()){

		sendStructure(select2,select3,select4,rowid,"Data","6");
			
			}
	}}
	
}
function classChanged(select, component){
	var rowid=select.id.split("_")[0];
	var select1=$('#'+select.id+' option:selected').text();
	
	if(select1.valueOf()!="".valueOf()){
		if(component==1){
send(select1,"",rowid,"Method1","1");
send(select1,"",rowid,"Names1","2");}
		}
		if(component==2){
			send(select1,"",rowid,"Method2","4");
			send(select1,"",rowid,"Names2","5");
			
		}
	
	}
function methodChanged(select){
	var rowid=select.id.split("_")[0];
	 var select1=$('#'+rowid+'_3 option:selected').text();
	var select2=$('#'+rowid+'_4 option:selected').text();
	var select3=$('#'+rowid+'_5 option:selected').text();
	if(select3==""){select3=" ";}
	if(select1.valueOf()!="".valueOf()&&select2.valueOf()!="".valueOf()&&select3.valueOf()!="".valueOf()){
sendStructure(select1,select2,select3,rowid,"Data","6");

	}}	
	
	 function getControllerSize(){
		 $.get("Servlet",{type:"getControllerSize",Class:"",method:""},
				 function(data){
				//http://www.javascripter.net/faq/convert2.htm
			 getRowsController=parseInt(data);				
		 });
	 }

	 function getController(){
	 getControllerSize();
	 addNewRow(getRowsController);
		 $.get("Servlet",{type:"getController",Class:"",method:""},
				 function(data){ 
				 var ll=data.split(";");
				 for (var i=0;i<ll.length;i++){
					var l=ll[i];
					var values=l.split(",");
					//http://stackoverflow.com/questions/4680075/set-selected-option-of-select-box
					$("#"+i+"_0").val(values[0]);     
				 }});
		 for(var j=0;j<rowsController;j++){
			 classChanged(document.getElementById(j+"_0"),1);
		    
		 } $.get("Servlet",{type:"getController",Class:"",method:""},
				 function(data
						 ){ 
			 var ll=data.split(";");
			 for (var i=0;i<ll.length;i++){
				var l=ll[i];
				var values=l.split(",");
				//http://stackoverflow.com/questions/4680075/set-selected-option-of-select-box
				$("#"+i+"_1").val(values[1]);
				$("#"+i+"_2").val(values[2]);
				$("#"+i+"_3").val(values[3]);  
			 }});	
		 for(var j=0;j<rowsController;j++){
		     classChanged(document.getElementById(j+"_3"),2);
		    
		 }	
		 $.get("Servlet",{type:"getController",Class:"",method:""},
				 function(data){ 
			 var ll=data.split(";");
			 for (var i=0;i<ll.length;i++){
				var l=ll[i];
				var values=l.split(",");
				//http://stackoverflow.com/questions/4680075/set-selected-option-of-select-box
				$("#"+i+"_4").val(values[4]);
			 }});
		 for(var j=0;j<rowsController;j++){
		     methodChanged(document.getElementById(j+"_4"));
		    
		 }
		 $.get("Servlet",{type:"getController",Class:"",method:""},
				 function(data){ 
			 var ll=data.split(";");
			 for (var i=0;i<ll.length;i++){
				var l=ll[i];
				var values=l.split(",");
				//http://stackoverflow.com/questions/4680075/set-selected-option-of-select-box
				$("#"+i+"_5").val(values[5]);
			 }});
		 $.get("Servlet",{type:"getController",Class:"",method:""},
				 function(data){ 
			 var ll=data.split(";");
			 for (var i=0;i<ll.length;i++){
				var l=ll[i];
				var values=l.split(",");
				//http://stackoverflow.com/questions/4680075/set-selected-option-of-select-box
				$("#"+i+"_6").val(values[6]);
				
			 }});
				
	 }
	 function setController(){
		 $.post("Servlet",{type:"deleteController"});
		 var rows=document.getElementsByTagName("tr");
		 for(var i=2;i<rows.length;i++){
		var idrow=rows[i].id;
		var id=idrow.split("_");
				var classInput=$("#"+id[0]+"_"+0).val();
				var methodInput=$("#"+id[0]+"_"+1).val();
				var nameInput=$("#"+id[0]+"_"+2).val();
				var classOutput=$("#"+id[0]+"_"+3).val();
			    var methodOutput=$("#"+id[0]+"_"+4).val();
				var topic=$("#"+id[0]+"_"+5).val();
				var data=$("#"+id[0]+"_"+6).val();
				$.post("Servlet",{type:"setController",classInput:classInput,methodInput:methodInput,nameInput:nameInput,classOutput:classOutput,methodOutput:methodOutput,topic:topic,data:data});
	 }
		
	 }
	 function searchMethods(){
		 $.post("Servlet",{type:"searchMethods"});
		 
	}
 function send(classes,method,rowid,type,col){
	 $.get("Servlet",{type:type,Class:classes,method:method},
			 function(data){
			 var ll=data.split(",");
			$("#"+rowid+"_"+col).find('option').remove();
			
				for(var j=0;j<ll.length;j++){
					//http://stackoverflow.com/questions/317095/how-do-i-add-options-to-a-dropdownlist-using-jquery
					$("#"+rowid+"_"+col).append(new Option(ll[j],ll[j]));}		
	 });
	 
 }
  function sendStructure(classes,method,topic,rowid,type,col){
	 $.get("Servlet",{type:type,Class:classes,method:method,topic:topic},
			 function(data){
			 var ll=data.split(",");
			
				for(var j=0;j<ll.length;j++){
					//http://stackoverflow.com/questions/463506/how-do-i-get-the-value-of-a-textbox-using-jquery
					$("#"+rowid+"_"+col).val(ll[j]);}		
	 });
	 
 }



 
// http://jmesnil.net/stomp-websocket/doc/
 //http://books.google.de/books?id=YIgdrqKZzYMC&pg=PA89&lpg=PA89&dq=activemq+stomp+over+websocket&source=bl&ots=F8Cdt_wXFO&sig=ir3BV-ZSaOVPTZQpEZ-2d98a8Y0&hl=de&sa=X&ei=X6yTUe6JHIrOtAbv4IGwDQ&ved=0CDwQ6AEwAjgK#v=onepage&q=activemq%20stomp%20over%20websocket&f=false
  var callback = function(message) {
		    // called when the client receives a STOMP message from the server
		    if (message.body) {
		    
		    if((""+message.body).valueOf()!="click".valueOf()){
		    if(clicked==false){	
		    	actual=message.body;
		      //www.w3schools.com/js/tryit.asp?filename=tryjs_focus
		     document.getElementById(message.body).focus();
		      //http://stackoverflow.com/questions/5065167/setting-background-color-of-select-options-in-jquery
		    $("select").css({'background':'white'});
		    $("input").css({'background':''});
		    $("text").css({'background':''});
		      $("#"+message.body).css({'background':'lightgrey'});
		    	  //document.getElementById("#0_5").focus();
		      //http://api.jquery.com/jQuery.get/
		      
		      $.get("http://localhost:8088/Middleware/Controller/data/getXY/steuerMenue",
		    	function(data){
		    	  XY=data;
		      }	  
		      );
		    }else{ 
		    if((""+message.body).valueOf()!="Arrayindex out of bounds".valueOf()){
		      $("#"+actual).val(message.body);
		      $("#"+actual).trigger('change');
		    } }}else{ 
		    	if(clicked){
		    	
		    		var splitted=XY.split(",", 2);
		    	clicked=false;
		    	$("#"+actual).attr('size', 1);
		    	$.post("http://localhost:8088/Middleware/Controller/addSimpleStructure/SteuerungsMenue/steuerung/GUI/steuerMenue/"+structure+"/"+splitted[0]+"/"+splitted[1]);
		    }
		    else{

//http://de.selfhtml.org/javascript/objekte/string.htm#search
		    	if(actual.search("._[0-6]")!=-1){
		    	clicked=true;
		    	//http://stackoverflow.com/questions/590163/how-to-get-all-options-of-a-select-using-jquery
		    	var options=document.getElementById(actual).options;
		    	var string ="";$("#"+actual).attr('size', options.length);
		    	for(var i=0;i<(options.length-1);i++){
		    		//http://stackoverflow.com/questions/2888446/get-the-selected-option-id-with-jquery
		    		string=string+options[i].value+"!";
		    	
		    	}string=string+options[options.length-1].value;
		    	//http://stackoverflow.com/questions/1221957/jquery-to-retrieve-and-set-selected-index-value-of-html-select-element
		    	var size=$("#"+actual).prop("selectedIndex");
		    	$.post("http://localhost:8088/Middleware/Controller/addSimpleStructure/SteuerungsMenue/steuerung/GUI/steuerMenue/"+string+"/+"+size+"/0");
		    	//http://www.electrictoolbox.com/jquery-count-select-options/
		    }
		    	else{
		    		$("#"+actual).trigger("onclick");
		    	}
		 //http://stackoverflow.com/questions/4457076/show-select-dropdown-in-jquery
		    	
		    }}	
		    } else {
		      alert("got empty message");
		    }
		  };
		
		  
		 var onconnect = function() {
	    // called back after the client is connected and authenticated to the STOMP server
	 id = client.subscribe("/topic/GUI", callback);
	  };   
error_callback = function(error) {
       // display the error's message header:
       alert(error.headers.message);
     };
 function connectToBroker(){
	 client = Stomp.client(url); 
     client.connect("guest","guest",onconnect,error_callback);
     
     
    
 }

 

 



