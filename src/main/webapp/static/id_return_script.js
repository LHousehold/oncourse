$(document).ready(function() {
    // All the Javascript that id_return.xhtml will need

    $("#id_datas").ready(function(e){

        $(".secnum").each(function(i) {
            var index;
            var id;
            if ($(this).attr("data-section-id") == -1) {
                index = $(this).attr("data-section");
                id = $(".id_data[data-index='" + index + "']").attr("data-id");
                if (id == null) {
                    id = $(".id_data[data-index='" + index + ".0']").attr("data-id");
                }
                $(this).attr("data-section-id",id);
            }
        });

        $(".original_cp_data")[0].value = get_data();
    });


    var get_data = function() {
        // need to collect all data from the course package
        var temp_data;
        var cp_data;
        var course_name = $("#edit_course_name").html();
        var cpid = $("#sections_content").attr("data-cpid");
        var cp_data = "{'cpid':'" + cpid + "', 'course_name':'" + course_name + "','sections':[";
        $(".secblock").each(function(i){
            var secnum = $(this).find(".secnum");
            var section_id = secnum.attr("data-section-id");
            var page_number = secnum.attr("data-pagenumber");
            var section_index = secnum.attr("data-section");
            var section_type = "";
            var section_name = "";
            if ($(this).hasClass("section_block")) {
                section_name = $(this).find(".sectitle").html();
                section_type = "section";
            }
            else {
                section_name = $(this).find(".subsectitle").html();
                section_type = "subsection";
            }
            temp_data = "{'id':'" + section_id + "', 'index':'" + section_index + "', 'name':'" + section_name + "', 'type':'" + section_type + "', 'page':'" + page_number + "'},";
            cp_data = cp_data + temp_data;
        });

        cp_data = cp_data.substring(0, cp_data.length - 1);
        cp_data = cp_data + "]}";

        return cp_data;
    };

})
