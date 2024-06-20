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

	// �Ϲ� ȸ�� ���� ������ �̵�
	@RequestMapping(value = "/normalAuth", method = RequestMethod.GET)
	public void normalAuthGET() {
		logger.info("�Ϲ� ȸ������ ���� ������ ����");
	}

	// �̸��� ���� ������ �̵�
	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public void mailGET() {
		logger.info("�̸��� ���� ������ ����");
	}

	// �̸��� ���� ó��
	public void verifyEmail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		session.setAttribute("verifiedEmail", email);
	}

	@RequestMapping(value = "/easyGeneral", method = RequestMethod.GET)
	public String easyGeneralGET(@RequestParam(value = "code", required = false) String code,
	        HttpServletRequest request, HttpSession session, Model model, RedirectAttributes rttr) throws Exception {
	    logger.info("easyGeneralGET ���� - code: {}", code);

	    if (code != null) {
	        String access_Token = memberservice.getAccessToken(code);
	        HashMap<String, Object> userInfo = memberservice.getUserInfo(access_Token);

	        // īī�� ����� ������ ����
	        memberservice.saveKakaoUserInfo(access_Token);

	        String email = (String) userInfo.get("email");
	        session.setAttribute("kakaoEmail", email); // �̸����� ���ǿ� ����

	        MemberVO existingMember = memberservice.getMemberByEmail(email);

	        if (existingMember != null) {
	            // Ż�� ���� üũ
	            if ("Y".equals(existingMember.getWithdrawal())) {
	                session.invalidate();
	                rttr.addFlashAttribute("withdrawalMessage", "Ż���� ȸ���Դϴ�.");
	                return "redirect:/member/login";
	            }

	            session.setAttribute("member", existingMember);
	            return "redirect:/main";  // ���� �������� �����̷�Ʈ
	        }

	        model.addAttribute("userInfo", userInfo);
	        model.addAttribute("nickname", userInfo.get("nickname"));
	        model.addAttribute("email", userInfo.get("email"));

	        verifyEmail(request);
	        session.setAttribute("userInfo", userInfo);

	        return "member/easyGeneral";
	    } else {
	        logger.info("�̿������� ������ ���� - code�� ����");
	        return "member/easyGeneral";
	    }
	}




	// ȸ������ ������ �̵�
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void joinGET() {
		logger.info("ȸ������ ���� �Է� ������ ����");
	}

	// ȸ������
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(MemberVO member, Model model) throws Exception {
		String rawPw = member.getMemberPw();
		String encodePw = pwEncoder.encode(rawPw);
		member.setMemberPw(encodePw);
		memberservice.memberJoin(member);
		return "redirect:/main";
	}

	// �α��� ������ �̵�
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGET() {
	    logger.info("�α��� ������ ����");
	    return "member/login"; // ���⿡ JSP ������ ��θ� ���߾� �ּ���.
	}

	// ���̵� �ߺ� �˻�
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception {
		logger.info("memberIdChk() ����");
		int result = memberservice.idCheck(memberId);
		logger.info("����� = " + result);
		return result != 0 ? "fail" : "success";
	}

	/* ���� �߼� */
	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public void sendMailTest() throws Exception {
		String subject = "test ����";
		String content = "���� �׽�Ʈ ����";
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

	/* �̸��� ���� */
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public String mailCheckGET(String email) throws Exception {
		logger.info("�̸��� ������ ���� Ȯ��: {}", email);

		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;
		logger.info("������ȣ " + checkNum);

		String setFrom = "dhkim0147@naver.com";
		String toMail = email;
		String title = "ȸ������ ���� �̸��� �Դϴ�.";
		String content = "Ȩ�������� �湮���ּż� �����մϴ�." + "<br><br>" + "���� ��ȣ�� " + checkNum + "�Դϴ�." + "<br>"
				+ "�ش� ������ȣ�� ������ȣ Ȯ�ζ��� �����Ͽ� �ּ���.";

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

	@RequestMapping(value = "/checkEmailDuplicate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> checkEmailDuplicate(@RequestParam("memberMail") String memberMail) {
	    logger.info("checkEmailDuplicate() ���� - �̸���: {}", memberMail);
	    int count = memberservice.checkEmailDuplicate(memberMail);
	    logger.info("�̸��� �ߺ� Ȯ�� ��� - ī��Ʈ: {}", count);

	    Map<String, Object> response = new HashMap<>();
	    response.put("isDuplicate", count == 1);

	    return response;
	}

	
	// �α��� POST
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception {
	    if (member == null || member.getMemberId() == null || member.getMemberPw() == null) {
	        rttr.addFlashAttribute("result", "�Էµ� ȸ�� ������ ��ȿ���� �ʽ��ϴ�.");
	        return "redirect:/member/login";
	    }

	    HttpSession session = request.getSession();
	    String rawPw = member.getMemberPw(); // ����ڰ� �Է��� ��й�ȣ
	    String memberId = member.getMemberId(); // ����ڰ� �Է��� ���̵�

	    // memberservice�� ���ԵǾ����� Ȯ��
	    if (memberservice == null) {
	        throw new IllegalStateException("MemberService�� ���Ե��� �ʾҽ��ϴ�.");
	    }

	    // ȸ�� Ż�� ���� Ȯ��
	    boolean isWithdrawn = memberservice.isMemberWithdrawn(memberId);
	    if (isWithdrawn) {
	        rttr.addFlashAttribute("withdrawalMessage", "Ż���� ȸ���Դϴ�.");
	        return "redirect:/member/login";
	    }

	    MemberVO lvo = memberservice.memberLogin(memberId, rawPw); // ��й�ȣ �� ����

	    if (lvo != null) {
	        lvo.setMemberPw(""); // ��й�ȣ ����
	        session.setAttribute("member", lvo);
	        session.setAttribute("memberId", lvo.getMemberId());
	        session.setAttribute("point", lvo.getPoint());
	        session.setAttribute("adminCk", lvo.getAdminCk());

	        if (lvo.getAdminCk() == 1) {
	            session.setAttribute("isAdmin", true);
	            System.out.println("�����ڷ� �α��εǾ����ϴ�. adminCk: " + lvo.getAdminCk());
	        } else {
	            System.out.println("����ڷ� �α��εǾ����ϴ�. adminCk: " + lvo.getAdminCk());
	        }

	        return "redirect:/main";
	    } else {
	        rttr.addFlashAttribute("result", 0);
	        return "redirect:/member/login";
	    }
	}

	/* �񵿱��� �α׾ƿ� �޼��� */
	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	@ResponseBody
	public void logoutPOST(HttpServletRequest request) throws Exception {
		logger.info("�񵿱� �α׾ƿ� �޼��� ����");
		HttpSession session = request.getSession();
		session.invalidate();
	}

	// ��й�ȣ Ȯ�� ó��
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

	// ȸ�� ���� ���� ������ �̵�
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

			// ���� ��й�ȣ�� ������� ���� ��쿡�� ���� ��й�ȣ�� ����
			if (member.getMemberPw() == null || member.getMemberPw().isEmpty()) {
				member.setMemberPw(sessionMember.getMemberPw());
			} else {
				String rawPw = member.getMemberPw(); // ���ڵ� �� ��й�ȣ
				String encodePw = pwEncoder.encode(rawPw); // ��й�ȣ ���ڵ�
				member.setMemberPw(encodePw); // ���ڵ��� ��й�ȣ�� ����
			}

			// ȸ�� ���� ���� ����
			memberservice.memberUpdate(member);

			// ��� �ʵ尪�� ��� MemberVO ��ü�� ��� ����
			model.addAttribute("member", member);

			return "redirect:/main";
		} else {
			return "redirect:/member/login";
		}
	}


	// ȸ�� Ż��
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
					rttr.addFlashAttribute("withdrawalMessage", "Ż���� ȸ���Դϴ�.");
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

	// ���̵� ã��
	@RequestMapping(value = "/findIdView", method = RequestMethod.GET)
	public String findIdView() throws Exception {
		return "/member/findIdView";
	}

	@RequestMapping(value = "/findId", method = RequestMethod.POST)
	public String findId(@RequestParam("memberMail") String memberMail, Model model) throws Exception {
		logger.info("memberMail: " + memberMail);

		if (memberservice.findIdCheck(memberMail) == 0) {
			model.addAttribute("msg", "�̸����� Ȯ�����ּ���");
			return "/member/findIdView";
		} else {
			List<MemberVO> members = memberservice.findId(memberMail);
			model.addAttribute("members", members);
			return "/member/findId";
		}
	}

	// ��й�ȣ ã�� ������ �̵�
	@RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	public String findPassword() throws Exception {
		return "/member/findPassword";
	}

	// ��й�ȣ ã�� ���� ������ �̵�
	@RequestMapping(value = "/findPasswordComplete", method = RequestMethod.GET)
	public String findPasswordComplete() {
		return "member/findPasswordComplete";
	}
	
	// ��й�ȣ ã�� ��û ó��
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
	            rttr.addFlashAttribute("message", "���� �̸����� ���۵Ǿ����ϴ�. �̸����� Ȯ���Ͽ� �������ּ���.");
	            return "redirect:/member/findPasswordComplete"; // ��й�ȣ ã�� �Ϸ� �������� �����̷�Ʈ
	        } catch (Exception e) {
	            rttr.addFlashAttribute("message", "���� �̸��� �߼ۿ� �����߽��ϴ�. ��� �� �ٽ� �õ����ּ���.");
	            return "redirect:/member/findPassword"; // ��й�ȣ ã�� �������� �����̷�Ʈ
	        }
	    } else {
	        rttr.addFlashAttribute("message", "��ϵ��� ���� �̸��� �ּ��Դϴ�. �ٽ� Ȯ�����ּ���.");
	        return "redirect:/member/findPassword"; // ��й�ȣ ã�� �������� �����̷�Ʈ
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
		String subject = "��й�ȣ ã�� ���� �̸���";
		String content = "�ȳ��ϼ���,\n\n��й�ȣ ã�� ������ ���� ���� �ڵ�� " + verificationCode + "�Դϴ�.\n\n�����մϴ�.";
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
			throw new Exception("���� �̸��� �߼ۿ� �����߽��ϴ�.");
		}
	}

	// �̸��� ���� ������ �̵�
	@RequestMapping(value = "/verifyPassword", method = RequestMethod.GET)
	public String verifyPasswordGET() {
		return "member/verifyPassword";
	}

	// �̸��� ���� ó��
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
	                    "������ �Ϸ�Ǿ����ϴ�. �ӽ� ��й�ȣ�� �̸��Ϸ� �߼۵Ǿ����ϴ�. �α��� �� ��й�ȣ�� �������ּ���.");
	            return "redirect:/member/login"; //�α��� �������� �����̷�Ʈ 
	        } catch (Exception e) {
	            rttr.addFlashAttribute("message", "�ӽ� ��й�ȣ �߱� �� ���ۿ� �����߽��ϴ�. ��� �� �ٽ� �õ����ּ���.");
	            return "redirect:/member/findPasswordComplete"; // ��й�ȣ ã�� �Ϸ� �������� �����̷�Ʈ
	        }
	    } else {
	        rttr.addFlashAttribute("message", "���� �ڵ尡 ��ġ���� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���.");
	        return "redirect:/member/findPasswordComplete"; // ��й�ȣ ã�� �Ϸ� �������� �����̷�Ʈ
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
		String subject = "�ӽ� ��й�ȣ �߱� �ȳ�";
		String content = "�ȳ��ϼ���,\n\nȸ������ �ӽ� ��й�ȣ�� " + temporaryPassword
				+ "�Դϴ�.\n\n�α��� �� �ݵ�� ��й�ȣ�� �������ּ���.\n\n�����մϴ�.";
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
			throw new Exception("�ӽ� ��й�ȣ �̸��� �߼ۿ� �����߽��ϴ�.");
		}
	}
}
