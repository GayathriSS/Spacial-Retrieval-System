

import java.util.ArrayList;
import java.util.List;

public class DocumentV {
	int docno;
	String paragraph;
	String url;
	ArrayList<String> relevance;
	String query;
	boolean judgement;

	public int getDocno() {
		return docno;
	}

	public void setDocno(int docno) {
		this.docno = docno;
	}

	public String getParagraph() {
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getRelevance() {
		return relevance;
	}

	public void setRelevance(ArrayList<String> relevance) {
		this.relevance = relevance;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isJudgement() {
		return judgement;
	}

	public void setJudgement(boolean judgement) {
		this.judgement = judgement;
	}

}
