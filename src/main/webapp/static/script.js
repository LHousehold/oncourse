$(document).ready(function() {

  $('.navbar a').click(function(e){
    $('.navbar li.active').removeClass('active');
    var $this = $(this);
    if (!$this.hasClass('active')) {
      $this.parent().addClass('active');
    }
    e.preventDefault();
    return false;
  });

  $('.sidebar_top a').click(function(e){
    $('.sidebar_top a.active').removeClass('active');
    var $this = $(this);
    $this.addClass('active');
    e.preventDefault();
    return false;
  });

  $('.navbar-brand').click(function(e){
    var $this = $(this);
    
    var $navbar = $this.parents(".navbar");
    var $active = $navbar.find(".active");
    var $default_active = $navbar.find(".default_active");

    $active.removeClass('active');
    $default_active.addClass('active');

    e.preventDefault();
    return false;
  });

  $('.course_menu_item').click(function(e){
    var $this = $(this);

    var course_code = $this.attr("data-course-code");

    var $placeholder = $("#coursepackage_placeholder");

    $placeholder.load("coursepackage.xhtml", {"coursecode":course_code});

  });

})
