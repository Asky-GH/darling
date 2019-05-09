$(document).ready(function () {
    $('#language').change(function () {
        const languageSelect = document.querySelector('#language');
        const languageId = languageSelect.value;
        const url = ctx + '/country?languageId=' + languageId;
        $.ajax({
            type: 'GET',
            url: url,
            headers: {
                Accept: 'application/json; charset=utf-8',
                'Content-Type': 'application/json; charset=utf-8'
            },
            success: function (result) {
                const countries = $.parseJSON(result);
                const countrySelect = $('#country');
                countrySelect.empty();
                for (i = 0; i < countries.length; i++) {
                    const option = document.createElement('OPTION');
                    option.value = countries[i].id;
                    option.innerHTML = countries[i].name;
                    countrySelect.append(option);
                }
            }
        })
    });
});
