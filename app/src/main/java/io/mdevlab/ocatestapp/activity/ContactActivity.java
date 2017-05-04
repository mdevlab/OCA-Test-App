package io.mdevlab.ocatestapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.mdevlab.ocatestapp.R;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private final String EMAIL_ACCOUNT = "mdevlab2017@gmail.com";
    private final String EMAIL_TYPE = "message/rfc822";
    private final String EMAIL_SUBJECT = "OCA TEST app message from ";

    EditText name;
    EditText message;

    TextInputLayout nameLayout;
    TextInputLayout messageLayout;

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        name = (EditText) findViewById(R.id.contact_name);
        message = (EditText) findViewById(R.id.contact_message);

        nameLayout = (TextInputLayout) findViewById(R.id.contact_name_container);
        messageLayout = (TextInputLayout) findViewById(R.id.contact_message_container);

        send = (Button) findViewById(R.id.send_email);

        // Setting listeners
        name.setOnClickListener(this);
        name.setOnFocusChangeListener(this);
        message.setOnClickListener(this);
        message.setOnFocusChangeListener(this);
        send.setOnClickListener(this);
    }

    /**
     * Each time an edit text loses focus, its content is validated
     *
     * @param v        Edit text view whose focus is changing
     * @param hasFocus Whether the view whose focus has changed is now focused or not
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.contact_name:
                    validateField(name, nameLayout, getString(R.string.contact_name_empty));
                    break;
                case R.id.contact_message:
                    validateField(message, messageLayout, getString(R.string.contact_message_empty));
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_email) {

            // The email is sent only if the form is valid
            if (validateForm())
                sendEmail();
        }
    }

    /**
     * @return Whether the name and message form is valid
     */
    private boolean validateForm() {
        boolean nameIsValid;
        boolean messageIsValid;

        nameIsValid = validateField(name, nameLayout, getString(R.string.contact_name_empty));
        messageIsValid = validateField(message, messageLayout, getString(R.string.contact_message_empty));

        return (nameIsValid && messageIsValid);
    }

    /**
     * @param field        Edit text field being validated
     * @param fieldLayout  Text input layout of the edit text field
     * @param errorMessage Error message to be displayed if the field is invalid
     * @return Whether the edit text field is valid. A valid field is one that isn't null
     * and that isn't empty (spaces are considered to be empty)
     */
    private boolean validateField(EditText field, TextInputLayout fieldLayout, String errorMessage) {

        // field is valid
        if (fieldIsValid(field)) {
            setTextInputLayoutError(fieldLayout, true, null);
            return true;
        }

        // field is invalid
        setTextInputLayoutError(fieldLayout, false, errorMessage);
        return false;
    }

    /**
     * @param field Field being validated
     * @return Whether the field is valid or not
     */
    private boolean fieldIsValid(EditText field) {
        return (field != null && !TextUtils.isEmpty(field.getText().toString().trim()));
    }

    /**
     * Method that displays an error in an editText's textInputLayout.
     *
     * @param textInputLayout TextInputLayout being to display the error
     * @param fieldIsValid    Whether the editText field is valid
     * @param errorMessage    Error message to display if the edit text field is invalid
     */
    private void setTextInputLayoutError(TextInputLayout textInputLayout, boolean fieldIsValid, String errorMessage) {
        textInputLayout.setErrorEnabled(!fieldIsValid);
        textInputLayout.setError(fieldIsValid ? null : errorMessage);
    }

    /**
     * Method that sends an email.
     * It first checks whether the user's device has an app that can send the email,
     * in the case where none is available, an error informing the user of this is displayed
     */
    private void sendEmail() {
        try {
            startActivity(Intent.createChooser(getEmailIntent(), getString(R.string.send_email)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_email_client_error), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method that prepares an intent to send an email.
     * It sets the subject using the user's name
     * It also fills the message's body
     *
     * @return Intent ready to send an email.
     */
    private Intent getEmailIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(EMAIL_TYPE);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ACCOUNT});
        intent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT + name.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
        return intent;
    }
}
