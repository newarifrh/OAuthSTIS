package dev.blank.oauthstis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class OAuthSTIS extends Button {
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

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    public interface LoginListener {
        public void onError(String error);

        public void onFinish(String result);
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


        final float scale = ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        setPadding((int) (8 * scale), (int) (8 * scale), (int) (10 * scale), (int) (8 * scale));
        setCompoundDrawablePadding((int) (12 * scale));
        setTextColor(Color.parseColor("#FFFFFF"));


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
                        listener.onError("Tidak terhubung ke Server");
                        q3Dialog.dismiss();
                    }

                    public void onPageFinished(WebView view, String url) {
                        if (url.contains(redirectUri + "?code=")) {
                            if (listener != null) {
                                webView.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);");
                                q3Dialog.dismiss();
                            }
                        }

                    }


                });
            }
        });

    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            String[] data = html.split("<body>");
            String[] data2 = data[1].split("</body>");
            listener.onFinish(data2[0]);
        }
    }


}
