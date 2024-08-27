function selectpay(button, url) {
  var paymentMethod = button.getAttribute('data-payment');
  document.getElementById('payment-method').value = paymentMethod;
  alert(paymentMethod + '을(를) 선택했습니다.');
  window.open(url, '_blank', 'width=700,height=600');
  return false;
}

function payment(){
	
	// 사용자가 입력한 ID 값을 가져옴
	var payvalue = document.getElementById('paymentbtn').value;
	
	// 부모 창으로 전달하기 위해 부모 창의 document 객체에 접근
	var parentDocument = window.opener.document;

	// 부모 창의 ID 입력 필드에 사용자가 입력한 값 설정
	parentDocument.getElementById('buyway').value = payvalue;	

	// 부모 창의 ID 입력 필드를 비활성화 (버튼 비활성화)
	parentDocument.getElementById('card-payment').disabled= true;
	parentDocument.getElementById('simple-payment').disabled= true;
	parentDocument.getElementById('bank-transfer').disabled= true;

	parentDocument.getElementById('submit-button').disabled= false;
	
	window.close();
}


function paymentbank(){
	
	// 사용자가 입력한 ID 값을 가져옴
	var payvalue = document.getElementById('paymentbtn').value;
	var accountvalue = document.getElementById('bank-account').value;
	
	// 부모 창으로 전달하기 위해 부모 창의 document 객체에 접근
	var parentDocument = window.opener.document;

	// 부모 창의 ID 입력 필드에 사용자가 입력한 값 설정
	parentDocument.getElementById('buyway').value = payvalue;	
	parentDocument.getElementById('buyaccount').value = accountvalue;	

	
	parentDocument.getElementById('card-payment').disabled = true;
	parentDocument.getElementById('simple-payment').disabled = true;
	parentDocument.getElementById('bank-transfer').disabled = true;
	
	parentDocument.getElementById('submit-button').disabled= false;
	
	
	window.close();
}









function addrsslist(url) {
    window.open(url, '_blank', 'width=600,height=400');
    return false;
}

function addressadd(url) {
    window.open(url, '_blank', 'width=600,height=450');
    return false;
}






function addSelect(element) {
  // 사용자가 클릭한 요소의 부모 행을 가져옴
  var row = element.closest('tr');
  
  // 해당 행의 입력 필드 값을 가져옴
  var addAno = row.querySelector('.ano').value;
  var addPostal = row.querySelector('.apostal').value;
  var addDong = row.querySelector('.adong').value;
  var addAddress = row.querySelector('.aadress').value;
  var addDetail = row.querySelector('.adetail').value;

  // 부모 창으로 전달하기 위해 부모 창의 document 객체에 접근
  var parentDocument = window.opener.document;

  // 부모 창의 ID 입력 필드에 사용자가 입력한 값 설정
  parentDocument.getElementById('ano').value = addAno;  
  parentDocument.getElementById('apostal').value = addPostal;  
  parentDocument.getElementById('adong').value = addDong;
  parentDocument.getElementById('aadress').value = addAddress;
  parentDocument.getElementById('adetail').value = addDetail;  
  
  window.close();
}


function discountcou(url){
	window.open(url, '_blank', 'width=600,height=450');
	return false;
}


function sample6_execDaumPostcode() {
       new daum.Postcode({
           oncomplete: function(data) {
               // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

               // 각 주소의 노출 규칙에 따라 주소를 조합한다.
               // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
               let addr = ''; // 주소 변수
               let extraAddr = ''; // 참고항목 변수

               //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
               if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                   addr = data.roadAddress;
               } else { // 사용자가 지번 주소를 선택했을 경우(J)
                   addr = data.jibunAddress;
               }

               // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
               if(data.userSelectedType === 'R'){
                   // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                   // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                   if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                       extraAddr += data.bname;
                   }
                   // 건물명이 있고, 공동주택일 경우 추가한다.
                   if(data.buildingName !== '' && data.apartment === 'Y'){
                       extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                   }
                   // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                   if(extraAddr !== ''){
                       extraAddr = ' (' + extraAddr + ')';
                   }
                   
                   // 조합된 참고항목을 해당 필드에 넣는다.
                   document.getElementById("sample6_extraAddress").value = extraAddr;
               
               } else {
                   document.getElementById("sample6_extraAddress").value = '';
               }

               // 우편번호와 주소 정보를 해당 필드에 넣는다.
               document.getElementById("sample6_postcode").value = data.zonecode;
               document.getElementById("sample6_address").value = addr;
               // 커서를 상세주소 필드로 이동한다.
               document.getElementById("sample6_detailAddress").focus();
           }
       }).open();
   }
   
