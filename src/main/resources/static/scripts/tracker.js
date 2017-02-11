$(function () {
    $(".button-collapse").sideNav();
    $('.modal').modal();
    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15 // Creates a dropdown of 15 years to control year
    });
    $("#addButton").click(function() {
        var $addForm = $("#add");
        $addForm.toggle();
    });
});