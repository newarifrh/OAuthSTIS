package dev.blank.oauthstis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

public class OAuthSTIS extends MaterialButton {
    private LoginListener listener;
    AlertDialog q3Dialog;
    Context context;

    public OAuthSTIS(Context context) {
        super(context);
        this.listener = null;
        this.context = context;
        init(context, null);
    }

    public OAuthSTIS(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.listener = null;
        this.context = context;
        init(context, attrs);
    }

    public OAuthSTIS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.listener = null;
        this.context = context;
        init(context, attrs);
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    public interface LoginListener {
        void onError(String error);

        void onFinish(JSONObject user);
    }

    private void init(final Context context, AttributeSet attrs) {
        this.context = context;
        final ViewGroup parent = findViewById(android.R.id.content);

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


        final float scale = ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        setPadding((int) (15 * scale), (int) (15 * scale), (int) (15 * scale), (int) (15 * scale));
        setCompoundDrawablePadding((int) (12 * scale));
        setTextColor(Color.parseColor("#FFFFFF"));

        setBackgroundTintList(getResources().getColorStateList(R.color.blue));

        setGravity(Gravity.START);
        setGravity(Gravity.CENTER_VERTICAL);

        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sipadu, 0, 0, 0);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_medium);
        setTypeface(typeface);
        setAllCaps(false);
        setText(context.getString(R.string.login_with_sipadu));
        setOnClickListener(new OnClickListener() {
            @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
            @Override
            public void onClick(View v) {

                if (clientId != null & redirectUri != null) {
                    if (!clientId.equals("") && !redirectUri.equals("")) {
                        AlertDialog.Builder q3 = new AlertDialog.Builder(context);
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        if (inflater != null) {
                            View view = inflater.inflate(R.layout.activity_webview, parent, false);
                            EditText editText = view.findViewById(R.id.edit);
                            editText.setVisibility(GONE);
                            final WebView webView = view.findViewById(R.id.web);
                            final ProgressBar progressBar = view.findViewById(R.id.loading);
                            q3.setView(view);
                            q3Dialog = q3.create();
                            q3Dialog.show();
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
                            webView.loadUrl("https://ws.stis.ac.id/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code&scope=");
                            webView.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    if (url.contains(redirectUri + "?code=")) {
                                        webView.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.VISIBLE);
                                    } else if (url.contains(redirectUri + "?error=")) {
                                        q3Dialog.dismiss();
                                        listener.onError("Gagal login");
                                        CookieManager cookieManager = CookieManager.getInstance();
                                        cookieManager.removeAllCookie();
                                    }
                                }

                                @Override
                                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                    super.onReceivedError(view, request, error);
                                    listener.onError(context.getString(R.string.tidak_terhubung_ke_server));
                                    q3Dialog.dismiss();
                                }

                                public void onPageFinished(WebView view, String url) {
                                    if (url.contains(redirectUri + "?code=")) {
                                        if (listener != null) {
                                            webView.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML, 'sukses');");
                                            q3Dialog.dismiss();
                                        }
                                    } else if (url.contains(context.getString(R.string.url_ws) + "oauth/authorize")) {
                                        if (listener != null) {
                                            webView.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML, 'gagal');");

                                        }
                                    }

                                }


                            });
                        }
                    } else {
                        listener.onError(context.getString(R.string.lengkapi_konfigurasi));
                    }
                } else {
                    listener.onError(context.getString(R.string.lengkapi_konfigurasi));
                }
            }
        });

    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html, String type) {
            String[] data = html.split("<body>");
            String[] data2 = data[1].split("</body>");
            if (type.equals("sukses")) {
                try {
                    JSONObject jo = new JSONObject(data2[0]);
                    listener.onFinish(jo);
                } catch (JSONException e) {
                    listener.onError(context.getString(R.string.kesalahan_konfigurasi_file_callback));
                }

            } else if (type.equals("gagal")) {
                if (data2[0].contains("error")) {
                    listener.onError(context.getString(R.string.kesalahan_konfigurasi));
                    q3Dialog.dismiss();
                }


            }
        }
    }


}
