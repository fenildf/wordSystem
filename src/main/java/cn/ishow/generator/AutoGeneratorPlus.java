package cn.ishow.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;

public class AutoGeneratorPlus extends AutoGenerator {
    private PathConfig pathConfig;

    @Override
    protected void initConfig() {
        if (null == config) {
            config = new ConfigBuilderPlus(getPackageInfo(), getDataSource(), getStrategy(), getTemplate(), getGlobalConfig(), pathConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
    }

    public PathConfig getPathConfig() {
        return pathConfig;
    }

    public void setPathConfig(PathConfig pathConfig) {
        this.pathConfig = pathConfig;
    }
}