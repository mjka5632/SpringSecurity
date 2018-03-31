package com.imooc.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * @author ruotian
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	/*
	 * 配置一个@AutoConfigureMockMvc注解就可以省去这些代码， ------注意----MockMvc头顶需要带上@Autowired这个注解
	 * 
	 * @Autowired private WebApplicationContext wac;
	 * 
	 * @Before public void setup() { mockMvc =
	 * MockMvcBuilders.webAppContextSetup(wac).build(); }
	 * 
	 */
	@Autowired
	// 伪造一个MVC的环境，不会真的启动Tomcat
	private MockMvc mockMvc;

	/**
	 * 上传
	 */
	@Test
	public void whenUploadSuccess() throws  Exception{
		String result=mockMvc.perform(fileUpload("/file")
				.file(new MockMultipartFile("file","test.txt","multipart/form-data","hello upload".getBytes("UTF-8"))))
				.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

	@Test
	public void whenQuerySuccess() throws Exception {
		String result = mockMvc.perform(get("/user").param("username", "mrt").param("age", "10").param("xxx", "yyyy")
				// .param("size", "15")
				// .param("page", "12")
				// .param("sort", "username,desc")
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3)).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}

	@Test
	public void whenGetInfoSuccess() throws Exception {
		String result = mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username").value("tom")).andReturn().getResponse()
				.getContentAsString();
		System.out.println(result);
	}

	@Test
	public void whenGetInfoFaile() throws Exception {
		mockMvc.perform(get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError());

	}
	
	@Test
	public void createSuccess() throws Exception {
		Date date=new Date();
		System.out.println(date.getTime());
		String content="{\"username\":\"tom\",\"password\":null,\"birthDay\":"+date.getTime()+"}";
		String result=mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"))
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);
		
	}
	
	@Test
	public void updateSuccess() throws Exception {
		Date date=new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		System.out.println(date.getTime());
		String content="{\"id\":\"1\",\"username\":\"tom\",\"password\":null,\"birthDay\":"+date.getTime()+"}";
		String result=mockMvc.perform(put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"))
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);
		
	}

	@Test
	public void deleteSuccess() throws Exception{
		mockMvc.perform(delete("/user/1")
		.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
}
