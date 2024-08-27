package kr.co.book.erp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.book.ecom.dto.BookDTO;
import kr.co.book.ecom.dto.memberDTO;
import kr.co.book.erp.dto.BranchDTO;

@Mapper

public interface BranchDAO {
	public int comInsert(BranchDTO bdto);
	public int comAdrInsert(BranchDTO bdto);
	public List<BranchDTO> branchList();
	public List<BranchDTO> vendorList();
	public List<BranchDTO> searchCom();
	public BranchDTO comSelect(String brno);
	public int comUpdate(BranchDTO bdto);
	public void changePw(@Param("brno") String brno, @Param("cpw2") String cpw2);
	public int comDelete(String brno);
	
	// 다음 brno 값 가져오기
	public int getNextBrno(int brclass);
	
	// 추가
	
	//회원정보
	public List<memberDTO> memList();
	public memberDTO viewMem(String mid);
	public int memDelete(String mid);
}
	