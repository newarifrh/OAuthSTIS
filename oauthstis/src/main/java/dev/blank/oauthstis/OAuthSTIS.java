package dev.blank.oauthstis;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OAuthSTIS extends Button {
    private String kode;
    private LoginListener listener;

    public OAuthSTIS(Context context) {
        super(context);
        this.listener = null;
        init(context, null);
    }

    public OAuthSTIS(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.listener = null;
        init(context, attrs);
    }

    public OAuthSTIS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.listener = null;
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OAuthSTIS(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.listener = null;
        init(context, attrs);
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    private void setKode(String kode) {
        this.kode = kode;
    }

    public String getKode() {
        return kode;
    }

    public interface LoginListener {
        public void onError(String error);

        public void onFinish(String token);
    }

    private void init(final Context context, AttributeSet attrs) {


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Text,
                0, 0);
        final String clientId = a.getString(R.styleable.Text_clientId);

        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Text,
                0, 0);
        final String redirectUri = b.getString(R.styleable.Text_redirectUri);

        TypedArray c = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Text,
                0, 0);
        final String clientSecret = b.getString(R.styleable.Text_clientSecret);


        final float scale = ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);


        setPadding((int) (8 * scale), (int) (8 * scale), (int) (10 * scale), (int) (8 * scale));
        setCompoundDrawablePadding((int) (12 * scale));
        setTextColor(Color.parseColor("#FFFFFF"));
        setTextSize((int) (4 * scale));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.bg_stroke));
        } else {
            setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_stroke));
        }

        setGravity(Gravity.LEFT);
        setGravity(Gravity.CENTER_VERTICAL);


        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sipadu, 0, 0, 0);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_medium);
        setTypeface(typeface);
        setAllCaps(false);
        setText("Login with Sipadu");
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder q3 = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.activity_webview, null);
                EditText editText = view.findViewById(R.id.edit);
                editText.setVisibility(GONE);

                final WebView webView = view.findViewById(R.id.web);

                final ProgressBar progressBar = view.findViewById(R.id.loading);
                q3.setView(view);


                final AlertDialog q3Dialog = q3.create();
                q3Dialog.show();
                webView.getSettings().setJavaScriptEnabled(true);

                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.loadUrl("https://ws.stis.ac.id/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code&scope=");
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        AccessToken accessToken;
                        System.out.println(url);
                        if (url.contains(redirectUri + "?code=")) {
                            webView.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            String[] data = url.split("code=");
                            String kode = data[1];
                            setKode(kode);

                            if (listener != null) {
                                UtilsApi.getAPIService().getAccessToken("authorization_code", clientId, clientSecret, redirectUri, kode).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            String result = response.body().string();
                                            try {
                                                JSONObject jo = new JSONObject(result);
                                                listener.onFinish(jo.getString("access_token"));

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        listener.onFinish(null);
                                    }
                                });


                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    q3Dialog.dismiss();
                                }
                            }, 2000);
                        }
                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);


                    }

                    public void onPageFinished(WebView view, String url) {


                    }


                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                       /* super.onReceivedSslError(view, handler, error);*/
                        System.out.println("sslerror");
                        handler.proceed();
                    }
                });
            }
        });

    }


}
