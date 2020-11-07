package com.zl.demo18.test2;

import java.util.HashMap;
import java.util.Map;

public class DbUtil {
    /**
     * 模拟从db中获取邮件配置信息，存放在map中
     *
     * @return
     */
    public static Map<String, Object> getMailInfoFromDb() {
        Map<String, Object> result = new HashMap<>();
        result.put("mail.host", "smtp.qq.com");
        result.put("mail.username", "路人");
        result.put("mail.password", "123");
        return result;
    }
}
