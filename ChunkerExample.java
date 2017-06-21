import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
 
import java.io.*;
 
/**
 * Chunker Example in Apache OpenNLP
 */
public class ChunkerExample {
 
    public static void main(String[] args){
        try{
            // test sentence
            String[] tokens = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
                    "morning", "and", "afternoon", "newspapers", "."};
 
            // Parts-Of-Speech Tagging
            // reading parts-of-speech model to a stream
            InputStream posModelIn = new FileInputStream("models"+File.separator+"en-pos-maxent.bin");
            // loading the parts-of-speech model from stream
            POSModel posModel = new POSModel(posModelIn);
            // initializing the parts-of-speech tagger with model
            POSTaggerME posTagger = new POSTaggerME(posModel);
            // Tagger tagging the tokens
            String tags[] = posTagger.tag(tokens);
 
            // reading the chunker model
            InputStream ins = new FileInputStream("models"+File.separator+"en-chunker.bin");
            // loading the chunker model
            ChunkerModel chunkerModel = new ChunkerModel(ins);
            // initializing chunker(maximum entropy) with chunker model
            ChunkerME chunker = new ChunkerME(chunkerModel);
            // chunking the given sentence : chunking requires sentence to be tokenized and pos tagged
            String[] chunks = chunker.chunk(tokens,tags);
 
            // printing the results
            System.out.println("\nChunker Example in Apache OpenNLP\nPrinting chunks for the given sentence...");
            System.out.println("\nTOKEN - POS_TAG - CHUNK_ID\n-------------------------");
            for(int i=0;i< chunks.length;i++){
                System.out.println(tokens[i]+" - "+tags[i]+" - "+chunks[i]);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
