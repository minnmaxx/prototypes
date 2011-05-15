package org.talz.domain.product;

import org.talz.AbstractReadOnlyConfigurableAspect.IReadOnlyConfigurable;

privileged aspect ReadOnlyConfigurableImplAspect {

	private interface IPackageInterface extends IReadOnlyConfigurable {}
	
	declare parents : org.talz.domain.product.* implements IPackageInterface;
	
	boolean IPackageInterface.readOnly = false;
	
	public boolean IPackageInterface.isReadOnly() {
		return this.readOnly;
	}
	public void IPackageInterface.readOnlyTrue() {
		this.readOnly = true;
	}
}
