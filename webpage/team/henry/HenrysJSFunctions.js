// JavaScript source code

$(document).ready(function () {
    $("#questionsListShow").click(function () {
        $("#questionsListDiv").animate({ height: 'show' }, 1000);
    });
    $("#questionsListHide").click(function () {
        $("#questionsListDiv").animate({ height: 'hide' }, 1000);
    });
    $("#questionsList2Show").click(function () {
        $("#questionsList2Div").animate({ height: 'show' }, 1000);
    });
    $("#questionsList2Hide").click(function () {
        $("#questionsList2Div").animate({ height: 'hide' }, 1000);
    });
    $("#questionsList3Show").click(function () {
        $("#questionsList3Div").animate({ height: 'show' }, 1000);
    });
    $("#questionsList3Hide").click(function () {
        $("#questionsList3Div").animate({ height: 'hide' }, 1000);
    });

    $("#descriptionShow").click(function () {
        $("#description").animate({ height: 'show' }, 1000);
    });
    $("#descriptionHide").click(function () {
        $("#description").animate({ height: 'hide' }, 1000);
    });

    $("#instructionsShow").click(function () {
        $("#instructionsDiv").animate({ height: 'show' }, 1000);
    });
    $("#instructionsHide").click(function () {
        $("#instructionsDiv").animate({ height: 'hide' }, 1000);
    });
});