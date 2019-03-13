package entity;

public class Common {

    private static String entityPackageName;//实体类包名
    private static String controllerPackageName;//控制类包名
    private static String servicePackageName;//服务层接口包名
    private static String serviceImplPackageName;//服务层实现类包名
    private static String mapperPackageName;//持久层接口包名
    private static String commonPackageName;//通用包名

    public static String getEntityPackageName() {
        return entityPackageName;
    }

    public static void setEntityPackageName(String entityPackageName) {
        Common.entityPackageName = entityPackageName;
    }

    public static String getControllerPackageName() {
        return controllerPackageName;
    }

    public static void setControllerPackageName(String controllerPackageName) {
        Common.controllerPackageName = controllerPackageName;
    }

    public static String getServicePackageName() {
        return servicePackageName;
    }

    public static void setServicePackageName(String servicePackageName) {
        Common.servicePackageName = servicePackageName;
    }

    public static String getServiceImplPackageName() {
        return serviceImplPackageName;
    }

    public static void setServiceImplPackageName(String serviceImplPackageName) {
        Common.serviceImplPackageName = serviceImplPackageName;
    }

    public static String getMapperPackageName() {
        return mapperPackageName;
    }

    public static void setMapperPackageName(String mapperPackageName) {
        Common.mapperPackageName = mapperPackageName;
    }

    public static String getCommonPackageName() {
        return commonPackageName;
    }

    public static void setCommonPackageName(String commonPackageName) {
        Common.commonPackageName = commonPackageName;
    }
}
