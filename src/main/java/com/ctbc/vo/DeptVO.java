package com.ctbc.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "z40180_deptTB")
@NamedQuery(name = "DeptVO.findAll", query = "SELECT dd FROM DeptVO AS dd")
public class DeptVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "deptno")
	private int deptno;

	@Column(name = "dname")
	private String dname;

	@Column(name = "loc")
	private String loc;

	//bi-directional many-to-one association to EmpVO
	@OneToMany(mappedBy = "deptVO", fetch = FetchType.LAZY , cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<EmpVO> empVOs;

	public DeptVO() {
	}

	public int getDeptno() {
		return this.deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public Set<EmpVO> getEmpVOs() {
		return this.empVOs;
	}

	public void setEmpVOs(Set<EmpVO> empVOs) {
		this.empVOs = empVOs;
	}

	public EmpVO addEmpVO(EmpVO empVO) {
		getEmpVOs().add(empVO);
		empVO.setDeptVO(this);
		return empVO;
	}

	public EmpVO removeEmpVO(EmpVO empVO) {
		getEmpVOs().remove(empVO);
		empVO.setDeptVO(null);
		return empVO;
	}

	@Override
	public String toString() {
		return "DeptVO [deptno=" + deptno + ", dname=" + dname + ", loc=" + loc + "]";
	}
	
//	@Override
//	public String toString() {
//		boolean outputTransients = true; // 是否輸出 【transient】 屬性
//		boolean outputStatics = false;   // 是否輸出 【static】 屬性
//		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE, outputTransients, outputStatics);
//	}

}
