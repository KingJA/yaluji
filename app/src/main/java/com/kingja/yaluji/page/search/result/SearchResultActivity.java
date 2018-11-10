package com.kingja.yaluji.page.search.result;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.yaluji.R;
import com.kingja.yaluji.adapter.CommonPageAdapter;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.constant.Constants;
import com.kingja.yaluji.i.OnSearchListener;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.page.search.article.ArticleListSearchFragment;
import com.kingja.yaluji.page.search.question.search.QuestionListSearchFragment;
import com.kingja.yaluji.page.search.ticket.TicketListSearchFragment;
import com.kingja.yaluji.util.SimpleTextWatcher;
import com.kingja.yaluji.util.SpSir;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:TODO
 * Create Time:2018/11/4 15:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SearchResultActivity extends BaseTitleActivity {
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    private String[] tabNames = {"鹿券", "美文", "鸡答"};
    private Fragment[] fragments = new Fragment[3];
    private String keyword;

    @OnClick({R.id.tv_search, R.id.iv_clear})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String keyword = etKeyword.getText().toString().trim();
                doSearch(keyword);
                break;
            case R.id.iv_clear:
                etKeyword.setText("");
                break;
            default:
                break;
        }
    }
    private Handler netHandler=new Handler();

    private void doSearch(String keyword) {
        SpSir.getInstance().putHistoryKeyword(keyword);
        SpSir.getInstance().addHistorySearch(keyword);
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] instanceof OnSearchListener) {
                int finalI = i;
                netHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((OnSearchListener) fragments[finalI]).search(keyword);
                    }
                },200);

            }
        }
    }

    @Override
    public void initVariable() {
        keyword = getIntent().getStringExtra(Constants.Extra.Keyword);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
    }

    @Override
    protected String getContentTitle() {
        return "";
    }

    @Override
    protected void initView() {
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.addTab(tab.newTab().setText(tabNames[0]));
        tab.addTab(tab.newTab().setText(tabNames[1]));
        tab.addTab(tab.newTab().setText(tabNames[2]));
        fragments[0] = TicketListSearchFragment.newInstance(keyword);
        fragments[1] = ArticleListSearchFragment.newInstance(keyword);
        fragments[2] = QuestionListSearchFragment.newInstance(keyword);
        CommonPageAdapter commonPageAdapter = new CommonPageAdapter(getSupportFragmentManager(), fragments,
                tabNames);
        vp.setAdapter(commonPageAdapter);
        vp.setOffscreenPageLimit(tabNames.length);
        tab.setupWithViewPager(vp);
    }

    @Override
    protected void initData() {
        etKeyword.setText(keyword);
        etKeyword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                ivClear.setVisibility(editable.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void initNet() {

    }

    public static void goActivity(Context context, String keyword) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(Constants.Extra.Keyword, keyword);
        context.startActivity(intent);
    }

}
