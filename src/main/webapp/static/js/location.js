$(document).ready(function () {
    $('#country').change(function () {
        const citySelect = $('#city');
        const cityLabel = $('#cityLabel');
        citySelect.empty();
        citySelect.append(cityLabel);
        const countrySelect = document.querySelector('#country');
        if (countrySelect.value !== 'Country') {
            const countryId = countrySelect.value;
            const url = '/darling/location?countryId=' + countryId;
            $.ajax({
                type: 'GET',
                url: url,
                headers: {
                    Accept: 'application/json; charset=utf-8',
                    'Content-Type': 'application/json; charset=utf-8'
                },
                success: function (result) {
                    const cities = $.parseJSON(result);
                    for (i = 0; i < cities.length; i++) {
                        const option = document.createElement('OPTION');
                        option.value = cities[i].id;
                        option.innerHTML = cities[i].name;
                        citySelect.append(option);
                    }
                }
            })
        }
    });
});
