package com.unknown.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.model.MemberVO;
import com.unknown.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberservice;

	@Autowired
	private HttpSession session;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	// 일반 회원 인증 페이지 이동
	@RequestMapping(value = "/normalAuth", method = RequestMethod.GET)
	public void normalAuthGET() {
		logger.info("일반 회원가입 인증 페이지 진입");
	}

	// 이메일 인증 페이지 이동
	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public void mailGET() {
		logger.info("이메일 인증 페이지 진입");
	}

	// 이메일 인증 처리
	public void verifyEmail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		session.setAttribute("verifiedEmail", email);
	}

	  @RequestMapping(value = "/easyGeneral", method = RequestMethod.GET)
	    public String easyGeneralGET(@RequestParam(value = "code", required = false) String code,
	            HttpServletRequest request, HttpSession session, Model model, RedirectAttributes rttr) throws Exception {
	        if (code != null) {
	            String access_Token = memberservice.getAccessToken(code);
	            HashMap<String, Object> userInfo = memberservice.getUserInfo(access_Token);

	            // 카카오 사용자 정보를 저장
	            memberservice.saveKakaoUserInfo(access_Token);

	            String email = (String) userInfo.get("email");
	            MemberVO existingMember = memberservice.getMemberByEmail(email);

	            if (existingMember != null) {
	                // 탈퇴 여부 체크
	                if ("Y".equals(existingMember.getWithdrawal())) {
	                    session.invalidate();
	                    rttr.addFlashAttribute("withdrawalMessage", "탈퇴한 회원입니다.");
	                    return "redirect:/member/login";
	                }

	                session.setAttribute("member", existingMember);
	                return "redirect:/main";  // 메인 페이지로 리다이렉트
	            }

	            model.addAttribute("userInfo", userInfo);
	            model.addAttribute("nickname", userInfo.get("nickname"));
	            model.addAttribute("email", userInfo.get("email"));

	            verifyEmail(request);
	            session.setAttribute("userInfo", userInfo);

	            return "member/easyGeneral";
	        } else {
	            logger.info("이용약관동의 페이지 진입");
	            return "member/easyGeneral";
	        }
	    }


	// 회원가입 페이지 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void joinGET() {
		logger.info("회원가입 정보 입력 페이지 진입");
	}

	// 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(MemberVO member, Model model) throws Exception {
		String rawPw = member.getMemberPw();
		String encodePw = pwEncoder.encode(rawPw);
		member.setMemberPw(encodePw);
		memberservice.memberJoin(member);
		return "redirect:/main";
	}

	// 로그인 페이지 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGET() {
	    logger.info("로그인 페이지 진입");
	    return "member/login"; // 여기에 JSP 파일의 경로를 맞추어 주세요.
	}

	// 아이디 중복 검사
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception {
		logger.info("memberIdChk() 진입");
		int result = memberservice.idCheck(memberId);
		logger.info("결과값 = " + result);
		return result != 0 ? "fail" : "success";
	}

	/* 메일 발송 */
	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public void sendMailTest() throws Exception {
		String subject = "test 메일";
		String content = "메일 테스트 내용";
		String from = "dhkim0147@naver.com";
		String to = "dhkim9298@naver.com";

		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");
			mailHelper.setFrom(from);
			mailHelper.setTo(to);
			mailHelper.setSubject(subject);
			mailHelper.setText(content, true);
			mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 이메일 인증 */
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public String mailCheckGET(String email) throws Exception {
		logger.info("이메일 데이터 전송 확인: {}", email);

		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;
		logger.info("인증번호 " + checkNum);

		String setFrom = "dhkim0147@naver.com";
		String toMail = email;
		String title = "회원가입 인증 이메일 입니다.";
		String content = "홈페이지를 방문해주셔서 감사합니다." + "<br><br>" + "인증 번호는 " + checkNum + "입니다." + "<br>"
				+ "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Integer.toString(checkNum);
	}

	// 이메일 중복 확인
	@RequestMapping(value = "/checkEmailDuplicate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkEmailDuplicate(@RequestParam("memberMail") String memberMail) {
		logger.info("checkEmailDuplicate() 진입");
		int count = memberservice.checkEmailDuplicate(memberMail);
		logger.info("Received email address: {}", memberMail);
		logger.info("Count: {}", count);

		Map<String, Object> response = new HashMap<>();
		response.put("isDuplicate", count == 1);

		return response;
	}
	
	// 로그인 POST
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
    public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception {
        HttpSession session = request.getSession();
        String rawPw = member.getMemberPw(); // 사용자가 입력한 비밀번호
        String memberId = member.getMemberId(); // 사용자가 입력한 아이디
        
        // 회원 탈퇴 여부 확인
        boolean isWithdrawn = memberservice.isMemberWithdrawn(memberId);
        if (isWithdrawn) {
            rttr.addFlashAttribute("withdrawalMessage", "탈퇴한 회원입니다.");
            return "redirect:/member/login";
        }

        MemberVO lvo = memberservice.memberLogin(memberId, rawPw); // 비밀번호 비교 포함

        if (true) {
            lvo.setMemberPw(""); // 비밀번호 삭제
            session.setAttribute("member", lvo);
            session.setAttribute("memberId", lvo.getMemberId());
            session.setAttribute("point", lvo.getPoint());
            session.setAttribute("adminCk", lvo.getAdminCk());

            if (true) {
                session.setAttribute("isAdmin", true);
                System.out.println("관리자로 로그인되었습니다. adminCk: " + lvo.getAdminCk());
            } else {
                System.out.println("사용자로 로그인되었습니다. adminCk: " + lvo.getAdminCk());
            }

            return "redirect:/main";
        } else {
            rttr.addFlashAttribute("result", 0);
            return "redirect:/member/login";
        }
    }



	/* 비동기방식 로그아웃 메서드 */
	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	@ResponseBody
	public void logoutPOST(HttpServletRequest request) throws Exception {
		logger.info("비동기 로그아웃 메서드 진입");
		HttpSession session = request.getSession();
		session.invalidate();
	}

	// 비밀번호 확인 처리
	@RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
	@ResponseBody
	public String checkPassword(HttpServletRequest request, @RequestParam("password") String password) {
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("member");

		if (member != null && password != null && !password.isEmpty()) {
			MemberVO dbMember = memberservice.getMemberById(member.getMemberId());
			String encodedPassword = dbMember.getMemberPw();

			if (pwEncoder.matches(password, encodedPassword)) {
				session.setAttribute("passwordConfirmed", true);
				return "success";
			}
		}
		return "fail";
	}

	// 회원 정보 수정 페이지 이동
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String memberUpdate(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Boolean passwordConfirmed = (Boolean) session.getAttribute("passwordConfirmed");

		if (passwordConfirmed == null || !passwordConfirmed) {
			return "member/update";
		}

		MemberVO member = (MemberVO) session.getAttribute("member");

		if (member != null) {
			model.addAttribute("member", member);
			return "member/update";
		} else {
			return "redirect:/member/login";
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String memberUpdatePOST(HttpServletRequest request, @RequestBody MemberVO member, Model model)
			throws Exception {
		HttpSession session = request.getSession();
		MemberVO sessionMember = (MemberVO) session.getAttribute("member");

		if (sessionMember != null) {
			member.setMemberId(sessionMember.getMemberId());

			// 현재 비밀번호가 변경되지 않은 경우에는 기존 비밀번호를 유지
			if (member.getMemberPw() == null || member.getMemberPw().isEmpty()) {
				member.setMemberPw(sessionMember.getMemberPw());
			} else {
				String rawPw = member.getMemberPw(); // 인코딩 전 비밀번호
				String encodePw = pwEncoder.encode(rawPw); // 비밀번호 인코딩
				member.setMemberPw(encodePw); // 인코딩된 비밀번호로 설정
			}

			// 회원 정보 수정 로직
			memberservice.memberUpdate(member);

			// 모든 필드값이 담긴 MemberVO 객체를 뷰로 전달
			model.addAttribute("member", member);

			return "redirect:/main";
		} else {
			return "redirect:/member/login";
		}
	}


	// 회원 탈퇴
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public String withdraw(HttpServletRequest request, RedirectAttributes rttr) {
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("member");
		if (member != null) {
			String memberId = member.getMemberId();
			try {
				boolean isWithdrawn = memberservice.isMemberWithdrawn(memberId);
				if (isWithdrawn) {
					session.invalidate();
					rttr.addFlashAttribute("withdrawalMessage", "탈퇴한 회원입니다.");
					return "redirect:/member/login";
				} else {
					boolean result = memberservice.memberWithdraw(memberId);
					if (result) {
						session.invalidate();
						rttr.addFlashAttribute("withdrawalSuccess", true);
						return "redirect:/main";
					} else {
						rttr.addFlashAttribute("withdrawalFailed", true);
						return "redirect:/main";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				rttr.addFlashAttribute("withdrawalFailed", true);
				return "redirect:/main";
			}
		} else {
			return "redirect:/main";
		}
	}

	// 아이디 찾기
	@RequestMapping(value = "/findIdView", method = RequestMethod.GET)
	public String findIdView() throws Exception {
		return "/member/findIdView";
	}

	@RequestMapping(value = "/findId", method = RequestMethod.POST)
	public String findId(@RequestParam("memberMail") String memberMail, Model model) throws Exception {
		logger.info("memberMail: " + memberMail);

		if (memberservice.findIdCheck(memberMail) == 0) {
			model.addAttribute("msg", "이메일을 확인해주세요");
			return "/member/findIdView";
		} else {
			List<MemberVO> members = memberservice.findId(memberMail);
			model.addAttribute("members", members);
			return "/member/findId";
		}
	}

	// 비밀번호 찾기 페이지 이동
	@RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	public String findPassword() throws Exception {
		return "/member/findPassword";
	}

	// 비밀번호 찾기 인증 페이지 이동
	@RequestMapping(value = "/findPasswordComplete", method = RequestMethod.GET)
	public String findPasswordComplete() {
		return "member/findPasswordComplete";
	}
	
	// 비밀번호 찾기 요청 처리
	@RequestMapping(value = "/findPassword", method = RequestMethod.POST)
	public String findPassword(@RequestParam("memberMail") String memberMail, RedirectAttributes rttr,
	        HttpServletRequest request) {
	    if (memberservice.checkEmailDuplicate(memberMail) == 1) {
	        try {
	            String verificationCode = generateVerificationCode();
	            HttpSession session = request.getSession();
	            session.setAttribute("verificationCode", verificationCode);
	            session.setAttribute("memberMail", memberMail);
	            sendVerificationEmail(memberMail, verificationCode);
	            rttr.addFlashAttribute("message", "인증 이메일이 전송되었습니다. 이메일을 확인하여 인증해주세요.");
	            return "redirect:/member/findPasswordComplete"; // 비밀번호 찾기 완료 페이지로 리다이렉트
	        } catch (Exception e) {
	            rttr.addFlashAttribute("message", "인증 이메일 발송에 실패했습니다. 잠시 후 다시 시도해주세요.");
	            return "redirect:/member/findPassword"; // 비밀번호 찾기 페이지로 리다이렉트
	        }
	    } else {
	        rttr.addFlashAttribute("message", "등록되지 않은 이메일 주소입니다. 다시 확인해주세요.");
	        return "redirect:/member/findPassword"; // 비밀번호 찾기 페이지로 리다이렉트
	    }
	}

	private String generateVerificationCode() {
		int length = 6;
		StringBuilder verificationCode = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			verificationCode.append(random.nextInt(10));
		}
		return verificationCode.toString();
	}

	private void sendVerificationEmail(String memberMail, String verificationCode) throws Exception {
		String subject = "비밀번호 찾기 인증 이메일";
		String content = "안녕하세요,\n\n비밀번호 찾기 인증을 위한 인증 코드는 " + verificationCode + "입니다.\n\n감사합니다.";
		String from = "dhkim0147@naver.com";

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(memberMail);
			helper.setSubject(subject);
			helper.setText(content);
			mailSender.send(message);
		} catch (Exception e) {
			throw new Exception("인증 이메일 발송에 실패했습니다.");
		}
	}

	// 이메일 인증 페이지 이동
	@RequestMapping(value = "/verifyPassword", method = RequestMethod.GET)
	public String verifyPasswordGET() {
		return "member/verifyPassword";
	}

	// 이메일 인증 처리
	@RequestMapping(value = "/findPasswordComplete", method = RequestMethod.POST)
	public String verifyPasswordPOST(@RequestParam("verificationCode") String verificationCode,
	        HttpServletRequest request, RedirectAttributes rttr) {
	    HttpSession session = request.getSession();
	    String sessionVerificationCode = (String) session.getAttribute("verificationCode");
	    String memberMail = (String) session.getAttribute("memberMail");

	    if (verificationCode.equals(sessionVerificationCode)) {
	        try {
	            String temporaryPassword = generateTemporaryPassword();
	            memberservice.saveTemporaryPassword(memberMail, temporaryPassword);
	            sendTemporaryPasswordByEmail(memberMail, temporaryPassword);
	            rttr.addFlashAttribute("message",
	                    "인증이 완료되었습니다. 임시 비밀번호가 이메일로 발송되었습니다. 로그인 후 비밀번호를 변경해주세요.");
	            return "redirect:/member/login"; //로그인 페이지로 리다이렉트 
	        } catch (Exception e) {
	            rttr.addFlashAttribute("message", "임시 비밀번호 발급 및 전송에 실패했습니다. 잠시 후 다시 시도해주세요.");
	            return "redirect:/member/findPasswordComplete"; // 비밀번호 찾기 완료 페이지로 리다이렉트
	        }
	    } else {
	        rttr.addFlashAttribute("message", "인증 코드가 일치하지 않습니다. 다시 확인해주세요.");
	        return "redirect:/member/findPasswordComplete"; // 비밀번호 찾기 완료 페이지로 리다이렉트
	    }
	}



	private String generateTemporaryPassword() {
		int length = 8;
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
		StringBuilder temporaryPassword = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			temporaryPassword.append(characters.charAt(random.nextInt(characters.length())));
		}
		return temporaryPassword.toString();
	}

	private void sendTemporaryPasswordByEmail(String memberMail, String temporaryPassword) throws Exception {
		String subject = "임시 비밀번호 발급 안내";
		String content = "안녕하세요,\n\n회원님의 임시 비밀번호는 " + temporaryPassword
				+ "입니다.\n\n로그인 후 반드시 비밀번호를 변경해주세요.\n\n감사합니다.";
		String from = "dhkim0147@naver.com";

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(memberMail);
			helper.setSubject(subject);
			helper.setText(content);
			mailSender.send(message);
		} catch (Exception e) {
			throw new Exception("임시 비밀번호 이메일 발송에 실패했습니다.");
		}
	}
}
