$(document).ready(function() {
    // All the Javascript that coursepackageeditor.xhtml will need

    $("#save_cp").click(function(e) {
        var cp_data = get_data();

        $(".save_cp_data")[0].value = cp_data;
        $(".save_cp_command").click();
        // clicking save_cp_command will send the original data and the new data to the database, to be compared and reflect changes
        // assume this works: update original data to new data for future comparison

        setTimeout(function(e) {
            var cpid = $("#sections_content").attr("data-cpid");
            $("#id_values").load("id_return.xhtml", {"cpid": cpid});
        }, 100);

    });

    $("#upload_button").click(function(e) {
        var upload_top = $("#upload_top");

        var visible = upload_top.attr("data-visible");

        if (visible == "false") {
            upload_top.css("visibility","visible");
            upload_top.css("height","auto");
            upload_top.attr("data-visible","true");
        }
        else {
            upload_top.css("visibility","hidden");
            upload_top.css("height","0px");
            upload_top.attr("data-visible","false");
        }

    });

    $("#sections_content").ready(function(e) {
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
            console.log(page_number);
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

    $("#sections_content").on("focus", ".edit", function(e){
        setTimeout(function(){
            // ensure focus is complete
            // this is because with no delay whole page is selected
            if ($(':focus').hasClass("edit")) {
                document.execCommand('selectAll',false,null);
            }
            else {
                setTimeout(function(){
                    // for slower browsers, give a little extra wait time
                    if ($(':focus').hasClass("edit")) {
                        document.execCommand('selectAll',false,null);
                    }
                }, 30);
            }
        }, 20);
    });

    $("#sections_content").on("keypress", ".edit", function(e){
        if (e.which === 13) {
            e.preventDefault();
        }
    });

    $("#sections_content").on("click", ".edit_section", function(e){
        var pagenumber = $(this).closest(".secblock").find(".secnum").attr("data-pagenumber");
        var cpid = $("#sections_content").attr("data-cpid");

        $("#coursepackage_content").load("pageeditor.xhtml", {"pagenumber":pagenumber, "cpid": cpid});
    });

    $("#sections_content").on("click", ".add_subsection", function(e){
        var pagenum = parseInt($(this).closest(".secblock").find(".secnum").attr("data-pagenumber"),10) + 1;
        var newsec = $('<div class="subsection_block secblock"> \
                <h4 class="secnum subsect" data-section-type="subsection" data-section-id="-1" data-section="0" data-pagenumber="' + pagenum + '">0</h4> \
                <h4 class="subsectitle edit" contenteditable="true">New Subsection</h4> \
                <div class="subsection_btns"> \
                    <input type="text" class="form-control section_pagenumber" value="' + pagenum + '" /> \
                    <button class="btn btn-default remove_subsection" title="Remove Subsection"><span class="glyphicon glyphicon-remove"></span></button> \
                    <button class="btn btn-default edit_section" title="Edit Subsection"><span class="glyphicon glyphicon-list-alt"></span></button> \
                    <button class="btn btn-default add_subsection" title="Add Subsection"><span class="glyphicon glyphicon-plus"></span></button> \
                </div> \
            </div>').insertAfter($(this).closest(".secblock"));
        refresh_indices();
        newsec.find(".edit").focus();
    });

    $("#sections_content").on("click", ".add_section", function(e){
        // var pagenum = parseInt($(this).closest(".secblock").find(".secnum").attr("data-pagenumber"),10) + 1;
        var pagenum = 1;
        var newsec = $('<div class="section_row"> \
            <div class="section_block secblock"> \
                <h3 class="secnum sect" data-section-type="section" data-section-id="-1" data-section="0" data-pagenumber="' + pagenum + '">0.</h3> \
                <h3 class="sectitle edit" contenteditable="true">New Section</h3> \
                <div class="section_btns"> \
                    <input type="text" class="form-control section_pagenumber" value="' + pagenum + '" /> \
                    <button class="btn btn-default remove_section" title="Remove Section"><span class="glyphicon glyphicon-remove"></span></button> \
                    <button class="btn btn-default edit_section" title="Edit Section"><span class="glyphicon glyphicon-list-alt"></span></button> \
                    <button class="btn btn-default add_subsection" title="Add Subsection"><span class="glyphicon glyphicon-plus"></span></button> \
                </div> \
            </div> \
            <button class="btn btn-default btn-block add_section" title="Add Section"><span class="glyphicon glyphicon-plus"></span></button> \
        </div>').insertAfter($(this).closest(".section_row")).focus();
        refresh_indices();
        newsec.find(".edit").focus();
    });

    $("#sections_content").on("click", ".remove_section", function(e){
        if ($("#sections_content").find(".section_row").length == 1) {
            alert("Cannot remove only section");
            return;
        }
        var r = confirm("Are you absolutely sure you want to remove this section?\n\nAll contained pages will be deleted.");
        if (r) {
            $(this).closest(".section_row").remove();
        }

        refresh_indices();
    });

    $("#sections_content").on("click", ".remove_subsection", function(e){
        var r = confirm("Are you sure you want to remove this subsection?\n\nAll contained pages will be deleted.");
        if (r) {
            $(this).closest(".secblock").remove();
        }

        refresh_indices();
    });

    var refresh_indices = function() {
        $(".section_row").each(function(i) {
            var sect = $(this).find(".sect");
            sect.html(i+1 + ".");
            sect.attr("data-section",i+1);

            subsects = $(this).find(".subsect");
            subsects.each(function(j) {
                $(this).html((i+1) + "." + (j+1));
                $(this).attr("data-section",(i+1) + "." + (j+1))
            });
        });
        // refresh_pagenumbers();
    }

    $("#sections_content").on("change", ".section_pagenumber", function(e){
        var new_value = $(this).val();
        $(this).closest(".secblock").find(".secnum").attr("data-pagenumber",new_value);
    });

})
