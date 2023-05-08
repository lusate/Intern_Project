package com.omnilab.templatekotlin.model

import org.apache.commons.lang3.builder.EqualsBuilder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


/**
 * <pre>
 * com.template.model
 * |_ EmployeeDto.java
</pre> *
 *
 * @author : OMNILAB-A_zd
 * @version :
 * @date : 2017. 5. 29. 오후 2:45:40
 */
class UserDetailDto : UserDetails {
    var rid = 0
    var emp_id: String? = null
    var pw: String? = null
    var name: String? = null
    var sub_name: String? = null
    var email: String? = null
    var h_phone: String? = null
    var o_phone: String? = null
    var dept_id: String? = null
    var position: String? = null
    var tm_manager: String? = null
    var level = 0
    var state = 0
    var office_hour: String? = null
    var join_date: Date? = null
    var resignation_date: Date? = null
    var role: String? = null
    var dept_nm: String? = null
    var posi_nm: String? = null
    private var username: String? = null
    private var password: String? = null
    private var authorities: Collection<GrantedAuthority?>? = null
    var login_dt: Date? = null
    var login_result = 0
    var ip: String? = null

    fun setAuthorities(authorities: Collection<GrantedAuthority?>?) {
        this.authorities = authorities
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return authorities!!
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return name!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(emp_id)
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj !is UserDetailDto) return false
        val that: UserDetailDto = obj
        return EqualsBuilder().append(emp_id, that.emp_id)
            .append(email, that.email)
            .append(username, that.username)
            .append(password, that.password)
            .append(authorities, that.authorities)
            .isEquals
    }

}