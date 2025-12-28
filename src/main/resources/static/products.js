
var stomp = null;

// подключаемся к серверу по окончании загрузки страницы
window.onload = function() {
    connect();
};

function connect() {
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe('/topic/products', function (product) {
            renderItem(product);
        });
    });
}

// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        if ($(this).attr('action') === undefined) {
            e.preventDefault();
        }
    });

    $( "#send" ).click(function() {
        sendContent();
        $("#title").val('');
        $("#price").val('');
    });
});

// отправка сообщения на сервер
function sendContent() {
    stomp.send("/app/products", {}, JSON.stringify({
        'title': $("#title").val(),
        'price': $("#price").val()
    }));
}

// рендер сообщения, полученного от сервера
function renderItem(productJson) {
    var product = JSON.parse(productJson.body);

    var html = "<tr>" +
        "<td>" + product.title + "</td>" +
        "<td>" + product.price + "</td>" +
        "<td><a href='/products/" + product.id + "/bucket'>Add to bucket</a></td>";

    var isManager = $("h3:contains('Add New Product')").length > 0;

    if (isManager) {
        html += "<td>" +
            "<form action='/products/delete/" + product.id + "' method='post'>" +
            "<button type='submit' style='color: red;'>Remove</button>" +
            "</form>" +
            "</td>";
    }

    html += "</tr>";

    $("#table").append(html);
}
