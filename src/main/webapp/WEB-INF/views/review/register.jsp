<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/css/mypage/main.css">

    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/review/register.css">
<div class="content_area">
    <div class="left_menu">
        <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
    </div>
    <div class="main_content">
        

        <div class="panel panel-default">
            <div class="panel-heading">리뷰 작성하기</div>
            <div class="panel-body form-container">
                <form role="form" action="/review/register" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label>제목</label>
                        <input class="form-control" name='reviewTitle'>
                    </div>
                    <div class="form-group">
                        <label for="itemId">상품</label>
                        <select id="itemId" name="itemId" class="form-control">
                            <c:forEach items="${items}" var="item">
                                <option value="${item.itemId}">${item.itemName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>리뷰 내용</label>
                        <textarea class="form-control" rows="3" name='reviewContent'></textarea>
                    </div>
                    <div class="form-group">
                        <input type="file" id="uploadFile" name="uploadFile" multiple class="form-control">
                        <input type="hidden" value='defaultImage.jpg' name='reviewImageURL'>
                        <label for="uploadFile">- 최대 15MB 이하의 JPG, PNG, GIF, BMP 파일 첨부 가능합니다.</label>
                    </div>
                    <button type="submit" class="btn btn-default review-btn-success">등록하기</button>
                    <button type="reset" class="btn btn-default btn-info">리셋</button>
                </form>
            </div>
        </div>
    </div>
</div>

    <%@ include file="../includes/footer.jsp"%>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
        crossorigin="anonymous"></script>
    <script>
        $(document).ready(function(){
            // 업로드할 수 없는 파일 확장자와 최대 파일 크기 설정
            var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
            var maxSize = 15,728,640;‬//15MB     5242880; //5MB

            // 파일 확장자 및 크기 체크하는 함수
            function checkExtension(fileName, fileSize) {
                // 파일 크기가 최대 크기를 초과하는 경우 경고 메시지 출력 후 false 반환
                if (fileSize >= maxSize) {
                    alert("파일 사이즈 초과");
                    return false;
                }
                // 업로드할 수 없는 파일 확장자인 경우 경고 메시지 출력 후 false 반환
                if (regex.test(fileName)) {
                    alert("해당 종류의 파일은 업로드할 수 없습니다.");
                    return false;
                }
                // 파일 확장자 및 크기가 모두 유효한 경우 true 반환
                return true;
            }  //end function
            
            $("#uploadBtn").on("click", function(e){
                var formData = new FormData();
                var inputFile = $("input[name='uploadFile']");
                var files = inputFile[0].files;  
                console.log(files);
                
                for(var i=0; i<files.length; i++){
                    // 가져온 파일들을 순회하며 확장자 및 크기 체크 후 formData에 추가
                    if (!checkExtension(files[i].name, files[i].size)) {
                        return false;
                    }
                    formData.append("uploadFile", files[i]);
                }
                // AJAX를 통해 파일 업로드 요청    
                $.ajax({
                    url: '/uploadAjaxAction',
                    processData: false,
                    contentType: false,
                    data: formData,
                    type: 'post',
                    success: function(result){
                        alert("Uploaded");
                    }
                }); //end ajax
                
            });
        });
    </script>
</body>
</html>
