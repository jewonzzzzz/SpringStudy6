package com.itwillbs.service;

import com.itwillbs.domain.MemberVO;

public interface MemberService {

	// 사용자의 처리 로직을 구현
	
	
	// 회원가입 동작
	public void memberJoin(MemberVO vo);
	
	// 회원 로그인 체크
	public MemberVO memberLoginCheck(MemberVO vo);
	
	
	
	
}
