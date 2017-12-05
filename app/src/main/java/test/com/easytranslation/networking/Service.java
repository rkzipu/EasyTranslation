package test.com.easytranslation.networking;



import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ennur on 6/25/16.
 */
public class Service {
    private final NetworkService networkService;
    private  String key;
    private  String lang;
    private  String format;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Service(NetworkService networkService, String key, String lang, String format) {
        this.networkService = networkService;
        this.key = key;
        this.lang = lang;
        this.format = format;
    }

    public Subscription getTranslation(final GetCityListCallback callback, String text) {

        return networkService.getTranslation(key,lang,text,format)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response>>() {
                    @Override
                    public Observable<? extends Response> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Response cityListResponse) {
                        callback.onSuccess(cityListResponse);

                    }
                });
    }

    public interface GetCityListCallback{
        void onSuccess(Response cityListResponse);

        void onError(NetworkError networkError);
    }
}
