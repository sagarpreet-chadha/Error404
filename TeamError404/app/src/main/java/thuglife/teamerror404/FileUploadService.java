package thuglife.teamerror404;


import android.widget.ArrayAdapter;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 31-10-2016.
 */

public interface FileUploadService {

  @POST("/useradd")
  Call<ResponseBody> postItem(@Body ArrayList<DashboardItem> list);

  @GET("/beacon")
  Call<ItemResponse> getShops(@Query("instance_id") String instanceID);

}
