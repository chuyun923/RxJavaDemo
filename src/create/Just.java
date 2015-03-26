package create;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by pengliang on 15-3-9.
 *
 * just，只会输出一个信号，将一个普通的Object转化成Observale
 *
 */
public class Just {
    static Subscription subscription = Subscriptions.empty();

    static Observable<Exception> observable = Observable.just(new Exception("jdlfsdjfl"));

    public static void main(String[] args) {
        subscription = observable.subscribe(new Subscriber<Exception>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError!");
            }

            @Override
            public void onNext(Exception s) {
                System.out.println(s.getMessage()+"onNext!");
            }
        });
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
}
