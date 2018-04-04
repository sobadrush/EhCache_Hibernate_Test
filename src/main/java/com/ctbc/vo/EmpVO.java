package com.ctbc.vo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "z40180_empTB")
@NamedQuery(name = "EmpVO.findAll", query = "SELECT ee FROM EmpVO AS ee")
public class EmpVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "empno")
	private int empno;

	@Column(name = "ename")
	private String ename;

	@Convert(converter = MyDateAttributeConverter.class) // 自訂轉換器
	@Column(name = "hiredate", columnDefinition = "TEXT")
	private java.sql.Date hiredate;

	@Column(name = "job")
	private String job;

	//bi-directional many-to-one association to DeptVO
	@ManyToOne(fetch = FetchType.EAGER , cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "deptno")
	private DeptVO deptVO;

	public EmpVO() {
	}

	public int getEmpno() {
		return this.empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public java.sql.Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(java.sql.Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public DeptVO getDeptVO() {
		return this.deptVO;
	}

	public void setDeptVO(DeptVO deptVO) {
		this.deptVO = deptVO;
	}

	@Override
	public String toString() {
		return "EmpVO [empno=" + empno + ", ename=" + ename + ", hiredate=" + hiredate + ", job=" + job + "]";
	}

//	@Override
//	public String toString() {
//		boolean outputTransients = true; // 是否輸出 【transient】 屬性
//		boolean outputStatics = false;   // 是否輸出 【static】 屬性
//		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, outputTransients, outputStatics);
//	}
}
