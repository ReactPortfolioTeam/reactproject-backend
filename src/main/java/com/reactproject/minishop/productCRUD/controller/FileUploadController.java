package com.reactproject.minishop.productCRUD.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reactproject.minishop.common.responseType.ResponseTypeForCommonErrorWithOnlyAMsg;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonSuccessWithImgPath;
import com.reactproject.minishop.productCRUD.service.FileManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin("*")
public class FileUploadController {

	
	
	@Autowired
	private FileManager fileManager;
		
	@PostMapping("/img")
	public ResponseEntity<?> uploadImg(@RequestParam(value = "files") MultipartFile[] files, @RequestParam("category")String category) {
		try {
			List<String> result = fileManager.storeImg(files, category);
			ResponseTypeForCommonSuccessWithImgPath msg = new ResponseTypeForCommonSuccessWithImgPath();
			msg.setIssuedAt(new Date());
			msg.setStatusCode(201);
			msg.setMsg("저장에 성공하였습니다");
			msg.setImgPath(result);
			
			return new ResponseEntity<ResponseTypeForCommonSuccessWithImgPath>(msg,HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
			msg.setIssuedAt(new Date());
			msg.setStatusCode(400);
			msg.setMsg(e.getMessage());
			
			return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@GetMapping(value="/file/{category}/{imgName}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
	public byte[] exportImgByImgname(@PathVariable String imgName,@PathVariable String category) throws FileNotFoundException, IOException{
		return fileManager.fetchImg(imgName, category);
	}
	
	@ExceptionHandler(value =  {FileNotFoundException.class,IOException.class})
	public ResponseEntity<?> handlerForIOException(IOException e){
		
		ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
		msg.setIssuedAt(new Date());
		msg.setStatusCode(400);
		msg.setMsg(e.getMessage());
		
		return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg, HttpStatus.BAD_REQUEST);
	}
	
	
}
