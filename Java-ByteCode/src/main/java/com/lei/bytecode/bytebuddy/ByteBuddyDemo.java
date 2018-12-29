package com.lei.bytecode.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

public class ByteBuddyDemo {


    public void createClass() {
        // 创建的类位于同父类相同的包下，这样父类的包级访问权限的方法对动态类型也可见。
        // java.lang包下的类扩展时，默认这些类型名称将会冠以 net.bytebuddy.renamed 的前缀
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                // 父类
                .subclass(Object.class)
                // 类名称，没指定使用约定配置
                // 默认配置提供了 NamingStrategy，它基于动态类型的超类名称来随机生成类名。
                .name("ClassName")
                .make();

        DynamicType.Unloaded<?> dynamicType2 = new ByteBuddy()
                .with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription typeDescription) {
                        return "i.love.ByteBuddy." + typeDescription.getSimpleName();
                    }
                })
                .subclass(Object.class)
                .make();
    }
}
