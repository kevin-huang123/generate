package entity;

/**
 * 列
 */
public class Column {

    private String name;//列名
    private String nameHumpCase;//列名（驼峰命名）
    private String nameUpperCase;//列名（首字母大写）
    private Integer type;//列的数据类型 0int 1varchar 2decimal 3double 4float
    private String comment;//列注释

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameHumpCase() {
        String[] split = name.split("_");
        StringBuilder sb = new StringBuilder(split[0]);
        if (split.length > 1){
            for (int i = 1; i < split.length; i++) {
                String tempName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
                sb.append(tempName);
            }
        }
        return sb.toString();
    }

    public void setNameHumpCase(String nameHumpCase) {
        this.nameHumpCase = nameHumpCase;
    }

    public String getNameUpperCase() {
        String[] split = name.split("_");
        String a = split[0];
        StringBuilder sb = new StringBuilder(a.substring(0, 1).toUpperCase());
        if (a.length() > 1){
            sb.append(a.substring(1));
        }
        if (split.length > 1){
            for (int i = 1; i < split.length; i++) {
                String tempName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
                sb.append(tempName);
            }
        }
        return sb.toString();
    }

    public void setNameUpperCase(String nameUpperCase) {
        this.nameUpperCase = nameUpperCase;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
