<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<style>
    .find_area {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        max-width: 500px;
        min-height: 300px;
        margin: 50px auto;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .form-control-user {
        width: 100%;
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
        margin-bottom: 20px;
    }

    .btn-custom {
        background-color: #d3d3d3;
        border-radius: 5px;
        border: none;
        color: #fff;
        font-size: 1rem;
        padding: 10px 20px;
        cursor: not-allowed;
        transition: background-color 0.3s, cursor 0.3s;
    }

    .btn-enabled {
        background-color: #ff6001;
        cursor: pointer;
    }

    .text-center {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 20px;
    }

    .text-center h1 {
        font-size: 1.5rem;
        margin-bottom: 20px;
    }

    .btn-group-horizontal {
        display: flex;
        justify-content: space-between;
    }

    .btn-home {
        background-color: #007bff;
        color: white;
        text-align: center;
        padding: 0.5rem 1rem;
        text-decoration: none;
        display: inline-block;
        border-radius: 5px;
    }

    .btn-home:hover {
        background-color: #0056b3;
        color: white;
        text-decoration: none;
    }

    .btn-main {
        background-color: #6c757d;
        color: white;
        text-align: center;
        padding: 0.5rem 1rem;
        text-decoration: none;
        display: inline-block;
        border-radius: 5px;
    }

    .btn-main:hover {
        background-color: #5a6268;
        color: white;
        text-decoration: none;
    }

    .footer-links {
        display: flex;
        justify-content: space-between;
        width: 100%;
        margin-top: 20px;
    }
</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>

    <div class="find_area">
        <div class="text-center">
            <h1 class="h4 text-gray-900 mb-2">아이디 찾기</h1>
        </div>
        <form class="user" action="/member/findId" method="POST">
            <div class="form-group">
                <input type="email" class="form-control form-control-user"
                    id="memberMail" aria-describedby="emailHelp" name="memberMail"
                    placeholder="이메일을 입력해주세요.">
            </div>
            <button type="submit" id="submitButton" class="btn btn-custom btn-user btn-block">
                확인</button>
        </form>
    </div>

    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>

    <script type="text/javascript">
        var msg = "${msg}";

        if (msg != "") {
            alert(msg);
        }

        document.getElementById('memberMail').addEventListener('input', function() {
            var email = this.value;
            var submitButton = document.getElementById('submitButton');
            if (email.length > 0) {
                submitButton.classList.add('btn-enabled');
            } else {
                submitButton.classList.remove('btn-enabled');
            }
        });
    </script>

</body>
</html>
