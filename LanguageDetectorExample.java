import java.io.*;
 
import opennlp.tools.langdetect.*;
import opennlp.tools.util.*;
 
/**
* Language Detector Example in Apache OpenNLP
*/
public class LanguageDetectorExample {
 
    private static LanguageDetectorModel model;
 
    public static void main(String[] args){
 
        // loading the training data to LanguageDetectorSampleStream
        LanguageDetectorSampleStream sampleStream = null;
        try {
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("training-data" + File.separator + "DoccatSample.txt"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            sampleStream = new LanguageDetectorSampleStream(lineStream);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        // training parameters
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
        params.put(TrainingParameters.CUTOFF_PARAM, 5);
        params.put("DataIndexer", "TwoPass");
        params.put(TrainingParameters.ALGORITHM_PARAM, "NAIVEBAYES");
 
        // train the model
        try {
            model = LanguageDetectorME.train(sampleStream, params, new LanguageDetectorFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Completed");
 
        // load the model
        LanguageDetector ld = new LanguageDetectorME(model);
        // use model for predicting the language
        Language[] languages = ld.predictLanguages("estava em uma marcenaria na Rua Bruno");
        System.out.println("Predicted languages..");
        for(Language language:languages){
            // printing the language and the confidence score for the test data to belong to the language
            System.out.println(language.getLang()+"  confidence:"+language.getConfidence());
        }
    }
}
