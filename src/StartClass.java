import entity.Column;
import entity.Common;
import entity.Table;
import entity.Template;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.ReplaceUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StartClass {

    private static String path = "";
    static{
        //使用类加载器加载book.xml文件的路径
        path = StartClass.class.getClassLoader().getResource("config.xml").getPath();
    }

    public static void main(String[] args) throws Exception {
        //1获取并封装数据库信息
        //1.1读取配置文件
        //1.1.1读取数据库基本信息
        SAXReader reader = new SAXReader();
        Document dom = reader.read(new File(path));
        Element root = dom.getRootElement();
        Element dataSource = root.element("dataSource");
        String driverClassName = dataSource.elementTextTrim("driverClassName");
        String url = dataSource.elementTextTrim("url");
        String userName = dataSource.elementTextTrim("userName");
        String password = dataSource.elementTextTrim("password");
        //1.1.2读取数据库所要求的表信息
        Element tablesElement = root.element("tables");
        List<Element> tableElements = tablesElement.elements();
        List<Table> tables = new ArrayList<>();//存储所要求的表的信息
        for (Element e : tableElements) {
            Table table = new Table();
            table.setName(e.attributeValue("name"));
            table.setEntityName(e.attributeValue("entityName"));
            table.setComment(e.attributeValue("comment"));
            tables.add(table);
        }
        //1.1.3读取静态参数信息
        Element staticCommons = root.element("staticCommons");
        Common.setEntityPackageName(staticCommons.elementTextTrim("entityPackageName"));
        Common.setControllerPackageName(staticCommons.elementTextTrim("controllerPackageName"));
        Common.setServicePackageName(staticCommons.elementTextTrim("servicePackageName"));
        Common.setServiceImplPackageName(staticCommons.elementTextTrim("serviceImplPackageName"));
        Common.setMapperPackageName(staticCommons.elementTextTrim("mapperPackageName"));
        Common.setCommonPackageName(staticCommons.elementTextTrim("commonPackageName"));
        //1.1.4读取模板配置信息
        Element templatesElement = root.element("templates");
        List<Element> templateElements = templatesElement.elements();
        List<Template> templates = new ArrayList<>();//存放各个模板信息
        for (Element e : templateElements){
            Template template = new Template();
            template.setName(e.attributeValue("name"));
            template.setTargetName(e.attributeValue("targetName"));
            template.setTargetFolder(e.attributeValue("targetFolder"));
            templates.add(template);
        }
        //1.2连接数据库查询所需信息
        Class.forName(driverClassName);
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        for (Table t : tables){
            String sql = "SHOW FULL COLUMNS FROM " + t.getName();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Column> columns = new ArrayList<>();
            while (resultSet.next()){
                Column column = new Column();
                column.setName(resultSet.getString("Field"));
                String type = resultSet.getString("Type");
                if (type.contains("int")){
                    column.setType(0);
                }else if (type.contains("varchar")){
                    column.setType(1);
                }else if (type.contains("blob")){
                    column.setType(1);
                }else if (type.contains("text")){
                    column.setType(1);
                }else if (type.contains("decimal")){
                    column.setType(2);
                }else if (type.contains("double")){
                    column.setType(3);
                }else if (type.contains("float")){
                    column.setType(4);
                }
                column.setComment(resultSet.getString("Comment"));
                columns.add(column);
            }
            t.setColumns(columns);
        }
        //打印测试
        System.out.println("读取到的表格信息......start");
        for (Table t : tables){
            List<Column> columns = t.getColumns();
            System.out.println(t.getName() +" "+ t.getEntityName() +" "+ t.getComment());
            for (Column c : columns){
                System.out.println(c.getName() +" "+ c.getType() +" "+ c.getComment());
            }
            System.out.println();
        }
        System.out.println("读取到的表格信息......end");
        //2获取模板信息
        path = StartClass.class.getClassLoader().getResource("./template").getPath();
        for (Table t : tables) {
            for (Template template : templates) {
                FileInputStream fis = new FileInputStream(new File(path + "/" + template.getName()));
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null){
                    sb.append(line).append("\r\n");
                }
                br.close();
                String targetFolder = ReplaceUtil.replace(t, template.getTargetFolder());
                File file = new File(targetFolder);
                if (!file.exists()){
                    file.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream( targetFolder + "/" + ReplaceUtil.replace(t, template.getTargetName()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
                bw.write(ReplaceUtil.replace(t, sb.toString()));
                bw.close();
            }
        }
        System.out.println("SUCCESS");
        //3替换模板相应内容，生成目标文件
    }
}
