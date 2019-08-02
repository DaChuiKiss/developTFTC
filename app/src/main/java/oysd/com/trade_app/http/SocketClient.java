package oysd.com.trade_app.http;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.util.Logger;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;

/**
 * @author houmingkuan
 * @time 2019/7/17
 * @desc StompClient
 */
public class SocketClient {

    private static volatile SocketClient instance;

    private StompClient stompClient;
    private CompositeDisposable compositeDisposable;
    private LifecycleEvent.Type connectType = LifecycleEvent.Type.CLOSED;


    //获取实例
    public static SocketClient getInstance() {
        //实例对象不存在
        if (instance == null) {
            synchronized (SocketClient.class) {
                if (instance == null) {
                    Logger.d("instance==null");
                    instance = new SocketClient();
                    instance.connect();//连接
                }
            }
        } else {
            Logger.d("instance!=null");
            //实例对象存在
//            instance.resetSubscriptions();
//            instance.stompClient.withClientHeartbeat(10000).withServerHeartbeat(10000);
//            instance.resetSubscriptions();
        }
        return instance;
    }

    private SocketClient() {
        init();
    }

    //初始化stompClient
    private void init() {
        stompClient = Stomp.over(Stomp.ConnectionProvider.JWS, Constants.BASE_WEB_SOCKET_URL);
        resetSubscriptions();
        stompClient.withClientHeartbeat(10000).withServerHeartbeat(10000);
        resetSubscriptions();
    }

    //依我理解-重新订阅
    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }


    //初始化检测
    private void checkInit() {
        if (stompClient == null) {
            init();
        }
    }


    /**
     * Socket 连接对应的 端点.
     */
    public void connect() {
        Disposable dispLifecycle = stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    connectType = lifecycleEvent.getType();
                    switch (connectType) {
                        case OPENED:
                            Logger.d("Stomp connection opened");
                            break;
                        case ERROR:
                            Logger.d("Stomp connection error" + lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            Logger.d("Stomp connection closed");
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Logger.d("Stomp failed server heartbeat");
                            break;
                    }
                });
        compositeDisposable.add(dispLifecycle);
        stompClient.connect();
    }

    /**
     * 订阅 市场最新价格 数据。
     *
     * @param marketId    Market Id
     * @param legalTender 法币code
     * @param listener    OnSocketReceivedListener
     */
    public Disposable topicMarketNewestPrice(int marketId, String legalTender,
                                             @NonNull OnSocketReceivedListener listener) {
        return stompClient.topic("/topic/newest." + marketId + "." + legalTender)
                .compose(RxSchedulers.flowableIoMain())
                .subscribe(
                        stompMessage -> listener.onReceive(stompMessage.getPayload()),
                        listener::onError);
    }

    /**
     * 订阅 K线 数据。
     *
     * @param marketId Market Id
     * @param range    时间线对应的毫秒数
     * @param listener OnSocketReceivedListener
     */
    public Disposable topicKLineInfo(int marketId, long range, @NonNull OnSocketReceivedListener listener) {
        return stompClient.topic("/topic/kLine." + marketId + "." + range)
                .compose(RxSchedulers.flowableIoMain())
                .subscribe(stompMessage -> listener.onReceive(stompMessage.getPayload()),
                        listener::onError);


    }


    /**
     * 订阅 市场深度 数据.
     *
     * @param marketId Market Id
     * @param listener OnSocketReceivedListener
     */
    public Disposable topicMarketDepth(int marketId, @NonNull OnSocketReceivedListener listener) {
        return stompClient.topic("/topic/entrust." + marketId)
                .compose(RxSchedulers.flowableIoMain())
                .subscribe(
                        stompMessage -> listener.onReceive(stompMessage.getPayload()),
                        listener::onError);
    }

    /**
     * 订阅 成交 数据。
     *
     * @param marketId Market Id
     * @param listener OnSocketReceivedListener
     */
    public Disposable topicReachedInfo(int marketId, @NonNull OnSocketReceivedListener listener) {
        return stompClient.topic("/topic/reached." + marketId)
                .compose(RxSchedulers.flowableIoMain())
                .subscribe(stompMessage -> listener.onReceive(stompMessage.getPayload()),
                        listener::onError);
    }


    /**
     * 断开 socket 连接。
     */
    public void disConnect() {
        //stompClient.disconnect();
        //用上面这个断开socket会导致ANR
        if (stompClient.isConnected()) {
            stompClient.disconnectCompletable();
        }
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public void setInstance() {
        instance = null;
    }

    /**
     * Socket 是否处于连接状态。
     */
    public boolean isConnect() {
        return stompClient.isConnected();
    }

    /**
     * 回调函数，用于向调用方返回响应。
     */
    public interface OnSocketReceivedListener {
        void onReceive(String response);

        void onError(Throwable throwable);
    }


}
