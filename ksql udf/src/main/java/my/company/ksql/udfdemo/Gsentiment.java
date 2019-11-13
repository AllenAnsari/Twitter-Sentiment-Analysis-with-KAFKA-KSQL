package com.github.ftisiot.kafka.streams.googlesentiment;
import java.util.Arrays;
import java.util.List;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.auth.Credentials;


@UdfDescription(name = "gsentiment", description = "sentiment scoring using Google NL API")

public class Gsentiment {

       

  @Udf(description = "return sentiment scoring")

  public List<Double> Gsentiment(String Text){

      

      Double[] arr = new Double[2];   

      try (LanguageServiceClient language = LanguageServiceClient.create()) {

    
           Document document = Document.newBuilder()

                    .setContent(Text)

                    .setType(Type.PLAIN_TEXT)

                    .build();
					
                             

           Sentiment sentiment = language.analyzeSentiment(document).getDocumentSentiment();

           

           arr[0]=(double)sentiment.getMagnitude();

           arr[1]=(double)sentiment.getScore();

           
         } 
          catch (Exception e) {
          

             arr[0]=(double) 0.0;

             arr[1]=(double) 0.0;

         }
        
             

      return Arrays.asList(arr);

      

  }

}
