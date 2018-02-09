var latitude = 24.801602,
    longitude = 120.966197,
    place_id, points = 50, translate, time, datepicker, list_size = 10, index_num;
var num, section = "", place_index = ""; var response; var data_array;
var in_out_door = ["全室內", "偏室內", "偏室外", "全室外"];
var tag = ["風景區", "觀光工廠", "休憩公園", "海岸風情", "湖岸風情", "觀光夜市", "森林遊樂區", "步道健身", "農牧場", "名勝古蹟", "瀑布", "廟宇建築", "遊樂園區", "博物館", "購物中心"];
var tag_imgs = ["landscape", "factory", "park", "baech", "lake", "night_market", "forest", "Hikers", "farm", "monuments", "waterfall", "temple", "amusement_park", "museum", "mall"];
var weather_box = [0, 9, 6, 5, 0, 6, 5, 7, 8, 6, 4, 2, 3, 3, 2, 3, 3, 1, 1, 0, 2, 2, 7, 4, 3, 1, 1, 1, 1, 3, 1, 0, 0, 0, 3, 1, 0, 0, 0, 5, 5, 4, 3, 8, 5, 6, 7, 4, 2, 2, 2, 1, 1, 6, 4, 3, 2, 1, 2, 0, 0, 0, 2, 1, 0, 0]
var weather_arry = ["雷雨", "陣雨", "陣雨", "短暫陣雨", "多雲", "多雲時陰", "多雲時晴", "晴時多雲", "晴時多雲", "晴天"];
var locate = ["臺北市", "基隆市", "新北市", "宜蘭縣", "新竹縣", "桃園市", "苗栗縣", "臺中市", "彰化縣", "南投縣", "嘉義縣", "雲林縣", "臺南市", "高雄市", "屏東縣", "臺東縣", "花蓮縣"];
var postcode = [
    [100, 116],
    [200, 206],
    [207, 253],
    [260, 272],
    [300, 315],
    [320, 338],
    [350, 369],
    [400, 439],
    [500, 530],
    [540, 558],
    [600, 625],
    [630, 655],
    [700, 745],
    [800, 852],
    [900, 947],
    [950, 966],
    [970, 983]
];
//在網頁加載後，對id=doAjaxBtn的Button設定click的function
$(document).ready(function () {
    index_num = Number(localStorage.getItem("scheduleQuantity"));
    if (!index_num)
        index_num = 0;
    //定位後送資料

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
            localStorage.setItem("latitude", latitude);/////
            localStorage.setItem("longitude", longitude);/////

            index_ajax();
        },
            function (error) {
                switch (error.code) {
                    case error.TIMEOUT:
                        alert('連線逾時');
                        break;
                    case error.POSITION_UNAVAILABLE:
                        alert('無法取得定位');
                        break;
                    case error.PERMISSION_DENIED: // 拒絕
                        alert('請開啟GPS定位功能');
                        break;
                    case error.UNKNOWN_ERROR:
                        alert('無法取得定位，請稍候再試');
                        break;
                }
            });
    }   

    $("#index_submit").click(function () {

        //postcode
        place_index = $("#place_index").val() != "" ? $("#place_index").val() : "";
        //section
        section = $("#section_index").val() != "" ? $("#section_index").val() : "";
        $('#setting').modal('toggle');
        index_ajax();
    });

});
function checkTime(i) {
    return (i < 10) ? "0" + i : i;
};
function set_index_data() {
    if (localStorage.getItem("scheduleQuantity"))
        index_num = localStorage.getItem("scheduleQuantity");
    else
        index_num = 0;
      //console.log(index_num);
    if (index_num > 0) {
        var schedule_obj = JSON.parse(localStorage.getItem("scheduleKey" + (index_num - 1)));
        datepicker = schedule_obj.departureTime.split(" ")[0];
        // console.log(schedule_obj.departureTime);
        var d = new Date(datepicker + " " + schedule_obj.arrivalTime);
        d.setHours(d.getHours() + Number(schedule_obj.playTime));
        time =checkTime( d.getHours()) + ":" + checkTime(d.getMinutes());
        translate = schedule_obj.transportation.slice(7,-4);
        place_id = schedule_obj.spotID;
        points = $("#range").val() * 10;
    } else {
        var dt = new Date();
        datepicker = $("#datepicker_home").val();
        if (datepicker === "") {
            var month = (dt.getMonth() + 1) < 10 ? "0" + (dt.getMonth() + 1) : (dt.getMonth() + 1);
            var day = dt.getDate() < 10 ? "0" + dt.getDate() : dt.getDate();
            datepicker = dt.getFullYear() + "-" + month + "-" + day;
        }
        time = $("#time_home").val();
        if (time === "") {
            var hour = dt.getHours() < 10 ? "0" + dt.getHours() : dt.getHours();
            var min = dt.getMinutes() < 10 ? "0" + dt.getMinutes() : dt.getMinutes();
            time = hour + ":" + min;
        }
        translate = $("#translate_home").val();
        place_id = "-1";
        if ($("#range").val())
            points = $("#range").val() * 10;
    }
    console.log("datepicker: " + datepicker + "time: " + time + "translate: " + translate + "points: " + points + "lat: " + latitude + "lon: " + longitude + "place_id: " + place_id);

}

