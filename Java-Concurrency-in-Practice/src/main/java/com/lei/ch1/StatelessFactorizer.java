package com.lei.ch1;


import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

@ThreadSafe
// 无状态
public class StatelessFactorizer extends GenericServlet implements Servlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        BigInteger i = extractFromRequest(request);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(response, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }

}
