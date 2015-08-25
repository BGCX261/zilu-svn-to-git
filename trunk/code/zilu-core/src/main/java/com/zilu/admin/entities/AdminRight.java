package com.zilu.admin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AdminRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_FUNC_RIGHT")
public class AdminRight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4453287293013633551L;
	private Long id;
	private String funcCode;
	private Long parentId;
	private String funcName;
	private String funcUrl;
	private String typeGroup;

	// Constructors

	/** default constructor */
	public AdminRight() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FUNC_ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long funcId) {
		this.id = funcId;
	}

	@Column(name = "FUNC_CODE", length = 32)
	public String getFuncCode() {
		return this.funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "FUNC_NAME", length = 50)
	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	@Column(name = "FUNC_URL", length = 150)
	public String getFuncUrl() {
		return this.funcUrl;
	}

	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}

	@Column(name = "TYPE_GROUP", length = 20)
	public String getTypeGroup() {
		return this.typeGroup;
	}

	public void setTypeGroup(String typeGroup) {
		this.typeGroup = typeGroup;
	}

}