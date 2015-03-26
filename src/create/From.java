package create;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by pengliang on 15-3-9.
 *
 * 可以将一个迭代器类型、或者多个对象作为observable的item释放出来
 * 如果是迭代器，释放的是其中的每一项
 */
public class From {
    static Subscription subscription = Subscriptions.empty();
    static String[] souce = {"hello","world"};
    static Observable<String> observable = Observable.from(souce);

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
                System.out.println(s+"onNext!");
            }
        });
    }
}
