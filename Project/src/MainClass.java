import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainClass {
	
	static String[] xis = new String[] {
		"","symptoms signs","tests","treatements","preventions","body organs","","infectious agent",""
	};
	static HashMap<String, DocumentV> doc_hashmap;
	static ArrayList<String>[] Xs = (ArrayList<String>[]) new ArrayList[10];
	static ArrayList<String>[] results = (ArrayList<String>[]) new ArrayList[200];
	static ArrayList<String> positive = new ArrayList<String>();
	static ArrayList<String> negative = new ArrayList<String>();
	static Document doc_judge;
	static List<String> stopwords;

	//initializing 
	public static void initialize() {

		doc_hashmap = new HashMap<String, DocumentV>();
		stopwords = Arrays.asList("a", "able", "about", "across", "after",
				"all", "almost", "also", "am", "among", "an", "any",
				"are", "as", "at", "be", "because", "been", "but", "by", "can",
				"cannot", "did", "do", "does", "either", "else", "ever",
				"every", "for", "from", "get", "got", "had", "has", "have",
				"he", "her", "hers", "him", "his", "how", "however", "i", "if",
				"in", "into", "is", "it", "its", "just", "least", "let",
				"like", "likely", "may", "me", "might", "most", "must", "my",
				"neither", "no", "nor", "not", "of", "off", "often", "on",
				"only",  "other", "our", "own", "rather", "said", "say",
				"says", "she", "should", "since", "so", "some", "than", "that",
				"the", "their", "them", "then", "there", "these", "they",
				"this", "tis", "to", "too", "was", "us", "wants", "was", "we",
				"were", "what", "when", "where", "which", "while", "who",
				"whom", "why", "will", "with", "would", "yet", "you", "your",
				"");

		for (int i = 0; i < 10; i++) {
			Xs[i] = new ArrayList();
		}

	}
	
	//checking for stop words
	public static boolean checkForStopWords(String word) {

		for (String stopword : stopwords) {
			if (stopword.equalsIgnoreCase(word))
				return true;
		}
		return false;
	}

	//removing stopwords and checking if OR AND already present in query
	public static ArrayList<String> checkAndRemoveStopwords(String[] queries) {
		ArrayList<String> return_Q = new ArrayList<String>();
		int i =0;
		for (i =0;i<queries.length;i++) {
			if (!(checkForStopWords(queries[i]))) {
				if (queries[i].equalsIgnoreCase("and")||queries[i].equalsIgnoreCase("or"))
				{
					String temp;
					if((i!=0)&&(i!=(queries.length-1)))
						{
							return_Q.remove(queries[i-1]);
							if (queries[i].equalsIgnoreCase("and"))
								temp = queries[i-1] + " AND " +queries[i+1];
							else  
								temp = queries[i-1] + " OR " +queries[i+1];
							i++;
							return_Q.add(temp);
						}
				}
				else
					return_Q.add(queries[i]);
			}

		}
		return return_Q;
	}

	//removing duplicates from results
	public static <T> List<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);

		return new ArrayList<T>(set);
	}

	//classifying results into positive and negative
	public static void judgementGrouping(ArrayList<String> arraylist) {

		for (String string : arraylist) {

			DocumentV doc_temp = doc_hashmap.get(string);
			if (doc_temp.judgement)
				positive.add(string);
			else
				negative.add(string);
		}

	}
	
	//generating various combinations
	 public static ArrayList<String> combinationOneZero(int n) {
	        int rows = (int) Math.pow(2,n);
			ArrayList<String> myString1 = new ArrayList<String>();
	      String temp = "";
	        for (int i=0; i<rows; i++) {
	            for (int j=n-1; j>=0; j--) {
	              temp = temp + (i/(int) Math.pow(2, j))%2 ;
	              
	            }
	            myString1.add(temp);
	          temp = "";
	          
	        }
	      return myString1;
	    }
	
	 //Adding OR AND to queries
	 public static ArrayList<String> finalQueriesANDOR(ArrayList<String> myString,ArrayList<String> myString2) {
		 
		 ArrayList<String> myString_final = new ArrayList<String>();		 
		 
		 
			for (int i =0; i<myString2.size(); i++ )
	        {
	          String onerow = myString2.get(i);
	          String result = "";
	          int j;
	          for(j = 0 ; j< onerow.length();j++)
	          {
	            if(onerow.charAt(j) == '0')
	            {
	              if(result.equalsIgnoreCase(""))
	              {
	                result = myString.get(j)+" OR ";
	              }
	              else
	              {
	                result = result + myString.get(j)+" OR ";
	              }
	            }
	            else
	            {
	              
	              if(result.equalsIgnoreCase(""))
	              {
	                result = myString.get(j)+" AND ";
	              }
	              else
	              {
	                result = result + myString.get(j)+" AND ";
	              }
	            }
	          }
	          result = result + myString.get(j);
	          myString_final.add(result);
	          
	        }
		 return myString_final;
	}
	 
	 
	 public static ArrayList<String> reverse(ArrayList<String> list) {
		    for(int i = 0, j = list.size() - 1; i < j; i++) {
		        list.add(i, list.remove(j));
		    }
		    return list;
		}
	
	//action has to be taken when button is clicked
	public static void buttonClicked(String query) {

		ArrayList<String> collective_result = new ArrayList<String>();
		ArrayList<String> queries = checkAndRemoveStopwords(query.trim().toLowerCase().split(" "));
		ArrayList<String> combination_list = new ArrayList<String>();
		ArrayList<String> one_zero_combination = new ArrayList<String>();
		ArrayList<String> final_queries = new ArrayList<String>();
		
		//getting combination - one and zeros
		one_zero_combination = combinationOneZero(queries.size()-1);
		
		//adding AND, OR to query terms
		combination_list = finalQueriesANDOR(queries, one_zero_combination);
		
		//reverse the arraylist
		final_queries = reverse(combination_list);
	
		
		SimpleSearcher s = new SimpleSearcher();
		try {
			
			//searching for given string
			s.main_searcher(final_queries);
			for (ArrayList<String> list : results) {
				if (list != null)
					collective_result = (ArrayList<String>) union(
							collective_result, list);
			}
			
			//clearing result

			for (int i =0; i <10;i++) {
				if(results[i]!= null)
				results[i].clear();
			}
			
			
			//Searching in relevance tag
			for (int i = 1; i < xis.length-1; i++) {
				if(i!=6)
				{
				for (String string : queries) {
					if(xis[i].contains(string))
					{
						collective_result = (ArrayList<String>) union(
								collective_result, Xs[i]);
					}
				}
				}
			}
			
			
			//searching its synonyms
			for (String string : queries) {
				String[] args = new String[] {string};
				ArrayList<String> syno = Synonyms.main_synonyms(args);
	//			System.out.println(syno);
				if(!(syno.isEmpty()))
				{
				s.main_searcher(syno);
				for (ArrayList<String> list : results) {
					if (list != null)
						collective_result = (ArrayList<String>) union(
								collective_result, list);
				}
				}
			}

			judgementGrouping(collective_result);

	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void clearAll() {
		
		for (int i =0; i <10;i++) {
			if(results[i]!= null)
			results[i].clear();
		}
		
		positive.clear();
		negative.clear();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		initialize();
		
		//path - where collected documents are stored
		String path = "C:\\Users\\saranath\\Desktop\\FinalFiles1";
		File folder = new File(path);
		File[] files = folder.listFiles();

		MainClass main_temp = new MainClass();
		getJudgement();
		for (File file : files) {

			main_temp.parseFile(file);

		}


		SimpleFileIndexer indexer_class = new SimpleFileIndexer();
		try {
			indexer_class.main_simpleindexer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		GUI.main_gui();
		
		
		

	}
	
	
	
	public static void getJudgement() {
		
		//path - judgement file location
		File file = new File("C:\\Users\\saranath\\Documents\\WorkSpace_Eclipse\\LuceneIntroProject\\judgment.xml");
	
		try
		{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		doc_judge = docBuilder.parse(file);

		// normalize the document
		doc_judge.getDocumentElement().normalize();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

	public void addToXsArraylist(String filename, NodeList xi, int i) {
		
		if (!((xi.item(0).getTextContent().equalsIgnoreCase("")) || (xi.item(0)
				.getTextContent().equalsIgnoreCase(" ")))) {
			Xs[i].add(filename);
		}

	}

	//parsing the stored files
	public String parseFile(File file) {

		String tempstring = null;

		try {
			// loading and parsing the document
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);

			// normalize the document
			doc.getDocumentElement().normalize();

			// retrieving text, author, title
			NodeList Paragraph = doc.getElementsByTagName("paragraph");
			NodeList url = doc.getElementsByTagName("url");
			NodeList Docno = doc.getElementsByTagName("docId");
			NodeList x1 = doc.getElementsByTagName("x1");
			NodeList x2 = doc.getElementsByTagName("x2");
			NodeList x3 = doc.getElementsByTagName("x3");
			NodeList x4 = doc.getElementsByTagName("x4");
			NodeList x5 = doc.getElementsByTagName("x5");
			NodeList x6 = doc.getElementsByTagName("x6");
			NodeList x7 = doc.getElementsByTagName("x7");
			NodeList x8 = doc.getElementsByTagName("x8");
			// NodeList x9 = doc.getElementsByTagName("x9");

			int doc_tempn = Integer.parseInt(Docno.item(0).getTextContent().trim());
			Node first_para = Paragraph.item(0);
			Node first_url = url.item(0);
			tempstring = first_para.getTextContent();

			DocumentV docvar = new DocumentV();
			docvar.setDocno(doc_tempn);
			NodeList judgement_node = doc_judge.getElementsByTagName("XML"+doc_tempn);					
			
			if (judgement_node.item(0).getTextContent().trim().equalsIgnoreCase("positive")) {
				docvar.setJudgement(true);
			} else {
				docvar.setJudgement(false);
			}
			docvar.setParagraph(tempstring);
			docvar.setUrl(first_url.getTextContent());

			addToXsArraylist(file.getName(), x1, 0);
			addToXsArraylist(file.getName(), x2, 1);
			addToXsArraylist(file.getName(), x3, 2);
			addToXsArraylist(file.getName(), x4, 3);
			addToXsArraylist(file.getName(), x5, 4);
			addToXsArraylist(file.getName(), x6, 5);
			addToXsArraylist(file.getName(), x7, 6);
			addToXsArraylist(file.getName(), x8, 7);
			// addToXsArraylist(file.getName(), x9, 8);

			doc_hashmap.put(file.getName(), docvar);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tempstring;
	}

}
