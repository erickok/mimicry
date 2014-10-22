package nl.nl2312.mimicry.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * An adapter that wraps a normal {@link ListAdapter} and presents it as a grid
 * instead. It is especially useful if it is combined with normal list adapters
 * as {@link GridView} can not be used in this case.
 * 
 * @author Eric Kok
 */
public class MimicryAdapter extends BaseAdapter {

	private final Context context;
	private final int columnCount;
	private final ListAdapter wrappedAdapter;
	private OnMimicryItemClickedListener onMimicryItemClicked = null;

	public MimicryAdapter(Context context, int columnCount,
			ListAdapter wrappedAdapter) {
		this.context = context;
		this.columnCount = columnCount;
		this.wrappedAdapter = wrappedAdapter;
		if (wrappedAdapter.getViewTypeCount() > 1) {
			throw new RuntimeException(
					"Mimicry currently only supports one view type per wrapped adapter.");
		}
	}

	public void setOnMimicryItemClicked(
			OnMimicryItemClickedListener onGridMimicItemClicked) {
		this.onMimicryItemClicked = onGridMimicItemClicked;
	}

	@Override
	public int getCount() {
		// Returns the number of rows we need to represent all items in
		// columnCount columns
		return (int) Math
				.ceil(wrappedAdapter.getCount() / (double) columnCount);
	}

	@Override
	public Object[] getItem(int position) {
		// Package the columnCount real items to supply as row data
		// Use null to complete the array if we run out of real adapter items
		Object[] items = new Object[columnCount];
		int first = position * columnCount;
		for (int j = 0; j < columnCount; j++) {
			if (first + j < wrappedAdapter.getCount())
				items[j] = wrappedAdapter.getItem(first + j);
			else
				items[j] = null;
		}
		return items;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Possibly reuse the row layout view
		GridMimicRow gridMimicRow;
		if (convertView != null && convertView instanceof GridMimicRow) {
			gridMimicRow = (GridMimicRow) convertView;
		} else {
			gridMimicRow = new GridMimicRow(context);
		}

		// Gather the views to contain in this row, directly from the wrapped
		// adapter
		// Use null to complete the array if we run out of real adapter items
		final View[] views = new View[columnCount];
		final Object[] items = getItem(position);
		for (int i = 0; i < columnCount; i++) {
			if (position * columnCount + i < wrappedAdapter.getCount()) {
				View recycleView = gridMimicRow.getChildCount() >= i ? gridMimicRow
						.getChildAt(i) : null;
				views[i] = wrappedAdapter.getView(position * columnCount + i,
						recycleView, parent);
			} else {
				views[i] = null;
			}
		}
		gridMimicRow.setItems(views, items, onMimicryItemClicked);

		return gridMimicRow;
	}

	// Contains a number of items, simply as views with even width in a
	// horizontal linear layout
	private static class GridMimicRow extends LinearLayout {

		public GridMimicRow(Context context) {
			super(context);
			setOrientation(LinearLayout.HORIZONTAL);
			setBaselineAligned(false);
		}

		public void setItems(final View[] views, final Object[] items,
				final OnMimicryItemClickedListener itemClicked) {
			removeAllViews();
			setWeightSum(views.length);
			for (int j = 0; j < views.length; j++) {
				View view = views[j];
				if (view == null) {
					// Insert a placeholder for this empty item
					view = new View(getContext());
				} else {
					view.setClickable(true);
					view.setFocusable(true);
					final Object item = items[j];
					view.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (itemClicked != null)
								itemClicked.onItemClicked(item);
						}
					});
				}
				addView(view, new LinearLayout.LayoutParams(0,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1F));
			}
		}

	}

	public interface OnMimicryItemClickedListener {
		void onItemClicked(Object item);
	}

}
