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

function is_disabled_grid (grid) {
    // check if the grid item is disabled
    var disabled = grid.attributes.getNamedItem("grid_disabled");
    if (disabled) {
        return true;
    }
    else {
        return false;
    }
}

function add_file_item_events (el) {
    el.ondragstart = file_item_drag;
}

function add_drag_style (el) {
    if (!is_disabled_grid(el)) {
        el.classList.add("drag_hover");
    }
}

function remove_drag_style (el) {
    el.classList.remove("drag_hover");
}

function get_effected_grids (grid) {
    var tiles = document.querySelectorAll(".grid_tile");
    
    var effected_grids_group = grid.attributes
        .getNamedItem("grid_group")
        .nodeValue
        .split(" ");
    
    // filter out duplicate groups to make sure the right
    // grid elements are effected
    var i = 0;
    while (i < tiles.length) {
        var tile_grid_id = tiles[i].attributes.getNamedItem("grid_id");
        var used_grid = document.getElementById(tile_grid_id.nodeValue);
        var tile_grid_group = used_grid.attributes
            .getNamedItem("grid_group")
            .nodeValue
            .split(" ");
        effected_grids_group = effected_grids_group.filter(function(val) {
            return tile_grid_group.indexOf(val) == -1;
        });
        i += 1;
    }
    
    // get actual elements
    var effected_grids = effected_grids_group.map(function(val) {
       return document.getElementById(val);
    });
    
    return effected_grids;
}

function enable_grids(grid) {
    var id = grid.id;
    
    var effected_grids = document.querySelectorAll("." + id);
    
    var i = 0;
    while (i < effected_grids.length) {
        if (effected_grids[i].attributes.getNamedItem("grid_disabled")) {
            effected_grids[i].attributes.removeNamedItem("grid_disabled");  
            effected_grids[i].classList.remove("grid_disabled");
        }
        i += 1;
    }
}

function disable_grids(grid) {
    var id = grid.id;
    
    var effected_grids = get_effected_grids(grid);
    
    var i = 0;
    while (i < effected_grids.length) {
        if (!effected_grids[i].attributes.getNamedItem("grid_disabled")) {
            effected_grids[i].setAttribute("grid_disabled", null);
            effected_grids[i].classList.add("grid_disabled");
        }
        i += 1;
    }
}

function add_file_to_grid (grid, file) {
    disable_grids(grid);
    var tile = document.createElement("div");
    tile.classList.add("grid_tile");
    tile.classList.add("pos_" + grid.id);
    tile.setAttribute("grid_id", grid.id);
    document.getElementById("drag_grid").appendChild(tile);
}

function grid_drag_enter (ev) {
    add_drag_style(ev.target);
}

function grid_drag_leave (ev) {
    remove_drag_style(ev.target);
}

function grid_allowDrop(ev) {
    ev.preventDefault();
}

function file_item_drag(ev) {
    ev.dataTransfer.setData("id", ev.target.id);
    console.log("drag");
}

function grid_drop(ev) {
    ev.preventDefault();
    var grid = ev.target;
    var id = ev.dataTransfer.getData("id");
    var file = document.getElementById(id);
    
    remove_drag_style(grid);

    if (is_disabled_grid(grid)) {
        console.log("disabled");
        return;
    }
    else {
        add_file_to_grid(grid, file);   
    }    
}
