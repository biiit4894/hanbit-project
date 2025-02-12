window.onload = function () {

    const urlStr = window.location.href;
    const pathVariable = urlStr.split('/').pop();
    const id = parseInt(pathVariable);
    console.log("id: ", id);
    console.log("typeof id: ", typeof id);

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

    const deleteButton = document.querySelector('#article-delete-button');
    if (deleteButton !== null) {
        document.querySelector('#article-delete-button').addEventListener('click', function(e) {
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