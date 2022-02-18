
$(document).ready(function () {
    $("#img-val").toggle()
    $("#title-val").toggle()
    $("#date-val").toggle()
    $("#description-val").toggle()

    let id = $(".granka").attr("granka")
    if (id)
        displayInfoMovie(id)
    else
    displayBody()


    $("#title").on("keyup", function () {
        $("#title-val").hide()
    })
    $("#img").on("keyup", function () {
        $("#img-val").hide()
    })
    $("#description").on("keyup", function () {
        $("#description-val").hide()
    })
    $("#date").on("keyup", function () {
        $("#date-val").hide()
    })
    $(".btn-test-add-movie").on("click", function () {
        if ($("#title").val() === '') {
            $("#title-val").show()
            return
        }
        if ($("#description").val() === '') {
            $("#description-val").show()
            return
        }
        if ($("#date").val() === '') {
            $("#date-val").show()
            return;
        }
        if ($("#image").val() === '') {
            $("#img-val").show()
            return;
        }
        let title = $("#title").val()
        let description = $("#description").val()
        let date = $("#date").val()
        let image = $("#image").val()
        let rating = $("#rating").val()
        let director = $("#directorId option:selected").val()
        let actors = []
        $("#actors option:selected").each(function () {
            actors.push($(this).val())
        })
        console.log(actors)
        let genres = []
        $("#genres option:selected").each(function () {
            genres.push($(this).val())
        })
        let query = 'mutation {'
        if (id)
         query += ' editMovie( id: ' + $(".granka").attr("granka") +" , "
        else
            query += ' saveMovie('



       query = query+="title: \"" + title +"\" , description: \"" + description + "\" , airingDate: \""+ date
        +"\" , image:  \""+ image + "\",  rating: " + rating

        if (director.length > 0)
            query+=" , director: " + director
        if (actors.length > 0)
            query+=" , " + " actorIds: [" + actors + "]"
        if (genres.length > 0)
            query+=" , genreIds: [" + genres +"]"
        query+=" ) { movieId title } }"
        console.log(JSON.stringify({query:query}))
        if (!id)
            callAjax(query,"add","додаден!")
        else
            callAjax(query,"add","променет!")

    })
})
function add(data,type){

        window.alert("Филмот со наслов " + data.title  + " и идентификатор " + data.movieId + " е успешно " + type)

    window.location = "/graphql/movie"

}
function callAjax(query,type,text){
    $.ajax({
        url: "/graphql",
        content: "application/json",
        data: JSON.stringify({
            query:query
        }),
        contentType: "application/json",
        method: "POST",
        accept: "application/json",
        success:function (data){
            console.log(data)
            console.log("ajaxSucess")
            if (type === 'display')
            doneDisplay(data)
            else if (type === 'add') {
                if (data.data.saveMovie)
                add(data.data.saveMovie, text)
                else
                    add(data.data.editMovie, text)

            }
            else if (type === 'edit')
                edit(data.data)
        }
    })

}
function displayBody() {
    callAjax( '{ actors{ '+
        ' personId name surname } '+
        'directors{ personId name surname }' +
        'genres{ ' +
        ' genreId genreType  } ' +
        '}','display')
}
function doneDisplay(data){
    for (let item of data.actors) {
        $("#actors").append("<option value='" + item.personId+"'>" + item.name + " " + item.surname+"</option>")
    }
    for (let item of data.directors) {
        $("#directorId").append("<option value='" + item.personId+"'>" + item.name + " " + item.surname+"</option>")
    }
    for (let item of data.genres) {
        $("#genres").append("<option value='" + item.genreId+"'>" + item.genreType+"</option>")
    }
    // if ($(".granka").attr("granka")){
    //     displayInfoMovie($(".granka").attr("granka"))
    // }

}
function edit(data){
    let movie = data.movie
    $("#title").val(movie.title)
    $("#description").val(movie.description)
    $("#image").val(movie.imageUrl)
    $("#date").val(movie.airingDate)
    $("#rating").val(movie.imdbRating)
    doneDisplay(data)
    for (let a of movie.actors){
        console.log(a.person)
        $('#actors option[value=' + a.person.personId+']').attr('selected','selected');
    }
    for (let g of movie.genres){
        console.log(g.genre)
        $('#genres option[value=' + g.genre.genreId+']').attr('selected','selected');
    }
    if (movie.director)
    $("#directorId option[value=" +movie.director.personId+"]").attr("selected","selected")
}
function displayInfoMovie(id) {
    console.log(id)
    let q = '{ movie(id: ' + id + '){ movieId title description imageUrl airingDate' +
        ' imdbRating actors{ person{ personId name surname } } director{ personId name surname }' +
        ' genres{ genre{ genreType genreId } } }'
    q+='  actors{ \n' +
        '         personId name surname } \n' +
        '        directors{ personId name surname } \n' +
        '        genres{ \n' +
        '        genreId genreType  } }'
    console.log(q)
    callAjax(q, 'edit')
}