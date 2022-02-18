var moviesDirector = []
var moviesActor = []
var typePerson
var returned
$(document).ready(function () {
    $("#img-val").toggle()
    $("#name-val").toggle()
    $("#date-val").toggle()
    $("#description-val").toggle()
    $("#surname-val").toggle()
    $("#type-val").toggle()

    let id = $(".granka").attr("granka")
    typePerson = $(".granka").attr("type")
    if (id){
        displayInfoPerson(id)
    }
    else
        displayBody()


    $("#name").on("keyup", function () {
        $("#name-val").hide()
    })
    $("#surname").on("keyup", function () {
        $("#surname-val").hide()
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
    $("input[type=radio]").on("change",function (){
        if (this.value == "A") {
            $("#type-val").hide()
            doneDisplay(moviesActor)
        }
        else if (this.value == 'D') {
            $("#type-val").hide()
            doneDisplay(moviesDirector)
        }
        else
            $("#type-val").show()
    })
    $(".btn-test-add-person").on("click", function () {
        if ($("#name").val() === '') {
            $("#name-val").show()
            return
        }
        if ($("#surname").val() === '') {
            $("#surname-val").show()
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
        if ($("#description").val() === '') {
            $("#description-val-val").show()
            return;
        }
        let name = $("#name").val()
        let surname = $("#surname").val()
        let description = $("#description").val()
        let date = $("#date").val()
        let image = $("#image").val()
        let type = $("input[type=radio]:checked").val()
        let actors = []
        $("#movies option:selected").each(function () {
            actors.push($(this).val())
        })
        console.log(actors)
        let query = 'mutation{'
        if (id)
            query += ' editPerson( id: ' + $(".granka").attr("granka") +" , "
        else
            query += ' savePerson('



        query +="name: \"" + name +"\" , surname: \"" + surname + "\" , type: \""+ type
            +"\" , birthDate:  \""+ date + "\",  image: \"" + image + "\" , description: \""+ description + "\""


        if (actors.length > 0)
            query+=" , " + " movieIds: [" + actors + "]"

        query+=" ){ personId name surname } }"
        console.log(JSON.stringify({query:query}))
        if (!id)
            callAjax(query,"add","додаден!")
        else
            callAjax(query,"add","променет!")

    })
})
function add(data,type){

    window.alert("Личноста " + data.name + " " + data.surname  + " и идентификатор " + data.personId + " е успешно " + type)

    if (type == 'A')
    window.location = "/graphql/person/actors"
    else
        window.location = "/graphql/person/directors"

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
            if (type === 'displayMoviesDirector'){
                moviesDirector = data.data.moviesByType
                if (typePerson=='D')
                    doneDisplay(moviesDirector)
            }
            else if (type==="displayMovieActors"){
                moviesActor = data.data.moviesByType
                if (typePerson=='A')
                    doneDisplay(moviesActor)
            }
            else if (type === 'add') {
                if (data.data.savePerson)
                    add(data.data.savePerson, text)
                else
                    add(data.data.editPerson, text)

            }
            else if (type === 'edit') {
                edit(data.data)
            }
        }
    })

}
function displayBody() {
    callAjax( '{ moviesByType(type: \"A\"){ '+
        ' movieId title } }',"displayMovieActors")
    callAjax('{ moviesByType(type: \"D\"){ '+
        ' movieId title } }',"displayMovieDirector")
}
function doneDisplay(data){
    $("#movies").empty()
    console.log("Da")
    $("#movies").append("<option></option>")
    for (let item of data) {
        $("#movies").append("<option value='" + item.movieId+"'>" + item.title + "</option>")
    }
    if (returned){
        console.log(data)
        if (data.length == 0) {
            for (let item of returned) {
                console.log(returned)
                $("#movies").append("<option selected value='" + item.movieId + "'>" + item.title + "</option>")

            }
        }
        else {
            for (let i of returned) {
                $('#movies option[value=' + i.movieId + ']').attr('selected', 'selected');
            }
        }
    }

    }

function edit(data){
    let person = data.person
    $("#name").val(person.name)
    $("#description").val(person.description)
    $("#image").val(person.imageUrl)
    $("#date").val(person.dateOfBirth)
    $("#surname").val(person.surname)

    inputs = $("input[type=radio]")
    for (let i of inputs){
        if ($(i).val() === data.type)
            $(i).attr("checked",true)
    }
    for(let item of data.moviesByType){
        $("#movies").append("<option  value='" + item.movieId + "'>" + item.title + "</option>")
    }
    if (person.type=='A') {
        for (let a of person.actors) {
            $('#movies option[value=' + a.movie.movieId + ']').attr('selected', 'selected');
        }
       // returned = data.actors.map(i => i.movie)
       // doneDisplay(moviesActor)

    }
    else{
        //returned = data.movies
       // doneDisplay(moviesDirector)
        for (let a in person.movies){
            $('#movies option[value=' + a.movieId + ']').attr('selected', 'selected');
        }
    }

}
function displayInfoPerson(id) {
    console.log(id)
    let q
    if (typePerson == 'D') {
        q = '{ person(id: ' + id + '){ personId name surname imageUrl dateOfBirth type description movies{ movieId title } }'
        q+=' moviesByType(type: \"D\"){ \n' +
            ' movieId title } }'
    }
    else {
        q = '{ person(id: ' + id + '){ personId name surname imageUrl dateOfBirth type description actors{ movie{ movieId title } } }'
        q += '\n moviesByType(type: \"A\"){ \n' +
            ' movieId title } }'
    }
    console.log(q)
    callAjax(q, 'edit')
}