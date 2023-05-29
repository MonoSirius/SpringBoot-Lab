# 实验笔记
对实验过程中踩的坑进行记录

## lab1 mapStruct
踩坑:

mapstruct在生成转换代码时找不到属性，生成转换代码时lombok还没有生成setter、getter相应方法，所以找不到属性

解决:

添加
```xml
<path>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok-mapstruct-binding</artifactId>
    <version>0.2.0</version>
</path>
```

[解决参考](https://blog.csdn.net/weixin_42272869/article/details/122337269)