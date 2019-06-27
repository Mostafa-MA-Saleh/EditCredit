package mostafa.ma.saleh.gmail.com.editcreditdemo

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit

class MainActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addListeners()
    }

    private fun addListeners() {
        separatorsRadioGroup.setOnCheckedChangeListener(this)
        gravityRadioGroup.setOnCheckedChangeListener(this)
        visaCheckBox.setOnCheckedChangeListener(this)
        masterCardCheckBox.setOnCheckedChangeListener(this)
        americanExpressCheckBox.setOnCheckedChangeListener(this)
        discoverCheckBox.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup, @IdRes checkedId: Int) {
        when (group.id) {
            R.id.separatorsRadioGroup -> onSeparatorsCheckedChanged(checkedId)
            R.id.gravityRadioGroup -> onGravityCheckedChanged(checkedId)
        }
    }

    private fun onSeparatorsCheckedChanged(@IdRes checkedId: Int) =
            editCredit.setSeparator(when (checkedId) {
                R.id.spacesRadioButton -> EditCredit.Separator.SPACES
                R.id.dashesRadioButton -> EditCredit.Separator.DASHES
                else -> EditCredit.Separator.NONE
            })

    private fun onGravityCheckedChanged(@IdRes checkedId: Int) =
            editCredit.setDrawableGravity(when (checkedId) {
                R.id.startRadioButton -> EditCredit.Gravity.START
                R.id.leftRadioButton -> EditCredit.Gravity.LEFT
                R.id.rightRadioButton -> EditCredit.Gravity.RIGHT
                else -> EditCredit.Gravity.END
            })

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val disabledCards = mutableListOf<EditCredit.Card>()
        if (visaCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.VISA)
        }
        if (masterCardCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.MASTERCARD)
        }
        if (americanExpressCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.AMEX)
        }
        if (discoverCheckBox.isChecked) {
            disabledCards.add(EditCredit.Card.DISCOVER)
        }
        editCredit.setDisabledCards(*disabledCards.toTypedArray())
    }

    fun onClick(view: View) {
        val message = when (view.id) {
            R.id.validateButton -> if (editCredit.isCardValid) "Valid" else "Not Valid"
            R.id.getNumberButton -> editCredit.textWithoutSeparator
            R.id.getTypeButton -> getCardTypeString(editCredit.cardType)
            else -> return
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCardTypeString(card: EditCredit.Card) =
            getString(when (card) {
                EditCredit.Card.VISA -> R.string.visa
                EditCredit.Card.MASTERCARD -> R.string.mastercard
                EditCredit.Card.AMEX -> R.string.american_express
                EditCredit.Card.DISCOVER -> R.string.discover
                else -> R.string.unknown
            })
}
