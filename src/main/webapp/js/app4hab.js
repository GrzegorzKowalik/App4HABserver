function initPage(){
    console.log("initPage");

    $("#TabActivities").addClass("active");
    $("#TabSensors").removeClass("active");
    $("#TabPhotos").removeClass("active");
    $("#TabLogs").removeClass("active");
}

function update(){
    updateLastActivity();
}


function updateLastActivity(){
    $.ajax({
        url: "/app4hab/control/lastactivity",
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
    $("#TabActivities").addClass("active");
    $("#TabSensors").removeClass("active");
    $("#TabPhotos").removeClass("active");
    $("#TabLogs").removeClass("active");

    loadActivities();
}

function clickTabSensors(){
    loadSensors();

    $("#TabActivities").removeClass("active");
    $("#TabSensors").addClass("active");
    $("#TabPhotos").removeClass("active");
    $("#TabLogs").removeClass("active");
}

function clickTabPhotos(){
    loadPhotos();
    
    $("#TabActivities").removeClass("active");
    $("#TabSensors").removeClass("active");
    $("#TabPhotos").addClass("active");
    $("#TabLogs").removeClass("active");
}

function clickTabLogs(){
    loadLogs();

    $("#TabActivities").removeClass("active");
    $("#TabSensors").removeClass("active");
    $("#TabPhotos").removeClass("active");
    $("#TabLogs").addClass("active");
}

function loadActivities(){
    if($("#TabActivities").hasClass("active")){
        clearLeftPane();
        $.ajax({
          url: "/app4hab/control/allactivities",
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
}

function showActivityDetails(id){
    $("#SelectionHeadline").text("Activity: " + id);
    $("#CodeArea").text("...");
    
    $.ajax({
      url: "/app4hab/control/activity/" + id,
      success: function(result){
          console.log(result);
          $("#CodeArea").html(syntaxHighlight(result));
          $("#utils").empty();

      }
    });
}

function loadSensors(){
    clearLeftPane();

    $.ajax({
        url: "/app4hab/control/statuses",
        success: function(result){
            jQuery.each(result, function(i, item){
                $("#TabTable").find('tbody')
                    .append($('<tr>')
                        .addClass("text-info")
                        .append($('<td>').text(item["id"]))
                        .append($('<td>').text(unixToReadableDate(item["timestamp"])))
                        .append($('<td>').text(item["alt"]))
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
            .append($('<th>').text("Altitude"))
        )
}

function showSensorsDetails(id){
    $("#SelectionHeadline").text("Status: " + id);
    $("#CodeArea").text("...");

    $.ajax({
        url: "/app4hab/control/status/" + id,
        success: function(result){
            console.log(result);
            $("#CodeArea").html(syntaxHighlight(result));
            $("#utils").empty();

            if(result["lon"] && result["lat"]){
                $("#utils").html("Latlong: " + result["lat"] + ", " + result["lon"] + "</br></br>")
                map = new OpenLayers.Map("utils");
                map.addLayer(new OpenLayers.Layer.OSM());

                var lonLat = new OpenLayers.LonLat( result["lon"] ,result["lat"] )
                      .transform(
                        new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
                        map.getProjectionObject() // to Spherical Mercator Projection
                      );

                var zoom=18;

                var markers = new OpenLayers.Layer.Markers( "Markers" );
                map.addLayer(markers);
                marker = new OpenLayers.Marker(lonLat)

                var size = new OpenLayers.Size(35, 35);
                var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
                marker.icon = new OpenLayers.Icon("img/mapmarker.png", size, offset);
                marker.icon.size = size;
                marker.icon.offset = offset;

                markers.addMarker(marker);

                map.setCenter (lonLat, zoom);
            }
        }
    });
}

function loadPhotos(){
    clearLeftPane();


    $.ajax({
        url: "/app4hab/control/photos",
        success: function(result){
            jQuery.each(result, function(i, item){
                $("#TabTable").find('tbody')
                    .append($('<tr>')
                        .addClass("text-info")
                        .append($('<td>').text(item["id"]))
                        .append($('<td>').text(unixToReadableDate(item["timestamp"])))
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
        url: "/app4hab/control/photo/" + id,
        success: function(result){
            console.log(result);
            $("#CodeArea").html(syntaxHighlight(result));
            $("#utils").empty();

            $("#utils").html('<img src="img/' + result["img"] + '"></img> ')
        }
    });
}

function loadLogs(){
   clearLeftPane();
   $.ajax({
     url: "/app4hab/control/logs",
     success: function(result){
         jQuery.each(result, function(i, item){
             $("#TabTable").find('tbody')
               .append($('<tr>')
                   .addClass("text-info")
                   .append($('<td>').text(item["id"]))
                   .append($('<td>').text(unixToReadableDate(item["timestamp"])))
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
       )
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