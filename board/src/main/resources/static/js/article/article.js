const urlStr = window.location.href;
const pathVariable = urlStr.split('/').pop();
const id = parseInt(pathVariable);


/*
    게시글 수정
     */

function toggleEditMode(isEdit) {
    const titleView = document.getElementById('title-view');
    const titleEdit = document.getElementById('title-edit');
    const contentView = document.getElementById('content-view');
    const contentEdit = document.getElementById('content-edit');

    const updateButton = document.getElementById('article-update-button');
    const returnButton = document.getElementById('article-return-button');
    const saveButton = document.getElementById('article-save-button');

    if (isEdit) {
        titleView.style.display = "none";
        contentView.style.display = "none";
        titleEdit.style.display = "block";
        contentEdit.style.display = "block";
        updateButton.style.display = "none";
        returnButton.style.display = "block";
        saveButton.style.display = "block";
    } else {
        if (confirm("수정을 취소합니다.")) {
            titleView.style.display = "block";
            contentView.style.display = "block";
            titleEdit.style.display = "none";
            contentEdit.style.display = "none";
            updateButton.style.display = "block";
            returnButton.style.display = "none";
            saveButton.style.display = "none";
        }
    }
}

/*
게시글 작성
 */
function submit() {
    fetch(`/api/article/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            title: document.getElementById('title-edit').value,
            content: document.getElementById('content-edit').value
        })
    }).then(response => {
        console.log("response");
        console.log(response);
        if (response.ok) {
            alert("게시글이 수정되었습니다.");
            window.location.href = `/dashboard/${id}`;
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
        console.log("Error: ", error);
    });
}

window.onload = function () {
    /*
    에러 메시지 초기화
     */
    document.querySelectorAll('.error-message').forEach(message => {
        if (message.innerText.length !== 0) {
            message.innerText = ' ';
        }
    });


    /*
    게시글 상세조회
     */
    fetch(`/api/article/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        console.log("response");
        console.log(response);
        response.json().then(data => {
            console.log("response.json()");
            console.log(data);
            const title = document.querySelector('.article-title');
            const author = document.querySelector('.article-author');
            const createdAt = document.querySelector('.article-created-at');
            const commentCount = document.querySelector('.article-comment-count-span');
            const likeCount = document.querySelector('.article-like-count-span');
            const content = document.querySelector('.article-content');

            title.textContent = data.title;
            createdAt.textContent = data.createdAt;
            author.textContent = data.author;
            commentCount.textContent = data.commentCount;
            likeCount.textContent = data.likeCount;
            content.textContent = data.content;
        });
    })

    /*
    게시글 삭제
     */
    const deleteButton = document.getElementById('article-delete-button');
    if (deleteButton !== null) {
        deleteButton.addEventListener('click', function(e) {
            if (confirm('게시글을 삭제하시겠습니까?')) {
                fetch(`/api/article/${id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(response => {
                    console.log(response);
                    if (response.ok) {
                        alert('게시물이 삭제되었습니다.');
                        window.location.href = '/dashboard';
                    }
                }).catch(error => {
                    console.log('Error: ', error);
                });
            }
        });
    }

};