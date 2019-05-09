package org.gy.framework.util.auth.storage.redis;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.auth.config.Configuration;
import org.gy.framework.util.auth.principal.AuthPrincipal;
import org.gy.framework.util.auth.principal.AuthPrincipalImpl;
import org.gy.framework.util.auth.storage.StorageAccessor;
import org.gy.framework.util.auth.util.SerializeUtils;
import org.gy.framework.util.jedis.RedisClient;
import org.gy.framework.util.jedis.ShardedJedisAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

public class RedisStorageAccessorImpl implements StorageAccessor {

    protected static final Logger logger = LoggerFactory.getLogger(RedisStorageAccessorImpl.class);

    private static final String   ID_KEY = "AUTH_STORAGE_ID";

    private RedisClient           redisClient;

    private RedisStorageConfig    redisStorageConfig;

    public RedisStorageAccessorImpl(RedisClient redisClient, RedisStorageConfig redisStorageConfig) {
        this.redisClient = redisClient;
        this.redisStorageConfig = redisStorageConfig;
    }

    private String getRedisKey(String sessionIdentifier) {
        return MessageFormat.format(redisStorageConfig.getPrincipalKeyPattern(), sessionIdentifier);
    }

    private String getSnapshotRedisKey(String snapshotId) {
        return MessageFormat.format(redisStorageConfig.getSnapshotKeyPattern(), snapshotId);
    }

    @Override
    public AuthPrincipal getPrincipal(String sessionIdentifier) {
        if (StringUtils.isBlank(sessionIdentifier)) {
            throw new IllegalArgumentException("The param sessionIdentifier can't be null!");
        }
        final String redisKey = getRedisKey(sessionIdentifier);
        try {
            Map<String, String> values = redisClient.execute(new ShardedJedisAction<Map<String, String>>() {
                @Override
                public Map<String, String> doAction(ShardedJedis shardedJedis) {
                    return shardedJedis.hgetAll(redisKey);
                }
            });
            if (values != null) {
                String id = values.remove(ID_KEY);
                if (StringUtils.isNotBlank(id)) {
                    return new AuthPrincipalImpl(id, values);
                }
            }
        } catch (Exception e) {
            logger.error("getPrincipal failed,sessionIdentifier=" + sessionIdentifier, e);
        }
        return null;
    }

    @Override
    public void savePrincipal(String sessionIdentifier, final AuthPrincipal principal) {
        if (StringUtils.isBlank(sessionIdentifier) || null == principal) {
            throw new IllegalArgumentException("The param sessionIdentifier, principal can't be null!");
        }
        final String redisKey = getRedisKey(sessionIdentifier);
        try {
            redisClient.execute(new ShardedJedisAction<String>() {
                @Override
                public String doAction(ShardedJedis shardedJedis) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(ID_KEY, principal.getName());
                    map.putAll(principal.getAttributes());
                    shardedJedis.hmset(redisKey, map);
                    shardedJedis.expire(redisKey, redisStorageConfig.getPrincipalExpireTime());
                    return null;
                }
            });
        } catch (Exception e) {
            logger.error("savePrincipal failed,sessionIdentifier=" + sessionIdentifier, e);
        }

    }

    @Override
    public void removePrincipal(String sessionIdentifier) {
        if (StringUtils.isBlank(sessionIdentifier)) {
            throw new IllegalArgumentException("The param sessionIdentifier can not both be null!");
        }

        final String redisKey = getRedisKey(sessionIdentifier);
        try {
            redisClient.execute(new ShardedJedisAction<Long>() {
                @Override
                public Long doAction(ShardedJedis shardedJedis) {
                    return shardedJedis.del(redisKey);
                }
            });
        } catch (Exception e) {
            logger.error("removePrincipal failed,sessionIdentifier=" + sessionIdentifier, e);
        }

    }

    @Override
    public void saveRequestParamsSnapshot(final String snapshotId, final Map paramsSnapshot) {
        if (StringUtils.isBlank(snapshotId) || null == paramsSnapshot) {
            throw new IllegalArgumentException("The param snapshotId and paramsSnapshot can't be null!");
        }
        final byte[] redisKey = getSnapshotRedisKey(snapshotId).getBytes(Charset.defaultCharset());
        redisClient.execute(new ShardedJedisAction<Long>() {
            @Override
            public Long doAction(ShardedJedis shardedJedis) {
                byte[] result = SerializeUtils.serialize(paramsSnapshot);
                if (result != null) {
                    shardedJedis.setex(redisKey, redisStorageConfig.getSnapshotExpireTime(), result);
                }
                return null;
            }
        });

    }

    @Override
    public Map getRequestParamsSnapshot(String snapshotId) {
        if (StringUtils.isBlank(snapshotId)) {
            throw new IllegalArgumentException("The param snapshotId can't be null!");
        }
        final byte[] redisKey = getSnapshotRedisKey(snapshotId).getBytes(Charset.defaultCharset());
        byte[] result = redisClient.execute(new ShardedJedisAction<byte[]>() {
            @Override
            public byte[] doAction(ShardedJedis shardedJedis) {
                return shardedJedis.get(redisKey);
            }
        });
        if (result != null) {
            Object value = SerializeUtils.deserialize(result);
            if (value != null) {
                return (Map) value;
            }
        }
        return null;
    }

    @Override
    public void removeRequestParamsSnapshot(String snapshotId) {
        if (StringUtils.isBlank(snapshotId)) {
            throw new IllegalArgumentException("The param snapshotId can't be null!");
        }
        final byte[] redisKey = getSnapshotRedisKey(snapshotId).getBytes(Charset.defaultCharset());
        redisClient.execute(new ShardedJedisAction<Long>() {
            @Override
            public Long doAction(ShardedJedis shardedJedis) {
                return shardedJedis.del(redisKey);
            }
        });

    }

    public static class RedisStorageConfig {

        private String principalKeyPattern = Configuration.DEFAULT_KEY_PATTERN;

        private int    principalExpireTime = Configuration.DEFAULT_EXPIRE_TIME;

        private String snapshotKeyPattern  = Configuration.DEFAULT_SNAPSHOT_KEY_PATTERN;

        private int    snapshotExpireTime  = Configuration.DEFAULT_SNAPSHOT_EXPIRE_TIME;

        public String getPrincipalKeyPattern() {
            return principalKeyPattern;
        }

        public void setPrincipalKeyPattern(String principalKeyPattern) {
            this.principalKeyPattern = principalKeyPattern;
        }

        public int getPrincipalExpireTime() {
            return principalExpireTime;
        }

        public void setPrincipalExpireTime(int principalExpireTime) {
            this.principalExpireTime = principalExpireTime;
        }

        public String getSnapshotKeyPattern() {
            return snapshotKeyPattern;
        }

        public void setSnapshotKeyPattern(String snapshotKeyPattern) {
            this.snapshotKeyPattern = snapshotKeyPattern;
        }

        public int getSnapshotExpireTime() {
            return snapshotExpireTime;
        }

        public void setSnapshotExpireTime(int snapshotExpireTime) {
            this.snapshotExpireTime = snapshotExpireTime;
        }

    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public RedisStorageConfig getRedisStorageConfig() {
        return redisStorageConfig;
    }

    public void setRedisStorageConfig(RedisStorageConfig redisStorageConfig) {
        this.redisStorageConfig = redisStorageConfig;
    }

}
