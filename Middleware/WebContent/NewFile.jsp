<%-- http://www.w3schools.com/tags/att_option_selected.asp
http://www.dcc.fc.up.pt/~zp/aulas/0607/es/geral/bibliografia/O%27Reilly%20-%20JavaServer%20Pages_2nd%20Edition.pdf Seite 92
http://www.se.uni-hannover.de/pages/de:tutorials_helloworld_jsp
http://www.torsten-horn.de/techdocs/jee-tomcat-eclipse.htm#JSP
http://www.torsten-horn.de/techdocs/jsp-grundlagen.htm

http://www.jsp-develop.de/knowledgebase/view/271/
http://de.selfhtml.org/dhtml/modelle/dom.htm
http://de.selfhtml.org/javascript/objekte/options.htm
http://www.javascriptkit.com/jsref/select.shtml


http://www.java-forum.org/web-tier/136754-string-array-javascript.html
--%>

<!DOCTYPE html">


<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
 

<head>
 <script type="text/Javascript" src="j-query-1.9.1.js" ></script>
 <script type="text/Javascript" src="stomp.js" ></script>

<script language="javascript">
var rowId=0;
var rowsController=0;
var getRowsController=0;
//http://stackoverflow.com/questions/6685249/jquery-performing-synchronous-ajax-requests
$.ajaxSetup({async:false});
 function myFunction()
 {var tabelle=document.getElementById("table");

var row = document.createElement("tr");
row.setAttribute("id",""+rowId);

 for (var i=0;i<3;i++){
	 var tabellenzelle = document.createElement("td");
 var select=document.createElement("select");
 select.setAttribute("style","width:140px");
 select.setAttribute("id",rowId+"_"+i);
// select.setAttribute("onChange","setMethods1("+select.getAttribute("id")+","+rowId+")");
tabellenzelle.appendChild(select);
row.appendChild(tabellenzelle);}

 var tabellenzelle = document.createElement("td");
row.appendChild(tabellenzelle);
 for (var j=3;j<5;j++){
	 var tabellenzelle = document.createElement("td");
 var select=document.createElement("select");
 select.setAttribute("id",rowId+"_"+j);
 select.setAttribute("style","width:140px");
 //select.setAttribute("onChange","setMethods("+select+","+rowId+")");
tabellenzelle.appendChild(select);
row.appendChild(tabellenzelle);}
//http://stackoverflow.com/questions/5656392/how-to-create-input-type-text-dynamically
//http://forum.joergkrusesweb.de/textfeld-mit-javascript-deaktivieren-t-696-1.html
var tabellenzelle = document.createElement("td");
 var textField=document.createElement("input");
 textField.setAttribute("id",rowId+"_"+5);
 textField.setAttribute("style","width:140px");
 textField.setAttribute("type","text");
 textField.setAttribute("readonly","true");
tabellenzelle.appendChild(textField);
row.appendChild(tabellenzelle);

var tabellenzelle = document.createElement("td");
 var select=document.createElement("select");
 select.setAttribute("id",rowId+"_"+6);
 select.setAttribute("style","width:140px");
tabellenzelle.appendChild(select);
row.appendChild(tabellenzelle);

tabellenzelle=document.createElement("td");
var deleteButton=document.createElement("input");
deleteButton.setAttribute("type","button");
deleteButton.setAttribute("value","-");
deleteButton.setAttribute("onclick","deleteRow("+row.id+")");
row.appendChild(deleteButton);
tabelle.appendChild(row);
rowId=rowId+1;

setClasses();
setMethods();
setNames();
setStructure();

 }
 
function setClasses(){
	var tabelle=document.getElementById("table");
	var rows=tabelle.getElementsByTagName("tr");
	for(var n=2;n<rows.length;n++){ 
	var row=rows[n];
    var rowid = row.id;
schicke("","",rowid,"getClasses1","0");
//http://forum.jquery.com/topic/binding-an-event-to-a-select-onchange
$('#'+rowid+'_0').change(function()
		    {
		      classChanged(this,2);
		     methodChanged(this);
		    });
	 

		schicke("","",rowid,"getClasses2","3");	
		//http://forum.jquery.com/topic/binding-an-event-to-a-select-onchange
		$('#'+rowid+'_3').change(function()
				    {
				      classChanged(this,2);
				     methodChanged(this);
				    });
	}	
	
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
schicke(select1,"",rowid,"Method1","1");

	}}
    if($('#'+rowid+'_4 option:selected').text()==""){
	var select2=$('#'+rowid+'_3 option:selected').text();
	if(select2.valueOf()!="".valueOf()){
		schicke(select2,"",rowid,"Method2","4");
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
schicke(select1,"",rowid,"Names1","2");
	
	}} 
	if($('#'+rowid+'_6 option:selected').text()==""){
	var select2=$('#'+rowid+'_3 option:selected').text();
	if(select2.valueOf()!="".valueOf()){
		schicke(select2,"",rowid,"Names2","6");
			
			}
	}}
	
}
function setStructure(){
	var tabelle=document.getElementById("table");
	var rows=tabelle.getElementsByTagName("tr");
	for(var n=2;n<rows.length;n++){ 
	var row=rows[n];
    var rowid = row.id; 
	if($('#'+rowid+'_5 option:selected').text()==""){
	var select2=$('#'+rowid+'_3 option:selected').text();
	var select3=$('#'+rowid+'_4 option:selected').text();
	if(select2.valueOf()!="".valueOf()&&select3.valueOf()!="".valueOf()){
		
		schickeStructure(select2,select3,rowid,"Structure","5");
			
			}
	}}
	
}
function classChanged(select, component){
	var rowid=select.id.split("_")[0];
	var select1=$('#'+select.id+' option:selected').text();
	
	if(select1.valueOf()!="".valueOf()){
		if(component==1){
schicke(select1,"",rowid,"Method1","1");
schicke(select1,"",rowid,"Names1","2");}
		}
		if(component==2){
			schicke(select1,"",rowid,"Method2","4");
			schicke(select1,"",rowid,"Names2","6");
			
		}
	
	}
