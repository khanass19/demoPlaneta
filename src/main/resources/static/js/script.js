$("#loginBtn").click(function () {

    var user = {
        email: $("#email").val(),
        password: $("#password").val()
    }

    $.ajax({
        url: "/public/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function (tokenData) {
            alert("++" + tokenData);
            localStorage.setItem('currentUser', user.email);
            localStorage.setItem('token', tokenData);
            window.open("/home", "_self");
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });


});

$("#registerBtn").click(function () {

    var user = {
        email: $("#email").val(),
        password: $("#password").val()
    }

    $.ajax({
        url: "/public/register",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function (tokenData) {
            alert("++" + tokenData);
            localStorage.setItem('currentUser', user.email);
            localStorage.setItem('token', tokenData);
            window.open("/home", "_self");
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });
});

function redirectOnError (xhr) {
    if(xhr.status == 401) {
        window.open("/public/main", "_self");
    }
}
