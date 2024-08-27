$('#product-modal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // 모달을 트리거한 버튼
    var bono = button.data('bono');
	var boname = button.data('boname');
	var bowriter = button.data('bowriter');


	var bocontent = button.data('bocontent');
	// 길이 제한 및 "..." 추가
	var bocontentlen = bocontent.length > 200 ? bocontent.substring(0, 200) + '...' : bocontent;
	
    // 모달 내용 업데이트
    var modal = $(this);
    modal.find('#modal-bono').text(bono);
	modal.find('#modal-title').text(boname);
	modal.find('#modal-content').text(bocontentlen);
	modal.find('#modal-writer').text(bowriter);

	modal.find('#modal-detail-link').attr('href', '/eBookDetail?bono=' + bono); // Bono 값을 링크에 추가
});