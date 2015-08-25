package com.zilu.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Class MantainAble.
 */
@MappedSuperclass
public abstract class MaintainAble implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8261198549562514199L;

	/** The creator id. */
	private Long creatorId;
	
	/** The creator type. */
	private String creatorType;
	
	/** The create time. */
	private Date createTime;

	/** The creator. */
	private String creator;
	
	/**
	 * Gets the creator.
	 *
	 * @return the creator
	 */
	@Column(name = "CREATOR", length = 32)
	public String getCreator() {
		return this.creator;
	}

	/**
	 * Sets the creator.
	 *
	 * @param creator the new creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * Gets the creator id.
	 *
	 * @return the creator id
	 */
	@Column(name = "CREATOR_ID")
	public Long getCreatorId() {
		return this.creatorId;
	}

	/**
	 * Sets the creator id.
	 *
	 * @param creatorId the new creator id
	 */
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * Gets the creator type.
	 *
	 * @return the creator type
	 */
	@Column(name = "CREATOR_TYPE", length = 1)
	public String getCreatorType() {
		return this.creatorType;
	}

	/**
	 * Sets the creator type.
	 *
	 * @param creatorType the new creator type
	 */
	public void setCreatorType(String creatorType) {
		this.creatorType = creatorType;
	}

	/**
	 * Gets the creates the time.
	 *
	 * @return the creates the time
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the new creates the time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
