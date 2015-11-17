$(document).ready(function() {
    // All the Javascript that coursepackage.xhtml will need

    $('#page_content').ready(function(e){
        // When course package loads, load first page
        var course_code = $(".coursepackage_title").attr("data-course-code");
        var page_number = 1;

        load_page(course_code,page_number);
    });

    var load_page = function(course_code, page_number){
        $("#page_content").load("cppage.xhtml", {"coursecode": course_code, "pagenumber": page_number});
    };

})
