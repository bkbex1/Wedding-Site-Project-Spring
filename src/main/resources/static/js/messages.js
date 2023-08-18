function start(){
    document.addEventListener("DOMContentLoaded", function() {
        var conversationLi = document.querySelectorAll(".conversation");
      
        function sendRequestToProfile(url) {
          var lastElement = url.split("/").pop();
          var viewDiv = document.getElementById("viewMessages");
          var profileUrl = "/profile/conversation/" + lastElement;
          fetch(profileUrl)
            .then((response) => response.json())
            .then((data) => {
              viewDiv.innerHTML = ""
              printing(data, viewDiv);
            })
            .catch((error) => {
              console.error("Грешка при изпращане на заявка:", error);
            });
        }
        
        conversationLi.forEach(function(li) {
          li.addEventListener("click", function(event) {
            event.preventDefault();
            
            var aElement = li.querySelector("a");
            if (aElement) {
              var hrefAttribute = aElement.getAttribute("href");
              sendRequestToProfile(hrefAttribute);
            }
          });
        });
      });

      function printing(dataToPrint, parent, form){

        let ul = document.createElement("ul");

        for(let elem of dataToPrint){
          let sender = elem.sender;
          let text = elem.text;
          let dateSend = elem.dateSend;
          ul.classList.add("list-unstyled");
          let li = document.createElement("li");
          li.classList.add("d-flex","mb-4");

          let img = document.createElement("img");
            img.setAttribute("src", "/image/"+sender.picture.id);
            img.setAttribute("alt", "avatar")
            img.setAttribute("width", "60")
            img.classList.add("rounded-circle","d-flex","align-self-start","me-3","shadow-1-strong")
          li.appendChild(img)
          let divCard = document.createElement("div");
            divCard.classList.add("card")
          let divCardHeader = document.createElement("div");
            divCardHeader.classList.add("card-header","d-flex","justify-content-between","p-3")
            let pName = document.createElement("p")
              pName.classList.add("fw-bold","mb-0")
              pName.textContent = sender.firstName + " " + sender.lastName
            let pTime = document.createElement("p");
              pTime.classList.add("text-muted","small","mb-0")
            let iClock = document.createElement("i");
              iClock.classList.add("far","fa-clock")
              pTime.appendChild(iClock)
              const currentDate = new Date();
              const recordedDate = new Date(dateSend);
              let time = currentDate.getTime() - recordedDate.getTime();
              let timeLeft = calculateTimeUnits(time);

              pTime.textContent = timeLeft;
          divCardHeader.appendChild(pName).appendChild(pTime)
          divCard.appendChild(divCardHeader);
            let divCardBody = document.createElement("div");
            divCardBody.classList.add("card-body")
            let pText = document.createElement("p");
            pText.textContent = text
              pText.classList.add("mb-0")
              divCardBody.appendChild(pText)
          divCard.appendChild(divCardBody);
          li.appendChild(divCard);
          ul.appendChild(li);
          parent.appendChild(ul);

        }
      }

        function calculateTimeUnits(number) {

          const daysDifference = Math.floor(number / (1000 * 60 * 60 * 24));
          const hoursDifference = Math.floor(number / (1000 * 60 * 60));
          const minutesDifference = Math.floor((number % (1000 * 60 * 60)) / (1000 * 60));
          const secondsDifference = Math.floor((number % (1000 * 60)) / 1000);

          if(daysDifference !=null && daysDifference>0){
            return daysDifference+" d "+hoursDifference+" h "+minutesDifference+" m ago";
          }else if(hoursDifference !=null && hoursDifference>0){
            return hoursDifference+" h "+minutesDifference+" m ago";
          }else if(minutesDifference !=null && minutesDifference>0){
            return minutesDifference+" m ago";
          }
      }
}

start();