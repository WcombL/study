package com.lei.ch1;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

@NotThreadSafe
// ++count：操作是非原子操作。由三个操作：读-改-写
public class UnsafeCountingFactorizer extends GenericServlet implements Servlet {

    private long count = 0;

    public long getCount() {
        return count;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        BigInteger i = extractFromRequest(request);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(response, factors);
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
