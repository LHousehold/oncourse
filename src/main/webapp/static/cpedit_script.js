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

})
