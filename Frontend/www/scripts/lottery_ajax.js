var latitude = 24.9682669,
    longitude = 121.1887079,
    place_id, points = 50, translate, time, datepicker, lottery_array, lottery_i, random_num;
var section = "", place_home = "";
var weather_backgound = [0, 2, 3, 1, 0, 3, 1, 3, 2, 2, 1, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 3, 1, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 2, 3, 1, 2, 3, 3, 2, 1, 0, 0, 0, 0, 0, 3, 1, 0, 0, 0, 4, 4, 0, 0, 0, 4, 0, 0]
var weather_src = ["images/lottertrainyday.gif", "images/lotterycloudydya.gif", "images/lotterysunday.gif", "images/clouds.gif", "images/thunderstorm.gif"];
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
var lottery_num;
var data_array;
//在網頁加載後，對id=doAjaxBtn的Button設定click的function

$(document).ready(function () {
    $("body").click(function (e) {
        if ($(e.target).hasClass('bl-main') && !$(e.target).hasClass('spot_type_box')) {

            console.log(datepicker + "  " + $(e.target).attr("arrtime") + "  " + $(e.target).attr("myID"));
            breakdown_ajax(datepicker, $(e.target).attr("arrtime"), $(e.target).attr("myID"));

            setTimeout(function () { window.location = $('#breakdown').prop('href'); }, 100);
        }
    });
    lottery_num = Number(localStorage.getItem("scheduleQuantity"));
    if (!lottery_num)
        lottery_num = 0;
    lotery_ajax();
    $("#lottery_submit").click(function () {
        //postcode
        place_home = $("#place_home").val() != "" ? $("#place_home").val() : "";
        //section
        section = $("#section_home").val() != "" ? $("#section_home").val() : "";
        $('#setting').modal('toggle');

        lotery_ajax();
    });

});

function checkTime(i) {
    return (i < 10) ? "0" + i : i;
};



function set_lottery_data() {

    //console.log(index_num);
    if (lottery_num > 0) {
        var schedule_obj = JSON.parse(localStorage.getItem("scheduleKey" + (lottery_num - 1)));
        datepicker = schedule_obj.departureTime.split(" ")[0];
        time = schedule_obj.arrivalTime;
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
}

function lotery_ajax() {
    set_lottery_data();
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
                lat: latitude,
                lon: longitude,
                place_id: place_id
            },
            dataType: "json",
            success: function (response) {
                console.log(response);
                lottery_array = "";
                for (var i = 0; i < response.length; i++) {
                    var tag_img = 0;
                    for (; tag_img < 15; tag_img++) {
                        if (response[i].tag == tag[tag_img])
                            break;
                    }

                    if (section || place_home) {
                        if (section && place_home) {
                            if ((postcode[place_home][0] <= Number(response[i].postal_code) && Number(response[i].postal_code) <= postcode[place_home][1])) {
                                console.log("both");
                                if (section == tag_img) {
                                    if (lottery_array)
                                        lottery_array += ",";
                                    lottery_array += JSON.stringify(response[i]);
                                }
                                else if (section == 15) {
                                    if (response[i].activity !== "[]") {
                                        if (lottery_array)
                                            lottery_array += ",";
                                        lottery_array += JSON.stringify(response[i]);
                                    }
                                }
                            }
                        }
                        else if (place_home) {//地區
                            console.log("place_home");
                            if (postcode[place_home][0] <= response[i].postal_code && response[i].postal_code <= postcode[place_home][1]) {
                                if (lottery_array)
                                    lottery_array += ",";
                                lottery_array += JSON.stringify(response[i]);
                            }
                        } else {//類別
                            console.log("section");
                            if (section == tag_img) {
                                if (lottery_array)
                                    lottery_array += ",";
                                lottery_array += JSON.stringify(response[i]);
                            }
                            else if (section == 15) {//活動
                                if (response[i].activity != "[]") {
                                    if (lottery_array)
                                        lottery_array += ",";
                                    lottery_array += JSON.stringify(response[i]);
                                }
                            }
                        }
                    } else {
                        console.log("else");
                        if (lottery_array)
                            lottery_array += ",";
                        lottery_array += JSON.stringify(response[i]);
                    }
                }

                lottery_array = JSON.parse("[" + lottery_array + "]");
                console.log(lottery_array);
                lottery_i = lottery_array.length;
                random_num = Math.floor(Math.random() * lottery_i);
                data_array = lottery_array[random_num];
                $("#notLike").css("display", "initial");
                $("#Like").css("display", "initial");
                show(data_array);
            },
            // Ajax失敗後要執行的function，此例為印出錯誤訊息
            error: function (xhr, ajaxOptions, thrownError) {
                //alert("lottery" + xhr.status + "\n" + thrownError);
            }
        });
    }, 100);
}


