package in.ka4tik.ribbit.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import in.ka4tik.ribbit.R;
import in.ka4tik.ribbit.activity.LoginActivity;
import in.ka4tik.ribbit.activity.MainActivity;
import in.ka4tik.ribbit.activity.ViewImageActivity;
import in.ka4tik.ribbit.adapters.MessageAdapter;
import in.ka4tik.ribbit.utils.ParseConstants;


public class InboxFragment extends ListFragment {

    public static final String TAG = ListFragment.class.getSimpleName();
    protected List<ParseObject> mMessages;


    public InboxFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inbox_fragment, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(0);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ParseUser.getCurrentUser() != null) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
            query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
            query.orderByDescending(ParseConstants.KEY_CREATED_AT);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> messages, ParseException e) {

                    if (e == null) {
                        mMessages = messages;
                        if (getListView().getAdapter() == null) {
                            MessageAdapter adapter = new MessageAdapter(
                                    getListView().getContext(),
                                    messages);
                            setListAdapter(adapter);
                        } else {
                            //refill the adapter
                            ((MessageAdapter) getListView().getAdapter()).refill(mMessages);

                        }
                    } else {
                        Log.d(TAG, e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
                        builder.setMessage(e.getMessage())
                                .setTitle("Something wrong happened")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        } else {
            navigateToLogin();
        }


    }

    public void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject message = mMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());

        if (messageType.equals(ParseConstants.TYPE_IMAGE)) {
            //view the image
            Intent intent = new Intent(getActivity(), ViewImageActivity.class);
            intent.setData(fileUri);
            startActivity(intent);
        } else {
            //view the video
            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
            intent.setDataAndType(fileUri, "video/*");
            startActivity(intent);
        }
    }
}
