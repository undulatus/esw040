package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="opportunity_collaborator")
public class OpportunityCollaborator implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OpportunityCollaborator() {
		super();
	}
	
	public OpportunityCollaborator(String username, Long opportunityId, String permission) {
		OpportunityCollaborator.OpportunityCollaboratorKey key =
			new OpportunityCollaborator.OpportunityCollaboratorKey();
		key.setUsername(username);
		key.setOpportunityId(opportunityId);
		this.setKey(key);
		this.setPermission(permission);
	}
	
	@EmbeddedId
	private OpportunityCollaboratorKey key;
	
	@Column(name="opportunity_collaborator_permission")
	private String permission;
	
	public OpportunityCollaboratorKey getKey() {
		return key;
	}
	
	public void setKey(OpportunityCollaboratorKey key) {
		this.key = key;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	@Embeddable
	public static class OpportunityCollaboratorKey implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Column(name="opportunity_id")
		private Long opportunityId;

		@Column(name="username")
		private String username;

		public Long getOpportunityId() {
			return opportunityId;
		}

		public void setOpportunityId(Long opportunityId) {
			this.opportunityId = opportunityId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		@Override
		public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || this.getClass() != o.getClass()) return false;
	        OpportunityCollaboratorKey that = (OpportunityCollaboratorKey) o;
	        if (opportunityId != null? !opportunityId.equals(that.getOpportunityId()) : that.getOpportunityId() != null) return false;
	        if (username != null? !username.equals(that.getUsername()) : that.getUsername() !=null) return false;
	        return true;
	    }

		@Override
	    public int hashCode() {
	        int result;
	        result = (username != null? username.hashCode() : 0);
	        result = 31 * result + (opportunityId !=null? opportunityId.hashCode() : 0);
	        return result;
	    }  
		
	}

}
