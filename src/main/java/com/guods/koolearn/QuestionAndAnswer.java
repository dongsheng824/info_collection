package com.guods.koolearn;

public class QuestionAndAnswer {

	private String question;
	private String Answer;
	public QuestionAndAnswer(String question, String answer) {
		super();
		this.question = question;
		Answer = answer;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
}
