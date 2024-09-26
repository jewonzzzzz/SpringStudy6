package com.itwillbs.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.domain.MemberVO;
import com.itwillbs.persistence.MemberDAO;
import com.itwillbs.service.MemberService;

// @RequestMapping(value = "/member/*")
// => 특정 동작의 형태를 구분 (~.me, ~.bo와 유사)

@Controller
@RequestMapping(value = "/member/*")
public class MemberController {
	
//	@Inject
//	private MemberDAO mdao;
	
	@Autowired
	private MemberService mService;
	
//	@Autowired
//	public MemberController(MemberService mService) {
//		this.mService = mService;
//	}
	
	
	

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//http://localhost:8088/web/test 
	//http://localhost:8088/web/member/test (x)
	//http://localhost:8088/member/test (o)
//	@RequestMapping(value = "/test", method=RequestMethod.GET)
//	public void test() {
//		logger.debug("test() 실행");
//	}
	
	// 회원가입 - 정보입력
	// http://localhost:8088/member/join
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void joinMemberGet() {
		logger.debug(" /join -> joinMemberGet() 실행");
		logger.debug(" 연결된 뷰(JSP)를 보여주기");
		// 페이징 이동(x) -> 스프링이 자동으로 연결
		logger.debug(" /views/member/join.jsp 뷰페이지 연결");
		
	}
	
	// 회원가입 - 정보처리
	// http://localhost:8088/member/joinMemberAction
	//@RequestMapping(value = "joinMemberAction", method = RequestMethod.GET) -> 오류
	//@RequestMapping(value = "joinMemberAction") -> 오류는 없지만 POST 작성해주는 걸로
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinMemberPost(MemberVO vo) {
		logger.debug(" /member/join -> joinMemberPost() 실행");
		// 한글 인코딩처리 => web.xml 필터로 처리
		
		// 전달정보(파라메터) 저장
		logger.debug(" vo : "+vo);
		
		// DB 객체 생성 - 회원가입
		// MemberDAO 객체 생성 => 객체 주입
		// mdao.insertMember(vo); (x - 서비스로 사용할꺼임)
		
		// MemberService 객체를 주입 -> 해당동작 수행
		
		mService.memberJoin(vo);
		
		logger.debug(" 회원가입 성공!");
		logger.debug(" 로그인 페이지로 이동 /member/login");
		
		return "redirect:/member/login";
	}
	
	// 로그인 처리 - 입력(GET)
	// http://localhost:8088/member/login
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginMemberGet() {
		logger.debug(" /member/login -> loginMemberGet() 실행");
		logger.debug(" 연결된 뷰페이지(jsp) 출력");
		return "/member/loginForm";
	}
	
	//public String loginMemberPost(@RequestParam("userid") String userid,
	//@ModelAttribute("userpw") String userpw) {
	// 로그인 처리 - 처리(POST)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginMemberPost(MemberVO vo, HttpSession session) {
		logger.debug(" /member/login(post) -> loginMemberPost() 실행");
		
		// 전달정보 저장(userid, userpw)
		logger.debug(" vo : "+vo);
		//logger.debug(" id : "+ userid+ ", pw : "+userpw);
		
		// 서비스 -> 회원정보 확인 -> DAO 호출
		MemberVO resultVO = mService.memberLoginCheck(vo);
		
		if(resultVO == null) {
			// 로그인 실패! -> 로그인 페이지로 이동
			return "redirect:/member/login";
		}
		
		// 사용자의 id 정보를 세션영역에 저장
		session.setAttribute("id", resultVO.getUserid());
		
		//logger.debug(" resulutVO : "+resultVO);
		// => main페이지로 정보가져가려면 model 필요
		
