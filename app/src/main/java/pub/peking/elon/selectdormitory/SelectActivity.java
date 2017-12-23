package pub.peking.elon.selectdormitory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends Activity implements View.OnClickListener {
    private Integer count = 3;
    TextView tvAddItem,tvId;
    ImageView btnBack;
    Button btnSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        init();
    }

    private void init() {
        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String stuId = sharedPreferences.getString("stuId", "1701220048");

        tvId = (TextView) findViewById(R.id.tv_id);
        btnBack = (ImageView) findViewById(R.id.title_back);
        btnBack.setOnClickListener(this);
        tvAddItem = (TextView) findViewById(R.id.tv_add_item);
        tvAddItem.setOnClickListener(this);
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(this);

        tvId.setText(stuId);
        Intent intent = getIntent();
        List<Integer> list = new ArrayList<>();
        if (intent != null) {
            list = getIntent().getIntegerArrayListExtra("num");
        }
        Log.d("myAppSelect", list.toString());
        Spinner spinner = findViewById(R.id.spinner);
        List<String> data_list = new ArrayList<String>();
        for (Integer i : list) {
            String str = new String();
            switch (i) {
                case 0:
                    str = "五";
                    break;
                case 1:
                    str = "十三";
                    break;
                case 2:
                    str = "十四";
                    break;
                case 3:
                    str = "八";
                    break;
                case 4:
                    str = "九";
                    break;
            }
            str += "号";
            data_list.add(str);
        }
        Log.d("myApp", data_list.toString());
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public static void actionStart(Context context, ArrayList<Integer> data1) {
        Intent intent = new Intent(context, SelectActivity.class);
        intent.putIntegerArrayListExtra("num", data1);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_add_item:
                LayoutInflater inflater = LayoutInflater.from(SelectActivity.this);
                // 获取需要被添加控件的布局
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.add_id_vcode_item);
                // 获取需要添加的布局
                LinearLayout layout = (LinearLayout) inflater.inflate(
                        R.layout.id_vcode_item, null).findViewById(R.id.id_vcode_item);
                // 将布局加入到当前布局中
                linearLayout.addView(layout);

                count -= 1;
                if (count==0) {
                    tvAddItem.setVisibility(View.GONE);
                    break;
                }
                break;
            case R.id.btn_select:
                new AlertDialog.Builder(SelectActivity.this).setTitle("系统反馈")//设置对话框标题
                        .setMessage("宿舍分配成功！")//设置显示的内容
                        .setPositiveButton("返回主页查看个人信息",new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                finish();
                            }
                }).show();//在按键响应事件中显示此对话框
                break;
            default:
                break;
        }
    }
}
