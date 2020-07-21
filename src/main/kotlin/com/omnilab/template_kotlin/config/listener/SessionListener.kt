package com.omnilab.template_kotlin.config.listener

import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

class SessionListener: HttpSessionListener {

    override fun sessionDestroyed(se: HttpSessionEvent?) {
        super.sessionDestroyed(se)
    }

    override fun sessionCreated(se: HttpSessionEvent?) {
        super.sessionCreated(se)
    }

}