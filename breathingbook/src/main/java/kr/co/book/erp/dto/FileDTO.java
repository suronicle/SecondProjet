package kr.co.book.erp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileDTO {

   //file
   //파일번호
   private String fileno;
   //파일 업로드 할 게시물
   private String filepostno;
   //파일 이름
   private String filerealname;
   //파일 저장이름
   private String filesavename;
   //파일 사이즈
   private long filesize;
   //파일 삭제여부
   private String filedelete;
   //파일 업로드 날짜
   private String filesavedate;
   //파일 삭제 날짜
   private String filedeletedate;
   //파일 경로
   private String filepath;
   
   @Builder
    public FileDTO(String filerealname, String filesavename, long filesize,String filepath) {
        this.filerealname = filerealname;
        this.filesavename = filesavename;
        this.filesize = filesize;
        this.filepath = filepath;
    }

    public void setFilepostno(String filepostno) {
        this.filepostno = filepostno;
    }
   
}
