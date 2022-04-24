package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.DBConnection;

public class createData {

	public Connection getconnection() {
		Connection conn = null;
		
		DBConnection DBc = DBConnection.getinstance();
		
		conn = DBc.getconnection();

		return conn;
	}
	
	public void closeSQL(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<String[]> checkData(String s_date, String e_date, String c_num)
	{
		ArrayList<String[]> result_data = new ArrayList<>();
		Connection con = getconnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			String sql =
					"select a.member_no                                                                                                                                          as member_no,  " +
					"(select nm from cm010 where member_no = a.member_no)                                                                                                        as nm, " +
					"count(distinct a.card_no)                                                                                                                                   as counting,  " +
					"(select LISTAGG(c.memo, ' / ') within group (order by d.member_no) from cm082 c, cm081 d where d.member_no = a.member_no and c.member_flag = d.member_flag) as member_flag " +
					"from sl063 a, TL_LOG_APPRO b " +
					"where a.card_no is not null " +
					"and a.member_no is not null " +
					"and a.member_no not in ('11011279','11011281') " +
					"and a.str_code = b.str_cd " +
					"and a.sale_ymd = b.sale_dy " +
					"and a.pos_no = b.pos_no " +
					"and a.trxn_no = b.trd_no " +
					"and b.content_fg = 'S' " +
					"and b.wcc like 'I_' " +
					"and a.sale_ymd between ? and ? " +
					"group by a.member_no " +
					"having count(distinct a.card_no) >= ? " +
					"order by count(distinct a.card_no) desc ";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, s_date);
			pstmt.setString(2, e_date);
			pstmt.setString(3, c_num);
			
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] in_list = new String[4];
				for(int i = 1; i < 5; i++)
				{
					in_list[i-1] = rs.getString(i);
				}
				result_data.add(in_list);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeSQL(con, pstmt, rs);
		}
		return result_data;
	}
	
	public ArrayList<String[]> checkDetail(String s, String s_date, String e_date)
	{
		ArrayList<String[]> result_data = new ArrayList<>();
		Connection con = getconnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			String sql =
					"SELECT MEMBER_NO,                          " +
							"       NM,                                " +
							"       CARD_NO ,                            " +
							"       SALE_YMD ,                          " +
							"       STR_CODE  ,                          " +
							"       POS_NO      ,                       " +
							"       TRXN_NO   ,                         " +
							"       SEQ              ,                  " +
							"       CANC_FLAG    ,                     " +
							"       PAY_UTYPE      ,                    " +
							"       CLASS_NAME    ,                    " +
							"       SAL_AMT          ,                  " +
							"       TRXN_NO_OLD                    " +
							"FROM  " +
							"        (SELECT A.MEMBER_NO                                                                                            AS MEMBER_NO,  " +
							"               (SELECT NM FROM CM010 WHERE MEMBER_NO = A.MEMBER_NO)                                                   AS NM,  " +
							"               A.MEMBER_CARD_NO                                                                                       AS MEMBER_CARD_NO,  " +
							"               (select card_no  " +
							"                  from oc043 " +
							"                 where sale_ymd = b.sale_ymd  " +
							"                   and DECODE(stjoin_code,'01','11','02','11','03','11','04','14','12','33','13','34',NULL) = b.str_code " +
							"                   and pos_no = b.pos_no " +
							"                   and trxn_no = b.trxn_no " +
							"                   and seq = b.seq " +
							"               ) " +
							"                                                                                              AS CARD_NO,  " +
							"               B.SALE_YMD                                                                                             AS SALE_YMD,  " +
							"               B.STR_CODE                                                                                             AS STR_CODE,  " +
							"               B.POS_NO                                                                                               AS POS_NO,  " +
							"               B.TRXN_NO                                                                                              AS TRXN_NO,  " +
							"               B.SEQ                                                                                                  AS SEQ,  " +
							"               CASE B.CANC_FLAG WHEN '0' THEN '[0]정상' WHEN '2' THEN '[2]반품' ELSE '[1]취소' END                             AS CANC_FLAG,  " +
							"               CASE B.PAY_UTYPE WHEN '01' THEN '현금' WHEN '04' THEN '외상' WHEN '08' THEN '사용'  " +
							"                                WHEN '11' THEN '잔전현금' WHEN '12' THEN 'CMS쿠폰' WHEN '18' THEN '포인트사용'  " +
							"                                WHEN '19' THEN 'T포인트' WHEN '20' THEN '예금' WHEN '31' THEN '장바구니'  " +
							"                                WHEN '32' THEN '식품구매권' WHEN '33' THEN '타사카드' WHEN '34' THEN 'COD발생'  " +
							"                                WHEN '38' THEN 'GIFT카드' WHEN '92' THEN '타사상품권' WHEN '93' THEN '자사상품권'  " +
							"                                ELSE B.PAY_UTYPE END                                                                  AS PAY_UTYPE,  " +
							"               (SELECT CLASS_NAME FROM CD060 WHERE STR_CODE = B.STR_CODE AND CLASS_CODE = B.CLASS_CODE)               AS CLASS_NAME,  " +
							"               CASE B.CANC_FLAG WHEN '2' THEN (B.PAY_AMT-B.CHG_AMT)*(-1)  " +
							"                                WHEN '1' THEN 0 ELSE (B.PAY_AMT-B.CHG_AMT) END                                        AS SAL_AMT,  " +
							"               A.TRXN_NO_OLD                                                                                          AS TRXN_NO_OLD  " +
							"        FROM (  " +
							"               SELECT CASE WHEN CANC_FLAG='0' AND TRXN_NO_OLD IS NOT NULL THEN SUBSTR(TRXN_NO_OLD,1,2)  ELSE STR_CODE    END AS STR_CODE,  " +
							"                      CASE WHEN CANC_FLAG='0' AND TRXN_NO_OLD IS NOT NULL THEN SUBSTR(TRXN_NO_OLD,3,8)  ELSE SALE_YMD    END AS SALE_YMD,  " +
							"                      CASE WHEN CANC_FLAG='0' AND TRXN_NO_OLD IS NOT NULL THEN SUBSTR(TRXN_NO_OLD,11,4) ELSE POS_NO      END AS POS_NO,  " +
							"                      CASE WHEN CANC_FLAG='0' AND TRXN_NO_OLD IS NOT NULL THEN SUBSTR(TRXN_NO_OLD,15,6) ELSE TRXN_NO     END AS TRXN_NO,  " +
							"                      CANC_FLAG, MEMBER_NO, MEMBER_CARD_NO, SAL_POINT, SAL_UPOINT, USE_POINT,  " +
							"                      CASE WHEN CANC_FLAG='0' AND TRXN_NO_OLD IS NOT NULL THEN TRXN_NO_OLD||' (사후적립)' ELSE TRXN_NO_OLD END AS TRXN_NO_OLD  " +
							"               FROM SL610  " +
							"               WHERE MEMBER_NO = ? " +
							"             )          A,  " +
							"             SL063      B  " +
							"        WHERE A.STR_CODE = B.STR_CODE  " +
							"        AND A.SALE_YMD = B.SALE_YMD  " +
							"        AND A.POS_NO = B.POS_NO  " +
							"        AND A.TRXN_NO = B.TRXN_NO  " +
							"        AND B.SALE_YMD BETWEEN ? AND ? " +
							"     )        " +
							"ORDER BY MEMBER_NO, SALE_YMD, TRXN_NO, SEQ ";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, s);
			pstmt.setString(2, s_date);
			pstmt.setString(3, e_date);
			
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] in_list = new String[13];
				for(int i = 1; i < 14; i++)
				{
					in_list[i-1] = rs.getString(i);
				}
				result_data.add(in_list);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeSQL(con, pstmt, rs);
		}
		return result_data;
	}
	
}















