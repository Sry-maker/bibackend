

### 数据库schema

#### 标签类型

+ PAPER
  + 属性列表
  + index (#index)
  + title (#*)
  + authors (#@) （冗余）
  + affiliations 作者们的隶属单位，数量和作者数量一致（#o）（冗余）
  + year (#t)
  + venue 发布论文的期刊/会议集 (#c) （冗余）
  + ref 引用 （#%）（冗余）
  + abstract 摘要（#!）

+ AUTHOR
  + index (#index)
  + name (#n)
  + affiliations (#a) 作者的隶属单位，可以多个（冗余）
  + publishCount （#pc）发表论文数
  + referenceCount （\#cn）被引次数
  + Hindex （#hi）
  + Pindex （#pi）
  + UPindex （#upi）
  + interests （#t）研究兴趣领域（冗余）

+ AFFILIATION (隶属单位)

  + name

    例：

    Polish Academy of Sciences, Warsaw, Poland

    School of Computing Sciences, University of East Anglia, Norwich, UK

+ INTEREST 领域 

  + name **（需要设置大小写不敏感）**

    例：

    Information Security

    control group

+ VENUE 期刊/会议

  + name

    例：

    ACM Transactions on Graphics (TOG)

    Physical structures and application on Database design techniques II

+ 年标签	

#### 关系类型

+ AUTHOR - WRITE ->  PAPER
+ AUTHOR - CO_AUTHOR -> AUTHOR
  + count 合作次数
+ AUTHOR - BELONG_TO -> 隶属单位
+ AUTHOR - INTERESTS -> 领域标签
+ PAPER - REFERENCE -> PAPER
+ 期刊/会议标签 - PUBLISH -> PAPER
