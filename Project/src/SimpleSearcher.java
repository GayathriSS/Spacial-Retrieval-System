

import java.io.File;
import java.util.ArrayList;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SimpleSearcher {
	
	
	public void main_searcher(ArrayList<String> queries) throws Exception {
			
			//path - location of indexed file
		File indexDir = new File("C:\\Users\\saranath\\Documents\\WorkSpace_Eclipse\\LuceneIntroProject");
		int hits = 100;
		int i =0;
		
		SimpleSearcher searcher = new SimpleSearcher();
		for (String string : queries) {
			MainClass.results[i] = searcher.searchIndex(indexDir, string, hits);
			i++;
		}
		
		
	}
	
	//searching and returning doc id
	private ArrayList<String> searchIndex(File indexDir, String queryStr, int maxHits) throws Exception {
		
		Directory directory = FSDirectory.open(indexDir);
		ArrayList<String> doc_ids = new ArrayList<String>();
		IndexSearcher searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new SimpleAnalyzer());
		Query query = parser.parse(queryStr);
		
		TopDocs topDocs = searcher.search(query, maxHits);
		
		ScoreDoc[] hits = topDocs.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			doc_ids.add(d.get("filename"));
		}
		
		return doc_ids;
	}

}
