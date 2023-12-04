package mostafa.ma.saleh.gmail.com.editcreditdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mostafa.ma.saleh.gmail.com.editcredit.EditCredit;
import mostafa.ma.saleh.gmail.com.editcreditdemo.databinding.ActivityMainBinding;

public class JavaMainActivity
        extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        addListeners();
    }

    private void addListeners() {
        binding.separatorsRadioGroup.setOnCheckedChangeListener(this);
        binding.gravityRadioGroup.setOnCheckedChangeListener(this);
        binding.visaCheckBox.setOnCheckedChangeListener(this);
        binding.masterCardCheckBox.setOnCheckedChangeListener(this);
        binding.americanExpressCheckBox.setOnCheckedChangeListener(this);
        binding.discoverCheckBox.setOnCheckedChangeListener(this);
        binding.dinersCheckBox.setOnCheckedChangeListener(this);
        binding.validateButton.setOnClickListener(this);
        binding.getNumberButton.setOnClickListener(this);
        binding.getTypeButton.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
        int id = group.getId();
        if (id == R.id.separatorsRadioGroup) {
            onSeparatorsCheckedChanged(checkedId);
        } else if (id == R.id.gravityRadioGroup) {
            onGravityCheckedChanged(checkedId);
        }
    }

    private void onSeparatorsCheckedChanged(@IdRes int checkedId) {
        EditCredit.Separator separator;
        if (checkedId == R.id.spacesRadioButton) {
            separator = EditCredit.Separator.SPACES;
        } else if (checkedId == R.id.dashesRadioButton) {
            separator = EditCredit.Separator.DASHES;
        } else {
            separator = EditCredit.Separator.NONE;
        }
        binding.editCredit.setSeparator(separator);
    }

    private void onGravityCheckedChanged(@IdRes int checkedId) {
        EditCredit.Gravity gravity;
        if (checkedId == R.id.startRadioButton) {
            gravity = EditCredit.Gravity.START;
        } else if (checkedId == R.id.leftRadioButton) {
            gravity = EditCredit.Gravity.LEFT;
        } else if (checkedId == R.id.rightRadioButton) {
            gravity = EditCredit.Gravity.RIGHT;
        } else {
            gravity = EditCredit.Gravity.END;
        }
        binding.editCredit.setDrawableGravity(gravity);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ArrayList<EditCredit.Card> disabledCards = new ArrayList<>();
        if (binding.visaCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.VISA);
        }
        if (binding.masterCardCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.MASTERCARD);
        }
        if (binding.americanExpressCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.AMEX);
        }
        if (binding.discoverCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.DISCOVER);
        }
        if (binding.dinersCheckBox.isChecked()) {
            disabledCards.add(EditCredit.Card.DINERS);
        }
        binding.editCredit.setDisabledCards(disabledCards.toArray(new EditCredit.Card[0]));
    }

    public void onClick(@NonNull View view) {
        String message;
        int id = view.getId();
        if (id == R.id.validateButton) {
            message = binding.editCredit.isCardValid() ? "Valid" : "Not Valid";
        } else if (id == R.id.getNumberButton) {
            message = binding.editCredit.getTextWithoutSeparator();
        } else if (id == R.id.getTypeButton) {
            message = getCardTypeString(binding.editCredit.getCardType());
        } else {
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getCardTypeString(@NonNull EditCredit.Card card) {
        return switch (card) {
            case VISA -> getString(R.string.visa);
            case MASTERCARD -> getString(R.string.mastercard);
            case AMEX -> getString(R.string.american_express);
            case DISCOVER -> getString(R.string.discover);
            case DINERS -> getString(R.string.diners_club_international);
            default -> getString(R.string.unknown);
        };
    }
}
