package com.lei.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class WhichGC {
	public static void main(String[] args) {
		try {
            List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
                System.out.println(gcMxBean.getName());
            }
        } catch (Exception exp) {
            System.err.println(exp);
        }
	}
}
