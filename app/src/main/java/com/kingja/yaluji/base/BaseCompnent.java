package com.kingja.yaluji.base;


import com.kingja.yaluji.page.praise.detail.PraiseDetailActivity;
import com.kingja.yaluji.page.praise.list.PraiseListActivity;
import com.kingja.yaluji.page.praise.list.PraiseListSearchFragment;
import com.kingja.yaluji.page.ticket.confirm.TicketConfirmActivity;
import com.kingja.yaluji.injector.annotation.PerActivity;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.injector.module.ActivityModule;
import com.kingja.yaluji.page.answer.detail.QuestionDetailActivity;
import com.kingja.yaluji.page.answer.success.AnswerSuccessActivity;
import com.kingja.yaluji.page.article.detail.ArticleDetailActivity;
import com.kingja.yaluji.page.article.list.ArticleListActivity;
import com.kingja.yaluji.page.business.BusinessDetailActivity;
import com.kingja.yaluji.page.feedback.FeedbackActivity;
import com.kingja.yaluji.page.forgetpassword.ForgetPasswordActivity;
import com.kingja.yaluji.page.headimg.PersonalActivity;
import com.kingja.yaluji.page.home.HomeFragment;
import com.kingja.yaluji.page.introduce.SceneryIntroduceActivity;
import com.kingja.yaluji.page.login.LoginActivity;
import com.kingja.yaluji.page.message.MsgActivity;
import com.kingja.yaluji.page.modifynickname.ModifyNicknameActivity;
import com.kingja.yaluji.page.modifypassword.ModifyPasswordActivity;
import com.kingja.yaluji.page.order.list.OrderListFragment;
import com.kingja.yaluji.page.order.orderdetail.OrderDetailActivity;
import com.kingja.yaluji.page.register.RegisterActivity;
import com.kingja.yaluji.page.search.article.ArticleListSearchFragment;
import com.kingja.yaluji.page.search.question.list.QuestionListActivity;
import com.kingja.yaluji.page.search.question.search.QuestionListSearchFragment;
import com.kingja.yaluji.page.search.result.SearchResultActivity;
import com.kingja.yaluji.page.search.ticket.TicketListSearchFragment;
import com.kingja.yaluji.page.ticket.detail.TicketDetailActivity;
import com.kingja.yaluji.page.ticket.list.TicketListActivity;
import com.kingja.yaluji.page.ticket.list.TicketListFragment;
import com.kingja.yaluji.page.ticket.success.TicketSuccessActivity;
import com.kingja.yaluji.page.visitor.add.VisitorAddActivity;
import com.kingja.yaluji.page.visitor.edit.VisitorEditActivity;
import com.kingja.yaluji.page.visitor.list.VisitorListActivity;
import com.kingja.yaluji.page.visitor.prefect.VisitorPrefectActivity;
import com.kingja.yaluji.service.initialize.InitializeService;
import com.kingja.yaluji.update.VersionUpdateSir;
import com.kingja.yaluji.view.dialog.QuestionSuccessDialog;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface BaseCompnent {
    void inject(PraiseListSearchFragment target);

    void inject(PraiseDetailActivity target);

    void inject(PraiseListActivity target);

    void inject(TicketListFragment target);

    void inject(VersionUpdateSir target);

    void inject(OrderListFragment target);

    void inject(OrderDetailActivity target);

    void inject(LoginActivity target);

    void inject(RegisterActivity target);

    void inject(ForgetPasswordActivity target);

    void inject(PersonalActivity target);

    void inject(ModifyNicknameActivity target);

    void inject(HomeFragment target);

    void inject(ModifyPasswordActivity target);

    void inject(VisitorAddActivity target);

    void inject(VisitorEditActivity target);

    void inject(VisitorListActivity target);

    void inject(VisitorPrefectActivity target);

    void inject(ArticleListActivity target);

    void inject(TicketListActivity target);

    void inject(TicketDetailActivity target);

    void inject(ArticleDetailActivity target);

    void inject(SearchResultActivity target);

    void inject(TicketListSearchFragment target);

    void inject(ArticleListSearchFragment target);

    void inject(QuestionListSearchFragment target);

    void inject(InitializeService target);

    void inject(QuestionListActivity target);

    void inject(QuestionDetailActivity target);

    void inject(TicketSuccessActivity target);

    void inject(FeedbackActivity target);

    void inject(BusinessDetailActivity target);

    void inject(MsgActivity target);

    void inject(QuestionSuccessDialog target);

    void inject(AnswerSuccessActivity target);

    void inject(SceneryIntroduceActivity target);

    void inject(TicketConfirmActivity target);

}
