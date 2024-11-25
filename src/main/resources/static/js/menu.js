document.addEventListener("DOMContentLoaded", () => {
    const hamburgerButton = document.querySelector(".hamburger-btn");
    const closeButton = document.querySelector(".close-btn");
    const menu = document.querySelector(".hamburger-menu");
    const newsToggle = document.querySelector(".toggle-news");
    const newsSubmenu = document.querySelector(".news-submenu");

    // Open hamburger menu
    hamburgerButton.addEventListener("click", () => {
        menu.classList.remove("hidden");
        hamburgerButton.classList.add("hidden");
        closeButton.classList.remove("hidden");
    });

    // Close hamburger menu
    closeButton.addEventListener("click", () => {
        menu.classList.add("hidden");
        closeButton.classList.add("hidden");
        hamburgerButton.classList.remove("hidden");
    });

    // Toggle news submenu
    newsToggle.addEventListener("click", (event) => {
        event.preventDefault();
        newsSubmenu.classList.toggle("hidden");
    });
});
