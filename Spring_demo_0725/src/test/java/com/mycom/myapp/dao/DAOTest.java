package com.mycom.myapp.dao;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.mycom.myapp.vo.MemberVO;
import com.mycom.myapp.dao.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DAOTest {

	@Inject
	private MemberDAO dao;
	
	@Test
	public void testTime(){
		try {
		System.out.println(dao.getTime());}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsert(){
		
		MemberVO vo=new MemberVO();
		vo.setUserid("user00");
		vo.setUserpw("user00");
		vo.setUsername("USER00");
		vo.setEmail("user00@naver.com");
		
		try {
		dao.insertMember(vo);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
