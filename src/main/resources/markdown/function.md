
在我们应用Freemarker过程中，经常会操作例如字符串，数字，集合等，却不清楚`Freemrker`有没有类似于Java一样有相关的类及方法。在本文当中，我将向大家详细的介绍Freemarke的内置函数及用法，以便能帮助大家更熟练的应用`Freemarker`完成项目开发。
 
## 一、  Sequence的内置函数
|function | 说明 |
|----|----|
| sequence?first| 返回sequence的第一个值。|
| sequence?last|  返回sequence的最后一个值。|
| sequence?reverse| 将sequence的现有顺序反转，即倒序排序|
| sequence?size|   返回sequence的大小|
| sequence?sort|    将sequence中的对象转化为字符串后顺序排序|
| sequence?sort_by(value)| 按sequence中对象的属性value进行排序|
 
> 注意：Sequence不能为null。
## 二、Hash的内置函数
 
1.hash?keys 返回hash里的所有key,返回结果为sequence
2.hash?values 返回hash里的所有value,返回结果为sequence
例如：
```
<#assign user={“name”:“hailang”, “sex”:“man”}>
  <#assign keys=user?keys>
  <#list keys as key>
      ${key}=${user[key]}
  </#list>
``` 
## 三、操作字符串函数
### 1.substring（start,end）从一个字符串中截取子串
start:截取子串开始的索引，start必须大于等于0，小于等于end
end: 截取子串的长度，end必须大于等于0，小于等于字符串长度，如果省略该参数，默认为字符串长度。
例子：
```
${‘str’?substring(0)}结果为str
${‘str’?substring(1)}结果为tr
${‘str’?substring(2)}结果为r
${‘str’?substring(3)}结果为
 
${‘str’?substring(0,0)}结果为
${‘str’?substring(0,1)}结果为s
${‘str’?substring(0,2)}结果为st
${‘str’?substring(0,3)}结果为str
 ```
 
### 2.cap_first 将字符串中的第一个单词的首字母变为大写。
${‘str’？cap_first}结果为Str

### 3.uncap_first将字符串中的第一个单词的首字母变为小写。
${‘Str’？cap_first}结果为str
 
### 4.capitalize将字符串中的所有单词的首字母变为大写
${‘str’？ capitalize}结果为STR

### 5.date,time，datetime将字符串转换为日期
例如：
```
<#assign date1=”2009-10-12”?date(“yyyy-MM-dd”)>
<#assign date2=”9:28:20”?time(“HH:mm:ss”)>
<#assign date3=” 2009-10-12 9:28:20”?time(“HH:mm:ss”)>
``` 
${date1}结果为2009-10-12
${date2}结果为9:28:20
${date3}结果为2009-10-12 9:28:20
 时间格式化： ${book.date?string('yyyy-MM-dd')}
判断长度：<#if oa.content?length gt= 20>
注意：如果指定的字符串格式不正确将引发错误。

### 6.ends_with 
判断某个字符串是否由某个子串结尾，返回布尔值。
${“string”?ends_with(“ing”)?string} 返回结果为true
注意：布尔值必须转换为字符串才能输出

### 7.html 
用于将字符串中的<、>、&和“替换为对应得&lt;&gt;&quot:&amp
### 8.index_of（substring,start）
在字符串中查找某个子串，返回找到子串的第一个字符的索引，如果没有找到子串，则返回-1。
  Start参数用于指定从字符串的那个索引处开始搜索，start为数字值。
  如果start大于字符串长度，则start取值等于字符串长度，如果start小于0， 则start取值为0。
```
${“string”?index_of(“in”)结果为3
${“string”?index_of(“ab”)结果为-1
```

### 9.length
返回字符串的长度
 ${“string”?length}结果为6

### 10.lower_case
将字符串转为小写
${“STRING”?lower_case}结果为string

### 11.upper_case
将字符串转为大写
${“string”?upper_case}结果为STRING

### 12.contains
判断字符中是否包含某个子串。返回布尔值
  ${“string”?contains(“ing”)?string}结果为true
> 注意：布尔值必须转换为字符串才能输出

### 13.number
将字符串转换为数字
${“111.11”?number}结果为111.11

### 14.replace
用于将字符串中的一部分从左到右替换为另外的字符串。
${“strabg”?replace(“ab”,”in”)}结果为string

### 15.split
使用指定的分隔符将一个字符串拆分为一组字符串
```
<#list “This|is|split”?split(“|”) as s>
    ${s}
</#list>
```
结果为:
This
is
split
 
### 16.trim
删除字符串首尾空格 

${“  String ”?trim}结果为String
 
 
## 四、操作数字
### 1.c 用于将数字转换为字符串
${123?c}结果为123

### 2.string用于将数字转换为字符串
Freemarker中预订义了三种数字格式：number,currency（货币）和percent(百分比)其中number为默认的数字格式转换
例如： 
<#assign tempNum=20>
${tempNum}    
${tempNum?string.number}或${tempNum?string(“number”)}结果为20
${tempNum?string.currency}或${tempNum?string(“currency”)}结果为￥20.00
${tempNum?string. percent}或${tempNum?string(“percent”)}结果为2,000%

##五、    操作布尔值
 
string 用于将布尔值转换为字符串输出
true转为“true”，false转换为“false”
 
foo?string(“yes”,”no”)如果布尔值是true,那么返回“yes”,否则返回no