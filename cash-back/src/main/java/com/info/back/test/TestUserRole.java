package com.info.back.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.info.back.service.IBackUserService;
import com.info.web.pojo.BackUserRole;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={"file:src/config/applicationContext.xml"})
public class TestUserRole {

	@Autowired
	IBackUserService backUserService;
	
	
	@Test
	public void test(){
		String userId="10001";
		List<BackUserRole> backUserRoleList = backUserService.findUserRoleByUserId(userId);
		for(BackUserRole backUserRole : backUserRoleList){
			System.out.println("id:"+backUserRole.getId());
		}
		
	}
	
	@Test
	public void test2(){
		String userId="10001";
		String result = backUserService.findUserRoleIdByUserId(userId);
		System.out.println("result:"+result);
	}
	
}
