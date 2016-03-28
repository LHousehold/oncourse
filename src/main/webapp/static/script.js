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

  $('.option_menu_item').click(function(e){
      $("#coursepackage_content").load("home.xhtml");
  });

  $('.course_menu_item').click(function(e){
      var course_code = $(this).attr("data-course-code");
      var cpid = $(this).attr("data-cpid");

      $("#coursepackage_content").load("coursepackage.xhtml", {"coursecode":course_code, "cpid": cpid});
  });

  $('.course_edit_item_new').click(function(e){
      var course_code = prompt("Please enter a Course Code for your new course", "APS100");
      if (course_code != null) {
          var resource = "new_cp.xhtml" +
                      "?course_code=" + course_code;

          console.log(resource);

          get_request(resource, new_cp_callback);
      }
  });


  function get_request(resource, func) {
      var req = new XMLHttpRequest();
      req.onload = func;
      req.open("GET", resource, true);
      req.send();
  }

  function new_cp_callback(data) {
      var resp = data.currentTarget.responseText;
      var parser = new DOMParser();
      var htmlDoc = parser.parseFromString(resp, "text/html");
      var result = htmlDoc.firstElementChild.innerText;

      var result_split = result.split(":");

      var cpid = result_split[0];
      var course_code = result_split[1];

      if (result != 0) {
          var html = '<a href="#" class="list-group-item course_menu_item" data-course-code="' + course_code + '" data-cpid="' + cpid + '">' + course_code + '</a>';
          $("#view_course_list").append(html);
          var html2 = '<a href="#" class="list-group-item course_edit_item" data-course-code="' + course_code + '" data-cpid="' + cpid + '">' + course_code + '</a>';
          $("#edit_course_list").append(html2);
      }
      else
          console.log("no");
  }




  $('.course_edit_item').click(function(e){
      var course_code = $(this).attr("data-course-code");
      var cpid = $(this).attr("data-cpid");

      $("#coursepackage_content").load("coursepackageeditor.xhtml", {"coursecode":course_code, "cpid": cpid});
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
