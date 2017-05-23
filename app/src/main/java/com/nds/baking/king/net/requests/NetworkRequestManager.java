package com.nds.baking.king.net.requests;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nds.baking.king.converters.RecipeConverter;
import com.nds.baking.king.models.RecipeResponseModel;
import com.nds.baking.king.utils.ValidationUtil;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public class NetworkRequestManager {

    private static final String TAG = "Test"+NetworkRequestManager.class.getSimpleName();
    public static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static NetworkRequestManager mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    private NetworkRequestManager(Context context) {
        this.mContext = context;
    }

    public static synchronized NetworkRequestManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkRequestManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public void initializeNetworkManager() {
        mRequestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String requestTag) {
        request.setTag(ValidationUtil.isValidString(requestTag) ? requestTag : TAG);
        mRequestQueue.add(request);
    }

    public void cancelOngoingRequest(String requestTag) {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(requestTag);
    }

    public void terminateNetworkManager() {
        if (mRequestQueue != null)
            mRequestQueue.stop();
    }

    public void getRecipeList(final WeakReference<NetworkRequester> wNetworkRequester, String url, String requestTag) {
        JSONObject obj = new JSONObject();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                NetworkRequester requester = null;
                if (wNetworkRequester != null) {
                    requester = wNetworkRequester.get();
                }
                RecipeResponseModel responseModel = null;
                if (ValidationUtil.isValidString(response)) {
                    Log.d(TAG, "received valid response ");
                    responseModel = RecipeConverter.convert(response);
                }
                if (requester != null && responseModel != null) {
                    requester.onSuccess(responseModel);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                NetworkRequester requester = null;
                if (wNetworkRequester != null) {
                    requester = wNetworkRequester.get();
                }
                if (requester != null)
                    requester.onFailure(volleyError);
            }
        };

        Uri.Builder builder = Uri.parse(REQUEST_URL).buildUpon();

        url = builder.build().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        addToRequestQueue(stringRequest, requestTag);
    }
}
