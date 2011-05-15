package test;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

import flex.management.ManageableComponent;
import flex.messaging.config.ConfigMap;


public class ManageableComponentFactoryBean implements FactoryBean, BeanNameAware {

    private ConfigMap properties = new ConfigMap();

    private String beanName;

    private final Class<? extends ManageableComponent> componentClass;

    /**
     * Creates a new ManageableComponentFactoryBean for the specified component class
     * 
     * @param componentClass the class of the component this {@link FactoryBean} will create
     */
    public ManageableComponentFactoryBean(Class<? extends ManageableComponent> componentClass) {
        this.componentClass = componentClass;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public Object getObject() throws Exception {
        ManageableComponent component = (ManageableComponent) BeanUtils.instantiateClass(this.componentClass);
        component.setId(this.beanName);
        component.initialize(this.beanName, this.properties);
        return component;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public Class<?> getObjectType() {
        return this.componentClass;
    }

    /**
     * It is expected that objects created by this factory will always be prototype instances.
     */
    public final boolean isSingleton() {
        return true;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /**
     * Sets the properties {@link ConfigMap} to use in initializing the created component
     * 
     * @param properties the properties map
     */
    public void setProperties(ConfigMap properties) {
        this.properties = properties;
    }

}
