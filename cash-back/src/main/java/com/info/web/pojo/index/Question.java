package com.info.web.pojo.index;

/**
 * 问答
 * 
 * @author gaoyuhai
 * 
 */
public class Question {

	private String questionName;
	private String questionContent;
	private String moreUrl;

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	@Override
	public String toString() {
		return "Question [moreUrl=" + moreUrl + ", questionContent="
				+ questionContent + ", questionName=" + questionName + "]";
	}

}
