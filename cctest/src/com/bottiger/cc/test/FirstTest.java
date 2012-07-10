package com.bottiger.cc.test;

import android.test.ActivityInstrumentationTestCase2;

import com.bottiger.cc.ui.CarCast;
import com.jayway.android.robotium.solo.Solo;

public class FirstTest extends ActivityInstrumentationTestCase2<CarCast> {

	private Solo solo;

	public FirstTest() {
		super("com.bottiger.cc", CarCast.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testPreferenceIsSaved() throws Exception {
		solo.sendKey(Solo.MENU);
		solo.clickOnText("Settings");
		solo.isCheckBoxChecked(1);// wifi checkbox
	}


}
