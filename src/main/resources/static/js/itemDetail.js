// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;

        fetch(`/api/items/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                // FIXME : alert() 적용 안됨
                alert('수정이 완료되었습니다.');
                location.replace(`/items/${id}`);
            });
    });
}