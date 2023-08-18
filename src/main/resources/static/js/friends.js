function start(){
    document.addEventListener("DOMContentLoaded", function() {
        var liElements = document.querySelectorAll(".conversation");
      
        function sendRequestToProfile(url) {
          var lastElement = url.split("/").pop();
          var profileUrl = "/profile/" + lastElement;
      
          fetch(profileUrl)
            .then((response) => response.json())
            .then((data) => {
              console.log("Отговор от сървъра:", data);
            })
            .catch((error) => {
              console.error("Грешка при изпращане на заявка:", error);
            });
        }
    
        liElements.forEach(function(li) {
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
    }