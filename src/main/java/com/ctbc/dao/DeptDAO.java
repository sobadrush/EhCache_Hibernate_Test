package com.ctbc.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ctbc.vo.DeptVO;
import com.ctbc.vo.EmpVO;

@Repository
public class DeptDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Cacheable(value = "myEhCache")
	public List<DeptVO> getAll(boolean isEager) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");  
		System.out.println(" 時間 ==========================================>>>>>>>> " + sdf.format(System.currentTimeMillis()));
		Session currentSession = sessionFactory.getCurrentSession();
		Query<DeptVO> query = currentSession.createNamedQuery("DeptVO.findAll", DeptVO.class);
		List<DeptVO> deptList = query.getResultList();
		
		if (isEager == true) {
			for (DeptVO deptVO : deptList) {
				Set<EmpVO> empSet = deptVO.getEmpVOs();
				for (EmpVO empVO : empSet) {
					empVO.toString();
				}
			}
		}
		
		return deptList;
	}

}
