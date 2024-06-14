document.addEventListener('DOMContentLoaded', (event) => {
  let slideIndex = 1;
  let slideInterval;

  function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("mySlides");
    let dots = document.getElementsByClassName("dot");
    
    if (n > slides.length) {slideIndex = 1}
    if (n < 1) {slideIndex = slides.length}
    
    for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";  
    }
    
    for (i = 0; i < dots.length; i++) {
      dots[i].className = dots[i].className.replace(" active", "");
    }
    
    slides[slideIndex-1].style.display = "block";  
    dots[slideIndex-1].className += " active";
    slideInterval = setTimeout(() => showSlides(slideIndex += 1), 5000); // Change image every 5 seconds
  }

  function plusSlides(n) {
    clearTimeout(slideInterval); // Clear the timeout to prevent jumping
    showSlides(slideIndex += n);
  }

  function currentSlide(n) {
    clearTimeout(slideInterval); // Clear the timeout to prevent jumping
    showSlides(slideIndex = n);
  }

  // Pause slideshow when mouse is over the container
  document.querySelector('.slideshow-container').addEventListener('mouseover', function() {
    clearTimeout(slideInterval);
  });

  // Resume slideshow when mouse leaves the container
  document.querySelector('.slideshow-container').addEventListener('mouseout', function() {
    slideInterval = setTimeout(() => showSlides(slideIndex += 1), 5000);
  });

  // Initial call to start the slideshow
  showSlides(slideIndex);

  // Expose functions to the global scope
  window.plusSlides = plusSlides;
  window.currentSlide = currentSlide;
});
