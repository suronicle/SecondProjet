package kr.co.book.ecom.dao;


import java.util.List;
import java.util.Map;

import javax.swing.Spring;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.book.ecom.dto.adressDTO;
import kr.co.book.ecom.dto.buyDTO;
import kr.co.book.ecom.dto.commentsDTO;
import kr.co.book.ecom.dto.inquiryDTO;
import kr.co.book.ecom.dto.loginsessionDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.ecom.dto.noticeDTO;
import kr.co.book.ecom.dto.pointgameDTO;
import kr.co.book.ecom.dto.reviewDTO;

@Mapper
public interface JbookDAO {
	
	//회원 등록
	public int enewmaber(memberDTO dto);
	
	//로그인 세션 정보
	@Select ("select m.*, c.codename as 'mclassname' from member m, codetable c where m.mclass = c.pid and m.mid = #{mid} and m.mpw =#{mpw} and m.mstate='H0';")
	public loginsessionDTO login (Map<String, String> map);
	
	//로그인 세션 정보
	@Select ("select * from branch where brno = #{mid} and brpw =#{mpw} and brdel=0 and brclass != 2;")
	public loginsessionDTO login2 (Map<String, String> map);
	
	//현재 기준 누군가의 확정 정보 수정
	public List<buyDTO> buyallok ();
	
	//사업자 비밀번호 찾기
	@Select ("select brpw from branch where brbusinessno = #{brbusinessno} and brno =#{brno};")
	public String brpw (Map<String, String> map);
	
	//주소 등록 넘버 설정
	@Select ("SELECT COALESCE(MAX(ano)+1,1) AS ano FROM adress WHERE aid = #{mid};")
	public int newano(String mid);
	
	//주소 등록
	public int newadress(adressDTO dto);
	
	//마이페이지 결재건수
	@Select ("select count(*) from buy where buyid = #{mid} and buycode != 'E1' and buycode != 'E0';")
	public int waycont (String mid);
	//마이페이지 주문수
	@Select ("select count(*) from buy where buyid = #{mid};")
	public int buycont (String mid);
	//마이페이지 배송수
	@Select ("select count(*) from delivery where did = #{mid};")
	public int deliverycont (String mid);   
	
	//메일 중복 여부
	@Select ("select * from member where memail = #{mmail};")
	public String mailselct (String mail);
	
	//아이디 중복 여부
	@Select ("select * from member where mid = #{mid};")
	public String idselct (String mid);
	
	
	//배송내역
	public List<buyDTO> mybuylist (String mid);
	//배송상세	
	public List<buyDTO> mybuyditail (buyDTO dto);
	//운송장
	public String deliveryno (int buyno);
	//상태 처리
	@Update("update buy set buycode = #{buycode} where buyno = #{buyno};")
	public int upcode (buyDTO dto);
	//매출처리
	@Insert("insert into purchase(brno,brreqnum,pdate,pclass,pbookno, pcode, buyno) values ('admin',#{buynum}, NOW(), 2, #{buybooknum}, '0', #{buyno});")
	public int confirm (buyDTO dto);
	//리뷰 리스트
	public List<reviewDTO> myreviewList (String mid);
	
	
	//아이디 찾기 이메일 전송 - 존재하는 멤버인가
	@Select("select memail from member where memail = #{mmail} and mname = #{mname}")
	public String mailck (Map<String, String> map);
	
	//아이디 찾기 완료
	@Select ("select mid from member where memail = #{mmail} and mname = #{mname};")
	public String findid (Map<String, String> map);
	
	//비밀번호 찾기 이메일 전송 - 존재하는 멤버인가
	@Select ("select mid from member where memail = #{mmail} and mname = #{mid};")
	public String mailck2 (Map<String, String> map);
	
	//비밀번호 찾기 완료
	@Select ("select mpw from member where memail = #{mmail} and mid = #{mid};")
	public String findpw (Map<String, String> map);
	
	
	
	//회원 정보
	@Select ("select * from member where mid = #{mid}")
	public memberDTO memberone (String mid);
	
	//회원 정보 - 주소 리스트
	@Select ("select * from adress where aid = #{aid} and adel=0")
	public List<adressDTO> adresslist (String mid);
	
	//회원 정보 - 주소 1건 조회
	@Select ("select * from member where ano = #{ano} and aid = #{aid}")
	public adressDTO adressone (String mid);
	
	//회원 정보 수정
	public int msetting (memberDTO dto);
	
	//회원 정보 - 주소 수정
	@Update("update adress set adetail = #{adetail} where aid = #{aid} and ano = #{ano};")
	public int asetting (adressDTO dto);
	
	//회원 정보 - 제일 작은 ano 값 찾기 - 기본 주소로 변경할 대상 찾기
	@Select ("SELECT ano FROM adress WHERE ano = (SELECT MIN(ano) FROM adress where aid = #{aid}) and aid = #{aid};")
	public int anomin (adressDTO dto);
	
