package kr.co.book.erp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import kr.co.book.erp.dto.FileDTO;

@Component
public class FileUtil {

   private final String uploadPath = Paths.get("C:", "breathingbook", "breathingbook", "src", "main", "resources", "static", "e-comm", "images", "book").toString();

   public List<FileDTO> uploadFiles(final List<MultipartFile> multipartFiles) {
      List<FileDTO> files = new ArrayList<>();
      for (MultipartFile multipartFile : multipartFiles) {
         if (multipartFile.isEmpty()) {
            continue;
         }
         files.add(uploadFile(multipartFile));
      }
      return files;
   }

   public FileDTO uploadFile(final MultipartFile multipartFile) {

      if (multipartFile.isEmpty()) {
         return null;
      }

      String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
      String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
      String uploadPath = getUploadPath(today) + File.separator + saveName;
      File uploadFile = new File(uploadPath);
      System.out.println(uploadPath);

      try {
         multipartFile.transferTo(uploadFile);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      return FileDTO.builder()
            .filerealname(multipartFile.getOriginalFilename())
            .filesavename(saveName)
            .filesize(multipartFile.getSize())
            .filepath(uploadPath)
            .build();
   }

   private String generateSaveFilename(final String filerealname) {
      String uuid = UUID.randomUUID().toString().replaceAll("-", "");
      String extension = StringUtils.getFilenameExtension(filerealname);
      return uuid + "." + extension;
   }

   private String getUploadPath() {
      return makeDirectories(uploadPath);
   }

   private String getUploadPath(final String addPath) {
      return makeDirectories(uploadPath + File.separator + addPath);
   }

   private String makeDirectories(final String path) {
      File dir = new File(path);
      if (dir.exists() == false) {
         dir.mkdirs();
      }
      return dir.getPath();
   }


}
