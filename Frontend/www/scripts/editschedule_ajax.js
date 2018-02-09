var schedule_obj = "", latitude, longitude;

 function get_schedule() {
     schedule_obj = "";
     var $viewpoint = $(".tableRowspan");

     $viewpoint.each(function() {
         var play_time, sportid, url;
         play_time = $(this).attr('rowspan');
         sportid = $(this).attr('spotid');
         var bg = $(this).css('background-image');
         bg = bg.replace('url(', '').replace(')', '').replace(/\"/gi, "");
         url = bg;
         if (schedule_obj != "")
             schedule_obj += ",";
         schedule_obj += '{"id":"' + sportid + '","playtime":"' + play_time + '","url":"' + url + '"}';
         console.log(schedule_obj);
     })
     schedule_obj = "[" + schedule_obj + "]";
     

 }



 function editschedule_ajax(latitude, longitude) {
     console.log(latitude + "," + longitude);
     get_schedule();
     $.ajax({

         type: "POST", // 指定http參數傳輸格式為POST

         url: "http://140.121.197.130:8050/WT/EditscheduleServlet", // 請求目標的url，可在url內加上GET參數，如
         // www.xxxx.com?xx=yy&xxx=yyy

         data: {
             latitude: latitude,
             longitude: longitude,
             starttime: startTime,
             transport: transport,
             schedule: schedule_obj
         },
         dataType: "json",
         success: function (response) {
             console.log(response);
           delete_DB();
             for(var i=0;i<response.length;i++){
                 var scheduleObj = '{ "departureTime":"", "arrivalTime":"", "playTime":"", "spotName":"", "weatherIcon":"",  "transportation":"",  "startPosition":"", "spotID":"", "spotPicture":""}';
                    
                    scheduleObj = JSON.parse(scheduleObj);
                    scheduleObj.departureTime = response[i].departureTime;
                    scheduleObj.arrivalTime = response[i].arrivalTime;
                    scheduleObj.playTime = response[i].playTime;
                    scheduleObj.spotName = response[i].spotName;
                    scheduleObj.weatherIcon = response[i].weatherIcon;
                    scheduleObj.transportation = response[i].transportation;
                    scheduleObj.startPosition = response[i].startPosition;
                    scheduleObj.spotID = response[i].spotID;
                    scheduleObj.spotPicture = response[i].spotPicture;
                    localStorage.setItem('scheduleKey' + i, JSON.stringify(scheduleObj));                   
                    localStorage.setItem("scheduleQuantity", i + 1);
                    console.log(localStorage.getItem('scheduleKey' + i))
             }
           //  $("#scheduleContent").html("");

             lattice = 0;
             //setTimeout(show_view, "2000");
             show_view();
            // console.log(a);
             $("#timeline").css("display", "block");
             $("#blockimg").css("display", "block");
         },

         // Ajax失敗後要執行的function，此例為印出錯誤訊息

         error: function(xhr, ajaxOptions, thrownError) {

             alert(xhr.status + "\n" + thrownError);
         }

     });

 }