我们理解您需要更便捷更高效的工具记录思想，整理笔记、知识，并将其中承载的价值传播给他人，**Cmd Markdown** 是我们给出的答案 —— 我们为记录思想和分享知识提供更专业的工具。 您可以使用 Cmd Markdown：

> * 整理知识，学习笔记
> * 发布日记，杂文，所见所想
> * 撰写发布技术文稿（代码支持）
> * 撰写发布学术论文（LaTeX 公式支持）

![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png)

除了您现在看到的这个 Cmd Markdown 在线版本，您还可以前往以下网址下载：

### [Windows/Mac/Linux 全平台客户端](https://www.zybuluo.com/cmd/)

> 请保留此份 Cmd Markdown 的欢迎稿兼使用说明，如需撰写新稿件，点击顶部工具栏右侧的 <i class="icon-file"></i> **新文稿** 或者使用快捷键 `Ctrl+Alt+N`。

------

## 什么是 Markdown

Markdown 是一种方便记忆、书写的纯文本标记语言，用户可以使用这些标记符号以最小的输入代价生成极富表现力的文档：譬如您正在阅读的这份文档。它使用简单的符号标记不同的标题，分割不同的段落，**粗体** 或者 *斜体* 某些文字，更棒的是，它还可以

### 1. 制作一份待办事宜 [Todo 列表](https://www.zybuluo.com/mdeditor?url=https://www.zybuluo.com/static/editor/md-help.markdown#13-待办事宜-todo-列表)

- [ ] 支持以 PDF 格式导出文稿
- [ ] 改进 Cmd 渲染算法，使用局部渲染技术提高渲染效率
- [x] 新增 Todo 列表功能
- [x] 修复 LaTex 公式渲染问题
- [x] 新增 LaTex 公式编号功能

```
private static void main(){
	String a="";
	
	}
```

`sdfsdfdsf`

> this is quote



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


