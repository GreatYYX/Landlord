package com.watch0ut.landlord.server.database;

import com.watch0ut.landlord.object.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by GreatYYX on 2/18/16.
 *
 * DB业务逻辑
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private DatabaseOperator db_ = null;

    public DatabaseHelper() {
        db_ = new DatabaseOperator();
    }

    public Player login(String user, String pwd, String ip) {
        Player player = null;
        try {
            // 登陆（需pgcrypt模块支持）
            PreparedStatement pst = db_.getConn().prepareStatement(
                    "SELECT * FROM player WHERE username=? AND password=crypt(?,password) LIMIT 1");
            pst.setString(1, user);
            pst.setString(2, pwd);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                player = new Player(rs.getInt("id"), rs.getString("username"), rs.getString("nickname"),
                       rs.getString("photo"), rs.getInt("score"), rs.getInt("roundCount"),
                       rs.getInt("winCount"), rs.getInt("loseCount"), rs.getInt("landlordCount"));
                // 更新信息
                pst = db_.getConn().prepareStatement(
                        "UPDATE player SET lastloginip=?, lastlogintime=now() WHERE username=?");
                pst.setString(1, ip);
                pst.setString(2, user);
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            player = null;
        }

        return player;
    }
}
