package com.omnilab.templatekotlin.config

import com.zaxxer.hikari.HikariDataSource
import net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.slf4j.LoggerFactory
import org.springframework.aop.Advisor
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import org.springframework.transaction.interceptor.RollbackRuleAttribute
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute
import org.springframework.transaction.interceptor.TransactionInterceptor
import java.sql.SQLException
import java.util.ArrayList
import java.util.Properties
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
internal class DatabaseConfig {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Value("\${variable.db-class}")
    private lateinit var dbClass: String

    @Value("\${variable.db-url}")
    private lateinit var dbUrl: String

    @Value("\${variable.db-username}")
    private lateinit var dbUsername: String

    @Value("\${variable.db-password}")
    private lateinit var dbPassword: String

    @Bean
    fun dataSource(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.driverClassName = dbClass
        dataSource.jdbcUrl = dbUrl
        dataSource.username = dbUsername
        dataSource.password = dbPassword
        dataSource.minimumIdle = 1
        dataSource.maximumPoolSize = 50
        dataSource.connectionTestQuery = "select 1"
        dataSource.connectionTimeout = 15000
        dataSource.validationTimeout = 60000

        val dataSourceSpy = DataSourceSpy(dataSource)
        dataSourceSpy.logDelegator = Slf4jSpyLogDelegator()
        return dataSourceSpy
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

        // val readOnlyAttribute = DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED)
        val readOnlyAttribute = DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW)
        readOnlyAttribute.isReadOnly = true

        // readOnlyAttribute.setTimeout(10);
        readOnlyAttribute.timeout = -1

        // val writeAttribute = RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, rollbackRules)
        val writeAttribute = RuleBasedTransactionAttribute(
            TransactionDefinition.PROPAGATION_REQUIRES_NEW,
            rollbackRules
        )
        writeAttribute.timeout = -1

        val readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString()
        val writeTransactionAttributesDefinition = writeAttribute.toString()
        // log.info("Read Only Attributes :: {}", readOnlyTransactionAttributesDefinition);
        // log.info("Write Attributes :: {}", writeTransactionAttributesDefinition);

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
        // pointcut.setExpression("(execution(* *..*.service..*.*(..)) || execution(* *..*.services..*.*(..)))");
        pointcut.expression = "execution(* com.template.repository..*.*(..))"
        return DefaultPointcutAdvisor(pointcut, txAdvice())
    }

    @Bean(name = ["SqlSessionFactory"])
    fun SqlSessionFactory(): SqlSessionFactory? {
        val sessionFactoryBean = SqlSessionFactoryBean()
        sessionFactoryBean.setDataSource(dataSource())
        sessionFactoryBean.setTypeAliasesPackage("com.omnilab.templateKotlin.model")
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"))
        sessionFactoryBean.setMapperLocations(*applicationContext.getResources("classpath:mybatis/mapper/*.xml"))
        return sessionFactoryBean.getObject()
    }

    @Bean(name = ["sqlSession"], destroyMethod = "clearCache")
    fun sqlSession(): SqlSession {
        return SqlSessionTemplate(SqlSessionFactory()!!)
    }
}
