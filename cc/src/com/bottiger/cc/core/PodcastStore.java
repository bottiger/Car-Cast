package com.bottiger.cc.core;

import com.bottiger.cc.services.Episode;
import com.bottiger.cc.services.EpisodeOnlineData;
import com.bottiger.cc.ui.BaseFragmentActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PodcastStore extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String EPISODE_TABLE_NAME = "episodes";
	private static final String EPISODE_COLUMN_ID = "_id";
	private static final String EPISODE_COLUMN_TITLE = "title";
	private static final String EPISODE_COLUMN_PUBLISHER = "publisher";
	private static final String EPISODE_COLUMN_DESCRIPTION = "description";
	private static final String EPISODE_COLUMN_FEED = "feed"; // podcast feed
	private static final String EPISODE_COLUMN_URL = "url"; // episode URL
	private static final String EPISODE_COLUMN_FILENAME = "filename"; // episode URL
	private static final String EPISODE_COLUMN_SIZE = "size";
	private static final String EPISODE_COLUMN_MIMETYPE = "mimetype";
	private static final String EPISODE_COLUMN_DURATION = "duration";
	private static final String EPISODE_COLUMN_ICON_ID = "icon";
	private static final String EPISODE_COLUMN_PUBDATE = "published_at";
	private static final String EPISODE_COLUMN_DOWNLOAD_DATE = "downloaded_at";
	private static final String EPISODE_COLUMN_CURRENT_POS = "current_position";
	private static final String EPISODE_COLUMN_HAS_LISTENED = "has_listened";

	private static final String EPISODE_TABLE_CREATE = "CREATE TABLE "
			+ EPISODE_TABLE_NAME + " (" + EPISODE_COLUMN_ID
			+ "INTEGER primary key autoincrement, " + EPISODE_COLUMN_TITLE
			+ " TEXT," + EPISODE_COLUMN_PUBLISHER + " TEXT,"
			+ EPISODE_COLUMN_DESCRIPTION + " TEXT," + EPISODE_COLUMN_FILENAME + " TEXT," + EPISODE_COLUMN_FEED
			+ " TEXT," + EPISODE_COLUMN_URL + " TEXT," + EPISODE_COLUMN_SIZE
			+ " INTEGER," + EPISODE_COLUMN_MIMETYPE + " TEXT,"
			+ EPISODE_COLUMN_DURATION + " INTEGER," + EPISODE_COLUMN_ICON_ID
			+ " INTEGER," + EPISODE_COLUMN_PUBDATE + " DATETIME,"
			+ EPISODE_COLUMN_DOWNLOAD_DATE + " DATETIME,"
			+ EPISODE_COLUMN_CURRENT_POS + " INTEGER,"
			+ EPISODE_COLUMN_HAS_LISTENED + " INTEGER," + 
			" UNIQUE(" + EPISODE_COLUMN_URL + ") );";

	public PodcastStore() {
		super(CarCastApplication.getContext(), EPISODE_TABLE_NAME, null, DATABASE_VERSION);
	}

	public long addEpisode(Episode episode) {
		SQLiteDatabase db = this.getWritableDatabase();

		// Inserting Row
		long id = db.insert(EPISODE_TABLE_NAME, null, asContentValues(episode));
		db.close(); // Closing database connection
		return id;
	}
	
	public Episode getEpisode(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(EPISODE_TABLE_NAME, new String[] { EPISODE_COLUMN_TITLE, EPISODE_COLUMN_FEED,
				EPISODE_COLUMN_CURRENT_POS }, EPISODE_COLUMN_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
		
		if (cursor != null)
	        cursor.moveToFirst();
		
		String title = cursor.getString(0);
		String feed = cursor.getString(1);
		int currentPos = Integer.parseInt(cursor.getString(2));
		
		Episode episode = new Episode(title, feed, currentPos);
		return episode;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(EPISODE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private ContentValues asContentValues(Episode episode) {
		ContentValues values = new ContentValues();
	    values.put("EPISODE_COLUMN_TITLE", episode.getTitle());
	    values.put("EPISODE_COLUMN_URL", episode.getUrl());
	    values.put("EPISODE_COLUMN_CURRENT_POS", episode.getCurrentPos());
	    values.put("EPISODE_COLUMN_FEED", episode.getFeedName());
		return values;
	}

}