function addForDislike() {
    var temp;
    temp = lottery_array[random_num];
    lottery_array[random_num] = lottery_array[lottery_i - 1];
    lottery_array[lottery_i - 1] = temp;
    lottery_i--;
    random_num = Math.floor(Math.random() * lottery_i);
    data_array = lottery_array[random_num];
    show(data_array);

}
function show(data_array) {
    console.log(data_array);
    var tag_img = 0;
    var postCode = 0;
    var x = 0;
    while (postcode[x][0] > data_array.postal_code || data_array.postal_code > postcode[x][1]) {
        x++;
    }
    postCode = x;
    for (; tag_img < 15; tag_img++) {
        if (data_array.tag == tag[tag_img])
            break;
    }
    $(".lottery_content_move").html("");
    var a = '<div id="bl-main" class="bl-main" myID="' + data_array.id + '" arrtime="' + data_array.arrival_time + '" postcode="' + locate[postCode] + '"><section style="display:none;"><div class="bl-box"></div><div class="bl-content"></div><span class="bl-icon bl-icon-close"></span></section><section><div class="bl-box spot_type_box"><nobr>' + in_out_door[Number(data_array.in_out)] + '</nobr><span class="badge"><img class="spot_type_icon" src="images/' + tag_imgs[tag_img] + '.png" /></span></div><div class="bl-content spot_type_content"><nobr style="margin-left:-2%;">地名 &nbsp;:  &nbsp;</nobr><nobr class="spot_type_content_namecolor">' + data_array.name + '</nobr><br><nobr style="line-height:4;margin-left:-33%;">天氣  &nbsp;:  &nbsp;</nobr><nobr style="line-height:4" class="spot_type_content_color overview">'
        + weather_arry[weather_box[Number(data_array.weather)]] + '</nobr><nobr style="position:absolute;padding-left:28%;line-height:4">紫外線  &nbsp;:  &nbsp;</nobr><nobr class="spot_type_content_color ultraviolet" style="padding-left:48%;line-height:4">' + data_array.uv + '</nobr><br><nobr style="line-height:2;margin-left:-33%;">類型  &nbsp;:  &nbsp;</nobr><nobr style="line-height:2" class="spot_type_content_color spot_type_text">' + data_array.tag + '</nobr><nobr style="position:absolute;padding-left:28%;line-height:2">抵達時間  &nbsp;:  &nbsp;</nobr><nobr class="spot_type_content_color" style="padding-left:52%;line-height:2">' + data_array.arrival_time + '</nobr><br><nobr style="line-height:4;margin-left:-33%;">距離  &nbsp;:  &nbsp;</nobr><nobr style="line-height:4" class="spot_type_content_color">' + data_array.distance + ' km</nobr><nobr style="position:absolute;padding-left:28%;line-height:4">室內外  &nbsp;:  &nbsp;</nobr><nobr class="spot_type_content_color inOutDoor" style="padding-left:48%;line-height:4">' + in_out_door[Number(data_array.in_out)] + '</nobr></div><span class="bl-icon bl-icon-close"></span></section></div><a href=""><img src="images/' + data_array.weather + '.png" class="lottery_weather_image">';

    if (data_array.activity !== "[]")
        a += '<img src="images/123.png" class="active_image">';
    a += '<p class="lottery_spot_name" >&nbsp;' + data_array.name + '&nbsp;</p>';
    if (translate == 'scooter')
        a += '<img src="images/white' + translate + '.png" class="lottery_transportation_image lottery_transportation_image_scooter">';
    else if (translate == 'walk')
        a += '<img src="images/white' + translate + '.png" class="lottery_transportation_image lottery_transportation_image_walk">';
    else
        a += '<img src="images/white' + translate + '.png" class="lottery_transportation_image">';
    a += '<p class="lottery_arrive_time">' + data_array.arrival_time + '</p><p class="lottery_arrive_distance">' + data_array.distance + ' km</p><img src="' + data_array.url + '" class="lottery_spot_image"><p class="lottery_spot_ID">' + data_array.id + '</p><p class="lottery_area_name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + locate[postCode] + '&nbsp;</p></a>';
    $("body").css('background-image', "url(" + weather_src[weather_backgound[Number(data_array.weather)]] + ")");
    //
    console.log([Number(data_array.weather)]);
    console.log(weather_src[weather_backgound[Number(data_array.weather)]]);
    $(".lottery_content_move").append(a);
    init();
}



