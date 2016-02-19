# watch0ut版湛江斗地主

为满足watch0ut旅行小分队打牌需求而创建，bin版仅提供小分队成员玩耍。

采用Java语言编写。一期计划实现服务器端及桌面客户端，二期计划实现安卓客户端。

# 技术概况

- 服务器基于Apache MINA 2，采用StateMachine驱动。数据库为PostGreSQL（开启pgcrypt）。
- 客户端使用JavaFX 2编写图形界面。

# 游戏规则明细

撰写：GreatYYX 审定：JackPan

## 玩家人数及角色
- 人数4人。
- 拿到黑桃A为小地主，黑桃3为大地主，同时拿到黑桃A和黑桃3则自为地主。无身份牌者为农民。

## 发牌出牌顺序及牌数
- 逆时针发牌。
- 顺时针出牌。
- 一副牌去除大小王，共52张。

## 合法牌型及大小比较
- 合法出牌数为1张、2张、3张或5张。
- 涉及点数比较时：4<5<6<7<8<9<10<J<Q<K<A<2<3。
- 涉及花色比较时：方片<草花<红桃<黑桃。
- 1张合法牌型为单片，遵循点数优先比较策略。
- 2张合法牌型为对子，遵循点数优先比较策略。
- 3张合法牌型为3线，遵循点数优先比较策略。
- 5张合法牌型有5种
	- 顺子（不包含同花），遵循点数优先比较策略。
	- 同花（不包含顺子），遵循花色优先比较策略。
	- 三带二，遵循点数优先比较策略。
	- 四带一，遵循点数优先比较策略。
	- 同花顺，遵循点数优先比较策略。
- 5张合法牌中顺子<同花<三带二<四带一<同花顺，此为5张牌比较最高优先级。
- 5张牌构成顺子或同花顺时，A2345<45678<…<10JQKA，A只能出现在开头或结尾。

## 出牌规则
- 持有方片4者第一个出牌，必须出含有方片4的合法牌型。
- 当上家出牌为n张时，下家必须出n张牌的合法牌形，且大于上家。
- 当一家逃出后，如果没有人接牌，则下家接风。

## 贡牌、还牌规则
- 自成地主（一地主三农民）
	- 第一个逃出被贡6张（农民一人2张）。
	- 第二个逃出被贡3张（农民一人1张）。
	- 第三个逃出贡3张。
	- 第四个逃出贡6张。
- 两地主两农民
	- 某角色第一、第二个逃出则另一角色两人一人贡2张。
	- 某角色第一、第三个逃出则另一角色两人一人贡1张。
	- 某角色第一、第四个逃出，则平局，不贡牌。
- 贡牌需贡除了身份牌（黑桃3、黑桃A）以外的最大牌。
- 还牌数量必须与收取的贡牌数量相同，牌随意。
- 贡牌还牌均不可包含特殊牌（黑桃3、黑桃A、方片4）。 
- 贡牌选取顺序：先逃出的先选，每轮选择限选一张，按照该顺序直到选完。
- 还牌选取顺序：贡牌大者先选，每轮选择限选一张，按照该顺序直到选完。

## watch0ut特殊规则
- watch0ut版会开发友情模式，随意选择贡牌和还牌。
