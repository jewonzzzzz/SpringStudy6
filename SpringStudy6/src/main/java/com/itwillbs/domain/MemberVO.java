package com.itwillbs.domain;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 
 *  VO(Value Object) : 데이터 저장 객체 (값을 저장하는 동작 이외의 동작 포함)
 *  DTO(Data Transfer Object) : 데이터 전송 객체 (값을 저장하는 동작 이외의 동작 X)
 *
 * tbl_member 테이블정보를 저장하는 객체
 */

//@Data : set/get메서드 자동생성
@Data
public class MemberVO {
	
	//private String uId; //getUId() x
	// 변수명은 소문자로

	private String userid;
	private String userpw;
	private String username;
	private String useremail;
	private Timestamp regdate;
	private Timestamp updatedate;
	
	
	
}
