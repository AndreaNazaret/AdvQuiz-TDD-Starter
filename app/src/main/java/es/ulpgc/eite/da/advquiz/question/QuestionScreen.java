package es.ulpgc.eite.da.advquiz.question;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advquiz.R;
import es.ulpgc.eite.da.advquiz.app.AppMediator;


public class QuestionScreen {

  public static void configure(QuestionContract.View view) {

    WeakReference<FragmentActivity> context = new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = AppMediator.getInstance();
    QuestionContract.Presenter presenter = new QuestionPresenter(mediator);

    String[] quizArray = context.get().getResources().getStringArray(R.array.quiz_array);
    QuestionContract.Model model = new QuestionModel(quizArray);
    model.setCorrectResultText( context.get().getString(R.string.correct_result));
    model.setIncorrectResultText( context.get().getString(R.string.incorrect_result));
    model.setEmptyResultText( context.get().getString(R.string.empty_result));

    presenter.injectModel(model);
    presenter.injectView(new WeakReference<>(view));
    view.injectPresenter(presenter);

  }
}
