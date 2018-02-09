function correctTourbasket(schedule, data, latitude,longitude) {

    $.ajax({
        type: "POST",
        url: "http://140.121.197.130:8050/WT/correctTourbasket", 
        data: {
            schedule:schedule,
            data: data,
            lat: latitude,
            lon: longitude
        }, 
        dataType: "json",
        success: function (response) {
            console.log(response);
            var i=0;
            for(;i<response.length;i++){
                  localStorage.setItem("tourBasketkey"+i,JSON.stringify(response[i]));
            }
            show();
     },
        error: function (xhr, ajaxOptions, thrownError) {
            //alert(xhr.status + "\n" + thrownError);
        }

    });

}
