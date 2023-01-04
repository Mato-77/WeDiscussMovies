
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
                string += 'imageUrl' + '\n'
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
    console.log(data)
    if (type === "режисер")
        items = data.data.directors
    else
        items = data.data.actors
    console.log(items)
    $("#tbody-table-parent").empty()
    let div = $("<div id='tbody-table'></div>")
    $("#tbody-table-parent").append(div)
    $("#tbody-table").empty()
    for (let i of items){
        let tr = $("<div class='accordion-item'></div>")

        let divHeader = $("<h2 class='accordion-header' id='heading"+ i.personId +"' >" +
            "<button class='accordion-button collapsed' type='button' data-bs-toggle='collapse' " +
            "data-bs-target='#collapse"+i.personId+"' aria-expanded='false' aria-controls='collapse"+i.personId+"'>" +
            i.name + ' ' + i.surname +
            "</button>" +
            "</h2>")
        let divBody = $("<div id='collapse" + i.personId + "' class='accordion-collapse collapse' " +
            "aria-labelledby='heading" + i.personId +
            "' data-bs-parent='#tbody-table'> " +
            "</div>")
        let divBodyAdd = $("<div class='accordion-body'></div>")
        $(divBodyAdd).append("<h3>Идентификатор: " + i.personId + "</h3>")
        $(divBodyAdd).append("<h3>Име: " + i.name + "</h3>")
        $(divBodyAdd).append("<h3>Презиме: " + i.surname + "</h3>")
        $(divBodyAdd).append("<h3>Опис: " + i.description + "</h3>")
        $(divBodyAdd).append("<h3>Датум на раѓање: " + i.dateOfBirth + "</h3>")
        $(divBodyAdd).append("<h3>Слика: " + i.imageUrl + "</h3>")

        if (i.personId && user){
            let divButtons = $("<div class='sub-container'></div>")
            let divButtonsInside = $("<div class=\"container my-3 bg-light\"></div>")
            let divButtonsInsideChild = $("<div class=\"col-md-12 text-center\"></div>")
            $(divButtonsInsideChild).append("<button class='btn btn-secondary btn-oceni-person mе-1' userId='" + user + "' personId='" + i.personId + "'>Оцени</button>")
            $(divButtonsInsideChild).append("<button class='btn btn-warning btn-еdit-person mе-1' personId='" + i.personId + "'>Промени</button>")
            $(divButtonsInsideChild).append("<button class='btn btn-danger btn-delete-person mе-1' personId='" + i.personId + "'>Избриши</button>")
            $(divButtonsInside).append(divButtonsInsideChild)
            $(divButtons).append($("<h2 style='text-align: center'>Акции</h2>"))
            $(divButtons).append(divButtonsInside)
            $(divBodyAdd).append(divButtons)
        }
        $(divBody).append(divBodyAdd)


        if (i.actors != null) {
            let divMovie = $("<div class='sub-container'></div>")

            for (let movie of i.actors) {
                let m = movie.movie
                if (m != null) {
                    $(divMovie).append("<h5>Идентификатор: " + m.movieId + "</h5>")
                    $(divMovie).append("<h5>Наслов: " + m.title + "</h5>")
                    $(divMovie).append("<h5>Опис: " + m.description + "</h5>")
                    $(divMovie).append("<h5>Датум: " + m.airingDate + "</h5>")
                    $(divMovie).append("<h5>Слика: " + i.imageUrl + "</h5>")
                    $(divMovie).append("<hr>")
                }
            }
            $(divBody).append(divMovie)
        }
        if (i.discussions != null) {
            let divDiscussions = $("<div class='sub-container'></div>")
            if(i.discussions.length === 0){
                divDiscussions.append($("<h3>Личноста нема дискусии.</h3>"))

            }
        else{
                divDiscussions.append($("<h3>Дискусии:</h3>"))
            }
            for (let disc of i.discussions) {
                $(divDiscussions).append("<h5>Идентификатор на дискусија: " + disc.discussionId + "</h5>")
                $(divDiscussions).append("<h5>Наслов: " + disc.title + "</h5>")
                $(divDiscussions).append("<h5>Опис: " + disc.text + "</h5>")
                $(divDiscussions).append("<h5>Датум: " + disc.date + "</h5>")
                let u = disc.user
                if (u != null) {
                    $(divDiscussions).append("<h5>Идентификатор корисник: " + u.userId + "</h5>")
                    $(divDiscussions).append("<h5>Име корисник: " + u.name + "</h5>")
                    $(divDiscussions).append("<h5>Презиме корисник: " + u.surname + "</h5>")
                    $(divDiscussions).append("<h5>Корисничко име: " + u.username + "</h5>")
                }
                let divDiscReplies = $("<div class='sub-container'></div>")
                if (disc.replies != null) {
                    divDiscReplies.append($("<h3>Реплики:</h3>"))
                    for (let r of disc.replies) {
                        $(divDiscReplies).append("<h6>Текст: " + r.text + "</h6>")
                        $(divDiscReplies).append("<h6>Датум реплика: " + r.date + "</h6>")
                        if (r.user != null) {
                            $(divDiscReplies).append("<h6>Идентификатор корисник: " + r.user.userId + "</h6>")
                            $(divDiscReplies).append("<h6>Корисничко име: " + r.user.username + "</h6>")
                        }
                        $(divDiscReplies).append("<hr>")
                    }
                }
                else{
                    $(divDiscussions).append("<h4>Дискусијата нема реплики.</h4>")
                }
                $(divDiscussions).append("<hr>")
                $(divDiscussions).append(divDiscReplies)
            }
            $(divBody).append(divDiscussions)
        }
        if (i.rates != null) {
            let rates = $("<div class='sub-container'></div>")
            if(i.rates.length > 0){
                rates.append($("<h2>Оцени за личноста:</h2>"))
            }
            for (let rate of i.rates) {
                $(rates).append("<h5>Оцена: " + rate.starsRated + "</h5>")
                $(rates).append("<h5>Причина: " + rate.reason + "</h5>")

                let u = rate.user
                if (u != null) {
                    $(rates).append("<h5>Идентификатор корисник: " + u.userId + "</h5>")
                    $(rates).append("<h5>Име корисник: " + u.name + "</h5>")
                    $(rates).append("<h5>Презиме корисник: " + u.surname + "</h5>")
                    $(rates).append("<h5>Корисничко име: " + u.username + "</h5>")
                }
                $(rates.append($("<hr>")))
            }
            $(divBody).append(rates)
        }
        $(tr).append(divHeader)
        $(tr).append(divBody)
        $("#tbody-table").append(tr)
    }
}