package transforming;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.TimeUnit;

/**
 * Created by pengliang on 15-3-16.
 *
 * 可以将原始的Obserable进行拆分，最后合并成一个Observble
 * 第一步：原始中的每一个item都会被转化成一个Oservable，所以原始Obserable有多少个item，就会产生多少个Obserable
 * 第二步：合并。产生的Obserable释放的item都会被放到结果的Observble中
 *
 * 需要主要的是，结果中的item和原始item产生的Observable之间没有严格时序
 *
 * 如果需要串行，请使用concatMap
 *
 * switchMap，不仅进有顺序，而且如果新的Observale产生，会直接终止之前Observble的item释放
 *
 */
public class FlatMap {
    static Subscription subscription = Subscriptions.empty();

    static Observable<Long> source = Observable.interval(2000, TimeUnit.MILLISECONDS);
    static Observable<Long> source1 = Observable.interval(1000, TimeUnit.MILLISECONDS);

    // source从0开始，我需要从1开始
    static Observable<Long> begin_1 = source.map(new Func1<Long, Long>() {
        @Override
        public Long call(Long aLong) {
            return aLong+1;
        }
    });

    /**
     * 使用begin_1作为原始Observable，释放item：1、2、3、4、.....
     * flatMap之后，item1的Observable从101、102、103...
     *             item2的Observable从201、202、203...
     * 如果使用concatMap这个例子只会释放item1对应的Observable的item
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> flatMap = begin_1.switchMap(new Func1<Long, Observable<? extends Long>>() {
            @Override
            public Observable<? extends Long> call(Long aLong) {
                return createIntervalObservable(aLong);
            }
        });

        subscription = flatMap.subscribe(new Subscriber<Long>() {
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

        Thread.sleep(10000);
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }


    /**
     * 这个方法将数据流进行百位移动，主要是为了观察flatMap
     * @param flag
     * @return
     */
    static Observable createIntervalObservable(final long flag) {
        Observable<Long> result = source1.map(new Func1<Long, Long>() {
            @Override
            public Long call(Long aLong) {
                return aLong+flag*100;
            }
        });
        return result;
    }
}
