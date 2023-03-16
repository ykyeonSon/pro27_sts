package com.myspring.pro27.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro27.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{

	@Autowired
	private SqlSession sqlSession;	

	/*
	 * public void setSqlSession(SqlSession sqlSession) { this.sqlSession =
	 * sqlSession; }
	 */

	@Override
	public List selectAllMembers() throws DataAccessException {
		List<MemberVO> membersList=null;
		membersList=sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}


	
//	public MemberVO findMember(String id) {
//		
//		
//		MemberVO memberVO=null;
//		if(sqlSession != null) {
//			memberVO=(MemberVO) sqlSession.selectOne("mapper.member.selectMemberById", id);	
//		}
//		
//		return memberVO;
//	}
//	
	
	
	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		int result=sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}



	public void removeMember(String id) throws DataAccessException {
		sqlSession.delete("mapper.member.deleteMember", id);		
	
	}



	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException {
		MemberVO vo = sqlSession.selectOne("mapper.member.loginById",memberVO);
		return vo;
	}


//	@Override
//	public int memberMod(MemberVO memberVO) throws DataAccessException {
//		
//		sqlMapper = getInstance();
//		SqlSession session = sqlMapper.openSession();
//		int result=session.update("mapper.member.updateMember", memberVO);
//		sqlSession.commit();
//		return result;
//		
//	}

}
