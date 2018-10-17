package com.yupaits;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;

/**
 * @author yupaits
 * @date 2018/10/17
 */
public class SessionTest {

    public static void main(String[] args) {
        Session session = new SimpleSession();
        System.out.println(new JavaUuidSessionIdGenerator().generateId(session));
    }
}
