package com.bottiger.cc.ui;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.bottiger.cc.core.CarCastApplication;
import com.bottiger.cc.core.ContentServiceListener;
import com.bottiger.cc.core.Subscription;
import com.bottiger.cc.services.ContentService;
import com.bottiger.cc.services.PlayStatusListener;


public abstract class BaseFragmentActivity extends FragmentActivity implements ContentServiceListener, PlayStatusListener {
	ContentService contentService;

	public ContentService getContentService() {
		return contentService;
	}

	protected List<Subscription> getSubscriptions() {
		return contentService.getSubscriptions();
	}

	protected void onContentService() { // TODO rename
	    // does nothing by default
	}

	@Override
	public void onContentServiceChanged(ContentService service) {
		if (contentService != null) {
			contentService.setPlayStatusListener(null);
		}
	    contentService = service;
	    if (service != null) {
	    	service.setPlayStatusListener(this);
            onContentService();
        }
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    getCarCastApplication().setContentServiceListener(this);
	}

    protected CarCastApplication getCarCastApplication() {
        return ((CarCastApplication)getApplication());
    }

	@Override
	public void playStateUpdated(boolean playing) {
		// default implementation does nothing
	}
}
