package testConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctbc.dao.DeptDAO;
import com.ctbc.vo.DeptVO;
import com.ctbc.vo.EmpVO;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class TestConnectionC3P0 {

	private static int testNumber = 0;

	@Resource(name = "c3p0DataSource")
	private DataSource dataSource;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DeptDAO deptDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testNumber++;
		System.err.println("===========================================");
		System.err.println("================= test_" + testNumber + " ==================");
		System.err.println("===========================================");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void test_001() throws SQLException {
		System.out.println("dataSource = " + dataSource);
		String databaseProductName = dataSource.getConnection().getMetaData().getDatabaseProductName();
		System.out.println("databaseProductName >>> " + databaseProductName);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM z40180_deptTB");
		ResultSet rs = pstmt.executeQuery();
		System.out.println("----------------------------------------");
		while (rs.next()) {
			System.out.println(rs.getString("deptno") + "\t" + rs.getString("dname") + "\t\t" + rs.getString("loc"));
		}
		System.out.println("----------------------------------------");
	}

	@Test
	@Ignore
	@Transactional
	public void test_002() {
		System.out.println("sessionFactory = " + sessionFactory);
		Session currentSession = sessionFactory.getCurrentSession();
		System.out.println("currentSession = " + currentSession);
	}

	@Test
	@Ignore
//	@Rollback(value = false) // 預設都會Rollback
	@Transactional
	public void test_003() {
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-= 測試rollback =-=-=-=-=-=-=-=-=-=-=-=-=");
		Session currentSession = sessionFactory.getCurrentSession();
		NativeQuery query = currentSession.createNativeQuery("DELETE FROM z40180_deptTB WHERE deptno IN ( 10 , 20 ) ");
		query.executeUpdate();
	}

	@Test
	@Ignore
	@Transactional
	public void test_004() {
		Session currentSession = sessionFactory.getCurrentSession();
		DeptVO deptvo = currentSession.get(DeptVO.class, 10);
		System.out.println("deptvo = " + deptvo);
	}
	
	@Test
	@Ignore
	@Transactional
	public void test_005() {
		Session currentSession = sessionFactory.getCurrentSession();
		EmpVO empVO = currentSession.get(EmpVO.class, 7001);
		System.out.println("empVO = " + empVO);
	}

	@Test
	@Ignore
	@Transactional
	public void test_006() {
		// 	empVO.setEmpHireDate(java.sql.Date.valueOf(rs.getString("hiredate")));
		String sql = " SELECT ee.*, dd.dname FROM z40180_empTB AS ee JOIN z40180_deptTB AS dd " +
					" ON ee.deptno = dd.deptno " +
					" WHERE ee.empno IN ( 7003 , 7004 , 7005 ) ";
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createNativeQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> list = query.getResultList();
		for (Map<String, Object> hmap : list) {
			System.out.println(hmap);
		}
	}

	@Test
	@Ignore
	@Transactional
	public void test_007() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<DeptVO> query = currentSession.createNamedQuery("DeptVO.findAll", DeptVO.class);
		List<DeptVO> deptList = query.getResultList();
		for (DeptVO vo : deptList) {
			System.out.println(vo);
		}
	}
	
	
	@Test
	@Ignore
	@Transactional
	public void test_008() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<DeptVO> query = currentSession.createNamedQuery("DeptVO.findAll", DeptVO.class);
		List<DeptVO> deptList = query.getResultList();
		for (DeptVO vo : deptList) {
			System.out.println(vo);
		}
	}

	/**
	 * 可開關 DeptDAO中的 @Cacheable 註解測試，觀察SQL發送狀況 
	 */
	@Test // 參考網站：http://www.importnew.com/20303.html
//	@Ignore
	@Transactional
	public void test_009() throws InterruptedException {
		System.err.println(">>>>>>>>>>>>>>>>>> 第一次呼叫 <<<<<<<<<<<<<<<<<");
		printDeptAndEmp(deptDAO.getAll(false));
		
		Thread.sleep(2000);
		System.err.println(">>>>>>>>>>>>>>>>>> 再過2秒後呼叫 <<<<<<<<<<<<<<<<<");// 可看到在2秒後並沒有再去查一次DB，Catche生效！
		printDeptAndEmp(deptDAO.getAll(false));
		
		Thread.sleep(11000);
		System.err.println(">>>>>>>>>>>>>>>>>> 再過11秒後呼叫 <<<<<<<<<<<<<<<<<");// 11秒後由於以大於快取時間，SQL會再發送！
		printDeptAndEmp(deptDAO.getAll(false));
	}
	
	
	private static void printDeptAndEmp(List<DeptVO> dList) {
		for (DeptVO deptVO : dList) {
			System.out.println(deptVO);
//			Set<EmpVO> empSet = deptVO.getEmpVOs();
//			for (EmpVO empVO : empSet) {
//				System.out.println(" >>> " + empVO);
//			}
		}
	}
}
