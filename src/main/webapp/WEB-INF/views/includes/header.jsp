<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome Unknown</title>
<link rel="stylesheet" href="/resources/css/main.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"
    integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
    crossorigin="anonymous"></script>
</head>
<body>
    <div class="wrapper">
        <div class="wrap">
            <div class="top_gnb_area">
                <ul class="list">
                    <c:if test="${member == null}">
                        <!-- 로그인 안했을 때 -->
                        <li><a href="/member/login">로그인</a></li>
                        <li><a href="/member/normalAuth">회원가입</a></li>
                         <li><a href="/board/notice">고객센터</a></li>
                    </c:if>
                    <c:if test="${member != null }">
                        <!-- 로그인 했을 때 -->
                        <c:if test="${member.adminCk == 1 }">
                            <!-- 관리자 계정일 때 -->
                            <li><a href="/admin/dashboard/main">관리자 페이지</a></li>
                        </c:if>
                        <li><a href="/mypage/orderList">주문조회</a></li>
                        <li><a id="gnb_logout_button">로그아웃</a></li>
                        <li><a href="/member/update">정보수정</a></li>
                        <li><a href="/board/notice">고객센터</a></li>
                    </c:if>
                </ul>
            </div>
            <div class="top_area">
                <div class="logo_area">
                    <a href="/main"><img src="/resources/img/mLogo.png"></a>
                </div>
                <div class="search_area">
                    <div class="search_wrap">
                        <form id="searchForm" action="/search" method="get">
                            <div class="search_input">
                                <select name="type">
                                    <option value="T">상품명</option>
                                    <option value="A">브랜드명</option>
                                </select>
                                <input type="text" name="keyword">
                                <button class='btn search_btn'>
                                    <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png">
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <div id="icon">
                    <div class="upicon1">
                    <a href="/manboki" id="manboki-icon">
                        <img src="https://cdn4.iconfinder.com/data/icons/simple-goods-services-outline/30/running-512.png" alt="run">
                    </a>
                    </div>
                    <div class="upicon1">
                        <img src="https://cdn0.iconfinder.com/data/icons/ecommerce-207/32/Coupon-256.png" alt="coupon">
                    </div>
                    <div class="upicon1" id="mypage-icon" >
                        <img src="https://cdn2.iconfinder.com/data/icons/squircle-ui/32/Avatar-256.png" alt="mypage">
                    </div>
                    <div class="upicon1">
                        <a href="/cart/${member.memberId}" id="cart-icon">
                            <img src="https://cdn0.iconfinder.com/data/icons/phosphor-regular-vol-4/256/shopping-cart-256.png" alt="cart">
                        </a>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="navi_bar_area">
                <div class="category_wrap">
                    <img src = "/resources/img/ico_gnb_all.png"><a href="#" class="category_btn"><span>카테고리</span></a>
                    <div class="menu-1">
                        <ul>
                            <li><a class="submenu1" href="/product/newArr"><img src="/resources/img/new.png"><span style="display: block;">신상품</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1000-1099">전체</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu2" href="/product/list?cateRange=1101-1115"><img src="/resources/img/chicken_breast.png"><span style="display: block;">닭가슴살</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1101-1115">전체</a></li>
                                    <li><a href="/product/list?cateCode=1101">프로</a></li>
                                    <li><a href="/product/list?cateCode=1102">스팀·소프트</a></li>
                                    <li><a href="/product/list?cateCode=1103">소스닭가슴살</a></li>
                                    <li><a href="/product/list?cateCode=1104">스테이크</a></li>
                                    <li><a href="/product/list?cateCode=1105">소시지·햄</a></li>
                                    <li><a href="/product/list?cateCode=1106">저염·염분무첨가</a></li>
                                    <li><a href="/product/list?cateCode=1107">생 닭가슴살</a></li>
                                    <li><a href="/product/list?cateCode=1108">훈제</a></li>
                                    <li><a href="/product/list?cateCode=1109">볼,큐브</a></li>
                                    <li><a href="/product/list?cateCode=1110">슬라이스</a></li>
                                    <li><a href="/product/list?cateCode=1111">냉장·실온보관</a></li>
                                    <li><a href="/product/list?cateCode=1112">핫바·어묵바</a></li>
                                    <li><a href="/product/list?cateCode=1113">스낵·칩</a></li>
                                    <li><a href="/product/list?cateCode=1114">크리스피</a></li>
                                    <li><a href="/product/list?cateCode=1115">육포</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu3" href="/product/list?cateRange=1201-1209"><img src="/resources/img/lunchbox.png"><span style="display: block;">도시락·볶음밥</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1201-1209">전체</a></li>
                                    <li><a href="/product/list?cateCode=1201">볶음밥</a></li>
                                    <li><a href="/product/list?cateCode=1202">다이어트 도시락</a></li>
                                    <li><a href="/product/list?cateCode=1203">주먹밥·김밥</a></li>
                                    <li><a href="/product/list?cateCode=1204">더담은 도시락</a></li>
                                    <li><a href="/product/list?cateCode=1205">간편 도시락</a></li>
                                    <li><a href="/product/list?cateCode=1206">덮밥·컵밥</a></li>
                                    <li><a href="/product/list?cateCode=1207">즉석밥·곤약밥</a></li>
                                    <li><a href="/product/list?cateCode=1208">간편 죽</a></li>
                                    <li><a href="/product/list?cateCode=1209">비건 도시락</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu4" href="/product/list?cateRange=1301-1308"><img src="/resources/img/drink.png"><span style="display: block;">음료·차·프로틴</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1301-1308">전체</a></li>
                                    <li><a href="/product/list?cateCode=1301">프로틴 쉐이크</a></li>
                                    <li><a href="/product/list?cateCode=1302">프로틴 음료</a></li>
                                    <li><a href="/product/list?cateCode=1303">물,탄산수</a></li>
                                    <li><a href="/product/list?cateCode=1304">보충제,부스터</a></li>
                                    <li><a href="/product/list?cateCode=1305">우유·두유·요거트</a></li>
                                    <li><a href="/product/list?cateCode=1306">커피·차</a></li>
                                    <li><a href="/product/list?cateCode=1307">클렌즈·주스</a></li>
                                    <li><a href="/product/list?cateCode=1308">제로음료</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu5" href="/product/list?cateRange=1401-1411"><img src="/resources/img/instant.png"><span style="display: block;">즉석 간편식</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1401-1411">전체</a></li>
                                    <li><a href="/product/list?cateCode=1401">브리또</a></li>
                                    <li><a href="/product/list?cateCode=1402">치킨</a></li>
                                    <li><a href="/product/list?cateCode=1403">핫도그</a></li>
                                    <li><a href="/product/list?cateCode=1404">즉석밥·곤약밥</a></li>
                                    <li><a href="/product/list?cateCode=1405">만두·딤섬</a></li>
                                    <li><a href="/product/list?cateCode=1406">떡볶이·튀김·순대</a></li>
                                    <li><a href="/product/list?cateCode=1407">피자·탕수육</a></li>
                                    <li><a href="/product/list?cateCode=1408">라면·쌀국수</a></li>
                                    <li><a href="/product/list?cateCode=1409">곤약면·파스타</a></li>
                                    <li><a href="/product/list?cateCode=1410">삼계탕</a></li>
                                    <li><a href="/product/list?cateCode=1411">죽·스프</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu6" href="/product/list?cateRange=1501-1503"><img src="/resources/img/beef.png"><span style="display: block;">소고기</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1501-1503">전체</a></li>
                                    <li><a href="/product/list?cateCode=1501">스테이크·볼</a></li>
                                    <li><a href="/product/list?cateCode=1502">설도·홍두깨살</a></li>
                                    <li><a href="/product/list?cateCode=1503">국거리·조리용</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu7" href="/product/list?cateRange=1601-1603"><img src="/resources/img/sweetpotato.png"><span style="display: block;">견과·고구마·감자</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1601-1603">전체</a></li>
                                    <li><a href="/product/list?cateCode=1601">고구마·감자</a></li>
                                    <li><a href="/product/list?cateCode=1602">단호박</a></li>
                                    <li><a href="/product/list?cateCode=1603">견과·옥수수</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu8" href="/product/list?cateRange=1701-1704"><img src="/resources/img/salad.png"><span style="display: block;">샐러드·과일</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1701-1704">전체</a></li>
                                    <li><a href="/product/list?cateCode=1701">알뜰샐러드</a></li>
                                    <li><a href="/product/list?cateCode=1702">토핑샐러드</a></li>
                                    <li><a href="/product/list?cateCode=1703">과일</a></li>
                                    <li><a href="/product/list?cateCode=1704">야채 믹스</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu9" href="/product/list?cateRange=1801-1804"><img src="/resources/img/eggs.png"><span style="display: block;">계란·난백·콩</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1801-1804">전체</a></li>
                                    <li><a href="/product/list?cateCode=1801">난백·흰자</a></li>
                                    <li><a href="/product/list?cateCode=1802">구운·훈제란</a></li>
                                    <li><a href="/product/list?cateCode=1803">반숙란</a></li>
                                    <li><a href="/product/list?cateCode=1804">콩·두부</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu10" href="/product/list?cateRange=1901-1902"><img src="/resources/img/fillet.png"><span style="display: block;">닭안심살</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=1901-1902">전체</a></li>
                                    <li><a href="/product/list?cateCode=1901">소프트</a></li>
                                    <li><a href="/product/list?cateCode=1902">생 안심</a></li>
                                </ul>
                            </li>
                            <li><a class="submenu11" href="/product/list?cateRange=2001-2007"><img src="/resources/img/cookie.png"><span style="display: block;">과자·간식·떡</span></a>
                                <ul>
                                    <li><a href="/product/list?cateRange=2001-2007">전체</a></li>
                                    <li><a href="/product/list?cateCode=2001">시리얼·바</a></li>
                                    <li><a href="/product/list?cateCode=2002">젤리·쫀드기</a></li>
                                    <li><a href="/product/list?cateCode=2003">초콜릿</a></li>
                                    <li><a href="/product/list?cateCode=2004">프로틴 스낵</a></li>
                                    <li><a href="/product/list?cateCode=2005">과자·쿠키</a></li>
                                    <li><a href="/product/list?cateCode=2006">아이스크림</a></li>
                                    <li><a href="/product/list?cateCode=2007">떡·오트밀</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
                
                <nav id="gnb" class="gnb">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/ranking"><span>랭킹</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/product/promotion"><span>이달의 특가</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/product/brand"><span>브랜드관</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/product/timesale"><span>888데이</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/benefits"><span>혜택정리</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/event"><span>이벤트</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/expressDelivery"><img src="/resources/img/delivery.png" name="특급배송"></a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
