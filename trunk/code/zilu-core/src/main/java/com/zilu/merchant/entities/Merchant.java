package com.zilu.merchant.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zilu.common.entities.MaintainAble;

/**
 * Merchant entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zilu_merchant", catalog = "zilu")
public class Merchant extends MaintainAble {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5987822118285296274L;
	private Long merchantId;
	private String merchantName;
	private String phone;
	private String linkMan;
	private String linkPhone;
	private String address;
	private String status;
	private String settlementAccount;
	private String settlementAccountBank;
	private String settlementAccountName;
	private String agentIndicator;


	/** default constructor */
	public Merchant() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MERCHANT_ID", unique = true, nullable = false)
	public Long getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@Column(name = "MERCHANT_NAME", length = 100)
	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "LINK_MAN", length = 20)
	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "LINK_PHONE", length = 20)
	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "SETTLEMENT_ACCOUNT", length = 32)
	public String getSettlementAccount() {
		return this.settlementAccount;
	}

	public void setSettlementAccount(String settlementAccount) {
		this.settlementAccount = settlementAccount;
	}

	@Column(name = "SETTLEMENT_ACCOUNT_BANK", length = 32)
	public String getSettlementAccountBank() {
		return this.settlementAccountBank;
	}

	public void setSettlementAccountBank(String settlementAccountBank) {
		this.settlementAccountBank = settlementAccountBank;
	}

	@Column(name = "SETTLEMENT_ACCOUNT_NAME", length = 32)
	public String getSettlementAccountName() {
		return this.settlementAccountName;
	}

	public void setSettlementAccountName(String settlementAccountName) {
		this.settlementAccountName = settlementAccountName;
	}

	@Column(name = "AGENT_INDICATOR", length = 1)
	public String getAgentIndicator() {
		return this.agentIndicator;
	}

	public void setAgentIndicator(String agentIndicator) {
		this.agentIndicator = agentIndicator;
	}


}