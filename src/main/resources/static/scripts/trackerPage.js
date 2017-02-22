$(function () {
    setInterval(function () {
       $.get(window.location.pathname + "/time", function (data) {
           $("#dateString").html(data.diff);
       });
    },60000);
});