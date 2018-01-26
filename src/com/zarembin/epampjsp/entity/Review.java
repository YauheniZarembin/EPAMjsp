package com.zarembin.epampjsp.entity;

public class Review extends Entity{
    private int reviewId;
    private String userName;
    private int mark;
    private String textReview;

    public Review(int reviewId, String userName, int mark, String textReview) {
        this.reviewId = reviewId;
        this.userName = userName;
        this.mark = mark;
        this.textReview = textReview;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (reviewId != review.reviewId) return false;
        if (mark != review.mark) return false;
        if (userName != null ? !userName.equals(review.userName) : review.userName != null) return false;
        return textReview != null ? textReview.equals(review.textReview) : review.textReview == null;
    }

    @Override
    public int hashCode() {
        int result = reviewId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + (textReview != null ? textReview.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", userName='" + userName + '\'' +
                ", mark=" + mark +
                ", textReview='" + textReview + '\'' +
                '}';
    }
}
