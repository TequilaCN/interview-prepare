#### 1. 数值类型的字段的宽度问题
  mysql中的数值类型并不能实际限制填入数据的长度, 数值的范围仍然由对应的数值类型决定
这个宽度仅仅在字段被设置为zerofill时才有意义,用于指定补0后的最大宽度, 超出宽度同样可以插入数据, 只是不会再补0

#### 2. UNSIGNED属性
当某个数值字段需要保存较大的正数值范围时, 可以为字段加上这个属性
> 比如tinyint的取值范围为-128~+127, 当指定为UNSIGNED之后, 取值范围为0~255

> 当某个字段被指定为zerofill的时候会自动加上UNSIGNED属性

#### 3. 存储时间常用的类型
- DATE: 存储日期
- DATETIME: 存储日期+时间
- TIME: 存储时间
- YEAR: 存储年份

#### 4. char和varchar的区别
- char是定长的, 无论存储的数据多长都会保留字段所指定的宽度值, varchar则会随着存储数据的长度变化
- char范围为0-255, varchar长度为0-255(5.0.3前) 0-65535(5.0.3后)
- 查询时如果字符串尾部有空格, char会将其去掉, 而varchar则会保留尾部空格

#### 5. REGEXP
mysql可以通过REGEXP来对字符串进行正则匹配
例如:
```sql
SELECT * from t2 WHERE t_char REGEXP '^a'
```

#### 6. MyIsam存储引擎
MyIsam是mysql的默认存储引擎, 不支持事务和外键, 优势是访问的速度快, 对事务的完整性没有要求或者以新增和查询为主的场景可以使用该存储引擎
每个MyIsam表在磁盘上面存储为三个文件, 文件名和表名都相同, 但是后缀不同, 分别是:
- .frm 存储表定义
- .MYD 存储数据
- .MYI 存储索引
数据文件和索引文件可以放在不同的目录, 平均分布IO, 以获取更快的速度

#### 7. Innodb存储引擎
Innodb是唯一一个支持事务的存储引擎, 同时也是唯一一个支持外键的存储引擎
在定义表结构的时候, 可以设置当主表外键发生删除/更新的时候子表数据的处理方式
- RESTRICT/NO ACTION: 当子表有关联记录的时候, 限制父表不能进行更新/删除
- CASCADE: 当父表数据发生变动的时候, 同步更新/删除子表中外键关联到该记录的记录
- SET NULL: 父表数据发生变动的时候, 将子表对应的字段设置为NULL
> 注意使用外键的时候所有的表都必须为Innodb存储引擎

Innodb存储表和索引有两种方式
- 使用共享表空间进行存储, 这种方式创建的表结构保存在.frm文件中, 数据和索引分别保存在innodb_data_home_dir和innodb_data_file_path定义的表空间中, 可以是多个文件, 这种情况同一个库表的所有的表数据和索引文件都放在同一个表空间中, 由于可以分为多个文件, 所以此时innodb的单个表的大小不受文件系统限制
- 使用独占表空间进行存储, 这种方式, 每一个表都会生成一个独立的.ibd文件来存储表的索引和数据内容, 此时单表的数据大小受文件系统限制

#### 8. Memory存储引擎
该存储引擎使用内存来创建表, 每个Memory表只对应一个.frm磁盘文件(存储表结构, 数据存储在内存中), 该存储引擎类型的表访问非常快, 默认使用HASH索引, 但是一旦服务关闭, 就会丢失表中的数据
> 在启动mysql服务的时候可以使用--init-file选项, 把INSERT INTO ... SELECT或者LOAD DATA INFILE这样的语句放入到这个文件里面来在启动服务的时候从稳定的数据源装载Memory表

#### 9. MERGE存储引擎
MERGE存储引擎是一组MyIsam表的组合, 这些表的表结构必须**完全相同**, 可以进行的操作有:
- 删除: 实际删除对应的子表
- 更新: 实际更新对应的子表
- 插入: 只有指定了INSERT_METHOD才能进行插入操作, 只能选择插入到第一张表或者最后一张表(FIRST/LAST)
- 查询: 相当于查询所有表union后的值, 实际测试根据id进行条件查询会出问题

#### 10. 存储引擎的选择
- MyIsam: 适合读操作和插入操作为主的场景, 只有很少的更新和删除操作, 并且对事务的完整性和并发性要求不高
- Innodb: 对事务完整性要求比较高, 并且要求在并发条件下的数据一致性, 除此之外, Innodb还能有效减少由于删除和更新导致的锁定
- Memory: 通常用于更新不太频繁的小表, 用于快速得到结果
- Merge: 优点在于能突破导个MyIsam表大小的限制, 通过将不同的表分布在多个磁盘上, 可以有效改善Merge表的访问效率

