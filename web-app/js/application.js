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