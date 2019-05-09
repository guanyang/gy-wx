package org.gy.framework.util.weixin.message.xml.response;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 功能描述: 图文响应消息
 * 
 */
@XStreamAlias("xml")
public class NewsResponseMessage extends WeiXinResponse {

    /**
     * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
     */
    @XStreamAlias("ArticleCount")
    private Integer                  articleCount;

    @XStreamAlias("Articles")
    private List<NewsArticle> articles = new ArrayList<NewsArticle>();

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public void addArticle(NewsArticle article) {
        articles.add(article);
    }

    public void removeArticle(NewsArticle article) {
        articles.remove(article);
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsArticle> articles) {
        this.articles = articles;
    }

}
