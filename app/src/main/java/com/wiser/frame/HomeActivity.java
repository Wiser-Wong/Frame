package com.wiser.frame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.helper.WISERHelper;

import butterknife.BindView;

public class HomeActivity extends WISERActivity<IHomeBiz> {

    private ABean aBean = new ABean();

    @BindView(R.id.tv_json)
    Button tvJson;

    @Override
    protected WISERBuilder build(WISERBuilder builder) {
        builder.layoutId(R.layout.activity_main);
        return builder;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        System.out.println("----tvJson--->>" + tvJson);
        biz().bizMethod("dddd");
    }

    public void parse(View view) {
//		List<ABean> aBeans = new ArrayList<>();
//		for (int i = 0; i < 4; i++) {
//			ABean aBean = new ABean();
//			aBean.age = i + "";
//			aBeans.add(aBean);
//		}
//		Gson gson = new Gson();
//		List<ABean> tBeans = new Utils<ABean>().getDatas(gson.toJson(aBeans), ABean.class);
//		homeMethod("aaa");
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void homeMethod(String s) {

        System.out.println("------biz()---->>" + biz());
        System.out.println("------biz-name---->>" + biz().getClass().getSimpleName());
        System.out.println("------IHomeBiz-name---->>" + IHomeBiz.class.getSimpleName());
        System.out.println("----isExistHome------->>" + WISERHelper.getStructureManage().isExist(IHomeBiz.class));
        System.out.println("-----isExistSecond------->>" + WISERHelper.getStructureManage().isExist(ISecondBiz.class));
        tvJson.setText(s);

    }

}
