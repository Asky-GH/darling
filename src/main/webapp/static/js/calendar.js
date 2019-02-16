$(document).ready(function () {
    const dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    const year = dtToday.getFullYear();

    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    const maxDate = (year - 18) + '-' + month + '-' + day;
    const minDate = (year - 101) + '-' + month + '-' + (day + 1);
    $('#date').attr('min', minDate);
    $('#date').attr('max', maxDate);
});