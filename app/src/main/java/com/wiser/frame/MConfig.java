package com.wiser.frame;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wiser.library.annotation.property.Property;
import com.wiser.library.config.property.WISERProperties;

public class MConfig extends WISERProperties {

	public MConfig(@NonNull Context context) {
		super(context);
	}

	@Override public int initType() {
		return WISERProperties.OPEN_TYPE_DATA;
	}

	@Property("name") public String name;
}
