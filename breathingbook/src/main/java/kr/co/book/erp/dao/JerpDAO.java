package kr.co.book.erp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.book.ecom.dto.inquiryDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.ecom.dto.noticeDTO;
import lombok.experimental.Delegate;

@Mapper
public interface JerpDAO {
	
	//공지사항 리스트
	@Select("select * from notice where nclass >= 1 order by ndate desc;")
	public List<noticeDTO> mnoticelist ();
	
	//팝업,공지사항 상세
	@Select("select * from notice where nnum = #{nnum};")
	public noticeDTO noticeditail (int nnum);
	
	//공지 등록
	public int newnotice(noticeDTO dto);
	
	//공지 수정
	public int upnotice(noticeDTO dto);
	
	//공지 취소 //팝업 취소
	@Update("update notice set ndel = 1 where nnum = #{nnum};")
	public int delnotice(int nnum);
	
	//팝업 리스트
	@Select("select * from notice where nclass= 0 order by ndate desc;")
	public List<noticeDTO> mpopuplist ();
	
	//팝업 등록
	public int newpopup(noticeDTO dto);
	
	//팝업 수정
	public int uppopup(noticeDTO dto);
	
	//완전삭제
	@Delete ("delete from notice where nnum = #{nnum};")
	public int delno(int nnum);
	
	//큐엔에이 리스트
	@Select("select * from inquiry order by idate desc;")
	public List<inquiryDTO> qlist();
	
	//큐엔에이 상세
	@Select("select * from inquiry where ino = #{ino};")
	public inquiryDTO qone(int ino);
	
	//큐엔에이 답변 등록
	@Update("update inquiry set ianswer = #{ianswer}, iadate = now() where ino = #{ino};")
	public int answer (inquiryDTO dto);
	
	//큐엔에이 완전비공개처리
	@Update("update inquiry set idel = '1' where ino = #{ino};")
	public int qdel (int ino);
	
	//큐엔에이 공개처리
	@Update("update inquiry set idel = '0' where ino = #{ino};")
	public int qopen (int ino);
	
	//큐엔에이 완전 삭제
	@Delete ("delete from inquiry where ino = #{ino};")
	public int qnadel (int ino);
	
	//유형 이름 구하기
	@Select("select codename from codetable where pid = #{pid}")
	public String pname(String pid);
	
	//회원 상세
	public memberDTO viewMem2(String mid);
	

}
