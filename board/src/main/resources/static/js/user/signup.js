window.onload = function () {
    function resetErrorMessages() {
        document.querySelectorAll('.error-message').forEach(message => {
            if (message.innerText.length !== 0) {
                message.innerText = ' ';
            }
        });
    }

    document.getElementById("signup-form").addEventListener('submit', function (e) {
        e.preventDefault();
        resetErrorMessages(); // 오류메시지 초기화

        fetch('/api/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId: document.getElementById('signup-input-userId').value,
                password: document.getElementById('signup-input-password').value,
                nickName: document.getElementById('signup-input-nickName').value,
                email: document.getElementById('signup-input-email').value
            })
        })
            .then(response => {
                console.log(response);
                if (response.ok) {
                    alert('회원가입이 완료되었습니다.');
                    window.location.href = '/login'; // 회원가입 완료 후 로그인 페이지로 이동
                } else if (response.status === 400) {
                    response.json().then(r => {
                        console.log(r);
                        Object.keys(r).forEach(key => {
                            console.log("r[" + key + "]: ", r[key]);
                            let errorMessages = r[key];
                            for (let i = 0; i < errorMessages.length; i++) {
                                console.log(`r[${key}] error message ${i + 1}: `, errorMessages[i]);
                                document.getElementById(`${key}-error`).innerText += errorMessages[i] + "\n"; // 오류메시지 표기

                            }
                        });
                        // if (r.message.includes("이미 사용중인 아이디입니다.")) {
                        //     document.getElementById("userId-error").innerText = r.message;
                        // } else if (r.message.includes("이미 사용중인 별명입니다.")) {
                        //     document.getElementById("nickName-error").innerText = r.message;
                        // } else if (r.message.includes("이미 사용중인 이메일입니다.")) {
                        //     document.getElementById("email-error").innerText = r.message;
                        // }
                    })
                } else {
                    response.json().then(r => {
                        Object.keys(r).forEach(key => {
                            console.log("r[key]: ", r[key]);
                            // document.getElementById(`${key}-error`).innerText = r[key]; // 오류메시지 표기
                        });
                    });
                }
            })
            .catch(error => {
                console.log('Error: ', error);
            });

    });
};

