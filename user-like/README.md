**错误码定义8位**

#  ┌─1─┬─2─┬─3─┬─4─┬─5─┬─6─┬─7─┬─8─┐
#  │预留 │C/B端│   模块名   │         错误码           │
#  └─1─┴─2─┴─3─┴─4─┴─5─┴─6─┴─7─┴─8─┘
#第1位：
#   预留
#第2位：
#   C/B端(客户端或服务端) 0-服务端, 1-客户端
#第3、4位：
#   2位模块名
#第5、6、7、8位：
#   4位错误码(后4位)，各位含义如下：
#   第5为：类别，可按业务分类、接口分类等划分，0-9
#   第6-8位：3位具体错误码
#       第6位：按以下含义定义分类：
#           0：预留
#           1：非空检查类提示，数据为空、不为空检查
#           2：有效性检查提示，数据有效性检查（如格式、存在、不存在、不在有效值范围等）
#           3：业务逻辑类提示，合法性/一致性/完整性检查提示
#           4：预留/待扩展定义
#           5：预留/待扩展定义
#           6：预留/待扩展定义
#           7：预留/待扩展定义
#           8：预留/待扩展定义
#           9：预留/待扩展定义
#       第7、8位：二位顺序标号，00-99


https://blog.csdn.net/xcbeyond/article/details/90708873