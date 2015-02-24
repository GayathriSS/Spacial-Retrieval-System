import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import edu.smu.tspell.wordnet.*;


public class Synonyms
{

	
	public static ArrayList<String> main_synonyms(String[] args)
	{
		ArrayList<String> a1 = new ArrayList<String>();
		
		//synonyms database 
		System.setProperty("wordnet.database.dir", "C:\\Users\\saranath\\Documents\\WorkSpace_Eclipse\\LuceneIntroProject\\dict");
		
		if (args.length > 0)
		{
			//  Concatenate the command-line arguments
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < args.length; i++)
			{
				buffer.append((i > 0 ? " " : "") + args[i]);
			}
			String wordForm = buffer.toString();
			//  Get the synsets containing the wrod form
			WordNetDatabase database = WordNetDatabase.getFileInstance();
			
			Synset[] synsets = database.getSynsets(wordForm);
			//  Display the word forms and definitions for synsets retrieved
			
			if (synsets.length > 0)
			{
				for (int i = 0; i < synsets.length; i++)
				{
			
					String[] wordForms = synsets[i].getWordForms();
					for (int j = 0; j < wordForms.length; j++)
					{
						a1.add(wordForms[j]);
					}
				}
			}
		}
		
		
		HashSet hs = new HashSet();
		hs.addAll(a1);
		a1.clear();
		a1.addAll(hs);
		a1.remove(args[0]);
		
		
		return a1;
		
	}
	
	
	

}