function index_ajax() {
    set_index_data();
    setTimeout(function () {
        $.ajax({
            type: "POST", // 指定http參數傳輸格式為POST
            url: "http://140.121.197.130:8050/WT/indexServlet", // 請求目標的url，可在url內加上GET參數，如
            // www.xxxx.com?xx=yy&xxx=yyy
            data: {
                datepicker: datepicker,
                time: time,
                translate: translate,
                points: points,
                lat: latitude.toFixed(5),
                lon: longitude.toFixed(5),
                place_id: place_id
            },
            dataType: "json",
            success: function (data) {
                response = "";
                console.log(data.length);
                var str = "";
            
                for (var i = 0; i < data.length; i++) {
                    var j = 0, scheduleNum = Number(localStorage.getItem("scheduleQuantity")), tourbasketNum = Number(localStorage.getItem("spotQuantity")) ;

                    while (j < scheduleNum) {
                       // console.log(data[i]);
                        if (JSON.parse(localStorage.getItem('scheduleKey' + j)).spotID == data[i].id)
                            i++, list_size++, j = 0;
                        else
                            j++;
                    }
                    j = 0;
                    while (j < tourbasketNum) {//旅遊籃重複
                        // console.log(data[i]);
                        if (JSON.parse(localStorage.getItem('tourBasketkey' + j)).spotID == data[i].id)
                            i++, list_size++, j = 0;
                        else
                            j++;
                    }

                    var data_array = data[i];

                    var tag_img = 0;
                    for (; tag_img < 15; tag_img++) {
                        if (data_array.tag == tag[tag_img])
                            break;
                    }
                    
                    if (section != "" && place_index != "") {
                        if ((postcode[place_index][0] <= Number(data_array.postal_code) && Number(data_array.postal_code) <= postcode[place_index][1])) {
                            if (section == tag_img) {
                                if (str != "")
                                    str += ",";
                                str += JSON.stringify(data_array);
                            }
                            else if (section == 15) {//活動
                                if (data_array.activity !== "[]") {
                                    if (str != "")
                                        str += ",";
                                    str += JSON.stringify(data_array);
                                }
                            }
                        }
                    }
                    else if (place_index != "") {//地區
                        if (postcode[place_index][0] <= data_array.postal_code && data_array.postal_code <= postcode[place_index][1]) {
                            if (str != "")
                                str += ",";
                            str += JSON.stringify(data_array);

                        }
                    }
                    else if (section != "") {//類別
                        if (section == tag_img) {
                            if (str != "")
                                str += ",";
                            str += JSON.stringify(data_array);
                        }
                        else if (section == 15) {//活動
                            if (data_array.activity != "[]") {
                                if (str != "")
                                    str += ",";
                                str += JSON.stringify(data_array);

                                
                            }
                        }
                    }
                    else {
                        if (str != "")
                            str += ",";
                        str += JSON.stringify(data_array);

                    }

                }
                //  console.log(str);
                response = JSON.parse("[" + str + "]");
               // console.log(response);
                $("#index_contenr").html("");
                list_size = 10;
                if (list_size > response.length)
                    list_size = response.length;////
                index_list();
            },
            // Ajax失敗後要執行的function，此例為印出錯誤訊息
            error: function (xhr, ajaxOptions, thrownError) {
              //  alert("index" + xhr.status + "\n" + thrownError);
            }
        });
    }, 100);
}
function index_list() {
    var i = list_size > 9 ? list_size - 10 : 0;
    for (i; i < list_size; i++) {

        var data_array = response[i];
        //console.log(data_array);
        var tag_img = 0;
        var postCode = 0;
        var x = 0;
        
        while (postcode[x][0] > Number(data_array.postal_code) || Number(data_array.postal_code) > postcode[x][1]) {
            x++;
        }
       // console.log(x);
        postCode = x;
        for (; tag_img < 15; tag_img++) {
            if (data_array.tag == tag[tag_img])
                break;
        }
        if (section == 15) {//活動
            var obj = JSON.parse(data_array.activity);
            for (var act_num = 0; act_num < obj.length; act_num++) {
                var act = '<div class="spot_div"><div id="bl-main" class="bl-main" myID="' + data_array.id + '"  arrtime="' + data_array.arrival_time + '" postcode="' + locate[postCode] + '"><section style="display:none;"><div class="bl-box"></div><div class="bl-content"></div><span class="bl-icon bl-icon-close"></span></section><section><div class="bl-box spot_type_box"><nobr class="inOutDoor">' + in_out_door[Number(data_array.in_out)] + '</nobr><span class="badge" style="margin-left: 10%;"><img class="spot_type_icon" src="images/' + tag_imgs[tag_img] + '.png" /></span></div><div class="bl-content spot_type_content"><nobr>地名 &nbsp;: &nbsp;</nobr><nobr class="spot_type_content_namecolor">' + data_array.name + '</nobr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<nobr>地區 &nbsp;:</nobr><nobr class="spot_type_content_color">' + locate[postCode] + '</nobr><br><nobr>天氣 &nbsp;: </nobr><nobr class="spot_type_content_color overview">' + weather_arry[weather_box[Number(data_array.weather)]] + '</nobr><nobr style="position:absolute;padding-left:30%;">抵達時間 &nbsp;: </nobr><nobr class="spot_type_content_color"  style="padding-left:55%">' + data_array.arrival_time + '</nobr><br><nobr>類型 &nbsp;: </nobr>&nbsp;<nobr class="spot_type_content_color spot_type_text">' + data_array.tag + '</nobr><nobr style="position:absolute;padding-left:29%;">室內外 &nbsp;: </nobr><nobr class="spot_type_content_color" style="padding-left:50%">' + in_out_door[data_array.in_out] + '</nobr></div><span class="bl-icon bl-icon-close"></span></section></div><img src="images/' + data_array.weather + '.png" class="weather_image"><img src="images/123.png" class="active_image"><p class="activePlaceName">&nbsp;' + data_array.name + '&nbsp;</p><p class="spot_name" style="font-size:21px;">&nbsp' + longText(obj[act_num].name) + '&nbsp </p><img src="images/' + translate + '.png" class="transportation_image"><p class="spot_ID">' + data_array.id + '</p><p class="ultraviolet">' + data_array.uv + '</p><p class="distance">' + data_array.distance + 'km</p><p class="arrive_time">' + data_array.arrival_time + '</p><img src="' + data_array.url + '" class="spot_image"></div></div>';
                $("#index_contenr").append(act);
            }
        }
        else {
            var place = '<div class="spot_div"><div id="bl-main" class="bl-main" myID="' + data_array.id + '"  arrtime="' + data_array.arrival_time + '" postcode="' + locate[postCode] + '"><section style="display:none;"><div class="bl-box"></div><div class="bl-content"></div><span class="bl-icon bl-icon-close"></span></section><section><div class="bl-box spot_type_box"><nobr class="inOutDoor">' + in_out_door[Number(data_array.in_out)] + '</nobr><span class="badge" style="margin-left: 10%;"><img class="spot_type_icon" src="images/' + tag_imgs[tag_img] + '.png" /></span></div><div class="bl-content spot_type_content"><nobr>地名 &nbsp;: &nbsp;</nobr><nobr class="spot_type_content_namecolor">' + data_array.name + '</nobr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<nobr>地區 &nbsp;:</nobr><nobr class="spot_type_content_color">' + locate[postCode] + '</nobr><br><nobr>天氣 &nbsp;: </nobr><nobr class="spot_type_content_color overview">' + weather_arry[weather_box[Number(data_array.weather)]] + '</nobr><nobr style="position:absolute;padding-left:30%;">抵達時間 &nbsp;: </nobr><nobr class="spot_type_content_color"  style="padding-left:55%">' + data_array.arrival_time + '</nobr><br><nobr>類型 &nbsp;: </nobr>&nbsp;<nobr class="spot_type_content_color spot_type_text">' + data_array.tag + '</nobr><nobr style="position:absolute;padding-left:29%;">室內外 &nbsp;: </nobr><nobr class="spot_type_content_color" style="padding-left:50%">' + in_out_door[data_array.in_out] + '</nobr></div><span class="bl-icon bl-icon-close"></span></section></div><img src="images/' + data_array.weather + '.png" class="weather_image">';
            if (data_array.activity != "[]")
                place += '<img src="images/123.png" class="active_image">';
            place += '<p class="spot_name">&nbsp' + data_array.name + '&nbsp </p><img src="images/' + translate + '.png" class="transportation_image"><p class="spot_ID">' + data_array.id + '</p><p class="ultraviolet">' + data_array.uv + '</p><p class="distance">' + data_array.distance + 'km</p><p class="arrive_time">' + data_array.arrival_time + '</p><img src="' + data_array.url + '" class="spot_image"></div></div>';
            $("#index_contenr").append(place);
        }

    }
    init();
    hammer(list_size);
    $("body").click(function (e) {
     
        if ($(e.target).hasClass('bl-main') && !$(e.target).hasClass('spot_type_box')) {
            breakdown_ajax(datepicker, $(e.target).attr("arrtime"), $(e.target).attr("myID"));
            setTimeout(function () { window.location = $('#breakdown').prop('href');console.log(e.target+":cc") }, 200);
        }
    })
    $("#loadingText").css("display", "block");
}

var $window = $(window);
var $document = $(document);
$window.scroll(function () {
    //列表到盡頭
    if ($document.scrollTop() + $window.height() >= $document.height()) {
        list_size += 10;
       
        if (list_size > response.length)
            list_size = response.length;////
        setTimeout(index_list, "1000");
        // index_list();
        //放在顯示完畢後

    }
    //console.log("cc:" + $document.scrollTop());
    //console.log("ff:" + $window.height());
   // console.log("dd:" + $document.height());
    //console.log($document.scrollTop());
    //console.log($window.height());
    //console.log($document.height());

});

function longText(text) {
    /*--------------------------------------------------- 處理過長文字-----------------------------------------------------------*/
    //var text = $(".spot_name").text().split('  ')[0];
    var totalByte = 0;
    var newText = '';
    for (var v = 0; v < text.length; v++) {
        if (totalByte < 19) {
            var ch = text.substring(v, v + 1);
            var textByte = encodeURIComponent(ch).replace(/%[A-F\d]{2}/g, 'U').length;
            totalByte += textByte;
            newText += ch;
        }
        else {
            newText += "..";
            break;
        }
           
    }
    var finalText = newText;
    return finalText;
}