function hammer() {
    var lotteryEle = document.getElementsByClassName('lottery_spot_div');
    [].slice.call(lotteryEle).forEach(function (element) {
        var hammertime = new Hammer(element);
        hammertime.on('swiperight', function (event) {
            $(element).animate({ left: '50%', opacity: '0' }, "200", function () {
                var obj = '{ "departureTime":"", "arrivalTime":"", "distance":"", "spotName":"", "weatherIcon":"", "spotTypeIcon":"", "spotTypeText":"", "spotInOutDoor":"", "transportation":"", "weatherOverview":"", "activeMark":"", "ultraviolet":"", "spotID":"", "spotPicture":"", "postcode":""}';
                obj = JSON.parse(obj);
                obj.departureTime = nowDate.getFullYear() + "-" + ((nowDate.getMonth() + 1) < 10 ? '0' : '') + (nowDate.getMonth() + 1) + "-" + nowDate.getDate() + " " + nowDate.getHours() + ":" + (nowDate.getMinutes() < 10 ? '0' : '') + nowDate.getMinutes();
                obj.arrivalTime = $(element).find(".lottery_arrive_time").text();
                obj.distance = $(element).find(".lottery_arrive_distance").text();
                obj.spotName = $(element).find(".lottery_spot_name").text().slice(1,-1);
                obj.weatherIcon = $(element).find(".lottery_weather_image").attr("src");
                obj.spotTypeIcon = $(element).find(".spot_type_icon").attr("src");
                obj.spotTypeText = $(element).find(".spot_type_text").text();
                obj.spotInOutDoor = $(element).find(".inOutDoor").text();
                obj.transportation = $(element).find(".lottery_transportation_image").attr("src").split("white")[0] + $(element).find(".lottery_transportation_image").attr("src").split("white")[1];
                obj.weatherOverview = $(element).find(".overview").text();
                obj.activeMark = $(element).find(".active_image").attr("src"); //之後需判斷是否為空
                obj.ultraviolet = $(element).find(".ultraviolet").text();
                obj.spotID = $(element).find(".lottery_spot_ID").text();
                obj.spotPicture = $(element).find(".lottery_spot_image").attr("src");
                obj.postcode = $(element).find(".bl-main").attr("postcode");
                localStorage.setItem('tourBasketkey' + num, JSON.stringify(obj));
                num++;
                localStorage.setItem("spotQuantity", num);
                addForDislike();
                $(element).css("left", "-50%");
                $(element).animate({ left: '0', opacity: '10' }, "200", function () {
                    show(data_array);
                });
            });
        });
        hammertime.on('swipeleft', function (event) {
            $(element).animate({ left: '-50%', opacity: '0' }, "200", function () {
                addForDislike();
                $(element).css("left", "50%");
                $(element).animate({ left: '0', opacity: '10' }, "200", function () {
                    show(data_array);
                });
            });
        });
        $("#notLike").click(function () {
            $(element).animate({ left: '-50%', opacity: '0' }, "200", function () {
                addForDislike();
                $(element).css("left", "50%");
                $(element).animate({ left: '0', opacity: '10' }, "200", function () {
                    show(data_array);
                });
            });
        });

        $("#Like").click(function () {
            $(element).animate({ left: '50%', opacity: '0' }, "200", function () {
                var obj = '{ "departureTime":"", "arrivalTime":"", "distance":"", "spotName":"", "weatherIcon":"", "spotTypeIcon":"", "spotTypeText":"", "spotInOutDoor":"", "transportation":"", "weatherOverview":"", "activeMark":"", "ultraviolet":"", "spotID":"", "spotPicture":"","postcode":""}';
                obj = JSON.parse(obj);
                obj.departureTime = nowDate.getFullYear() + "-" + ((nowDate.getMonth() + 1) < 10 ? '0' : '') + (nowDate.getMonth() + 1) + "-" + nowDate.getDate() + " " + nowDate.getHours() + ":" + (nowDate.getMinutes() < 10 ? '0' : '') + nowDate.getMinutes() ;
                obj.arrivalTime = $(element).find(".lottery_arrive_time").text();
                obj.distance = $(element).find(".lottery_arrive_distance").text();
                obj.spotName = $(element).find(".lottery_spot_name").text().slice(1,-1);
                obj.weatherIcon = $(element).find(".lottery_weather_image").attr("src");
                obj.spotTypeIcon = $(element).find(".spot_type_icon").attr("src");
                obj.spotTypeText = $(element).find(".spot_type_text").text();
                obj.spotInOutDoor = $(element).find(".inOutDoor").text();
                obj.transportation = $(element).find(".lottery_transportation_image").attr("src").split("white")[0] + $(element).find(".lottery_transportation_image").attr("src").split("white")[1];
                obj.weatherOverview = $(element).find(".overview").text();
                obj.activeMark = $(element).find(".active_image").attr("src"); //之後需判斷是否為空
                obj.ultraviolet = $(element).find(".ultraviolet").text();
                obj.spotID = $(element).find(".lottery_spot_ID").text();
                obj.spotPicture = $(element).find(".lottery_spot_image").attr("src");
                obj.postcode = $(element).find(".bl-main").attr("postcode");
                localStorage.setItem('tourBasketkey' + num, JSON.stringify(obj));
                num++;
                localStorage.setItem("spotQuantity", num);
                addForDislike();
                $(element).css("left", "-50%");
                $(element).animate({ left: '0', opacity: '10' }, "200", function () {
                    addForDislike();
                    show(data_array);
                });
            });
        });
    });
}



