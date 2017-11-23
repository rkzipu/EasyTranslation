package test.com.easytranslation.home.model;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.com.easytranslation.Constant;
import test.com.easytranslation.api.ApiClient;
import test.com.easytranslation.api.endpoint.ITranslateEnpoint;
import test.com.easytranslation.home.MainContract;


/**
 * Created by Zipu on 11/23/2017.
 */

public class TranslationInetractor implements Callback<TranslationResponse> {

    private MainContract.Presenter mListener;
    private ITranslateEnpoint service;
    private String text;

    public TranslationInetractor(MainContract.Presenter mListener) {
        this.mListener = mListener;
        service=ApiClient.getClient().create(ITranslateEnpoint.class);
    }

    public void loadNetworkReq(String text){
        this.text=text;
        service.getTranslation(Constant.KEY,"en-bn",text,"plain").enqueue(this);
    }
    @Override
    public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
        if(mListener!=null&&response.code()==200){
            mListener.onSuccessResult(response.body().getCode(),text,response.body().getText().get(0));
        }else {
            mListener.onFailure("notfound");
        }
    }

    @Override
    public void onFailure(Call<TranslationResponse> call, Throwable t) {
        mListener.onFailure("notfound");
    }
}
