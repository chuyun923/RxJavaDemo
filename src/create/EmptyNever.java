package create;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by pengliang on 15-3-9.
 *
 * empty:只触发onCompleted
 * never：不会触发任何操作
 */
public class EmptyNever {
    static Observable<String> empty = Observable.empty();
    static Observable<String> never = Observable.never();


    public static void main(String[] args) {
        empty.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError!");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext!");
            }
        });

        never.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError!");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext!");
            }
        });
    }
}
