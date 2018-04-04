package testConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class TestConnectionC3P0 {

	private int testNumber = 0;

//	@Resource(name = "driverManagerDatasource")
	@Resource(name = "c3p0DataSource")
	private DataSource dataSource;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.testNumber++;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_001() throws SQLException {
		System.out.println("================= test_" + this.testNumber + "==================");
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

}
