package htoyama.timetable.presentation.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import htoyama.timetable.R;
import htoyama.timetable.domain.repository.tasks.TimetableRegisterTask;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.utils.StringUtils;

public class InputActivity extends BaseActivity {
    private static final String TAG = "InputActivity";
    private static final int REQUEST_CODE_FIND_TIMETABLE_TXT = 1;

    private String mTimetableFilePath;

    @InjectView(R.id.input_train)
    EditText mTrainEditText;

    @InjectView(R.id.input_station)
    EditText mStationEditText;

    @InjectView(R.id.input_bound_for_name)
    EditText mBoundForNameEditText;

    @InjectView(R.id.input_part_type)
    Spinner mPartTypeSpinner;

    @InjectView(R.id.input_day_type)
    Spinner mDayTypeSpinner;

    @InjectView(R.id.input_timetable_file_name)
    TextView mTimetableFileNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.inject(this);

        setupPartTypeSpinner();
        setupDayTypeSpinner();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onsaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onResotreInstanceState");
    }

    @Override
    protected void onDestroy() {
        BusHolder.getBus().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CODE_FIND_TIMETABLE_TXT) {
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }

        Uri uri = data.getData();
        mTimetableFilePath = uri.toString();
        String filename = new File(mTimetableFilePath).getName();

        mTimetableFileNameTextView.setText(filename);
        mTimetableFileNameTextView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.input_timetable_file_area)
    public void onClickTimetableFileArea() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        startActivityForResult(intent, REQUEST_CODE_FIND_TIMETABLE_TXT);
    }

    @OnClick(R.id.input_submit)
    public void onClickSubmit() {
        boolean valStation = validateStationText();
        boolean valTrain = validateTrainText();
        boolean valBound = validateBoundForName();
        boolean valTimetableText = validateTimetableTextfile();

        if (!valStation || !valTrain || !valBound || !valTimetableText) {
           return;
        }

        saveInputedData();
    }

    private void saveInputedData() {
        final String stationText = mStationEditText.getText().toString();
        final String trainText = mTrainEditText.getText().toString();
        final String boundForNameText = mBoundForNameEditText.getText().toString();
        final int partTypeId = mPartTypeSpinner.getSelectedItemPosition();
        final int dayTypeId = mDayTypeSpinner.getSelectedItemPosition();

        BaseInfo baseInfo = new BaseInfo(stationText, trainText, boundForNameText, PartType.valueOf(partTypeId));
        String path = StringUtils.trimFileSchemeIfNeeded(mTimetableFilePath);

        new TimetableRegisterTask(getApplicationContext())
                .register(baseInfo, dayTypeId, path);
    }

    private boolean validateStationText() {
        String text = mStationEditText.getText().toString();
        String trimedText = StringUtils.trimSpacing(text);

        if (TextUtils.isEmpty(trimedText)) {
            Toast.makeText(this, "駅名に文字を入力してください", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!text.equals(trimedText)) {
            Toast.makeText(this, "駅名に空白を含めないでください", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateTrainText() {
        String text = mTrainEditText.getText().toString();
        String trimedText = StringUtils.trimSpacing(text);

        if (TextUtils.isEmpty(trimedText)) {
            Toast.makeText(this, "電車に文字を入力してください", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!text.equals(trimedText)) {
            Toast.makeText(this, "電車に空白を含めないでください", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateBoundForName() {
        String text = mBoundForNameEditText.getText().toString();
        String trimedText = StringUtils.trimSpacing(text);

        if (TextUtils.isEmpty(trimedText)) {
            Toast.makeText(this, "行き先にに文字を入力してください", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!text.equals(trimedText)) {
            Toast.makeText(this, "行き先に空白を含めないでください", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateTimetableTextfile() {
        if (mTimetableFilePath == null) {
            Toast.makeText(this, "ファイルを選んでください", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateAllInputs() {
        return  validateStationText()
                && validateTrainText()
                && validateBoundForName()
                && validateTimetableTextfile();
    }

    private void setupDayTypeSpinner() {
        List<String> arr = new ArrayList<>();
        for (DayType dayType : DayType.values() ) {
            arr.add(dayType.id, dayType.name);
        }

        String[] s = arr.toArray(new String[0]);
        setSpinner(mDayTypeSpinner, s);
    }

    private void setupPartTypeSpinner() {
        final int size = PartType.values().length;
        String[] arr = new String[size];

        for (PartType partType : PartType.values() ) {
            arr[partType.id] = partType.name;
        }

        setSpinner(mPartTypeSpinner, arr);
    }

    private void setSpinner(Spinner spinner, String[] arr) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
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
