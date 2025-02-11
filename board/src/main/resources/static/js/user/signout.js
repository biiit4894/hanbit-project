window.onload = function () {
    document.getElementById('signout-form').addEventListener('submit', function(e) {
        e.preventDefault();

        fetch('/api/user/signout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                password: document.getElementById('signout-form-password-input').value
            })
        })
            .then(response => {
                if (response.ok) {
                    response.json().then(r => {
                        console.log(r);
                    })
                    alert("회원 탈퇴가 완료되었습니다.")
                    window.location.href = '/logout';
                } else {
                    console.log(response);
                    return response.json().then(error => {
                        throw new Error(error.message);
                    });
                }

            })
            .catch(error => {
                alert(`${error.message}`)
            }).then()
    })
};

