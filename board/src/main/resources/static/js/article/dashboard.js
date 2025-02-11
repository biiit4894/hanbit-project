window.onload = function () {
    let page = 0
    fetch(`/api/article?page=${page}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then(response => {
        console.log(response);
        console.log(response.content)
        response.json().then(r => {
            const data = r.content;
            console.log(data);

            const COUNT_PER_PAGE = 6; // 페이지 당 보여줄 게시물 수
            const numberButtonWrapper = document.querySelector('.number-button-wrapper'); // 페이지네이션 버튼 wrapper
            const ul = document.querySelector('ul'); // 게시물을 담을 unordered list
            const prevButton = document.querySelector('.prev-button'); // 이전 페이지 버튼
            const nextButton = document.querySelector('.next-button'); // 이후 페이지 버튼
            let pageNumberButtons; // 페이지 버튼들

            let currentPage = 1; // 초기 페이지 번호

            // 필요한 페이지 번호 개수 구하기
            const getTotalPageCount = () => {
                return Math.ceil(data.length / COUNT_PER_PAGE);
            };

            // 필요한 페이지 번호 수에 맞게 페이지 버튼 구성하기
            const setPageButtons = () => {
                numberButtonWrapper.innerHTML = '';

                for (let i = 1; i <= getTotalPageCount(); i++) {
                    numberButtonWrapper.innerHTML += `<span class="number-button"> ${i} </span`;
                }

                numberButtonWrapper.firstChild.classList.add('selected');
                pageNumberButtons = document.querySelectorAll('.number-button');
            };

            // 페이지에 해당하는 게시물 ul에 넣어주기 (pageNumber - 이동할 페이지 번호)
            const setPageOf = (pageNumber) => {
                ul.innerHTML = '';

                for (
                    let i = COUNT_PER_PAGE * (pageNumber - 1) + 1;
                    i <= COUNT_PER_PAGE * (pageNumber - 1) + 6 && i <= data.length;
                    i++
                ) {
                    const li = document.createElement('li');

                    // 컨테이너
                    const postContainer = document.createElement('div');
                    postContainer.className = 'post-container';

                    // 글 번호
                    const postNumber = document.createElement('p');
                    postNumber.className = 'post-number';

                    // 글 제목
                    const postTitle = document.createElement('p');
                    postTitle.className = 'post-title';

                    // 글 작성일자
                    const postCreatedAt = document.createElement('p');
                    postCreatedAt.className = 'post-created-at';

                    // 글 좋아요 개수
                    const postLikeCount = document.createElement('p');
                    postLikeCount.className = 'post-like-count';

                    // 글 댓글 개수
                    const postCommentCount = document.createElement('p');
                    postCommentCount.className = 'post-comment-count';

                    postNumber.textContent = data[i - 1].id;
                    postTitle.textContent = data[i - 1].title;
                    postCreatedAt.textContent = data[i - 1].createdAt;
                    postLikeCount.textContent = data[i - 1].likeCount;
                    postCommentCount.textContent = data[i - 1].commentCount;

                    postContainer.append(postNumber, postTitle, postCreatedAt, postLikeCount, postCommentCount);
                    li.append(postContainer);
                    ul.append(li);
                }
            };

            // 페이지 이동에 따른 css 클래스 적용
            const moveSelectedPageHighlight = () => {
                const pageNumberButtons = document.querySelectorAll('.number-button'); // 페이지 버튼들

                pageNumberButtons.forEach((numberButton) => {
                    if (numberButton.classList.contains('selected')) {
                        numberButton.classList.remove('selected');
                    }
                });

                pageNumberButtons[currentPage - 1].classList.add('selected');
            };

            setPageButtons();
            setPageOf(currentPage);

            // 페이지 번호 버튼 클릭 리스너
            pageNumberButtons.forEach((numberButton) => {
                numberButton.addEventListener('click', (e) => {
                    currentPage = +e.target.innerHTML;
                    console.log(currentPage);
                    setPageOf(currentPage);
                    moveSelectedPageHighlight();
                });
            });

            // 이전 버튼 클릭 리스너
            prevButton.addEventListener('click', () => {
                if (currentPage > 1) {
                    currentPage -= 1;
                    setPageOf(currentPage);
                    moveSelectedPageHighlight();
                }
            });


            // 이후 버튼 클릭 리스너
            nextButton.addEventListener('click', () => {
                if (currentPage < getTotalPageCount()) {
                    currentPage += 1;
                    setPageOf(currentPage);
                    moveSelectedPageHighlight();
                }
            });
        });
    });




};