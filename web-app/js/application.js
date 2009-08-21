function showSpinner() {
    document.getElementById("spinner").style.visibility = "visible";
}

function toggleAdvanced() {
    for (var i = 1; i <= 7; i++) {
	var ele = document.getElementById("advanced"+i);
	var text = document.getElementById("displayText");
	if (ele.style.display == "table-row") {
            ele.style.display = "none";
            text.innerHTML = "Show Advanced";
  	}
	else {
            ele.style.display = "table-row";
            text.innerHTML = "Hide Advanced";
	}
    }
}

function toggleOptions(name) {
    for (var i = 1; i <= 2; i++) {
	var ele = document.getElementById(name+i)
	if (ele.style.display == "table-row") {
            ele.style.display = "none";
  	}
	else {
            ele.style.display = "table-row";
	}
    }
}