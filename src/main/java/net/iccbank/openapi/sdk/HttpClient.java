package net.iccbank.openapi.sdk;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import net.iccbank.openapi.sdk.enums.ErrorCodeEnum;
import net.iccbank.openapi.sdk.exception.ICCBankException;

public class HttpClient {
	
	protected long connectTimeout = 30;
	protected long writeTimeout = 30;
	protected long readTimeout = 30;
	
	public String callPost(String url, Map<String, String> headers, String body) throws IOException {
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setConnectTimeout(connectTimeout, TimeUnit.MINUTES);
		okHttpClient.setWriteTimeout(writeTimeout, TimeUnit.MINUTES);
		okHttpClient.setReadTimeout(readTimeout, TimeUnit.MINUTES);
	    
		Builder hb = new Builder();
		if (headers != null) {
			headers.keySet().forEach(name -> {
				hb.add(name, headers.get(name));
			});
		}
		RequestBody requestBody = RequestBody.create(MediaType.parse(ApiConstants.CONTENT_TYPE), body);
		Request request = new Request.Builder()
		        .url(url)
		        .headers(hb.build())
		        .post(requestBody)
		        .build();
		
		final Call call = okHttpClient.newCall(request);
		Response response = call.execute();

		if(response.code() != 200){
			throw ICCBankException.buildException(ErrorCodeEnum.REMOTE_REQUEST_ERROR, "[Invoking] Response Status Error : "+response.code() + " message:"+ response.message());
		}

		return response.body().string();
	}

}
