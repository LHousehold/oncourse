function get_elms (query) {
    return document.querySelectorAll(query);
}

function get_request(resource, func) {
    var req = new XMLHttpRequest();
    req.onload = func;
    req.open("GET", resource, true);
    req.send();
}

function initLogin() {
    var username_box = get_elms("#username_enter")[0];
    var password_box = get_elms("#password_enter")[0];

    username_box.onkeyup = detectEnter;
    password_box.onkeyup = detectEnter;
}

function is_valid_login(data) {
    var resp = data.currentTarget.responseText;
    var parser = new DOMParser();
    var htmlDoc = parser.parseFromString(resp, "text/html");
    var result = htmlDoc.firstElementChild.innerText;

    if (result != 0) {
        var indxpg = "index.xhtml?uid=" +
                        result;
        window.location.replace(indxpg);
    }
    else
        alert("Incorrect username or password. Please try again");
}

function login() {
    var username_box = get_elms("#username_enter")[0];
    var password_box = get_elms("#password_enter")[0];

    var username = username_box.value;
    var password = password_box.value;

    var resource = "login_helper.xhtml" +
                "?username=" + username +
                "&password=" + password;

    console.log(resource);

    get_request(resource, is_valid_login);
}

function detectEnter(e) {
    if (e.keyCode == 13) { // enter {
        login();
    }
}
