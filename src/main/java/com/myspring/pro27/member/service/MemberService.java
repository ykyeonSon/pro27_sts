package com.myspring.pro27.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro27.member.vo.MemberVO;

public interface MemberService {
	public List listMembers() throws DataAccessException;
	public int addMember(MemberVO member) throws DataAccessException;
	public void removeMember(String id)throws DataAccessException;
	
	
	
}
