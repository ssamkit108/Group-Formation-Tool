/**
 * 
 */

function loadUser(value) {
	alert("here");
	var value = document.getElementById('searchTA').value;
	alert(value);
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            //document.getElementById("demo").innerHTML =
            //this.responseText;
       }
    };
    alert("here");
    xhttp.open("GET", "user/finduser/"+value, true);
    xhttp.send();
}


var coll = document.getElementsByClassName("collapsible");
alert("here");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
	  alert(coll[i]);
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
    } else {
      content.style.display = "block";
    }
  });
}
