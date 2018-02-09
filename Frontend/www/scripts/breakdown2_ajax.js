function do_breakdown2ajax(sub_place_id) {
    $.ajax({
        type: "POST", 
        url: "http://140.121.197.130:8050/WT/breakdown2AjaxServlet", 
        data: {
            sub_place_id: sub_place_id
        }, 
        dataType: "json",
        success: function(response) {
            localStorage.setItem("breakdown2_place", response.place);
            localStorage.setItem("breakdown2_sub_place", response.sub_place);
            localStorage.setItem("breakdown2_img", response.img);
        },
        error: function(xhr, ajaxOptions, thrownError) {
           // alert(xhr.status + "\n" + thrownError);
        }
    });

}
