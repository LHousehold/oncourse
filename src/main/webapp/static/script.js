$(document).ready(function() {
    // All top level Javascript. Note that any page elements loaded through Ajax will not be able to access these functions

  $('.navbar a').click(function(e){
    $('.navbar li.active').removeClass('active');
    if (!$(this).hasClass('active')) {
      $(this).parent().addClass('active');
    }
    e.preventDefault();
    return false;
  });

  $('.sidebar_top a').click(function(e){
    $('.sidebar_top a.active').removeClass('active');
    $(this).addClass('active');
    e.preventDefault();
    return false;
  });

  $('.navbar-brand').click(function(e){
    var navbar = $(this).parents(".navbar");
    var active = navbar.find(".active");
    var default_active = navbar.find(".default_active");

    active.removeClass('active');
    default_active.addClass('active');

    e.preventDefault();
    return false;
  });

  $('.course_menu_item').click(function(e){
      var course_code = $(this).attr("data-course-code");
      var cpid = $(this).attr("data-cpid");

      $("#coursepackage_content").load("coursepackage.xhtml", {"coursecode":course_code, "cpid": cpid});
  });

  $('.upload_item').click(function(e){
      $("#coursepackage_content").load("upload.xhtml");
  });

  $(document).keydown(function(e){
      if (e.keyCode == 37) {
          // left key
          $('.left_arrow').click();
      }
      else if (e.keyCode == 39) {
          // right key
          $('.right_arrow').click();
      }
  });

})
