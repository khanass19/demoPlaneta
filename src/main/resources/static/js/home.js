$("#saveBtn").click(function () {
    var filmToSave = {
        name: $("#nameFilm").val(),
        lengthInSeconds: $("#lengthFilm").val()
    };

    $.ajax({
        url: "/film",
        type: "POST",
        contentType: "application/json",
        headers: {
            'Authorize': "Bearer " + localStorage.getItem('token')
        },
        data: JSON.stringify(filmToSave),
        success: function (data) {
            alert("Film with name : " + data.name + " was saved!");
            $("#listOfAllFilms").append("<tr><td>" + data.id +
                "</td><td>" + data.name + "</td><td>"
                + data.lengthInSeconds + "</td><td><button type='button' name='"
                + data.id +
                "' class='deleteBtn btn btn-danger'>DELETE</button></td><td><button type='button' " +
                "name='" + data.id + "' class='updateBtn btn btn-warning'>UPDATE</button></td></tr>");
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });
});

var pageCount = 2;

$(document).ready(function () {
    $.ajax({
        url: "/films/pageable?page=1&size=5",
        type: "GET",
        headers: {
            'Authorize': "Bearer " + localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (data) {
            $("#listOfAllFilms tr").remove();
            for (var i = 0; i < data.content.length; i++) {
                $("#listOfAllFilms").append("<tr><td>" + data.content[i].id +
                    "</td><td>" + data.content[i].name + "</td><td>"
                    + data.content[i].lengthInSeconds + "</td><td><button class='deleteBtn btn btn-danger' type='button' " +
                    " name='"
                    + data.content[i].id +
                    "'>DELETE</button></td><td><button class='updateBtn btn btn-warning' type='button' name='"
                    + data.content[i].id +
                    "'>UPDATE</button></td></tr>");
            }
            $("#welcomeField").text(localStorage.getItem('currentUser'));
            console.log(data.totalPages);
            pageCount = data.totalPages
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });
    test();
});

function test() {
    $.ajax({
        url: "/user-info/get/1",
        type: "GET",
        headers: {
            'Authorize': "Bearer " + localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            $("#imageFromDb").attr('src','/images/' + data);
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });
}

$(document).on('click', 'button.deleteBtn', function () {
    var currentClickedButton = this;
    var idToDelete = currentClickedButton.name;
    console.log(this.name);
    $.ajax({
        url: "/film/" + idToDelete,
        type: "DELETE",
        contentType: "application/json",
        headers: {
            'Authorize': "Bearer " + localStorage.getItem('token')
        },
        success: function (user) {
            alert("delete");
            $(currentClickedButton).closest('tr').remove();
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });
});

var Ident;
var focusedButton;

$(document).on('click', 'button.updateBtn', function () {
    $("#myModal").modal('show');
    Ident = this.name;
    focusedButton = this;
});

$('#doUpdate').click(function () {

    var filmToUpdate = {
        id: Ident,
        name: $("#updateValue1").val(),
        lengthInSeconds: $("#updateValue2").val()
    };

    console.log(filmToUpdate);
    alert(localStorage.getItem('token'));

    $.ajax({
        url: "/film",
        type: "PATCH",
        contentType: "application/json",
        headers: {
            'Authorize': "Bearer " + localStorage.getItem('token')
        },
        data: JSON.stringify(filmToUpdate),
        success: function (data) {
            alert("+");
            var oldRowWhichWeUpdatedThen = $(focusedButton).closest('tr');  // шукаємо
            // найближчий відносно кнопки row
            var newUpdatedRow = "<tr><td>" + data.id +
                "</td><td>" + data.name + "</td><td>"
                + data.lengthInSeconds + "<td><button  class='deleteBtn btn btn-danger'" +
                " type='button' name='" +
                data.id
                + "'>Delete</button></td><td><button  class='updateBtn btn btn-warning'" +
                "type='button' name='" +
                data.id + "'>Update</button></td></tr>";  // генеримо рядок з оновленими даними
            oldRowWhichWeUpdatedThen.replaceWith(newUpdatedRow); //робимо заміну старого
            // елементу новим
            $('#myModal').modal('hide');
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });

});

function redirectOnError(xhr) {
    if (xhr.status == 401) {
        alert(xhr.message);
        window.open("/public/main", "_self");
    }
};

$('#pagination-demo').twbsPagination({
    totalPages: pageCount,
    visiblePages: 3,
    next: 'Next',
    prev: 'Prev',
    onPageClick: function (event, page) {
        //fetch content and render here
        $('#page-content').text('Page ' + page) + ' content here';

        loadNewPage(page-1);
    }
});


function loadNewPage(page) {
    $.ajax({
        url: "/films/pageable?page=" + page + "&size=5",
        type: "GET",
        headers: {
            'Authorize': "Bearer " + localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (data) {
            $("#listOfAllFilms tr").remove();
            for (var i = 0; i < data.content.length; i++) {
                $("#listOfAllFilms").append("<tr><td>" + data.content[i].id +
                    "</td><td>" + data.content[i].name + "</td><td>"
                    + data.content[i].lengthInSeconds + "</td><td><button class='deleteBtn btn btn-danger' type='button' " +
                    " name='"
                    + data.content[i].id +
                    "'>DELETE</button></td><td><button class='updateBtn btn btn-warning' type='button' name='"
                    + data.content[i].id +
                    "'>UPDATE</button></td></tr>");
            }
            $("#welcomeField").text(localStorage.getItem('currentUser'));
        },
        error: function (xhr, status, error) {
            var err = eval("(" + xhr.responseText + ")");
            redirectOnError(xhr);
        }
    });
};

function addInfo(object) {
    var notBase64UploadedFile = object.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(notBase64UploadedFile);
    reader.onload = function () {
        var base64File = reader.result;

        var userInfo = {
            id: '1',
            name: $("#t1").val(),
            dateBirth: $("#t2").val(),
            avatar: base64File,
            email: 'admin'
        }

        $.ajax({
            url: "/user-info/save",
            type: "POST",
            contentType: "application/json",
            headers: {
                'Authorize': "Bearer " + localStorage.getItem('token')
            },
            data: JSON.stringify(userInfo),
            success: function (data) {
                alert("+");
            },
            error: function (xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
            }
        });



    }


}