#### 11. 数据类型的选择
##### char和varchar
由于char是定长的, 处理速度比varchar会快, 但是会浪费存储空间, 所以对于长度变化不大且对查询速度有较高要求的数据可以考虑用char来存储
- MyIsam: 建议使用固定长度的数据列代替可变长度的数据列
- Innodb: 建议使用varchar, 由于Innodb在内部存储的所有数据行都是使用指向数据列值的头指针, 所以char的性能在该引擎不一定比varchar要好, 主要影响性能因素的是s数据行使用的数据总量, 所以使用varchar来减少存储总量和c磁盘IO比较好
- Memory: 使用char和varchar都会被当做char类型来处理, 二者并无区别

#### 12. 前缀索引
也就是对字符串的前n个字符进行索引, 由于进行匹配的部分变短, 可能会导致扫描的记录增多, 一般在建立前缀索引之前, 会通过count函数来查看字段前n位数据的区分度, 以决定前缀索引的最佳长度
全列选择性：
```sql
SELECT COUNT(DISTINCT column_name) / COUNT(*) FROM table_name;
```
某一长度前缀的选择性：
```sql
SELECT COUNT(DISTINCT LEFT(column_name, prefix_length)) / COUNT(*) FROM table_name;
```
当两者越接近时, 前缀索引的长度越优

前缀索引的特点:
- 占用空间小而且快
- 无法使用前缀索引做GROUP BY和ORDER BY操作
- 无法使用前缀索引做覆盖扫描
- 有可能会增加扫描行数

> 存储身份证号的时候, 可以将身份证倒序存储, 然后取前6位做前缀索引(身份证后六位区分度远远大于前六位), 这样既节省了索引空间, 也能获得较好的索引区分度

#### 13. 索引设计的原则
- 最适合加在索引中的, 应该是出现在where子句中的列, 而不是出现在select子句中的列
- 建索引的列区分度越大越好, 如果是男/女这种区分度不高的字段建索引, 不管搜索哪个值都会得出接近一半的行数
- 索引字段为字符串类型时, 尽可能使用前缀索引来代替全索引, 这样能有效减少存储空间的浪费, 也能让mysql在同样大小的内存中缓存更多的索引值, 提高索引速度
- 利用最左前缀原则: 创建多列组合索引的时候, 位于左边的列集合都可以利用来匹配行
- 避免过度索引, 只针对查询优化来建立索引
- 对于innodb存储引擎的表来说, 记录是通过一定顺序来存储的, 如果有主键, 会按主键的顺序来进行存储, 如果没有主键, 但是存在唯一索引, 会根据唯一索引的顺序来进行存储, 如果既没有主键也没有唯一索引, innodb会生成一个内部列, 并且按照这个内部列的顺序来进行存储, 所以, innodb要尽量自己指定主键, 还要注意的一点是, innodb的普通索引也会存储主键的键值, 所以主键要选择尽可能短的数据类型, 这样可以有效的减少索引的磁盘占用, 提高缓存效果

#### 14. hash索引和btree索引
HASH索引和BTREE索引的区别:
- HASH索引只能用=或者<=>这种类型的全匹配等式来进行比较, 否则查询无法走索引, BTREE则没有限制
- 优化器不能使用HASH索引来加速ORDER BY操作(因为hash索引存储的是计算后的hash值, 不能保证有序性)
- 如果需要使用>, >=, <=, <, like, between, !=或者<>这种查询, 应该使用btree索引来代替hash索引
- hash索引在任何时候都不能避免表扫描(因为hash索引只存储了hash值, 不管怎么时候都要访问表数据并对比是否匹配, 因为hash值相等, 原字符串不一定相等)
- 通常情况下, hash索引只需要计算一次即可定位, 不用像btree索引一样从根节点到枝节点, 查询效率相对较高, 但是在hash碰撞较多的情况下, hash索引的效率不一定会比btree高
- 最左前缀原则不适用于hash索引(建立一个hash类型的组合索引, 是计算组合索引字段合并后的hash值, 取左边的字段子集查询并不能通过hash值来进行索引匹配), 故最左匹配原则在hash索引身上会失效

