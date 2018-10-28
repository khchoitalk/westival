package org.kh.westival.notice.model.service;

import java.util.ArrayList;

import org.kh.westival.admin.model.vo.Admin;
import org.kh.westival.notice.model.dao.NoticeDao;
import org.kh.westival.notice.model.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
		
	@Autowired
	private NoticeDao noticeDao;
	
	public NoticeServiceImpl(){}
		 
	@Override
	public ArrayList<Notice> selectList() {
		System.out.println("selectList service 실행됨");
		return noticeDao.selectList();
	}

	@Override
	public Notice noticeDetail(int notice_no) {
		System.out.println("noticeDetail service");
		return noticeDao.noticeDetail(notice_no);
		
	}

	@Override
	public int noticeInsert(Notice notice) {
		System.out.println("noticeInsert service");
		return noticeDao.noticeInsert(notice);
	}
	@Override
	public int noticeInsert2(Notice notice) {
		System.out.println("noticeInsert2 service");
		return noticeDao.noticeInsert2(notice);
	}

	@Override
	public int noticeDelete(int notice_no) {
		System.out.println("noticeDelete service");
		return noticeDao.noticeDelete(notice_no);
	}

	@Override
	public int noticeUpdate(Notice notice) {
		System.out.println("noticeUpdate service");
		return noticeDao.noticeUpdate(notice);
	}
	
	@Override
	public int noticeUpdate2(Notice notice) {
		System.out.println("noticeUpdate2 service");
		return noticeDao.noticeUpdate2(notice);
	}
	
	//정보 보내는 메소드
	@Override
	public Object noticeUpdate(int notice_no) {
		System.out.println("noticeUpdate service 보내기");
		return noticeDao.noticeUpdate(notice_no);
	}
	
	//페이징 뷰
	@Override
	public int getListCount() {
		System.out.println("getListCount 페이징뷰");
		return noticeDao.noticegetListCount();
	}

	@Override
	public ArrayList<Notice> selectList(int currentPage, int limit) {
		System.out.println("selectList 페이징뷰");
		return noticeDao.selectList(currentPage, limit);
	}
	
	//공지전체 페이징 및 리스트
		@Override
		public int nGetListCount() {
			System.out.println("nGetListCount serviceImpl");
			return noticeDao.nGetListCount();
		}

		@Override
		public ArrayList<Notice> nAllSelectList(int currentPage, int limit) {
			System.out.println("nAllSelectList serviceImpl");
			return noticeDao.nAllSelectList(currentPage, limit);
		}
	
	

	//공지 검색 페이징 및 뷰
	@Override
	public int nGetSelectListCount(String filter, String searchTF) {
		System.out.println("nGetSelectListCount serviceImpl");
		return noticeDao.nGetSelectListCount(filter, searchTF);
	}

	@Override
	public ArrayList<Notice> nSelectList(int currentPage, int limit, String filter, String searchTF) {
		System.out.println("nSelectList serviceImpl");
		return noticeDao.nSelectList(currentPage, limit, filter, searchTF);
	}
	
	

	

	

	

	

	

	
}
