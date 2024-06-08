<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
section {
	width: 1150px;
	height: 1800px;
	margin: 0 auto;
}

.box {
	width: 530px;
	height: 208px;
	margin: 10px;
	float: left;
	text-align: center;
	padding: 10px 8px 55px 15px;
}

.box img {
	width: 100%;
	height: 100%;
}

.box h5, .box p {
	margin: 5px 0
}

.container {
	width: 1100px; /* Two boxes + margins */
	margin: 0 auto;
	padding: 0 10px 0 10px;
}

.pagination {
	clear: both;
	text-align: center;
	margin-top: 20px;
}

.pagination a {
	margin: 0 5px;
	text-decoration: none;
	padding: 5px 10px;
}


</style>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.0.min.js"></script>
<script>
  $(function() {
    const itemsPerPage = 12;
    let currentPage = 1;
    let data = [];
    let ongoingEvents = [];
    let completedEvents = [];

    function loadData() {
      $.ajax({
        url: "/resources/img/event/event1.json",
        dataType: "json",
        success: function(response) {
          data = response;
          categorizeEvents();
          renderPage(currentPage);
          renderPagination();
        }
      });
    }

    function categorizeEvents() {
      ongoingEvents = data.filter(event => event.status === "ongoing");
      completedEvents = data.filter(event => event.status === "completed");
    }

    function renderPage(page) {
      let paginatedData;
      if (page === 1) {
        paginatedData = ongoingEvents;
      } else {
        paginatedData = completedEvents;
      }

      $('.box').empty();
      paginatedData.slice(0, itemsPerPage).forEach((item, index) => {
        $(".box").eq(index).append('<div><a href="#">' + "<img src='/resources/img/event/" + item.url + "'/>" + '</a></div>');
        $(".box").eq(index).append('<h5><a href="#">' + item.title + "</a></h5>");
        $(".box").eq(index).append('<p><a href="#">' + item.date + "</a></p>");
      });
    }

    function renderPagination() {
      $('.pagination').empty();
      $('.pagination').append('<a href="#" class="page-link" data-page="1">진행중인 이벤트</a>');
      $('.pagination').append('<a href="#" class="page-link" data-page="2">종료된 이벤트</a>');
    }

    $(document).on('click', '.page-link', function(e) {
      e.preventDefault();
      currentPage = $(this).data('page');
      renderPage(currentPage);
    });

    loadData();
  });
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/includes/header.jsp"%>
	<div class="content_area">
		<section>
			<div class="pagination"></div>
			<article>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>
				<div class="box"></div>

			</article>

		</section>
	</div>


	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>