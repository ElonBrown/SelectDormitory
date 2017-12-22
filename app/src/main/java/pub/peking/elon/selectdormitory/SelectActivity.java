package pub.peking.elon.selectdormitory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        init();
    }

    private void init(){
        Intent intent = getIntent();
        List<Integer> list = new ArrayList<>();
        if (intent != null) {
            list =(ArrayList<Integer>) getIntent().getIntegerArrayListExtra("num");
        }
        Log.d("myAppSelect",list.toString());
    }

    public static void actionStart(Context context, ArrayList<Integer> data1) {
        Intent intent = new Intent(context, SelectActivity.class);
        intent.putIntegerArrayListExtra("num", data1);
        context.startActivity(intent);
    }
}
