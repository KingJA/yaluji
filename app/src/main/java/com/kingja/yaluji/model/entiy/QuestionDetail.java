package com.kingja.yaluji.model.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/11/7 23:48
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QuestionDetail {

    /**
     * questionCount : 2
     * userCorrectCount : 1
     * paperQuestion : {"id":"665294762d724e5b9d1df3b3ceb6a37e","PaperId":"a43d98c1bf314582b681b0f3a9f9f9ac",
     * "title":"问题2"}
     * correctCount : 8
     * questionItems : [{"id":"1524122492e64ab494005c7a83253d8e","questionId":"665294762d724e5b9d1df3b3ceb6a37e",
     * "content":"223aax"},{"id":"92cfe9d6762e41a1a3ce0cc37c428404","questionId":"665294762d724e5b9d1df3b3ceb6a37e",
     * "content":"2323"},{"id":"cc2c67e027b444c3b291c7eb26c3fc3d","questionId":"665294762d724e5b9d1df3b3ceb6a37e",
     * "content":"1112"},{"id":"d666f9d73113438ebb840271b09e9690","questionId":"665294762d724e5b9d1df3b3ceb6a37e",
     * "content":"4444"},{"id":"f2a124c69e4247dbbd6546c058dbae1f","questionId":"665294762d724e5b9d1df3b3ceb6a37e",
     * "content":"2223ccc"}]
     */

    private int questionCount;
    private int userCorrectCount;
    private PaperQuestion paperQuestion;
    private int correctCount;
    private List<Answer> questionItems;

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getUserCorrectCount() {
        return userCorrectCount;
    }

    public void setUserCorrectCount(int userCorrectCount) {
        this.userCorrectCount = userCorrectCount;
    }

    public PaperQuestion getPaperQuestion() {
        return paperQuestion;
    }

    public void setPaperQuestion(PaperQuestion paperQuestion) {
        this.paperQuestion = paperQuestion;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public List<Answer> getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(List<Answer> questionItems) {
        this.questionItems = questionItems;
    }

    public static class PaperQuestion {
        /**
         * id : 665294762d724e5b9d1df3b3ceb6a37e
         * PaperId : a43d98c1bf314582b681b0f3a9f9f9ac
         * title : 问题2
         */

        private String id;
        private String paperId;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
