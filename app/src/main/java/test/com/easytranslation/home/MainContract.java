package test.com.easytranslation.home;

/**
 * Created by Zipu on 11/23/2017.
 */

public interface MainContract {
  interface HomeView{
        void onCompleteTransLation(String text,String meaningText);
        void onFailure(String msg);
    }
    interface Presenter{
        void onSuccessResult(int code,String text,String resultText);
        void onFailure(String erroMsg);
    }
}
