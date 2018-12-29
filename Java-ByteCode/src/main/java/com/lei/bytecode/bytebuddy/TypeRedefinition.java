package com.lei.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

/**
 * type redefinition 类型重定义
 */
public class TypeRedefinition {

    public static void main(String[] args) {
        new ByteBuddy().subclass(Foo.class);
        new ByteBuddy().redefine(Foo.class);
        new ByteBuddy().rebase(Foo.class);

        Class<?> type = new ByteBuddy()
                .subclass(Object.class)
                .make()
                .load(TypeRedefinition.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }
}

class Foo {
    String bar() { return "foo"; }
}

class Bar {
    String m() { return "bar"; }
}