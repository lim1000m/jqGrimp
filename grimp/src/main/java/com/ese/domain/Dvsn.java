package com.ese.domain; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import c.e.g.annotation.Grider;
import c.e.g.annotation.Grider.Align;
import c.e.g.annotation.Grider.Format;
import c.e.g.annotation.Grider.Sort;
import c.e.g.annotation.config.Gid;

/**
 * @File Name : Dvsn.java
 * @Project Name : grimp
 * @package Name : com.ese.domain
 * @create Date : 2016. 2. 29.
 * @explain : Table이 매핑된 테이블의 컬럼을 @Grider를 통해서 옵션 설정 
 * @made by : "GOEDOKID"
 * @see
 * 1.0 Grid에 사용하지 않는 변수 선언만 해서 사용 가능 
 * 	   hibernate에서 사용할거면 @transient를 선언 필요 
 * 
 */
@Entity
@Table(name="EEAM_DVSN_TBL")
public class Dvsn {

	@Transient
	@Grider(label="com.ese.customCheck",align=Align.center, sort=Sort.none,format=Format.customCheckbox,hidden=false, width="10")
	private int checkCustom;
	
	@Transient
	@Grider(label="com.ese.check",align=Align.center, format=Format.checkbox, width="10")
	private String check;	
	
	@Transient
	@Grider(label="com.ese.no",align=Align.center,  width="10")
	private int no;
	
	@Id
	@Column(name="DVSN_CD", nullable=false, length=2)
	@Gid
	@Grider(label="com.ese.dsvnCd", align=Align.center, width="20")
	private String dvsnCd = "";

	@Column(name="DVSN_NM", nullable=true, length=50)
	@Grider(label="com.ese.dvsnNm", sort=Sort.desc, width="30")
	private String dvsnNm = "";

	@Column(name="STD_DVSN_CD", nullable=true, length=1)
	@Grider(label="com.ese.stdDvsnCd", width="10")
	private String stdDvsnCd = "";

	@Column(name="USE_YN", nullable=true, length=1)
	@Grider(label="com.ese.useYn", width="10")
	private String useYn = "";

	@Transient
	@Grider(label="com.ese.useOrdr", hidden=false, width="10")
	private int nowordr;
	
	@Column(name="ORDR", nullable=true, length=1)
	@Grider(label="com.ese.ordr", format=Format.function, width="60")
	private int ordr;
	
	@Transient
	private String extra;
	
	public int getCheckCustom() {
		return checkCustom;
	}

	public void setCheckCustom(int checkCustom) {
		this.checkCustom = checkCustom;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public int getNowordr() {
		return nowordr;
	}

	public void setNowordr(int nowordr) {
		this.nowordr = nowordr;
	}

	public int getOrdr() {
		return ordr;
	}

	public void setOrdr(int ordr) {
		this.ordr = ordr;
	}

	public String getDvsnCd() {
		return dvsnCd;
	}

	public String getDvsnNm() {
		return dvsnNm;
	}

	public String getStdDvsnCd() {
		return stdDvsnCd;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setDvsnCd(String dvsnCd) { 
		this.dvsnCd = dvsnCd;
	}

	public void setDvsnNm(String dvsnNm) { 
		this.dvsnNm = dvsnNm;
	}

	public void setStdDvsnCd(String stdDvsnCd) { 
		this.stdDvsnCd = stdDvsnCd;
	}

	public void setUseYn(String useYn) { 
		this.useYn = useYn;
	}

}