$(document).ready(function() {
    // All the Javascript that coursepackageeditor.xhtml will need

    $("#sections_content").load(function(e) {
        $("#edit_course_name").click();
    });

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
        //$("#coursepackage_content").load("pageeditor.xhtml", {"pagenumber":pagenumber, "cpid": cpid});
        $("#coursepackage_content").load("pageeditor.xhtml");
    });

    $("#sections_content").on("click", ".add_subsection", function(e){
        var newsec = $('<div class="subsection_block secblock"> \
<h4 class="subsecnum" data-section="1">1.1</h4> \
<h4 class="subsectitle edit" contenteditable="true">New Subsection</h4> \
<div class="subsection_btns"> \
<button class="btn btn-default remove_subsection" title="Remove Subsection"><span class="glyphicon glyphicon-remove"></span></button> \
<button class="btn btn-default edit_subsection" title="Edit Subsection"><span class="glyphicon glyphicon-list-alt"></span></button> \
<button class="btn btn-default add_subsection" title="Add Subsection"><span class="glyphicon glyphicon-plus"></span></button> \
</div> \
</div>').insertAfter($(this).closest(".secblock"));
        refresh_indices();
        newsec.find(".edit").focus();
    });

    $("#sections_content").on("click", ".add_section", function(e){
        var newsec = $('<div class="section_row"> \
<div class="section_block secblock"> \
<h3 class="secnum" data-section="1">1.</h3> \
<h3 class="sectitle edit" contenteditable="true">New Section</h3> \
<div class="section_btns"> \
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

    function refresh_indices() {
        $(".section_row").each(function(i) {
            var secnum = $(this).find(".secnum");
            secnum.html(i+1 + ".");
            secnum.attr("data-section",i+1);
            $(this).find(".subsecnum").each(function(j) {
                var subsecnum = $(this);
                subsecnum.html((i+1) + "." + (j+1));
                subsecnum.attr("data-section",(i+1) + "." + (j+1))
            });
        });
    }

    $(".upload_button").click(function(e) {
        $(".upload_form_file").click();
    });

    $(".upload_form_file").change(function(e) {
        $(".upload_form").submit();
    });

    $(".upload_form").submit(function(e) {
        e.preventDefault();
        alert("Upload Successful!");
    });

})
