package kr.co.book.erp.dao;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import kr.co.book.ecom.dto.BookDTO;


@Mapper

public interface HmainDAO {
	public int countVen();
	public BookDTO bestVen();
	public int countBra();
	public BookDTO bestBra();
	public int countBook();
	public BookDTO bestBook();
	public BookDTO lastmonth();
	public BookDTO curmonth();
	public List<BookDTO> getMonthlySales();
	public List<BookDTO> getMonthlyBuy();
}
	