	//회원 정보 - 기본 주소 설정 map
	@Update("update adress set ano = '1' where aid = #{aid} and ano = #{ano};")
	public int abasic (Map<String, String> map);
	
	//회원 정보 - 기본 주소 설정 dto
	@Update("update adress set ano = '1' where aid = #{aid} and ano = #{ano};")
	public int abasic2 (adressDTO dto);
	
	//회원 정보 - 번호 변경 (기본 주소 관련)
	@Update("update adress set ano = #{ano2} where aid = #{aid} and ano = #{ano};")
	public int anoup (Map<String, String> map);
	
	//주소 삭제
	@Update ("update adress set adel=1 where aid = #{aid} and ano =#{ano};")
	public int adel (adressDTO dto);
	
	//회원 탈퇴
	@Update ("update member set mstate = 'H2' where mid = #{mid};")
	public int mdel (String mid);
	
	//비밀번호 찾기 완료
	@Select ("select mpw from member where mid = #{mid};")
	public String findpw2 (String mid);
	
	
	
	//리뷰 리스트
	public List<reviewDTO> reviewList (String bono);
	
	//리뷰 번호 추출
	@Select("select count(rno) from review where rbookno = #{bono};")
	public int rno (String bono);
	
	//리뷰 등록
	public int newreview(reviewDTO dto);
	
	//리뷰 삭제
	@Update("update review set rdel = 1 where rno = #{rno};")
	public int rdel (String rno);
	
	//리뷰 수정
	public int upreview (reviewDTO dto);
	
	//리뷰 대댓 리스트
	public List<commentsDTO> comList (String rno);
	
	//리뷰 대댓 등록
	public int newcom(commentsDTO dto);
	
	//리뷰 대댓 삭제
	@Update("update comments set cdel = 1 where cno = #{cno};")
	public int cdel (String cno);
	
	//리뷰 대댓 수정
	public int upcom (commentsDTO dto);
	
	//사용자 공감 여부 조회
	@Select ("select sno from sympathy where sno = #{sno} and sid = #{sid};")
	public String sympathy (Map<String, String> map);
	
	//사용자 공감 추가
	@Insert("insert into sympathy (sno, sid) value (#{sno}, #{sid});")
	public int newsympathy (Map<String, String> map);
	
	//사용자 공감 삭제
	@Delete("delete from sympathy where sno = #{sno} and sid = #{sid};")
	public int delsympathy (Map<String, String> map);
	
	
	
	
	//공지사항 리스트
	@Select("select * from notice where ndel = 0 and nclass != 0 order by ndate desc;")
	public List<noticeDTO> noticelist ();
	
	//팝업 전체 리스트
	@Select("select * from notice where ndel = 0 and nclass= 0 and nenddate > now() and nstartdate < now() order by ndate asc;")
	public List<noticeDTO> poplist ();
	
	//팝업 상세 리스트
	@Select("select * from notice where ndel = 0 and nclass= 0 and nenddate > now() and nstartdate < now() and nnum = #{nnum};")
	public noticeDTO popone (int nnum);
	
	//포인트 등록
	public int pointgame (pointgameDTO dto);
	@Update("update member set mpoint =  (mpoint + #{gpoint}) where mid = #{gid};")
	public int pointgame2 (pointgameDTO dto);
	
	//포인트 게임 이력 조회
	@Select("select * from pointgame where gid = #{mid};")
	public List<pointgameDTO> mypoint (String mid);
	
	
	
	
	//문의 등록
	public int newinquiry (inquiryDTO dto);
	
	//문의 취소
	@Update ("update inquiry set idel = 1 where ino = #{ino} and iid =#{iid};")
	public int delinquiry (Map<String, String> map);
	
	//문의 내역 조회
	@Select("select * from inquiry where iid = #{iid} ORDER BY idate DESC")
	public List<inquiryDTO> inquirylist (String iid);
	//익명 문의 내역
	@Select("select iname,ino,iid,ititle,idate,idiv, IF(ipw IS NULL or ipw = '', '', '1') AS ipw, IF(ianswer IS NULL or ianswer = '', '', '1') AS ianswer from inquiry where iid = 'none' and idel = 0 ORDER BY idate DESC;")
	public List<inquiryDTO> inquirylist2 ();
	//비회원 패스워드
	@Select("select ipw from inquiry where ino = #{ino};")
	public String inquirypw (int ino);
	//비회원 내용
	@Select("select icontent, ianswer, iadate from inquiry where ino = #{ino};")
	public inquiryDTO inquirycont (int ino);
	//이메일 찾기 - 문의시 기존 멤버 이메일 찾기
	@Select("select memail from member where mid = #{mid} and mname = #{mname}")
	public String memail (Map<String, String> map);
	//유형 이름 구하기
	@Select("select codename from codetable where pid = #{pid}")
	public String pid(String pid);

	
	
}
