package org.gy.framework.util.weixin.message.json.custom;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：图文集合封装
 * 
 */
public class CustomNews {

    /**
     * 图文集合，条数限制在10条以内，注意，如果图文数超过10，则将会无响应
     */
    @JsonProperty(value = "articles")
    private List<CustomArticle> articles;

    /**
     * 获取图文集合，条数限制在10条以内，注意，如果图文数超过10，则将会无响应
     * 
     * @return articles 图文集合，条数限制在10条以内，注意，如果图文数超过10，则将会无响应
     */
    public List<CustomArticle> getArticles() {
        return articles;
    }

    /**
     * 设置图文集合，条数限制在10条以内，注意，如果图文数超过10，则将会无响应
     * 
     * @param articles 图文集合，条数限制在10条以内，注意，如果图文数超过10，则将会无响应
     */
    public void setArticles(List<CustomArticle> articles) {
        this.articles = articles;
    }

}
