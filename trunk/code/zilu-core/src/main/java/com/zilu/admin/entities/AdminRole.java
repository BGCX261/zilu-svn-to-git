package com.zilu.admin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zilu.common.entities.MaintainAble;

/**
 * AdminRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_ROLE")
public class AdminRole extends MaintainAble {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8613509882748055139L;
	private Long id;
	private String roleName;
	private String description;
	private String enabled;
	private Long merchantId;
	private String type;

	// Constructors

	/** default constructor */
	public AdminRole() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long roleId) {
		this.id = roleId;
	}

	@Column(name = "ROLE_NAME", length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "DESCRIPTION", length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "ENABLED", length = 1)
	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "MERCHANT_ID")
	public Long getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}