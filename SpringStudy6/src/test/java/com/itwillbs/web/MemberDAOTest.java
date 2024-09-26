package com.itwillbs.web;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itwillbs.domain.MemberVO;
import com.itwillbs.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})

public class MemberDAOTest {

	// MemberDAO객체의 메서드 호출
	@Inject
	private MemberDAO mdao;
	
	
	public void getBean() {
		System.out.println("mdao : "+mdao);
	}
	
	//@Test
	public void getTime() {
		mdao.getTime();
	}
	
	//@Test
	public void 회원가입테스트() {
		System.out.println(" Test : 회원가입테스트() 시작");
		
		// 회원가입정보
		MemberVO vo = new MemberVO();
		vo.setUserid("itwill1");
		vo.setUserpw("1234");
		vo.setUsername("아이티윌1");
		vo.setUseremail("itwill1@admin.com");
		
		mdao.insertMember(vo);
		
		System.out.println(" Test : 회원가입테스트() 끝");
	}
	
	//@Test
	public void 로그인테스트() {
		
		System.out.println(" Test : 로그인테스트() 시작");
		
		MemberVO vo = new MemberVO();
		vo.setUserid("admin");
		vo.setUserpw("1234");
		
		MemberVO resultVO = mdao.loginMember(vo);
		System.out.println(" Test : "+resultVO);
		
		if(resultVO != null) {
			System.out.println(" Test : 로그인 성공");
		} else {
			System.out.println(" Test : 로그인 실패");
		}
		
		System.out.println(" Test : 로그인테스트() 끝");
		
	}
	
	
	//@Test
	public void 로그인테스트2() {
		
		System.out.println(" Test : 로그인테스트() 시작");
		
		MemberVO resultVO = mdao.loginMember("admin", "1234");;
		System.out.println(" Test : "+resultVO);
		
		if(resultVO != null) {
			System.out.println(" Test : 로그인 성공");
		} else {
			System.out.println(" Test : 로그인 실패");
		}
		
		System.out.println(" Test : 로그인테스트() 끝");
	}
	
	// 회원정보 조회
	//@Test
	public void 사용자정보조회() {
		System.out.println(" Test : 사용자정보조회() 시작");
				
		String id = "admin";
		
		MemberVO resultVO = mdao.getMember(id);
		
		if(resultVO != null) {
			System.out.println(" Test : 사용자 정보있음");
		} else {
			System.out.println(" Test : 사용자 정보없음");
		}
		
		System.out.println(" Test : "+resultVO);
		System.out.println(" Test : 사용자정보조회() 끝");
	}
	
	//@Test
	public void 사용자정보수정() {
		
		MemberVO uvo = new MemberVO();
		uvo.setUserid("admin");
		uvo.setUserpw("1234");
		uvo.setUsername("수정관리자");
		uvo.setUseremail("uadmin@admin.com");
		
		int result = mdao.updateMember(uvo);
		System.out.println(" Test : "+result);
		
		if(result > 0) {
			System.out.println(" 회원정보 수정 성공");
		} else {
			System.out.println(" 회원정보 수정 실패");
		}
	}
	
	// 회원정보 삭제
	//@Test
	public void 사용자정보삭제() {
		
		MemberVO dvo = new MemberVO();
		dvo.setUserid("itwill1");
		dvo.setUserpw("1234");
		
		int result = mdao.deleteMember(dvo);
		
		if(result > 0) {
			System.out.println(" Test : 회원정보 삭제 성공");
		} else {
			System.out.println(" Test : 회원정보 삭제 실패");
		}
	}
	
	// 회원정보리스트 조회
	@Test
	public void 사용자정보리스트조회() {
		List<MemberVO> memberList = mdao.getMemberList();
		
		for(MemberVO vo: memberList) {
			System.out.println("vo : "+vo);
		}
	}
}
