package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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

    int basePrice;
    int numberOfCoffees = 2;
//    String priceMessage;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    /**
//     * Checkbox check.
//     */
//    public void checkBoxCheck(View v) {
//        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
//        boolean hasWhippedCream = whippedCream.isChecked();
//
//        Toast checkBoxTrue = Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_LONG);
//        Toast checkBoxFalse = Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_LONG);
//
//        if (hasWhippedCream) {
//            checkBoxTrue.show();
//        } else {
//            checkBoxFalse.show();
//        }
//    }

//    /**
//     * This method is called when the order button is clicked.
//     */
//    public void submitOrder(View view) {
////        displayPrice(numberOfCoffees * basePrice);
//        if (numberOfCoffees == 0) {
//            priceMessage = "Free";
//        } else {
//            priceMessage = "Amount Due $" + (numberOfCoffees * basePrice);
//        }
//        displayMessage(priceMessage);
//    }

    /**
     * Creates an order summary.
     */
    public String submitOrderSummary(View v) {
        int total = 0;
        String summary = "";
        /**
         * Checking what is present in the name text box
         */
        EditText nameField = (EditText) findViewById(R.id.name_edit_text);
        name = nameField.getText().toString();

        if (!name.isEmpty()) {
            summary += "Name:  " + name + "\n\n";
        }

        summary += "Number of coffees ordered:  " + numberOfCoffees;

        /**
         * Storing the boolean values from the checkboxes.
         */
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        basePrice = 5;

        if (hasWhippedCream) {
            summary += "\nYou added whipped cream toppings";
            basePrice += 1;
        }
        if (hasChocolate) {
            summary += "\nYou added chocolate toppings";
            basePrice += 2;
        }

        total = (basePrice * numberOfCoffees);

        if (numberOfCoffees != 0) {
            summary += "\n\nTotal:  $" + total;
        } else {
            summary += "\n\nYour order is FREE!";
        }

        summary += "\n\nThank you!";

        displayMessage(summary);

        return summary;
    }

    public void emailOrderSummary(View v) {
        composeEmail("Just Java order for " + name, submitOrderSummary(v));
    }

    /**
     * Intent to handle composing order emails
     * @param subject of the order
     * @param body of the order
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

    /**
     * Increases the number of coffees.
     */
    public void increment(View view) {
        if (numberOfCoffees == 100) {
            Toast lessCoffeeToast = Toast.makeText(getApplicationContext(), "That is way too much coffee!", Toast.LENGTH_LONG);
            lessCoffeeToast.show();
        } else {
            numberOfCoffees++;
            display(numberOfCoffees);
        }
    }

    /**
     * Decreases the number of coffees.
     */
    public void decrement(View view) {
        if (numberOfCoffees == 0) {
            Toast moreCoffeeToast = Toast.makeText(getApplicationContext(), "Please order more coffee!", Toast.LENGTH_LONG);
            moreCoffeeToast.show();
        } else {
            numberOfCoffees--;
            display(numberOfCoffees);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    /**
//     * This method displays the price quantity value on the screen.
//     **/
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }
}