window.onload = function () {
    function resetErrorMessages() {
        document.querySelectorAll('.error-message').forEach(message => {
            if (message.innerText.length !== 0) {
                message.innerText = ' ';
            }
        });
    }

    const cancelButton = document.querySelector('.button-special');
    const submitButton = document.querySelector('.button-save');

    cancelButton.addEventListener('click', function () {
        if (confirm('작성을 취소하시겠습니까?')) {
            window.location.href = '/dashboard';
        }
    });

    document.querySelector('.article-editor-wrapper').addEventListener('submit', function (e) {
        e.preventDefault();

        resetErrorMessages()

        fetch(`/api/article`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                title: document.querySelector('#article-title').value,
                content: document.querySelector('#article-content').value,
            })
        }).then(response => {
            console.log(response);
            if (response.ok) {
                alert('게시글이 저장되었습니다.');
                // 대시보드 이동, 전체 게시글 fetch
                fetch('/dashboard', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Cache-Control': 'no-cache', // 캐싱 방지
                        'Pragma': 'no-cache'
                    },
                }).then(response => {
                    window.location.href = '/dashboard';
                    if (!response.ok) {
                        console.log(response);
                    }
                }).catch(error => {
                    console.log('Error: ', error);
                });
            } else if (response.status === 400) {
                response.json().then(r => {
                    Object.keys(r).forEach(key => {
                        console.log("r[key]: ", r[key]);
                        document.getElementById(`${key}-error`).innerText = r[key]; // 오류메시지 표기
                    })
                    console.log(r);
                })
            }
        }).catch(error => {
            console.log('Error: ', error);
        });

    });
};