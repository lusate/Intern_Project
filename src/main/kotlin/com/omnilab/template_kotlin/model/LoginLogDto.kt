/**
 *
 */
package com.omnilab.template_kotlin.model

import java.io.Serializable
import java.util.*

/**
 * <pre>
 * com.template.model
 * |_ LoginLogDto.java
</pre> *
 * @date : 2017. 6. 9. 오전 10:18:30
 * @version :
 * @author : OMNILAB-A
 */
/**
 * @author OMNILAB-A
 */
class LoginLogDto : Serializable {
    var rid = 0
    var emp_id: String? = null
    var login_dt: Date? = null
    var login_result = 0
    var ip: String? = null
    var ito1: String? = null
    var ito2: String? = null
    var ito3: String? = null
    var dept_id: String? = null
    var position: String? = null
    var posi_nm: String? = null
    var tm_manager: String? = null
    var level = 0
    fun setLeve(level: Int) {
        this.level = level
    }
}