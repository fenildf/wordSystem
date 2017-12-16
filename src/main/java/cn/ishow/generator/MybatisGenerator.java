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
 * ������������ʾ
 * </p>
 */
public class MybatisGenerator {
	private static String parentDir ="G:\\eclipse\\day2";
	private static boolean isOverRide = true;
	private static String[] include = new String[]{"t_person","t_word","t_word_note"};
	private static String[] tabPrefix = new String[]{"t_"};
	
    /**
     * <p>
     * MySQL ������ʾ
     * </p>
     */
    public static void main(String[] args) {
        AutoGeneratorPlus mpg = new AutoGeneratorPlus();

        // ȫ������
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(parentDir);
        gc.setFileOverride(isOverRide);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML ��������
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setOpen(false);//�������Ƿ��Ŀ¼
        gc.setAuthor("���޸�");

        // �Զ����ļ�������ע�� %s ���Զ�����ʵ�����ԣ�
//        gc.setMapperName("%sDao");
//        gc.setXmlName("%sDao");
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sServiceImpl");
//        gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // ����Դ����
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // �Զ������ݿ���ֶ�����ת������ѡ��
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("ת�����ͣ�" + fieldType);
                // ע�⣡��processTypeConvert ����Ĭ������ת�������������Ҫ��Ч�����Զ��巵�ء�������ֱ�ӷ��ء�
                return super.processTypeConvert(fieldType);
            }
        });

        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://127.0.0.1/yin_chong");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // ��������
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// ȫ�ִ�д���� ORACLE ע��
        strategy.setTablePrefix(tabPrefix);//
        // �˴������޸�Ϊ���ı�ǰ׺
        strategy.setNaming(NamingStrategy.underline_to_camel);// �������ɲ���
        strategy.setInclude(include); // ��Ҫ���ɵı�
        // strategy.setExclude(new String[]{"test"}); // �ų����ɵı�
        mpg.setStrategy(strategy);

        // ������
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.ishow");
        // pc.setModuleName("");
        mpg.setPackageInfo(pc);
        
        PathConfig pathConfig = new PathConfig();//����ģ��·����Ϣ
        pathConfig.setController(joinPath("word-system","src\\main\\java"));
        pathConfig.setEntity(joinPath("word-system","src\\main\\java"));
        pathConfig.setMapper(joinPath("word-system","src\\main\\java"));
        pathConfig.setService(joinPath("word-system","src\\main\\java"));
        pathConfig.setServiceImpl(joinPath("word-system","src\\main\\java"));
        pathConfig.setXml(joinPath("word-system","src\\main\\resources"));
        mpg.setPathConfig(pathConfig);
        // ִ������
        mpg.execute();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>�����������.......");
    }
    
    private static String joinPath(String moduleName,String childPath){
    	String outDir = parentDir+"\\"+moduleName+"\\"+childPath;
    	return outDir;
    }
}