package com.twg.notesapp;

import java.util.ArrayList;

import com.twg.notesapp.model.NoteModel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NotesActivityAdapter extends ArrayAdapter<NoteModel> {
	
	private final Activity context;
    private final ArrayList<NoteModel> models;
    
    public NotesActivityAdapter(final Activity context, final ArrayList<NoteModel> models) {
        super(context, 0, models.toArray(new NoteModel[0]));
        this.context = context;
        this.models = models;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            final LayoutInflater inflater = this.context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.listitem_note, null);
            
            final TextView title = (TextView) rowView.findViewById(R.id.listitem_note_title);
            final TextView body = (TextView) rowView.findViewById(R.id.listitem_note_body);
            
            final NoteItemViewHolder viewHolder = new NoteItemViewHolder();
            viewHolder.setTitle(title);
            viewHolder.setBody(body);
            
            rowView.setTag(viewHolder);
        }
        
        final NoteItemViewHolder viewHolder = (NoteItemViewHolder)rowView.getTag();
        final NoteModel model = this.models.get(position);
        viewHolder.getTitle().setText(model.getTitle());
        viewHolder.getBody().setText(model.getBody());
        
        return rowView;
    }
    
    private final class NoteItemViewHolder {
        private TextView title;
        private TextView body;
        
		public TextView getTitle() {
			return title;
		}
		
		public void setTitle(TextView title) {
			this.title = title;
		}
		
		public TextView getBody() {
			return body;
		}
		
		public void setBody(TextView body) {
			this.body = body;
		}

    }
}
