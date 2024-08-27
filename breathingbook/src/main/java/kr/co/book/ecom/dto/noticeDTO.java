package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("noticeDTO")
public class noticeDTO {

	private int nnum; //공지번호
	private String nuser; //공지작성자
	private String ntitle; //공지 제목
	private String ncontent; //공지 내용
	private String ndate; //공지 작성일
	private String nfilereal; //첨부 파일 기존이름
	private String nfilefake; //첨부 파일 변경 이름
	private String npicturereal; //이미지 기존 이름
	private String npicturefake; //이미지 변경 이름
	private String npicturetype; //이미지 타입
	private int nclass; // 팝업인지 공지인지 구분
	private int nlength; // 팝업 길이
	private int nwidth; // 팝업 넓이
	private String nstartdate; // 이벤트 시작일
	private String nenddate; // 이벤트 종료일
	private int ndel; //삭제여부
	@Override
	public String toString() {
		return "noticeDTO [nnum=" + nnum + ", nuser=" + nuser + ", ntitle=" + ntitle + ", ncontent=" + ncontent
				+ ", ndate=" + ndate + ", nfilereal=" + nfilereal + ", nfilefake=" + nfilefake + ", npicturereal="
				+ npicturereal + ", npicturefake=" + npicturefake + ", npicturetype=" + npicturetype + ", nclass="
				+ nclass + ", nlength=" + nlength + ", nwidth=" + nwidth + ", nstartdate=" + nstartdate + ", nenddate="
				+ nenddate + ", ndel=" + ndel + "]";
	}
	
	

}
