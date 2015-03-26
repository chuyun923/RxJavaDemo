package transforming;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func0;
import rx.subscriptions.Subscriptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pengliang on 15-3-16.
 *
 * 缓存原始Observable中的item，满足一定缓存规则之后再吐出来
 *
 * buffer能接受一个缓存item个数
 *
 * 也可以通过一个Observable来触发buffer的行为
 *
 * everything is stream ！不是白说的
 *
 */
public class Buffer {
    static Subscription subscription = Subscriptions.empty();

    static Observable<Long> observable = Observable.interval(1000, TimeUnit.MILLISECONDS);

    public static void main(String[] args) throws InterruptedException {
        Observable<List<Long>> bufferedObservable = observable.buffer(2);
        Observable<List<Long>> bufferedObservable1 = observable.buffer(new Func0<Observable<?>>() {
            @Override
            public Observable<?> call() {
                return Observable.interval(3000, TimeUnit.MILLISECONDS);
            }
        });
        subscription = bufferedObservable1.subscribe(new Subscriber<List<Long>>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError!");
            }

            @Override
            public void onNext(List<Long> s) {
                System.out.println(s+"onNext!");
            }
        });
        Thread.sleep(5000);
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
}
