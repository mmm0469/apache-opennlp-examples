import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
 
/**
 * oepnnlp version 1.7.2
 * Training of Document Categorizer using Maximum Entropy Model in OpenNLP
 * @author www.tutorialkart.com
 */
public class DocumentCategorizerMaxentExample {
 
	public static void main(String[] args) {
 
		try {
			// read the training data
			InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("train"+File.separator+"en-movie-category.train"));
			ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			ObjectStream sampleStream = new DocumentSampleStream(lineStream);
 
			// define the training parameters
			TrainingParameters params = new TrainingParameters();
			params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
			params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
 
			// create a model from traning data
			DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
			System.out.println("\nModel is successfully trained.");
 
			// save the model to local
			BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("model"+File.separator+"en-movie-classifier-maxent.bin"));
			model.serialize(modelOut);
			System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-maxent.bin");
 
			// test the model file by subjecting it to prediction
			DocumentCategorizer doccat = new DocumentCategorizerME(model);
			String[] docWords = "Afterwards Stuart and Charlie notice Kate in the photos Stuart took at Leopolds ball and realise that her destiny must be to go back and be with Leopold That night while Kate is accepting her promotion at a company banquet he and Charlie race to meet her and show her the pictures Kate initially rejects their overtures and goes on to give her acceptance speech but it is there that she sees Stuarts picture and realises that she truly wants to be with Leopold".replaceAll("[^A-Za-z]", " ").split(" ");
			double[] aProbs = doccat.categorize(docWords);
 
			// print the probabilities of the categories
			System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
			for(int i=0;i<doccat.getNumberOfCategories();i++){
				System.out.println(doccat.getCategory(i)+" : "+aProbs[i]);
			}
			System.out.println("---------------------------------");
 
			System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
		}
		catch (IOException e) {
			System.out.println("An exception in reading the training file. Please check.");
			e.printStackTrace();
		}
	}
}
