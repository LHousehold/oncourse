$(document).ready(function() {
    // All the Javascript that coursepackage.xhtml will need

    var load_page = function(cpid, page_number){
        cpid = parseInt(cpid,10);
        page_number = parseInt(page_number,10);
        $("#page_content").load("cppage.xhtml", {"cpid": cpid, "pagenumber": page_number});

        $('#nav_page_number').text("Page " + page_number);
    };

    var set_page_height= function(){
        var page_content = document.querySelector('#page_content');

        if(page_content){
            var jq_page_content = $('#page_content');

            var heightOffset = jq_page_content.offset().top;
            var windowHeight = window.innerHeight;

            var height = windowHeight - heightOffset - 55;

            page_content.style.height = height + "px";
        }
    };

    window.onresize = set_page_height;

    $('#page_content').ready(function(e){
        // When course package loads, load first page
        var cpid = $(".coursepackage_title").attr("data-cpid");
        var page_number = 1;

        load_page(cpid,page_number);

        //dynamically setting height
        set_page_height();
    });

    $('.section_reference').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page_number = $(this).attr("data-page-number");
        //Might need to make this....
        var cpid = $(".coursepackage_title").attr("data-cpid");

        load_page(cpid,page_number);
    });

    $('.left_arrow').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page_number = $("#cppage_top").attr("data-page-number");
        var cpid = $(".coursepackage_title").attr("data-cpid");

        var page_number = parseInt(page_number,10) - 1;

        if (page_number < 1) {
            // if on page 1, do nothing
            return;
        }

        load_page(cpid,page_number);
    });

    $('.right_arrow').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page_number = $("#cppage_top").attr("data-page-number");
        var cpid = $(".coursepackage_title").attr("data-cpid");

        var page_number = parseInt(page_number,10) + 1;

        var max_pages = 100; // actual number will be stored

        if (page_number > max_pages) {
            // if on last page, do nothing
            return;
        }

        load_page(cpid,page_number);
    });

})
