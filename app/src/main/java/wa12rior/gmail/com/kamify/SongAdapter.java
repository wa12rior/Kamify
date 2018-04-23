package wa12rior.gmail.com.kamify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Friizza on 18.02.2018.
 *
 * {@link SongAdapter} is an {@link ArrayAdapter}  that can provide the layout for each list item
 * based on a data source, which is a list of {@link Song} objects.
 */

public class SongAdapter extends ArrayAdapter<Song> {

    /**
     * Create a new {@link SongAdapter} object.
     *
     * @param context - current context
     * @param songs - list of {@link Song}s to be displayed
     */
    public SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        // Get the current song by its position
        Song currentSong = getItem(position);

        // Finding TextViews

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        TextView songName = (TextView) listItemView.findViewById(R.id.song_name);
        TextView duration = (TextView) listItemView.findViewById(R.id.duration);

        // Binding data to TextViews

        author.setText(currentSong.getSongAuthor());
        songName.setText(currentSong.getSongName());
        duration.setText(currentSong.getDuration());

        return listItemView;
    }
}
