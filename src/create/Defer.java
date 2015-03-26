package create;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func0;
import rx.subscriptions.Subscriptions;

/**
 * Created by pengliang on 15-3-9.
 *
 * 在订阅者订阅时才去创建observable
 */
public class Defer {
    static Subscription subscription = Subscriptions.empty();

    static Observable<String> souceObservable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("hello");
            subscriber.onCompleted();
        }
    });

    static Observable<String> observable = Observable.defer(new Func0<Observable<? extends String>>() {
        @Override
        public Observable<? extends String> call() {
            return souceObservable;
        }
    });

    public static void main(String[] args) {
        subscription = observable.subscribe(new Subscriber<String>() {
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
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
}
