package com.zilu.coupon.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zilu.common.entities.MaintainAble;

/**
 * CouponEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cp_coupon_entity", catalog = "zilu")
public class CouponEntity extends MaintainAble {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4079552694758554317L;
	private Long id;
	private Coupon coupon;
	private String seqNo;
	private String status;
	private String issueStatus;
	private String cardNo;
	private String checkCode;
	private String userPhone;
	private String useChannel;
	private Long customerId;
	private Date lastUseTime;
	private String useDescript;
	private String otherVerifyIndicator;
	private String verifyKey;
	private String verifyKeyType;
	private Long merchantId;
	private Long agentMerchantId;


	/** default constructor */
	public CouponEntity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENTITY_ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long entityId) {
		this.id = entityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_ID")
	public Coupon getCoupon() {
		return this.coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Column(name = "SEQ_NO", length = 20)
	public String getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ISSUE_STATUS", length = 1)
	public String getIssueStatus() {
		return this.issueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}

	@Column(name = "CARD_NO", length = 32)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "CHECK_CODE", length = 32)
	public String getCheckCode() {
		return this.checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Column(name = "USER_PHONE", length = 20)
	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Column(name = "USE_CHANNEL", length = 1)
	public String getUseChannel() {
		return this.useChannel;
	}

	public void setUseChannel(String useChannel) {
		this.useChannel = useChannel;
	}

	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_USE_TIME", length = 19)
	public Date getLastUseTime() {
		return this.lastUseTime;
	}

	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	@Column(name = "USE_DESCRIPT", length = 512)
	public String getUseDescript() {
		return this.useDescript;
	}

	public void setUseDescript(String useDescript) {
		this.useDescript = useDescript;
	}

	@Column(name = "OTHER_VERIFY_INDICATOR", length = 1)
	public String getOtherVerifyIndicator() {
		return this.otherVerifyIndicator;
	}

	public void setOtherVerifyIndicator(String otherVerifyIndicator) {
		this.otherVerifyIndicator = otherVerifyIndicator;
	}

	@Column(name = "VERIFY_KEY", length = 32)
	public String getVerifyKey() {
		return this.verifyKey;
	}

	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}

	@Column(name = "VERIFY_KEY_TYPE", length = 1)
	public String getVerifyKeyType() {
		return this.verifyKeyType;
	}

	public void setVerifyKeyType(String verifyKeyType) {
		this.verifyKeyType = verifyKeyType;
	}

	@Column(name = "MERCHANT_ID")
	public Long getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@Column(name = "AGENT_MERCHANT_ID")
	public Long getAgentMerchantId() {
		return this.agentMerchantId;
	}

	public void setAgentMerchantId(Long agentMerchantId) {
		this.agentMerchantId = agentMerchantId;
	}

}