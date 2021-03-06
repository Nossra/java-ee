<jsp:include page="header.jsp">
	<jsp:param value="Workout plans" name="title" />
</jsp:include>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<%@page import="viewmodel.LoginViewModel"%>
<%@page import="entities.Exercise"%>
<%@page import="java.util.List"%>

<% LoginViewModel user = (LoginViewModel) session.getAttribute("user"); %>
<div class="row">
	<div class="jumbotron" style="width: 100% !important; padding-left:5% !important;padding-right:5% !important;">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="viewplans">Manage plans</a></li>
                <li class="breadcrumb-item active">Create plan</li>
            </ol>
		<h1 class="display-3">
			Create plans for
			<% out.println(user.getUsername()); %>
		</h1>
		<hr class="my-4">

	</div>
</div>
<div class="row" >
	<div class="col-12">
		<form method="post" style="margin:5%;"> 
			<div class="form-group" id="step1">
				<legend>Step 1, Plan setup</legend>
				<label>Plan name</label> 
				<input type="text" class="form-control" name="planName" id="planName" placeholder="Enter plan name">
				<div id="planWarning" style="color:red !important;"></div>
				<label for="amountOfDays">Amount of days</label>
				<select class="form-control" name="amountOfDays" id="amountOfDays">
					<option value="" disabled selected>Select how many times you work out</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
			    </select>
			    <div id="daysWarning" style="color:red !important;"></div>
			    <button type="button" onclick="step2Btn()" class="btn btn-primary" style="margin-top:3%;" id="nextStepBtn" name="nextStepBtn">Next step</button>
			</div>
			<div class="form-group" id="step2" style="display:none !important;">
				<legend>Step 2, exercises</legend>
 				<div id="exerciseDiv"></div>
 				<button type="button" onclick="addExercisesToDay()">Add exercise</button>
			</div>
			
			<div class="form-group" id="exercises">
			
			</div>
			
		</form>
	</div>
</div>
</div>
    
<script>


function addExercisesToDay() {
	var ex = document.getElementById("exerciseDiv");
	ex.innerHTML = "";
	ex.innerHTML =
		"<label>Choose exercise</label>" 
	+	"<select class=\"form-control\" onchange=\"ex()\" name=\"exerciseSelectList\" id=\"exerciseSelectList\">"
	+	"<option value=\"\" disabled selected>Choose your exercise</option>"
	+    "</select>"
	+    "<label for=\"sets\">Amount of sets</label>"
	+    "<select type=\"text\" name=\"sets\" onchange=\"createSetFields()\" id=\"sets\" placeholder=\"Amount of sets\" class=\form-control\">"
	+       "<option disabled selected>Sets for this exercise</option>"
	+        "<option value=\"1\">1</option>"
	+        "<option value=\"2\">2</option>"
	+        "<option value=\"3\">3</option>"
	+        "<option value=\"4\">4</option>"
	+        "<option value=\"5\">5</option>"
	+    "</select>"
	+	 "<button type=\"button\" id=\"goback\" class=\"btn btn-primary\" style=\"margin-top:3% !important\" onclick=\"goBack()\">Go back</button>"
	callExerciseData();
}

function goBack() {
	var step1 = document.getElementById("step1").style = "display: visible !important;"
	var step2 = document.getElementById("step2").style = "display: none !important;"
	var div = document.getElementById("exercises").innerHTML = "";	
}

function callExerciseData() {
	var e = document.getElementById("exerciseSelectList");
	/*	plan.name = document.getElementById("planName").value;
		plan.amountOfDays = document.getElementById("amountOfDays").value;*/
		if (e.length < 2) {
			$.ajax({
				"url": "http://localhost:8080/GymTrackerTest/api/exercise",
				"method": "get",
 				"success": function(result) {
					for(var i = 0; i < result.length;i++) {
						var option = document.createElement("option");
						option.innerText = result[i].name;
						option.value = result[i].id;
						e.appendChild(option);
					}					
 	 			}
			})
		}
}

function step2Btn() {
	if (validation()) {
		var step1 = document.getElementById("step1").style = "display: none !important;"
		var step2 = document.getElementById("step2").style = "display: visible !important;"
	}
}

var planExercises = {};

function createSetFields() {
	var e = document.getElementById("sets");
	var val = e.options[e.selectedIndex].value;
	var div = document.createElement('div');
	var ex = document.getElementById("exercises");
	ex.innerHTML = "";
	/* ex.innerHTML += "<input type='hidden' name='planName' value='" + plan.name + "' />";
	ex.innerHTML += "<input type='hidden' name='amountOfDays' value='" + plan.amountOfDays + "' />"; */
	for (var i = 0; i < val; i++) {
		div.innerHTML += 
		"<legend>Set "+(i+1)+"</legend>"
	+	"<label>Weights</label>" 
	+	"<input type=\"text\" name=\"weights"+(i+1)+"\" id=\"weights"+(i+1)+"\" placeholder=\"Weights\" class=\"form-control\">"
	+	"<label>Repetitions</label>"
	+	"<input type=\"text\" name=\"repetitions"+(i+1)+"\" id=\"repetitions"+(i+1)+"\" placeholder=\"Repetitions\" class=\"form-control\">"
	}
	div.innerHTML += "<button type=\"button\" onclick=\"addExercise()\" class=\"btn btn-primary\" style=\"margin-top: 3%;\">Add exercise</button>";

	ex.appendChild(div);
}

function addExercise() {
	var val = e.options[e.selectedIndex].value;
	for (var i = 0; i < val; i++) {
		planExercises.weights = document.getElementById("weights" + (i+1)).value;
		planExercises.repetitions = document.getElementById("repetitions" + (i+1)).value;
	}
}

function validation() {
	if (planNameCharacterValidation()) {
		if (planNameLengthValidation()) {
			if (daysValidation()) {
				return true;
			} else {
				var warning = document.getElementById("daysWarning");
				warning.innerHTML = "You must select how many times you work out!";
				return false;
			}
		} else {
			var warning = document.getElementById("planWarning");
			warning.innerHTML = "Plan name is too short!";
			return false;
		}
	} else {
		var warning = document.getElementById("planWarning");
		warning.innerHTML = "Unallowed character found";
		return false;
	}
}

function planNameLengthValidation() {
	var input = document.getElementById("planName").value;
	if (input.length < 3) {
		return false;
	} else {
		var warning = document.getElementById("planWarning");
		warning.innerHTML = ""
		return true;
	}
}

function planNameCharacterValidation() {
	window.localStorage.clear();
	var input = document.getElementById("planName").value;
	var allowed = "abcdefghijklmnopqrstuvwxyz���ABCDEFGHIJKLMNOPQRSTUVWXYZ���0123456789undefined ";

	for (var i = 0; i < allowed.length; i++) {
		if (allowed.indexOf(input[i]) == -1) {
			return false;
		}
	}
	var warning = document.getElementById("planWarning");
	warning.innerHTML = ""
	return true;
}

function daysValidation() {
	var days = document.getElementById("amountOfDays");
	var val = days.options[days.selectedIndex].value;
	if (val < 1) {
		return false;
	} else { 
		var warning = document.getElementById("daysWarning");
		warning.innerHTML = ""
		return true;
	}
}	
</script>
</body>
</html>