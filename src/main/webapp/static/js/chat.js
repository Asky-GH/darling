$(document).ready(function () {
    const sender_id = document.querySelector('#sender').value;
    const receiver_id = document.querySelector('#receiver').value;
    const url = "/darling/refresh?sender_id=" + sender_id + "&receiver_id=" + receiver_id;
    setInterval(function () {
        $.ajax({
            type: 'GET',
            url: url,
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            success: function (result) {
                var messages = $.parseJSON(result);
                if (messages != null) {
                    for (i = 0; i < messages.length; i++) {
                        var columns = document.createElement('DIV');
                        columns.className = "columns";
                        var contentColumn = document.createElement('DIV');
                        contentColumn.className = "column is-4 is-offset-2";
                        var content = document.createElement('DIV');
                        content.className = "notification";
                        content.innerHTML = messages[i].text + " " + messages[i].created_at;
                        contentColumn.appendChild(content);
                        var emptyColumn = document.createElement('DIV');
                        emptyColumn.className = "column is-4";
                        columns.appendChild(contentColumn);
                        columns.appendChild(emptyColumn);
                        document.querySelector('#msgs').appendChild(columns);
                    }
                }
            }
        })
    }, 1000);
});
