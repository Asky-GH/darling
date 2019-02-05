$(document).ready(function () {
    var citySelect = $('#city');
    $('#country').change(function () {
        const country_id = document.querySelector('#country').value;
        const url = "/darling/location?country_id=" + country_id;
        $.ajax({
            type: 'GET',
            url: url,
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            success: function (result) {
                var cities = $.parseJSON(result);
                citySelect.empty();
                for (i = 0; i < cities.length; i++) {
                    var option = document.createElement('OPTION');
                    option.value = cities[i].id;
                    option.innerHTML = cities[i].name;
                    citySelect.append(option);
                }
            }
        })
    });
});
