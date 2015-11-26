$(document).ready(function() {
    // All the Javascript that coursepackage.xhtml will need

    var load_page = function(course_code, page_number){
        $("#page_content").load("cppage.xhtml", {"coursecode": course_code, "pagenumber": page_number});

        $('#nav_page_number').text("Page " + page_number);
    };

    $('#page_content').ready(function(e){
        // When course package loads, load first page
        var course_code = $(".coursepackage_title").attr("data-course-code");
        var page_number = 1;

        load_page(course_code,page_number);
    });

    $('.section_reference').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page_number = $(this).attr("data-page-number");
        //Might need to make this....
        var course_code = $(".coursepackage_title").attr("data-course-code");

        load_page(course_code,page_number);
    });

    $('.left_arrow').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page_number = $(".cppage_top").attr("data-page-number");
        var course_code = $(".coursepackage_title").attr("data-course-code");

        var page_number = parseInt(page_number,10) - 1;

        if (page_number < 1) {
            // if on page 1, do nothing
            return;
        }

        load_page(course_code,page_number);
    });

    $('.right_arrow').click(function(e){
        e.preventDefault();
        // go get current page number and course code
        var page_number = $(".cppage_top").attr("data-page-number");
        var course_code = $(".coursepackage_title").attr("data-course-code");

        var page_number = parseInt(page_number,10) + 1;

        var max_pages = 100; // actual number will be stored

        if (page_number > max_pages) {
            // if on last page, do nothing
            return;
        }

        load_page(course_code,page_number);
    });

})
