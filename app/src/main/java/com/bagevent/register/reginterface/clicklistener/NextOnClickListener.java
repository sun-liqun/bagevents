package com.bagevent.register.reginterface.clicklistener;

/**
 * Created by zwj on 2016/5/27.
 *
 * 执行下一步
 */
public interface NextOnClickListener {
    void checkSuccess();
    void checkFail(String errInfo);
    void checkIsExist(String errInfo);
}
