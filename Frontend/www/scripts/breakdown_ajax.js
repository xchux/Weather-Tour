function breakdown_ajax(date, arrivalTime, place_id) {

    $.ajax({
        type: "POST",
        url: "http://140.121.197.130:8050/WT/breakdownAjaxServlet", 
        data: {
            date: date,
            arrivalTime: arrivalTime,
            place_id: place_id
        }, 
        dataType: "json",
        success: function (response) {
           // console.log(response);
            localStorage.setItem("breakdown_place", response.place);
            localStorage.setItem("breakdown_weather", response.weather);
            localStorage.setItem("breakdown_img", response.img.toString());
            localStorage.setItem("breakdown_play", JSON.stringify(response.play));
            localStorage.setItem("breakdown_food", JSON.stringify(response.food));
            localStorage.setItem("breakdown_activity", JSON.stringify(response.activity));
        },
        error: function (xhr, ajaxOptions, thrownError) {
            //alert(xhr.status + "\n" + thrownError);
        }

    });

}
