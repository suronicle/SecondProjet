package kr.co.book.erp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.book.erp.dto.FileDTO;

@Mapper
public interface FileDAO {
	void saveAll(List<FileDTO> files);


}
