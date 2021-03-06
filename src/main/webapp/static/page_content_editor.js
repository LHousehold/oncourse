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

function delete_from_db (table_id) {
    $.post( "page_edit_remove.xhtml",
        {
            id: parseInt(table_id),
        });
}

function remove_file_from_grid(ev) {
    var tile = ev.target.parentElement;
    var grid_id = tile.attributes.getNamedItem("grid_id").nodeValue;

    tile.parentElement.removeChild(tile);

    var table_id = tile.getAttribute("data_table_id");
    delete_from_db(table_id);

    enable_grids(document.getElementById(grid_id));
}

function insert_media(tile, media_type, source) {
    if (media_type == "PDF"){
        tile.innerHTML = "<iframe class = 'iframeStyle' src='" + source + "'></iframe>";
    }
    else if (media_type == "MP3") {
        tile.innerHTML = "<span class='center_span'></span>" +
            "<audio class='cp_audio' controls><source src='" + source +
            "' type='audio/mpeg'>Your browser does not support the audio element.</audio>";
    }
    else if (media_type == "YOUTUBE") {
        tile.innerHTML = "<iframe class = 'iframeStyle' src='https://www.youtube.com/embed/"
            + source + "'frameborder='0' allowfullscreen></iframe>";
    }
    else if (media_type == "IMAGE"){
        tile.innerHTML = "<span class='center_span'></span><img class='cp_img' src='" + source +
            "' alt='Problem loading Image'>";
    }
    else if (media_type == "MP4"){
        tile.innerHTML = "<span class='center_span'><video class='cp_video' controls><source src='" + source +
            "' type='video/mp4'>Your browser does not support the video element with mp4.</video>";
    }
}

// must call finalize tile after calling this to add id and remove button
function add_tile(grid_id, media_type, source) {
    // tile for content
    var tile = document.createElement("div");
    tile.classList.add("grid_tile");
    tile.classList.add("pos_" + grid_id);
    tile.setAttribute("grid_id", grid_id);

    insert_media(tile, media_type, source);

    document.getElementById("drag_grid").appendChild(tile);

    return tile;
}

function finalize_tile(tile, data_table_id) {
    tile.setAttribute("data_table_id", data_table_id);

    // x button to remove content
    var remove_button = document.createElement("div");
    remove_button.classList.add("rem_button");
    remove_button.onclick = remove_file_from_grid;
    tile.appendChild(remove_button);
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

// this will return the id of the new entry it adds in the course_package table
function save_page (pos, cp_media_type, cp_source, tile) {

    // get all the information for generating the post parameters
    var cp_cpid, cp_pagenumber, cp_media_type, cp_source, cp_location;

    var packegeID = document.getElementById("packegeID");

    // package and page info
    cp_cpid = packegeID.getAttribute("data_cpid");
    cp_pagenumber = packegeID.getAttribute("data_page");

    // layout info (converted to database format
    cp_location = location_db_or_local(pos);

    $.post( "page_edit_save.xhtml",
        {
            cpid: cp_cpid,
            pagenumber: cp_pagenumber,
            media_type: cp_media_type,
            source: cp_source,
            location: cp_location
        },
        function( data ) {
            var parser = new DOMParser();
            var htmlDoc = parser.parseFromString(data, "text/html");
            finalize_tile(tile, htmlDoc.firstElementChild.innerText);
        });
}

// this version of the function is needed for initialization
function initial_tile_add (grid_id, cp_table_id, media_type, source) {
    var loc_id = location_db_or_local(grid_id);

    var grid = document.getElementById(loc_id);

    disable_grids(grid);

    var tile = add_tile(grid.id, media_type, source);

    finalize_tile(tile, cp_table_id);
}

// very hard coded to only take very specific form of url
function getvid(url, pos) {
    var tmp = url.split("?")[1];
    var id = tmp.slice(2, 13);
    
    var source = id;
    var media_type = "YOUTUBE";
    
    var tile = add_tile(pos, media_type, source); // 1 is a test value

    save_page(pos, media_type, source, tile);
}

function urlkeypress(e) {
    if (e.keyCode == 13) { // enter {
        var pos = e.target.getAttribute("data-pos");
        getvid(e.target.value, pos);
        document.body.removeChild(e.target.parentElement);
    }
}

// long winded function to get youtube url to parse
// don't do this kind of code normally kids, its fucking aids
function add_youtubevid(pos) {
    var messagebox = document.createElement("div");
    messagebox.id = "yt_msgbox";
    messagebox.style.opacity = 1;
    messagebox.style.backgroundColor = "#f4f4f4";
    messagebox.style.height = "70px";
    messagebox.style.width= "450px";
    messagebox.style.position = "absolute";
    messagebox.style.top = "300px";
    messagebox.style.left = "400px";
    //messagebox.style.box-shadow = "5px 3px 20px 0 black"
    
    var sp = document.createElement("span");
    sp.classList.add("center_span");
    
    messagebox.appendChild(sp);
    
    //<input id="password_enter" type="text" class="form-control" placeholder="Enter Password" />
    var input = document.createElement("input");
    input.id = "yt_txtbox";
    input.type = "text";
    input.class = "form-control";
    input.placeholder = "Paste a YouTube Video URL";
    input.style.display = "inline-block";
    input.style.width = "95%";
    input.style.margin = "0 auto 0 auto";
    input.style.position = "relative";
    input.style.left = "10px";
    
    input.onkeyup = urlkeypress;
    input.setAttribute("data-pos", pos);
    
    messagebox.appendChild(input);
    
    document.body.appendChild(messagebox);
}

function add_file_to_grid (grid, file) {
    disable_grids(grid);

    if (file.id == "youtube_add") {
        add_youtubevid(grid.id);
        return;
    }

    var media_type = file.getAttribute("data_media_type");
    var source = file.getAttribute("data_media_source");

    var tile = add_tile(grid.id, media_type, source); // 1 is a test value

    save_page(grid.id, media_type, source, tile);
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
