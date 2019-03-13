package util;

import entity.Column;
import entity.Common;
import entity.Table;

import java.util.List;

public class ReplaceUtil {

    public static String replace(Table table, String target){
        //处理表格内容
        String result = target.replace("!{table.name}", table.getName());
        result = result.replace("!{table.comment}", table.getComment());
        result = result.replace("!{table.entityName}", table.getEntityName());
        result = result.replace("!{table.entityNameHumpCase}", table.getEntityNameHumpCase());
        //处理通用参数
        result = result.replace("!{Common.entityPackageName}", Common.getEntityPackageName());
        result = result.replace("!{Common.controllerPackageName}", Common.getControllerPackageName());
        result = result.replace("!{Common.servicePackageName}", Common.getServicePackageName());
        result = result.replace("!{Common.serviceImplPackageName}", Common.getServiceImplPackageName());
        result = result.replace("!{Common.mapperPackageName}", Common.getMapperPackageName());
        result = result.replace("!{Common.commonPackageName}", Common.getCommonPackageName());
        //处理for循环内容
        int beginIndex = result.indexOf("!{for ");
        while (beginIndex != -1){
            int endIndex = result.indexOf("!{/for}", beginIndex);
            String temp = result.substring(beginIndex, endIndex + 7);//for标签及标签包含内容
            String tempResult = manageFor(table, temp);
            result = result.replace(temp, tempResult);
            beginIndex = result.indexOf("!{for ");
        }
        return result;
    }

    private static String manageFor(Table table, String target){
        target = target.substring(2);
        int index = target.indexOf("}");
        String args = target.substring(3, index);
        String[] argsArr = args.split(":");
        String item = argsArr[0].trim();
        String separate = null;
        if (args.contains("separate")){
            int beginIndex = args.indexOf("\"");
            int endIndex;
            if (beginIndex != -1){
                endIndex = args.lastIndexOf("\"");
            }else {
                beginIndex = args.indexOf("'");
                endIndex = args.lastIndexOf("'");
            }
            separate = args.substring(beginIndex + 1, endIndex);
        }
        String content = target.substring(index + 1, target.length() - 7);
        StringBuilder sb = new StringBuilder();
        String result;
        List<Column> columns = table.getColumns();
        if (separate == null){
            for (Column c : columns) {
                String temp = content;
                temp = temp.replace("!{"+item+".name}", c.getName());
                temp = temp.replace("!{"+item+".nameHumpCase}", c.getNameHumpCase());
                temp = temp.replace("!{"+item+".nameUpperCase}", c.getNameUpperCase());
                String comment = c.getComment();
                if (comment == null){
                    comment = "";
                }
                temp = temp.replace("!{"+item+".comment}", comment);

                int beginIndex = temp.indexOf("!{if ");
                while (beginIndex != -1){
                    int endIndex = temp.indexOf("!{/if}", beginIndex);
                    String temp2 = temp.substring(beginIndex, endIndex + 6);//if标签及标签包含内容
                    String tempResult = manageIf(c, item, temp2);
                    temp = temp.replace(temp2, tempResult);
                    beginIndex = temp.indexOf("!{if ");
                }

                sb.append(temp);
            }
            result = sb.toString();
        }else{
            for (Column c : columns) {
                String temp = content;
                temp = temp.replace("!{"+item+".name}", c.getName());
                temp = temp.replace("!{"+item+".nameHumpCase}", c.getNameHumpCase());
                temp = temp.replace("!{"+item+".nameUpperCase}", c.getNameUpperCase());
                String comment = c.getComment();
                if (comment == null){
                    comment = "";
                }
                temp = temp.replace("!{"+item+".comment}", comment);

                int beginIndex = temp.indexOf("!{if ");
                while (beginIndex != -1){
                    int endIndex = temp.indexOf("!{/if}", beginIndex);
                    String temp2 = temp.substring(beginIndex, endIndex + 6);//if标签及标签包含内容
                    String tempResult = manageIf(c, item, temp2);
                    temp = temp.replace(temp2, tempResult);
                    beginIndex = temp.indexOf("!{if ");
                }

                sb.append(temp).append(separate);
            }
            result = sb.toString();
            int a = result.lastIndexOf(separate);
            result = result.substring(0, a);
        }
        return result;
    }

    private static String manageIf(Column column, String item, String target){
        String result;
        target = target.substring(2);
        int index = target.indexOf("}");
        String args = target.substring(2, index).trim();
        int caculateType = -1;// caculateType = 0表示"=="; caculateType = 1表示"!="
        int type = -1;
        if (args.contains("==")){
            caculateType = 0;
            type = Integer.parseInt(args.split("==")[1].trim());
        }else if (args.contains("!=")){
            caculateType = 1;
            type = Integer.parseInt(args.split("!=")[1].trim());
        }
        if (caculateType != -1 && type != -1){
            int endIndex = target.lastIndexOf("!{/if}");
            String temp = target.substring(index + 1, endIndex);
            temp = temp.replace("!{"+ item +".name}",column.getName());
            temp = temp.replace("!{"+ item +".nameHumpCase}",column.getNameHumpCase());
            temp = temp.replace("!{"+ item +".nameUpperCase}",column.getNameUpperCase());
            String comment = column.getComment();
            if (comment == null){
                comment = "";
            }
            temp = temp.replace("!{"+ item +".comment}",comment);
            if (caculateType == 0){
                if (column.getType() == type){//条件成立
                    result = temp;
                }else{//条件不成立
                    result = "";
                }
            }else{
                if (column.getType() != type){//条件成立
                    result = temp;
                }else{//条件不成立
                    result = "";
                }
            }
        }else{
            result = "!{" + target;
        }
        return result;
    }
}
