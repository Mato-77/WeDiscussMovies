var urlRating;

$(document).ready(function (){
    var elements = $(".elements")
    var elementGrade;
    var first_time = true;

    $("#button-add").on("click", function (){
        itemInput = $("#pageInput")
        itemInput.val(Number(itemInput.val()) + 1);
        $("#button-submit-page").click()
    })

    $("#button-sub").on("click", function (){
        itemInput = $("#pageInput")
        if(Number(itemInput.val()) > 1){
            itemInput.val(Number(itemInput.val()) - 1);
            $("#button-submit-page").click()
        }
    })

    $("#searchTitle").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        console.log(value)
        //$(this).text().toLowerCase().indexOf(value) <= -1
        $(".elements div a .title").filter(function() {
            if($(this).text().toLowerCase().includes(value) || $(this).text().toLowerCase() === value)
                $(this).parent().parent().parent().removeClass("visibility")
            else
                $(this).parent().parent().parent().addClass("visibility")
        });
    });




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
        urlRating ="/api/movies/grade/"+$(this).attr("movie-id")
        $("#dialog-rating").dialog("open")
    })
    $(".button-add-grade-person").on("click",function (){
        elementGrade = $(this)
        urlRating ="/api/persons/grade/"+$(this).attr("person-id")
        $("#dialog-rating").dialog("open")
    })


    // $(".search-button-title").on("click",function (){
    //     let filter = $("#searchTitle").val()
    //     console.log(elements)
    //     for (let item of elements){
    //         let title =  $(item).find(".card-title").text()
    //         if (title.toLowerCase() === filter.toLowerCase()){
    //             console.log("Da")
    //             $(item).css("display","block")
    //         }
    //         else{
    //             $(item).css("display","none")
    //         }
    //     }
    // })

    $("#button_toggle_filters").on("click", function (){
        if(first_time){
            $("#filters_div").removeClass("invisible-search")
            first_time=false
        }
        else
            $("#filters_div").fadeToggle();
    })

    $(".user-movies-list").on("click", function (){
       let children = $(this).children()
        let first = true
        for (let item of children){
            if(first){
                first = !first
            }
            else{
                $(item).fadeToggle();
            }
        }
        $(this).toggleClass("hidden-class")
    })

   $(".search-button").on("click",function () {
       elements = $(".elements")
       let filter = $("#searchGenre").val()
       console.log('\''+filter+'\'')
       console.log(elements)
       if(filter === ''){
           console.log("HERE")
           for (let item of elements) {
               $(item).removeClass("visibility")
           }
           console.log(elements)
       }
       else {
           for (let item of elements) {
               $(item).removeClass("visibility")
               let genre = $(item).find("span")
               let visible = false;
             console.log(genre)
               for (let g of genre) {
                   console.log(g)
                   if (($(g).text().toLowerCase() === filter.toLowerCase())) {
                     //  console.log(item.text + ' ' + $(g).text())
                       visible = true
                       $(item).removeClass("visibility")
                       break;
                   }
               }
               if (!visible) {
                   $(item).addClass("visibility")
               }

           }
       }
    });

    $(".button-delete-movie").on("click",function (){
        let button = $(this)
        let url = "/api/movies/delete/" + $(button).attr("movie-id")
        ajaxCallDelete(url,button)
    })
    $(".button-delete-actor").on("click",function (){
        let button = $(this)
        let url  = "/api/persons/delete/" + $(button).attr("person-id")
        ajaxCallDelete(url,button)
    })
    $(".button-delete-discussion").on("click",function (){
        let button = $(this)
        let url = "/api/discussions/delete/" + $(button).attr("discussion-id")
        ajaxCallDelete(url,button)
    })
    $(".button-delete-reply").on("click",function (){
        let button = $(this)
        let url = "/api/replies/delete/" + $(this).attr("discussion-id")
        let replyId = $(this).attr("reply-id")
        $.ajax({
            url:url,
            method:"post",
            data:{
                replyId:replyId
            },
            success: function (data){
                if (data){
                    $(button).parent().parent().fadeOut(1500)
                }
            }

        })

    })

    $(document.body).on("click",".button-confirm",function (){

        $(this).parent().parent().fadeOut(2000)
    })
    $(".person-movies").change(function (){
        if (this.value === "A"){
            $(".movie-directors").attr("hidden",true)


            $(".movies-actors").attr("hidden",false)

        }
        else{
            $(".movie-directors").attr("hidden",false)
            $(".movies-actors").attr("hidden",true)

        }
    })
    $(document.body).on("click",".button-add-favourite-list",function (){
        let button = $(this)
        let url = "/api/movies/like/"+ $(this).attr("movie-id") + "?userId="+ $(this).attr("user-id")
        ajaxCallLike(url,button,'like','Веќе е филмот допаднат!')
    })
    $(document.body).on("click",".button-remove-favourite-list",function (){
        let button = $(this)
        let url = "/api/movies/unlike/"+ $(this).attr("movie-id")+"?userId="+ $(this).attr("user-id")
        ajaxCallLike(url,button,'unlike','Немате оставено допаѓање на филмот!')
    })
    $(document.body).on("click",".button-add-genre-liked-list",function (){
        let button = $(this)
        let url = "/api/genres/like/"+ $(this).attr("genre-id") + "?userId="+ $(this).attr("user-id")
        ajaxCallLikeGenre(url,button,'like','Веќе ви се допаѓа жанрот!')
    })
    $(document.body).on("click",".button-remove-genre-liked-list",function (){
        let button = $(this)
        let url = "/api/genres/unlike/"+ $(this).attr("genre-id")+"?userId="+ $(this).attr("user-id")
        ajaxCallLikeGenre(url,button,'unlike','Немате оставено допаѓање на жанрот!')
    })
    $(document.body).on("click",".button-like-discussion",function (){
        let button = $(this)
        let url = "/api/discussions/like/"+ $(this).attr("discussion-id")+"?userId="+ $(this).attr("user-id")
        ajaxCallLikeDiscussion(url,button,'like','Веќе имате оставено допаѓање на дискусијата!')
    })
    $(document.body).on("click",".button-unlike-discussion",function (){
        let button = $(this)
        let url = "/api/discussions/unlike/"+ $(this).attr("discussion-id")+"?userId="+ $(this).attr("user-id")
        console.log(url)
        ajaxCallLikeDiscussion(url,button,'unlike','Немате оставено допаѓање на дискусијата!')
    })
    $(document.body).on("click",".button-like-discussion-alt",function (){
        let button = $(this)
        let url = "/api/discussions/like/"+ $(this).attr("discussion-id")+"?userId="+ $(this).attr("user-id")
        ajaxCallLikeDiscussionAlternative(url,button,'like','Веќе имате оставено допаѓање на дискусијата!')
    })
    $(document.body).on("click",".button-unlike-discussion-alt",function (){
        let button = $(this)
        let url = "/api/discussions/unlike/"+ $(this).attr("discussion-id")+"?userId="+ $(this).attr("user-id")
        ajaxCallLikeDiscussionAlternative(url,button,'unlike','Немате оставено допаѓање на дискусијата!')
    })
    $(document.body).on("click",".button-like-reply",function (){
        let button = $(this)
        let url = "/api/replies/like/"+ $(this).attr("reply-id")+"?userId="+ $(this).attr("user-id")+"&discussionId="+$(this).attr("discussionId")
        console.log(url)
        ajaxCallLikeReply(url,button,'like','Немате оставено допаѓање на реплика!')
    })
    $(document.body).on("click",".button-unlike-reply",function (){
        let button = $(this)
        let url = "/api/replies/unlike/"+ $(this).attr("reply-id")+"?userId="+ $(this).attr("user-id")+"&discussionId="+$(this).attr("discussionId")
        ajaxCallLikeReply(url,button,'unlike','Немате оставено допаѓање на реплика!')
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
                    $(button).parent().append("<a class='btn btn-danger button-remove-favourite-list' movie-id=" + movieId + " user-id=" + userId + ">💔</a>")
                    console.log("da")
                }
                else{
                    $(button).parent().append("<a class='btn btn-success button-add-favourite-list' movie-id=" + movieId + " user-id=" + userId + ">❤</a>")
                }
                likes_count = $("#movie_likes_count")
                count = Number($(likes_count).text())
                if(type==="like")
                    count++
                else
                    count--
                likes_count.text(count)
                $(button).remove()
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



function ajaxCallLikeGenre(url,button,type,message){
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
                let genreId=$(button).attr("genre-id")
                if (type==='like') {
                    $(button).parent().append("<a class='btn btn-danger button-remove-genre-liked-list' genre-id=" + genreId + " user-id=" + userId + ">💔</a>")
                    console.log("da")
                }
                else{
                    $(button).parent().append("<a class='btn btn-success button-add-genre-liked-list' genre-id=" + genreId + " user-id=" + userId + ">❤</a>")
                }
                let likes_sibling = $("#"+genreId+"genre")
                value_likes = Number(likes_sibling.text())
                if(type=="like")
                    value_likes+=1
                else
                    value_likes-=1
                likes_sibling.text(value_likes)
                $(button).remove()
            }
            else {
                $(button).parent().append("<div>" + message +" <button class='button-confirm'>Ок</button></div>")
            }
        }
    })
}


