package testConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
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

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class TestConnectionC3P0 {

	private static int testNumber = 0;

	@Resource(name = "c3p0DataSource")
	private DataSource dataSource;

	@Autowired
	private SessionFactory sessionFactory;
	
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
//	@Rollback(value = false) // 預設都會Rollback
	@Transactional
	public void test_003() {
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-= 測試rollback =-=-=-=-=-=-=-=-=-=-=-=-=");
		Session currentSession = sessionFactory.getCurrentSession();
		NativeQuery query = currentSession.createNativeQuery("DELETE FROM z40180_deptTB WHERE deptno IN ( 10 , 20 ) ");
		query.executeUpdate();
	}
}


