/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    getForms();
    getEdits();
    getSuperTable();
    $(".hide").click(function(){$(".singles").hide();});
});

function getForms(){
    $("#toggleSuperForm").click(function(event){
        $("#addSuper").toggle();
    });
    $("#heroSubmit").click(function(event){
        $("#addSuper").hide();
    });
    $("#togglePowersForm").click(function(event){
        $("#addPower").toggle();
    });
    $("#powerSubmit").click(function(event){
        $("#addPower").hide();
    });
    $("#toggleLocalForm").click(function(event){
        $("#addLoc").toggle();
    });
    $("#localSubmit").click(function(event){
        $("#addLoc").hide();
    });
    $("#toggleOrgForm").click(function(event){
        $("#addOrg").toggle();
    });
    $("#orgSubmit").click(function(event){
        $("#addOrg").hide();
    });
    $("#toggleSightForm").click(function(event){
        $("#addSight").toggle();
    });
    $("#sightSubmit").click(function(event){
        $("#addSight").hide();
    });
}

function getEdits(){
    $("#editSuper").click(function(event){
        $("#superEdit").show();
        
    });
    $("#heroEditSubmit").click(function(event){
        $("#superEdit").hide();
    });
}


function initMap(){
    map = new google.maps.Map(document.getElementById('map-canvas'), {
    center: {lat: 33.432102, lng: -111.737564},
    zoom: 5
    });
  console.log(s1lat);
  
  $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/api/locals',
        success: function(locArray) {
            for(x in locArray){
                console.log(locArray[x]);
                var markerGrandPlace = new google.maps.Marker({
                    position: new google.maps.LatLng(locArray[x][3], locArray[x][4]),
                    map: map,
                    title: locArray[x][1]
                  });
            }
        },
        error: function() {
            alert('FAILURE!');
        }
    })
 
}

function getSuperTable(){
    $(".displaySuper").click(function(e){
        let id = $(this).attr("data-id");
        $(".singles").hide();
        $("#"+id).toggle();
    });
}