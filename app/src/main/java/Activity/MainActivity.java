package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.erradns.sophix.R;
import com.erradns.sophix.Util;
import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);


        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Util.test();
                Toast.makeText(this,"补丁成功",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}