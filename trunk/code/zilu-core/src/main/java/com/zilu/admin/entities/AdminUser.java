package com.zilu.admin.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zilu.common.entities.MaintainAble;

/**
 * AdminUser entity.
 */
@Entity
@Table(name = "SYS_USER")
public class AdminUser extends MaintainAble {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3585036735674456271L;

	/** The user id. */
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The password. */
	private String password;

	/** The real name. */
	private String realName;

	/** The phone. */
	private String phone;

	/** The mobile phone. */
	private String mobilePhone;

	/** The email. */
	private String email;

	/** The merchant id. */
	private Long merchantId;

	/** The type. */
	private String type;

	/** The last login time. */
	private Date lastLoginTime;

	// Constructors

	/**
	 * default constructor.
	 */
	public AdminUser() {
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the login name.
	 * 
	 * @return the login name
	 */
	@Column(name = "LOGIN_NAME", length = 32)
	public String getLoginName() {
		return this.loginName;
	}

	/**
	 * Sets the login name.
	 * 
	 * @param loginName
	 *            the new login name
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	@Column(name = "PASSWORD", length = 32)
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the real name.
	 * 
	 * @return the real name
	 */
	@Column(name = "REAL_NAME", length = 32)
	public String getRealName() {
		return this.realName;
	}

	/**
	 * Sets the real name.
	 * 
	 * @param realName
	 *            the new real name
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * Gets the phone.
	 * 
	 * @return the phone
	 */
	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Sets the phone.
	 * 
	 * @param phone
	 *            the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the mobile phone.
	 * 
	 * @return the mobile phone
	 */
	@Column(name = "MOBILE_PHONE", length = 20)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	/**
	 * Sets the mobile phone.
	 * 
	 * @param mobilePhone
	 *            the new mobile phone
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	@Column(name = "EMAIL", length = 64)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the merchant id.
	 * 
	 * @return the merchant id
	 */
	@Column(name = "MERCHANT_ID")
	public Long getMerchantId() {
		return this.merchantId;
	}

	/**
	 * Sets the merchant id.
	 * 
	 * @param merchantId
	 *            the new merchant id
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the last login time.
	 * 
	 * @return the last login time
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_LOGIN_TIME", length = 19)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	/**
	 * Sets the last login time.
	 * 
	 * @param lastLoginTime
	 *            the new last login time
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


}