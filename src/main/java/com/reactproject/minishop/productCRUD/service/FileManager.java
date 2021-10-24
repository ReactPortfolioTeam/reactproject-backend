package com.reactproject.minishop.productCRUD.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FileManager {
	
	
	private final FileLocator fileLocator = new FileLocatorInLocal();
	private final String filepath="C:/upload/"; //추후 profile에 따라 변경

	
	public void verify(String contentType) throws IllegalArgumentException {
		
		if(!contentType.contains("image")) {
			throw new IllegalArgumentException("올바르지 않은 형식의 데이터입니다 "+contentType);
		}
	}
	
	public List<String> storeImg(MultipartFile[] files, String category) throws IllegalArgumentException,IOException  {
		StringBuilder sb = new StringBuilder();
		List<String> imgs = new ArrayList<>();
		
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
		      	    
		       
		            	verify(file.getContentType());
		            	file.transferTo(dest);
		            
		          }
			  imgs.add("/file/"+fileName);
			}
		
		return imgs;
	}
	
	public byte[] fetchImg(String name, String category) throws FileNotFoundException, IOException {
		
		System.out.println("hi");
		
		return fileLocator.fetchImgByNameAndCategory(name, category);
			
	
	}
}
