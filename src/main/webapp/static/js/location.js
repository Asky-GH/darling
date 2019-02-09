$(document).ready(function () {
    var citySelect = $('#city');
    $('#country').change(function () {
        var cityLabel = $('#cityLabel');
        citySelect.empty();
        citySelect.append(cityLabel);
        if (document.querySelector('#country').innerHTML !== 'Country') {
            const countryId = document.querySelector('#country').value;
            const url = "/darling/location?countryId=" + countryId;
            $.ajax({
                type: 'GET',
                url: url,
                headers: {
                    Accept: "application/json; charset=utf-8",
                    "Content-Type": "application/json; charset=utf-8"
                },
                success: function (result) {
                    var cities = $.parseJSON(result);
                    for (i = 0; i < cities.length; i++) {
                        var option = document.createElement('OPTION');
                        option.value = cities[i].id;
                        option.innerHTML = cities[i].name;
                        citySelect.append(option);
                    }
                }
            })
        }
    });
});
