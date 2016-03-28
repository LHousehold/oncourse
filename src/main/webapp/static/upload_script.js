$(document).ready(function() {
    // All the Javascript that upload.xhtml will need

    $("#front_select_btn").click(function(e) {
        $(".back_select_btn").click();
    });

    $("#front_upload_btn").click(function(e) {
        $(".back_upload_btn").click();
    });

    $(".back_select_btn").change(function(e) {
        var filepath = e.currentTarget.value.split("\\");
        var filename = filepath[filepath.length-1];
        $("#front_filename").html(filename);
    });

    $("#upload_div").ready(function(e) {
        var cpid = window.parent.$("#sections_content").attr("data-cpid");
        $(".cpid_holder")[0].value = cpid;
    });

})
