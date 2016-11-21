package com.hzyanglili1.myxili.mina;

import android.util.Log;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by hzyanglili1 on 2016/11/14.
 */

public class IoListener implements IoServiceListener {

    @Override
    public void serviceActivated(IoService ioService) throws Exception {

        Log.d("haha","serviceActivated");

    }

    @Override
    public void serviceIdle(IoService ioService, IdleStatus idleStatus) throws Exception {

        Log.d("haha","serviceIdle ");
    }

    @Override
    public void serviceDeactivated(IoService ioService) throws Exception {

        Log.d("haha"," serviceDeactivated");
    }

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {

        Log.d("haha"," sessionCreated");
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {

        Log.d("haha","sessionClosed ");
    }

    @Override
    public void sessionDestroyed(IoSession ioSession) throws Exception {

        Log.d("haha","sessionDestroyed ");
    }
}
