package test.com.easytranslation.home.presenter;

import test.com.easytranslation.home.MainContract;
import test.com.easytranslation.home.model.TranslationInetractor;

/**
 * Created by Zipu on 11/23/2017.
 */

public class MainPresenter implements MainContract.Presenter{
    private TranslationInetractor inetractor;
    private MainContract.HomeView homeView;

    public MainPresenter(MainContract.HomeView homeView) {
        this.homeView = homeView;
        inetractor=new TranslationInetractor(this);
    }

    public void translateWord(String text){
        if(inetractor!=null){
            inetractor.loadNetworkReq(text);
        }
    }
    @Override
    public void onSuccessResult(int code, String text, String resultText) {
        homeView.onCompleteTransLation(text,resultText);
         }

    @Override
    public void onFailure(String erroMsg) {
         homeView.onFailure(erroMsg);
    }
}
