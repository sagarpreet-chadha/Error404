package thuglife.teamerror404;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 10-07-2016.
 */
public class Client
{
    private static FileUploadService service;

    public static FileUploadService getService() {

        if (service == null) {


            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS)

                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request r = chain.request();
                            r = r.newBuilder().addHeader("X-Mashape-Key", "jrrbWTSGXjmsh8FuIW5eDI2hKwuHp1ewHltjsnuin7D0rb9nqN")
                                    .addHeader("Accept", "application/json")
                                    .build();
                            return chain.proceed(r);
                        }
                    })
                    .build();

            Retrofit r = new Retrofit.Builder().baseUrl("http://10.0.0.28:3000").
                    addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create())).client(client)
                    .build();
            service = r.create(FileUploadService.class);
        }
        return service;
    }
}
