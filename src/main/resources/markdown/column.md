

## 字段说明

### 数据表（table）

描述数据库中的某一个表 主要包括以下变量：

| 字段名         | 类型           | 描述                    |
| ----------- | ------------ | --------------------- |
| packagename | String       | 包名，即基础包名              |
| name        | String       | 数据库中的原始表名             |
| columnlist  | List<Column> | 列集合                   |
| comment     | String       | 表备注                   |
| classname   | String       | 类名（即去掉表名前缀，并将首字母转为大写） |
| lowername   | String       | 去掉前缀的表名               |

### 列（column）

描述数据表中的某一列,主要包括以下变量

| 名称         | 类型     | 说明                                |
| ---------- | ------ | --------------------------------- |
| name       | String | 列名（去掉了_，并转为驼峰命名法）                 |
| comment    | String | 备注，建议默认在数据库里都写上备注，代码里的所有注释都来源于此备注 |
| type       | String | Java数据类型                          |
| length     | int    | 数据长度                              |
| bigname    | String | 将列名变为首字母大写                        |
| columnname | String | 数据表中的原始列名                         |
| isnull     | String | 是否为空（YES或者NO）                     |
| columntype | String | 数据库中的数据类型                         |
| columnkey  | String | 是否主键                              |
| extra      | String | 其它                                |
