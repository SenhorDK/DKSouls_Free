package me.senhordk.dkalmas.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class MySQL {

	public static void criarTabela() {

		try {

			Connection con = abrirConecao();

			PreparedStatement st = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS `DKSouls`("
					        + "Nick VARCHAR(55),"
					        + "Souls INT"
					        + ", PRIMARY KEY(Nick)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
			st.executeUpdate();

			con.close();
			Bukkit.getConsoleSender().sendMessage("§6[DKSouls]§aMYSQL Connection successful!");
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§6[DKSouls]§cCould not connect to database, one or more information should be invalid!");
		}

	}

	public static void addJogador(Player player) {
		String Nick = player.getName();
		try {
			Connection con = abrirConecao();
			PreparedStatement st = con.prepareStatement("INSERT INTO DKSouls VALUES (?,0);");
			st.setString(1, Nick.toString());
			st.executeUpdate();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static int getSouls(Player player) {
		int value = 0;
		try {
			String Nick = player.getName();
			try {
				Connection con = abrirConecao();
				PreparedStatement st = con.prepareStatement("SELECT Souls FROM DKSouls WHERE Nick = ?;");
				st.setString(1, Nick.toString());
				ResultSet rs = st.executeQuery();

				if (rs.next()) {
					value = rs.getInt("Souls");
				}

				con.close();

			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
		}
		return value;
	}
	public static int getSoulsOf(OfflinePlayer offline) {
		int value = 0;
		try {
			String Nick = offline.getName();
			try {
				Connection con = abrirConecao();
				PreparedStatement st = con.prepareStatement("SELECT Souls FROM DKSouls WHERE Nick = ?;");
				st.setString(1, Nick.toString());
				ResultSet rs = st.executeQuery();

				if (rs.next()) {
					value = rs.getInt("Souls");
				}

				con.close();

			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
		}
		return value;
	}
	public static boolean hasJogador(Player player) {
		try {
			String Nick = player.getName();
			try {
				Connection con = abrirConecao();
				PreparedStatement st = con.prepareStatement("SELECT Souls FROM DKSouls WHERE Nick = ?;");
				st.setString(1, Nick.toString());
				ResultSet rs = st.executeQuery();
				boolean result = rs.next();
				con.close();
				return result;

			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static void setJogador(Player player, int Souls) {
		String Nick = player.getName();
		try {
			Connection con = abrirConecao();
			PreparedStatement st = con.prepareStatement("UPDATE DKSouls SET Souls = ? WHERE Nick = ?;");
			st.setString(2, Nick.toString());
			st.setInt(1, Souls);
			st.executeUpdate();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	public static void setJogadorOf(OfflinePlayer offline, int Souls) {
		String Nick = offline.getName();
		try {
			Connection con = abrirConecao();
			PreparedStatement st = con.prepareStatement("UPDATE DKSouls SET Souls = ? WHERE Nick = ?;");
			st.setString(2, Nick.toString());
			st.setInt(1, Souls);
			st.executeUpdate();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void addSouls(Player p, int Souls) {
		String Nick = p.getName();
		try {
			Connection con = abrirConecao();
			PreparedStatement st = con.prepareStatement("UPDATE DKSouls SET Souls = ? WHERE Nick = ?;");
			int value = MySQL.getSouls(p);
			st.setString(2, Nick.toString());
			st.setInt(1, value + Souls);
			st.executeUpdate();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public static void removeSouls(Player p, int souls) {
		String Nick = p.getName();
		try {
			Connection con = abrirConecao();
			PreparedStatement st = con.prepareStatement("UPDATE DKSouls SET Souls = ? WHERE Nick = ?;");
			int value = MySQL.getSouls(p);
			st.setString(2, Nick.toString());
			st.setInt(1, value - souls);
			st.executeUpdate();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static Connection abrirConecao() {
	    final ConfigUtils ca = ConfigUtils.getConfig(new File("plugins/DKSouls/config.yml"));
		String storage = ca.getConfig().getString("storage");
	    if (storage.equalsIgnoreCase("MySQL")) {
		try {
			String password = ca.getConfig().getString("mysql.password");
			String user = ca.getConfig().getString("mysql.username");
			String host = ca.getConfig().getString("mysql.host");
			String port = ca.getConfig().getString("mysql.port");
			String database = ca.getConfig().getString("mysql.dbname");
			String type = "jdbc:mysql://";
			String url = type + host + ":" + port + "/" + database;
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
		}
	    }
		return null;

	}

}