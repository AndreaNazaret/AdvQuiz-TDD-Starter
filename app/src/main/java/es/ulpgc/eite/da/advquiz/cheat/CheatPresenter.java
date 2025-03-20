package es.ulpgc.eite.da.advquiz.cheat;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advquiz.app.AppMediator;
import es.ulpgc.eite.da.advquiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.advquiz.app.QuestionToCheatState;


public class CheatPresenter implements CheatContract.Presenter {

  public static String TAG = "AdvQuiz.CheatPresenter";

  private AppMediator mediator;
  private WeakReference<CheatContract.View> view;
  private CheatState state;
  private CheatContract.Model model;

  public CheatPresenter(AppMediator mediator) {
    this.mediator = mediator;
  }


  @Override
  public void onCreateCalled() {
    Log.e(TAG, "onCreateCalled");

    // TODO: include code if necessary
    // Inicializar el estado

    initializeState();

    // Recuperar el estado guardado de la pantalla anterior
    restoreStateFromPreviousScreen();

  }
  private void initializeState() {
    state = new CheatState();
    state.answer = model.getAnswerEmptyText();
    state.buttonEnabled=true;

  }

  private void restoreStateFromPreviousScreen() {
    QuestionToCheatState savedState = mediator.getQuestionToCheatState();
    if (savedState != null) {
      state.answer = savedState.answer;
      model.setCorrectAnswer(savedState.answer);
    }
  }

  @Override
  public void onRecreateCalled() {
    Log.e(TAG, "onRecreateCalled");

    // TODO: include code if necessary
    state = mediator.getCheatState();
  }

  @Override
  public void onResumeCalled() {
    Log.e(TAG, "onResumeCalled");

    // TODO: include code if necessary

    // update the view
    view.get().displayAnswerData(state);

  }

  @Override
  public void onPauseCalled() {
    Log.e(TAG, "onPauseCalled");

    // TODO: include code if necessary
    mediator.setCheatState(state);
  }

  @Override
  public void onDestroyCalled() {
    Log.e(TAG, "onDestroyCalled");

  }

  @Override
  public void onBackButtonPressed() {
    Log.e(TAG, "onBackButtonPressed");

    // TODO: include code if necessary

    CheatToQuestionState newState = new CheatToQuestionState(true);
    mediator.setCheatToQuestionState(newState);
    view.get().finishView();
  }

  @Override
  public void onWarningButtonClicked(int option) {
    Log.e(TAG, "onWarningButtonClicked");

    // TODO: include code if necessary

    if(option==1){
      Log.e(TAG, "onYesButtenCliked");
      CheatToQuestionState newState = new CheatToQuestionState(true);
      mediator.setCheatToQuestionState(newState);

      state.buttonEnabled=false;

      String correctAnswer = model.getCorrectAnswer();
      Log.e(TAG, "Correct Answer from Model: " + correctAnswer);

      state.answer=correctAnswer;
      if (view.get() != null) {
        CheatViewModel viewModel = new CheatViewModel();
        viewModel.answer = state.answer;
        viewModel.buttonEnabled = state.buttonEnabled;

        Log.e(TAG, "Enviando a la vista: " + viewModel.answer);
        view.get().displayAnswerData(viewModel);
      } else {
        Log.e(TAG, "ERROR: La vista no está disponible en onWarningButtonClicked");
      }


    }else{
      Log.e(TAG, "onNoButtenCliked");
      CheatToQuestionState newState = new CheatToQuestionState(false);
      mediator.setCheatToQuestionState(newState);

      view.get().finishView();
    }
  }


  @Override
  public void injectView(WeakReference<CheatContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CheatContract.Model model) {
    this.model = model;
  }

}
