package com.myspring.pro27.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro27.member.dao.MemberDAO;
import com.myspring.pro27.member.vo.MemberVO;


@Service(value = "memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO memberDAO;

	/*
	 * public void setMemberDAO(MemberDAO memberDAO) { this.memberDAO = memberDAO; }
	 */



	@Override
	public List<MemberVO> listMembers() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = memberDAO.selectAllMembers();
		return membersList;
	}



	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		int result =memberDAO.addMember(memberVO);
		return result;
		
	}


	public void removeMember(String id ) throws DataAccessException{
		memberDAO.removeMember(id);
	}



	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {
		return memberDAO.loginById(memberVO);
	}



//	@Override
//	public int memberMod(MemberVO memberVO) throws DataAccessException {
//		int result =memberDAO.memberMod(memberVO);
//		return result;
//		
//	}
	

}
