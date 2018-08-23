package com.wiser.library.pager.banner;

import java.util.List;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * @author Wiser banner
 */
@SuppressWarnings("unchecked")
public class BannerManage {

	public static <T> void setBanner(ConvenientBanner banner, List<T> list, final Holder holder) {
		banner.setPages(new CBViewHolderCreator() {

			@Override public Object createHolder() {
				return holder;
			}
		}, list).setPointViewVisible(false).startTurning(4000).setManualPageable(true);
	}

}
