package com.xdcplus.mp.controller;

import com.xdcplus.mp.utils.AuthUtils;

/**
 * 抽象 Controller层
 * @author Rong.Jia
 * @date 2019/04/16 10:58
 */
public abstract class AbstractController {

    protected String getAccount() {
        return AuthUtils.getCurrentUser();
    }


}
