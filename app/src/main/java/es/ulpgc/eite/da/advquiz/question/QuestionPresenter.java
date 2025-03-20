package es.ulpgc.eite.da.advquiz.question;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advquiz.app.AppMediator;
import es.ulpgc.eite.da.advquiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.advquiz.app.QuestionToCheatState;

public class QuestionPresenter implements QuestionContract.Presenter {

  public static String TAG = "AdvQuiz.QuestionPresenter";

  private AppMediator mediator;
  private WeakReference<QuestionContract.View> view;
  private QuestionState state;
  private QuestionContract.Model model;

  public QuestionPresenter(AppMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onCreatedCalled() {
    Log.e(TAG, "onCreatedCalled");

    // init the state
    state = new QuestionState();

    // TODO: include code if necessary
    state.result= model.getEmptyResultText();
    state.question=model.getQuestion();
    state.option1= model.getOption1();
    state.option2= model.getOption2();
    state.option3= model.getOption3();

    enableAnswerButtons();
  }

  @Override
  public void onRecreatedCalled() {
    Log.e(TAG, "onRecreatedCalled");

    // TODO: include code if necessary
    state = mediator.getQuestionState();
    model.setQuizIndex(state.quizIndex);
  }


  @Override
  public void onResumeCalled() {
    Log.e(TAG, "onResumeCalled");

    // TODO: include code if necessary
    CheatToQuestionState savedState = mediator.getCheatToQuestionState();
    if (savedState != null) {
      if (savedState.cheated) { //si el estado que se pasa es true porque se ha visto la respuesta
          disableAnswerButtons();
          state.result=model.getIncorrectResultText();
          view.get().displayQuestionData(state);
          return;
      }
    }
    // update the view
    view.get().displayQuestionData(state);
  }


  @Override
  public void onPauseCalled() {
    Log.e(TAG, "onPauseCalled");

    // TODO: include code if necessary
    mediator.setQuestionState(state);

  }

  @Override
  public void onDestroyCalled() {
    Log.e(TAG, "onDestroyCalled");
    // Reset current state
    //mediator.resetCheatState();
  }

  @Override
  public void onOptionButtonClicked(int option) {
    Log.e(TAG, "onOptionButtonClicked");

    // TODO: include code if necessary
    boolean answer = model.isCorrectOption(option);

    Log.e(TAG, "Respuesta correcta: " + model.getCorrectAnswer());
    Log.e(TAG, "Opción seleccionada: " + option);

    if(answer){
      state.result=model.getCorrectResultText();
      disableAnswerButtons();
      state.cheatEnabled=false;
    }else{
      state.result=model.getIncorrectResultText();
      disableAnswerButtons();
      state.cheatEnabled=true;
    }
    view.get().displayQuestionData(state);
  }

  @Override
  public void onNextButtonClicked() {
    Log.e(TAG, "onNextButtonClicked");

    // TODO: include code if necessary

    model.incrQuizIndex();
    state.quizIndex= model.getQuizIndex();

    state.question= model.getQuestion();
    state.option1= model.getOption1();
    state.option2= model.getOption2();
    state.option3= model.getOption3();
    state.result=model.getEmptyResultText();

    enableAnswerButtons();
    state.nextEnabled=false;

    view.get().displayQuestionData(state);

  }

  @Override
  public void onCheatButtonClicked() {
    Log.e(TAG, "onCheatButtonClicked");

    // TODO: include code if necessary
    String correctAnswer= model.getCorrectAnswer();
    QuestionToCheatState newState = new QuestionToCheatState(correctAnswer);
    mediator.setQuestionToCheatState(newState);

    view.get().navigateToCheatScreen();
  }

  @Override
  public void disableAnswerButtons() {
    Log.e(TAG, "disableAnswerButtons");

    // TODO: include code if necessary
    state.optionEnabled=false;
    state.nextEnabled=true;

  }


  @Override
  public void enableAnswerButtons() {
    Log.e(TAG, "enableAnswerButtons");

    // TODO: include code if necessary

    state.optionEnabled=true;
    state.nextEnabled=false;
    state.cheatEnabled=true;
  }


  @Override
  public void injectView(WeakReference<QuestionContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(QuestionContract.Model model) {
    this.model = model;
  }

}
