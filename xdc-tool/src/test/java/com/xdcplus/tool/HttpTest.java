package com.xdcplus.tool;


import com.xdcplus.tool.utils.OkHttpUtils;
import okhttp3.Call;
import org.junit.jupiter.api.Test;

public class HttpTest {

    @Test
    public void sendRequestAsync() {

        String a = OkHttpUtils.builder().url("")
                .get()
                .async();
        System.out.println(a);
    }

    @Test
    public void sendRequestCallback() {

        OkHttpUtils.builder().url("").post(false).async(new OkHttpUtils.ICallBack() {
            @Override
            public void onSuccessful(Call call, String data) {
                System.out.println("请求成功后的处理:" + data);
                // 请求成功后的处理
            }

            @Override
            public void onFailure(Call call, String errorMsg) {
                System.out.println("请求失败后的处理:" + errorMsg);
                // 请求失败后的处理
            }
        });
    }

}
