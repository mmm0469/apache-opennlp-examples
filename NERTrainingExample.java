import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
 
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;
 
/**
 * NER Training in OpenNLP with Name Finder Training Java Example
 * @author www.tutorialkart.com
 */
public class NERTrainingExample {
 
	public static void main(String[] args) {
 
		// reading training data
		InputStreamFactory in = null;
		try {
			in = new MarkableFileInputStreamFactory(new File("AnnotatedSentences.txt"));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
	    ObjectStream<NameSample> sampleStream = null;
		try {
			sampleStream = new NameSampleDataStream(
	            new PlainTextByLineStream(in, StandardCharsets.UTF_8));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
 
		// setting the parameters for training
	    TrainingParameters params = new TrainingParameters();
	    params.put(TrainingParameters.ITERATIONS_PARAM, 70);
	    params.put(TrainingParameters.CUTOFF_PARAM, 1);
 
	    // training the model using TokenNameFinderModel class 
	    TokenNameFinderModel nameFinderModel = null;
		try {
			nameFinderModel = NameFinderME.train("en", null, sampleStream,
			    params, TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// saving the model to "ner-custom-model.bin" file
		try {
			File output = new File("ner-custom-model.bin");
			FileOutputStream outputStream = new FileOutputStream(output);
			nameFinderModel.serialize(outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// testing the model and printing the types it found in the input sentence
	    TokenNameFinder nameFinder = new NameFinderME(nameFinderModel);
	    
	    String[] testSentence ={"Alisa","Fernandes","is","a","tourist","from","Spain"};
 
	    System.out.println("Finding types in the test sentence..");
	    Span[] names = nameFinder.find(testSentence);
	    for(Span name:names){
	    	String personName="";
	    	for(int i=name.getStart();i<name.getEnd();i++){
	    		personName+=testSentence[i]+" ";
	    	}
	    	System.out.println(name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
	    }
	}
 
}
