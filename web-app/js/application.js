function showHideSpinner() {
    var divstyle = new String();
    divstyle = document.getElementById("spinner").style.visibility;
    if(divstyle.toLowerCase()=="visible" || divstyle == "")
    {
        document.getElementById("spinner").style.visibility = "hidden";
    }
    else
    {
        document.getElementById("spinner").style.visibility = "visible";
    }
}

function toggle() {
	var ele = document.getElementById("advanced");
	var text = document.getElementById("displayText");
	if(ele.style.display == "table-row") {
    		ele.style.display = "none";
		text.innerHTML = "Show Advanced";
  	}
	else {
		ele.style.display = "table-row";
		text.innerHTML = "Hide Advanced";
	}
}
