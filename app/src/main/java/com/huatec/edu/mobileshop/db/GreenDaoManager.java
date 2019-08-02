package com.huatec.edu.mobileshop.db;

import com.huatec.edu.mobileshop.common.MyApplication;
import com.huatec.edu.mobileshop.gen.DaoMaster;
import com.huatec.edu.mobileshop.gen.DaoSession;

/**
 * Created by Administrator on 2016/8/31.
 */
public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private static DaoMaster mDaoMaster; //以一定的模式管理Dao类的数据库对象
    private static DaoSession mDaoSession; //管理制定模式下的所有可用Dao对象


    public GreenDaoManager() {
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "mydb",
                    null);
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public static DaoMaster getMaster() {
        return mDaoMaster;
    }

    public static DaoSession getSession() {
        return mDaoSession;
    }

    public static DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
