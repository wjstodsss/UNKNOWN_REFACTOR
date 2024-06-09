$(document).ready(function() {

	appendCommonHiddenInputs('actionForm');
	var actionForm = $("#actionForm");
	var sortColumnValue = $("#sortColumn")
	var groupColumnValue = $("#groupColumn")
	var sortTypeValue = $("#sortType")
	$(".paginate_button a").on(
			"click",
			function(e) {
				e.preventDefault();
				actionForm.find("input[name='pageNum']")
						.val($(this).attr("href"));
				actionForm.submit();
	});

	$(".button-add a")
		.on("click", 
		function(e) {
			var groupColumn = this
					.getAttribute('data-groupColumn');
			groupColumnValue.val(groupColumn);
			var sortColumn = this
					.getAttribute('data-sortColumn');
			sortColumnValue.val(sortColumn);
			var sortType = this
					.getAttribute('data-sortType');
			sortTypeValue.val(sortType);
		
			var sortForm = $('<form>').attr({
				'id' : 'sortForm',
				'method' : 'get'
			});
		
			$('body').append(sortForm);
		
			appendCommonHiddenInputs('sortForm');
		
			e.preventDefault();
		
			var href = "/admin" + groupColumn
					+ "/list";
			sortForm.attr("action", href);
			sortForm.submit();
	});
		
var typeSelect = document
.querySelector('.custom-select[name="type"]');
var keywordInput = document
.querySelector('.custom-keyword');

typeSelect.addEventListener('change',
updateKeywordInputType);
updateKeywordInputType();

function updateKeywordInputType() {
	var selectedOption = typeSelect.value;

	if (selectedOption === 'D') {
		keywordInput.type = 'date';
	} else {
		keywordInput.type = 'text';
	}
}

document.getElementById('searchForm').onsubmit = function() {
	if (!searchForm.find("option:selected").val()) {
		alert("검색종류를 선택하세요");
		return false;
	}

	if (!searchForm.find("input[name='keyword']").val()) {
		alert("키워드를 입력하세요");
		return false;
	}
	appendCommonHiddenInputs('searchForm');
};


/*
 var newPath = updateActionUrl();
	var actionForm = $("#actionForm");
	var sortColumnValue	= $("#sortColumn")
	var groupColumnValue	= $("#groupColumn")
	$(".paginate_button a").on(
			"click",
			function(e) {
				e.preventDefault();
				console.log("click");

				actionForm.find("input[name='pageNum']")
						.val($(this).attr("href"));
			    actionForm.attr("action", newPath);
				actionForm.submit();
			}
	);
*/	
	
/*
$(".button-add a").on("click", function(e) {
	console.log(groupColumnValue);
	console.log(sortColumnValue);
		var groupColumn = this.getAttribute('data-groupColumn');
		groupColumnValue.val(groupColumn);
		var sortColumn = this.getAttribute('data-sortColumn');
		sortColumnValue.val(sortColumn);
	    e.preventDefault();
	    var href = $(this).attr("href");
	    actionForm.attr("action", href);
	    actionForm.submit();
	});

*/	
	
	
/*
	var searchForm = $("#searchForm");
	$("#searchForm button").on("click", function(e){
		if(!searchForm.find("option:selected").val()){
			alert("검색종류를 선택하세요");
			return false;
		}

		if(!searchForm.find("input[name='keyword']").val()){
			alert("키워드를 입력하세요");
			return false;
		}
		searchForm.find("input[name='pageNum']").val("1");
		e.preventDefault();
		searchForm.attr("action", newPath);
		searchForm.submit();
		
	});
*/

});


function appendCommonHiddenInputs(formId) {
	const form = document.getElementById(formId);
	const commonInputs = document.getElementById(
			'commonHiddenInputs').cloneNode(true);
	commonInputs.style.display = 'none';
	form.appendChild(commonInputs);
}

function extractPageName(url) {
    var lastSlashIndex = url.lastIndexOf('/');
    var pageName = url.substring(lastSlashIndex + 1);
    return pageName;
}

function removeAction(){
	appendCommonHiddenInputs('modifyForm');
	$("#modifyForm").attr("action", "remove");
	$("#modifyForm").submit();
	var result = '${result}';
	console.log(result);
}


function goToModalForm() {
    clearModalForm();
    $('#formModal').modal('show');
}

function clearModalForm() {
    console.log("ff");
    $('#registerForm')[0].reset();
}

function closeModal(element) {
	$(element).closest('.modal').modal('hide');
}

function validateInput() {
    const input = document.getElementById("itemId");
    const value = input.value;
    const pattern = /^[0-9]+$/;

    if (!pattern.test(value)) {
        alert("숫자만 입력하세요");
        input.focus();
    }
}

function showElement(elementId) {
    var element = document.getElementById(elementId);
    if (element) {
        element.classList.remove("d-none"); // Remove the d-none class
        element.classList.add("d-block");   // Add the d-block class
    }
}