		//로그인 성공! -> 메인페이지 이동
		return "redirect:/member/main";
	}
	
	//메인페이지 - GET
	// http://localhost:8088/member/main
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void mainMemberGet() {
		logger.debug(" /member/main -> mainMemberGet() 실행");
		logger.debug(" 연결된 뷰페이지(views/member/main.jsp)로 이동");
	}
	
	//로그아웃 - GET(정보입력, 화면조회/출력)/POST(처리-insert,update,delete..)
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String memberlogoutGet(HttpSession session) {
		logger.debug(" /member/logout -> memberlogoutGet() 실행");
		
		// 로그아웃 => 세션정보 초기화
		session.invalidate();
		logger.debug(" 사용자 정보 로그아웃!");

		// 페이지 이동
		return "redirect:/member/main";
	}
	
	// 회원정보 조회 - GET
	//@RequestMapping(value = "/info", method = RequestMethod.GET)
	@GetMapping(value = "/info")
	public void infoMemberGET(HttpSession session, Model model) {
		logger.debug(" /member/info -> infoMemberGET() 실행 ");
		
		// 아이디 정보가 필요함
		String userid = (String) session.getAttribute("id");
		logger.debug(" 아이디 : "+ userid);
		// 서비스 -> DAO : 특정 아이디를 사용해서 회원정보 조회
		MemberVO resultVO = mService.memberInfo(userid);
		logger.debug(" vo : "+resultVO);
		
		// 서비스에서 가져온 데이터를 연결된 뷰페이지에 전달해서 출력
		model.addAttribute(resultVO);
		// 이름이 없을 경우 첫글자를 소문자로 바꾼 클래스명 사용
		logger.debug(" 연결된 뷰페이지로 이동 (/member/info.jsp)");
	}
	
	//회원정보 수정 - 입력 GET
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateMemberGET(HttpSession session, Model model) {
		logger.debug(" /member/update -> updateMemberGET() 실행");
		
		String id = (String) session.getAttribute("id");
		logger.debug(" 아이디 : "+id);
		
		logger.debug(" 기존의 회원정보를 DB에서 가져오기");
		// 서비스 -> DAO : 회원정보 가져오기
		MemberVO resultVO = mService.memberInfo(id);
//		logger.debug(" vo : "+resultVO);
//		model.addAttribute(resultVO);
		
		model.addAttribute(mService.memberInfo(id));
		
		logger.debug(" 연결된 뷰페이지 출력(/views/member/update.jsp");
		
		return "/member/update";
	}
	
	//회원정보 수정 - 처리 POST
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateMemberPOST(/* @ModelAttribute */ MemberVO vo) {
		logger.debug("/member/update -> updateMemberPOST() 실행");
		// 한글처리 인코딩 => web.xml filter 처리
		logger.debug(" 전달받은 정보(파라메터)를 저장");
		logger.debug(" vo : "+vo);
		
		// 서비스를 사용해서 DAO : 전달받은 정보를 사용해서 정보 수정
		int result = mService.memberUpdate(vo);
		
		if(result == 0) {
			// SQL-update 실행결과가 없음(수정X)
			return "redirect:/member/update";
		}
		
		//수정성공
		return "redirect:/member/main";
	}
	
	// 회원정보 삭제 - 정보입력(GET)
	@GetMapping(value = "/delete")
	public void deleteMemberGET() {
		logger.debug("/member/delete -> deleteMemberGET()");
		logger.debug(" 연결된 뷰페이지(views/member/delete.jsp) 이동");
	}
	
	// 회원정보 삭제 - 정보처리(POST)
	@PostMapping(value = "/delete")
	public String deleteMemberPOST(MemberVO vo, HttpSession session) {
		logger.debug("/member/delete -> deleteMemberPOST()");
		logger.debug(" 전달받은 정보 :"+vo);
		
		// 서비스 -> DAO : 회원정보 삭제
		int result = mService.memberDelete(vo);
		
		if(result == 0) {
			return "redirect:/member/delete";
		}
		// 세션객체 정보 초기화
		session.invalidate();
		
		// 페이지 이동
		return "redirect:/member/main";
	}
	
	// 회원정보목록 조회 - GET
	@GetMapping(value = "/list")
	public void listMemberGET(Model model) {
		logger.debug(" /member/list -> listMemberGET() 실행");
		
		// 로그인 (세션정보) => 생략
		// 서비스 -> DAO : 회원목록정보 가져오기
		List<MemberVO> memberList = mService.memberList();
		model.addAttribute("memberList", memberList);
		
		// 연결된 view페이지(/member/list.jsp)로 전달해서 출력
	}
	
}
