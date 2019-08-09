package com.zuofei.xmpp;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zuofei.xmpp.utils.ThreadUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    /**
     * 验证用户
     */
    private void verifyUser() {
        final String username = this.username.getText().toString();
        final String password = this.password.getText().toString();
        if(TextUtils.isEmpty(username)){
            this.username.setError("用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            this.password.setError("密码不能为空");
            return;
        }
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                connectServer(username,password);
            }
        });

    }

    /**
     * 连接openfire服务端
     * @param username
     * @param password
     */
    private void connectServer(String username, String password) {
        String ip = "192.168.3.104";
        int port = 5222;
        ConnectionConfiguration config = new ConnectionConfiguration(ip,port);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        config.setDebuggerEnabled(true);
        XMPPConnection conn = new XMPPConnection(config);
        try {
            conn.connect();
            conn.login(username,password);
            ThreadUtils.runInUIThread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });
        } catch (XMPPException e) {
            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        loginBtn = findViewById(R.id.loginBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }
}
