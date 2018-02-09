function store_ajax(encloseid, enclosename, startPosition, transoprt, viewpoint) {
    console.log("aa"+ viewpoint)
    $.ajax({
        type: "POST",
        url: "http://140.121.197.130:8050/WT/storeshareAjaxServlet",
        data: {
            encloseid: encloseid,
            enclosename: enclosename,
            startPosition: startPosition,
            viewpoint: viewpoint,
            transoprt: transoprt            
        },
        dataType: "json",
        success: function (response) {
           // delete_DB();////////
        },
        error: function (xhr, ajaxOptions, thrownError) {

        }

    });
}