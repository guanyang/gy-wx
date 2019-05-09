package org.gy.framework.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
    private static final RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final String                algorithmName         = "md5";
    private static final int                   hashIterations        = 2;

    /**
     * 功能描述: 生成盐
     * 
     * @return
     * @Author gy
     */
    public static String generateSalt() {
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * 功能描述: 根据盐、明文密码生成密文密码
     * 
     * @param source 明文密码
     * @param salt 盐值
     * @return
     * @Author gy
     */
    public static String generatePassword(String source, String salt) {
        return new SimpleHash(algorithmName, source, ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

    /**
     * 功能描述: 验证密码是否相等
     * 
     * @param source 明文密码
     * @param salt 盐值
     * @param target 密文密碼
     * @return
     * @Author gy
     */
    public static boolean validateEqual(String source, String salt, String target) {
        return generatePassword(source, salt).equals(target);
    }
    
    public static void main(String[] args) {
        System.out.println(generatePassword("123456", "b6d97d8b2ff7d54a1b9819b4e9c78205"));
    }
}
