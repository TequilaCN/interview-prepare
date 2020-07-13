# 设计模式学习笔记
## 1. 设计模式的七大原则
### 1.1 单一职责原则
一个类只负责一项职责, 假设一个类A负责两个职责, 在职责1变更的时候可能会改变职责2, 此时应该将A分为A1, A2, 分别负责单一的职责
### 1.2  接口隔离原则
客户端不应该依赖它不需要的接口, 即一个类对另外一个类的依赖应该建立在最小的接口上
### 1.3 依赖倒转原则
 1) 高层模块不应该依赖底层模块, 二者都应该依赖其抽象
 2) 抽象不应该依赖细节, 细节应该依赖抽象
 3) 依赖倒转的中心思想是面向接口编程
### 1.4 里氏替换原则
1) 所有引用基类的地方必须能透明的使用其子类的对象
2) 在子类中尽量不要重写父类的方法
3) 继承使得类之间的耦合增强, 可以考虑通过聚合, 组合, 依赖来解决问题
### 1.5 开闭原则
 一个软件实体比如类, 模块和函数应该对扩展开放, 对修改关闭, 用抽象构建框架, 用实现扩展细节, 当软件需要变化时, 通过扩展软件实体的行为来实现变化, 而不是通过修改已有的代码
 来实现变化
 ### 1.6 迪米特法则
 1) 一个对象应该对其他对象保持最少的了解
 2) 类与类之间关系越密切, 耦合度越大
 3) 迪米特法则又叫最少知道原则, 也就是说对于依赖的类不管多么复杂, 都尽量将逻辑封装在类的内部, 对外除了提供public方法, 不对外泄漏任何信息