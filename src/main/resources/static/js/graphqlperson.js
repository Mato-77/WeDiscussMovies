
var filters = {}
var user
$(document).ready(function (){
    user = $("#user-info").attr("user")
    $("#discussions").toggle()
    $("#likes").toggle()
    $("#rates").toggle()
    $("#replies").toggle()
    $("#reply-user").toggle()


    $("input[type=checkbox]").change(function (){
        filters[$(this).val()] = this.checked
    })
    $("#replyUser").change(function () {
        $("#reply-user").toggle()
        filters['replyUser'] = this.checked
    })
    $("#personActor").change(function () {
        $("#actors").toggle()
        filters['movies'] = this.checked
    })
    $("#personDirector").change(function () {
        $("#director").toggle()
        filters['movies'] = this.checked

    })
    $("#personDiscussions").change(function () {
        $("#discussions").toggle()
        filters['discussions'] = this.checked

    })
    $("#personRates").change(function () {
        $("#rates").toggle()
        filters['rates'] = this.checked
    })


})