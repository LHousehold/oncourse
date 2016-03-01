function set_drag_listener () {
    var grid_items = document.querySelectorAll(".grid_item");
    
    var file_items = document.querySelectorAll(".file_item");
    
    var i = 0;
    while (i < grid_items.length) {
        grid_items[i].ondrop = drop;
        grid_items[i].ondragover = allowDrop;
        grid_items[i].ondragenter = drag_enter;
        grid_items[i].ondragleave = drag_leave;
        i += 1;
    }
    
    i = 0;
    while (i < file_items.length) {
        file_items[i].ondragstart = drag;
        i += 1;
    }
}

function add_drag_style (el) {
    el.classList.add("drag_hover");
}

function remove_drag_style (el) {
    el.classList.remove("drag_hover");
}

function drag_enter (ev) {
    add_drag_style(ev.target);
}

function drag_leave (ev) {
    remove_drag_style(ev.target);
}

//function drag_end (ev) {
//    remove_drag_style(ev.target);
//}

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("id", ev.target.id);
    console.log("drag");
}

function drop(ev) {
    ev.preventDefault();
    var id = ev.dataTransfer.getData("id");
    var file = document.getElementById(id);
    console.log(file);
    remove_drag_style(ev.target);
    //ev.target.appendChild(document.getElementById(data));
}