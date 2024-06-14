<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">



	var msg = "${msg}";
		 
		if (msg != "") {
			alert(msg);
		}


</script>


   <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-2">아이디 찾기</h1>
                                        <p class="mb-4">이메일을 입력해주세요!</p>
                                    </div>
                                    <form class="user" action="/member/findId" method="POST">
                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user"
                                                id="memberMail" aria-describedby="emailHelp" name="memberMail"
                                                placeholder="Enter Email Address...">
                                        </div>
                                        <button type="submit" class="btn btn-primary btn-user btn-block">
                                            Find ID
                                        </button>
                                    </form>
                                    <hr>
                                  
                                    <a href="/member/login" class="btn btn-facebook btn-user btn-block">
                                           Login
                                        </a>
                                        <hr>
                                       <div class="text-center">
                                        <a class="small" href="/main">메인페이지</a>
                                    </div>
</body>
</html>