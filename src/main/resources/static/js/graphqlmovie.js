var page = 1


$(document).ready(function (){
       displayBody()
    })
function displayBody(){
  $.ajax({
      url:"/graphql",
      content:"application/json",
      data:JSON.stringify({
          query:'{movies{ title }}'
      }),
      contentType:"application/json",
      method:"POST",
      accept:"application/json",
      success: function (data){
            let counter= 0;
            let row = "<div class='row'></div>"
          for(let item of data.data.movies){
              let movie = "<div class='col-md-3 elements'></div>"
              let background = "background:url(" + item.imageUrl + ") no-repeat center #eee;"
              let cardBody = "<div class='card-body card bg-image' style='"+background+"'></div>"
              let title = item.title
              let a = "<a class='card-text-center' style='color: white' href='/movies/'"+item.movieId+"'>" +
                  " <h3 class='card-title title' th:text='${'"+title+"'}'></h3></a>"
              for (let genre of item.genres){
                  let text = genre.genreType
                  let span =  "<span th:text='"+text+"' hidden class=\"card-genre\"></span>"
                  $(a).append(span)
              }
              let rated = item.imbdRating
              let h3 = "<h3 className='card-text bottom' th:text='${'Rated '"+rated+"'}'></h3>"


          }

      },
      error:function (error){
          console.log(error)
      }
  })
}