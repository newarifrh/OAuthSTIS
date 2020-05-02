package dev.blank.oauthstis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import dev.blank.oauthstis.api.OauthService;
import dev.blank.oauthstis.api.RetrofitClient;
import dev.blank.oauthstis.api.UserService;
import dev.blank.oauthstis.model.Token;
import dev.blank.oauthstis.model.dosen.Dosen;
import dev.blank.oauthstis.model.mahasiswa.Mahasiswa;
import dev.blank.oauthstis.utils.LoginListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dev.blank.oauthstis.utils.Constant.GRANT_TYPE;
import static dev.blank.oauthstis.utils.Constant.TAG;

public class OAuthSTIS extends MaterialButton {
    private LoginListener listener;
    private AlertDialog alertDialog;
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    public OAuthSTIS(Context context) {
        super(context);
        this.listener = null;
        initView(context);
        initListener(context);
    }

    public OAuthSTIS(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.listener = null;
        initView(context);
        initListener(context);
    }

    public OAuthSTIS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.listener = null;
        initView(context);
        initListener(context);
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    private void initListener(final Context context) {
        final ViewGroup parent = findViewById(android.R.id.content);

        setOnClickListener(new OnClickListener() {
            @SuppressLint({"SetJavaScriptEnabled"})
            @Override
            public void onClick(View v) {
                if (clientId != null && clientSecret != null && redirectUri != null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    if (inflater != null) {
                        View view = inflater.inflate(R.layout.activity_webview, parent, false);
                        EditText editText = view.findViewById(R.id.edit);
                        editText.setVisibility(GONE);
                        final WebView webView = view.findViewById(R.id.web);
                        final ProgressBar progressBar = view.findViewById(R.id.loading);
                        alertDialogBuilder.setView(view);
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setDomStorageEnabled(true);
                        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                        webView.loadUrl("https://ws.stis.ac.id/oauth/authorize?client_id=" + clientId + "&redirect_uri=&response_type=code&scope=");
                        webView.setWebViewClient(new WebViewClient() {
                            @Override

                            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                if (url.contains("?code=")) {
                                    String token = url.split("code=")[1];
                                    webView.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.VISIBLE);

                                    OauthService oauthService = RetrofitClient.getRetrofitInstance().create(OauthService.class);
                                    oauthService.getToken(GRANT_TYPE, clientId, clientSecret, redirectUri, token).enqueue(new Callback<Token>() {
                                        @Override
                                        public void onResponse(Call<Token> call, Response<Token> response) {
                                            if (response.isSuccessful()) {
                                                UserService userService = RetrofitClient.getRetrofitInstance().create(UserService.class);
                                                userService.getUser("Bearer " + response.body().getAccess_token()).enqueue(new Callback<JsonElement>() {
                                                    @Override
                                                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                                        if (response.isSuccessful()) {
                                                            Gson gson = new Gson();
                                                            if (response.body().getAsJsonObject().get("profile").getAsJsonObject().get("role").getAsString().equalsIgnoreCase("mahasiswa")) {
                                                                Mahasiswa mahasiswa = gson.fromJson(response.body(), Mahasiswa.class);
                                                                listener.onFinish(mahasiswa);
                                                            } else {
                                                                Dosen dosen = gson.fromJson(response.body(), Dosen.class);
                                                                listener.onFinish(dosen);
                                                            }
                                                            Log.d(TAG, context.getString(R.string.berhasil_login));
                                                            alertDialog.dismiss();
                                                        } else {
                                                            alertDialog.dismiss();
                                                            listener.onError(context.getString(R.string.gagal_login));
                                                            Log.d(TAG, context.getString(R.string.gagal_login));
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                                CookieManager.getInstance().removeAllCookies(null);
                                                                CookieManager.getInstance().flush();
                                                            } else {
                                                                CookieManager.getInstance().removeAllCookie();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<JsonElement> call, Throwable t) {
                                                        alertDialog.dismiss();
                                                        listener.onError(context.getString(R.string.gagal_login));
                                                        Log.d(TAG, context.getString(R.string.gagal_login));
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                            CookieManager.getInstance().removeAllCookies(null);
                                                            CookieManager.getInstance().flush();
                                                        } else {
                                                            CookieManager.getInstance().removeAllCookie();
                                                        }
                                                    }
                                                });
                                            } else {
                                                alertDialog.dismiss();
                                                listener.onError(context.getString(R.string.gagal_login));
                                                Log.d(TAG, context.getString(R.string.gagal_login));
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    CookieManager.getInstance().removeAllCookies(null);
                                                    CookieManager.getInstance().flush();
                                                } else {
                                                    CookieManager.getInstance().removeAllCookie();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Token> call, Throwable t) {
                                            alertDialog.dismiss();
                                            listener.onError(context.getString(R.string.gagal_login));
                                            Log.d(TAG, context.getString(R.string.gagal_login));
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                CookieManager.getInstance().removeAllCookies(null);
                                                CookieManager.getInstance().flush();
                                            } else {
                                                CookieManager.getInstance().removeAllCookie();
                                            }
                                        }
                                    });
                                } else if (url.contains("?error=")) {
                                    alertDialog.dismiss();
                                    listener.onError(context.getString(R.string.gagal_login));
                                    Log.d(TAG, context.getString(R.string.gagal_login));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        CookieManager.getInstance().removeAllCookies(null);
                                        CookieManager.getInstance().flush();
                                    } else {
                                        CookieManager.getInstance().removeAllCookie();
                                    }
                                }
                            }

                            @Override
                            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                super.onReceivedError(view, request, error);
                                listener.onError(context.getString(R.string.tidak_terhubung_ke_server));
                                Log.e(TAG, context.getString(R.string.tidak_terhubung_ke_server));
                                alertDialog.dismiss();
                            }

                            public void onPageFinished(WebView view, String url) {
                            }


                        });
                    }

                } else {
                    Log.e(TAG, context.getString(R.string.lengkapi_konfigurasi));
                    listener.onError(context.getString(R.string.lengkapi_konfigurasi));
                }
            }
        });

    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    private void initView(Context context) {
        final float scale = ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        setPadding((int) (15 * scale), (int) (15 * scale), (int) (15 * scale), (int) (15 * scale));
        setCompoundDrawablePadding((int) (12 * scale));
        setTextColor(Color.parseColor("#FFFFFF"));
        setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.blue));
        setGravity(Gravity.START);
        setGravity(Gravity.CENTER_VERTICAL);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sipadu, 0, 0, 0);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_medium);
        setTypeface(typeface);
        setAllCaps(false);
        setText(context.getString(R.string.login_with_sipadu));
    }
}
