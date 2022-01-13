package mostafa.ma.saleh.gmail.com.editcreditdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit
import mostafa.ma.saleh.gmail.com.editcreditdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener,
    CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        addListeners()
    }

    private fun addListeners() {
        binding.separatorsRadioGroup.setOnCheckedChangeListener(this)
        binding.gravityRadioGroup.setOnCheckedChangeListener(this)
        binding.visaCheckBox.setOnCheckedChangeListener(this)
        binding.masterCardCheckBox.setOnCheckedChangeListener(this)
        binding.americanExpressCheckBox.setOnCheckedChangeListener(this)
        binding.discoverCheckBox.setOnCheckedChangeListener(this)
        binding.dinersCheckBox.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup, @IdRes checkedId: Int) {
        when (group.id) {
            R.id.separatorsRadioGroup -> onSeparatorsCheckedChanged(checkedId)
            R.id.gravityRadioGroup -> onGravityCheckedChanged(checkedId)
        }
    }

    private fun onSeparatorsCheckedChanged(@IdRes checkedId: Int) {
        binding.editCredit.separator = when (checkedId) {
            R.id.spacesRadioButton -> EditCredit.Separator.SPACES
            R.id.dashesRadioButton -> EditCredit.Separator.DASHES
            else -> EditCredit.Separator.NONE
        }
    }

    private fun onGravityCheckedChanged(@IdRes checkedId: Int) {
        binding.editCredit.drawableGravity = when (checkedId) {
            R.id.startRadioButton -> EditCredit.Gravity.START
            R.id.leftRadioButton -> EditCredit.Gravity.LEFT
            R.id.rightRadioButton -> EditCredit.Gravity.RIGHT
            else -> EditCredit.Gravity.END
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val disabledCards = mutableListOf<EditCredit.Card>()
        if (binding.visaCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.VISA)
        }
        if (binding.masterCardCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.MASTERCARD)
        }
        if (binding.americanExpressCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.AMEX)
        }
        if (binding.discoverCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.DISCOVER)
        }
        if (binding.dinersCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.DINERS)
        }
        binding.editCredit.setDisabledCards(*disabledCards.toTypedArray())
    }

    fun onClick(view: View) {
        val message = when (view.id) {
            R.id.validateButton -> if (binding.editCredit.isCardValid) "Valid" else "Not Valid"
            R.id.getNumberButton -> binding.editCredit.textWithoutSeparator
            R.id.getTypeButton -> getCardTypeString(binding.editCredit.cardType)
            else -> return
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCardTypeString(card: EditCredit.Card) =
        getString(
            when (card) {
                EditCredit.Card.VISA -> R.string.visa
                EditCredit.Card.MASTERCARD -> R.string.mastercard
                EditCredit.Card.AMEX -> R.string.american_express
                EditCredit.Card.DISCOVER -> R.string.discover
                EditCredit.Card.DINERS -> R.string.diners_club_international
                else -> R.string.unknown
            }
        )
}
