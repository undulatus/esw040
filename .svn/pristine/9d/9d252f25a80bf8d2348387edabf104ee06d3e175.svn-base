package com.pointwest.workforce.planner.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="version")
public class Version extends Auditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Version() {
		super();
	}
	
	public Version(Long opportunityId, String versionName) {
		Version.VersionKey key = new Version.VersionKey();
		key.setOpportunityId(opportunityId);
		key.setVersionName(versionName);
		this.key = key;
	}
	
	public Version(Long opportunityId, String versionName, String versionDescription, String versionData, Boolean isNew, Boolean isActive) {
		Version.VersionKey key = new Version.VersionKey();
		key.setOpportunityId(opportunityId);
		key.setVersionName(versionName);
		this.key = key;
		this.versionDescription = versionDescription;
		this.versionData = versionData;
		if(isNew) {			
			this.setDateCreated(Timestamp.from(Instant.now()));
		}
		this.isActive = isActive;
	}
	
	public Version(Long opportunityId, String versionName, String versionDescription, String versionData, Boolean isNew) {
		Version.VersionKey key = new Version.VersionKey();
		key.setOpportunityId(opportunityId);
		key.setVersionName(versionName);
		this.key = key;
		this.versionDescription = versionDescription;
		this.versionData = versionData;
		if(isNew) {			
			this.setDateCreated(Timestamp.from(Instant.now()));
		}
	}
	
	public Version(Long opportunityId, String versionName, String versionDescription, String versionData, Boolean isNew, Boolean isActive, Boolean isDeleted) {
		Version.VersionKey key = new Version.VersionKey();
		key.setOpportunityId(opportunityId);
		key.setVersionName(versionName);
		this.key = key;
		this.versionDescription = versionDescription;
		this.versionData = versionData;
		if(isNew) {			
			this.setDateCreated(Timestamp.from(Instant.now()));
		}
		this.isActive = isActive;
		this.isDeleted = isDeleted;
	}

	@EmbeddedId
	private VersionKey key;
	
	@Column(name="version_description")
	private String  versionDescription;
	
	@Column(name="version_data")
	private String versionData;
	
	@Column(name="version_date_created")
	private Timestamp dateCreated;
	
	@Column(name="version_is_active", columnDefinition="INT(1)")
	private Boolean isActive;
	
	@Column(name="username")
	private String username;
	
	@Column(name="version_is_deleted", columnDefinition="INT(1)")
	private Boolean isDeleted;

	public VersionKey getKey() {
		return key;
	}

	public void setKey(VersionKey key) {
		this.key = key;
	}

	public String getVersionDescription() {
		return versionDescription;
	}

	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}

	public String getVersionData() {
		return versionData;
	}

	public void setVersionData(String versionData) {
		this.versionData = versionData;
	}
	
	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Embeddable
	public static class VersionKey implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	
		@Column(name="version_name")
		private String versionName;
		
		@Column(name="opportunity_id")
		private Long opportunityId;

		public String getVersionName() {
			return versionName;
		}

		public void setVersionName(String versionName) {
			this.versionName = versionName;
		}
		
		public Long getOpportunityId() {
			return opportunityId;
		}

		public void setOpportunityId(Long opportunityId) {
			this.opportunityId = opportunityId;
		}

		@Override
		public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || this.getClass() != o.getClass()) return false;
	        VersionKey that = (VersionKey) o;
	        if (opportunityId != null? !opportunityId.equals(that.getOpportunityId()) : that.getOpportunityId() != null) return false;
	        if (versionName != null? !versionName.equals(that.getVersionName()) : that.getVersionName() != null) return false;
	        return true;
	    }

		@Override
	    public int hashCode() {
	        int result;
	        result = (versionName != null? versionName.hashCode() : 0);
	        result = 31 * result + (opportunityId !=null? opportunityId.hashCode() : 0);
	        return result;
	    }  
		
	}
}
