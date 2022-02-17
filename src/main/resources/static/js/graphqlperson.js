
var filters = {}
var user
var type
var typePerson
var button
$(document).ready(function (){
    $("#discussions").toggle()
    $("#likes").toggle()
    $("#rates").toggle()
    $("#replies").toggle()
    $("#reply-user").toggle()
    $("#discussion-user").toggle()
    $("#movies").toggle()
    $("#rates-user").toggle()
    user = $("#user-info").attr("user")
    type = $("#user-info").attr("type")



    $("input[type=checkbox]").change(function (){
        console.log($(this).val())
        console.log(filters)
        filters[$(this).val()] = this.checked
    })
    $("#replyUser").change(function () {
        $("#reply-user").toggle()
        filters['replyUser'] = this.checked
    })
    $("#personActor").change(function () {
        $("#movies").toggle()
        filters['movies'] = this.checked
    })
    $("#personDirector").change(function () {
        $("#movies").toggle()
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
    $("#discussionUser").change(function (){
        $("#discussion-user").toggle()
        filters['discussionUser'] = this.checked
    })
    $("#discussionReplies").change(function (){
        $("#replies").toggle()
        filters['replies'] = this.checked
    })
    $("#rates-show-user").change(function (){
        $("#rates-user").toggle()
        filters['rates-show-user'] = this.checked
    })
    $(".filter").click(function (){
        console.log("Adada")
        console.log(filters)
        if ((!filters['personDiscussions'] &&
            !filters['personActor'] &&
            !filters['personDirector'] &&
            !filters['personRates'] )&& (
            !filters['personId'] &&
            !filters['personName'] &&
            !filters['personSurname'] &&
            !filters['personImage'] &&
            !filters['personDate'] &&
            !filters['personDescription']
        ))
            return
        let string
        if (type === "актер")
            string = "{ actors{" + '\n'
        else
            string = "{ directors{" + '\n'
        if (filters['personId'])
            string += "personId" + '\n'
        if (filters['personName'])
            string += "name" + '\n'
        if (filters['personSurname'])
            string += "surname" + '\n'
        if (filters['personImage'])
            string += 'imageUrl' + '\n'
        if (filters['personDate'])
            string += 'dateOfBirth' + '\n'
        if (filters['personDescription'])
            string += 'description' + '\n'

        if ((filters['personActor'] || filters['personDirector']) && (filters['movieId'] || filters['movieTitle'] || filters['movieDescription']
            || filters['movieAiringDate'] || filters['movieImage'])) {
            string += 'actors{movie{' + '\n'
            if (filters['movieId'])
                string += 'movieId' + '\n'
            if (filters['movieTitle'])
                string += 'title' + '\n'
            if (filters['movieDescription'])
                string += 'description' + '\n'
            if (filters['movieAiringDate'])
                string += 'airingDate' + '\n';
            if (filters['movieImage'])
                string += 'image' + '\n'
            string += '} }' + '\n'
        }

        if (filters['personDiscussions'] && (filters['discussionId'] || filters['discussionText']
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
        console.log("Here I am")
        ajaxCall($(this),string,type)
    })
    $(document.body).on("click",".btn-oceni-person",function (){
        button = $(this)
        $("#dialog-rating-person").dialog("open")
    })
    $(document.body).on("click",".btn-delete-person",function (){
        let string = 'mutation{\n' +
            '    deletePerson(id: ' +$(this).attr("personId")+'){ name }' +
            '} '
        ajaxCall(button,string,"delete")
    })
    $(document.body).on("click",".btn-еdit-person",function (){
        ajaxCall($(this),"{ person(id: " + $(this).attr("personId") + " ){ personId type } } " ,"getType")
    })
    $("#dialog-rating-person").dialog({
        autoOpen: false,
        modal: true,
        buttons: [
            {
                text: "Потврди",
                click: function () {
                    $("#dialog-rating-person").dialog("close")

                    let reason = $("#reason-person").val()
                    let grade = $("#grade-person").val()
                    console.log(reason)

                    let string = "mutation{ addGradePerson(id: " + $(button).attr("personId")+ " , userId: " + user + " , grade: {reason: \"" + reason +"\", rating: " + grade +" } ) }"
                    ajaxCall(button,string,"grade")
                }
            },
            {
                text: "Откажи",
                click: function () {
                    $("#dialog-rating-person").dialog("close")
                }
            }
        ]
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
            if (type === "актер" || type === "режисер"){
                displayDataPerson(data,type)
            }
            else if (type == "grade"){
                $(button).text("Промени оцена")
            }
            else if (type == "delete"){
                window.alert("Личност со име " + data.data.deletePerson.name + " е успешно избришана!")
                $(button).parent().parent().parent().fadeOut(2000)
            }
            else if (type==="getType"){
                typePerson = data.data.person.type
                id = data.data.person.personId
                window.location = "/graphql/person/add/" + id + "?type="+typePerson
            }
        }
        })
    }


function displayDataPerson(data,type){
    let items = []
    if (type === "режисер")
        items = data.data.directors
    else
        items = data.data.actors
    console.log(items)
    $("#tbody-table").empty()
    for (let i of items){
        let tr = $("<tr class='elements'></tr>")
        let divPerson =$("<td><div></div></td>")
        if (i.personId && user){
            $(divPerson).append("<button class='btn-oceni-person' userId='" + user + "' personId='" + i.personId + "'>Оцени</button>")
            $(divPerson).append("<button class='btn-еdit-person' personId='" + i.personId + "'>Промени</button>")
            $(divPerson).append("<button class='btn-delete-person' personId='" + i.personId + "'>Избриши</button>")

        }
        $(divPerson).append("<h3>Идентификатор: " + i.personId + "</h3>")
        $(divPerson).append("<h3>Име: " + i.name + "</h3>")
        $(divPerson).append("<h3>Презиме: " + i.surname + "</h3>")
        $(divPerson).append("<h3>Опис: " + i.description + "</h3>")
        $(divPerson).append("<h3>Датум: " + i.dateOfBirth + "</h3>")
        $(divPerson).append("<h3>Слика: " + i.imageUrl + "</h3>")
        $(tr).append(divPerson)


        if (i.actors != null) {
            let divMovie = $("<td><div></div></td>")

            for (let movie of i.actors) {
                let m = movie.movie
                if (m != null) {
                    $(divMovie).append("<h3>Идентификатор: " + m.movieId + "</h3>")
                    $(divMovie).append("<h3>Наслов: " + m.title + "</h3>")
                    $(divMovie).append("<h3>Опис: " + m.description + "</h3>")
                    $(divMovie).append("<h3>Датум: " + m.airingDate + "</h3>")
                    $(divMovie).append("<h3>Слика: " + i.imageUrl + "</h3>")
                }
            }
            $(tr).append(divMovie)
        }
        if (i.discussions != null) {
            let divDiscussions = $("<td><div></div></td>")
            for (let disc of i.discussions) {
                $(divDiscussions).append("<h3>Идентификатор: " + disc.discussionId + "</h3>")
                $(divDiscussions).append("<h3>Наслов: " + disc.title + "</h3>")
                $(divDiscussions).append("<h3>Опис: " + disc.text + "</h3>")
                $(divDiscussions).append("<h3>Датум: " + disc.date + "</h3>")
                let u = disc.user
                if (u != null) {
                    $(divDiscussions).append("<h3>Идентификатор корисник: " + u.userId + "</h3>")
                    $(divDiscussions).append("<h3>Име корисник: " + u.name + "</h3>")
                    $(divDiscussions).append("<h3>Презиме корисник: " + u.surname + "</h3>")
                    $(divDiscussions).append("<h3>Корисничко име: " + u.username + "</h3>")
                }
                if (disc.replies != null) {
                    for (let r of disc.replies) {
                        $(divDiscussions).append("<h3>Текст: " + r.text + "</h3>")
                        $(divDiscussions).append("<h3>Датум реплика: " + r.date + "</h3>")
                        if (r.user != null) {
                            $(divDiscussions).append("<h3>Идентификатор корисник: " + r.user.userId + "</h3>")
                            $(divDiscussions).append("<h3>Корисничко име: " + r.user.username + "</h3>")
                        }
                    }
                }
            }
            $(tr).append(divDiscussions)
        }
        if (i.rates != null) {
            let rates = $("<td><div></div></td>")
            for (let rate of i.rates) {
                $(rates).append("<h3>Причина: " + rate.reason + "</h3>")
                $(rates).append("<h3>Оцена: " + rate.starsRated + "</h3>")

                let u = rate.user
                if (u != null) {
                    $(rates).append("<h3>Идентификатор корисник: " + u.userId + "</h3>")
                    $(rates).append("<h3>Име корисник: " + u.name + "</h3>")
                    $(rates).append("<h3>Презиме корисник: " + u.surname + "</h3>")
                    $(rates).append("<h3>Корисничко име: " + u.username + "</h3>")
                }
            }
            $(tr).append(rates)
        }
        $("#tbody-table").append(tr)
    }
}