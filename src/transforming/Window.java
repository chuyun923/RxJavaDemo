package transforming;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func0;
import rx.subscriptions.Subscriptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pengliang on 15-3-18.
 *
 * 和buffer类似，不过这个玩意返回的是多个Observable，而不是一个item集合
 *
 * 和buffer一样，可以通过Observable来进行划分
 */
public class Window {
    static Subscription subscription = Subscriptions.empty();

    static Observable<Long> observable = Observable.interval(1000, TimeUnit.MILLISECONDS);

    public static void main(String[] args) throws InterruptedException {
        Observable<Observable<Long>> window = observable.window(new Func0<Observable<?>>() {
            @Override
            public Observable<?> call() {
                return Observable.interval(5000, TimeUnit.MILLISECONDS);
            }
        });
        window.subscribe(new Subscriber<Observable<Long>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Observable<Long> longObservable) {
                longObservable.subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted!");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println(aLong);
                    }
                });
            }
        });

        Thread.sleep(12000);
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
}
