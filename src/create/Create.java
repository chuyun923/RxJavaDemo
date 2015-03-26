package create;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by pengliang on 15-3-9.
 *
 * Observable.create里面主要是在call函数里面正确处理订阅者Subscriber的三个回调函数
 */
public class Create {
    static Subscription subscription = Subscriptions.empty();

    static Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("hello");
            subscriber.onCompleted();
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

