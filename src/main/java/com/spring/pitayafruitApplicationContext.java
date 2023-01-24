package com.spring;

import com.spring.annotation.Component;
import com.spring.annotation.ComponentScan;
import com.spring.annotation.Scope;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

//spring容器
public class pitayafruitApplicationContext {

    //配置文件参数
    private Class configClass;

    //单例池
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    //所有bean定义
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionObjects = new ConcurrentHashMap<>();

    //构造方法
    public pitayafruitApplicationContext(Class configClass){
        this.configClass = configClass;
        //解析配置类
        //ComponentScan注解 =》 扫描路径 =》 扫描
        ComponentScan componentScan = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String scanPath = componentScan.value();
        scanPath = scanPath.replace(".", "/");
        //扫描 通过类加载器扫描路径下的资源
        ClassLoader classLoader = pitayafruitApplicationContext.class.getClassLoader(); //app应用类加载器
        URL resource = classLoader.getResource(scanPath);
        //将路径资源转成目录
        File file = new File(resource.getFile());
        //判断下是否是目录对象
        if (file.isDirectory()){
            File[] listFiles = file.listFiles();
            for (File listFile : listFiles) {
                String fileName = listFile.getAbsolutePath();
                //先判断下对象是否是类文件
                if (fileName.endsWith(".class")){
                    fileName = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class")).replace("\\",".");
                    //通过类的全限定名去加载相应的类
                    try {
                        Class<?> aClass = classLoader.loadClass(fileName);
                        //判断是否有相应注解
                        if (aClass.isAnnotationPresent(Component.class)){
                            //表示当前类是一个Bean
                            //解析类->BeanDefinition
                            Component componentAnnotation = aClass.getDeclaredAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            //解析scope注解判断是否为单例bean
                            BeanDefinition beanDefinition = new BeanDefinition();
                            if (aClass.isAnnotationPresent(Scope.class)){
                                Scope scopeAnnotation = aClass.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            }else{
                                beanDefinition.setScope("singleton");
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //相关方法
    public Object getBean(String beanName){
        return null;
    }

}
