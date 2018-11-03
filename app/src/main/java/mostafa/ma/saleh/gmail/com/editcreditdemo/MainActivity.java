package mostafa.ma.saleh.gmail.com.editcreditdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private EditCredit mEditCredit;
    private RadioGroup separatorsRadioGroup;
    private RadioGroup gravityRadioGroup;
    private Button validateButton;
    private Button getNumberButton;
    private Button getTypeButton;
    private CheckBox visaCheckBox;
    private CheckBox masterCardCheckBox;
    private CheckBox americanExpressCheckBox;
    private CheckBox discoverCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        setListeners();
    }

    private void setListeners() {
        separatorsRadioGroup.setOnCheckedChangeListener(this);
        gravityRadioGroup.setOnCheckedChangeListener(this);
        validateButton.setOnClickListener(this);
        getNumberButton.setOnClickListener(this);
        getTypeButton.setOnClickListener(this);
        visaCheckBox.setOnCheckedChangeListener(this);
        masterCardCheckBox.setOnCheckedChangeListener(this);
        americanExpressCheckBox.setOnCheckedChangeListener(this);
        discoverCheckBox.setOnCheckedChangeListener(this);
    }

    private void findViewsById() {
        mEditCredit = findViewById(R.id.editcredit);
        separatorsRadioGroup = findViewById(R.id.separators_radio_group);
        gravityRadioGroup = findViewById(R.id.gravity_radio_group);
        validateButton = findViewById(R.id.btn_validate);
        getNumberButton = findViewById(R.id.btn_get_number);
        getTypeButton = findViewById(R.id.btn_get_type);
        visaCheckBox = findViewById(R.id.chk_visa);
        masterCardCheckBox = findViewById(R.id.chk_mastercard);
        americanExpressCheckBox = findViewById(R.id.chk_amex);
        discoverCheckBox = findViewById(R.id.chk_discover);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.separators_radio_group:
                onSeparatorsCheckedChanged(checkedId);
                break;
            case R.id.gravity_radio_group:
                onGravityCheckedChanged(checkedId);
                break;
        }
    }

    private void onSeparatorsCheckedChanged(@IdRes int checkedId) {
        switch (checkedId) {
            case R.id.no_separators_radio_button:
                mEditCredit.setSeparator(EditCredit.Separator.NONE);
                break;
            case R.id.spaces_radio_button:
                mEditCredit.setSeparator(EditCredit.Separator.SPACES);
                break;
            case R.id.dashes_radio_button:
                mEditCredit.setSeparator(EditCredit.Separator.DASHES);
                break;
        }
    }

    private void onGravityCheckedChanged(@IdRes int checkedId) {
        switch (checkedId) {
            case R.id.start_radio_button:
                mEditCredit.setDrawableGravity(EditCredit.Gravity.START);
                break;
            case R.id.end_radio_button:
                mEditCredit.setDrawableGravity(EditCredit.Gravity.END);
                break;
            case R.id.left_radio_button:
                mEditCredit.setDrawableGravity(EditCredit.Gravity.LEFT);
                break;
            case R.id.right_radio_button:
                mEditCredit.setDrawableGravity(EditCredit.Gravity.RIGHT);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        List<EditCredit.Card> disabledCards = new ArrayList<>();
        if (visaCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.VISA);
        }
        if (masterCardCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.MASTERCARD);
        }
        if (americanExpressCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.AMEX);
        }
        if (discoverCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.DISCOVER);
        }
        mEditCredit.setDisabledCards(disabledCards.toArray(new EditCredit.Card[0]));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_validate:
                Toast.makeText(this, mEditCredit.isCardValid() ? "Valid" : "Not Valid", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_get_number:
                Toast.makeText(this, mEditCredit.getTextWithoutSeparator(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_get_type:
                Toast.makeText(this, getCardTypeString(mEditCredit.getCardType()), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String getCardTypeString(EditCredit.Card card) {
        @StringRes
        int stringRes;
        switch (card) {
            case VISA:
                stringRes = R.string.visa;
                break;
            case MASTERCARD:
                stringRes = R.string.mastercard;
                break;
            case AMEX:
                stringRes = R.string.american_express;
                break;
            case DISCOVER:
                stringRes = R.string.discover;
                break;
            default:
                stringRes = R.string.unknown;
                break;
        }
        return getString(stringRes);
    }
}
