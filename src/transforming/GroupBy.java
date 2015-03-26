package transforming;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.subscriptions.Subscriptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pengliang on 15-3-17.
 *
 * groupBy函数感觉实现还不是太好，在Call函数中返回的key要么就是原始Observable返回的item，此时相当于对值相同的item合成一个
 * GroupedObservable；
 * 如果直接返回一个key，那么会筛选出这个key对应的item，组成一个Observable
 *
 */
public class GroupBy {
    static Subscription subscription = Subscriptions.empty();

    static Observable<Long> observable = Observable.interval(1000, TimeUnit.MILLISECONDS);
    static Observable<Long> oddEven = observable.map(new Func1<Long, Long>() {
        @Override
        public Long call(Long aLong) {
            return aLong%2==0 ? 1l:2l;
        }
    }) ;
    public static void main(String[] args) throws InterruptedException {
        Observable<GroupedObservable<Long,Long>> groupedObservableObservable = oddEven.groupBy(new Func1<Long, Long>() {
            @Override
            public Long call(Long aLong) {
                return aLong;
            }
        });

        subscription = groupedObservableObservable.subscribe(new Subscriber<GroupedObservable<Long,Long>>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError!");
            }

            @Override
            public void onNext(final GroupedObservable<Long,Long> observable1) {
               observable1.subscribe(new Subscriber<Long>() {
                   @Override
                   public void onCompleted() {
                       System.out.println("onCompleted!");
                   }

                   @Override
                   public void onError(Throwable e) {
                       System.out.println("onError!");
                   }

                   @Override
                   public void onNext(Long aLong) {
                       if(observable1.getKey()==1l) {
                           System.out.println( "even "+ "  "+ aLong);
                       }else {
                           System.out.println( "odd"+ "  "+ aLong);
                       }
                   }
               });
            }
        });
        Thread.sleep(5000);
        System.out.println(subscription.isUnsubscribed());
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }
}
