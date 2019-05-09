package org.gy.framework.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.cache.RedisConfig;
import org.gy.framework.util.cache.RedisConfig.CacheKey;
import org.gy.framework.util.jedis.JedisBean;
import org.gy.framework.util.jedis.JedisType;
import org.gy.framework.util.jedis.RedisClient;
import org.gy.framework.util.jedis.ShardedJedisAction;
import org.gy.framework.util.jedis.SimpleJedisUtils;
import org.gy.framework.util.response.Response;
import org.gy.framework.vo.ComboBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * 功能描述：主系统redis管理
 * 
 * @version 2.0.0
 * @author guanyang
 */
@Controller
@RequestMapping("/masterRedis")
public class SystemMasterRedisController extends BaseController {

    private static final String DEFAULT_KEY_PREFIX = "GY:CACHE:CONFIG*";

    private static final String PATTERN_STRING     = ":{";

    private static final String DEFAULT_SUFFIX     = "*";

    @Autowired
    private RedisClient         redisClient;

    /**
     * 功能描述: 跳转到key信息列表页
     * 
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("admin/masterRedis/list.ftl");
    }

    /**
     * 功能描述: 获取key列表
     * 
     * @param prefix key前缀，必须带*
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String prefix) {
        if (StringUtils.isBlank(prefix) || "*".equals(prefix)) {
            prefix = DEFAULT_KEY_PREFIX;
        }
        if (!prefix.endsWith("*")) {
            prefix = DEFAULT_KEY_PREFIX;
        }
        Map<String, Object> map = new HashMap<>();
        List<JedisBean> list = new ArrayList<>();
        Collection<Jedis> jedisList = redisClient.getAllShards();
        if (jedisList != null) {
            for (Jedis jedis : jedisList) {
                Set<String> keys = jedis.keys(prefix);
                for (String string : keys) {
                    JedisBean bean = new JedisBean();
                    bean.setKey(string);
                    bean.setType(jedis.type(string));
                    bean.setTimeToLive(jedis.ttl(string));
                    list.add(bean);
                }
            }
        }
        map.put("rows", list);
        map.put("total", list.size());
        ajaxJson(request, response, map);
    }

    /**
     * 功能描述: 删除redis缓存
     * 
     * @param keys key值串，逗号分隔
     * @return 删除成功的条数
     * @version 2.0.0
     * @author guanyang
     */
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       String keys) {

        if (StringUtils.isBlank(keys)) {
            ajaxJson(request, response, 0);
            return;
        }
        String[] key = keys.split(",");
        int count = 0;
        if (null != key && key.length > 0) {
            for (final String string : key) {
                Long tmpLong = redisClient.execute(new ShardedJedisAction<Long>() {
                    @Override
                    public Long doAction(ShardedJedis jedis) {
                        return jedis.del(string);
                    }
                });
                count += tmpLong;
            }
        }
        ajaxJson(request, response, count);
    }

    /**
     * 功能描述: 获取key对应信息
     * 
     * @param bean jedis封装对象
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void get(HttpServletRequest request,
                    HttpServletResponse response,
                    final JedisBean bean) {
        JedisBean result = redisClient.execute(new ShardedJedisAction<JedisBean>() {
            @Override
            public JedisBean doAction(ShardedJedis jedis) {
                return SimpleJedisUtils.getByKey(jedis, bean);//
            }
        });
        ajaxJson(request, response, result);
    }

    /**
     * 功能描述: 设置redis缓存
     * 
     * @param key 键
     * @param value 值
     * @param time 有效期，单位：秒
     * @return true设置成功，false失败
     * @version 2.0.0
     * @author guanyang
     */
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public void set(HttpServletRequest request,
                    HttpServletResponse response,
                    final JedisBean bean) {
        Response<Object> result = new Response<>();
        result.setSuccess(false);
        if (StringUtils.isBlank(bean.getKey())) {
            result.setMessage("key不能为空");
            ajaxJson(request, response, result);
            return;
        }
        if (bean.getValue() == null) {
            result.setMessage("value不能为空");
            ajaxJson(request, response, result);
            return;
        }
        if (bean.getTime() == null || bean.getTime() < 1) {
            result.setMessage("expire必须大于零");
            ajaxJson(request, response, result);
            return;
        }
        if (StringUtils.isBlank(bean.getType())) {
            result.setMessage("type不能为空");
            ajaxJson(request, response, result);
            return;
        }
        if (JedisType.ZSET.getCode().equals(bean.getType())) {
            if (bean.getScore() == null) {
                result.setMessage("zset类型score不能为空");
                ajaxJson(request, response, result);
                return;
            }
        }
        if (JedisType.HASH.getCode().equals(bean.getType())) {
            if (StringUtils.isBlank(bean.getField())) {
                result.setMessage("hash类型field不能为空");
                ajaxJson(request, response, result);
                return;
            }
        }
        Object tmp = redisClient.execute(new ShardedJedisAction<Object>() {
            @Override
            public Object doAction(ShardedJedis jedis) {
                return SimpleJedisUtils.setRedis(jedis, bean);//
            }
        });
        result.setSuccess(true);
        result.setMessage("操作成功");
        result.setResult(tmp);
        ajaxJson(request, response, result);
    }

    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public void getKeyList(HttpServletRequest request,
                           HttpServletResponse response) {
        CacheKey[] cacheKeys = RedisConfig.CacheKey.values();
        List<ComboBean> list = new ArrayList<>();
        for (int i = 0; i < cacheKeys.length; i++) {
            CacheKey item = cacheKeys[i];
            String value = item.getKeyPrefix();
            int index = value.indexOf(PATTERN_STRING);
            if (index != -1) {
                // 如果有占位符
                value = value.substring(0, index).concat(DEFAULT_SUFFIX);
            } else {
                // 没有占位符
                value = value.concat(DEFAULT_SUFFIX);
            }
            String group = RedisConfig.TYPE_CACHE;
            if (value.contains(RedisConfig.TYPE_LOCK)) {
                group = RedisConfig.TYPE_LOCK;
            } else if (value.contains(RedisConfig.TYPE_PERSIST)) {
                group = RedisConfig.TYPE_PERSIST;
            }
            ComboBean bean = new ComboBean();
            bean.setValue(value);
            bean.setText(item.getDescription());
            bean.setGroup(group);
            bean.setExpireTime(item.getExpireTime());
            list.add(bean);
        }
        ajaxJson(request, response, list);

    }
}
