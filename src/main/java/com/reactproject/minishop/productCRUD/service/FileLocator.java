package com.reactproject.minishop.productCRUD.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileLocator {

	public byte[] fetchImgByNameAndCategory(String name, String category) throws FileNotFoundException, IOException;
}
