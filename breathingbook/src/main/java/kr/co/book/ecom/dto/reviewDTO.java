package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("reviewDTO")
public class reviewDTO {
	
	private String rbookno; //책번호
	private String rno; //리뷰번호
	private String rid; // 작성자
	private String rcontents; // 내용
	private int rrating; //별점
	private int rspoiler; // 스포여부
	private int rsympathy; //공감수
	private String rdate; //작성일
	private int rdel; //삭제여부
	
	private int cno; //대댓글번호
	
	
	@Override
	public String toString() {
		return "reviewDTO [rbookno=" + rbookno + ", rno=" + rno + ", rid=" + rid + ", rcontents=" + rcontents
				+ ", rrating=" + rrating + ", rspoiler=" + rspoiler + ", rsympathy=" + rsympathy + ", rdate=" + rdate
				+ ", rdel=" + rdel + "]";
	}
	
	

}
