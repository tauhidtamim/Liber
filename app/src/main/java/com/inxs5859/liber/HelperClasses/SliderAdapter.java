package com.inxs5859.liber.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.inxs5859.liber.R;

public class SliderAdapter extends PagerAdapter {

    Context context; //context means activity
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[]={
            R.drawable.ic_reading_person,
            R.drawable.bookshelf,
            R.drawable.ic_search_books,
    };

    int headings[]={
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
    };

    int descriptions[]={
            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc,
    };

    @Override
    public int getCount() {
        return headings.length;//how many slides
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout)object;
    }

    //to initialize the heading and text


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //requesting system so that we can use design
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //specify which design we want to use. in this case -> slides_layout
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        //hooks
        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView desc = view.findViewById(R.id.slider_desc);


        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
