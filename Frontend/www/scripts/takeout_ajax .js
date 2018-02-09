function takeout_ajax(encloseid) {
    $.ajax({
        type: "POST",
        url: "http://140.121.197.130:8050/WT/takeoutshareAjaxServlet", 
        data: {
            encloseid: encloseid
        },
        dataType: "json",
        success: function (response) {          
            var shareObj = '{ "encloseid":"", "enclosename":"","startPosition":"","transoprt":"", "viewpoint":""}';
            shareObj = JSON.parse(shareObj);
            shareObj.encloseid = response.encloseid;
            shareObj.enclosename = response.enclosename;
            shareObj.startPosition = response.startPosition;
            shareObj.transport = response.transport;
            shareObj.viewpoint = response.viewpoint;
            var num = Number(localStorage.getItem("shareQuantity"));
            if (!num)
                num = 0;
            localStorage.setItem('shareKey' + num, JSON.stringify(shareObj));
            num++;
            localStorage.setItem("shareQuantity", num);
            if (response.length < 1)
                alert("輸入錯誤");
            else
                show();

        },error: function (xhr, ajaxOptions, thrownError) {
          //  alert(xhr.status + "\n" + thrownError);
        }
    });
}
