$(document).ready(function () {
    const now = new Date();
    var smalls = $('.user-age');
    for (i = 0; i < smalls.length; i++) {
        const birthdate = smalls[i].innerHTML;
        var age = now.getFullYear() - new Date(birthdate).getFullYear();
        if (now.getMonth() < new Date(birthdate).getMonth() || (now.getMonth() === new Date(birthdate).getMonth() && now.getDay() < new Date(birthdate).getDay())) {
            age--;
        }
        smalls[i].innerHTML = age;
    }
});
