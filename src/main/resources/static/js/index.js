/*var counter = 1;
setInterval(function()  {
  document.getElementById('radio'+ counter).checked = true;
  counter++; 

  if(counter>4){
    counter=1
  }

}, 5000);*/

var slide_index = 1;
        displaySlides(slide_index);
        function nextSlide(n) {
            displaySlides(slide_index += n);
        }
        function currentSlide(n) {
            displaySlides(slide_index = n);
        }
        function displaySlides(n) {
            var i;
            var slides = document.getElementsByClassName("showSlide");
            if (n > slides.length) { slide_index = 1 }
            if (n < 1) { slide_index = slides.length }
            for (i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            slides[slide_index - 1].style.display = "block";
        }