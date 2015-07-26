package in.ka4tik.ribbit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ViewImageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Uri imageUri = getIntent().getData();
        Picasso.with(this).load(imageUri.toString()).into(imageView);

    }
}
