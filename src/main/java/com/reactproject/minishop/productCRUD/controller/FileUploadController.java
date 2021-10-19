package com.reactproject.minishop.productCRUD.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,path = "/api/v1/img")
public class FileUploadController {

	
	private String filepath="C://upload/"; //추후 profile에 따라 변경
		
	@PostMapping
	public String uploadImg(@RequestParam("files") MultipartFile[] files, @RequestParam("category")String category) {
		System.out.println(filepath);
		StringBuilder sb = new StringBuilder();
		
		for(MultipartFile file : files ) {
			
			
			//보안요소 관리할 서비스 계층 추가 예정
			String folderPath = filepath+category+"/";
	        File makeFolder = new File(folderPath);
	        String fileName="";
	        if(!makeFolder.exists()) {
	        	makeFolder.mkdirs();
	        }
			
			System.out.println(file.getOriginalFilename());
			sb.append(new Date().getTime());
			sb.append(file.getOriginalFilename().hashCode()+"."+file.getContentType().substring(6)); //이미지 경로 조정할 예정.
			
			  if (!file.isEmpty()) {
		      	    File dest = new File(folderPath + sb.toString());
		      	    fileName = dest.getName();
		      	    
		      	    
		            try {
		               file.transferTo(dest);
		            } catch (IllegalStateException e) {
		               e.printStackTrace();
		            } catch (IOException e) {
		               e.printStackTrace();
		            }
		          }
				return "/file/"+fileName;
			}
		return "Not OKAY";
	}
}
