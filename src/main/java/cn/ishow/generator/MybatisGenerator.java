package cn.ishow.generator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class MybatisGenerator {
	private static String parentDir ="G:\\eclipse\\day2";
	private static boolean isOverRide = true;
	private static String[] include = new String[]{"t_person","t_word","t_word_note"};
	private static String[] tabPrefix = new String[]{"t_"};
	
    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGeneratorPlus mpg = new AutoGeneratorPlus();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(parentDir);
        gc.setFileOverride(isOverRide);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setOpen(false);//生成完是否打开目录
        gc.setAuthor("代艳格");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
//        gc.setMapperName("%sDao");
//        gc.setXmlName("%sDao");
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sServiceImpl");
//        gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });

        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://127.0.0.1/yin_chong");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(tabPrefix);//
        // 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(include); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.ishow");
        // pc.setModuleName("");
        mpg.setPackageInfo(pc);
        
        PathConfig pathConfig = new PathConfig();//配置模块路径信息
        pathConfig.setController(joinPath("word-system","src\\main\\java"));
        pathConfig.setEntity(joinPath("word-system","src\\main\\java"));
        pathConfig.setMapper(joinPath("word-system","src\\main\\java"));
        pathConfig.setService(joinPath("word-system","src\\main\\java"));
        pathConfig.setServiceImpl(joinPath("word-system","src\\main\\java"));
        pathConfig.setXml(joinPath("word-system","src\\main\\resources"));
        mpg.setPathConfig(pathConfig);
        // 执行生成
        mpg.execute();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>生产代码完成.......");
    }
    
    private static String joinPath(String moduleName,String childPath){
    	String outDir = parentDir+"\\"+moduleName+"\\"+childPath;
    	return outDir;
    }
}