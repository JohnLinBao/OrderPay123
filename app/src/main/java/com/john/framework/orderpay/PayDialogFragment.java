package com.john.framework.orderpay;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PayDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.pay_cancel)
    ImageView payCancel;
    @BindView(R.id.pay_wechat_check)
    CheckBox payWechatCheck;
    @BindView(R.id.pay_wechat_ll)
    LinearLayout payWechatLl;
    @BindView(R.id.pay_zhifubao_check)
    CheckBox payZhifubaoCheck;
    @BindView(R.id.pay_zhifubao_ll)
    LinearLayout payZhifubaoLl;
    Unbinder unbinder;

    private static final int SDK_PAY_FLAG = 1001;
    private String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCtRbO4yimd7D4GhUM+uN0BFuv8I1QjvUwRzfXKluTTGEwFuBvO4IwKdwayUOr3yTXEQ02tN8/FEHHbR5JGTVX9MSPsII6vZSaMBFaz6S5ZhElEWum45marD51UB0cYBzY9IOqOS74wdEaEG2EF1xT9gc8KYj6muhgqEFuWdxoWcS6nQwsr94BP7RPKobJpGcDGPb6cpuDPkhfrofj7OipXQfFKtYAdv8kPjZIbRvJbgIAmLGu8wj39ttWsn+E/mH58ecci5puJma99JkvD5Erkx+n9fW0vL6XAXR50HrEJuAAyJtmGcceGb2cfMeT5RugxnhfPsG1ir5aeVCc9DVKZAgMBAAECggEBAIoTrmlGaK2ZMcA+d9zGsg8JNhqiCI3GbpUNnJ3JayWxCfvQwBA2rkI0oZh9lRDKPdddjsvHWwF93k1QlocBX9G5VBf5xdqNb49KcdFCs5XQLhaToAahTBVrBwOMHSJPtPA3h9LHFxNDxWHG86UUxXxS8isjKSvxxgnXKTUZSiewzI/E8r88yIQ9oqNDLuxdthvtXyEnS/jNk2/3Zdzgkx9ECtVJ3p74pWnJJpBmGU8lt2hK0J/MJv+3og0kYhe+GJmQP2f721WQQvvmlRwvMYjgY3GW2M0DoxSZVoNY3xcGVoxXMdCKtlqOb2LSXnKhX4/TWeEBFHbrXCX6pNIarwECgYEA9EYbexBwpjeNoIDHg8ha48DUP7Y7XAM90ZgQgqu2JX+j5C7AgfOPxdhq3sZmlK1jEnnoKspOH0Cnx+UIcubz8CIFCaaQXmBuoo8T2+A71Ejgy18kOJAfHgWxhD5sDPTuKaOLgfZX+fURfLDSqVPS10ndOcMXkJhbwAj9POuYa3kCgYEAtZcNj5ZG1vP3SAeuzzwhsodULV1gXInkOgZmICYuVtTn5biY/XlzDbLHCKzd2QoQZYLiS5JCrQpUKBWMduO8vDyKaVbP0X/BuK5H/+1IqaCVn97/bQriRpUL46Lo1rfOxsFIl8YT24gtSNwEpms0k3wycfESxkVrTFgZ/G/NOCECgYBP6KtOu9cOGPhEvDwIWT+aZ9dHLFcIx6quaP9lr8R7SeEUYVLqcAHlZ+G2FNMz6fCR971CWkHqKV15n+v+opzEPmzFdm6d4Q/wL7wAXr9Tt6+J+ArCUitLx/MFXuI77tgGWRDnJ+4pv8jDsJiJeflsIiazoaWQ0LabAJXPGwcWiQKBgQCe4AEKXqWuDZeNmsM0J10oMMTSR/82F6LprY2OlTk32WDnGSIpqyehlYgULAKRkP+VgPTyv428jevhgjD6F/iaDLHgxgZfOlLIfRsbbISFfK1MSbPauIoOzPqw+GiU7A5q0NLtj2zyRuuv8mfTHrrKSwBrtksB09yz1+UserjnYQKBgAlvkTzvmrWwbrN8bv78ZeigpU04V+Ceotsz274Tj1cIj0xVC+2uvzfuGzgZYvUXrHl3etdqdoKqUuH0XfSfJN2MOfKrZWyJZDfyIkD077H382VIStaQ9bjySojja2/BbmecaTEEbvmWeWbex3Y3bB1H8R32kK2KiqEmKr14yDk0";
    public static final String APPID = "2018041402560337";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    Log.i("Pay", "Pay:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PayDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayDialogFragment newInstance(String param1, String param2) {
        PayDialogFragment fragment = new PayDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.payDialog);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getDialog().getWindow().setLayout(metrics.widthPixels, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        payZhifubaoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //构造支付订单参数列表
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, true);
                //构造支付订单参数信息
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                //对支付参数信息进行签名
                String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, true);
                //订单信息
                final String orderInfo = orderParam + "&" + sign;
                //异步处理
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        //新建任务
                        PayTask alipay = new PayTask(getActivity());
                        //获取支付结果
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
