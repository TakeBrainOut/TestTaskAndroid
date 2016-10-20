package testapp.vka.testmagapp.Model;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by kirill on 17.10.2016.
 */

public class ApiService {

    public static String URL = "http://ufa.farfor.ru/getyml/";
    private static ApiService instance = new ApiService();
    private Api api;

    private ApiService() {
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);

        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofitApi = new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .baseUrl(URL)
                .build();
        api = retrofitApi.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

    public static ApiService getInstance() {
        return instance;
    }

    public static void setInstance(ApiService instance) {
        ApiService.instance = instance;
    }

    public interface Api {
        @GET("?key=ukAXxeJYZN")
        Observable<ResponseModel> getResponceModel();
    }
}
