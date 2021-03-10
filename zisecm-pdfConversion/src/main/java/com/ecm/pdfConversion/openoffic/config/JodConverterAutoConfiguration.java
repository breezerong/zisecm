package com.ecm.pdfConversion.openoffic.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.ExistingProcessAction;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * openoffice 配置
 *
 */
@Configuration
@ConditionalOnClass({DocumentConverter.class})
@ConditionalOnProperty(prefix = "jodconverter", name = {"enabled"}, havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({JodConverterProperties.class})
@Slf4j
public class JodConverterAutoConfiguration {
    private final JodConverterProperties properties;

    public JodConverterAutoConfiguration(JodConverterProperties properties) {
        this.properties = properties;
    }

    private OfficeManager createOfficeManager() {
        LocalOfficeManager.Builder builder = LocalOfficeManager.builder();
        if (!StringUtils.isBlank(this.properties.getPortNumbers())) {
            Set<Integer> iports = new HashSet<>();
            String[] var3 = StringUtils.split(this.properties.getPortNumbers(), ", ");
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String portNumber = var3[var5];
                iports.add(NumberUtils.toInt(portNumber, 8100));
            }
            builder.portNumbers(ArrayUtils.toPrimitive(iports.toArray(new Integer[iports.size()])));
        }
        builder.officeHome(this.properties.getOfficeHome());
        builder.workingDir(this.properties.getWorkingDir());
        builder.templateProfileDir(this.properties.getTemplateProfileDir());
        builder.existingProcessAction(ExistingProcessAction.KILL);
        builder.processTimeout(this.properties.getProcessTimeout());
        builder.processRetryInterval(this.properties.getProcessRetryInterval());
        builder.taskExecutionTimeout(this.properties.getTaskExecutionTimeout());
        builder.maxTasksPerProcess(this.properties.getMaxTasksPerProcess());
        builder.taskQueueTimeout(this.properties.getTaskQueueTimeout());
        LocalOfficeManager lom = null;
        try {
            lom = builder.build();
        } catch (Exception e) {
            e.getStackTrace();
            throw e;
        }
        return lom;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnMissingBean
    public OfficeManager officeManager() {
        return this.createOfficeManager();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({OfficeManager.class})
    public DocumentConverter jodConverter(OfficeManager officeManager) {
        return LocalConverter.make(officeManager);
    }
}