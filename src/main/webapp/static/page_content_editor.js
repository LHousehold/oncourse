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
    var effected_grids = get_effected_grids(grid);

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

function remove_file_from_grid(ev) {
    var tile = ev.target.parentElement;
    var grid_id = tile.attributes.getNamedItem("grid_id").nodeValue;

    tile.parentElement.removeChild(tile);

    enable_grids(document.getElementById(grid_id));
}

function add_tile(grid_id, cp_table_id) {
    // tile for content
    var tile = document.createElement("div");
    tile.classList.add("grid_tile");
    tile.classList.add("pos_" + grid_id);
    tile.setAttribute("grid_id", grid_id);
    tile.setAttribute("cp_table_id", cp_table_id);

    // x button to remove content
    var remove_button = document.createElement("div");
    remove_button.classList.add("rem_button");
    remove_button.onclick = remove_file_from_grid;
    tile.appendChild(remove_button);

    document.getElementById("drag_grid").appendChild(tile);
}

function location_db_or_local(loc) {
    var db_format =     ["TL",  "TOP",   "TR",   "LEFT",    "FULL", "RIGHT",    "BL",   "BOTTOM",   "BR"];
    var local_format =  ["TL",  "T",     "TR",   "L",       "M",    "R",        "BL",   "B",        "BR"];

    var i = 0;

    // check if db format
    while ( i < db_format.length ) {
        if (db_format[i] == loc) {
            return local_format[i];
        }
        i += 1;
    }

    i = 0;
    // check if local format
    while ( i < local_format.length ) {
        if (local_format[i] == loc) {
            return db_format[i];
        }
        i += 1;
    }

}

function save_page (file) {
    $.post( "page_edit_save.xhtml", { cpid: '4'}, function( data ) {
        document.getElementById("file_menu").innerHTML = data;
    });
}

// this version of the function is needed for initialization
function initial_tile_add (grid_id, cp_table_id) {
    var loc_id = location_db_or_local(grid_id);

    var grid = document.getElementById(loc_id);

    disable_grids(grid);

    add_tile(grid.id, cp_table_id);
}

function add_file_to_grid (grid, file) {
    disable_grids(grid);

    add_tile(grid.id, 1); // 1 is a test value
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
