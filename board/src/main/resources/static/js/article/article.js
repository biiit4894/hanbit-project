const urlStr = window.location.href;
const pathVariable = urlStr.split('/').pop();
const id = parseInt(pathVariable); // articleId


let articleData;

/*
    게시글 수정 모드 판별
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
게시글 수정 후 저장
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

/*
좋아요 아이콘 박스 셰도우 표현
 */

// TODO: 함수이름 변경
function bigImg(isLarge, field) {
    const likeIcon = document.getElementById('like-img');
    if (isLarge) {
        if (field === 'like') {
            likeIcon.style.boxShadow = "0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23)";
        }
    } else {
        if (field === 'like') {
            likeIcon.style.boxShadow = "none";
        }
    }
}

/*
    댓글 수정 모드 판별
     */
function toggleCommentEditMode(isEdit) {
    const contentView = document.getElementById('comment-content-view');
    const contentEdit = document.getElementById('comment-content-edit');

    const updateButton = document.getElementById('comment-update-button');      // 댓글 수정모드 전환 버튼
    const returnButton = document.getElementById('comment-return-button');      // 댓글 수정 취소 버튼
    const saveButton = document.getElementById('comment-update-save-button');  // 댓글 수정 내역 저장 버튼
    const deleteButton = document.getElementById('comment-delete-button');      // 댓글 삭제 버튼
    if (isEdit) {
        contentView.style.display = "none";
        contentEdit.style.display = "block";

        updateButton.style.display = "none";
        returnButton.style.display = "block";
        saveButton.style.display = "block";
        deleteButton.style.display = "none"
    } else {
        if (confirm("수정을 취소합니다.")) {
            contentView.style.display = "block";
            contentEdit.style.display = "none";

            updateButton.style.display = "block";
            returnButton.style.display = "none";
            saveButton.style.display = "none";
            deleteButton.style.display = "block"
        }
    }
}

/*
댓글 수정 후 저장
*/
function submitComment(commentId) {
    fetch(`/api/comment/${commentId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            content: document.getElementById('comment-content-edit').value
        })
    }).then(response => {
        console.log("댓글 저장 후 value: ", document.getElementById('comment-content-edit').value)
        console.log("response");
        console.log(response);
        if (response.ok) {
            alert("댓글이 수정되었습니다.");
            window.location.href = `/dashboard/${id}`;
        } else if (response.status === 400) {
            response.json().then(r => {
                Object.keys(r).forEach(key => {
                    console.log("r[key]: ", r[key]);
                    document.getElementById(`comment-${key}-edit-error`).innerText = r[key]; // 오류메시지 표기
                })
                console.log(r);
            })
        }
    }).catch(error => {
        console.log("Error: ", error);
    });
}

/*
댓글 삭제
 */
function deleteComment(commentId) {
    fetch(`/api/comment/${commentId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => {
        console.log(response);
        if (response.ok) {
            alert("댓글이 삭제되었습니다.");
            window.location.href = `/dashboard/${id}`;
        } else {
            alert(response);
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
            // 인라인 스크립트에서의 참조를 위한 위해 할당
            articleData = data;

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

    /*
    댓글 작성
     */
    const submitCommentButton = document.getElementById('comment-save-button');
    const commentTextArea = document.getElementById('comment-content-write');
    submitCommentButton.addEventListener('click', function (e) {
        fetch(`/api/comment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                articleId: articleData.id,
                // parentId: ,
                content: commentTextArea.value
            })
        }).then(response => {
            console.log(response);
            if (response.ok) {
                alert('댓글이 작성되었습니다.');
                window.location.href = `/dashboard/${id}`;
            } else if (response.status === 400) {
                response.json().then(r => {
                    Object.keys(r).forEach(key => {
                        console.log("key: ", key);
                        document.getElementById(`comment-${key}-error`).innerText = r[key];
                    })
                })
            }
        }).catch(error => {
            console.log("Error: ", error);
        });
    });



};