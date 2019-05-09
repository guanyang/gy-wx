package org.gy.framework.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.gy.framework.biz.WeiXinBiz;
import org.gy.framework.util.weixin.api.custom.CustomService;
import org.gy.framework.util.weixin.message.json.custom.CustomArticle;
import org.gy.framework.util.weixin.message.json.custom.CustomNews;
import org.gy.framework.util.weixin.message.json.custom.CustomNewsMessage;
import org.gy.framework.util.weixin.util.GeneralResponse;
import org.gy.framework.util.weixin.util.WeiXinConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private WeiXinBiz weiXinBiz;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/test.ftl");
        mav.addObject("time", new Date());
        return mav;
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public ModelAndView json() {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        mav.addObject("time", new Date());
        logger.debug("测试日志debug");
        logger.info("测试日志info");
        logger.error("测试日志error");
        return mav;
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ModelAndView token(String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        mav.addObject("token", weiXinBiz.getTokenByAppId(appId));
        return mav;
    }

    @RequestMapping(value = "/customService", method = RequestMethod.GET)
    public ModelAndView customService(String openId,
                                      String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());

        // 获取accessToken
        String accessToken = weiXinBiz.getTokenByAppId(appId);

        CustomNewsMessage message = new CustomNewsMessage();
        message.setTouser(openId);
        message.setMsgtype(WeiXinConstantUtil.MESSAGE_TYPE_NEWS);
        CustomNews news = new CustomNews();
        message.setNews(news);
        List<CustomArticle> list = new ArrayList<CustomArticle>();
        news.setArticles(list);
        CustomArticle article = new CustomArticle();
        article.setTitle("标题标题标题标题标题");
        article.setDescription("描述描述描述描述");
        article.setUrl("https://mp.weixin.qq.com/");
        article.setPicurl("https://mmbiz.qlogo.cn/mmbiz/dGgPndsiaHgwB8iaQmbrHcSUMsqib1kp4CZG5GI5ZZblcUT9MEL9n0ObUuQI2aJAcWL5eNWzjsFusLJnu6sWNhsVw/0?wx_fmt=jpeg");
        list.add(article);
        list.add(article);
        list.add(article);
        list.add(article);
        list.add(article);

        // 发送客服消息
        GeneralResponse result = CustomService.sendCustomMessage(accessToken, message);

        mav.addObject("accessToken", accessToken);
        mav.addObject("customResult", result);
        mav.addObject("time", System.currentTimeMillis());
        return mav;
    }

    public static void main(String[] args) {
        ZkClient client = new ZkClient("172.19.59.22:2181,172.19.59.23:2181,172.19.59.24:2181");
        boolean exists = client.exists("/lock1");
        String path = client.createEphemeralSequential("/lock1/node-", null);
        System.out.println(path);
    }

}
