package pl.edu.pwr.aerospace.app4hab.server;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class PostsenderLog {
    static final Logger LOG = Logger.getLogger(PostsenderSensor.class);
    public static void main(String[] args){

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {


            HttpPost request = new HttpPost("http://localhost:8080/app4hab/api/log");
            StringEntity params =new StringEntity("log-info");
            request.addHeader("log", "String");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            LOG.info(response.getStatusLine().getStatusCode());

            //handle response here...

        }catch (Exception ex) {

            //handle exception here

        } finally {
            //Deprecated
            //httpClient.getConnectionManager().shutdown();
        }

    }
}