结论: 
在MySQL中，只有HEAP/MEMORY引擎表才能显式支持哈希索引,还需要注意到：HEAP/MEMORY引擎表在mysql实例重启后，数据会丢失。
通常，B+树索引结构适用于绝大多数场景，像下面这种场景用哈希索引才更有优势：
在HEAP表中，如果存储的数据重复度很低（也就是说基数很大），对该列数据以等值查询为主，没有范围查询、没有排序的时候，特别适合采用哈希索引
例如这种SQL：
```sql
SELECT … FROM t WHERE C1 = ?; #仅等值查询
```
在大多数场景下，都会有范围查询、排序、分组等查询特征，用B+树索引就可以了。

#### 15. 用rand()提取随机行
当需要提取随机数据的时候, 可以用下面的方式提取前n行随机数据
```sql
select * from table order by rand() limit n
```

#### 16. load命令导入数据优化
使用load命令的时候, 可以通过一定的设置来提高数据导入的速度
- MyIsam存储引擎(表非空的情况下): 
1. 先关闭表非唯一索引的更新
```sql
ALTER TABLE tbl_name DISABLE KEYS;
```
2. load指令导入数据
```sql
load data infile 'xxxxpath' into table table_name;
```
3. 开启非唯一索引的更新
```sql
ALTER TABLE tbl_name ENABLE KEYS;
```
- Innodb存储引擎:
1. 将导入的数据按主键顺序排列
2. 在导入数据前执行 SET UNIQUE_CHECKS=0，关闭唯一性校验，在导入结束后执行 SET UNIQUE_CHECKS=1，恢复唯一性校验
3. 导入前执行 SET AUTOCOMMIT=0，关闭自动提交，导入结束后再执行 SET AUTOCOMMIT=1，打开自动提交

#### 17. 优化insert语句
- 使用批量insert代替多条单个insert
- 使用INSERT DELAYED代替INSERT(让insert语句马上执行, 默认情况下放在队列中执行)
- 将索引和数据放在不同的磁盘中存储
- 用load data infile代替insert

#### 18. 优化group by语句
```sql
select * from table order by col1, col2
```
默认情况下mysql会对group by的字段进行排序, 上述语句类似于带了一个隐式的order by col1, col2
此时可以通过添加order by null来禁止排序, 提升查询速度

#### 19. 优化order by语句
```sql
SELECT [column1],[column2],…. FROM [TABLE] ORDER BY [sort];
# 在[sort]这个栏位上建立索引就可以实现利用索引进行order by 优化。

SELECT [column1],[column2],…. FROM [TABLE] WHERE [columnX] = [value] ORDER BY [sort];
# 建立一个联合索引(columnX,sort)来实现order by 优化。

# 注意：如果columnX对应多个值，如下面语句就无法利用索引来实现order by的优化
SELECT [column1],[column2],…. FROM [TABLE] WHERE [columnX] IN ([value1],[value2],…) ORDER BY[sort];

SELECT * FROM [table] WHERE uid=1 ORDER x,y LIMIT 0,10;
# 建立索引(uid,x,y)实现order by的优化,比建立(x,y,uid)索引效果要好得多。

```

不能利用索引优化排序的情况:
```sql
MySQL Order By不能使用索引来优化排序的情况
# 对不同的索引键做 ORDER BY ：(key1,key2分别建立索引)
SELECT * FROM t1 ORDER BY key1, key2;

# 在非连续的索引键部分上做 ORDER BY：(key_part1,key_part2建立联合索引;key2建立索引)
SELECT * FROM t1 WHERE key2=constant ORDER BY key_part2;

# 同时使用了 ASC 和 DESC：(key_part1,key_part2建立联合索引)
SELECT * FROM t1 ORDER BY key_part1 DESC, key_part2 ASC;

# 用于搜索记录的索引键和做 ORDER BY 的不是同一个：(key1,key2分别建立索引)
SELECT * FROM t1 WHERE key2=constant ORDER BY key1;

# 如果在WHERE和ORDER BY的栏位上应用表达式(函数)时，则无法利用索引来实现order by的优化
SELECT * FROM t1 ORDER BY YEAR(logindate) LIMIT 0,10;
```

#### 20. 子查询优化
如果可以的话尽量使用join来代替子查询, 由于子查询需要mysql中建立和销毁临时表, 效率相对较低

#### 21. or查询
假设有三个字段, col1, col2, col3
1. 当col1和col2分别建了独立索引时
```sql
# or连接的各个条件都有独立索引的情况
# myisam引擎能走索引(index_merge, 实际上查的是两个条件分别查询后union的结果)
# innodb不能走索引
select * from t wher col1 = 1 or col2 = 1

# or连接的条件里面有不含索引字段的情况
# 不管什么几把引擎都不能走索引
select * from t where col1 = 1 or col3 =1 
```