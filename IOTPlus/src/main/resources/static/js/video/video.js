/**
 * Created by JSexy on 2016/3/2.
 */
var clicked = "Nope.";
var mausx = "0";
var mausy = "0";
var winx = "0";
var winy = "0";
var difx = mausx - winx;
var dify = mausy - winy;

$("html").mousemove(function (event) {
    mausx = event.pageX;
    mausy = event.pageY;
    winx = $("#videoWindow").offset().left;
    winy = $("#videoWindow").offset().top;
    if (clicked == "Nope.") {
        difx = mausx - winx;
        dify = mausy - winy;
    }
    var newx = event.pageX - difx - $("#videoWindow").css("marginLeft").replace('px', '');
    var newy = event.pageY - dify - $("#videoWindow").css("marginTop").replace('px', '');
    $("#videoWindow").css({ top: newy, left: newx });

});

$(".draggin").mousedown(function (event) {
    clicked = "Yeah.";
});

$("html").mouseup(function (event) {
    clicked = "Nope.";
});