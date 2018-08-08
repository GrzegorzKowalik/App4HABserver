
function initSend(){
    showLastActivityDetails();

    $("#commandsForm").submit(function(e) {
        console.log($("#commandsForm").serialize())
        $.ajax({
               type: "POST",
               url: '/app4hab/control/commands/',
               data: $("#commandsForm").serialize(),
               contentType: "text/plain",
               success: function(data)
               {
                   showLastActivityDetails()
               },
               error: function(jqXHR, exception)
               {
                   alert("Błąd: " + jqXHR.status);
               }
             });
        e.preventDefault();
    });
}

function showLastActivityDetails(){
    $("#CodeArea").text("...");

    $.ajax({
      url: "/app4hab/control/lastcommands/",
      success: function(result){
          console.log(result);
          $("#CodeArea").html(syntaxHighlight(result));
      }
    });
}

function selectAll(){
    $("#logging").prop("checked", true)
    $("#camera").prop("checked", true)
    $("#radio").prop("checked", true)
    $("#picture").prop("checked", true)
    $("#device").prop("checked", true)
}

