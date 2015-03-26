package create;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.TimeUnit;

/**
 * Created by pengliang on 15-3-9.
 *
 * 定时发出一个Long类型的信号，0,1,2,3....，可以设置时间和单位
 */
public class Interval {
    static Subscription subscription = Subscriptions.empty();

    static Observable<Long> observable = Observable.interval(1000, TimeUnit.MILLISECONDS);

    public static void main(String[] args) throws InterruptedException {
        subscription = observable.subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError!");
            }

            @Override
            public void onNext(Long s) {
                System.out.println(s+"onNext!");
            }
        });
        Thread.sleep(5000);
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
}
