package cn.ishow.generator;

import java.io.File;
import java.util.Map;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.toolkit.StringUtils;

public class ConfigBuilderPlus extends ConfigBuilder {

    public ConfigBuilderPlus(PackageConfig packageConfig, DataSourceConfig dataSourceConfig,
                             StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig, PathConfig pathConfig) {
        super(packageConfig, dataSourceConfig, strategyConfig, template, globalConfig);
        Map<String, String> packageInfo = getPackageInfo();
        Map<String, String> pathInfo = getPathInfo();
        template = getTemplate();
        if (StringUtils.isNotEmpty(template.getEntity())) {
            pathInfo.put(ConstVal.ENTITY_PATH, joinPath(pathConfig.getEntity(), packageInfo.get(ConstVal.ENTITY)));
        }
        if (StringUtils.isNotEmpty(template.getMapper())) {
            pathInfo.put(ConstVal.MAPPER_PATH, joinPath(pathConfig.getMapper(), packageInfo.get(ConstVal.MAPPER)));
        }
        if (StringUtils.isNotEmpty(template.getXml())) {
            pathInfo.put(ConstVal.XML_PATH, joinPath(pathConfig.getXml(), "mybatis.mapper"));
        }
        if (StringUtils.isNotEmpty(template.getService())) {
            pathInfo.put(ConstVal.SERIVCE_PATH, joinPath(pathConfig.getService(), packageInfo.get(ConstVal.SERIVCE)));
        }
        if (StringUtils.isNotEmpty(template.getServiceImpl())) {
            pathInfo.put(ConstVal.SERVICEIMPL_PATH, joinPath(pathConfig.getServiceImpl(), packageInfo.get(ConstVal.SERVICEIMPL)));
        }
        if (StringUtils.isNotEmpty(template.getController())) {
            pathInfo.put(ConstVal.CONTROLLER_PATH, joinPath(pathConfig.getController(), packageInfo.get(ConstVal.CONTROLLER)));
        }
    }

    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", "\\" + File.separator);
        return parentDir + packageName;
    }
}