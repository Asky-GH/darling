$(document).ready(function () {
    const senderId = document.querySelector('#sender').value;
    const receiverId = document.querySelector('#receiver').value;
    const url = ctx + '/refresh?senderId=' + senderId + '&receiverId=' + receiverId;
    setInterval(function () {
        $.ajax({
            type: 'POST',
            url: url,
            headers: {
                Accept: 'application/json; charset=utf-8',
                'Content-Type': 'application/json; charset=utf-8'
            },
            success: function (result) {
                const messages = $.parseJSON(result);
                if (messages != null) {
                    for (i = 0; i < messages.length; i++) {
                        const columns = document.createElement('DIV');
                        columns.className = 'columns';
                        const contentColumn = document.createElement('DIV');
                        contentColumn.className = 'column is-5 is-offset-2 is-11-mobile';
                        const content = document.createElement('DIV');
                        content.className = 'notification content';
                        const text = document.createElement('P');
                        text.innerHTML = messages[i].text;
                        const time = document.createElement('P');
                        time.style = 'text-align: right';
                        const small = document.createElement('SMALL');
                        const locale = document.querySelector('#locale').value;
                        if (locale === 'ru-RU') {
                            small.innerHTML = new Date(messages[i].createdAt).toLocaleDateString(locale) + " " + new Date(messages[i].createdAt).toLocaleTimeString(locale);
                        } else {
                            const options = { year: 'numeric', month: 'short', day: 'numeric' };
                            small.innerHTML = new Date(messages[i].createdAt).toLocaleDateString(locale, options) + " " + new Date(messages[i].createdAt).toLocaleTimeString(locale);
                        }
                        time.appendChild(small);
                        content.appendChild(text);
                        content.appendChild(time);
                        contentColumn.appendChild(content);
                        columns.appendChild(contentColumn);
                        document.querySelector('#msgs').appendChild(columns);
                    }
                }
            }
        })
    }, 1000);
});
