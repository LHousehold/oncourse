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

})