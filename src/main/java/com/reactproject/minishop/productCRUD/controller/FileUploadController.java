package com.reactproject.minishop.productCRUD.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.antkorwin.ioutils.resourcefile.ResourceFile;
import com.reactproject.minishop.productCRUD.service.FileManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,path = "/api/v1")
public class FileUploadController {

	
	private String filepath="C:/upload/"; //추후 profile에 따라 변경
	
	@Autowired
	private FileManager fileManager;
		
	@PostMapping("/img")
	public String uploadImg(@RequestParam("files") MultipartFile[] files, @RequestParam("category")String category) {
		System.out.println(filepath);
		StringBuilder sb = new StringBuilder();
		
		for(MultipartFile file : files ) {
			
			String folderPath = filepath+category+"/";
	        File makeFolder = new File(folderPath);
	        String fileName="";
	        if(!makeFolder.exists()) {
	        	makeFolder.mkdirs();
	        }
			
			sb.append(new Date().getTime());
			sb.append(file.getOriginalFilename().hashCode()+"."+file.getContentType().substring(6)); //이미지 경로 조정할 예정.
			
			  if (!file.isEmpty()) {
		      	    File dest = new File(folderPath + sb.toString());
		      	    fileName = dest.getName();
		      	    
		            try {
		            	fileManager.verify(file.getContentType());
		               file.transferTo(dest);
		            } catch (IllegalStateException e) {
		            	
		               e.printStackTrace();
		               return "올바르지 않은 파일 타입입니다";
		            } catch(IllegalArgumentException e) {
		            		log.info("올바르지 않은 파일 타입이 인수로 들어옴 : {}",e.getMessage());
			               return "올바르지 않은 파일 타입입니다";
		            }catch (IOException e) {
		            
		               e.printStackTrace();
		            }
		          }
				return "/file/"+fileName;
			}
		return "Not OKAY";
	}
	
	@GetMapping("/file/{category}/{imgName}")
	public String exportImgByImgname(@PathVariable String imgName,@PathVariable String category) throws NotFoundException {


		return imgName+category;
	}
}
