package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("commentsDTO")
public class commentsDTO {
	
	private int cno; //대댓리뷰번호
	private String rno; //A1본댓글리뷰번호
	private String cid; //작성자
	private String ccontents; //내용
	private int cspoiler; //스포여부
	private int csympathy; // 공감수
	private String cdate; //작성일
	private int cdel; //삭제여부
	
	@Override
	public String toString() {
		return "commentsDTO [cno=" + cno + ", rno=" + rno + ", cid=" + cid + ", ccontents=" + ccontents + ", cspoiler="
				+ cspoiler + ", csympathy=" + csympathy + ", cdate=" + cdate + ", cdel=" + cdel + "]";
	}

}
