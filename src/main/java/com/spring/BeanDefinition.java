package com.spring;

public class BeanDefinition {

    //bean的类型
    private Class aClass;

    //bean的作用域
    private String scope;

    public BeanDefinition() {
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
