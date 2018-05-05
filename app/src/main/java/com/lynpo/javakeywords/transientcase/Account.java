package com.lynpo.javakeywords.transientcase;

import java.io.Serializable;


/**
 * Create by fujw on 2018/4/17.
 * *
 * Account
 */
public class Account implements Serializable {
    // Java 的序列化机制通过判断以下 ID 来进行版本比对，本处使用默认
    private static final long serialVersionUID = 1L;

    private Long accountId;
    private String username;
    // transient 修饰：
    private transient String password;
    private transient double balance;

    public static int staticVar;

    public Account(Long accountId, String username, String password, double balance) {
        super();
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String toString() {
        return "Account [accountId=" + accountId + ", username=" + username + ", password=" + password + ", balance="
                + balance + "]";
    }
}
