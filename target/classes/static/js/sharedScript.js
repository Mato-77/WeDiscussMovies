var urlRating;

$(document).ready(function (){
    var elements = $(".elements")
    var elementGrade;


    $("#dialog-rating").dialog({
        autoOpen: false,
        modal: true,
        buttons: [
            {
                text: "Потврди",
                click: function () {
                    $("#dialog-rating").dialog("close")
                    ajaxCallRating(urlRating,elementGrade)
                }
            },
            {
                text: "Откажи",
                click: function () {
                    $("#dialog-rating").dialog("close")
                }
            }
        ]
    });
    $(".button-add-grade-movie").on("click",function (){
        elementGrade = $(this)
        urlRating ="api/movies/grade/"+$(this).attr("movie-id")
        $("#dialog-rating").dialog("open")
    })
    $(".button-add-grade-person").on("click",function (){
        elementGrade = $(this)
        urlRating ="api/persons/grade/"+$(this).attr("person-id")
        $("#dialog-rating").dialog("open")
    })


    $(".search-button-title").on("click",function (){
        let filter = $("#searchTitle").val()
        console.log(elements)
        for (let item of elements){
            let title =  $(item).find(".card-title").text()
            if (title.toLowerCase() === filter.toLowerCase()){
                console.log("Da")
                $(item).css("display","block")
            }
            else{
                $(item).css("display","none")
            }
        }

    })


   $(".search-button").on("click",function () {
        let filter = $("#searchGenre").val()
       console.log(elements)

       for (let item of elements) {
            let genre = $(item).find(".card-genre")
            let visible = false;

            for (let g of genre) {

                if ($(g).text().toLowerCase() === filter.toLowerCase()) {
                    visible = true
                    $(item).css("display","block")
                    break;
                }
            }
            if (!visible)
                $(item).css("display","none")
        }
    });

    $(".button-delete-movie").on("click",function (){
        let button = $(this)
        let url = "api/movies/delete/" + $(button).attr("movie-id")
        ajaxCallDelete(url,button)
    })
    $(".button-delete-actor").on("click",function (){
        let button = $(this)
        let url  = "api/persons/delete/" + $(button).attr("person-id")
        ajaxCallDelete(url,button)
    })
    $(".button-delete-discussion").on("click",function (){
        let button = $(this)
        let url = "api/discussions/delete/" + $(button).attr("discussion-id")
        ajaxCallDelete(url,button)
    })

    $(document.body).on("click",".button-confirm",function (){

        $(this).parent().parent().fadeOut(2000)
    })
    $(".person-movies").change(function (){
        if (this.value === "A"){
            $(".movie-directors").attr("hidden",true).prop("selected",false)


            $(".movies-actors").attr("hidden",false)

        }
        else{
            $(".movies-actors").attr("hidden",true).prop("selected",false)
            $(".movie-directors").attr("hidden",false)
        }
    })
    $(document.body).on("click",".button-add-favourite-list",function (){
        let button = $(this)
        let url = "api/movies/like/"+ $(this).attr("movie-id") + "?userId="+ $(this).attr("user-id")
        ajaxCallLike(url,button,'like','Веќе е филмот допаднат!')
    })
    $(document.body).on("click",".button-remove-favourite-list",function (){
        let button = $(this)
        let url = "api/movies/unlike/"+ $(this).attr("movie-id")+"?userId="+ $(this).attr("user-id")
        ajaxCallLike(url,button,'unlike','Немате оставено допаѓање на филмот!')
    })
    $(".discussion-type").change(function (){
        if (this.value === "M"){
            $(".persons-discussion").hide()
            $(".movies-discussion").show()


        }
        else{
            $(".movies-discussion").hide()
            $(".persons-discussion").show()
        }
    })


})
function ajaxCallLike(url,button,type,message){
    $.ajax({
        url:url,
        success:function (data){
            if (data){
                let el = $(button).parent().siblings().eq(3)
                console.log(el)
                if (type=="like") {
                    $(el).html(parseInt($(el).text()) + 1)
                    console.log("da")
                }
                else
                    $(el).html(parseInt($(el).text()) - 1)
                $(button).css("display","none")
                let userId = $(button).attr("user-id")
                let movieId=$(button).attr("movie-id")
                if (type==='like') {
                    $(button).parent().append("<a class='bottom-heart btn btn-danger button-remove-favourite-list' movie-id=" + movieId + " user-id=" + userId + ">💔</a>")
                    console.log("da")
                }
                else{
                    $(button).parent().append("<a class='bottom-heart btn btn-success button-add-favourite-list' movie-id=" + movieId + " user-id=" + userId + ">❤</a>")

                }
            }
            else {
                $(button).parent().append("<div>" + message +" <button class='button-confirm'>Ок</button></div>")
            }
        }
    })
}
function ajaxCallDelete(url,button){
    $.ajax({
        url:url,
        method:"DELETE",
        success: function (data){
            if (data){
                console.log(data)
                $(button).parent().parent().html("<div>Бришењето е успешно!<button class='button-confirm'>Ок</button></div>")
            }
            else {
                var div = "<div>Веќе е избришан записот! <button class='button-confirm'>Ок</button></div>"
                button.parent().html(button.parent().html() + div)
            }
        }
    })
}
function  ajaxCallRating(url,button,type){
    model = {
        rating:$("#grade").val(),
        reason:$("#reason").val()
    }
    $.ajax({
        url:urlRating,
        method: "POST",
        dataType:"json",
        data:JSON.stringify(model),
        contentType : 'application/json; charset=utf-8',
        success: function (data){
            if (data){
                console.log(data)
                $(button).text("Промени оцена и мислење")
            }

        }
    })
}
