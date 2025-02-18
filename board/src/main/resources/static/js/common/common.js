function logout() {
    fetch('/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        console.log(response);
        if (response.ok) {
            alert("로그아웃이 완료되었습니다.")
            window.location.href = '/';
        }
    }).catch(error => {
        console.log("Error: ", error);
    });
}