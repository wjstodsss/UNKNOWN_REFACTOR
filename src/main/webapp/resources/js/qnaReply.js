console.log("Reply Module........");

var qnaReplyService = (function () {
    function add(reply, callback, error) {
        console.log("reply...............");
        $.ajax({
            type: "post",
            url: "/replies2/new",
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success: function (result, status, xhr) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    error(er);
                }
            },
        });
    }

    function getList(param, callback, error) {
        console.log(1);
        var qnaId = param.qnaId;
        console.log(qnaId);
        var page = param.page || 1;

        $.ajax({
            type: "get",
            url: "/replies2/pages/" + qnaId + "/" + page + ".json",
            success: function (result, status, xhr) {
                if (callback) {
                    console.log('Response Data:', result);
                    try {
                        var jsonData = typeof result === 'string' ? JSON.parse(result) : result;
                        callback(jsonData);
                    } catch (e) {
                        console.error('Invalid JSON response:', result);
                        if (error) error(e);
                    }
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    console.log('AJAX error:', status, er);
                    error(er);
                }
            },
        });
    }

    function remove(replyId, callback, error) {
        $.ajax({
            type: "delete",
            url: "/replies2/" + replyId,
            success: function (result, status, xhr) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    error(er);
                }
            },
        });
    }

    function update(reply, callback, error) {
        $.ajax({
            type: "put",
            url: "/replies2/" + reply.qnaReplyId,
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success: function (result, status, xhr) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    error(er);
                }
            },
        });
    }

    function get(replyId, callback, error) {
        console.log('Fetching reply:', replyId);
        $.ajax({
            type: "get",
            url: "/replies2/" + replyId + ".json",
            success: function (result, status, xhr) {
                if (callback) {
                    try {
                        var jsonData = typeof result === 'string' ? JSON.parse(result) : result;
                        callback(jsonData);
                    } catch (e) {
                        console.error('Invalid JSON response:', result);
                        if (error) error(e);
                    }
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    error(er);
                }
            },
        });
    }

    function displayTime(timeValue) {
        var today = new Date();
        var gap = today.getTime() - timeValue;
        var dateObj = new Date(timeValue);
        var str = "";

        if (gap < (1000 * 60 * 60 * 24)) {
            var hh = dateObj.getHours();
            var mi = dateObj.getMinutes();
            var ss = dateObj.getSeconds();
            return [(hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss].join('');
        } else {
            var yy = dateObj.getFullYear();
            var mm = dateObj.getMonth() + 1;
            var dd = dateObj.getDate();
            return [yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd].join('');
        }
    }

    return {
        add: add,
        getList: getList,
        remove: remove,
        update: update,
        get: get,
        displayTime: displayTime,
    };
})();
