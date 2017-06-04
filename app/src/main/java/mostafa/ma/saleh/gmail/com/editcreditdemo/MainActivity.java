package mostafa.ma.saleh.gmail.com.editcreditdemo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import mostafa.ma.saleh.gmail.com.editcredit.EditCredit;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private EditCredit mEditCredit;
    private RadioGroup rgOptions;
    private Button btnValidate;
    private Button btnGetNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        setListeners();
    }

    private void setListeners() {
        rgOptions.setOnCheckedChangeListener(this);
        btnValidate.setOnClickListener(this);
        btnGetNumber.setOnClickListener(this);
    }

    private void findViewsById(){
        mEditCredit = (EditCredit) findViewById(R.id.editcredit);
        rgOptions = (RadioGroup) findViewById(R.id.rg_options);
        btnValidate = (Button) findViewById(R.id.btn_validate);
        btnGetNumber = (Button) findViewById(R.id.btn_get_number);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rb_no_separators:
                mEditCredit.setSeparator(EditCredit.NO_SEPARATOR);
                break;
            case R.id.rb_spaces:
                mEditCredit.setSeparator(EditCredit.SPACES_SEPARATOR);
                break;
            case R.id.rb_dashes:
                mEditCredit.setSeparator(EditCredit.DASHES_SEPARATOR);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_validate:
                Toast.makeText(this, mEditCredit.isCardValid() ? "Valid" : "Not Valid", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_get_number:
                Toast.makeText(this, mEditCredit.getTextWithoutSeparator(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
