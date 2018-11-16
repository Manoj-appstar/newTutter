package com.appstar.tutionportal.Model;

import android.content.Intent;

public interface MessageListener {
    public void onMessageAdded(Intent intent);

    public void onErrorMessage();
}
