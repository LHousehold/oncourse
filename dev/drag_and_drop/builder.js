function set_drag_listener () {
    var grid_items = document.querySelectorAll(".grid_item");
    
    var file_items = document.querySelectorAll(".file_item");
    
    // add event listeners for all the static elements
    var i = 0;
    while (i < grid_items.length) {
        grid_items[i].ondrop = grid_drop;
        grid_items[i].ondragover = grid_allowDrop;
        grid_items[i].ondragenter = grid_drag_enter;
        grid_items[i].ondragleave = grid_drag_leave;
        i += 1;
    }
    
    // add event listeners for dynamic elements (file_items)
    i = 0;
    while (i < file_items.length) {
        add_file_item_events(file_items[i]);
        i += 1;
    }
}

function add_file_item_events (el) {
    el.ondragstart = file_item_drag;
}

function add_drag_style (el) {
    el.classList.add("drag_hover");
}

function remove_drag_style (el) {
    el.classList.remove("drag_hover");
}

function grid_drag_enter (ev) {
    add_drag_style(ev.target);
}

function grid_drag_leave (ev) {
    remove_drag_style(ev.target);
}

//function drag_end (ev) {
//    remove_drag_style(ev.target);
//}

function grid_allowDrop(ev) {
    ev.preventDefault();
}

function file_item_drag(ev) {
    ev.dataTransfer.setData("id", ev.target.id);
    console.log("drag");
}

function grid_drop(ev) {
    ev.preventDefault();
    var id = ev.dataTransfer.getData("id");
    var file = document.getElementById(id);
    console.log(file);
    remove_drag_style(ev.target);
    //ev.target.appendChild(document.getElementById(data));
}