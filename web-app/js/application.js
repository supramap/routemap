function showSpinner() {
    document.getElementById("spinner").style.visibility = "visible";
}

function toggleAdvanced(on, off) {
    for (var i = 1; i <= 7; i++) {
	var ele = document.getElementById("advanced"+i);
	var text = document.getElementById("displayText");
	if (ele.style.display == "table-row") {
            ele.style.display = "none";
            text.innerHTML = on;
  	}
	else {
            ele.style.display = "table-row";
            text.innerHTML = off;
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