function ajaxCallLikeReply(url,button,type,message){
    $.ajax({
        url:url,
        success:function (data){
            if (data){
                console.log(data)
                let el = $(button).parent().siblings().eq(3)
                console.log(el)
                if (type=="like") {
                    $(el).html(parseInt($(el).text()) + 1)
                    console.log("da")
                }
                else
                    $(el).html(parseInt($(el).text()) - 1)
                $(button).css("display","none")
                $(button).siblings("a").css("display","block")
                let userId = $(button).attr("user-id")
                let discussionId=$(button).attr("discussionId")
                let replyId = $(button).attr("user-id")
                // if (type==='like') {
                //     $(button).parent().append("<a class='btn btn-success button-like-reply' discussionId=" + discussionId + " user-id=" + userId + " reply-id="+replyId+">👍</a>")
                //     console.log("da")
                // }
                // else{
                //     $(button).parent().append("<a class='btn btn-danger button-unlike-reply' discussionId=" + discussionId + " user-id=" + userId + " reply-id="+replyId+">👎</a>")
                // }
                // var likes_count = $("#likes_count")
                // var count = Number($(likes_count).text())
                // if(type==='like')
                //     count += 1
                // else
                //     count -= 1
                // $(likes_count).text(count);
                // $(button).remove()
            }
            else {
                $(button).parent().append("<div>" + message +" <button class='button-confirm'>Ок</button></div>")
            }
        }
    })
}

