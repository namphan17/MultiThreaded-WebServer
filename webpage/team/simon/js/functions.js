
$(document).ready(function(){

$("#home").click(function(){
$("#subcontent").children().filter(":visible").fadeOut("slow", function(){
$("#welcome").fadeIn("slow");});
$("#asn0").fadeOut("slow");
$("#asn1").fadeOut("slow");
});
$("#asn0").click(function(){
$("#subcontent").children().filter(":visible").fadeOut("slow", function(){
$("#assignment").fadeIn("slow");});
$("#asn0").fadeOut("slow");
});
$("#spanningLink").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#spanning").fadeIn("slow");});
});
$("#randomLink").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#random").fadeIn("slow");});
});
$("#asn1").click(function(){
$("#subcontent").children().filter(":visible").fadeOut("slow", function(){
$("#assignment1").fadeIn("slow");});
$("#asn1").fadeOut("slow");
});
$("#asn2").click(function(){
$("#subcontent").children().filter(":visible").fadeOut("slow", function(){
$("#assignment2").fadeIn("slow");});
$("#asn1").fadeOut("slow");
});
$("#assignmentLink").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#assignment").fadeIn("slow");});
});
$("#assignmentLink1").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#assignment1").fadeIn("slow");});
});
$("#assignmentLink2").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#assignment2").fadeIn("slow");});
});
$("#notesLink").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#notes").fadeIn("slow");});
});
$("#oct10Link").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#oct10").fadeIn("slow");});
});
$("#oct11Link").click(function(){
$("#welcome").fadeOut("slow", function(){
$("#oct11").fadeIn("slow");});
});
$("#synchLink").click(function(){
$("#assignment").fadeOut("slow", function(){
$("#synch").fadeIn("slow");});
$("#asn0").fadeIn("slow");
});
$("#daviesLink").click(function(){
$("#assignment").fadeOut("slow", function(){
$("#davies").fadeIn("slow");});
$("#asn0").fadeIn("slow");
});
$("#tutorialLink").click(function(){
$("#assignment").fadeOut("slow", function(){
$("#tutorial").fadeIn("slow");});
$("#asn0").fadeIn("slow");
});
$("#wsLabLink").click(function(){
$("#assignment1").fadeOut("slow", function(){
$("#wsLab").fadeIn("slow");});
$("#asn1").fadeIn("slow");
});
$("#myQALink").click(function(){
$("#assignment1").fadeOut("slow", function(){
$("#myQA").fadeIn("slow");});
$("#asn1").fadeIn("slow");
});
$("#textQALink").click(function(){
$("#assignment1").fadeOut("slow", function(){
$("#textQA").fadeIn("slow");});
$("#asn1").fadeIn("slow");
});
$("#dijkstraLink").click(function(){
$("#assignment2").fadeOut("slow", function(){
$("#dijkstra").fadeIn("slow");});
$("#asn2").fadeIn("slow");
});
});


  function openMenu() {
    document.getElementById("menu").classList.toggle("show");
  }

  function openMenu1() {
    document.getElementById("menu1").classList.toggle("show");
  }

  function openMenu2() {
    document.getElementById("menu2").classList.toggle("show");
  }
  function openMenu3() {
    document.getElementById("menu3").classList.toggle("show");
  }
  
 
  //Closes menu when clicked outside
  window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

  if (document.getElementById("menu").classList.contains("show")) {
    document.getElementById("menu").classList.toggle("show");
    }
  if (document.getElementById("menu1").classList.contains("show")) {
    document.getElementById("menu1").classList.toggle("show");
    }
  if (document.getElementById("menu2").classList.contains("show")) {
    document.getElementById("menu2").classList.toggle("show");
    }
	if (document.getElementById("menu3").classList.contains("show")) {
    document.getElementById("menu3").classList.toggle("show");
    }
  }
}

       
      function Answer(text){
        var finalAns = trimEq(text);
	if ( typeof Answer.question == 'undefined' ) {
	        Answer.question = 0;
		Answer.right = 0;
		document.getElementById("ans0").innerHTML = "Assume that upon sending or receiving a message, each router takes a timestamp. The first router's clock will be labeled A, and the second router's will be labeled B. The first time measured by a router will be labeled by a 1, the second time a 2, and so on. Our challenge is to synchronize the clocks at both routers, so that at any given time, A = B. A router sends a message at A1. What time, according to A, will<br> the message be received at B?";
      document.getElementById("input").value = "";

	      }


      if (text != "") {
      Answer.question++;
      }

      switch (Answer.question) {

       case 1:
      document.getElementById("ans1").innerHTML = "The answer is A1 + D1.<br><br>Let's assume that we can measure this time with 100% accuracy. Note that if you know the difference, or offset, between the clocks, you can synchronize with little effort. What is the difference between B and A?";
      document.getElementById("input").value = "";
       if (finalAns === "a1+d1"){
       document.getElementById("res").innerHTML = "Correct!";
       Answer.right++;
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect.";
      }
      break;

       case 2:
      document.getElementById("ans2").innerHTML = "The answer is B1 - (A1 + D1).<br><br>Unfortunately, we can only measure D1 on the clock at A. When B receives the message, it sees the timestamp (A1), but it doesn't know what D1 is, so the difference can't be calculated. Now, assume that a message is sent from A to B, is held for some time, and then sent back. What formula would you use to get the total time delay (D1 + D2)?";
      document.getElementById("input").value = "";
       if (finalAns === "-a1+b1-d1"){
      document.getElementById("res").innerHTML = "Correct!";
       Answer.right++;
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect.";
      }
      break;

       case 3:
      document.getElementById("ans3").innerHTML = "The answer is A2 - A1 - (B2 - B1).<br><br>Using the answers to the previous two questions, can you estimate the difference between the clocks? Use only As and Bs in your answer.";
      document.getElementById("input").value = "";
       if (finalAns === "-a1+a2+b1-b2"){
      document.getElementById("res").innerHTML = "Correct!";
       Answer.right++;
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect.";
      }
      break;
       case 4:
      document.getElementById("ans4").innerHTML = "The answer is (B1 + B2 - (A1 + A2))/2.<br><br>This method is actually used by GPS synch protocol and NTP (Network Time Protocol) to synchronize clocks. The fact that one way delay cannot be measured makes perfect synchronization impossible. The accuracy of the previous formula depends on the assumption that the total delay (D1 + D2), divided by two, is just about equal to the first delay, D1. Let's say a message takes 1 second to reach a distant router, and then 3 seconds to return. What is the difference between the estimated one way delay and the true one way delay?";
      document.getElementById("input").value = "";
       if (finalAns === "-a1-a2+b1+b2/2"){
      document.getElementById("res").innerHTML = "Correct!";
       Answer.right++;
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect.";
      }
      break;

       case 5:
      document.getElementById("ans5").innerHTML = "The answer is 1 second.<br><br>Assume you have an extremely stable, but slow, connection. P1 and Q1 add to 9 ms, P2 and Q2 add to 11 ms, and the propagation delay is negligible. T1 = 30 sec, and T2 = 31 sec. What is the difference between the estimated one way delay and the true one way delay in ms?";
      document.getElementById("input").value = "";
      if (text === "1"){
      document.getElementById("res").innerHTML = "Correct!";
       Answer.right++;
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect.";
      }
      break;

       case 6:
      document.getElementById("ans6").innerHTML = "The answer is 501 ms.<br><br>Now your connection is blazing fast, but your router is ancient and can barely handle the strain of a single user. P1 and Q1 add to 2 sec, P2 and Q2 add to 10 ms, T1 = 150 ms, and T2 = 200 ms. What is the difference between the estimated one way delay and the true one way delay in ms?";
      document.getElementById("input").value = "";
       if (text === "501"){
      document.getElementById("res").innerHTML = "Correct!";
       Answer.right++;
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect.";
      }
      break;

       case 7:
      document.getElementById("ans7").innerHTML = "The answer is 920 ms.<br><br>This should show that the biggest differences between components of the delay are the terms that dominate, creating a bottleneck for your <br>synchronization accuracy. If your transmission delay is low, but your queueing delay is high, Q will impact synch accuracy much more than T. To achieve accurate synchronization, a network must have extremely fast, stable connections between all links, and will often take a large number of measurements to reduce uncertainty further. Along a fast, stable connection, P1 and Q1 add to 5 ms, P2 and Q2 add to 6 ms, T1 = 2 ms and T2 = 3 ms. What is the difference between the estimated one way delay and the true one way delay in ms?";
      document.getElementById("input").value = "";
       if (text === "920"){
      document.getElementById("res").innerHTML = "Correct!";
       Answer.right++;
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect.";
      }
      break;

      case 8:
      document.getElementById("ans8").innerHTML = "The answer is 1 ms."
      document.getElementById("input").value = "";
       if (text === "1"){
       Answer.right++;
      document.getElementById("res").innerHTML = "Correct! You scored " + Answer.right + "/8.";
      }
      else {
      document.getElementById("res").innerHTML = "Incorrect. You scored " + Answer.right + "/8.";
      }
		      break;
      }
      }

      function trimEq(text){
      var newtext = text.replace(/ /g, "").toLowerCase();
      var isPar = newtext.indexOf("(");
      var chars = ["a1", "a2", "b1", "b2", "d1", "d2"];
      if (isPar != -1){
        var newPar = "";
        var inPar = newtext.substring(isPar + 1, newtext.indexOf(")"));
        if (newtext.charAt(isPar-1) == "-") {
          var i;
        
          for (i in chars){
            if (inPar.indexOf(chars[i]) == 0){
              newPar = newPar + "-" + chars[i];
            }
            else if (inPar.charAt(inPar.indexOf(chars[i])-1) == "-"){
              newPar = newPar + "+" + chars[i];
            }
            else if (inPar.charAt(inPar.indexOf(chars[i])-1) == "+"){
              newPar = newPar + "-" + chars[i];
            }
          }
        }
        else {
          newPar = newtext.substring(0,isPar) + inPar + newtext.substring(newtext.indexOf(")")+1);

        }
      newtext = newtext.substring(0, isPar-1) + newPar + newtext.substring(newtext.indexOf(")")+1);
      }
    
    
    if (newtext.charAt(0) != "+" && newtext.charAt(0) != "-"){
        newtext = "+" + newtext;
      }
      var fAnswer = "";
      for (var i = 0; i < chars.length; i++){
        if (newtext.indexOf(chars[i]) != -1){
        fAnswer = fAnswer + newtext.charAt(newtext.indexOf(chars[i]) - 1) + chars[i];
      }
      }
      if (fAnswer.charAt(0) == "+"){
        fAnswer = fAnswer.substring(1);
      }
      return fAnswer;
    }
