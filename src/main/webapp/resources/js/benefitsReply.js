
console.log("Reply Module........");

var benefitsReplyService = (function () {
function add(reply, callback, error) {
console.log("benefits...............");
$.ajax({
type: "post",
url: "/replies1/new",
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
// param: 댓글 목록을 가져오기 위해 필요한 매개변수
// pageId(게시물 번호)와 선택적으로 page (페이지 번호)가 필요
// param 객체에서 page 값을 가져와서 page 변수에 할당
// page 값이 없다면 기본값으로 1을 사용, 댓글을 가져올 페이지의 번호를 의미

function getList(param, callback, error) {
//전체데이타 가져오기
console.log('gggg123213123213ssaaaaaassssfffff');
var pageId = param.pageId;

var page = param.page || 1;

$.ajax({
type: "get",
url: "/replies1/pages/" + pageId + "/" + page + ".json",

success: function (result, status, xhr) {

if (callback) {
console.log('gggg123213123213fffff');
    callback(result);
}
},
error: function (xhr, status, er) {
if (error) {
console.log('ffff123213123213gggggg');
    error(er);
}
},
});
}
function remove(benefitsReplyId, callback, error) {
//데이타 삭제
$.ajax({
type: "delete",
url: "/replies1/" + benefitsReplyId,

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
//테이타 수정
$.ajax({
    type: "put",
    url: "/replies1/" + benefits.benefitsReplyId,
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
function get(benefitsReplyId, callback, error) {
//단건 데이타 가져오기
console.log('123213123213fffff');
$.ajax({
    type: "get",
    url: "/replies1/" + benefitsReplyId + ".json",

    success: function (result, status, xhr) {
    if (callback) {
        console.log('ffff123213123213fffff');
        callback(result);
        
    }
    },
    error: function (xhr, status, er) {
    if (error) {
    console.log('ggggggg1111123123123213');
        error(er);
    }
    },
});
}
// 주어진 시간을 현재 시간과 비교하여, 24시간 이내라면 시:분:초로, 그 이상이면 년/월/일로 표시
function displayTime(timeValue) {

		var today = new Date();

		var gap = today.getTime() - timeValue;

		var dateObj = new Date(timeValue);
		var str = "";

		if (gap < (1000 * 60 * 60 * 24)) {

			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();

			return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss ].join('');

		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; // getMonth() is zero-based
			var dd = dateObj.getDate();

			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd ].join('');
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
