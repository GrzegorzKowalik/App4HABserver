function sendTestAjax() {
    console.log("SendTestAjax");
    
    $.ajax({
      url: "/app4hab/api/test",
      success: function(result){
          console.log(result);
      }
    });
}


function initPage(){
    console.log("initPage");
    clickTabActivities();
    updateLastActivity();
}

function update(){
    updateLastActivity();
}


function updateLastActivity(){
    $.ajax({
        url: "/app4hab/api/control/lastactivity",
        success: function(result){
          
            var date = unixToReadableDate(result["timestamp"]);
            var ago = calculateTimeAgo(result["timestamp"]);
            if($('#LastActivityTime').text() != date){
                loadActivities();
                $('#LastActivityTime').text(date);
            }
            $('#LastActivityAgo').text(ago);
        }
    });
}


function unixToReadableDate(unixTime){
    var date = new Date(unixTime);

    var d = date.toLocaleDateString();
    var t = date.toLocaleTimeString();
    return d + " " + t;
} 

function calculateTimeAgo(unixTime){
    var date = new Date(Date.now() - unixTime);
    
    var days = 0;
    var res = "";

    days += (date.getFullYear() - 1970) * 365;
    days += date.getMonth() * 30;
    days += date.getDate() - 1;
            
    var hours = date.getHours() - 1;
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    
    if (days != 0) res += days + "d "
    if (hours != 0) res += hours + "h "
    if (minutes != 0) res += minutes + "m "
    if (seconds != 0) res += seconds + "s "

    return res;
}

function clickTabActivities(){
    loadActivities();
    
    $("#TabActivities").addClass("active");
    $("#TabSensors").removeClass("active");
    $("#TabPhotos").removeClass("active");
    
}

function clickTabSensors(){
    loadSensors();
    
    $("#TabActivities").removeClass("active");
    $("#TabSensors").addClass("active");
    $("#TabPhotos").removeClass("active");
    
}

function clickTabPhotos(){
    loadPhotos();
    
    $("#TabActivities").removeClass("active");
    $("#TabSensors").removeClass("active");
    $("#TabPhotos").addClass("active");
    
}

function loadActivities(){

    clearLeftPane();
    $.ajax({
      url: "/app4hab/api/control/allactivities",
      success: function(result){
          jQuery.each(result, function(i, item){
              $("#TabTable").find('tbody')
                .append($('<tr>')
                    .addClass("text-info")
                    .append($('<td>').text(item["id"]))
                    .append($('<td>').text(unixToReadableDate(item["timestamp"])))
                    .append($('<td>').text(item["endpoint"]))
                    .click(function(){
                        showActivityDetails(item["id"]);
                        $("html, body").animate({ scrollTop: 0 }, "slow");
                    })
                )
          })
      }
    });
    
    $("#TabTable").find('thead')
        .append($('<tr>')
            .append($('<th>').text("ID"))
            .append($('<th>').text("Time"))
            .append($('<th>').text("Endpoint"))
        )
}

function showActivityDetails(id){
    $("#SelectionHeadline").text("Activity: " + id);
    $("#CodeArea").text("...");
    
    $.ajax({
      url: "/app4hab/api/control/activity/" + id,
      success: function(result){
          console.log(result);
          $("#CodeArea").html(syntaxHighlight(result));
      }
    });
}

function loadSensors(){
    clearLeftPane();

    $.ajax({
        url: "/app4hab/api/control/statuses",
        success: function(result){
            jQuery.each(result, function(i, item){
                $("#TabTable").find('tbody')
                    .append($('<tr>')
                        .addClass("text-info")
                        .append($('<td>').text(item["id"]))
                        //.append($('<td>').text(unixToReadableDate(item["timestamp"])))
                        .append($('<td>').text(item["timestamp"]))
                        .click(function(){
                            showSensorsDetails(item["id"]);
                            $("html, body").animate({ scrollTop: 0 }, "slow");
                        })
                    )
            })
        }
    });


    $("#TabTable").find('thead')
        .append($('<tr>')
            .append($('<th>').text("ID"))
            .append($('<th>').text("Time"))
            //.append($('<th>').text("Altitude"))
        )
}

function showSensorsDetails(id){
    $("#SelectionHeadline").text("Status: " + id);
    $("#CodeArea").text("...");

    $.ajax({
        url: "/app4hab/api/control/status/" + id,
        success: function(result){
            console.log(result);
            $("#CodeArea").html(syntaxHighlight(result));
        }
    });
}

function loadPhotos(){
    clearLeftPane();


    $.ajax({
        url: "/app4hab/api/control/photos",
        success: function(result){
            jQuery.each(result, function(i, item){
                $("#TabTable").find('tbody')
                    .append($('<tr>')
                        .addClass("text-info")
                        .append($('<td>').text(item["id"]))
                        .append($('<td>').text(item["timestamp"]))
                        .append($('<td>').text(item["altitude"]))
                        .click(function(){
                            showPhotosDetails(item["id"]);
                            $("html, body").animate({ scrollTop: 0 }, "slow");
                        })
                    )
            })
        }
    });



    $("#TabTable").find('thead')
        .append($('<tr>')
            .append($('<th>').text("ID"))
            .append($('<th>').text("Time"))
            .append($('<th>').text("Altitude"))
        )
}

function showPhotosDetails(id){
    $("#SelectionHeadline").text("Photo: " + id);
    $("#CodeArea").text("...");

    $.ajax({
        url: "/app4hab/api/control/photo/" + id,
        success: function(result){
            console.log(result);
            $("#CodeArea").html(syntaxHighlight(result));
        }
    });
}

function clearLeftPane(){
    $("#TabTable").find('thead').empty();
    $("#TabTable").find('tbody').empty();
}




//Stolen from https://stackoverflow.com/questions/4810841/how-can-i-pretty-print-json-using-javascript
function syntaxHighlight(json) {
    if (typeof json != 'string') {
         json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}