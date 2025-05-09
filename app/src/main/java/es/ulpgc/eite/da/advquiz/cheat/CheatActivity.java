package es.ulpgc.eite.da.advquiz.cheat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.da.advquiz.R;



public class CheatActivity
    extends AppCompatActivity implements CheatContract.View {

  public static String TAG = "AdvQuiz.CheatActivity";

  private CheatContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    setTitle(R.string.cheat_screen_title);

    ((TextView) findViewById(R.id.noButton)).setText(R.string.no_button);
    ((TextView) findViewById(R.id.yesButton)).setText(R.string.yes_button);
    ((TextView) findViewById(R.id.warningTextView)).setText(R.string.warning_message);



    // do the setup
    CheatScreen.configure(this);

    if(savedInstanceState == null) {
      presenter.onCreateCalled();

    } else {
      presenter.onRecreateCalled();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    // load the answer
    presenter.onResumeCalled();
  }


  @Override
  protected void onPause() {
    super.onPause();

    presenter.onPauseCalled();
  }

  @SuppressLint("MissingSuperCall")
  @Override
  public void onBackPressed() {
    //super.onBackPressed();

    presenter.onBackButtonPressed();
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();

    presenter.onDestroyCalled();
  }


  @Override
  public void displayAnswerData(CheatViewModel viewModel) {
    Log.e(TAG, "displayAnswerData() llamado");
    Log.e(TAG, "Respuesta a mostrar: " + viewModel.answer);



    TextView answerTextView = findViewById(R.id.answerTextView);
    answerTextView.setText(viewModel.answer);
/*
    if (answerTextView != null) {
      answerTextView.setText(viewModel.answer);
      Log.e(TAG, "Texto actualizado en answerTextView");
    } else {
      Log.e(TAG, "ERROR: answerTextView es NULL");
    }
*/
    findViewById(R.id.noButton).setEnabled(viewModel.buttonEnabled);
    findViewById(R.id.yesButton).setEnabled(viewModel.buttonEnabled);
  }



  public void onWarningButtonClicked(View view) {

    int option = Integer.valueOf((String) view.getTag());
    presenter.onWarningButtonClicked(option);
  }


  @Override
  public void finishView() {
    finish();
  }


  @Override
  public void injectPresenter(CheatContract.Presenter presenter) {
    this.presenter = presenter;
  }

}
