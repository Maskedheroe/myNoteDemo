package com.example.asus.mynotebook.presenter.minepager;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.UserBean;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import org.litepal.crud.ClusterQuery;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by asus on 2018/2/14.
 */

public class UpdatePwd {
    public static void showDialog(final Context context, final FragmentManager fragmentManager) {
        NiceDialog.init()
                .setLayoutId(R.layout.correct_dialog)     //设置dialog布局文件
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(final ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        final EditText account = viewHolder.getView(R.id.account);
                        final EditText pwd = viewHolder.getView(R.id.password);
                        final EditText end_password = viewHolder.getView(R.id.end_password);
                        viewHolder.setOnClickListener(R.id.login, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<UserBean> userBeans = DataSupport.where("userName == ?",
                                        account.getText().toString()).find(UserBean.class);
                                if (userBeans.get(0)!=null){
                                    if (DataSupport.where("userPwd == ?", pwd.getText().toString()).find(UserBean.class).size()!=0){
                                        UserBean userBean = new UserBean(account.getText().toString(),end_password.getText().toString());
                                        userBean.saveOrUpdate();  //修改密码逻辑
                                        Toast.makeText(context,"更改成功!",Toast.LENGTH_SHORT).show();
                                        baseNiceDialog.dismiss();
                                    }else {
                                        Toast.makeText(context,"密码错误",Toast.LENGTH_SHORT).show();
                                        pwd.setText("");
                                        end_password.setText("");
                                    }
                                }
                            }
                        });
                    }
                })
                .setDimAmount(0.5f)     //调节灰色背景透明度[0-1]，默认0.5f
                .setShowBottom(false)     //是否在底部显示dialog，默认flase
                .setOutCancel(true)     //设置点击外部消失
                .show(fragmentManager);
    }
}