package com.myspring.pro27.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro27.member.service.MemberService;
import com.myspring.pro27.member.vo.MemberVO;

@Controller(value = "memberController")
public class MemberControllerImpl implements MemberController{

	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	/*
	 * Marks a constructor, field, setter method, or config method as to be
	 * autowired by Spring's dependency injection facilities.
	 * 생성자, 필드, 셋터 메서드 , 메서드 설정에 의존성 주입합
	 */
	
	@Autowired
	private MemberService memberService;	
	
	@Autowired
	private MemberVO memberVO;
	
	


	@Override
	@RequestMapping(value = "/member/listMembers.do", method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String viewName = getViewName(request);
//		System.out.println(viewName);		
		logger.debug("viewName: "+ viewName);
		List<MemberVO> membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}
	
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(@RequestParam(value = "result", required = false) String result, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = getViewName(request);
//		System.out.println(viewName);		
		logger.debug("viewName: "+ viewName);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("result", result);
		mav.setViewName(viewName);
		return mav;
	}
	
	
	/*
	 * public ModelAndView modMemberForm(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception{ String viewName =
	 * getViewName(request); System.out.println(viewName); ModelAndView mav = new
	 * ModelAndView(viewName); mav.setViewName(viewName);
	 * 
	 * return mav; }
	 * 
	 * 
	 * public ModelAndView modMember(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception{
	 * 
	 * String viewName = getViewName(request); System.out.println(viewName);
	 * 
	 * MemberDAO dao = new MemberDAOImpl(); MemberVO memberVO = new MemberVO();
	 * 
	 * 
	 * String id=request.getParameter("id"); String pwd=request.getParameter("pwd");
	 * String name=request.getParameter("name"); String
	 * email=request.getParameter("email");
	 * 
	 * memberVO.setId(id); memberVO.setPwd(pwd); memberVO.setName(name);
	 * memberVO.setEmail(email);
	 * 
	 * 
	 * 
	 * dao.memberMod(memberVO); request.setAttribute("memberVO", memberVO);
	 * 
	 * 
	 * 
	 * 
	 * ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
	 * return mav; }
	 */
	

	
	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
					
		int result = 0; 
		result=memberService.addMember(member);		
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");

		return mav;
	}
	
	
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	
	
	
	
	private String getViewName(HttpServletRequest request) throws Exception{
		String contextPath = request.getContextPath();
		System.out.println(contextPath);
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		System.out.println(uri);
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}
		System.out.println(uri);
		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}
		System.out.println(begin);
		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}
		System.out.println(end);
		
		String viewName = uri.substring(begin, end);
		System.out.println(viewName);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
			System.out.println(viewName);
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/",1), viewName.length());
			System.out.println(viewName);
		}
		return viewName;
	}

	
	
// RedirectAttributes	A specialization of the Model interface that controllers can use to select attributes for a redirect scenario. Since the intent of adding redirect attributes is very explicit -- i.e. to be used for a redirect URL,attribute values may be formatted as Strings and stored that way to make them eligible to be appended to the query string or expanded as URI variables in org.springframework.web.servlet.view.RedirectView. 
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(MemberVO member, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(member);
		
		if(memberVO != null) {
			 HttpSession session = request.getSession();
			    session.setAttribute("member", memberVO);
			    session.setAttribute("isLogOn", true);
			    mav.setViewName("redirect:/member/listMembers.do");
		}else {
			rAttr.addAttribute("result","loginFailed");
			mav.setViewName("redirect:/member/loginForm.do");
		}
		
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}
	
	
	
	
	
	
	
	

}
