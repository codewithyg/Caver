document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("search-form");
    
    // 폼 제출 이벤트 처리
    form.addEventListener("submit", function (event) {
        const cityInput = document.getElementById("city-input").value.trim();

        // 입력값이 비어있는지 체크
        if (!cityInput) {
            alert("지역명을 입력해주세요.");
            event.preventDefault();  // 폼 제출 방지
        } else {
            // 입력값이 유효하다면, 이를 서버로 제출
            console.log("지역명:", cityInput);
        }
    });
});
