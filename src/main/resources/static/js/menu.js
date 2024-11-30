// 페이지의 햄버거 메뉴 처리를 위한 JS 코드

document.addEventListener("DOMContentLoaded", () => {
    const hamburgerButton = document.querySelector(".hamburger-btn");
    const closeButton = document.querySelector(".close-btn");
    const menu = document.querySelector(".hamburger-menu");
    const newsToggle = document.querySelector(".toggle-news");
    const newsSubmenu = document.querySelector(".news-submenu");

    // 햄버거 메뉴 열기
    hamburgerButton.addEventListener("click", () => {
        menu.classList.remove("hidden");
        hamburgerButton.classList.add("hidden");
        closeButton.classList.remove("hidden");
    });

    // 햄버거 메뉴 열기
    closeButton.addEventListener("click", () => {
        menu.classList.add("hidden");
        closeButton.classList.add("hidden");
        hamburgerButton.classList.remove("hidden");
    });

    // 뉴스 기능의 토글 메뉴
    newsToggle.addEventListener("click", (event) => {
        event.preventDefault();
        newsSubmenu.classList.toggle("hidden");
    });
});
