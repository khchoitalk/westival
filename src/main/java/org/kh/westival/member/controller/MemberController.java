package org.kh.westival.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kh.westival.festival.model.vo.Festival;
import org.kh.westival.member.model.service.MemberService;
import org.kh.westival.member.model.vo.Member;
import org.kh.westival.ticket.model.vo.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	// 병훈 (10/23수정)
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public ModelAndView loginMethod(Member member, @RequestParam(value = "user_id") String user_id,
			@RequestParam(value = "user_pwd") String user_pwd, HttpServletResponse response, ModelAndView mv)
			throws Exception {

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		member.setUser_id(user_id);
		member.setUser_pwd(user_pwd);
		member = memberService.loginCheck(member);

		System.out.println("확인 : " + member);

		if (member != null) {
			out.print(1);
		} else if (member == null) {
			out.print(0);
		}

		out.flush();
		out.close();

		mv.addObject("member", member);
		return mv;
	}

	@RequestMapping("logout.do")
	public String logoutMethod(SessionStatus status) {
		status.setComplete();
		return "main";

	}

	@RequestMapping("loginSuc.do")
	public ModelAndView loginSuccess(ModelAndView mv, Member member) {
		System.out.println("suc : " + member);

		member = memberService.selectMember(member);

		mv.addObject("member", member);
		mv.setViewName("main");
		return mv;
	}

	@RequestMapping(value = "checkid.do", method = RequestMethod.POST)
	public void checkidMethod(Member member, @RequestParam(value = "user_id") String user_id,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("user_id : " + user_id);
		member.setUser_id(user_id);

		member = memberService.checkId(member);

		System.out.println(member);

		if (member != null) {
			out.print(1);
		} else if (member == null) {
			out.print(0);
		}

		out.flush();
		out.close();
	}

	// 10/23 로그인, 회원가입
	@RequestMapping(value = "loginPage.do")
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "registerPage.do")
	public String registerPage() {
		return "register";
	}

	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	public String registerMethod(Member member) {

		int result = memberService.insertMember(member);
		return "main";

	}
	
	@RequestMapping(value = "ipsearch.do")
	public String ipsearchPage() {
		return "ipsearch";
	}
	
	  @RequestMapping(value = "searchId.do", method = RequestMethod.POST)
      @ResponseBody
      public String searchIdMethod(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
         Member member = new Member();
         member.setUser_name((String)request.getParameter("user_name"));
         member.setUser_email((String)request.getParameter("user_email"));
         
         String user_id = memberService.searchId(member);

         JSONObject job = new JSONObject();
         
         if (user_id != null) {
            job.put("user_id", user_id);
         } else if (user_id == null) {
            job.put("user_id", "0");
         }

         return job.toJSONString();
      }
      
      @RequestMapping(value = "searchPwd.do", method = RequestMethod.POST)
      @ResponseBody
      public String searchPwdMethod(HttpServletResponse response, HttpServletRequest request) throws IOException{
         
         Member member = new Member();
         member.setUser_id((String)request.getParameter("user_id"));
         member.setUser_email((String)request.getParameter("user_email"));
         member.setUser_phone((String)request.getParameter("user_phone"));
         
         int result =  memberService.searchPwd(member);

         JSONObject job = new JSONObject();
         if(result == 1){
            job.put("result", "1");
         } else{
            job.put("result", "0");
         }
         return job.toJSONString();
      }

      @RequestMapping(value = "changePwdPage.do", method = RequestMethod.POST)
      public ModelAndView changePwdPageMethod(ModelAndView mv, HttpServletRequest request) {
         String user_id = (String)request.getParameter("p_user_id");

         System.out.println(user_id);

         mv.addObject("user_id", user_id);
         mv.setViewName("changePwd");
         return mv;
      }   
      
      @RequestMapping(value = "changePwd.do")
      @ResponseBody
      public String changePwdMethod(HttpServletRequest request) {
         Member member = new Member();
         member.setUser_id((String)request.getParameter("user_id"));
         member.setUser_pwd((String)request.getParameter("user_pwd"));
         
         int result = memberService.changePwd(member);
         
         JSONObject job = new JSONObject();
         if(result == 1){
            job.put("result", "1");
         } else{
            job.put("result", "0");
         }
         return job.toJSONString();
      }

	// 페이지 이동처리만 담당
	@RequestMapping(value = "memberInfo.do")
	public String memberInfo() {
		return "member/memberInfo";
	}

	// 페이지 이동처리만 담당
	@RequestMapping(value = "recommendList.do")
	public String recommendList() {
		return "member/recommendList";
	}

	// 페이지 이동처리만 담당
	@RequestMapping(value = "likeFesta.do")
	public String likeFesta() {
		return "member/likeFesta";
	}

	// 페이지 이동처리만 담당
	@RequestMapping(value = "myList.do")
	public String myList() {
		return "member/myList";
	}

	// 회원정보 수정
	@RequestMapping(value = "updateMemberInfo.do", method = RequestMethod.POST)
	public ModelAndView updateMemberInfo(ModelAndView mv, Member member) {
		mv.addObject(memberService.updateMemberInfo(member));
		mv.setViewName("member/memberInfo");
		return mv;
	}

	// 회원정보 삭제
	@RequestMapping(value = "deleteMemberInfo.do", method = RequestMethod.GET)
	public String deleteMemberInfo(ModelAndView mv, @RequestParam(value = "user_id") String user_id) throws Exception {
		mv.addObject(memberService.deleteMemberInfo(user_id));
		return "redirect:/logout.do";
	}

	// 내 게시글 페스티벌 삭제여부 'Y'로 전환
	@RequestMapping(value = "updateMyList.do", method = RequestMethod.GET)
	public String updateMyList(ModelAndView mv, HttpServletRequest request) {
		String check = request.getParameter("checkList");
		String[] checkArr = check.split(" ");
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (String checkItem : checkArr)
			list.add(new Integer(checkItem));

		mv.addObject(memberService.updateMyList(list));
		return "member/myList";
	}

	// 내 게시글 페스티벌 전체 조회
	@RequestMapping(value = "myTotalList.do", method = RequestMethod.POST)
	public void myTotalList(Member member, HttpServletResponse response, HttpServletRequest request,
			HttpSession session) throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}

		List<Festival> list = memberService.myTotalList(member.getUser_id());
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());
	
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}
	
	// 내 게시글 페스티벌 날짜 검색
	@RequestMapping(value = "myListSearch.do", method = RequestMethod.POST)
	public void myListSearch(Member member, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		String start_date = (String) (request.getParameter("start_date"));
		String end_date = (String) (request.getParameter("end_date"));
		List<Festival> list = memberService.myListSearch(start_date, end_date, member);
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());
	
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 내 게시글 페스티벌 날짜검색(1,3,6개월)
	@RequestMapping(value = "myListSearchMonth.do", method = RequestMethod.POST)
	public void myListSearchMonth(Member member, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int month = Integer.parseInt((String) request.getParameter("month"));
		List<Festival> list = memberService.myListSearchMonth(month, member);
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());
	
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		sendJson.put("month", month);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 관심축제 스크랩 삭제
	@RequestMapping(value = "deleteMyFesta.do", method = RequestMethod.GET)
	public String deleteMyFesta(ModelAndView mv, HttpServletRequest request) {
		String check = request.getParameter("checkList");
		String[] checkArr = check.split(" ");
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (String checkItem : checkArr)
			list.add(new Integer(checkItem));

		mv.addObject(memberService.deleteMyFesta(list));
		return "member/likeFesta";
	}

	// 관심축제 페스티벌 전체 조회
	@RequestMapping(value = "myLikeFestaList.do", method = RequestMethod.POST)
	public void myLikeFestaList(Member member, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		List<Festival> list = memberService.myLikeFestaList(member.getUser_id());
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());
	
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 관심축제 페스티벌 날짜 검색
	@RequestMapping(value = "myLikeFestaSearch.do", method = RequestMethod.POST)
	public void myLikeFestaSearch(Member member, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		String start_date = (String) (request.getParameter("start_date"));
		String end_date = (String) (request.getParameter("end_date"));
		List<Festival> list = memberService.myLikeFestaSearch(start_date, end_date, member);
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());
	
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 관심축제 페스티벌 날짜검색(1,3,6개월)
	@RequestMapping(value = "myLikeFestaSearchMonth.do", method = RequestMethod.POST)
	public void myLikeFestaSearchMonth(Member member, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int month = Integer.parseInt((String) request.getParameter("month"));
		List<Festival> list = memberService.myLikeFestaSearchMonth(month, member);
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("new_img_name", list.get(i).getNew_img_name());
				job.put("no", new Integer(list.get(i).getNo()).toString());
				job.put("start_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getStart_date()));
				job.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getEnd_date()));
				job.put("name", list.get(i).getName());
				job.put("manage", list.get(i).getManage());
				job.put("address", list.get(i).getAddress());
				job.put("content", list.get(i).getContent());
				
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		sendJson.put("month", month);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 내 예매내역 환불할 티켓 정보 조회
	@RequestMapping(value = "myCurrentTicket.do", method = RequestMethod.POST)
	public void myCurrentTicket(Member member, HttpServletResponse response, String ticket_no) throws IOException {
		Ticket ticket = memberService.myCurrentTicket(ticket_no);

		JSONArray jarr = new JSONArray();
		JSONObject job = new JSONObject();

		job.put("ticket_no", ticket.getTicket_no());
		job.put("ticket_date", new SimpleDateFormat("yyyy-MM-dd").format(ticket.getTicket_date()));
		job.put("festival_name", memberService.selectFestivalName(ticket.getNo()));
		job.put("ticket_count", new Integer(ticket.getTicket_count()).toString());
		job.put("price", new Integer(ticket.getPrice()).toString());

		jarr.add(job);

		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 내 예매내역 환불 처리
	@RequestMapping(value = "refundCurrentTicket.do", method = RequestMethod.POST)
	public void refundCurrentTicket(HttpServletResponse response, String ticket_no, String refund_why)
			throws Exception {
		memberService.refundCurrentTicket(ticket_no);
		memberService.updateRefundWhy(ticket_no, refund_why);
	}

	// 경호
	// 내 예매내역
	@RequestMapping(value = "myTicketList.do", method = RequestMethod.POST)
	public void myTicketList(Member member, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		List<Ticket> list = memberService.recommendList(member.getUser_id());
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("ticket_no", list.get(i).getTicket_no());
				job.put("ticket_date", list.get(i).getTicket_date().toString());
				job.put("festival_name", memberService.selectFestivalName(list.get(i).getNo()));
				job.put("ticket_count", list.get(i).getTicket_count());
				job.put("price", list.get(i).getPrice());
				job.put("state", list.get(i).getState());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("ticket_no", list.get(i).getTicket_no());
				job.put("ticket_date", list.get(i).getTicket_date().toString());
				job.put("festival_name", memberService.selectFestivalName(list.get(i).getNo()));
				job.put("ticket_count", list.get(i).getTicket_count());
				job.put("price", list.get(i).getPrice());
				job.put("state", list.get(i).getState());
				
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 내 예매내역 티켓 날짜조회
	@RequestMapping(value = "myTicketSearch.do", method = RequestMethod.POST)
	public void myTicketSearch(Member member, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		String start_date = (String) (request.getParameter("start_date"));
		String end_date = (String) (request.getParameter("end_date"));
		List<Ticket> list = memberService.myTicketSearch(start_date, end_date, member);
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("ticket_no", list.get(i).getTicket_no());
				job.put("ticket_date", list.get(i).getTicket_date().toString());
				job.put("festival_name", memberService.selectFestivalName(list.get(i).getNo()));
				job.put("ticket_count", list.get(i).getTicket_count());
				job.put("price", list.get(i).getPrice());
				job.put("state", list.get(i).getState());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("ticket_no", list.get(i).getTicket_no());
				job.put("ticket_date", list.get(i).getTicket_date().toString());
				job.put("festival_name", memberService.selectFestivalName(list.get(i).getNo()));
				job.put("ticket_count", list.get(i).getTicket_count());
				job.put("price", list.get(i).getPrice());
				job.put("state", list.get(i).getState());
				
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	// 내 예매내역 티켓 날짜조회 (1, 3, 6개월)
	@RequestMapping(value = "myTicketSearchMonth.do", method = RequestMethod.POST)
	public void myTicketSearchMonth(Member member, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JSONArray jarr = new JSONArray();
		
		int currentPage = 1;
		int limit = 10;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int month = Integer.parseInt((String) request.getParameter("month"));
		List<Ticket> list = memberService.myTicketSearchMonth(month, member);
		int listCount = list.size();
		int maxPage = (int) Math.ceil(((double) listCount / limit));
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if (maxPage <= endPage) {
			if(maxPage < endPage)
				endPage = maxPage;
			for (int i = limit * (currentPage - 1); i <listCount; i++) {
				JSONObject job = new JSONObject();
				
				job.put("ticket_no", list.get(i).getTicket_no());
				job.put("ticket_date", list.get(i).getTicket_date().toString());
				job.put("festival_name", memberService.selectFestivalName(list.get(i).getNo()));
				job.put("ticket_count", list.get(i).getTicket_count());
				job.put("price", list.get(i).getPrice());
				job.put("state", list.get(i).getState());

				jarr.add(job);
			}
		} else {
			for (int i = limit * (currentPage - 1); i < limit * currentPage; i++) {
				JSONObject job = new JSONObject();
				
				job.put("ticket_no", list.get(i).getTicket_no());
				job.put("ticket_date", list.get(i).getTicket_date().toString());
				job.put("festival_name", memberService.selectFestivalName(list.get(i).getNo()));
				job.put("ticket_count", list.get(i).getTicket_count());
				job.put("price", list.get(i).getPrice());
				job.put("state", list.get(i).getState());
				
				jarr.add(job);
			}
		}
		
		System.out.println(request.getParameter("page"));
		System.out.println(currentPage + ", " + maxPage + ", " + startPage + ", " + endPage);
		
		JSONObject sendJson = new JSONObject(); // 전송용 객체
		sendJson.put("list", jarr); // 전송용 객체에 저장
		sendJson.put("currentPage", currentPage);
		sendJson.put("maxPage", maxPage);
		sendJson.put("startPage", startPage);
		sendJson.put("endPage", endPage);
		sendJson.put("month", month);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}
	

}
