function load_page (cpid, page) {

    $("#page_content").load("grid.xhtml",
            {
                "cpid": cpid,
                "pagenumber": page
            });

    document.getElementById("nav_page_number").setAttribute("data-cpid", cpid);
    document.getElementById("nav_page_number").setAttribute("data-page", page);

    $('#nav_page_number').text("Page " + page);
}

$(document).ready(function() {
    // All the Javascript that coursepackage.xhtml will need

    $('.left_arrow').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page = $("#nav_page_number").attr("data-page");
        var cpid = $("#nav_page_number").attr("data-cpid");

        var page = parseInt(page, 10) - 1;

        if (page < 1) {
            // if on page 1, do nothing
            return;
        }

        load_page(cpid, page);
    });

    $('.right_arrow').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page = $("#nav_page_number").attr("data-page");
        var cpid = $("#nav_page_number").attr("data-cpid");

        var page = parseInt(page, 10) + 1;

        //if (page < 1) {
        //    // if on page 1, do nothing
        //    return;
        //}

        load_page(cpid, page);
    });

})
