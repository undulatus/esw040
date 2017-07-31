package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sys_system_role_access")
public class SystemRoleAccess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemRoleAccess() {
		super();
	}
	
	public SystemRoleAccess(String systemRole, String module, String action) {
		SystemRoleAccess.SystemRoleAccessKey key = new SystemRoleAccess.SystemRoleAccessKey();
		key.setSystemRole(systemRole);
		key.setModule(module);
		key.setAction(action);
		this.setKey(key);
	}
	
	@EmbeddedId
	private SystemRoleAccessKey key;
	
	public SystemRoleAccessKey getKey() {
		return key;
	}

	public void setKey(SystemRoleAccessKey key) {
		this.key = key;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Embeddable
	public static class SystemRoleAccessKey implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Column(name="system_role")
		private String systemRole;

		@Column(name="system_role_access_module")
		private String module;
		
		@Column(name="system_role_access_action")
		private String action;

		public String getSystemRole() {
			return systemRole;
		}

		public void setSystemRole(String systemRole) {
			this.systemRole = systemRole;
		}

		public String getModule() {
			return module;
		}

		public void setModule(String module) {
			this.module = module;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}
		
		@Override
		public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || this.getClass() != o.getClass()) return false;
	        SystemRoleAccessKey that = (SystemRoleAccessKey) o;
	        if (systemRole != null? !systemRole.equals(that.getSystemRole()) : that.getSystemRole() != null) return false;
	        if (module != null? !module.equals(that.getModule()) : that.getModule()!=null) return false;
	        if (action != null? !action.equals(that.getAction()) : that.getAction()!=null) return false;
	        return true;
	    }

		@Override
	    public int hashCode() {
	        int result;
	        result = (systemRole != null? systemRole.hashCode() : 0);
	        result = 31 * result + (module !=null? module.hashCode() : 0);
	        result = 31 * result + (action !=null? action.hashCode() : 0);
	        return result;
	    }
	}

}
