package com.example.asus.mynotebook.view.pages.minepager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.UserBean;
import com.example.asus.mynotebook.presenter.minepager.MyLogin;
import com.example.asus.mynotebook.presenter.minepager.UpdatePwd;
import com.example.asus.mynotebook.view.activity.UpdateIcon;
import com.example.asus.mynotebook.view.interfaces.BasePager;


import org.litepal.crud.ClusterQuery;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;


/**
 * Created by asus on 2018/1/20.
 */

public class MinePager extends BasePager {
    //我的页面

    private final FragmentManager mFragmentManager;
    private CircleImageView circleImageView;
    private MaterialLoginView ml_login;
    private TextView accountName;
    private LinearLayout update_pwd;
    private LinearLayout login;
    private LinearLayout manager_login;
    private LinearLayout update_icon;
    private View dismissLogin;


    public MinePager(Activity activity, FragmentManager mFragmentManager) {
        super(activity);
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public void initData() {
        final View view = View.inflate(mactivity, R.layout.pager_mine, null);
        frame_Content_Layout.addView(view);
        circleImageView = view.findViewById(R.id.circleImageView);
        accountName = view.findViewById(R.id.tv_accountName);
        ml_login = view.findViewById(R.id.ml_login);
        update_pwd = view.findViewById(R.id.update_pwd);
        login = view.findViewById(R.id.login_pwd);
        manager_login = view.findViewById(R.id.login_manager);
        update_icon = view.findViewById(R.id.login_updateicon);
        dismissLogin = view.findViewById(R.id.ib_dismisslogin);
        dismissLogin.setVisibility(View.INVISIBLE);
        initLogIn(view);
        update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.currentAccount == -1) {
                    Toast.makeText(mactivity, "未登录！", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    UpdatePwd.showDialog(mactivity, mFragmentManager);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyLogin(view).mlogin(mactivity);
            }
        });
        manager_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyLogin(view).managerlogin(mactivity);
            }
        });
        update_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flags.currentAccount != -1) {
                    mactivity.startActivity(new Intent(mactivity, UpdateIcon.class));
                }else {
                    new SVProgressHUD(mactivity).showErrorWithStatus("未登录，请先登陆", SVProgressHUD.SVProgressHUDMaskType.Clear);
                    return;
                }
            }
        });
        Bundle extras = mactivity.getIntent().getExtras();
        if (extras != null) {
            String icon = extras.getString("icon");
            Glide.with(mactivity).load(Uri.fromFile(new File(icon))).into(circleImageView);
        }
    }

    private void initLogIn(View view) {
        SQLiteDatabase db = Connector.getDatabase();
        new MyLogin(view).login(mactivity);
    }


}
