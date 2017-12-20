package pub.peking.elon.selectdormitory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int UPDATE_STUDENT_INFORMATION = 1;
    private String stuId;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_STUDENT_INFORMATION:
//                    updateStuInfo((Student) msg.obj);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
    }

    private void initView() {
        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        stuId = sharedPreferences.getString("stuId", "1701220048");
        Log.d("myApp", stuId);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tvStuId = (TextView) headerView.findViewById(R.id.tvStuId);
        tvStuId.setText(stuId);
    }

    private void getStuInfo(String stuId) {
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid=" + stuId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Student stuInfo = queryUrl(address);
                Log.d("myApp", stuInfo.toString());
                if (stuInfo != null) {
                    Message msg = new Message();
                    msg.what = UPDATE_STUDENT_INFORMATION;
                    msg.obj = stuInfo;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }


    public Student queryUrl(final String address) {
        Log.d("myAppUrl", address);
        HttpURLConnection con = null;
        Student stuInfo = new Student();
        try {
            URL url = new URL(address);
            //ignore https certificate validation | 忽略 https 证书验证
            if (url.getProtocol().toUpperCase().equals("HTTPS")) {
                trustAllHosts();
                HttpsURLConnection https = (HttpsURLConnection) url
                        .openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                con = https;
            } else {
                con = (HttpURLConnection) url.openConnection();
            }

            con.setRequestMethod("GET");
            con.setConnectTimeout(8000);
            con.setReadTimeout(8000);
            InputStream in = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                response.append(str);
            }
            String responseStr = response.toString();
            Log.d("myAppURL", responseStr);

            stuInfo = parseJson(responseStr);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return stuInfo;
    }

    private Student parseJson(String jsonStr) {
        Student stuInfo = new Student();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject data = jsonObject.getJSONObject("data");
            if (!jsonObject.getString("errcode").toString().equals("0")) {
                String errmsg = data.getString("errmsg");
                Log.d("myApp",errmsg);
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("loginErr", errmsg);
                startActivityForResult(intent, 1);

            } else {
                stuInfo.setId(data.getString("studentid"));
                stuInfo.setName(data.getString("name"));
                stuInfo.setGender(data.getString("gender"));
                stuInfo.setVcode(data.getString("vcode"));
                stuInfo.setRoom(data.getString("room"));
                stuInfo.setBuilding(data.getString("building"));
                stuInfo.setLocation(data.getString("location"));
                stuInfo.setGrade(data.getString("grade"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stuInfo;
    }

    public static void trustAllHosts() {
        //设置信任所有服务器，不检查证书
        // Create a trust manager that does not validate certificate chains
        // Android use X509 cert
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //自定义DO_NOT_VERIFY
    public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, LoginActivity.class);                 //新建Intent交互通信
            startActivityForResult(intent, 1);                                        //开启一个Activity并返回值
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                 //记录新的信息
        if (requestCode == 1 && resultCode == RESULT_OK) {
            stuId = data.getStringExtra("stuId");
            SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("stuId", stuId);
            editor.commit();
            Log.d("myAppRes", "登录的学号为：" + stuId);
            initView();
            getStuInfo(stuId);
        }
    }
}
