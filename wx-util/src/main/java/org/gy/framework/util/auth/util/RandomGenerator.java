package org.gy.framework.util.auth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomGenerator {

    protected final Logger         logger   = LoggerFactory.getLogger(RandomGenerator.class);

    private static RandomGenerator instance = new RandomGenerator();

    /**
     * 常量，数字4.
     */
    private static final int       FOUR     = 4;

    /**
     * 常量，数字8.
     */
    private static final int       EIGHT    = 8;

    /**
     * 常量，数字10.
     */
    private static final int       TEN      = 10;

    /**
     * 常量，数字16.
     */
    private static final int       SIXTEEN  = 16;

    /**
     * Seed the random number
     */
    public RandomGenerator() {
        // Start with the current system time as a seed
        long seed = System.currentTimeMillis();
        // Also throw in the system identifier for 'this' from toString
        char[] entropy = toString().toCharArray();
        for (int i = 0; i < entropy.length; i++) {
            // noinspection IntegerMultiplicationImplicitCastToLong
            long update = ((byte) entropy[i]) << ((i % EIGHT) * EIGHT);
            seed ^= update;
        }
        random.setSeed(seed);
    }

    public static String nextId() {
        return instance.generateId(SIXTEEN);
    }

    /**
     * Generate and return a new session identifier.
     * 
     * @param length The number of bytes to generate
     * @return A new page id string
     */
    public synchronized String generateId(int length) {
        byte[] buffer = new byte[length];
        // Render the result as a String of hexadecimal digits
        StringBuilder reply = new StringBuilder();
        int resultLenBytes = 0;
        while (resultLenBytes < length) {
            random.nextBytes(buffer);
            buffer = getDigest().digest(buffer);
            for (int j = 0; j < buffer.length && resultLenBytes < length; j++) {
                byte b1 = (byte) ((buffer[j] & 0xf0) >> FOUR);
                if (b1 < 10) {
                    reply.append((char) ('0' + b1));
                } else {
                    reply.append((char) ('A' + (b1 - TEN)));
                }
                byte b2 = (byte) (buffer[j] & 0x0f);
                if (b2 < 10) {
                    reply.append((char) ('0' + b2));
                } else {
                    reply.append((char) ('A' + (b2 - TEN)));
                }
                resultLenBytes++;
            }
        }
        return reply.toString();
    }

    /**
     * @return the algorithm
     */
    public synchronized String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm the algorithm to set
     */
    public synchronized void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        digest = null;
    }

    /**
     * Return the MessageDigest object to be used for calculating session identifiers. If none has been created yet,
     * initialize one the first time this method is called.
     * 
     * @return The hashing algorithm
     */
    private MessageDigest getDigest() {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException ex) {
                try {
                    digest = MessageDigest.getInstance(DEFAULT_ALGORITHM);
                } catch (NoSuchAlgorithmException ex2) {
                    digest = null;
                    throw new IllegalStateException("No algorithms for IdGenerator");
                }
            }
            logger.info("Using MessageDigest: " + digest.getAlgorithm());
        }
        return digest;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings({
        "EmptyMethod"
    })
    @Override
    public final String toString() {
        // This is to make the point that we need toString to return something
        // that includes some sort of system identifier as does the default.
        // Don't change this unless you really know what you are doing.
        return super.toString();
    }

    /**
     * The default message digest algorithm to use if we cannot use the requested one.
     */
    protected static final String DEFAULT_ALGORITHM = "MD5";

    /**
     * The message digest algorithm to be used when generating session identifiers. This must be an algorithm supported
     * by the <code>java.security.MessageDigest</code> class on your platform.
     */
    private String                algorithm         = DEFAULT_ALGORITHM;

    /**
     * A random number generator to use when generating session identifiers.
     */
    private Random                random            = new SecureRandom();

    /**
     * Return the MessageDigest implementation to be used when creating session identifiers.
     */
    private MessageDigest         digest            = null;

}
