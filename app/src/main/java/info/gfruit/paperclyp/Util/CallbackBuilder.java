package info.gfruit.paperclyp.Util;

import info.gfruit.paperclyp.API.Callback.StandardCallback;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by lite20 on 7/22/2017.
 */

public class CallbackBuilder {
    /**
     * Dynamically construct a StandardCallback to add to a callback queue
     * @param call
     * @param cb
     * @return
     */
    public static StandardCallback construct(final Call call, final Callback cb) {
        return new StandardCallback() {
            @Override
            public void onComplete(String error, Object data) {
                call.enqueue(cb);
            }
        };
    }
}
