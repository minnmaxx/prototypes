package org.talz.domain;

import org.talz.AbstractReadOnlyConfigurableAspect.IReadOnlyConfigurable;

privileged aspect ReadOnlyConfigurableImplAspect {

	private interface IPackageInterface extends IReadOnlyConfigurable {}
	
	declare parents : org.talz.domain.* implements IPackageInterface;
	
	boolean IPackageInterface.readOnly = false;

	public boolean IPackageInterface.isReadOnly() {
		return this.readOnly;
	}
	
	public void IPackageInterface.readOnlyTrue() {
		this.readOnly = true;
	}
	
	public String IPackageInterface.toString() {
		return Boolean.toString(this.readOnly);
	}
}