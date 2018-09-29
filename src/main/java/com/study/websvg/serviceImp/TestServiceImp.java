package com.study.websvg.serviceImp;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.websvg.service.TestService;

@Service
public class TestServiceImp implements TestService {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public String getCount() {
		return sqlSession.selectOne("TestMapper.getcount");
//		return sqlSession.selectOne("TestMapper.getnow");
	}
}