function ajaxCallLikeDiscussion(url,button,type,message){
    $.ajax({
        url:url,
        success:function (data){
            if (data){
                console.log(data)
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
                let discussionId=$(button).attr("discussion-id")
                if (type==='like') {
                    $(button).parent().append("<a class='btn btn-danger button-unlike-discussion' discussion-id=" + discussionId + " user-id=" + userId + ">💔</a>")
                    console.log("da")
                }
                else{
                    $(button).parent().append("<a class='btn btn-success button-like-discussion' discussion-id=" + discussionId + " user-id=" + userId + ">❤</a>")
                }
                var likes_count = $("#likes_count")
                var count = Number($(likes_count).text())
                if(type==='like')
                    count += 1
                else
                    count -= 1
                $(likes_count).text(count);
                $(button).remove()
            }
            else {
                $(button).parent().append("<div>" + message +" <button class='button-confirm'>Ок</button></div>")
            }
        }
    })
}



function ajaxCallLikeDiscussionAlternative(url,button,type,message){
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
                let discussionId=$(button).attr("discussion-id")
                if (type==='like') {
                    $(button).parent().append("<a class='btn btn-danger button-unlike-discussion-alt' discussion-id=" + discussionId + " user-id=" + userId + ">💔</a>")
                    console.log("da")
                }
                else{
                    $(button).parent().append("<a class='btn btn-success button-like-discussion-alt' discussion-id=" + discussionId + " user-id=" + userId + ">❤</a>")
                }
                var likes_count = $(button).parent().siblings(".likes_count").first()
                var count = Number(likes_count.text())
                if(type==='like')
                    count += 1
                else
                    count -= 1
                $(likes_count).text(count);
                $(button).remove()
            }
            else {
                $(button).parent().append("<div>" + message +" <button class='button-confirm'>Ок</button></div>")
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