function methodChanged(select){
	var rowid=select.id.split("_")[0];
	 var select1=$('#'+rowid+'_3 option:selected').text();
	var select2=$('#'+rowid+'_4 option:selected').text();
	
	if(select1.valueOf()!="".valueOf()&&select2.valueOf()!="".valueOf()){
schickeStructure(select1,select2,rowid,"Structure","5");

	}}	
	
	 function getControllerSize(){
		 $.get("Servlet",{type:"getControllerSize",Class:"",method:""},
				 function(data){
				//http://www.javascripter.net/faq/convert2.htm
			 getRowsController=parseInt(data);				
		 });
	 }
	 function getClassNames(component){
		 $.get("Servlet",{type:"getClasses",Class:"",method:"",Component:component},
				 function(data){alert(data);
				//http://www.javascripter.net/faq/convert2.htm
			return data;				
		 });
	 }
	 function getController(){
	 getControllerSize();
	 addNewRow(getRowsController);
		 $("Servlet",{type:"getController",Class:"",method:""},
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
				 function(data){ 
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
 function schicke(classes,method,rowid,type,col){
	 $.get("Servlet",{type:type,Class:classes,method:method},
			 function(data){
			 var ll=data.split(",");
			$("#"+rowid+"_"+col).find('option').remove();
			
				for(var j=0;j<ll.length;j++){
					//http://stackoverflow.com/questions/317095/how-do-i-add-options-to-a-dropdownlist-using-jquery
					$("#"+rowid+"_"+col).append(new Option(ll[j],ll[j]));}		
	 });
	 
 }
 function schickeStructure(classes,method,rowid,type,col){
	 $.get("Servlet",{type:type,Class:classes,method:method},
			 function(data){
			 var ll=data.split(",");
			
				for(var j=0;j<ll.length;j++){
					//http://stackoverflow.com/questions/463506/how-do-i-get-the-value-of-a-textbox-using-jquery
					$("#"+rowid+"_"+col).val(ll[j]);}		
	 });
	 
 }
 function addNewRow(j){
	for(var i=0;i<j;i++){
		myFunction();
	}  rowsController=rowsController+j;
 }
 //http://stackoverflow.com/questions/170997/what-is-the-best-way-to-remove-a-table-row-with-jquery
 function deleteRow(i){
	var tabelle= document.getElementById("table");
	$('#'+i).remove();
	rowsController=rowsController-1;
 }
 function controllerInit(){
	 $.post("Servlet",{type:"init"});
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
			var structure=$("#"+id[0]+"_"+5).val();
			var topic=$("#"+id[0]+"_"+6).val();
			$.post("Servlet",{type:"setController",classInput:classInput,methodInput:methodInput,nameInput:nameInput,classOutput:classOutput,methodOutput:methodOutput,structure:structure,topic:topic});
 }
	
 }
 function test(){
		//$.get("http://localhost:8088/Middleware/Schicht1/Smartphone/rechtsKlick/nummer1");

	  var url = "ws://localhost:61614/stomp";
	  var client = Stomp.client(url);
	  connect_callback = function() {
		    // called back after the client is connected and authenticated to the STOMP server
		  };
	  error_callback = function(error) {
		    // display the error's message header:
		    alert(error.headers.message);
		  };
	  client.connect("guest", "guest");

	 
	  client.send("/queue/test888", {priority: 9}, "Hello, STOMP");
	 }
// $.post("Servlet",{type:"init"});
 //$.get("http://localhost:8088/Middleware/Controller/anmelden/2/SteuerungsMenue/NewFile");

 //http://activemq.apache.org/ajax.html


 
 </script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body onload="getController()">

<h1 align="left">Controller</h1><form><input align="middle" type="submit" name="nextRow" value="initialisieren" onclick="setController(),controllerInit()"/> 
</form>
<script>

</script>

<div id="User">

<table id="table" cellspacing="15" align="middle" style="font-family:'Arial'">
<tr>
<td style="width:150px"> Eingabegerät</td>
<td style="width:150px"> </td>
<td style="width:150px"></td>
<td style="width:40px"></td>
<td style="width:150px"> Ausgabesoftware</td>
<td style="width:150px"> </td>
<td style="width:150px"></td>
<td style="width:150px"></td>
<td style="width:60px"> </td>
</tr>
<tr>
<td> Klasse</td>
<td> Methode</td>
<td>Name</td>
<td></td>
<td> Klasse</td>
<td> Methode</td>
<td>Struktur</td>
<td>Topic</td>
<td></td>
<td> 
	 <input type="submit" name="nextRow" value="+" onclick="addNewRow(1)"/> 
	
	</td>
	<td><form>
	<input type="submit" name="submit" value="OK" onclick="setController(),test()"  /> 
	</form>
	</td>
</tr>
</table>
</div>
</body>
</html>