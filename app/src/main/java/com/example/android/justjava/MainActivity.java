/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int price = 5;
    int extra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whipped = (CheckBox) findViewById(R.id.whippedCheckBox);
        boolean hasWhippedCream = whipped.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolateCheckBox);
        boolean hasChocolate = chocolate.isChecked();
        EditText nameEditText = (EditText) findViewById(R.id.nameEditView);
        String editText = nameEditText.getText().toString();

        if (hasWhippedCream == true) {
            extra = 1;
        }
        if (hasChocolate == true) {
            extra = 2;
        }
        if (hasChocolate == true && hasWhippedCream == true) {
            extra = 3;
        }

        display(quantity);
        createOrderSummary(quantity * (price + extra), hasWhippedCream, hasChocolate, editText);
    }

    /**
     * This method initializes the quantity to 3.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method initializes the quantity to 1.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


        /**
         * This method displays the given price and an overall summary of toppings and customer info on the screen.
         */
    private void createOrderSummary(int number, boolean hasWhipped, boolean hasChoco, String editText) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        String emailBody = ("Name: "+ editText +"\nAdd whipped cream? " + hasWhipped + "\nAdd chocolate? " + hasChoco + "\nQuantity: " + quantity + "\nTotal: " + NumberFormat.getCurrencyInstance().format(number) + "\nThank you!");
        composeEmail("Order Summary", emailBody);
    }

    /**
     *
     * @param subject = The subject title of the email " Order Summary "
     * @param body = The entire order summary to be in the actual body of the email.
     */
    public void composeEmail(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
