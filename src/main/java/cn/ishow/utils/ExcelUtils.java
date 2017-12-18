package cn.ishow.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelUtils {

    /***
     * 批量从excel中导入数据到内存中
     * @param clazz 要导入数据映射在内存中的对象
     * @param fileName 文件名称包含后缀名
     * @param is 输入流
     * @param heads 要导出的字段(name,id,age,...)
     * @return
     * @throws Exception
     */
    public static <T> List<T> importData(Class<T> clazz, String fileName, InputStream is, List<String> heads)
            throws Exception {
        if (!fileName.matches("^.+\\.(?i)((xls)|(xlsx))$"))
            throw new RuntimeException("选择正确的excel文件");
        // 判断是否为2003或2007版本的excel
        boolean is03Excell = fileName.matches("^.+\\.(?i)(xls)$") ? true : false;
        Workbook workbook = is03Excell ? new HSSFWorkbook(is) : new XSSFWorkbook(is);

        // 2.读取工作表
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet.getPhysicalNumberOfRows() < 2)
            throw new RuntimeException("excel 格式有误");
        // 直接使用字段反射机制设置
        List<Field> fields = getFields(clazz, heads);
        List<T> beans = new LinkedList<>();
        for (int k = 2; k < sheet.getPhysicalNumberOfRows(); k++) {
            T bean = clazz.newInstance();
            // 读取单元格
            Row row = sheet.getRow(k);
            int rowNum = row.getLastCellNum();
            for (int j = 0; j < rowNum; j++) {
                Field field = fields.get(j);
                // 获取列
                Cell cell = row.getCell(j);
                Class fieldType = field.getType();
                if (fieldType.equals(String.class)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat format = new DecimalFormat("#");
                        String value = format.format(cell.getNumericCellValue());
                        field.set(bean, value);
                    } else {
                        field.set(bean, cell.getStringCellValue());
                    }
                } else if (fieldType.equals(Integer.class)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat format = new DecimalFormat("#");
                        String value = format.format(cell.getNumericCellValue());
                        field.set(bean, Integer.parseInt(value));
                    } else {
                        String value = cell.getStringCellValue();
                        field.set(bean, Integer.parseInt(value));
                    }
                } else if (fieldType.equals(Long.class)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat format = new DecimalFormat("#");
                        String value = format.format(cell.getNumericCellValue());
                        field.set(bean, Long.parseLong(value));
                    } else {
                        String value = cell.getStringCellValue();
                        field.set(bean, Long.parseLong(value));
                    }
                } else if (fieldType.equals(Double.class)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat format = new DecimalFormat("#.######");
                        String value = format.format(cell.getNumericCellValue());
                        field.set(bean, Double.parseDouble(value));
                    } else {
                        String value = cell.getStringCellValue();
                        field.set(bean, Double.parseDouble(value));
                    }
                } else if (fieldType.equals(Float.class)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat format = new DecimalFormat("#.######");
                        String value = format.format(cell.getNumericCellValue());
                        field.set(bean, Float.parseFloat(value));
                    } else {
                        String value = cell.getStringCellValue();
                        field.set(bean, Float.parseFloat(value));
                    }
                } else if (fieldType.equals(Boolean.class)) {
                    boolean value = cell.getBooleanCellValue();
                    field.set(bean, value);
                } else if (fieldType.equals(Date.class)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        Date value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        field.set(bean, value);
                    } else {
                        String value = cell.getStringCellValue();
                        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = formate.parse(value);
                        field.set(bean, date);
                    }
                } else if (fieldType.equals(Short.class)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat format = new DecimalFormat("#");
                        String value = format.format(cell.getNumericCellValue());
                        field.set(bean, Short.parseShort(value));
                    } else {
                        String value = cell.getStringCellValue();
                        field.set(bean, value);
                    }
                }

            }
            beans.add(bean);
        }
        workbook.close();
        return beans;
    }


    /***
     *
     * @param beans 要输出的数据集合
     * @param os 输出流
     * @param map 要输出的数据结构为LinkedHashMap( 姓名:name,年龄:age,.....)
     */
    public static <T> void exportData(List<T> beans, OutputStream os, LinkedHashMap<String, String> map, String title) throws Exception {
        List<String> headName = new LinkedList<>();
        List<String> headAttribute = new LinkedList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headName.add(key);
            headAttribute.add(value);
        }
        // 1.创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 1.1创建合并单元格对象
        CellRangeAddress callRangeAddress = new CellRangeAddress(0, 0, 0, 4);// 起始行,结束行,起始列,结束列
        // 1.2头标题样式
        HSSFCellStyle headStyle = createCellStyle(workbook, (short) 16);
        // 1.3列标题样式
        HSSFCellStyle colStyle = createCellStyle(workbook, (short) 13);
        // 2.创建工作表
        HSSFSheet sheet = workbook.createSheet(title);
        // 2.1加载合并单元格对象
        sheet.addMergedRegion(callRangeAddress);
        // 设置默认列宽
        sheet.setDefaultColumnWidth(25);
        // 3.创建行
        // 3.1创建头标题行;并且设置头标题
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell0 = row.createCell(0);

        // 加载单元格样式
        cell0.setCellStyle(headStyle);
        cell0.setCellValue(title);

        // 3.2创建列标题;并且设置列标题
        HSSFRow row2 = sheet.createRow(1);

        for (int i = 0; i < headName.size(); i++) {
            HSSFCell cell2 = row2.createCell(i);
            // 加载单元格样式
            cell2.setCellStyle(colStyle);
            cell2.setCellValue(headName.get(i));
        }

        if (beans != null) {
            List<Field> fields = getFields(beans.get(0).getClass(), headAttribute);
            for (int i = 0; i < beans.size(); i++) {
                HSSFRow row3 = sheet.createRow(i + 2);
                T bean = beans.get(i);
                for (int k = 0; k < headAttribute.size(); k++) {
                    HSSFCell cellTemp = row3.createCell(k);
                    Field field = fields.get(k);
                    Object value = field.get(bean);
                    String temp = "";
                    if (value instanceof Date) {
                        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        temp = formate.format(value);
                    } else {
                        temp = value + "";
                    }
                    cellTemp.setCellValue(temp);
                }
            }
        }

        workbook.write(os);
        workbook.close();
    }

    private static List<Field> getFields(Class clazz, List<String> heads) throws Exception {
        if (heads == null || heads.size() == 0)
            return null;
        List<Field> fields = new ArrayList<>();
        for (String head : heads) {
            Field field = clazz.getDeclaredField(head);
            field.setAccessible(true);
            fields.add(field);
        }
        return fields;
    }


    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize) {
        // TODO Auto-generated method stub
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        // 创建字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints(fontsize);
        // 加载字体
        style.setFont(font);
        return style;
    }

}
