package in.ka4tik.ribbit;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class LoginActivity extends ActionBarActivity {

    protected TextView mSignUpTextView;
    private EditText mUserName;
    private EditText mPassword;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);

        mSignUpTextView = (TextView)findViewById(R.id.signup);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        mUserName = (EditText)findViewById(R.id.username);
        mPassword = (EditText)findViewById(R.id.password);
        mLoginButton=(Button)findViewById(R.id.login);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUserName.getText().toString();
                String password = mPassword.getText().toString();

                username = username.trim();
                password = password.trim();

                if(username.isEmpty() ||  password.isEmpty() )
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Please make sure you enter a username and password")
                            .setTitle("Error")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else
                {
                    //Login
                    setSupportProgressBarIndeterminateVisibility(true);

                    ParseUser.logInInBackground(username,password,new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {

                            setSupportProgressBarIndeterminateVisibility(false);

                            if(e==null)//success
                            {
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                //makes user don't go back to signup page by pressing back button
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                            else
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle("Error")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
