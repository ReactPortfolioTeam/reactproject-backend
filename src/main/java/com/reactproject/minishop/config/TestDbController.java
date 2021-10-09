package com.reactproject.minishop.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestDbController {
	  @Autowired
	   private TestDbDao testDAO;

	    @GetMapping("/hello")
	    public List<TestDbVo> HelloWorld() {
	        return testDAO.getTestData();
	    }
}
