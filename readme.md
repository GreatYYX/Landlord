# watch0ut版湛江斗地主

为满足watch0ut旅行小分队打牌需求而创建，bin版仅提供小分队成员玩耍。
采用Java语言编写。一期计划实现服务器端及桌面客户端，二期计划实现安卓客户端。

# 技术概况

- 服务器基于Apache MINA 2，数据库为PostGreSQL（开启pgcrypto）。
- 客户端使用JavaFX 2编写图形界面。

## 三方库

- mina-core-2.0.9 (commons-lang, mina-core, mina-statemachine, slf4j-api, slf4j-simple)
- postgresql-9.5

# 游戏规则明细
参考[rule.md](rule.md)。

