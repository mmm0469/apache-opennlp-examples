import opennlp.tools.langdetect.*;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
 
import java.io.*;
 
/**
 * Dictionary Lemmatizer Example in Apache OpenNLP
 */
public class DictionaryLemmatizerExample {
 
    public static void main(String[] args){
        try{
            // test sentence
            String[] tokens = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
                    "morning", "and", "afternoon", "newspapers", "."};
 
            // Parts-Of-Speech Tagging
            // reading parts-of-speech model to a stream
            // place the model file "en-pos-maxent.bin" in the folder "models", located in the project root directory
            InputStream posModelIn = new FileInputStream("models"+File.separator+"en-pos-maxent.bin");
            // loading the parts-of-speech model from stream
            POSModel posModel = new POSModel(posModelIn);
            // initializing the parts-of-speech tagger with model
            POSTaggerME posTagger = new POSTaggerME(posModel);
            // Tagger tagging the tokens
            String tags[] = posTagger.tag(tokens);
 
            // loading the dictionary to input stream
            // find en-lemmatizer.txt at https://raw.githubusercontent.com/richardwilly98/elasticsearch-opennlp-auto-tagging/master/src/main/resources/models/en-lemmatizer.dict
            InputStream dictLemmatizer = new FileInputStream("dictionary"+File.separator+"en-lemmatizer.txt");
            // loading the lemmatizer with dictionary
            DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
 
            // finding the lemmas
            String[] lemmas = lemmatizer.lemmatize(tokens, tags);
 
            // printing the results
            System.out.println("\nPrinting lemmas for the given sentence...");
            System.out.println("WORD -POSTAG : LEMMA");
            for(int i=0;i< tokens.length;i++){
                System.out.println(tokens[i]+" -"+tags[i]+" : "+lemmas[i]);
            }
 
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
