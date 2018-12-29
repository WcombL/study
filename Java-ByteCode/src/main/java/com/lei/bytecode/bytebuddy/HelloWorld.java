package com.lei.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloWorld {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("ClassName")
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(HelloWorld.class.getClassLoader())
                .getLoaded();
        System.out.println(dynamicType.getName());
        System.out.println(dynamicType.newInstance().toString());
    }
}
