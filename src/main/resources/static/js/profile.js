// const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
// const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

// var lingElem = document.getElementById("weddings")

// lingElem.addEventListener("click", weddingShow)

// async function weddingShow(event) {
//     event.preventDefault()

//     fetch("http://localhost:8080/weddings", {
//         method: 'GET',
//         headers: {
//             'Content-Type': 'application/json',
//             'Accept': 'application/json',
//             [csrfHeaderName]: csrfHeaderValue
//         }
//     }).then(res => res.json())
//       .then(data => {
//         console.log(data)
//       })
// }