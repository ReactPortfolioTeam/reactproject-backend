package com.reactproject.minishop.productCRUD.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class FileLocatorInLocal implements FileLocator {

	private String path = "C:/upload/"; 
	
	@Override
	public byte[] fetchImgByNameAndCategory(String name, String category) throws FileNotFoundException, IOException {
			System.out.println("1");
			InputStream imageStream;
			String reactPath = path+category+"/"+name; //추후 profile 별 바꾸기
			System.out.println(reactPath);

			imageStream = new FileInputStream(reactPath);
			byte[] imageByteArray = IOUtils.toByteArray(imageStream);
			imageStream.close();	
			return imageByteArray;
	}
}
