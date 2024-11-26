function deletePost(postNo) {
    if (confirm("정말 삭제하시겠습니까?")) {
      // 삭제 요청 처리
      fetch(`/board/${postNo}/delete`, { method: 'POST' })
        .then(response => {
          if (response.ok) {
            alert("삭제되었습니다.");
            location.href = '/board';
          } else {
            alert("삭제에 실패했습니다.");
          }
        })
        .catch(error => {
          console.error("삭제 중 오류 발생:", error);
          alert("오류가 발생했습니다.");
        });
    }
  }