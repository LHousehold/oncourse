$(document).ready(function() {
    // All the Javascript that coursepackageeditor.xhtml will need

    $(".edit").focus(function(e){
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

    $(".edit").keypress(function(e){
        if (e.which === 13) {
            e.preventDefault();
        }
    });

    // $(".add_subsection").click(function(e){
    //     var new_el = add_subsection($(this));
    //     var add_subsection_btn = new_el.find(".add_subsection");
    //     console.log("Button: " + add_subsection_btn);
    //     add_subsection_btn.bind("click", function() {
    //         add_subsection(add_subsection_btn);
    //     });
    // });

    $("#sections_content").on("click", ".add_subsection", function(e){
        return $('<div class="subsection_block secblock" data-section="1.1"> \
<h4 class="secnum">1.1</h4> \
<h4 class="subsectitle edit" contenteditable="true">New Subsection</h4> \
<div class="subsection_btns"> \
<button class="btn btn-default remove_subsection" title="Remove Subsection"><span class="glyphicon glyphicon-remove"></span></button> \
<button class="btn btn-default edit_subsection" title="Edit Subsection"><span class="glyphicon glyphicon-list-alt"></span></button> \
<button class="btn btn-default add_subsection" title="Add Subsection"><span class="glyphicon glyphicon-plus"></span></button> \
<div> \
<div>').insertAfter($(this).closest(".secblock"));
    });

    $("#sections_content").on("click", ".add_section", function(e){
        return $('<div class="section_row"> \
            <div class="section_block secblock" data-section="1"> \
<h3 class="secnum">1.</h3> \
<h3 class="sectitle edit" contenteditable="true">JSP Overview</h3> \
<div class="section_btns"> \
<button class="btn btn-default remove_section" title="Remove Section"><span class="glyphicon glyphicon-remove"></span></button> \
<button class="btn btn-default edit_section" title="Edit Section"><span class="glyphicon glyphicon-list-alt"></span></button> \
<button class="btn btn-default add_subsection" title="Add Subsection"><span class="glyphicon glyphicon-plus"></span></button> \
</div> \
</div> \
<button class="btn btn-default btn-block add_section" title="Add Section"><span class="glyphicon glyphicon-plus"></span></button> \
</div>').insertAfter($(this).closest(".section_row"));
    });

    $("#sections_content").on("click", ".remove_section", function(e){
        var r = confirm("Are you absolutely sure you want to remove this section?");
        if (r) {
            $(this).closest(".section_row").remove();
        }
    });

    $("#sections_content").on("click", ".remove_subsection", function(e){
        var r = confirm("Are you sure you want to remove this subsection?");
        if (r) {
            $(this).closest(".secblock").remove();
        }
    });

})
