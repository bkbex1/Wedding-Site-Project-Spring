// function start(){

//     document.querySelectorAll(".edit-btn").forEach(element => {
//         element.addEventListener("click", (event)=>{

//             event.preventDefault
//             let parent = event.target.parentElement
//             console.log(parent.getAttribute("commentId"))
//             console.log("almoust")
//         })
//     });

//     document.querySelectorAll(".delete-btn").forEach(element => {
//         element.addEventListener("click", (event)=>{
//             event.preventDefault
//             const parent = event.target.parentElement
//             console.log(parent.id)
//             fetch(`http://localhost:8080/comment/${parent.id}/`, {
//                 method: 'DELETE',
//                 headers: {

//                 }
//               }).then( console.log("cliced"))
            
//         })
//     });
// }

// start();