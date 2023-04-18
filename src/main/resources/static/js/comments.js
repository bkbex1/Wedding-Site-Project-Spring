function start(){
    let path = window.location.pathname.split("/");
    let weddingId = path[path.length-1];
    let commentDiv = document.getElementById("comments")
    // let commentDiv = document.createElement("div")
    fetch(`http://localhost:8080/${weddingId}/comments`).then(res=>res.json())
    .then(data=>{

        // for(let comment of data){
        //     commentDiv.innerHTML += '<div class="mt-2">'
        //     commentDiv.innerHTML +=     '<div class="d-flex flex-row p-3">'
        //     commentDiv.innerHTML += `<img src="/image/${comment.creator.picture.id}" width="40" height="40" class="rounded-circle mr-3">`
        //     commentDiv.innerHTML +=         '<div class="w-100">'
        //     commentDiv.innerHTML +=             '<div class="d-flex justify-content-between align-items-center">'
        //     commentDiv.innerHTML +=                 '<div class="d-flex flex-row align-items-center">'
        //     commentDiv.innerHTML += `<span class="mr-2">${comment.creator.username}</span>`
        //     commentDiv.innerHTML +=                 '</div>'
        //     commentDiv.innerHTML += `<small>${comment.created}</small>`
        //     commentDiv.innerHTML +=             '</div>'
        //     commentDiv.innerHTML += `<p class="text-justify comment-text mb-0">${comment.text}</p>`
        //     if(comment.creator.username.equals(userDto.getUsername())){
        //         commentDiv.innerHTML += '<div>'
        //         commentDiv.innerHTML += '<div th:id="${comment.id}" class="d-flex flex-row user-feed">'
        //         commentDiv.innerHTML += `<form method="DELETE" action="/comment/${comment.getId()}">`
        //         commentDiv.innerHTML += '<button class="edit-btn btn btn-default btn-sm btn-info">'
        //         commentDiv.innerHTML += '<span class=" glyphicon glyphicon-edit"></span> Edit">'
        //         commentDiv.innerHTML += '</button>'
        //         commentDiv.innerHTML += '<button type="submit"  class="delete-btn btn btn-default btn-sm btn-danger">'
        //         commentDiv.innerHTML += '<span class=" glyphicon glyphicon-edit"></span> Delete'
        //         commentDiv.innerHTML += '</button>'
        //         commentDiv.innerHTML += '</form>'
        //         commentDiv.innerHTML += '</div></div>'

        //     }
        //     commentDiv.innerHTML += '</div></div></div>'
        //     commentContainer.appendChild(commentDiv)
        //     console.log(comment)

        // }

        })

    // document.querySelectorAll(".edit-btn").forEach(element => {
    //     element.addEventListener("click", (event)=>{

    //         event.preventDefault
    //         let parent = event.target.parentElement
    //         console.log(parent.getAttribute("commentId"))
    //         console.log("almoust")
    //     })
    // });

    // document.querySelectorAll(".delete-btn").forEach(element => {
    //     element.addEventListener("click", (event)=>{
    //         event.preventDefault
    //         const parent = event.target.parentElement
    //         console.log(parent.id)
    //         fetch(`http://localhost:8080/comment/${parent.id}/`, {
    //             method: 'DELETE',
    //             headers: {

    //             }
    //           }).then( console.log("cliced"))
            
    //     })
    // });
}

start();