package oysd.com.trade_app.modules.trade.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import oysd.com.trade_app.modules.trade.view.fragment.KLine15Fragment;
import oysd.com.trade_app.modules.trade.view.fragment.KLine30Fragment;
import oysd.com.trade_app.modules.trade.view.fragment.KLine4hFragment;
import oysd.com.trade_app.modules.trade.view.fragment.KLine5Fragment;
import oysd.com.trade_app.modules.trade.view.fragment.KLine60Fragment;
import oysd.com.trade_app.modules.trade.view.fragment.KLineDayFragment;
import oysd.com.trade_app.modules.trade.view.fragment.KLineWeekFragment;
import oysd.com.trade_app.modules.trade.view.fragment.MinuteChartFragment;

public class FragmentAdapter_text extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public FragmentAdapter_text(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    MinuteChartFragment min = new MinuteChartFragment();
                    return min;
                case 1:
                    KLine4hFragment Line4h = new KLine4hFragment();
                    return Line4h;
                case 2:
                    KLine5Fragment line5 =new KLine5Fragment();
                    return line5;

                case 3:
                    KLine15Fragment line15 =new KLine15Fragment();
                    return line15;

                case 4:
                    KLine30Fragment line30 =new KLine30Fragment();
                    return line30;
                case 5:
                    KLine60Fragment line60 =new KLine60Fragment();
                    return line60;

                case 6:
                    KLineDayFragment lineDay =new KLineDayFragment();
                    return lineDay;

                case 7:
                    KLineWeekFragment lineWeek =new KLineWeekFragment();
                    return lineWeek;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
}

