package com.kingja.yaluji.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingja.yaluji.R;
import com.kingja.yaluji.base.BaseTitleActivity;
import com.kingja.yaluji.injector.component.AppComponent;
import com.kingja.yaluji.model.entiy.HotSearch;
import com.kingja.yaluji.page.search.result.SearchResultActivity;
import com.kingja.yaluji.util.DialogUtil;
import com.kingja.yaluji.util.LogUtil;
import com.kingja.yaluji.util.SpSir;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:热搜和历史搜索
 * Create Time:2018/7/9 16:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SearchDetailActivity extends BaseTitleActivity implements TextView.OnEditorActionListener,TextWatcher {
    @BindView(R.id.et_search_keyword)
    EditText etSearchKeyword;
    @BindView(R.id.iv_clearInput)
    ImageView ivClearInput;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tfl_hotSearch)
    TagFlowLayout tflHotSearch;
    @BindView(R.id.ll_hotSearch)
    LinearLayout llHotSearch;
    @BindView(R.id.ll_history_clear)
    LinearLayout llHistoryClear;
    @BindView(R.id.tfl_history)
    TagFlowLayout tflHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    private String[] historySearchs;
    private List<HotSearch> hotSearches = new ArrayList<>();


    @Override
    public void initVariable() {


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_detail;
    }


    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected String getContentTitle() {
        return "搜索";
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        initHint();
        initLocalhostData();
        etSearchKeyword.setOnEditorActionListener(this);
        etSearchKeyword.addTextChangedListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void initHint() {
        String historyKeyword = SpSir.getInstance().getHistoryKeyword();
        if (!TextUtils.isEmpty(historyKeyword)) {
            etSearchKeyword.setHint(historyKeyword);
        }
    }

    private void initLocalhostData() {
        String hotSearch = SpSir.getInstance().getHotSearch();
        if (!TextUtils.isEmpty(hotSearch)) {
            llHotSearch.setVisibility(View.VISIBLE);
            hotSearches = new Gson().fromJson(hotSearch, new TypeToken<List<HotSearch>>() {
            }.getType());
            tflHotSearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    doSaveSearch(hotSearches.get(position).getKeyword());
                    return true;
                }
            });
            tflHotSearch.setAdapter(new TagAdapter<HotSearch>(hotSearches) {
                @Override
                public View getView(FlowLayout parent, int position, HotSearch hotSearch) {
                    TextView tv = (TextView) View.inflate(SearchDetailActivity.this, R.layout.single_tag_textview,
                            null);
                    tv.setText(hotSearch.getKeyword());
                    if (hotSearch.getIshighlight() == 1) {
                        tv.setTextColor(ContextCompat.getColor(SearchDetailActivity.this, R.color.orange_hi));
                    } else {
                        tv.setTextColor(ContextCompat.getColor(SearchDetailActivity.this, R.color.c_3));
                    }
                    return tv;
                }
            });
        }
        initHistorySearchStatus();
    }

    private void initHistorySearchStatus() {
        String historySearch = SpSir.getInstance().getHistorySearch();
        if (!TextUtils.isEmpty(historySearch)) {
            llHistory.setVisibility(View.VISIBLE);
            historySearchs = historySearch.split("#");
            tflHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    doSearch(historySearchs[position]);
                    return true;
                }
            });
            tflHistory.setAdapter(new TagAdapter<String>(historySearchs) {
                @Override
                public View getView(FlowLayout parent, int position, String historySearch) {
                    TextView tv = (TextView) View.inflate(SearchDetailActivity.this, R.layout.single_tag_textview,
                            null);
                    tv.setText(historySearch);
                    return tv;
                }
            });
        } else {
            llHistory.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initNet() {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            doSaveSearch(v.getText().toString().trim());
            return true;
        }
        return false;
    }

    private void doSearch(String keyword) {
        etSearchKeyword.setText(keyword);
        etSearchKeyword.setSelection(keyword.length());
        LogUtil.e(TAG,"关键字:"+keyword);
        SearchResultActivity.goActivity(this, keyword);
    }

    private void doSaveSearch(String keyword) {
        SpSir.getInstance().putHistoryKeyword(keyword);
        SpSir.getInstance().addHistorySearch(keyword);
        initHistorySearchStatus();
        doSearch(keyword);
    }


    @OnClick({R.id.ll_history_clear, R.id.tv_search, R.id.iv_clearInput})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_history_clear:
                DialogUtil.showDoubleDialog(this, "确认删除搜索历史?", (dialog, which) -> {
                    SpSir.getInstance().clearHistorySearch();
                    llHistory.setVisibility(View.GONE);
                });
                break;
            case R.id.tv_search:
                String keyword = etSearchKeyword.getText().toString().trim();
                doSaveSearch(keyword);
                break;
            case R.id.iv_clearInput:
                etSearchKeyword.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        ivClearInput.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
    }
}
