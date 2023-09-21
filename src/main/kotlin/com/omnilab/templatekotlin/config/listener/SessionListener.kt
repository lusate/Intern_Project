package com.omnilab.templatekotlin.config.listener

import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

class SessionListener: HttpSessionListener {

    // 세션이 사라질 때마다 호출된다.
    override fun sessionDestroyed(se: HttpSessionEvent?) {
        super.sessionDestroyed(se)
    }

    // 메서드가 호출되는 시점은 session이 생성되는 시점.
    override fun sessionCreated(se: HttpSessionEvent?) {
        super.sessionCreated(se)
    }

}


// SessionListener : 세션이 생성되거나 사라질 때, 알림을 받는 것.
// 중복 로그인