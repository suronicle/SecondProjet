package kr.co.book.ecom.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("pointgameDTO")
public class pointgameDTO {
	
	private int gno;
	private String gid;
	private String gdate;
	private int gpoint;
	
	@Override
	public String toString() {
		return "pointgameDTO [gno=" + gno + ", gid=" + gid + ", gdate=" + gdate + ", gpoint=" + gpoint + "]";
	}
	
	

}
