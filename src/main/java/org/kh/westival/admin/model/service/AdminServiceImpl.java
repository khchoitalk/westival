package org.kh.westival.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.kh.westival.admin.model.dao.AdminDao;
import org.kh.westival.admin.model.vo.Admin;
import org.kh.westival.festival.model.vo.Festival;
import org.kh.westival.festival.model.vo.TicketOption;
import org.kh.westival.member.model.vo.Member;
import org.kh.westival.ticket.model.vo.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminDao adminDao;

	
//----------------------------------------------
	//--예매관리
	
	//페이징(예매)
	@Override
	public int ticketgetListCount() {
		System.out.println("ticketgetListCount 페이징 뷰");
		return adminDao.ticketgetListCount();
	}
	//페이징뷰(예매)
	@Override
	public ArrayList<Ticket> ticketselectList(int currentPage, int limit) {
		System.out.println("ticketselectList 페이징뷰");
		return adminDao.ticketselectList(currentPage, limit);
	}
	//예매관리 페이징
	@Override
	public int tGetListCount() {
		System.out.println("tGetListCount");
		return adminDao.tGetListCount();
	}
	//예매관리 리스트
	@Override
	public ArrayList<Admin> tAllSelectList(int currentPage, int limit) {
		System.out.println("tAllSelectList");
		return adminDao.tAllSelectList(currentPage, limit);
	}
	
	//검색(예매)
	@Override
	public ArrayList<Admin> searchList(String filter, String searchTF) {
		System.out.println("searhList serviceImpl");
		return adminDao.searchList(filter, searchTF);
	}
	//예매관리(검색)(페이징)
	@Override
	public int tGetSelectListCount(HashMap map) {
		System.out.println("tGetSelectListCount serviceImpl");
		return adminDao.tGetSelectListCount(map);
	}
	//예매관리(검색)(리스트)
	@Override
	public ArrayList<Admin> tSelectList(int currentPage, int limit, String filter, String searchTF) {
		System.out.println("tSelectList serviceImpl");
		return adminDao.testivalSelectList(currentPage, limit, filter, searchTF);
		
	}
	//-------------------------------------------------------
	//축제관리
	
	//축제관리(페이징 및 리스트)
	@Override
	public int fGetListCount() {
		 System.out.println("fGetListCount serviceImpl");
		return adminDao.fGetListCount();
	}
	@Override
	public ArrayList<Festival> fAllSelectList(int currentPage, int limit) {
		System.out.println("fAllSelectList serviceImple");
		return adminDao.fAllSelectList(currentPage, limit);
	}
	
	//축제관리(검색)(페이징 및 리스트)
	@Override
	public int fGetSelectListCount(String filter, String searchTF) {
		System.out.println("fGetSelectListCount serviceImpl");
		return adminDao.fGetSelectListCount(filter, searchTF);
	}
	@Override
	public ArrayList<Festival> festivalSelectList(int currentPage, int limit, String filter, String searchTF) {
		System.out.println("festivalSelectList serviceImpl ");
		return adminDao.festivalSelectList(currentPage, limit, filter, searchTF);
	}
	
	//축제 디테일
	@Override
	public Festival festivalDetail(int fno) {
		System.out.println("festivalDetail serviceImpl");
		return adminDao.festivalDetail(fno);
	}
	//축제 수정(축제)
	@Override
	public int updateFestival(Festival festival) {
		System.out.println("updateFestival serviceImpl");
		return adminDao.updateFestival(festival);
	}
	//축제 수정(티켓)
	@Override
	public int updateTicketOption(TicketOption ticketOption) {
		System.out.println("updateTicketOption serviceImpl");
		return adminDao.updateTicketOption(ticketOption);
	}
	
	//축제 삭제
	@Override
	public int afdelete(int fno) {
		System.out.println("afdelete serviceImpl");
		return adminDao.afdelete(fno);
	}
	

	
	//----------------------------------------------------------
	//회원관리
	
	//회원등록
	@Override
	public int insertMember(Member member) {
		// TODO Auto-generated method stub
		return adminDao.insertMember(member);
	}
	
	//회원리스트
	/*@Override
	public Object adminMemberView() {
		System.out.println("adminMemberView serviceImpl");
		return adminDao.adminMemberView();
	}*/
	@Override
	public int getListCount() {
		System.out.println("getListCOunt serviceImpl");
		return adminDao.getListCount();
	}
	@Override
	public ArrayList<Member> selectList(int currentPage, int limit) {
		System.out.println("selectList serviceImpl");
		return adminDao.selectList(currentPage, limit);
	}
	
	
	//회원관리(페이징 및 리스트)
	@Override
	public int mGetListCount() {
		System.out.println("mGetListCount serviceImpl");
		return adminDao.mGetListCount();
	}
	@Override
	public ArrayList<Member> mAllSelectList(int currentPage, int limit) {
		System.out.println("mAllSelectList serviceImpl");
		return adminDao.mAllSelectList(currentPage, limit);
	}
	
	//회원관리(검색)(페이징 및 리스트)
	@Override
	public int mGetSelectListCount(HashMap map) {
		System.out.println("mGetSelectListCount serviceImpl");
		return adminDao.mGetSelectListCount(map);
	}
	@Override  
	public ArrayList<Member> mSelectList(int currentPage, int limit, String filter, String searchTF) {
		System.out.println("mSelectList serviceImpl");
		return adminDao.mSelectList(currentPage, limit, filter, searchTF);
	}
	
	//회원삭제
	@Override
	public Object amdelete(String mid) {
		System.out.println("amdelete serviceImpl");
		return adminDao.amdelete(mid);
	}
	
	
	
	
	
	
	
	 
	


	

	



}
