package com.otitan.xnbhq.entity.ym;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "YMDC_TB".
 */
public class YmDao extends AbstractDao<Ym, String>
{

	public static final String TABLENAME = "YMDC_TB";

	/**
	 * Properties of entity Ym.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties
	{
		public final static Property YDH = new Property(0, String.class, "YDH",
				false, "YDH");
		public final static Property YMBH = new Property(1, String.class,
				"YMBH", true, "YMBH");
		public final static Property SZDM = new Property(2, String.class,
				"SZDM", false, "SZDM");
		public final static Property XIONGJING = new Property(3, double.class,
				"XIONGJING", false, "XIONGJING");
		public final static Property PJMSG = new Property(4, Double.class,
				"PJMSG", false, "PJMSG");
		public final static Property LMZL = new Property(5, String.class,
				"LMZL", false, "LMZL");
		public final static Property LMXL = new Property(6, String.class,
				"LMXL", false, "LMXL");
		public final static Property SSLC = new Property(7, String.class,
				"SSLC", false, "SSLC");
		public final static Property BEIZHU = new Property(8, String.class,
				"BEIZHU", false, "BEIZHU");
	};

	public YmDao(DaoConfig config)
	{
		super(config);
	}

	public YmDao(DaoConfig config, DaoSession daoSession)
	{
		super(config, daoSession);
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists)
	{
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "\"YMDC_TB\" (" + //
				"\"YDH\" TEXT NOT NULL ," + // 0: YDH
				"\"YMBH\" TEXT PRIMARY KEY NOT NULL ," + // 1: YMBH
				"\"SZDM\" TEXT NOT NULL ," + // 2: SZDM
				"\"XIONGJING\" REAL NOT NULL ," + // 3: XIONGJING
				"\"PJMSG\" REAL," + // 4: PJMSG
				"\"LMZL\" TEXT NOT NULL ," + // 5: LMZL
				"\"LMXL\" TEXT NOT NULL ," + // 6: LMXL
				"\"SSLC\" TEXT NOT NULL ," + // 7: SSLC
				"\"BEIZHU\" TEXT);"); // 8: BEIZHU
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists)
	{
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "")
				+ "\"YMDC_TB\"";
		db.execSQL(sql);
	}

	/** @inheritdoc */
	@Override
	protected void bindValues(SQLiteStatement stmt, Ym entity)
	{
		stmt.clearBindings();
		stmt.bindString(1, entity.getYDH());

		String YMBH = entity.getYMBH();
		if (YMBH != null)
		{
			stmt.bindString(2, YMBH);
		}
		stmt.bindString(3, entity.getSZDM());
		stmt.bindDouble(4, entity.getXIONGJING());

		Double PJMSG = entity.getPJMSG();
		if (PJMSG != null)
		{
			stmt.bindDouble(5, PJMSG);
		}
		stmt.bindString(6, entity.getLMZL());
		stmt.bindString(7, entity.getLMXL());
		stmt.bindString(8, entity.getSSLC());

		String BEIZHU = entity.getBEIZHU();
		if (BEIZHU != null)
		{
			stmt.bindString(9, BEIZHU);
		}
	}

	/** @inheritdoc */
	@Override
	public String readKey(Cursor cursor, int offset)
	{
		return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
	}

	/** @inheritdoc */
	@Override
	public Ym readEntity(Cursor cursor, int offset)
	{
		Ym entity = new Ym(
				//
				cursor.getString(offset + 0), // YDH
				cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // YMBH
				cursor.getString(offset + 2), // SZDM
				cursor.getDouble(offset + 3), // XIONGJING
				cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // PJMSG
				cursor.getString(offset + 5), // LMZL
				cursor.getString(offset + 6), // LMXL
				cursor.getString(offset + 7), // SSLC
				cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // BEIZHU
		);
		return entity;
	}

	/** @inheritdoc */
	@Override
	public void readEntity(Cursor cursor, Ym entity, int offset)
	{
		entity.setYDH(cursor.getString(offset + 0));
		entity.setYMBH(cursor.isNull(offset + 1) ? null : cursor
				.getString(offset + 1));
		entity.setSZDM(cursor.getString(offset + 2));
		entity.setXIONGJING(cursor.getDouble(offset + 3));
		entity.setPJMSG(cursor.isNull(offset + 4) ? null : cursor
				.getDouble(offset + 4));
		entity.setLMZL(cursor.getString(offset + 5));
		entity.setLMXL(cursor.getString(offset + 6));
		entity.setSSLC(cursor.getString(offset + 7));
		entity.setBEIZHU(cursor.isNull(offset + 8) ? null : cursor
				.getString(offset + 8));
	}

	/** @inheritdoc */
	@Override
	protected String updateKeyAfterInsert(Ym entity, long rowId)
	{
		return entity.getYMBH();
	}

	/** @inheritdoc */
	@Override
	public String getKey(Ym entity)
	{
		if (entity != null)
		{
			return entity.getYMBH();
		} else
		{
			return null;
		}
	}

	/** @inheritdoc */
	@Override
	protected boolean isEntityUpdateable()
	{
		return true;
	}

}
