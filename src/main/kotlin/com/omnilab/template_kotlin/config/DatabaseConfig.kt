package com.omnilab.template_kotlin.config

import java.util.ArrayList
import java.util.Properties

import org.apache.commons.dbcp2.BasicDataSource
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory

import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.aop.Advisor
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import org.springframework.transaction.interceptor.RollbackRuleAttribute
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute
import org.springframework.transaction.interceptor.TransactionInterceptor

import net.sf.log4jdbc.Log4jdbcProxyDataSource
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter
import net.sf.log4jdbc.tools.LoggingType
import org.slf4j.LoggerFactory
import org.springframework.jdbc.BadSqlGrammarException
import java.sql.SQLException

@Configuration
@EnableTransactionManagement
internal class DatabaseConfig {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Value("\${variable.db-class}")
    private val db_class: String? = null

    @Value("\${variable.db-url}")
    private val db_url: String? = null

    @Value("\${variable.db-username}")
    private val db_username: String? = null

    @Value("\${variable.db-password}")
    private val db_password: String? = null

    @Bean(destroyMethod = "postDeregister")
    fun configureDataSource(): BasicDataSource {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = db_class
        dataSource.url = db_url
        dataSource.username = db_username
        dataSource.password = db_password
        dataSource.initialSize = 2 // 최초 커넥션 풀에 채원 넣을 커넥션 수
        dataSource.maxTotal = 50
        dataSource.maxIdle = 20 // 커넥션 풀에 반납할 때 최대로 유지될 수 있는 커넥션 수
        dataSource.validationQuery = "select 1"
        dataSource.testWhileIdle = true // 커넥션 유효성 검사를 idle 상태에 존재 할 때 실시 할것인지 여부
        dataSource.testOnBorrow = false // 커넥션 풀에서 커넥션을 얻어올 때 테스트 실행
        dataSource.testOnReturn = true // 커넥션 풀로 커넥션을 반환할 때 테스트 실행
        dataSource.defaultAutoCommit = true // 자동 커밋 옵션
        dataSource.timeBetweenEvictionRunsMillis = 60000 // Evictor 스레드가 동작하는 간격
        dataSource.minEvictableIdleTimeMillis = 3600000 // Evictor 스레드 동작 시 커넥션의 유휴 시간을 확인해 설정 값 이상일 경우 커넥션을 제거한다.
        return dataSource
    }

    @Bean
    fun dataSource(): Log4jdbcProxyDataSource {
        val formatter = Log4JdbcCustomFormatter()
        formatter.loggingType = LoggingType.MULTI_LINE
        formatter.sqlPrefix = "SQL:::"
        val dataSources = Log4jdbcProxyDataSource(configureDataSource())
        dataSources.logFormatter = formatter
        return dataSources
    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val transactionManager = DataSourceTransactionManager(dataSource())
        transactionManager.isGlobalRollbackOnParticipationFailure = false
        return transactionManager
    }

    @Bean
    fun txAdvice(): TransactionInterceptor {
        val txAdvice = TransactionInterceptor()
        val txAttributes = Properties()

        val rollbackRules = ArrayList<RollbackRuleAttribute>()
        rollbackRules.add(RollbackRuleAttribute(java.lang.Exception::class.java))
        rollbackRules.add(RollbackRuleAttribute(SQLException::class.java))
        rollbackRules.add(RollbackRuleAttribute(BadSqlGrammarException::class.java))
        rollbackRules.add(RollbackRuleAttribute(Exception::class.java))

        //val readOnlyAttribute = DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED)
        val readOnlyAttribute = DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW)
        readOnlyAttribute.isReadOnly = true

        //readOnlyAttribute.setTimeout(10);
        readOnlyAttribute.timeout = -1


        //val writeAttribute = RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, rollbackRules)
        val writeAttribute = RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, rollbackRules)
        writeAttribute.timeout = -1

        val readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString()
        val writeTransactionAttributesDefinition = writeAttribute.toString()
        //log.info("Read Only Attributes :: {}", readOnlyTransactionAttributesDefinition);
        //log.info("Write Attributes :: {}", writeTransactionAttributesDefinition);

        // read-only
        txAttributes.setProperty("sel*", readOnlyTransactionAttributesDefinition)
        txAttributes.setProperty("get*", readOnlyTransactionAttributesDefinition)
        txAttributes.setProperty("list*", readOnlyTransactionAttributesDefinition)
        txAttributes.setProperty("count*", readOnlyTransactionAttributesDefinition)

        // write rollback-rule
        txAttributes.setProperty("*", writeTransactionAttributesDefinition)
        txAdvice.setTransactionAttributes(txAttributes)
        txAdvice.transactionManager = transactionManager()
        return txAdvice
    }

    @Bean
    fun txAdviceAdvisor(): Advisor {
        val pointcut = AspectJExpressionPointcut()
        //pointcut.setExpression("(execution(* *..*.service..*.*(..)) || execution(* *..*.services..*.*(..)))");
        pointcut.expression = "execution(* com.template.repository..*.*(..))"
        return DefaultPointcutAdvisor(pointcut, txAdvice())
    }

    @Bean(name = ["SqlSessionFactory"])
    fun SqlSessionFactory(): SqlSessionFactory? {
        val sessionFactoryBean = SqlSessionFactoryBean()
        sessionFactoryBean.setDataSource(dataSource())
        sessionFactoryBean.setTypeAliasesPackage("com.omnilab.template_kotlin.model")
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"))
        sessionFactoryBean.setMapperLocations(*applicationContext.getResources("classpath:mybatis/mapper/*.xml"))
        return sessionFactoryBean.getObject()
    }

    @Bean(name = ["sqlSession"], destroyMethod = "clearCache")
    fun sqlSession(): SqlSession {
        return SqlSessionTemplate(SqlSessionFactory()!!)
    }
}
