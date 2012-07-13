package com.bottiger.cc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bottiger.cc.core.CarCastApplication;
import com.bottiger.cc.core.ContentServiceListener;
import com.bottiger.cc.core.Util;
import com.bottiger.cc.services.ContentService;
import com.bottiger.cc.services.DownloadHistory;
import com.bottiger.cc.services.MetaFile;
import com.bottiger.cc.services.MetaHolder;
import com.bottiger.cc.services.PlayStatusListener;
import com.bottiger.cc.R;

public class PodcastList extends Fragment implements ContentServiceListener, PlayStatusListener {

	View V;
	ContentService contentService;
	SimpleAdapter podcastsAdapter;
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			showPodcasts();
		}
	}*/

	protected void onContentService() {
		showPodcasts();
	}
	
	public void onContentServiceChanged(ContentService service) {
		if (contentService != null) {
			contentService.setPlayStatusListener(null);
		}
	    contentService = service;
	    if (service != null) {
	    	service.setPlayStatusListener(this);
            onContentService();
        }
	}
	
	@Override
	public void playStateUpdated(boolean playing) {
		// default implementation does nothing
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
		//setContentView(R.layout.podcast_list_wbar);

		//setTitle(CarCastApplication.getAppTitle() + ": Downloaded podcasts");
		
		View V = inflater.inflate(R.layout.podcast_list_wbar, container, false);

		Button deleteButton = (Button) V.findViewById(R.id.delete);
		/*deleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(PodcastList.this).setIcon(android.R.drawable.ic_dialog_alert)
						.setMessage("Delete " + checkedItems.size() + " podcasts?")
						.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (contentService.isPlaying())
									contentService.pauseNow();
								while (!checkedItems.isEmpty()) {
									contentService.deletePodcast(checkedItems.last());
									checkedItems.remove(checkedItems.last());
								}
								podcastsAdapter.notifyDataSetChanged();
								showPodcasts();
							}
						}).setNegativeButton("Cancel", null).show();
			};
		});*/
		return V;

		/*((Button) findViewById(R.id.top)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (contentService.isPlaying())
					contentService.pauseNow();
				checkedItems = contentService.moveTop(checkedItems);
				podcastsAdapter.notifyDataSetChanged();
				showPodcasts();
			};
		});*/

		/*((Button) findViewById(R.id.up)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (contentService.isPlaying())
					contentService.pauseNow();
				checkedItems = contentService.moveUp(checkedItems);
				podcastsAdapter.notifyDataSetChanged();
				showPodcasts();
			};
		});*/

		/*((Button) findViewById(R.id.down)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (contentService.isPlaying())
					contentService.pauseNow();
				checkedItems = contentService.moveDown(checkedItems);
				podcastsAdapter.notifyDataSetChanged();
				showPodcasts();
			};
		});

		((Button) findViewById(R.id.bottom)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (contentService.isPlaying())
					contentService.pauseNow();
				checkedItems = contentService.moveBottom(checkedItems);
				podcastsAdapter.notifyDataSetChanged();
				showPodcasts();
			};
		});*/
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		//super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Play");
		menu.add("Delete");
		menu.add("Delete All Before");
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater mInflater = new MenuInflater(getActivity().getApplicationContext());
		mInflater.inflate(R.menu.podcasts_menu, menu);
	}

	public void onMenuItemSelected(int featureId, MenuItem item) {

		if (item.getItemId() == R.id.deleteListenedTo) {
			String currTitle = "";
			currTitle = contentService.currentTitle();
			MetaHolder metaHolder = new MetaHolder();
			for (int i = metaHolder.getSize() - 1; i >= 0; i--) {
				MetaFile metaFile = metaHolder.get(i);
				if (currTitle.equals(metaFile.getTitle())) {
					continue;
				}
				if (metaFile.getDuration() <= 0) {
					continue;
				}
				if (metaFile.isListenedTo()) {
					contentService.deletePodcast(i);
					list.remove(i);
				}
			}
			podcastsAdapter.notifyDataSetChanged();

		} else if (item.getItemId() == R.id.deleteAllPodcasts) {

			// Ask the user if they want to really delete all
			/*new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete All?")
					.setMessage("Do you really want to delete all downloaded podcasts?")
					.setPositiveButton("Confirm Delete All", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							contentService.purgeAll();
							list.clear();
							podcastsAdapter.notifyDataSetChanged();
							finish();
						}

					}).setNegativeButton("Cancel", null).show();
			*/
		}
		if (item.getItemId() == R.id.eraseDownloadHistory) {
			/*new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setMessage("Erase Download History?")
					.setPositiveButton("Erase", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int historyDeleted = DownloadHistory.getInstance().eraseHistory();
							Util.toast(PodcastList.this, "Erased " + historyDeleted + " podcast from dowload history.");
						}

					}).setNegativeButton("Cancel", null).show();
			*/
		}
		//return super.onMenuItemSelected(featureId, item);
	}

	protected void showPodcasts() {

		ListView listView = (ListView) V.findViewById(R.id.list);

		MetaHolder metaHolder = new MetaHolder();
		list.clear();

		for (int i = 0; i < metaHolder.getSize(); i++) {
			MetaFile metaFile = metaHolder.get(i);
			HashMap<String, String> item = new HashMap<String, String>();
			if (contentService.currentTitle().equals(metaFile.getTitle())) {
				if (contentService.isPlaying()) {
					item.put("line1", "> " + metaFile.getFeedName());
				} else {
					item.put("line1", "|| " + metaFile.getFeedName());
				}
			} else {
				item.put("line1", metaFile.getFeedName());
			}
			String time = ContentService.getTimeString(metaFile.getCurrentPos()) + "-"
					+ ContentService.getTimeString(metaFile.getDuration());
			if (metaFile.getCurrentPos() == 0 && metaFile.getDuration() == -1) {
				time = "";
			}
			if (metaFile.isListenedTo()) {
				item.put("listened", "true");
				time = "End" + "-" + ContentService.getTimeString(metaFile.getDuration());
			}
			item.put("xx:xx-xx:xx", time);
			item.put("line2", metaFile.getTitle());
			list.add(item);

		}

		// When doing a delete before, we rebuild the list, but the adapter is
		// ok.
		/*
		if (podcastsAdapter == null) {
			podcastsAdapter = new SimpleAdapter(this, list,
			// R.layout.main_item_two_line_row, new String[] { "line1",
			// "line2" }, new int[] { R.id.text1, R.id.text2 });
					R.layout.podcast_items_checks, new String[] { "line1", "xx:xx-xx:xx", "line2" }, new int[] { R.id.firstLine,
							R.id.amountHeard, R.id.secondLine }) {
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					View view = super.getView(position, convertView, parent);
					Map map = (Map) getItem(position);
					if (map.get("listened") != null) {
						view.setBackgroundColor(Color.rgb(0, 70, 70));
					} else {
						view.setBackgroundColor(Color.TRANSPARENT);
					}
					final CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkBox1);
					checkbox.setOnClickListener(checkBoxClicked);
					// ((TextView)view.findViewById(R.id.firstLine)).setOnClickListener(itemClicked);
					// ((TextView)view.findViewById(R.id.secondLine)).setOnClickListener(itemClicked);
					view.setOnClickListener(itemClicked);
					view.setOnLongClickListener(itemLongClicked);

					Tag tag = (Tag) view.getTag();
					if (tag == null) {
						view.setTag(tag = new Tag());
					}
					tag.position = position;
					tag.item = map;

					checkbox.setChecked(checkedItems.contains(position));
					return view;
				}
			};

			listView.setAdapter(podcastsAdapter);
		} else {
			podcastsAdapter.notifyDataSetChanged();
		}
		*/

	}

	SortedSet<Integer> checkedItems = new TreeSet<Integer>();

	class Tag {
		int position;
		Map item;
	};

	OnClickListener checkBoxClicked = new OnClickListener() {
		public void onClick(View v) {
			final CheckBox checkbox = (CheckBox) v;
			View pView = (View) v.getParent();
			Tag tag = (Tag) pView.getTag();
			if (checkbox.isChecked()) {
				checkedItems.add(tag.position);
			} else {
				checkedItems.remove(tag.position);
			}
			// v.getTag()
			for (Button button : getBarButtons()) {
				button.setEnabled(!checkedItems.isEmpty());
			}
		}

	};

	public List<Button> getBarButtons() {
		List<Button> barButtons = new ArrayList<Button>();
		barButtons.add((Button) V.findViewById(R.id.delete));
		barButtons.add((Button) V.findViewById(R.id.top));
		barButtons.add((Button) V.findViewById(R.id.up));
		barButtons.add((Button) V.findViewById(R.id.down));
		barButtons.add((Button) V.findViewById(R.id.bottom));
		return barButtons;
	}

	OnClickListener itemClicked = new OnClickListener() {
		public void onClick(View v) {
			Tag tag = (Tag) v.getTag();

			MetaHolder metaHolder = new MetaHolder();
			MetaFile mfile = metaHolder.get(tag.position);

			if (mfile.getTitle().equals(contentService.currentTitle())) {
				contentService.pauseOrPlay();
			} else {
				// This saves our position
				if (contentService.isPlaying())
					contentService.pauseNow();
				contentService.play(tag.position);
			}
			showPodcasts();
		}
	};

	OnLongClickListener itemLongClicked = new OnLongClickListener() {
		public boolean onLongClick(View v) {
			final Tag tag = (Tag) v.getTag();

			final MetaHolder metaHolder = new MetaHolder();
			final MetaFile mfile = metaHolder.get(tag.position);

			// Ask the user if they want to really delete all
			/*
			new AlertDialog.Builder(PodcastList.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Before?")
					.setMessage("Delete all before " + mfile.getTitle())
					.setPositiveButton("Confirm Delete " + tag.position + " podcasts", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (contentService.isPlaying())
								contentService.pauseNow();

							while ((tag.position--) != 0) {
								contentService.deletePodcast(0);
							}

							podcastsAdapter.notifyDataSetChanged();
						}

					}).setNegativeButton("Cancel", null).show();
			*/
			return true;
		}
	};

}
