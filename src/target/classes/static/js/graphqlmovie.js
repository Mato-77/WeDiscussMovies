var page = 1

var filters = {}
var user
var likedMovies
var movieId
$(document).ready(function () {
    // displayBody()
    likedMovies = $("#user-info").attr("likedMovies")
    user = $("#user-info").attr("user")
    console.log(likedMovies)
    likedMovies = likedMovies.split("movieId:").filter(i =>!isNaN(parseInt(i))).map(i =>splitString(i))
    console.log(likedMovies)
    $("#actors").toggle()
    $("#director").toggle()
    $("#discussions").toggle()
    $("#likes").toggle()
    $("#rates").toggle()
    $("#replies").toggle()
    $("#reply-user").toggle()


    $( "#discussed-top" ).dialog({
        autoOpen: false,
        width: 650,
        height: 330,
        title: "Најдискутирани филмови низ годините:",
        show: {
            effect: "blind",
            duration: 300
        },
        hide: {
            effect: "explode",
            duration: 150
        }
    });

    $("#button-show-discussed-top").on("click", function(){
        $( "#discussed-top" ).dialog( "open" );
    })


    $("#replyUser").change(function () {
        $("#reply-user").toggle()
        filters['replyUser'] = this.checked
    })
    $("#movieGenres").change(function () {
        filters['movieGenres'] = this.checked
    })


    $("#movieActors").change(function () {
        $("#actors").toggle()
        filters['actors'] = this.checked
    })
    $("#movieDirector").change(function () {
        $("#director").toggle()
        filters['director'] = this.checked

    })
    $("#movieDiscussions").change(function () {
        $("#discussions").toggle()
        filters['discussions'] = this.checked

    })


    $("#movieRates").change(function () {
        $("#rates").toggle()
        filters['rates'] = this.checked
    })
    $("#movieLikes").change(function () {
        $("#likes").toggle()
        filters['likes'] = this.checked

    })

    $("#movieId").change(function () {
        filters['movieId'] = this.checked
    })
    $("#replyText").change(function () {
        filters['replyText'] = this.checked
    })
    $("#replyDate").change(function () {
        filters['replyDate'] = this.checked
    })
    $("#movieTitle").change(function () {
        filters['movieTitle'] = this.checked
    })
    $("#movieDesc").change(function () {
        filters['movieDesc'] = this.checked
    })

    $("#movieImageUrl").change(function () {
        filters['movieImageUrl'] = this.checked
    })
    $("#movieAiringDate").change(function () {
        filters['movieAiringDate'] = this.checked
    })
    $("#imdbRating").change(function () {
        filters['imdbRating'] = this.checked
    })

    $("#actorId").change(function () {
        filters['actorId'] = this.checked
    })
    $("#actorName").change(function () {
        filters['actorName'] = this.checked
    })
    $("#actorSurname").change(function () {
        filters['actorSurname'] = this.checked
    })
    $("#actorBirth").change(function () {
        filters['actorBirth'] = this.checked
    })
    $("#actorImage").change(function () {
        filters['actorImage'] = this.checked
    })
    $("#actorDesc").change(function () {
        filters['actorDesc'] = this.checked
    })
    $("#directorId").change(function () {
        filters['directorId'] = this.checked
    })
    $("#directorName").change(function () {
        filters['directorName'] = this.checked
    })
    $("#directorSurname").change(function () {
        filters['directorSurname'] = this.checked
    })
    $("#directorBirth").change(function () {
        filters['directorBirth'] = this.checked
    })
    $("#directorImage").change(function () {
        filters['directorImage'] = this.checked
    })
    $("#directorDesc").change(function () {
        filters['directorDesc'] = this.checked
    })
    $("#discussion-user").toggle()

    $("#discussionId").change(function () {
        filters['discussionId'] = this.checked
    })
    $("#discussionText").change(function () {
        filters['discussionText'] = this.checked
    })
    $("#discussionDate").change(function () {
        filters['discussionDate'] = this.checked
    })
    $("#discussionTitle").change(function () {
        filters['discussionTitle'] = this.checked
    })
    $("#discussionImage").change(function () {
        filters['discussionImage'] = this.checked
    })
    $("#discussionUser").change(function () {
        filters['discussionUser'] = this.checked
        $("#discussion-user").toggle()
    })

    $("#discussion-userId").change(function () {
        filters['discussion-userId'] = this.checked
    })
    $("#discussion-userName").change(function () {
        filters['discussion-userName'] = this.checked
    })
    $("#discussion-userSurname").change(function () {
        filters['discussion-userSurname'] = this.checked
    })
    $("#discussion-userUserName").change(function () {
        filters['discussion-userUserName'] = this.checked
    })

    $("#like-userId").change(function () {
        filters['like-userId'] = this.checked
    })
    $("#like-userName").change(function () {
        filters['like-userName'] = this.checked
    })
    $("#like-userSurname").change(function () {
        filters['like-userSurname'] = this.checked
    })
    $("#like-userUserName").change(function () {
        filters['like-userUserName'] = this.checked
    })
    $("#rates-user").toggle()

    $("#rates-grade").change(function () {
        filters['rates-grade'] = this.checked
    })
    $("#rates-reason").change(function () {
        filters['rates-reason'] = this.checked
    })
    $("#rates-show-user").change(function () {
        filters['rates-show-user'] = this.checked
        $("#rates-user").toggle()
    })

    $("#rates-userId").change(function () {
        filters['rates-userId'] = this.checked
    })
    $("#rates-userName").change(function () {
        filters['rates-userName'] = this.checked
    })
    $("#rates-userSurname").change(function () {
        filters['rates-userSurname'] = this.checked
    })
    $("#rates-userUserName").change(function () {
        filters['rates-userUserName'] = this.checked
    })
    $("#discussionReplies").change(function () {
        filters['discussionReplies'] = this.checked
        $("#replies").toggle()
    })

    $("#replyUserId").change(function () {
        filters['replyUserId'] = this.checked
    })
    $("#replyUserName").change(function () {
        filters['replyUserName'] = this.checked
    })
   $(document.body).on("click",".btn-promeni",function (){

           window.location = "/graphql/movie/add/"+$(this).attr("movieId")
   })


    $("#okay-button").click(function () {
        if ((!filters['actors'] &&
            !filters['likes'] &&
            !filters['genres'] &&
            !filters['rates'] &&
            !filters['discussions'] &&
            !filters['director']) && (
            !filters['movieId'] &&
            !filters['movieDesc'] &&
            !filters['movieTitle'] &&
            !filters['movieImageUrl'] &&
            !filters['movieAiringDate'] &&
            !filters['imdbRating']
        ))
            return
        let string = "{ movies{" + '\n'
        if (filters['movieId'])
            string += "movieId" + '\n'
        if (filters['movieTitle'])
            string += "title" + '\n'
        if (filters['movieDesc'])
            string += "description" + '\n'
        if (filters['movieImageUrl'])
            string += 'imageUrl' + '\n'
        if (filters['movieAiringDate'])
            string += 'airingDate' + '\n'
        if (filters['imdbRating'])
            string += 'imdbRating' + '\n'

        if (filters['actors'] && (filters['actorId'] || filters['actorName'] || filters['actorSurname']
            || filters['actorImage'] || filters['actorDesc'] || filters['actorBirth'])) {
            string += 'actors{person{' + '\n'
            if (filters['actorId'])
                string += 'personId' + '\n'
            if (filters['actorName'])
                string += 'name' + '\n'
            if (filters['actorSurname'])
                string += 'surname' + '\n'
            if (filters['actorImage'])
                string += 'imageUrl' + '\n';
            if (filters['actorDesc'])
                string += 'description' + '\n'
            if (filters['actorBirth'])
                string += 'dateOfBirth' + '\n'
            string += '} }' + '\n'
        }

        if (filters['director'] && (filters['director'] || filters['directorName'] || filters['directorSurname']
            || filters['directorImage'] || filters['directorDesc'] || filters['directorBirth'])) {
            string += 'director{' + '\n'

            if (filters['director'])
                string += 'personId' + '\n'
            if (filters['directorName'])
                string += 'name' + '\n'
            if (filters['directorSurname'])
                string += 'surname' + '\n'
            if (filters['directorImage'])
                string += 'imageUrl' + '\n';
            if (filters['directorDesc'])
                string += 'description' + '\n'
            if (filters['directorBirth'])
                string += 'dateOfBirth' + '\n'

            string += '}' + '\n'
        }
        if (filters['movieGenres']) {
            string += "genres{ genre{ genreType } }" + '\n'
        }
        if (filters['discussions'] && (filters['discussionId'] || filters['discussionText']
            || filters['discussionDate'] || filters['discussionTitle'] || filters['discussionReplies']
            || filters['discussionUser'])) {
            string += 'discussions{ ' + '\n'
            if (filters['discussionId'])
                string += 'discussionId' + '\n'
            if (filters['discussionText'])
                string += 'text' + '\n'
            if (filters['discussionDate'])
                string += 'date' + '\n'
            if (filters['discussionTitle'])
                string += 'title' + '\n'
            if (filters['discussionUser'] && (filters['discussion-userId'] || filters['discussion-userName']
                || filters['discussion-userSurname'] || filters['discussion-userUserName'])) {
                string += 'user{' + '\n'
                if (filters['discussion-userId'])
                    string += 'userId' + '\n'
                if (filters['discussion-userUserName'])
                    string += 'username' + '\n'
                if (filters['discussion-userName'])
                    string += 'name' + '\n'
                if (filters['discussion-userSurname'])
                    string += 'surname' + '\n'
                string += '}' + '\n'
            }
            if (filters['discussionReplies'] && (filters['replyText'] || filters['replyDate']
                || filters['replyUser'])) {
                string += 'replies{'
                if (filters['replyText'])
                    string += 'text' + '\n'
                if (filters['replyDate'])
                    string += 'date' + '\n'
                if (filters['replyUser'] && (filters['replyUserId'] || filters['replyUserName'])) {
                    string += 'user{' + '\n'
                    if (filters['replyUserId'])
                        string += 'userId' + '\n'
                    if (filters['replyUserName'])
                        string += 'username' + '\n'
                    string += '}'
                }
                string += '}'
            }
            string += '}' + '\n'

        }
        if (filters['likes'] && (filters['like-userId'] || filters['like-userUserName'])) {
            string += 'likes{ user {' + '\n'
            if (filters['like-userId'])
                string += 'userId' + '\n'
            if (filters['like-userUserName'])
                string += 'username' + '\n'
            string += '} }' + '\n'
        }
        if (filters['rates'] && (filters['rates-grade'] || filters['rates-reason'] || filters['rates-show-user'])) {
            string += 'rates{' + '\n'
            if (filters['rates-grade'])
                string += 'starsRated' + '\n'
            if (filters['rates-reason'])
                string += 'reason' + '\n'
            if (filters['rates-show-user'] && (filters['rates-userId'] || filters['rates-userUserName'])) {
                string += 'user{' + '\n'
                if (filters['rates-userId'])
                    string += 'userId' + '\n'
                if (filters['rates-userUserName'])
                    string += 'username' + '\n'
                string += '}' + '\n'
            }
            string += '}' + '\n'
        }

        string += '} }'
        ajaxCall(null,string,"fetch")

    })
    $("#dialog-rating-qraphql").dialog({
        autoOpen: false,
        modal: true,
        buttons: [
            {
                text: "Потврди",
                click: function () {
                    $("#dialog-rating-qraphql").dialog("close")

                    let reason = $("#reason-graph").val()
                    let grade = $("#grade-graph").val()
                    console.log(reason)

                    let string = "mutation{ addGradeMovie(movieId:" + movieId+ " , userId: " + user + " , grade: {reason: \"" + reason +"\", rating: " + grade +" }){ reason starsRated } }"
                    ajaxCall(null,string,"grade")
                }
            },
            {
                text: "Откажи",
                click: function () {
                    $("#dialog-rating-qraphql").dialog("close")
                }
            }
        ]
    });
    $(document.body).on("click",".btn-delete",function (){
        let string = 'mutation{\n' +
            '    deleteMovie(id: ' +id+') {\n' +
            '        title\n' +
            '    }\n' +
            '} '
        ajaxCall($(this),string,"delete")
    })
    $(document.body).on("click",".btn-unlike",function (){
        let string = 'mutation{\n' +
            '    unlikeMovie(movieId: ' +$(this).attr("movieId")+' , userId: ' + user + ')}'
        ajaxCall($(this),string,"unlike")
    })
    $(document.body).on("click",".btn-like",function (){
        let string = 'mutation{\n' +
            '    likeMovie(movieId: ' +$(this).attr("movieId")+' , userId: ' + user + ')}'
        ajaxCall($(this),string,"like")
    })

    $(document.body).on("click",".btn-oceni",function (){
        console.log("Da")
        movieId = $(this).attr("movieId")
        $("#dialog-rating-qraphql").dialog("open")
    })

})
function ajaxCall(button,string,type){
    $.ajax({
        url: "/graphql",
        content: "application/json",
        data: JSON.stringify({
            query: string
        }),
        contentType: "application/json",
        method: "POST",
        accept: "application/json",
        success:function (data){
            if (type === "delete") {
                window.alert("Филмот со наслов " + data.data.deleteMovie.title + " е успешно избришан!")
                $(button).parent().parent().fadeOut(2000)
            }
            else if (type === "fetch"){
                displayData(data)
            }
            else if (type === "grade"){
            //rabote, nemam display deka dodal ocena
            }
            else if (type === "like"){
                $(button).parent().append("<button class='btn-unlike bottom-heart btn btn-danger' movieId='" +$(button).attr("movieId") +"'>💔</button>")
                $(button).css('display',"none")
            }
            else if (type === 'unlike'){
                $(button).parent().append("<button class='bottom-heart btn btn-success btn-like' movieId='" +$(button).attr("movieId") +"' >❤</button>")
                $(button).css('display',"none")

            }

        }
    })
}
function displayData(data){
    $("#tbody-table-parent").empty()
   // let div =
   //$(document.body).remove("#tbody-table")
    $("#tbody-table").remove()
    let container = $("<div id='tbody-table' class='col-md-10' style='margin: auto'></div>")
   // $("#tbody-table-parent").append(container)


    for (let item of data.data.movies) {
        let tr = $("<div class='accordion-item' style='overflow: hidden'></div>")
        let divPerson =$("<div></div>")

        let divHeader = $("<h2 class='accordion-header' id='heading"+ item.movieId +"' >" +
            "<button class='accordion-button collapsed' type='button' data-bs-toggle='collapse' " +
            "data-bs-target='#collapse"+item.movieId+"' aria-expanded='false' aria-controls='collapse"+item.movieId+"'>" +
            item.title +
            "</button>" +
            "</h2>")
        let divBody = $("<div id='collapse" + item.movieId + "' class='accordion-collapse collapse' " +
            "aria-labelledby='heading" + item.movieId +
            "' data-bs-parent='#tbody-table'> " +
            "</div>")
        let divBodyAdd = $("<div class='accordion-body'></div>")
        $(divBodyAdd).append("<h3>Идентификатор: " + item.movieId + "</h3>")
        $(divBodyAdd).append("<h3>Наслов: " + item.title + "</h3>")
        $(divBodyAdd).append("<h3>Опис: " + item.description + "</h3>")
        $(divBodyAdd).append("<h3>Датум на Премиера: " + item.airingDate + "</h3>")
        $(divBodyAdd).append("<h3>Рејтинг: " + item.imdbRating + "</h3>")
        $(divBodyAdd).append("<h3>Постер: " + item.imageUrl + "</h3>")


        if (item.movieId && user){
            let divButtons = $("<div class='sub-container'></div>")
            let divButtonsInside = $("<div class=\"container my-3 bg-light\"></div>")
            let divButtonsInsideChild = $("<div class=\"col-md-12 text-center\"></div>")
            $(divButtonsInsideChild).append("<button class='btn btn-warning btn-promeni mе-1'  movieId='" + item.movieId + "'>Промени</button>")
            $(divButtonsInsideChild).append("<button class='btn btn-secondary btn-oceni mе-1'  movieId='" + item.movieId + "'>Оцени</button>")
            if (!likedMovies.includes('' + item.movieId))
                $(divButtonsInsideChild).append("<button class='btn-like bottom-heart btn btn-success' movieId='" +item.movieId + "'>❤</button>")
            else
                $(divButtonsInsideChild).append("<button class='btn-unlike bottom-heart btn btn-danger' movieId='" +item.movieId + "'>💔</button>")
            $(divButtonsInside).append(divButtonsInsideChild)
            $(divButtons).append($("<h2 style='text-align: center'>Акции</h2>"))
            $(divButtons).append(divButtonsInside)
            $(divBodyAdd).append(divButtons)
        }


        $(divBody).append(divBodyAdd)

        if (item.genres) {
            let genres = $("<div class='sub-container'></div>")
            $(genres).append($("<h3>Жанрови на филмот</h3>"))
            for (let g of item.genres) {
                let span = $("<span class=\"card-genre\">" + g.genre.genreType + " </span>")
                $(genres).append(span)
                $(genres).append("<hr>")
            }
            $(divBody).append(genres)
        }
        if (item.actors) {
            let actors = $("<div class='sub-container'></div>")
            $(actors).append($("<h3>Актери на филмот</h3>"))
            for (let a of item.actors) {
                let person = a.person
                let personDiv = $("<div></div>")
                $(personDiv).append("<h5> Ид: " + person.personId + "</h5>")
                $(personDiv).append("<h5> Име: " + person.name + "</h5>")
                $(personDiv).append("<h5> Презиме: " + person.surname + "</h5>")
                $(personDiv).append("<img style='height: 150px; width: auto' src='" + person.imageUrl + "'/>")
                $(personDiv).append("<h5> Опис: " + person.description + "</h5>")
                $(personDiv).append("<h5> Датум на раѓање: " + person.dateOfBirth + "</h5>")
                $(personDiv).append("<hr>")


                $(actors).append(personDiv)
            }
            $(divBody).append(actors)
        }
        let director = item.director
        if (item.director != null) {
            let directorDiv = $("<div class='sub-container'></div>")
            $(directorDiv).append($("<h3>Режисер на филмот</h3>"))
            $(directorDiv).append("<h5> Ид: " + director.personId + "</h5>")
            $(directorDiv).append("<h5> Име: " + director.name + "</h5>")
            $(directorDiv).append("<h5> Презиме: " + director.surname + "</h5>")
            $(directorDiv).append("<img style='height: 150px; width: auto' src='" + director.imageUrl + "'/>")
            $(directorDiv).append("<h5> Опис: " + director.description + "</h5>")
            $(directorDiv).append("<h5> Датум на раѓање: " + director.dateOfBirth + "</h5>")

            $(divBody).append(directorDiv)
        }
        if (item.discussions != null) {
            let discussions = $("<div class='sub-container'></div>")
            $(discussions).append($("<h3>Дискусии на филмот</h3><br>"))
            for (let disc of item.discussions) {
                let d = $("<div></div>")
                $(d).append("<h5> Ид: " + disc.discussionId + "</h5>")
                $(d).append("<h5> Наслов: " + disc.title + "</h5>")
                $(d).append("<h5> Текст: " + disc.text + "</h5>")
                $(d).append("<h5> Датум: " + disc.date + "</h5>")

                if (disc.user){
                    let user = $("<div class='sub-container'>Корисник</div>")
                    $(user).append($("<h6>Автор на дискусијата</h6>"))
                    $(user).append("<h6> Ид: " + disc.user.userId + "</h6>")
                    $(user).append("<h6> Корисничко име: " + disc.user.username + "</h6>")
                    $(user).append("<h6> Име: " + disc.user.name + "</h6>")
                    $(user).append("<h6> Презиме: " + disc.user.surname + "</h6>")
                    $(d).append(user)
                }
                if (disc.replies) {
                    let replies = $("<div class='sub-container'></div>")
                    $(replies).append($("<h3>Реплики за дискусијата</h3>"))
                    for (let r of disc.replies) {
                        let reply = $("<div></div>")
                        $(reply).append("<h5> Текст: " + r.text + "</h5>")
                        $(reply).append("<h5> Датум: " + r.date + "</h5>")

                        if (r.user) {
                            let userDiv = $("<div></div>")
                            $(userDiv).append("<h5> Ид: " + r.user.userId + "</h5>")
                            $(userDiv).append("<h5> Корисничко име: " + r.user.username + "</h5>")

                            $(reply).append(userDiv)
                            $(reply).append($("<hr>"))
                            $(replies).append(reply)
                        }
                    }
                    $(d).append(replies)
                }
                $(d).append($("<hr>"))
                $(discussions).append(d)
            }
            $(divBody).append(discussions)
        }
        if (item.likes) {
            let likes = $("<div class='sub-container'></div>")
            $(likes).append($("<h3>Лајкови за филмот</h3>"))
            for (let like of item.likes) {
                let l = $("<div></div>")
                $(l).append("<h5> Ид: " + like.user.userId + "</h5>")
                $(l).append("<h5> Корисничко име: " + like.user.username + "</h5>")
                $(l).append("<hr>")
                $(likes).append(l)
            }
            $(divBody).append(likes)
        }
        if (item.rates) {
            let rates = $("<div class='sub-container'></div>")
            $(rates).append($("<h3>Оцени за филмот</h3>"))
            for (let rate of item.rates) {
                let r = $("<div></div>")
                $(r).append("<h5> Рејтинг: " + rate.starsRated + "</h5>")
                $(r).append("<h5> Причина: " + rate.reason + "</h5>")

                if (rate.user) {
                    let u = $("<div></div>")
                    $(u).append("<h5> Идентификатор: " + rate.user.userId + "</h5>")
                    $(u).append("<h5> Корисничко име: " + rate.user.username + "</h5>")
                    $(r).append(u)
                    $(rates).append(r)
                }
            }
            $(divBody).append(rates)
        }
        console.log(user)
        $(tr).append(divHeader)
        $(tr).append(divBody)
        $(container).append(tr)

    }
    console.log(container)
    $(document.body).append(container)


}
function displayBody() {
    //initial query - no ne e potrebno
    $.ajax({
        url: "/graphql",
        content: "application/json",
        data: JSON.stringify({
            query: '{ movies' +
                '{ movieId title imdbRating imageUrl ' +
                'genres{ ' +
                'genre{ genreType } } ' +
                '} ' +
                'genres { genreType }' +
                '}'
        }),
        contentType: "application/json",
        method: "POST",
        accept: "application/json",
        success: function (data) {
            for (let item of data.data.movies) {
                let movie = $("<div class='col-md-3 elements row elem-graphql'></div>")
                let background = "background:url(" + item.imageUrl + ") no-repeat center #eee;"
                let cardBody = $("<div class='card-body card bg-image' style='" + background + "'></div>")
                let title = item.title
                let a = $("<a class='card-text-center' style='color: white' href='/movies/'" + item.movieId + "'>" +
                    " <h3 class='card-title title'>" + title + "</h3></a>")
                for (let genre of item.genres) {
                    let text = genre.genre.genreType
                    let span = "<span th:text='" + text + "' hidden class=\"card-genre\"></span>"
                    $(a).append(span)
                }
                console.log(a)
                let rated = item.imdbRating
                let h3 = $("<span class='card-text bottom'><h3>Оценет: " + item.imdbRating + "/10</h3><span>")
                $(cardBody).append(a)
                $(cardBody).append(h3)
                if ($(".cont").attr("user") != null) {
                    $(cardBody).append("<th:block sec:authorize=\"isAuthenticated()\">\n" +
                        "                            <a class=\"bottom-heart btn btn-success button-add-favourite-list\" th:user-id='${user.getUserId()}' th:if='${!likedMovies.contains(movie)}' th:movie-id='${'" + item.movieId + "'}'>❤</a>\n" +
                        "                            <a class=\"bottom-heart btn btn-danger button-remove-favourite-list\" th:user-id='${user.getUserId()}'  th:if='${!likedMovies.contains(movie)}' th:movie-id='${'" + item.movieId + "'}'>💔</a>\n" +
                        "                        </th:block>")
                }
                $(movie).append(cardBody)
                $(".cont").append(movie)
            }

        },
        error: function (error) {
            console.log(error)
        }
    })
}
function splitString(value){
    if(value.indexOf(',') === -1) {
        if (value.indexOf(']') != -1)
            return value.substr(0,value.indexOf(']'))
        return value
    }
    else
        return value.substr(0,value.indexOf(','))
}
