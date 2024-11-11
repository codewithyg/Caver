// Select the hamburger button and hidden menu
const hamburgerButton = document.querySelector('.hamburger');
const hiddenMenu = document.querySelector('.hidden-menu');

// Toggle menu visibility on button click
hamburgerButton.addEventListener('click', () => {
    hiddenMenu.classList.toggle('show-menu');
});
