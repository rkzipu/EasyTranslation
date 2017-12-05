package test.com.easytranslation.home.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import test.com.easytranslation.home.MainContract;
import test.com.easytranslation.home.model.TranslationInetractor;
import test.com.easytranslation.networking.NetworkError;
import test.com.easytranslation.networking.Response;
import test.com.easytranslation.networking.Service;

/**
 * Created by Zipu on 11/23/2017.
 */

public class MainPresenter implements MainContract.Presenter{
    private TranslationInetractor inetractor;
    private MainContract.HomeView homeView;

    private final Service service;
    private CompositeSubscription subscriptions;

    public MainPresenter(Service service, MainContract.HomeView view) {
        this.service = service;
        this.homeView = view;
        this.subscriptions = new CompositeSubscription();
    }

    public MainPresenter(MainContract.HomeView homeView) {
        this.homeView = homeView;
        inetractor=new TranslationInetractor(this);
        service = null;
    }

  /*  public void translateWord(String text){
        if(inetractor!=null){
            inetractor.loadNetworkReq(text);
        }
    }*/
    @Override
    public void onSuccessResult(int code, String text, String resultText) {
        homeView.onCompleteTransLation(text,resultText);
         }

    @Override
    public void onFailure(String erroMsg) {
         homeView.onFailure(erroMsg);
    }

    public void translateWord(final String word){

    Subscription subscription = service.getTranslation(new Service.GetCityListCallback() {
        @Override
        public void onSuccess(Response cityListResponse) {
            homeView.onCompleteTransLation(word,cityListResponse.getText().get(0));
          //  view.removeWait();
           // view.getCityListSuccess(cityListResponse);
        }

        @Override
        public void onError(NetworkError networkError) {
            homeView.onFailure(networkError.getAppErrorMessage());
          //  view.removeWait();
           // view.onFailure(networkError.getAppErrorMessage());
        }

    },word);

        subscriptions.add(subscription);
}
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
