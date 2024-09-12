package com.itwillbs.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.MemberVO;

/*
 *  MemberDAO 동작을 수행
 */

//@Repository : 스프링이 해당클래스를 DAO객체로 (Bean) 인식
//              root-context.xml 파일에서 해당 객체를 사용하도록 설정  
@Repository
public class MemberDAOImpl implements MemberDAO{
	
	// 공통변수, 디비연결, 자원해제
	// 디비연결객체(SqlSessionFactory) 필요 -> 의존관계 주입
	//	@Inject
	//	private SqlSessionFactory sqlFactory;
	
	@Inject
	private SqlSession sqlSession; // 자동으로 연결, 자원해제, SQL 실행, Mybatis...
	
	// Mapper namespace 정보 저장
	private static final String NAMESPACE = "com.itwillbs.mapper.MemberMapper";
	
	@Override
	public String getTime() {
		System.out.println("DAO : getTime() 실행");
		
		// 1.2. 디비연결
		// SqlSession sqlSession = sqlFactory.openSession();
		// 3. SQL구문 & pstmt 객체
		// 4. SQL 실행
		//sqlFactory.selectOne(SQL구문);
		//sqlFactory.selectOne(SQL구문, 전달정보);
		String result = sqlSession.selectOne("com.itwillbs.mapper.MemberMapper.getTime");
		  // => Mapper의 sql구문 id를 호출, 직접 적인 SQL 구문 호출은 불가
		// 5. 데이터 처리
		System.out.println("결과 : "+result);
		
		return result;
	}
	
	@Override
	public void insertMember(MemberVO vo) {
		System.out.println(" DAO : 회원가입 동작 실행");
		 
		//1.2. 디비연결 => 생략 SqlSession객체 수행
		//3. sql 구문 (mapper생성) & pstmt 객체 (mybatis 관리)
		//4. sql 실행
		// [com.itwillbs.mapper.MemberMapper.insertMember]
		int result = sqlSession.insert(NAMESPACE + ".insertMember", vo);
		
		System.out.println(" DAO : "+result);
		System.out.println(" DAO : 회원가입 완료");

	}
	
	@Override
	public MemberVO loginMember(MemberVO vo) {
		System.out.println(" DAO : loginMember(MemberVO vo) 실행");
		
		// sql 구문을 mapper에 생성
		System.out.println(" DAO : mapper SQL 생성완료");
		// sql 구문 실행
		MemberVO resultVO = sqlSession.selectOne(NAMESPACE+".loginMember", vo);
		
		System.out.println(" DAO : "+resultVO);
		
		return resultVO;
	}
	
	@Override
	public MemberVO loginMember(String userid, String userpw) {
		System.out.println(" DAO : loginMember(String userid, String userpw) 실행");
		
		//sqlSession.selectOne(NAMESPACE+".loginMember", ); (x)
		
//		MemberVO vo = new MemberVO();
//		vo.setUserid(userid);
//		vo.setUserpw(userpw);
//		
//		MemberVO resultVO = sqlSession.selectOne(NAMESPACE+".loginMember", vo);
		// => 전달받은 정보를 하나의 공통 객체에 저장 => 전달할때 객체로 전달
		
		// * 가정 : userid(회원가입), userpw(게시판)는 하나의 객체(MemberVO)에 저장이 불가능
		// => Join 구문 실행 시 사용함(테이블이 다를 때)
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("mapper에서 호출하는 이름", 전달될 값)
		paramMap.put("userid", userid);
		paramMap.put("userpw", userpw);
		
		MemberVO resultVO = sqlSession.selectOne(NAMESPACE+".loginMember", paramMap);
		//resultVO.setUserid(userid+"@@@"); 보통 변경할 수 있는 것들은 변수로 저장 후 처리
		return resultVO;
	}
	
	@Override
	public MemberVO getMember(MemberVO vo) {
		System.out.println(" DAO : memberInfo(MemberVO vo) 실행");
		
		// sql 구문 mapper에 생성
		System.out.println(" DAO : mapper SQL 생성완료");
		// sql 실행
		//MemberVO resultVO = sqlSession.selectOne(NAMESPACE+".getMember", vo);
		
		return sqlSession.selectOne(NAMESPACE+".getMember", vo);
	}
	
	@Override
	public int updateMember(MemberVO uvo) {
		System.out.println(" DAO : updateMember(MemberVO uvo) 실행");
		// sql 구문 mapper 생성
		// sqlSession 실행 (결과에 따른 정수데이터 리턴)
		return sqlSession.update(NAMESPACE+".updateMember", uvo);
	}
	
	@Override
	public Integer deleteMember(MemberVO dvo) {
		System.out.println(" DAO : deleteMember(MemberVO vo) 실행");
		// sql 구문 mapper 생성
		// sqlSession 실행 (결과에 따른 정수데이터 리턴)
		return sqlSession.delete(NAMESPACE+".deleteMember", dvo);
	}
	
	@Override
	public List<MemberVO> getMemberList() {
		System.out.println(" DAO : getMemberList() 실행");
		// sql 구문 mapper 생성
		// sqlSession 실행 (결과에 따른 정수데이터 리턴)
		return sqlSession.selectList(NAMESPACE+".getMemberList");
	}
	
}
