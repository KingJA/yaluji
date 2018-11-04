package com.kingja.yaluji.model.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/11/4 10:27
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleDetail {
    private PreviousArticleBean previousArticle;
    private ArticleBean article;
    private NextArticleBean nextArticle;
    private List<ArticleSimpleItem> recommendArticles;

    public PreviousArticleBean getPreviousArticle() {
        return previousArticle;
    }

    public void setPreviousArticle(PreviousArticleBean previousArticle) {
        this.previousArticle = previousArticle;
    }

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }

    public NextArticleBean getNextArticle() {
        return nextArticle;
    }

    public void setNextArticle(NextArticleBean nextArticle) {
        this.nextArticle = nextArticle;
    }

    public List<ArticleSimpleItem> getRecommendArticles() {
        return recommendArticles;
    }

    public void setRecommendArticles(List<ArticleSimpleItem> recommendArticles) {
        this.recommendArticles = recommendArticles;
    }

    public static class PreviousArticleBean {
        /**
         * id : 3
         * title :  100%纯玩-进店赔2000+连住三亚高级酒店丨蜈支洲玩一整天+南山+天涯
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ArticleBean {
        /**
         * id : 9ac284cf351b4c69b0abe6e10fc4d1a6
         * periodnum : 20181024172924
         * title :  海南三亚纯玩半自助5日浪漫游☂4晚连住☂京海假日海景套房☀蜈支洲+天堂森林
         * headimg : /upload/image/20181030/20181030231216_233.jpg
         * content :
         * sort : 1
         * keywords : 112
         * cndate : 2018年10月24日
         */

        private String id;
        private String periodnum;
        private String title;
        private String headimg;
        private String content;
        private int sort;
        private String keywords;
        private String cndate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPeriodnum() {
            return periodnum;
        }

        public void setPeriodnum(String periodnum) {
            this.periodnum = periodnum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getCndate() {
            return cndate;
        }

        public void setCndate(String cndate) {
            this.cndate = cndate;
        }
    }

    public static class NextArticleBean {
        /**
         * id : ce662535c61a4a4eb80abe3ed3e772e3
         * title : 火爆地带 不带钱畅游三亚|住海景房&吃海鲜大餐&游蜈支洲+分界洲双岛
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
