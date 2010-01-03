package com.jadn.cc.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jadn.cc.core.PlaySet;

/** Meta information about podcasts. **/
public class MetaHolder {

	public MetaHolder() {
		// start up.. load meta and scan podcast folder for user 'dropped
		// entries'
		loadMeta();
	}

	private List<MetaFile> metas = new ArrayList<MetaFile>();

	public List<MetaFile> getMeta() {
		return metas;
	}

	public void loadMeta() {
		File[] files = PlaySet.PODCASTS.getRoot().listFiles();
		if (files == null)
			return;

		for (File file : files) {
			if (file.getName().endsWith(".mp3")
					|| file.getName().endsWith(".3gp")) {				
				MetaFile meta = new MetaFile(file);
				metas.add(meta);
			}
		}
		Collections.sort(metas, new Comparator<MetaFile>() {
			@Override
			public int compare(MetaFile object1, MetaFile object2) {
				return new Long(object1.file.lastModified()).compareTo(new Long(
						object2.file.lastModified()));
			}
		});
		// playSetCache = (File[]) list.toArray(new File[list.size()]);
	}

	public int getSize() {
		return metas.size();
	}

	public MetaFile get(int current) {
		return metas.get(current);
	}

	public void delete(int i) {
		metas.get(i).delete();
		metas.remove(i);
	}

}
