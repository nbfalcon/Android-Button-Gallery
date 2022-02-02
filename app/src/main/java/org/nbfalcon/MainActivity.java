package org.nbfalcon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.example.R;
import com.google.android.material.chip.Chip;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static void bindImageRadioButton(RadioButton button, @DrawableRes int drawable,
                                             TextView textView, ImageView imageView, ImageButton imageButton) {
        button.setOnCheckedChangeListener((view, state) -> {
            if (state) {
                textView.setText(view.getText());
                imageView.setImageResource(drawable);
                imageButton.setImageResource(drawable);
            }
        });
    }

    @SuppressLint("SetTextI18n") // " | " is not language-specific
    private static void bindChipsToTextView(TextView textView, Chip chip1, Chip chip2) {
        if (chip1.isChecked() && chip2.isChecked()) {
            textView.setText(chip1.getText() + " | " + chip2.getText());
        } else if (chip1.isChecked()) {
            textView.setText(chip1.getText());
        } else if (chip2.isChecked()) {
            textView.setText(chip2.getText());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lambda
        final ImageView image = findViewById(R.id.imageView);
        final SwitchCompat enableImage = findViewById(R.id.enableImage);
        final ToggleButton enableImageToggle = findViewById(R.id.enableImageToggle);
        // Prevent endless recursion
        final boolean[] enableImageDebounce = new boolean[1];
        enableImage.setOnCheckedChangeListener((view, state) -> {
            image.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
            if (!enableImageDebounce[0]) {
                enableImageDebounce[0] = true;
                enableImageToggle.setChecked(state);
            } else {
                enableImageDebounce[0] = false;
            }
        });
        enableImageToggle.setOnCheckedChangeListener((view, state) -> {
            if (!enableImageDebounce[0]) {
                enableImageDebounce[0] = true;
                enableImage.setChecked(state);
            } else {
                enableImageDebounce[0] = false;
            }
        });

        final TextView checkBoxTextView = findViewById(R.id.checkBoxTextView);
        final CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener((view, state) -> checkBoxTextView.setText(
                state ? R.string.checked_yes : R.string.checked_no));
        checkBoxTextView.setOnClickListener((view) -> checkBox.setChecked(!checkBox.isChecked()));

        final Random random = new Random();

        final RadioButton image1 = findViewById(R.id.image1);
        final RadioButton image2 = findViewById(R.id.image2);
        final RadioButton image3 = findViewById(R.id.image3);
        final TextView imageTextView = findViewById(R.id.imageTextView);
        final ImageButton randomImage2 = findViewById(R.id.randomImage2);
        bindImageRadioButton(image1, android.R.drawable.star_on, imageTextView, image, randomImage2);
        bindImageRadioButton(image2, android.R.drawable.star_off, imageTextView, image, randomImage2);
        bindImageRadioButton(image3, android.R.drawable.star_big_on, imageTextView, image, randomImage2);

        Button randomImage = findViewById(R.id.randomImage);
        final View.OnClickListener randomImageCB = (view) -> {
            switch (random.nextInt(3)) {
                case 0:
                    image1.setChecked(true);
                    break;
                case 1:
                    image2.setChecked(true);
                    break;
                case 2:
                    image3.setChecked(true);
                    break;
            }
        };
        randomImage.setOnClickListener(randomImageCB);
        randomImage2.setOnClickListener(randomImageCB);

        Chip chip1 = findViewById(R.id.chip1);
        Chip chip2 = findViewById(R.id.chip2);
        TextView chipTextView = findViewById(R.id.chipTextView);
        final CompoundButton.OnCheckedChangeListener cb =
                (view, state) -> bindChipsToTextView(chipTextView, chip1, chip2);
        chip1.setOnCheckedChangeListener(cb);
        chip2.setOnCheckedChangeListener(cb);
    }
}