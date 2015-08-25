package com.zilu.coupon.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zilu.common.entities.MaintainAble;

/**
 * Coupon entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CP_COUPON")
public class Coupon extends MaintainAble {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6815444877176396928L;
	private Long id;
	private String title;
	private String couponType;
	private Integer issueQuantity;
	private Integer usedQuantity;
	private Integer leftQuantity;
	private String source;
	private String status;
	private String description;
	private String couponImage;
	private Date validTime;
	private Date expireTime;
	private String useScope;
	private String issueType;
	private Integer fee;
	private String consumeLimit;
	private Integer minimumCunsume;
	private Set<CouponEntity> couponEntities = new HashSet<CouponEntity>(0);

	// Constructors

	/** default constructor */
	public Coupon() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COUPON_ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long couponId) {
		this.id = couponId;
	}

	@Column(name = "TITLE", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "COUPON_TYPE", length = 3)
	public String getCouponType() {
		return this.couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	@Column(name = "ISSUE_QUANTITY")
	public Integer getIssueQuantity() {
		return this.issueQuantity;
	}

	public void setIssueQuantity(Integer issueQuantity) {
		this.issueQuantity = issueQuantity;
	}

	@Column(name = "USED_QUANTITY")
	public Integer getUsedQuantity() {
		return this.usedQuantity;
	}

	public void setUsedQuantity(Integer usedQuantity) {
		this.usedQuantity = usedQuantity;
	}

	@Column(name = "LEFT_QUANTITY")
	public Integer getLeftQuantity() {
		return this.leftQuantity;
	}

	public void setLeftQuantity(Integer leftQuantity) {
		this.leftQuantity = leftQuantity;
	}

	@Column(name = "SOURCE", length = 1)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DESCRIPTION", length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "COUPON_IMAGE", length = 100)
	public String getCouponImage() {
		return this.couponImage;
	}

	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_TIME", length = 19)
	public Date getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRE_TIME", length = 19)
	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Column(name = "USE_SCOPE", length = 100)
	public String getUseScope() {
		return this.useScope;
	}

	public void setUseScope(String useScope) {
		this.useScope = useScope;
	}

	@Column(name = "ISSUE_TYPE", length = 30)
	public String getIssueType() {
		return this.issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}


	@Column(name = "FEE")
	public Integer getFee() {
		return this.fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	@Column(name = "CONSUME_LIMIT", length = 1)
	public String getConsumeLimit() {
		return this.consumeLimit;
	}

	public void setConsumeLimit(String consumeLimit) {
		this.consumeLimit = consumeLimit;
	}

	@Column(name = "MINIMUM_CUNSUME")
	public Integer getMinimumCunsume() {
		return this.minimumCunsume;
	}

	public void setMinimumCunsume(Integer minimumCunsume) {
		this.minimumCunsume = minimumCunsume;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coupon")
	public Set<CouponEntity> getCouponEntities() {
		return this.couponEntities;
	}

	public void setCouponEntities(Set<CouponEntity> couponEntities) {
		this.couponEntities = couponEntities;
	}

}