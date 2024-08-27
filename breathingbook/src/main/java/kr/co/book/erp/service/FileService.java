package kr.co.book.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.co.book.erp.dao.FileDAO;
import kr.co.book.erp.dto.FileDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	
	private final FileDAO fDao;
	
	@Transactional
	public void saveFiles(final String filepostno, final List<FileDTO> files) {
		System.out.println(files);
		
        if (CollectionUtils.isEmpty(files)) {
        	System.out.println("nope");
            return;
        }
        for (FileDTO file : files) {
        	System.out.println(filepostno);
            file.setFilepostno(filepostno);
            
        }
        fDao.saveAll(files);
    }
}
