package com.itwillbs.service;

import java.util.List;

import com.itwillbs.domain.MemberVO;

public interface MemberService {

	// 사용자의 처리 로직을 구현
	
	
	// 회원가입 동작
	public void memberJoin(MemberVO vo);
	
	// 회원 로그인 체크
	public MemberVO memberLoginCheck(MemberVO vo);
	
	// 회원 정보 조회
	public MemberVO memberInfo(String userid);
	
	// 회원정보 수정
	public int memberUpdate(MemberVO uvo);
	
	// 회원정보 삭제
	public int memberDelete(MemberVO dvo);
	
	// 회원정보목록 조회
	public List<MemberVO> memberList();
	
	
	
	
}
