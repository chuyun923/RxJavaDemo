package filtering;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pengliang on 15-3-18.
 *
 * 给定一个时间间隔，如果这个时间间隔内没有下一个item释放，那么这个原始item会被放到结果Observable中
 *
 * 也可以通过一个流来产生，原始item走到call的时候相当于被缓存了一下，然后产生一个Observable,等到这个Observale发出一个item，如果此时原始流还没有产生一个
 * item，那么之前的item就可以放到结果流中了
 */
public class Debounce {
    static Subscription subscription = Subscriptions.empty();

    static Observable<Long> observable = Observable.interval(1000, TimeUnit.MILLISECONDS);

    public static void main(String[] args) throws InterruptedException {

        Observable<Long> observable1 = observable.debounce(new Func1<Long, Observable<String>>() {
            @Override
            public Observable<String> call(Long aLong) {
                System.out.println("call"+aLong);
                return Observable.just("string");
            }
        });
        observable1.subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                System.out.println(aLong+"   ");
            }
        });


//        observable.debounce(1200,TimeUnit.MILLISECONDS).subscribe(new Subscriber<Long>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                System.out.println("onNext"+aLong);
//            }
//        });


        Thread.sleep(10000);
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
}
