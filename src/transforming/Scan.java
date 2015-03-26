package transforming;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

import java.util.concurrent.TimeUnit;

/**
 * Created by pengliang on 15-3-18.
 *
 * 对原始Observable中的每一个item都进行转化，得到的结果会返回给下一次转化
 */
public class Scan {
    public static void main(String args[]) throws InterruptedException {
        Observable<Long> observable = Observable.interval(1000, TimeUnit.MILLISECONDS);

        observable.scan(new Func2<Long, Long, Long>() {
            @Override
            public Long call(Long sum, Long item) {
                //叠加  参数sum表示上一次处理的结果 item表示这一次原始Obserable释放的item
                return sum + item;
            }
        }).subscribe(new Subscriber<Long>() {
            @Override
            public void onNext(Long item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
        Thread.sleep(5000);
    }

}
