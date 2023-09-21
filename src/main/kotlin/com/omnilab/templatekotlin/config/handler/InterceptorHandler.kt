package com.omnilab.templatekotlin.config.handler

import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

class InterceptorHandler: HandlerInterceptor {

    override fun preHandle(req: HttpServletRequest, rep: HttpServletResponse, handler: Any): Boolean {
        req.characterEncoding = "UTF-8"
        rep.contentType = "text/html; charset=UTF-8"
        return super.preHandle(req, rep, handler)
    }

    override fun postHandle(req: HttpServletRequest, rep: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(req, rep, handler, modelAndView)
    }

    override fun afterCompletion(req: HttpServletRequest, rep: HttpServletResponse, handler: Any, ex: Exception?) {
        super.afterCompletion(req, rep, handler, ex)
    }

}



/**
 * URI 요청, 응답 시점을 가로채서 전/후 처리를 하는 역할을 합니다. Interceptor 시점에 Spring Context와 Bean에 접근할 수 있다.
 *
 * 1. PreHandle
 * 컨트롤러에 진입하기 전(핸들러가 실행되기 전)에 실행됩니다. 반환 값이 true일 경우 컨트롤러로 진입하고 false일 경우 진입하지 않습니다.
 * Object handler는 진입하려는 컨트롤러의 클래스 객체가 담겨있습니다.
 * 사용자의 로그인 정보들이 비정상이라 판별되면 return false해서 중단시킬 수 있다.
 *
 * 2. afterCompletion
 * 컨트롤러 진입 후 view가 랜더링 된 후에 실행되는 메소드입니다. 뷰가 클라이언트에 응답을 전송한 뒤에 실행된다.
 *
 * 3. afterConcurrentHandlingStarted
 * 비동기 요청 시 PostHandle과 afterCompletion이 수행되지 않고 afterConcurrentHandlingStarted가 수행됩니다.
 *
 * 4. PostHandle
 * 컨트롤러 진입 후  View가 랜더링 되기 전에 수행됩니다.
 */

/*
인터셉터
여러 컨트롤러에서 같은 관심사를 갖고 반복되어 사용하는 코드를 제거하고,
다수의 컨트롤러에 동일한 기능을 제공하기 위해 사용하는 것이 인터셉터이다.
인터셉터는 컨트롤러 로직이 실행되기 전에 실행된다.
 */